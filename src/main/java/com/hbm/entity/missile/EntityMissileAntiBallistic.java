package com.hbm.entity.missile;

import com.hbm.config.WeaponConfig;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.packet.LoopedEntitySoundPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.amlfrom1710.Vec3;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class EntityMissileAntiBallistic extends EntityMissileBaseAdvanced {

	private static final double steps = 5;

	public EntityMissileAntiBallistic(World p_i1582_1_) {
		super(p_i1582_1_);
		this.motionY = 0.5;

		this.velocity = 0.0;
	}
	
	@Override
    public void onUpdate() {
		if(this.ticksExisted < 10){
			ExplosionLarge.spawnParticlesRadial(world, posX, posY, posZ, 15);
			return;
		} else if(this.ticksExisted < 60){
			this.setLocationAndAngles(posX + this.motionX, posY + this.motionY, posZ + this.motionZ, 0, 0);
			this.rotation();
			if(this.world.isRemote) {
				MainRegistry.proxy.spawnParticle(posX, posY, posZ, "exHydrogen", new float[]{(float)(this.motionX * -3D), (float)(this.motionY * -3D), (float)(this.motionZ * -3D)});
			}
			return;
		}

		this.getDataManager().set(HEALTH, Integer.valueOf(this.health));
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		
		if(this.velocity < 20)
			this.velocity += 0.05;

		for(int i = 0; i < steps; i++) {
			double[] targetVec = targetMissile();
			if(targetVec != null){
				this.motionX = targetVec[0] * velocity;
				this.motionY = targetVec[1] * velocity;
				this.motionZ = targetVec[2] * velocity;
			}
			this.setLocationAndAngles(posX + this.motionX, posY + this.motionY, posZ + this.motionZ, 0, 0);
			this.rotation();

			if(this.world.isRemote) {
				MainRegistry.proxy.spawnParticle(posX, posY, posZ, "exDark", new float[]{(float)(this.motionX * -3D), (float)(this.motionY * -3D), (float)(this.motionZ * -3D)});
			}
			explodeIfNearTarget();

		}
		Block b = this.world.getBlockState(new BlockPos((int) this.posX, (int) this.posY, (int) this.posZ)).getBlock();
		if((b != Blocks.AIR && b != Blocks.WATER && b != Blocks.FLOWING_WATER) || posY < 1 || posY > 7000) {
			if(posY < 1){
				this.setLocationAndAngles((int)this.posX, world.getHeight((int)this.posX, (int)this.posZ), (int)this.posZ, 0, 0);
			}
			if (!this.world.isRemote) {
				onImpact();
			}
			this.setDead();
			return;
		}

		PacketDispatcher.wrapper.sendToAll(new LoopedEntitySoundPacket(this.getEntityId()));
		if((int) (posX / 16) != this.chunkX || (int) (posZ / 16) != this.chunkZ){
			this.chunkX = (int) (posX / 16);
			this.chunkZ = (int) (posZ / 16);
			loadNeighboringChunks(this.chunkX, this.chunkZ);
		}
    }

    private double[] targetMissile() {
    	//Targeting missiles - returns normalized vector pointing towards closest rocket
		List<Entity> listOfMissiles = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(posX - WeaponConfig.radarRange, 0, posZ - WeaponConfig.radarRange, posX + WeaponConfig.radarRange, 5000, posZ + WeaponConfig.radarRange));
		
		Entity target = null;
		double closest = WeaponConfig.radarRange*2;
		
		for(Entity e : listOfMissiles) {
			if(!(e instanceof EntityMissileAntiBallistic) && (e instanceof EntityMissileBaseAdvanced || e instanceof EntityMissileCustom)) {
				double dis = Math.sqrt(Math.pow(e.posX - posX, 2) + Math.pow(e.posY - posY, 2) + Math.pow(e.posZ - posZ, 2));
				
				if(dis < closest) {
					closest = dis;
					target = e;
				}
			}
		}
		
		if(target != null) {
			Vec3 vec = Vec3.createVectorHelper(target.posX - posX, target.posY - posY, target.posZ - posZ);
			vec = vec.normalize();
			
			return new double[]{vec.xCoord/steps, vec.yCoord/steps, vec.zCoord/steps};
		}
		return null;
    }

    private void explodeIfNearTarget(){
    	List<Entity> listOfMissilesInExplosionRange = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(posX - 7.5, posY - 7.5, posZ - 7.5, posX + 7.5, posY + 7.5, posZ + 7.5));

		boolean hasHits = false;
		for(Entity e : listOfMissilesInExplosionRange) {
			if(!(e instanceof EntityMissileAntiBallistic) && (e instanceof EntityMissileBaseAdvanced || e instanceof EntityMissileCustom)) {
				e.attackEntityFrom(ModDamageSource.blast, 40);
				hasHits = true;
			}
		}
		if(hasHits){
			ExplosionLarge.explode(world, posX, posY, posZ, 15F, true, false, true);
			this.setDead();
        }
    }

	@Override
	public RadarTargetType getTargetType() {
		return RadarTargetType.MISSILE_AB;
	}

	@Override
	public List<ItemStack> getDebris() {
		List<ItemStack> list = new ArrayList<ItemStack>();

		list.add(new ItemStack(ModItems.plate_titanium, 4));
		list.add(new ItemStack(ModItems.thruster_small, 1));
		list.add(new ItemStack(ModItems.circuit_targeting_tier1, 1));
		
		return list;
	}

	@Override
	public ItemStack getDebrisRareDrop() {
		return new ItemStack(ModItems.circuit_targeting_tier3);
	}

	@Override
	public void onImpact() {
		ExplosionLarge.explode(world, posX, posY, posZ, 10.0F, true, true, true);
	}
}
