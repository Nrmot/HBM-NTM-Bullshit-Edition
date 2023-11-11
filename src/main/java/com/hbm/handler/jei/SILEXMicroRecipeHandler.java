package com.hbm.handler.jei;

import mezz.jei.api.IGuiHelper;

public class SILEXMicroRecipeHandler extends SILEXRecipeHandler {

	public SILEXMicroRecipeHandler(IGuiHelper help){
		super(help);
	}

	@Override
	public String getUid(){
		return JEIConfig.SILEX_MICRO;
	}

	@Override
	public String getTitle(){
		return "SILEX Micro Recipes";
	}
}
