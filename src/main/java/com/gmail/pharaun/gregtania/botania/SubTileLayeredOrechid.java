package com.gmail.pharaun.gregtania.botania;

import com.gmail.pharaun.gregtania.misc.BotaniaHelper;
import javafx.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.WeightedRandom;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.Botania;
import vazkii.botania.common.lexicon.LexiconData;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Rio on 7/9/2018.
 */
public class SubTileLayeredOrechid extends SubTileAbstractEvolvedOrechid {

    private static final int COST = 17500;
    private static final int COST_GOG = 700;
    private static final int DELAY = 100;
    private static final int DELAY_GOG = 2;

    public static final Set<Block> sourceBlocks = new HashSet<>(Collections.singletonList(Blocks.sand));
    public static LexiconEntry lexiconEntry = null;

    @Override
    public boolean canOperate() {
        return true;
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
        return 0x818181;
    }

    @Override
    public LexiconEntry getEntry() {
        return lexiconEntry;
    }

    @Override
    public String getOredictToPut(int x, int y, int z) {
        Block block = supertile.getWorldObj().getBlock(x, y, z);
        int meta = supertile.getWorldObj().getBlockMetadata(x, y, z);
        if (block == Blocks.sand) return BLACK_SANDS;

        List<BotaniaHelper.StringRandomItem> oreDicts = BotaniaHelper.wgLayerOres.get(new Util.BlockType(block, meta));
        if (oreDicts != null && oreDicts.size() > 0) {
            return ((BotaniaHelper.StringRandomItem)WeightedRandom.getRandomItem(supertile.getWorldObj().rand, oreDicts)).s;
        }
        return null;
    }

    @Override
    public Collection<BotaniaHelper.StringRandomItem> getOreWeights() {
        return null;
    }

}
