package com.hbm.blocks.generic;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.RadiationSystemNT;
import com.hbm.interfaces.IDoor;
import com.hbm.interfaces.IRadResistantBlock;
import com.hbm.lib.ForgeDirection;
import com.hbm.tileentity.DoorDecl;
import com.hbm.tileentity.TileEntityDoorGeneric;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockDoorGeneric extends BlockDummyable  implements IRadResistantBlock {

	public DoorDecl type;
	private final boolean isRadResistant;
	
	public BlockDoorGeneric(Material materialIn, DoorDecl type, boolean isRadResistant, String s){
		super(materialIn, s);
		this.type = type;
		this.isRadResistant = isRadResistant;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta){
		if(meta >= 12)
			return new TileEntityDoorGeneric();
		return null;
	}

	@Override
	public int[] getDimensions(){
		return type.getDimensions();
	}

	@Override
	public int getOffset(){
		return 0;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		if(!world.isRemote && !playerIn.isSneaking()) {
			int[] pos1 = findCore(world, pos.getX(), pos.getY(), pos.getZ());
			if(pos1 == null)
				return false;
			TileEntityDoorGeneric door = (TileEntityDoorGeneric) world.getTileEntity(new BlockPos(pos1[0], pos1[1], pos1[2]));

			if(door != null) {
				return door.tryToggle(playerIn);
			}
		}
        return !playerIn.isSneaking();
    }
	
	@Override
	public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity){
		TileEntity te = world.getTileEntity(pos);
		int meta = state.getValue(META);
		boolean open = hasExtra(meta) || (te instanceof TileEntityDoorGeneric && ((TileEntityDoorGeneric)te).shouldUseBB);
		return type.isLadder(open);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState){
		AxisAlignedBB box = state.getCollisionBoundingBox(worldIn, pos);
		if(box!= null && (box.minY == 0 && box.maxY == 0))
			return;
		super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, isActualState);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos){
		if(!world.isRemote){
			int[] corePos = findCore(world, pos.getX(), pos.getY(), pos.getZ());
			if(corePos != null){
				TileEntity core = world.getTileEntity(new BlockPos(corePos[0], corePos[1], corePos[2]));
				if(core instanceof TileEntityDoorGeneric){
					TileEntityDoorGeneric door = (TileEntityDoorGeneric)core;
					door.updateRedstonePower(pos);
				}
			}
		}
		super.neighborChanged(state, world, pos, blockIn, fromPos);
	}

	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return true;
	}

	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
		int meta = state.getValue(META);
		TileEntity te = source.getTileEntity(pos);
		int[] core = this.findCore(source, pos.getX(), pos.getY(), pos.getZ());
		boolean open = hasExtra(meta) || (te instanceof TileEntityDoorGeneric && ((TileEntityDoorGeneric)te).shouldUseBB);
		if(core == null){
			return FULL_BLOCK_AABB;
		}
		TileEntity te2 = source.getTileEntity(new BlockPos(core[0], core[1], core[2]));
		ForgeDirection dir = ForgeDirection.getOrientation(te2.getBlockMetadata() - BlockDummyable.offset);
		AxisAlignedBB box = type.getBlockBound(pos.add(-core[0], -core[1], -core[2]).rotate(dir.getBlockRotation().add(Rotation.COUNTERCLOCKWISE_90)), open);
		//System.out.println(te2.getBlockMetadata()-offset);
		switch(te2.getBlockMetadata()-offset){
		case 2:
			return new AxisAlignedBB(1-box.minX, box.minY, 1-box.minZ, 1-box.maxX, box.maxY, 1-box.maxZ);
		case 4:
			return new AxisAlignedBB(1-box.minZ, box.minY, box.minX, 1-box.maxZ, box.maxY, box.maxX);
		case 3:
			return new AxisAlignedBB(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
		case 5:
			return new AxisAlignedBB(box.minZ, box.minY, 1-box.minX, box.maxZ, box.maxY, 1-box.maxX);
		}
		return FULL_BLOCK_AABB;
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if(this.isRadResistant){
			RadiationSystemNT.markChunkForRebuild(worldIn, pos);
		}
		super.onBlockAdded(worldIn, pos, state);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if(this.isRadResistant){
			RadiationSystemNT.markChunkForRebuild(worldIn, pos);
		}
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean isRadResistant(World worldIn, BlockPos blockPos){
		if (!this.isRadResistant)
			return false;

		if (worldIn != null) {
			TileEntity entity = worldIn.getTileEntity(blockPos);
			if (entity != null) {
				if (IDoor.class.isAssignableFrom(entity.getClass())) {
					// Doors should be rad resistant only when closed
					return ((IDoor) entity).getState() == IDoor.DoorState.CLOSED;
				}
			}
		}

		return true;
	}

	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		float hardness = this.getExplosionResistance(null);
		if(this.isRadResistant){
			tooltip.add("§2[Radiation Shielding]§r");
		}
		if(hardness > 50){
			tooltip.add("§6Blast Resistance: "+hardness+"§r");
		}
	}
}
