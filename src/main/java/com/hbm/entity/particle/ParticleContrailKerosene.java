package com.hbm.entity.particle;

import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.World;

public class ParticleContrailKerosene extends ParticleContrail {

	public ParticleContrailKerosene(TextureManager manage, World worldIn, double posXIn, double posYIn, double posZIn) {
		super(manage, worldIn, posXIn, posYIn, posZIn, 0.9F, 0.8F, 0.7F, 1F);
		flameRed = 0.5F;
		flameGreen = 0.9F;
		flameBlue = 1F;
		this.doFlames = true;
	}
}
