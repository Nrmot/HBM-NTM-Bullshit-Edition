package com.hbm.handler.jei;

import mezz.jei.api.IGuiHelper;

public class SILEXXRayRecipeHandler extends SILEXRecipeHandler {

	public SILEXXRayRecipeHandler(IGuiHelper help){
		super(help);
	}

	@Override
	public String getUid(){
		return JEIConfig.SILEX_XRAY;
	}

	@Override
	public String getTitle(){
		return "SILEX X-Ray Recipes";
	}
}
