package com.hbm.items.bomb;

import com.hbm.items.special.ItemHazard;
import com.hbm.main.MainRegistry;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ItemBoy extends ItemHazard {

	public ItemBoy(float radiation, String s) {
		super(radiation, s);
		this.setCreativeTab(MainRegistry.nukeTab);
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flagIn) {
		list.add("Used in:");
		list.add(" Little Boy");
		super.addInformation(stack, world, list, flagIn);
	}
	
}
