package com.gmail.pharaun.gregtania.lexicon;

import gregapi.data.MT;
import gregapi.data.OP;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.lexicon.LexiconPage;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.lexicon.page.PageCraftingRecipe;
import vazkii.botania.common.lexicon.page.PagePetalRecipe;
import vazkii.botania.common.lexicon.page.PageRuneRecipe;
import vazkii.botania.common.lexicon.page.PageText;

import java.util.ArrayList;
import java.util.List;

import static com.gmail.pharaun.gregtania.misc.ModFlowers.*;
import static com.gmail.pharaun.gregtania.misc.ModCraftingRecipes.*;

/**
 * Created by Rio on 7/12/2018.
 */
public class GTLexiconData {

    public static GTLexiconCategory gtLexiconCategory;
    public static LexiconEntry entryBumbleiscus;
    public static LexiconEntry entryEvolvedOrechid;
    public static LexiconEntry entryEvolvedOrechidIgnem;
    public static LexiconEntry entryEvolvedOrechidEndium;
    public static LexiconEntry entryStratodendron;
    private static GTLexiconEntry entryIridiumBore;
    private static GTLexiconEntry entryGarden;

    public static void init() {
        BotaniaAPI.addCategory(gtLexiconCategory = new GTLexiconCategory("gregtania.category.gregtania"));

        entryGarden = new GTLexiconEntry("gregtania", gtLexiconCategory);
        entryGarden.setLexiconPages(
                new PageText("0"),
                new PageText("1"),
                new PageText("2"),
                new PageText("3"),
                new PageText("4"),
                new PageText("5"),
                new PageText("6")
        );
        entryGarden.setPriority();
        entryGarden.setIcon(OP.rockGt.mat(MT.Stone, 1));

        entryBumbleiscus = new GTLexiconEntry(SUBTILE_BUMBLEBISCUS, gtLexiconCategory);

        entryBumbleiscus.setLexiconPages(
                new PageText("0"),
                new PagePetalRecipe<>("1", recipeBumbleiscus)
        );

        entryEvolvedOrechid = new GTLexiconEntry(SUBTILE_EVOLVED_ORECHID, gtLexiconCategory);


        entryEvolvedOrechidIgnem = new GTLexiconEntry(SUBTILE_EVOLVED_ORECHID_IGNEM, gtLexiconCategory);

        entryEvolvedOrechidIgnem.setLexiconPages(
                new PageText("0"),
                new PageRuneRecipe("1", recipeOrechidIgnemI),
                new PageRuneRecipe("2", recipeOrechidIgnemII),
                new PageRuneRecipe("3", recipeOrechidIgnemIII)
        );

        entryEvolvedOrechidEndium = new GTLexiconEntry(SUBTILE_EVOLVED_ORECHID_ENDIUM, gtLexiconCategory);

        entryEvolvedOrechidEndium.setLexiconPages(
                new PageText("0"),
                new PageRuneRecipe("1", recipeOrechidEndiumI),
                new PageRuneRecipe("2", recipeOrechidEndiumII),
                new PageRuneRecipe("3", recipeOrechidEndiumIII),
                new PageRuneRecipe("4", recipeOrechidEndiumIV)
        );

        entryStratodendron = new GTLexiconEntry(SUBTILE_STRATODENDRON, gtLexiconCategory);

        entryIridiumBore = new GTLexiconEntry("lensIridiumBore", gtLexiconCategory);

        entryIridiumBore.setLexiconPages(
                new PageText("0"),
                new PageCraftingRecipe("1", recipeIridiumBoreLens)
        );
    }

    public static void postInit() {

        List<LexiconPage> pages = new ArrayList<>();

        pages.add(new PageText("0"));
        pages.addAll(PageStoneTables.createPages(0, SUBTILE_STRATODENDRON + "I"));
        pages.add(new PagePetalRecipe<>("1", recipeStratodendronI));
        pages.addAll(PageStoneTables.createPages(1, SUBTILE_STRATODENDRON + "II"));
        pages.add(new PageRuneRecipe("2", recipeStratodendronII));
        pages.addAll(PageStoneTables.createPages(2, SUBTILE_STRATODENDRON + "III"));
        pages.add(new PageRuneRecipe("3", recipeStratodendronIII));
        pages.addAll(PageStoneTables.createPages(3, SUBTILE_STRATODENDRON + "IV"));
        pages.add(new PageRuneRecipe("4", recipeStratodendronIV));

        entryStratodendron.setLexiconPages(pages.toArray(new LexiconPage[]{}));


        pages = new ArrayList<>();

        pages.add(new PageText("0"));
        pages.addAll(PageOreTables.createPages(SUBTILE_EVOLVED_ORECHID));
        pages.add(new PagePetalRecipe<>("1", recipeOrechidEvolved));

        entryEvolvedOrechid.setLexiconPages(pages.toArray(new LexiconPage[]{}));
    }
}
