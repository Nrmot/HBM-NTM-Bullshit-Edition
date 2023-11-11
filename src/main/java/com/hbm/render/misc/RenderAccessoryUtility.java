package com.hbm.render.misc;

import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.util.Map;

public class RenderAccessoryUtility {

	private static final ResourceLocation hbm = new ResourceLocation(RefStrings.MODID + ":textures/models/CapeHbm.png");
	private static final ResourceLocation hbm2 = new ResourceLocation(RefStrings.MODID + ":textures/models/CapeHbm2.png");
	private static final ResourceLocation drillgon = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeDrillgon.png");
	private static final ResourceLocation dafnik = new ResourceLocation(RefStrings.MODID + ":textures/models/CapeDafnik.png");
	private static final ResourceLocation lpkukin = new ResourceLocation(RefStrings.MODID + ":textures/models/CapeShield.png");
	private static final ResourceLocation vertice = new ResourceLocation(RefStrings.MODID + ":textures/models/CapeVertice_2.png");
	private static final ResourceLocation red = new ResourceLocation(RefStrings.MODID + ":textures/models/CapeRed.png");
	private static final ResourceLocation ayy = new ResourceLocation(RefStrings.MODID + ":textures/models/CapeAyy.png");
	private static final ResourceLocation nostalgia = new ResourceLocation(RefStrings.MODID + ":textures/models/CapeNostalgia.png");
	private static final ResourceLocation sam = new ResourceLocation(RefStrings.MODID + ":textures/models/CapeSam.png");
	private static final ResourceLocation hoboy = new ResourceLocation(RefStrings.MODID + ":textures/models/CapeHoboy.png");
	private static final ResourceLocation master = new ResourceLocation(RefStrings.MODID + ":textures/models/CapeMaster.png");
	private static final ResourceLocation mek = new ResourceLocation(RefStrings.MODID + ":textures/models/CapeMek.png");
	private static final ResourceLocation test = new ResourceLocation(RefStrings.MODID + ":textures/models/CapeTest.png");
	private static final ResourceLocation swiggs = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeSweatySwiggs.png");
	private static final ResourceLocation doctor17 = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeDoctor17.png");
	private static final ResourceLocation shimmeringblaze = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeBlaze.png");
	private static final ResourceLocation wiki = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeWiki.png");
	private static final ResourceLocation leftnugget = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeLeftNugget.png");
	private static final ResourceLocation rightnugget = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeRightNugget.png");
	private static final ResourceLocation alcater = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/capealcater.png");
	private static final ResourceLocation golem = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/capegolem.png");
	
	public static ResourceLocation getCloakFromPlayer(EntityPlayer player) {
		String uuid = player.getUniqueID().toString();
		String name = player.getDisplayName().getUnformattedText();
		if(uuid.equals(Library.HbMinecraft)) {

			if(MainRegistry.polaroidID == 11)
				return hbm;
			else
				return hbm2;
		}
		if(uuid.equals(Library.Drillgon)) {
			return drillgon;
		}
		if(uuid.equals(Library.Dafnik)) {
			return dafnik;
		}
		if(uuid.equals(Library.LPkukin)) {
			return lpkukin;
		}
		if(uuid.equals(Library.LordVertice)) {
			return vertice;
		}
		if(uuid.equals(Library.CodeRed_)) {
			return red;
		}
		if(uuid.equals(Library.dxmaster769)) {
			return ayy;
		}
		if(uuid.equals(Library.Dr_Nostalgia)) {
			return nostalgia;
		}
		if(uuid.equals(Library.Samino2)) {
			return sam;
		}
		if(uuid.equals(Library.Hoboy03new)) {
			return hoboy;
		}
		if(uuid.equals(Library.Dragon59MC)) {
			return master;
		}
		if(uuid.equals(Library.SteelCourage)) {
			return mek;
		}
		if(uuid.equals(Library.SweatySwiggs)) {
			return swiggs;
		}
		if(uuid.equals(Library.Doctor17) || uuid.equals(Library.Doctor17PH)) {
			return doctor17;
		}
		if(uuid.equals(Library.ShimmeringBlaze)) {
			return shimmeringblaze;
		}
		if(uuid.equals(Library.FifeMiner)) {
			return leftnugget;
		}
		if(uuid.equals(Library.lag_add)) {
			return rightnugget;
		}
		if(Library.contributors.contains(uuid)) {
			return wiki;
		}
		if(name.startsWith("Player")) {
			return test;
		}
		if(uuid.equals(Library.Alcater)) {
			return alcater;
		}
		if(uuid.equals(Library.Golem)) {
			return golem;
		}

		return null;
	}
	
	public static void loadCape(NetworkPlayerInfo info, ResourceLocation rl) {
		try {
			// Map<Type, ResourceLocation> playerTextures =
			// ObfuscationReflectionHelper.getPrivateValue(NetworkPlayerInfo.class,
			// info, "playerTextures");
			@SuppressWarnings("deprecation")
			Map<Type, ResourceLocation> playerTextures = ReflectionHelper.getPrivateValue(NetworkPlayerInfo.class, info, "playerTextures", "field_187107_a");

			playerTextures.put(Type.CAPE, rl);
		} catch(Exception x) {
		}
	}
}