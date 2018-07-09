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

import java.util.*;

public class OrechidEndiumI extends SubTileAbstractEvolvedOrechid {

	private static final int COST = 22500;
	private static final int COST_GOG = 1100;
	private static final int DELAY = 300;
	private static final int DELAY_GOG = 6;

	private static final int ORE_TIER = 1;
	private static final ArrayList<Block> sourceBlocks = new ArrayList<>(Collections.singletonList(Blocks.end_stone));

    @Override
	public boolean canOperate() {
		return supertile.getWorldObj().provider.dimensionId == 1;
	}

	@Override
	public Collection<BotaniaHelper.StringRandomItem> getOreWeights() {
    	return BotaniaHelper.tieredOreWeightEnd.get(ORE_TIER);
	}

	@Override
	public List<Block> getSourceBlocks() {
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
		return 0x8026AD;
	}

	@Override
	public LexiconEntry getEntry() {
		return LexiconData.orechidIgnem;
	}
}
