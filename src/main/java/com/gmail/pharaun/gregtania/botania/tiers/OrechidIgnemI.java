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

import com.gmail.pharaun.gregtania.botania.SubTileAbstractEvolvedOrechid;
import com.gmail.pharaun.gregtania.misc.BotaniaHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.Botania;
import vazkii.botania.common.lexicon.LexiconData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class OrechidIgnemI extends SubTileAbstractEvolvedOrechid {

	private static final int COST = 20000;
	private static final int COST_GOG = 900;
	private static final int DELAY = 200;
	private static final int DELAY_GOG = 4;

	private static final int ORE_TIER = 1;
	private static final Set<Block> sourceBlocks = new HashSet<>(Arrays.asList(Blocks.netherrack, Blocks.gravel));

	@Override
	public boolean canOperate() {
		return supertile.getWorldObj().provider.isHellWorld;
	}

	@Override
	public Collection<BotaniaHelper.StringRandomItem> getOreWeights() {
		return BotaniaHelper.tieredOreWeightNether.get(ORE_TIER);
	}

	@Override
	public Set<Block> getSourceBlocks() {
		return sourceBlocks;
	}

	@Override
	public int getCost() {
		return Botania.gardenOfGlassLoaded ? COST_GOG : COST;
	}

	@Override
	public int getDelay() {
		return Botania.gardenOfGlassLoaded ? DELAY_GOG : DELAY;
	}

	@Override
	public int getColor() {
		return 0xAE3030;
	}

	@Override
	public LexiconEntry getEntry() {
		return LexiconData.orechidIgnem;
	}

}
