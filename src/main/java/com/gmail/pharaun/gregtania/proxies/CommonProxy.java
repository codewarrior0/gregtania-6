package com.gmail.pharaun.gregtania.proxies;

import com.gmail.pharaun.gregtania.botania.SubTileClayconiaAlluvia;
import com.gmail.pharaun.gregtania.botania.Util;
import com.gmail.pharaun.gregtania.botania.tiers.*;
import com.gmail.pharaun.gregtania.misc.BotaniaHelper;
import com.gmail.pharaun.gregtania.misc.Config;
import com.gmail.pharaun.gregtania.misc.LogHelper;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.common.Botania;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;

import java.util.List;

public class CommonProxy {
    public static final String SUBTILE_CLAYCONIA_ALLUVIA = "clayconiaAlluvia";
    public static final String SUBTILE_EVOLVED_ORECHID = "evolvedOrechid";
    public static final String SUBTILE_EVOLVED_ORECHID_IGNEM = "evolvedOrechidIgnem";
    public static final String SUBTILE_EVOLVED_ORECHID_ENDIUM = "evolvedOrechidEndium";

    private final int costTier1 = 5200;
    private final int costTier2 = 8000;
    private final int costTier3 = 12000;
    private final int costTier4 = 16000;

    public void preInit(FMLPreInitializationEvent event) {}

    public void init(FMLInitializationEvent event) {

        // Disable the two vanilla botania orechids
        if(Config.disableVanillaOrechid) {
            Util.disableBotaniaFunctionalFlower("orechid");
            Util.disableBotaniaFunctionalFlower("orechidIgnem");
        }

        // Register The tiered orechid
        Util.registerFlower(SUBTILE_EVOLVED_ORECHID + "I", OrechidI.class);
        Util.registerFlower(SUBTILE_EVOLVED_ORECHID + "II", OrechidII.class);
        Util.registerFlower(SUBTILE_EVOLVED_ORECHID + "III", OrechidIII.class);
        Util.registerFlower(SUBTILE_EVOLVED_ORECHID + "IV", OrechidIV.class);

        Util.registerFlower(SUBTILE_EVOLVED_ORECHID_IGNEM + "I", OrechidIgnemI.class);
        Util.registerFlower(SUBTILE_EVOLVED_ORECHID_IGNEM + "II", OrechidIgnemII.class);
        Util.registerFlower(SUBTILE_EVOLVED_ORECHID_IGNEM + "III", OrechidIgnemIII.class);

        Util.registerFlower(SUBTILE_EVOLVED_ORECHID_ENDIUM + "I", OrechidEndiumI.class);
        Util.registerFlower(SUBTILE_EVOLVED_ORECHID_ENDIUM + "II", OrechidEndiumII.class);
        Util.registerFlower(SUBTILE_EVOLVED_ORECHID_ENDIUM + "III", OrechidEndiumIII.class);
        Util.registerFlower(SUBTILE_EVOLVED_ORECHID_ENDIUM + "IV", OrechidEndiumIV.class);

        // Overworld Orechid recipes
        Util.registerFunctionalPetalRecipe(SUBTILE_EVOLVED_ORECHID + "I", "petalGray", "petalGray", "petalYellow", "petalYellow", "petalGreen", "petalGreen", "petalRed", "petalRed");

        ItemStack flower = ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID + "I");
        ItemStack livingRockBlock = new ItemStack(ModBlocks.livingrock, 1, 1);
        ItemStack livingWoodBlock = new ItemStack(ModBlocks.livingwood, 1, 3);
        Util.registerFunctionalRunicRecipe(SUBTILE_EVOLVED_ORECHID + "II", costTier1, flower, flower, livingRockBlock, livingRockBlock, livingWoodBlock, livingWoodBlock);

