package com.hbm.handler.jei;

import mezz.jei.api.IGuiHelper;

public class SILEXUVRecipeHandler extends SILEXRecipeHandler {

	public SILEXUVRecipeHandler(IGuiHelper help){
		super(help);
	}

	@Override
	public String getUid(){
		return JEIConfig.SILEX_UV;
	}

	@Override
	public String getTitle(){
		return "SILEX UV Recipes";
	}
}
