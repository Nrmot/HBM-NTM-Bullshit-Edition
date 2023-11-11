package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityMachineBoiler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMachineBoiler extends Container {

private final TileEntityMachineBoiler diFurnace;
	
	public ContainerMachineBoiler(InventoryPlayer invPlayer, TileEntityMachineBoiler tedf) {
		
		diFurnace = tedf;

		//Fluid ID 
		//Drillgon200: don't need these anymore
		//this.addSlotToContainer(new Slot(tedf, 0, 8, 17));
		//this.addSlotToContainer(new SlotMachineOutput(invPlayer.player, tedf, 1, 8, 53));
		
		//Input IO
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 2, 26, 17));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 3, 26, 53));
		//Fuel
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 4, 80, 53));
		//Output IO
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 5, 134, 17));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 6, 134, 53));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142));
		}
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2)
    {
		ItemStack var3 = ItemStack.EMPTY;
		Slot var4 = this.inventorySlots.get(par2);
		
		if (var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			
            if (par2 <= 6) {
				if (!this.mergeItemStack(var5, 7, this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(var5, 4, 5, false))
			{
				if (!this.mergeItemStack(var5, 2, 3, false))
					if (!this.mergeItemStack(var5, 5, 6, false))
						if (!this.mergeItemStack(var5, 0, 1, false))
							return ItemStack.EMPTY;
			}
			
			if (var5.isEmpty())
			{
				var4.putStack(ItemStack.EMPTY);
			}
			else
			{
				var4.onSlotChanged();
			}
		}
		
		return var3;
    }

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return diFurnace.isUseableByPlayer(player);
	}
}
