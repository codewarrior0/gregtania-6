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
import com.gmail.pharaun.gregtania.botania.SubTileLayeredOrechid;
import com.gmail.pharaun.gregtania.misc.BotaniaHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.Botania;
import vazkii.botania.common.lexicon.LexiconData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class OrechidI extends SubTileLayeredOrechid {

	private static final int COST = 17500;
	private static final int COST_GOG = 700;
	private static final int DELAY = 100;
	private static final int DELAY_GOG = 2;

	private static final ArrayList<Block> sourceBlocks = new ArrayList<>(Arrays.asList(Blocks.stone, Blocks.gravel, Blocks.sand));

	@Override
	public int getOreTier() {
		return 0;
	}

    @Override
	public boolean canOperate() {
    	return true;
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
		return 0x818181;
	}

	@Override
	public LexiconEntry getEntry() {
		return LexiconData.orechid;
	}

}
