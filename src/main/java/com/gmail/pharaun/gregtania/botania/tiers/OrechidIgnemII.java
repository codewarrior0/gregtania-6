/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * 
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * 
 * File Created @ [Apr 30, 2015, 3:27:20 PM (GMT)]
 */
package com.gmail.pharaun.gregtania.botania.tiers;


import com.gmail.pharaun.gregtania.misc.BotaniaHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import vazkii.botania.api.BotaniaAPI;

import java.util.Map;

public class OrechidIgnemII extends OrechidIgnemI {

	private static final int ORE_TIER = 2;

	@Override
	public Map<String, Integer> getOreMap() {
    	return BotaniaHelper.tieredOreWeightNether.get(ORE_TIER);
	}

}
