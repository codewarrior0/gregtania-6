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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.Botania;
import vazkii.botania.common.lexicon.LexiconData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class OrechidI extends SubTileAbstractEvolvedOrechid {

	private static final int COST = 17500;
	private static final int COST_GOG = 700;
	private static final int DELAY = 100;
	private static final int DELAY_GOG = 2;

	private static final int ORE_TIER = 0;

	private static final ArrayList<Block> sourceBlocks = new ArrayList<>(Arrays.asList(Blocks.stone, Blocks.gravel, Blocks.sand));

    @Override
	public boolean canOperate() {
    	return true;
	}

	@Override
	public Map<String, Integer> getOreMap() {
    	return BotaniaHelper.tieredOreWeightOverworld.get(ORE_TIER);
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

	// TODO: This is a workaround for getting a red rose cos i haven't been able to track down why
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon() {
		return BotaniaAPI.getSignatureForName("evolvedOrechidI").getIconForStack(null);
	}
}
