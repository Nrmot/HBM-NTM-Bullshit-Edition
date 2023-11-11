package com.hbm.tileentity;

import api.hbm.energy.IEnergyConductor;
import api.hbm.energy.IPowerNet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class TileEntityProxyConductor extends TileEntityProxyBase implements IEnergyConductor {

	@Override
	public long transferPower(long power) {
		
		TileEntity te = this.getTE();
		
		if(te instanceof IEnergyConductor) {
			return ((IEnergyConductor)te).transferPower(power);
		}
		
		return 0;
	}

	@Override
	public long getPower() {
		
		TileEntity te = this.getTE();
		
		if(te instanceof IEnergyConductor) {
			return ((IEnergyConductor)te).getPower();
		}
		
		return 0;
	}

	@Override
	public long getMaxPower() {
		
		TileEntity te = this.getTE();
		
		if(te instanceof IEnergyConductor) {
			return ((IEnergyConductor)te).getMaxPower();
		}
		
		return 0;
	}

	@Override
	public IPowerNet getPowerNet() {
		
		TileEntity te = this.getTE();
		
		if(te instanceof IEnergyConductor) {
			return ((IEnergyConductor)te).getPowerNet();
		}
		
		return null;
	}

	@Override
	public void setPowerNet(IPowerNet network) {
		
		TileEntity te = this.getTE();
		
		if(te instanceof IEnergyConductor) {
			((IEnergyConductor)te).setPowerNet(network);
		}
	}
	
	@Override
	public List<BlockPos> getConnectionPoints() {
		
		/*TileEntity te = this.getTE();
		
		if(te instanceof IEnergyConductor) {
			return ((IEnergyConductor)te).getConnectionPoints();
		}*/
		
		/* Proxy TE doesn't need to implement proxying here because the conductor main TE already has a network-specific proxying system */
		return new ArrayList();
	}
}
