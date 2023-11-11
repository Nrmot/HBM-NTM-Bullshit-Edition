package com.hbm.entity.missile;

import com.hbm.config.BombConfig;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.explosion.ExplosionParticle;
import com.hbm.explosion.ExplosionParticleB;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class EntityMissileMicro extends EntityMissileBaseAdvanced {

	public EntityMissileMicro(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileMicro(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
        if (!this.world.isRemote)
        {
    	    	
    	    this.world.spawnEntity(EntityNukeExplosionMK4.statFac(world, BombConfig.fatmanRadius, posX, posY, posZ));
    	    
    	    if(MainRegistry.polaroidID == 11)
    	    	if(rand.nextInt(100) >= 0)
    	    	{
    	    		ExplosionParticleB.spawnMush(this.world, (int)this.posX, (int)this.posY - 3, (int)this.posZ);
    	    	} else {
    	    		ExplosionParticle.spawnMush(this.world, (int)this.posX, (int)this.posY - 3, (int)this.posZ);
    	    	}
    	    else
    	    	if(rand.nextInt(100) == 0)
    	    	{
    	    		ExplosionParticleB.spawnMush(this.world, (int)this.posX, (int)this.posY - 3, (int)this.posZ);
    	    	} else {
    	    		ExplosionParticle.spawnMush(this.world, (int)this.posX, (int)this.posY - 3, (int)this.posZ);
    	    	}
        }
	}

	@Override
	public List<ItemStack> getDebris() {
		List<ItemStack> list = new ArrayList<ItemStack>();

		list.add(new ItemStack(ModItems.wire_aluminium, 4));
		list.add(new ItemStack(ModItems.plate_titanium, 4));
		list.add(new ItemStack(ModItems.hull_small_aluminium, 2));
		list.add(new ItemStack(ModItems.ducttape, 1));
		list.add(new ItemStack(ModItems.circuit_targeting_tier1, 1));
		
		return list;
	}

	@Override
	public ItemStack getDebrisRareDrop() {
		return new ItemStack(ModItems.gun_fatman_ammo, 1);
	}

	@Override
	public RadarTargetType getTargetType() {
		return RadarTargetType.MISSILE_TIER0;
	}
}
