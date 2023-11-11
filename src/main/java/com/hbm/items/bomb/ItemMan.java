package com.hbm.items.bomb;

import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ItemMan extends Item {

	public ItemMan(String s){
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.nukeTab);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		list.add("Used in:");
		list.add(" Fat Man");
		super.addInformation(stack, worldIn, list, flagIn);
	}
}
