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

import java.util.Collection;

public class OrechidEndiumIV extends OrechidEndiumI {

	private static final int ORE_TIER = 4;

	@Override
	public Collection<BotaniaHelper.StringRandomItem> getOreWeights() {
    	return BotaniaHelper.tieredOreWeightEnd.get(ORE_TIER);
	}

}
