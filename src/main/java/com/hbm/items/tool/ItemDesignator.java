package com.hbm.items.tool;

import com.hbm.blocks.bomb.LaunchPad;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.main.MainRegistry;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.List;

public class ItemDesignator extends Item {

	public ItemDesignator(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.missileTab);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger("xCoord", 0);
		stack.getTagCompound().setInteger("zCoord", 0);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(stack.getTagCompound() != null)
		{
			tooltip.add("§aTarget Coordinates§r");
			tooltip.add("§aX: " + stack.getTagCompound().getInteger("xCoord") + "§r");
			tooltip.add("§aZ: " + stack.getTagCompound().getInteger("zCoord") + "§r");
		} else {
			tooltip.add("§ePlease select a target.§r");
		}
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		if(!(world.getBlockState(pos).getBlock() instanceof LaunchPad))
		{
			if(stack.getTagCompound() != null)
			{
				stack.getTagCompound().setInteger("xCoord", pos.getX());
				stack.getTagCompound().setInteger("zCoord", pos.getZ());
			} else {
				stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setInteger("xCoord", pos.getX());
				stack.getTagCompound().setInteger("zCoord", pos.getZ());
			}
	        if(world.isRemote)
			{
	        	player.sendMessage(new TextComponentTranslation("§a[Position set]§r"));
			}

	        world.playSound(player.posX, player.posY, player.posZ, HBMSoundHandler.techBleep, SoundCategory.PLAYERS, 1.0F, 1.0F, true);
        	
	        return EnumActionResult.SUCCESS;
		}
    	
        return EnumActionResult.PASS;
	}
}
