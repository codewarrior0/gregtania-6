package com.gmail.pharaun.gregtania.misc;

import com.gmail.pharaun.gregtania.botania.*;
import com.gmail.pharaun.gregtania.botania.tiers.*;
import vazkii.botania.common.Botania;

/**
 * Created by Rio on 7/12/2018.
 */
public class ModFlowers {
    public static final String SUBTILE_CLAYCONIA_ALLUVIA = "clayconiaAlluvia";
    public static final String SUBTILE_STRATODENDRON = "stratodendron";
    public static final String SUBTILE_EVOLVED_ORECHID_SMALL = "evolvedOrechidSmall";
    public static final String SUBTILE_EVOLVED_ORECHID = "evolvedOrechid";
    public static final String SUBTILE_EVOLVED_ORECHID_IGNEM = "evolvedOrechidIgnem";
    public static final String SUBTILE_EVOLVED_ORECHID_ENDIUM = "evolvedOrechidEndium";
    public static final String SUBTILE_BUMBLEBISCUS = "bumblebiscus";

    public static void init() {


        Util.registerFlower(SUBTILE_EVOLVED_ORECHID_SMALL, SubTileSmallOrechid.class);
        Util.registerFlower(SUBTILE_EVOLVED_ORECHID, SubTileLayeredOrechid.class);

        Util.registerFlower(SUBTILE_EVOLVED_ORECHID_IGNEM + "I", OrechidIgnemI.class);
        Util.registerFlower(SUBTILE_EVOLVED_ORECHID_IGNEM + "II", OrechidIgnemII.class);
        Util.registerFlower(SUBTILE_EVOLVED_ORECHID_IGNEM + "III", OrechidIgnemIII.class);

        Util.registerFlower(SUBTILE_EVOLVED_ORECHID_ENDIUM + "I", OrechidEndiumI.class);
        Util.registerFlower(SUBTILE_EVOLVED_ORECHID_ENDIUM + "II", OrechidEndiumII.class);
        Util.registerFlower(SUBTILE_EVOLVED_ORECHID_ENDIUM + "III", OrechidEndiumIII.class);
        Util.registerFlower(SUBTILE_EVOLVED_ORECHID_ENDIUM + "IV", OrechidEndiumIV.class);

        Util.registerFlower(SUBTILE_STRATODENDRON + "I", StratodendronI.class);
        Util.registerFlower(SUBTILE_STRATODENDRON + "II", StratodendronII.class);
        Util.registerFlower(SUBTILE_STRATODENDRON + "III", StratodendronIII.class);
        Util.registerFlower(SUBTILE_STRATODENDRON + "IV", StratodendronIV.class);

        Util.registerFlower(SUBTILE_BUMBLEBISCUS, SubTileBumblebiscus.class);

        Util.registerFlower("autoclavicusVile", SubTileAutoclavicus.class);

        if (Botania.gardenOfGlassLoaded) {
            // Gravel Clayconia - because:
            // sand needs an alchemy catalyst, which needs gold, which needs a crucible
            // and Clayconia needs an earth rune, which needs iron, which needs a clay crucible

            Util.registerFlower(SUBTILE_CLAYCONIA_ALLUVIA, SubTileClayconiaAlluvia.class);
        }
    }
}
