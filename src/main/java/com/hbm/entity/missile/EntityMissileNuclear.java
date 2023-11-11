package com.hbm.entity.missile;

import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.items.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class EntityMissileNuclear extends EntityMissileBaseAdvanced {

	public EntityMissileNuclear(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileNuclear(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
   	
    	this.world.spawnEntity(EntityNukeExplosionMK4.statFac(world, BombConfig.missileRadius, posX, posY, posZ));
		EntityNukeCloudSmall entity2 = new EntityNukeCloudSmall(this.world, BombConfig.missileRadius);
    	entity2.posX = this.posX;
    	entity2.posY = this.posY/* - 9*/;
    	entity2.posZ = this.posZ;
    	this.world.spawnEntity(entity2);
	}

	@Override
	public List<ItemStack> getDebris() {
		List<ItemStack> list = new ArrayList<ItemStack>();

		list.add(new ItemStack(ModItems.plate_titanium, 16));
		list.add(new ItemStack(ModItems.plate_steel, 20));
		list.add(new ItemStack(ModItems.plate_aluminium, 12));
		list.add(new ItemStack(ModItems.thruster_large, 1));
		list.add(new ItemStack(ModItems.circuit_targeting_tier4, 1));
		
		return list;
	}

	@Override
	public ItemStack getDebrisRareDrop() {
		return new ItemStack(ModItems.warhead_nuclear);
	}

	@Override
	public RadarTargetType getTargetType() {
		return RadarTargetType.MISSILE_TIER4;
	}
}
