package com.hbm.sound;

import com.hbm.tileentity.machine.TileEntityMachineMiningDrill;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundEvent;

import java.util.ArrayList;
import java.util.List;

public class SoundLoopMiner extends SoundLoopMachine {
	
	public static List<SoundLoopMiner> list = new ArrayList<SoundLoopMiner>();

	public SoundLoopMiner(SoundEvent path, TileEntity te) {
		super(path, te);
		list.add(this);
	}

	@Override
	public void update() {
		super.update();
		
		if(te instanceof TileEntityMachineMiningDrill) {
			TileEntityMachineMiningDrill drill = (TileEntityMachineMiningDrill)te;
			
			if(this.volume != 3)
				volume = 3;
			
			if(drill.torque <= 0.5F)
				this.donePlaying = true;
		}
	}
	
	public TileEntity getTE() {
		return te;
	}

}
