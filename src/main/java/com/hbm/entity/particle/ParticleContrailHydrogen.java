package com.hbm.entity.particle;

import com.hbm.entity.particle.ParticleContrail;

import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.World;

public class ParticleContrailHydrogen extends ParticleContrail {

	public ParticleContrailHydrogen(TextureManager manage, World worldIn, double posXIn, double posYIn, double posZIn) {
		super(manage, worldIn, posXIn, posYIn, posZIn, 0.9F, 0.9F, 0.9F, 1F);
		flameRed = 0.9F;
		flameGreen = 0.6F;
		flameBlue = 1F;
		this.doFlames = true;
	}
}
