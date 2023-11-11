package com.hbm.items.machine;

import com.hbm.items.ModItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

public class ItemLens extends Item {

	public long maxDamage;
	public float fieldMod;
	public float drainMod;
	
	public ItemLens(long maxDamage, float fieldMod, float drainMod, String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.maxDamage = maxDamage;
		this.fieldMod = fieldMod;
		this.drainMod = drainMod;
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		long damage = getLensDamage(stack);
		double percent = (int)((maxDamage - damage) * 100000000D / maxDamage) / 1000000D;


		tooltip.add("§3Durability in ticks: " + (maxDamage - damage) + " / " + maxDamage);
		tooltip.add("§3Durability in Percent: " + percent + "%");

		tooltip.add("§eForce Field Modifier: " + (fieldMod >= 1 ? "§a+" : "§c") + (Math.round(fieldMod * 1000) * .10 - 100) + "%");
		tooltip.add("§ePower Drain Modifier: " + (drainMod >= 1 ? "§c+" : "§a") + (Math.round(drainMod * 1000) * .10 - 100) + "%");
	}
	
	@Override
    public double getDurabilityForDisplay(ItemStack stack){
        return (double)getLensDamage(stack) / (double)maxDamage;
    }
    
    @Override
    public boolean showDurabilityBar(ItemStack stack){
        return getDurabilityForDisplay(stack) != 0;
    }
	
	public static long getLensDamage(ItemStack stack) {
		
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
			return 0;
		}
		
		return stack.getTagCompound().getLong("damage");
	}
	
	public static void setLensDamage(ItemStack stack, long damage) {
		
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		
		stack.getTagCompound().setLong("damage", damage);
	}
	
	
}
