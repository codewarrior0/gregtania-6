package com.gmail.pharaun.gregtania.botania;

import com.gmail.pharaun.gregtania.misc.BotaniaHelper;
import javafx.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.util.WeightedRandom;
import vazkii.botania.api.lexicon.LexiconEntry;

import java.util.Collection;
import java.util.List;

/**
 * Created by Rio on 7/9/2018.
 */
public abstract class SubTileLayeredOrechid extends SubTileAbstractEvolvedOrechid {
    @Override
    public String getOreDictToPut(int x, int y, int z) {
        Block block = supertile.getWorldObj().getBlock(x, y, z);
        int meta = supertile.getWorldObj().getBlockMetadata(x, y, z);
        if (block.getHarvestLevel(meta) > getOreTier()) return null;

        List<BotaniaHelper.StringRandomItem> oreDicts = BotaniaHelper.wgLayerOres.get(new Pair<Block, Byte>(block, (byte)meta));
        if (oreDicts != null) {
            return ((BotaniaHelper.StringRandomItem)WeightedRandom.getRandomItem(supertile.getWorldObj().rand, oreDicts)).s;
        }
        return null;
    }

    @Override
    public Collection<BotaniaHelper.StringRandomItem> getOreWeights() {
        return null;
    }

    public abstract int getOreTier();
}