        flower = ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID + "II");
        Util.registerFunctionalRunicRecipe(SUBTILE_EVOLVED_ORECHID + "III", costTier1, flower, flower, "ingotManasteel", "ingotManasteel", "runeEarthB", "runeEarthB");

        flower = ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID + "III");
        Util.registerFunctionalRunicRecipeElven(SUBTILE_EVOLVED_ORECHID + "IV", costTier2, flower, flower, "ingotElvenElementium", "ingotElvenElementium", "runeSlothB", "runeSlothB");

        // Nether Functional recipes
        flower = ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID + "II");
        Util.registerFunctionalRunicRecipe(SUBTILE_EVOLVED_ORECHID_IGNEM + "I", costTier1, flower, flower, "ingotManasteel", "ingotManasteel", "runeFireB", "runeFireB");

        flower = ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_IGNEM + "I");
        Util.registerFunctionalRunicRecipeElven(SUBTILE_EVOLVED_ORECHID_IGNEM + "II", costTier2, flower, flower, "ingotElvenElementium", "ingotElvenElementium", "runeFireB", "runeFireB");

        flower = ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_IGNEM + "II");
        Util.registerFunctionalRunicRecipeElven(SUBTILE_EVOLVED_ORECHID_IGNEM + "III", costTier3, flower, flower, "ingotTerrasteel", "ingotTerrasteel", "runeGreedB", "runeGreedB");

        // End Functional recipes
        flower = ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID + "IV");
        ItemStack flower2 = ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_IGNEM + "III");
        Util.registerFunctionalRunicRecipeElven(SUBTILE_EVOLVED_ORECHID_ENDIUM + "I", costTier3, flower, flower2, "ingotTerrasteel", "ingotTerrasteel", "runeGreedB", "runeSlothB");

        flower = ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_ENDIUM + "I");
        Util.registerFunctionalRunicRecipeElven(SUBTILE_EVOLVED_ORECHID_ENDIUM + "II", costTier4, flower, flower, "gaiaIngot", "gaiaIngot", "runePrideB", "runePrideB");

        flower = ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_ENDIUM + "II");
        ItemStack star = new ItemStack(Items.nether_star, 1);
        Util.registerFunctionalRunicRecipeElven(SUBTILE_EVOLVED_ORECHID_ENDIUM + "III", costTier4, flower, flower, star, "runePrideB", "runePrideB");

        flower = ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_ENDIUM + "III");
        Util.registerFunctionalRunicRecipeElven(SUBTILE_EVOLVED_ORECHID_ENDIUM + "IV", costTier4, flower, flower, flower, flower);

        if (Botania.gardenOfGlassLoaded) {
            // This looks like it allows Botania pebbles to be used as GT pebbles
            // But it actually makes GT pebbles drop instead of Botania pebbles from right-clicking dirt

            OreDictionary.registerOre("rockGtStone", new ItemStack(ModItems.manaResource, 1, 21));
            OreDictionary.registerOre("rockGtAnyStone", new ItemStack(ModItems.manaResource, 1, 21));

            // Gravel Clayconia - because:
            // sand needs an alchemy catalyst, which needs gold, which needs a crucible
            // and Clayconia needs an earth rune, which needs iron, which needs a clay crucible

            Util.registerFlower(SUBTILE_CLAYCONIA_ALLUVIA, SubTileClayconiaAlluvia.class);
            SubTileClayconiaAlluvia.lexiconEntry = Util.registerFunctionalPetalRecipe(SUBTILE_CLAYCONIA_ALLUVIA, "petalGray", "petalLightGray", "petalLightGray", "petalCyan");

            // Greg makes bone meal unobtainable before getting a Mortar, but no bone meal makes
            // the GoG early game painful. Let's get one bone meal per bone from Botania's mortar.

            CraftingManager.getInstance().getRecipeList().add(
                    new ShapelessOreRecipe(
                            new ItemStack(Items.dye, 1, 15),
                            new ItemStack(Items.bone, 1), "pestleAndMortar"
                    )
            );

            // Greg takes over the Blaze Lamp recipe, replacing it with Blaze Powder Block
            // Add a cheaper Blaze Lamp recipe since the Iron for Iron Bars is harder to get
            ItemStack blazeBlock = new ItemStack(ModBlocks.blazeBlock);

            CraftingManager.getInstance().addShapelessRecipe(
                    blazeBlock,
                    new ItemStack(Items.blaze_powder, 1),
                    new ItemStack(Items.blaze_powder, 1),
                    new ItemStack(Items.blaze_powder, 1),
                    new ItemStack(Items.blaze_powder, 1)
            );

            // Also change the reverse recipe.

            for (IRecipe recipe: (List<IRecipe>)CraftingManager.getInstance().getRecipeList()) {
                ItemStack output = recipe.getRecipeOutput();
                if (recipe instanceof ShapelessRecipes && output.getItem() == Items.blaze_powder && output.stackSize == 9) {
                    ShapelessRecipes sRecipe = (ShapelessRecipes) recipe;
                    if (sRecipe.recipeItems.size() == 1
                            && ((ItemStack) sRecipe.recipeItems.get(0)).isItemEqual(blazeBlock)) {
                        output.stackSize = 4;
                        break;
                    }
                }
            }
        }

        // Wrought Iron -> Manasteel
        BotaniaAPI.registerManaInfusionRecipe(new ItemStack(ModItems.manaResource, 1, 0), "ingotAnyIron", 3000);

        // Steel -> Manasteel (with discount)
        BotaniaAPI.registerManaInfusionRecipe(new ItemStack(ModItems.manaResource, 1, 0), "ingotAnyIronSteel", 1500);

    }

    public void postInit(FMLPostInitializationEvent event) {
        // Init the ore tables in postInit so we can use the worldgen data GT creates in init
        BotaniaHelper.initOreTables();

        // Log the available levels
        LogHelper.info("Overworld Harvest Levels: " + BotaniaHelper.tieredOreWeightOverworld.keySet().toString());
        LogHelper.info("Nether Harvest Levels: " + BotaniaHelper.tieredOreWeightNether.keySet().toString());
        LogHelper.info("End Harvest Levels: " + BotaniaHelper.tieredOreWeightEnd.keySet().toString());

    }
}
