package com.gmail.pharaun.gregtania.botania.tiers;

import com.gmail.pharaun.gregtania.botania.SubTileAbstractStratodendron;
import vazkii.botania.api.lexicon.LexiconEntry;

/**
 * Created by Rio on 7/10/2018.
 */
public class StratodendronII extends SubTileAbstractStratodendron {

    public static LexiconEntry lexiconEntry;

    @Override
    public LexiconEntry getEntry() {
        return lexiconEntry;
    }

    @Override
    public int getStoneTier() {
        return 1;
    }

}
