package com.gmail.pharaun.gregtania.misc;

import com.gmail.pharaun.gregtania.botania.SubTileBumblebiscus;
import com.gmail.pharaun.gregtania.botania.SubTileClayconiaAlluvia;
import com.gmail.pharaun.gregtania.botania.SubTileLayeredOrechid;
import com.gmail.pharaun.gregtania.botania.Util;
import com.gmail.pharaun.gregtania.botania.tiers.OrechidEndiumI;
import com.gmail.pharaun.gregtania.botania.tiers.OrechidEndiumII;
import com.gmail.pharaun.gregtania.botania.tiers.OrechidEndiumIII;
import com.gmail.pharaun.gregtania.botania.tiers.OrechidEndiumIV;
import com.gmail.pharaun.gregtania.botania.tiers.OrechidIgnemI;
import com.gmail.pharaun.gregtania.botania.tiers.OrechidIgnemII;
import com.gmail.pharaun.gregtania.botania.tiers.OrechidIgnemIII;
import com.gmail.pharaun.gregtania.botania.tiers.StratodendronI;
import com.gmail.pharaun.gregtania.botania.tiers.StratodendronII;
import com.gmail.pharaun.gregtania.botania.tiers.StratodendronIII;
import com.gmail.pharaun.gregtania.botania.tiers.StratodendronIV;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import vazkii.botania.common.Botania;
import vazkii.botania.common.item.ModItems;

/**
 * Created by Rio on 7/12/2018.
 */
public class ModFlowers {
    public static final String SUBTILE_CLAYCONIA_ALLUVIA = "clayconiaAlluvia";
    public static final String SUBTILE_STRATODENDRON = "stratodendron";
    public static final String SUBTILE_EVOLVED_ORECHID = "evolvedOrechid";
    public static final String SUBTILE_EVOLVED_ORECHID_IGNEM = "evolvedOrechidIgnem";
    public static final String SUBTILE_EVOLVED_ORECHID_ENDIUM = "evolvedOrechidEndium";
    public static final String SUBTILE_BUMBLEBISCUS = "bumblebiscus";

    public static void init() {


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

        if (Botania.gardenOfGlassLoaded) {
            // Gravel Clayconia - because:
            // sand needs an alchemy catalyst, which needs gold, which needs a crucible
            // and Clayconia needs an earth rune, which needs iron, which needs a clay crucible

            Util.registerFlower(SUBTILE_CLAYCONIA_ALLUVIA, SubTileClayconiaAlluvia.class);
        }
    }
}
