/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 *
 * File Created @ [Mar 11, 2014, 5:40:55 PM (GMT)]
 */
package com.gmail.pharaun.gregtania.botania;

import gregapi.data.OP;
import gregapi.oredict.OreDictMaterial;
import gregapi.util.WD;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.oredict.OreDictionary;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileFunctional;
import vazkii.botania.common.core.handler.ConfigHandler;

import java.util.*;

public abstract class SubTileAbstractEvolvedOrechid extends SubTileFunctional {

	private static final int RANGE = 5;
	private static final int RANGE_Y = 3;

	/*
	 * Abstract entries for all of the sub-type to implement
	 */
	@Override
	public abstract int getColor();

	@Override
	public abstract LexiconEntry getEntry();
	public abstract boolean canOperate();
	public abstract Map<String, Integer> getOreMap();
	public abstract List<Block> getSourceBlocks();
	public abstract int getCost();
	public abstract int getDelay();

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (redstoneSignal > 0 || !canOperate())
			return;

		int cost = getCost();
		if (!supertile.getWorldObj().isRemote && mana >= cost && ticksExisted % getDelay() == 0) {
			ChunkCoordinates coords = getCoordsToPut();
			if (coords != null) {
				String oreDictEntry = getOreDictToPut();
				if (oreDictEntry != null) {
					ItemStack stack = OreDictionary.getOres(oreDictEntry).get(0);
					Block block = Block.getBlockFromItem(stack.getItem());
					int meta = stack.getItemDamage();

					OreDictMaterial mat = OreDictMaterial.get(oreDictEntry.substring(3));

					if (mat != null && OP.ore.mat(mat, 1) != null) {
						WD.setOre(supertile.getWorldObj(), coords.posX, coords.posY, coords.posZ, mat);
						supertile.getWorldObj().markBlockForUpdate(coords.posX, coords.posY, coords.posZ);
					} else {
						// Not gregtech, do a regular place
						supertile.getWorldObj().setBlock(coords.posX, coords.posY, coords.posZ, block, meta, 1 | 2);
					}

					if (ConfigHandler.blockBreakParticles)
						supertile.getWorldObj().playAuxSFX(2001, coords.posX, coords.posY, coords.posZ, Block.getIdFromBlock(block) + (meta << 12));
					supertile.getWorldObj().playSoundEffect(supertile.xCoord, supertile.yCoord, supertile.zCoord, "botania:orechid", 2F, 1F);

					mana -= cost;
					sync();
				}
			}
		}
	}

	public String getOreDictToPut() {
		Collection<WeightedRandom.Item> values = new ArrayList<>();
		Map<String, Integer> map = getOreMap();
		for(String s : map.keySet())
			values.add(new StringRandomItem(map.get(s), s));

		return ((StringRandomItem) WeightedRandom.getRandomItem(supertile.getWorldObj().rand, values)).s;
	}

	public ChunkCoordinates getCoordsToPut() {
		List<ChunkCoordinates> possibleCoords = new ArrayList<>();

		List<Block> sources = getSourceBlocks();
		for(int i = -RANGE; i < RANGE + 1; i++)
			for(int j = -RANGE_Y; j < RANGE_Y; j++)
				for(int k = -RANGE; k < RANGE + 1; k++) {
					int x = supertile.xCoord + i;
					int y = supertile.yCoord + j;
					int z = supertile.zCoord + k;
					Block block = supertile.getWorldObj().getBlock(x, y, z);
					for (Block source: sources) {
						if (block != null && block.isReplaceableOreGen(supertile.getWorldObj(), x, y, z, source))
							possibleCoords.add(new ChunkCoordinates(x, y, z));
					}
				}

		if(possibleCoords.isEmpty())
			return null;
		return possibleCoords.get(supertile.getWorldObj().rand.nextInt(possibleCoords.size()));
	}

	@Override
	public RadiusDescriptor getRadius() {
		return new RadiusDescriptor.Square(toChunkCoordinates(), RANGE);
	}

	@Override
	public boolean acceptsRedstone() {
		return true;
	}

	@Override
	public int getMaxMana() {
		return getCost();
	}

	private static class StringRandomItem extends WeightedRandom.Item {

		public String s;

		public StringRandomItem(int par1, String s) {
			super(par1);
			this.s = s;
		}

	}
}