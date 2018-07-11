package com.gmail.pharaun.gregtania.proxies;

import com.gmail.pharaun.gregtania.botania.SubTileClayconiaAlluvia;
import com.gmail.pharaun.gregtania.botania.SubTileLayeredOrechid;
import com.gmail.pharaun.gregtania.botania.Util;
import com.gmail.pharaun.gregtania.botania.tiers.*;
import com.gmail.pharaun.gregtania.misc.BotaniaHelper;
import com.gmail.pharaun.gregtania.misc.Config;
import com.gmail.pharaun.gregtania.misc.LogHelper;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.LanguageRegistry;
import gregapi.data.CS;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.StringTranslate;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeManaInfusion;
import vazkii.botania.common.Botania;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.ModFluffBlocks;
import vazkii.botania.common.crafting.ModManaAlchemyRecipes;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;
import vazkii.botania.common.lexicon.page.PageText;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommonProxy {
    public static final String SUBTILE_CLAYCONIA_ALLUVIA = "clayconiaAlluvia";
    public static final String SUBTILE_STRATODENDRON = "stratodendron";
    public static final String SUBTILE_EVOLVED_ORECHID = "evolvedOrechid";
    public static final String SUBTILE_EVOLVED_ORECHID_IGNEM = "evolvedOrechidIgnem";
    public static final String SUBTILE_EVOLVED_ORECHID_ENDIUM = "evolvedOrechidEndium";

    private final int costTier1 = 5200;
    private final int costTier2 = 8000;
    private final int costTier3 = 12000;
    private final int costTier4 = 16000;

    public void preInit(FMLPreInitializationEvent event) {
//        CS.GT.mAfterPostInit.add(this::afterGregPostInit);
    }

    public void init(FMLInitializationEvent event) {

        // Disable the two vanilla botania orechids
        if (Config.disableVanillaOrechid) {
            Util.disableBotaniaFunctionalFlower("orechid");
            Util.disableBotaniaFunctionalFlower("orechidIgnem");
        }

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

        // Overworld Orechid recipes
        SubTileLayeredOrechid.lexiconEntry =
                Util.registerFunctionalPetalRecipe(SUBTILE_EVOLVED_ORECHID,
                        "petalGray", "petalGray", "petalYellow", "petalYellow", "petalGreen", "petalGreen", "petalRed", "petalRed");

        // Nether Functional recipes
        ItemStack flower;
        flower = ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID);
        Util.registerFunctionalRunicRecipe(SUBTILE_EVOLVED_ORECHID_IGNEM + "I", costTier1, flower, flower, "ingotManasteel", "ingotManasteel", "runeFireB", "runeFireB");

        flower = ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_IGNEM + "I");
        Util.registerFunctionalRunicRecipeElven(SUBTILE_EVOLVED_ORECHID_IGNEM + "II", costTier2, flower, flower, "ingotElvenElementium", "ingotElvenElementium", "runeFireB", "runeFireB");

        flower = ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_IGNEM + "II");
        Util.registerFunctionalRunicRecipeElven(SUBTILE_EVOLVED_ORECHID_IGNEM + "III", costTier3, flower, flower, "ingotTerrasteel", "ingotTerrasteel", "runeGreedB", "runeGreedB");

        // End Functional recipes
        flower = ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID);
        ItemStack flower2 = ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_IGNEM + "III");
        Util.registerFunctionalRunicRecipeElven(SUBTILE_EVOLVED_ORECHID_ENDIUM + "I", costTier3, flower, flower2, "ingotTerrasteel", "ingotTerrasteel", "runeGreedB", "runeSlothB");

        flower = ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_ENDIUM + "I");
        Util.registerFunctionalRunicRecipeElven(SUBTILE_EVOLVED_ORECHID_ENDIUM + "II", costTier4, flower, flower, "gaiaIngot", "gaiaIngot", "runePrideB", "runePrideB");

        flower = ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_ENDIUM + "II");
        ItemStack star = new ItemStack(Items.nether_star, 1);
        Util.registerFunctionalRunicRecipeElven(SUBTILE_EVOLVED_ORECHID_ENDIUM + "III", costTier4, flower, flower, star, "runePrideB", "runePrideB");

        flower = ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_ENDIUM + "III");
        Util.registerFunctionalRunicRecipeElven(SUBTILE_EVOLVED_ORECHID_ENDIUM + "IV", costTier4, flower, flower, flower, flower);

        // Register the Layered Stone creators


        StratodendronI.lexiconEntry = Util.registerFunctionalPetalRecipe(SUBTILE_STRATODENDRON + "I", "petalGray", "petalYellow", "petalGreen", "petalRed");

        flower = ItemBlockSpecialFlower.ofType(SUBTILE_STRATODENDRON + "I");
        ItemStack livingRockBlock = new ItemStack(ModBlocks.livingrock, 1, 1);
        ItemStack livingWoodBlock = new ItemStack(ModBlocks.livingwood, 1, 3);
        StratodendronII.lexiconEntry = Util.registerFunctionalRunicRecipe(SUBTILE_STRATODENDRON + "II", costTier1,
                flower, flower, livingRockBlock, livingRockBlock, livingWoodBlock, livingWoodBlock);

        flower = ItemBlockSpecialFlower.ofType(SUBTILE_STRATODENDRON + "II");
        StratodendronIII.lexiconEntry = Util.registerFunctionalRunicRecipe(SUBTILE_STRATODENDRON + "III", costTier1,
                flower, flower, "ingotManasteel", "ingotManasteel", "runeEarthB", "runeEarthB");

        flower = ItemBlockSpecialFlower.ofType(SUBTILE_STRATODENDRON + "III");
        StratodendronIV.lexiconEntry = Util.registerFunctionalRunicRecipeElven(SUBTILE_STRATODENDRON + "IV", costTier2,
                flower, flower, "ingotElvenElementium", "ingotElvenElementium", "runeSlothB", "runeSlothB");

//        StratodendronI.lexiconEntry.pages.add(1, new PageText("gregtania.dynamic:stratodendronI.page"));
//        StratodendronII.lexiconEntry.pages.add(1, new PageText("gregtania.dynamic:stratodendronII.page"));
//        StratodendronIII.lexiconEntry.pages.add(1, new PageText("gregtania.dynamic:stratodendronIII.page"));
//        StratodendronIV.lexiconEntry.pages.add(1, new PageText("gregtania.dynamic:stratodendronIV.page"));

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

        }

        // Wrought Iron -> Manasteel
        BotaniaAPI.registerManaInfusionRecipe(new ItemStack(ModItems.manaResource, 1, 0), "ingotAnyIron", 3000);

        // Steel -> Manasteel (with discount)
        BotaniaAPI.registerManaInfusionRecipe(new ItemStack(ModItems.manaResource, 1, 0), "ingotAnyIronSteel", 1500);

        // Replace mana catalyst recipes for 1.8 stones with greg stones (Granite, Diorite, Andesite, Basalt only)
        List<RecipeManaInfusion> newRecipes = new ArrayList<>();
        for (RecipeManaInfusion recipe : ModManaAlchemyRecipes.stoneRecipes) {
            Object input = recipe.getInput();
            if ((input instanceof ItemStack)) {
                input = convertStoneToGreg((ItemStack)input);
            } else if (input == "stone") {
                input = new ItemStack(Blocks.stone);
            }
            ItemStack output = convertStoneToGreg(recipe.getOutput());

            RecipeManaInfusion newRecipe = new RecipeManaInfusion(
                    output, input, recipe.getManaToConsume()
            );
            newRecipe.setAlchemy(true);
            newRecipes.add(newRecipe);
        }
        BotaniaAPI.manaInfusionRecipes.removeAll(ModManaAlchemyRecipes.stoneRecipes);
        ModManaAlchemyRecipes.stoneRecipes.clear();
        ModManaAlchemyRecipes.stoneRecipes.addAll(newRecipes);
        BotaniaAPI.manaInfusionRecipes.addAll(newRecipes);

        int saplingMana = ModManaAlchemyRecipes.saplingRecipes.get(5).getManaToConsume();
        BotaniaAPI.manaInfusionRecipes.remove(ModManaAlchemyRecipes.saplingRecipes.get(5));
        ModManaAlchemyRecipes.saplingRecipes.remove(5);

        for (int i=0; i<8; i++) {
            ItemStack output = i<7 ? new ItemStack(CS.BlocksGT.Sapling, 1, i+1) : new ItemStack(Blocks.sapling, 1, 0);
            ItemStack input = i>0 ? new ItemStack(CS.BlocksGT.Sapling, 1, i) : new ItemStack(Blocks.sapling, 1, 5);
            RecipeManaInfusion newRecipe = new RecipeManaInfusion(
                    output, input, saplingMana
            );
            newRecipe.setAlchemy(true);
            ModManaAlchemyRecipes.saplingRecipes.add(newRecipe);
            BotaniaAPI.manaInfusionRecipes.add(newRecipe);
        }
    }

    private static ItemStack convertStoneToGreg(ItemStack stack) {
        if (stack.getItem() instanceof ItemBlock && ((ItemBlock)stack.getItem()).field_150939_a == ModFluffBlocks.stone) {
            int meta = stack.getItemDamage();
            Block block;
            switch(meta) {
                case 0:
                    block = CS.BlocksGT.Andesite; break;
                case 1:
                    block = CS.BlocksGT.Basalt; break;
                case 2:
                    block = CS.BlocksGT.Diorite; break;
                case 3:
                    block = CS.BlocksGT.Granite; break;
                default:
                    return stack;
            }
            return new ItemStack(block);
        }
        return stack;
    }

    public void postInit(FMLPostInitializationEvent event) {
        BotaniaHelper.initOreTables();

        if (Botania.gardenOfGlassLoaded) {
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

            for (IRecipe recipe : (List<IRecipe>) CraftingManager.getInstance().getRecipeList()) {
                ItemStack output = recipe.getRecipeOutput();
                if (recipe instanceof ShapelessOreRecipe
                        && output.getItem() == Items.blaze_powder
                        && output.stackSize == 9) {
                    ShapelessOreRecipe sRecipe = (ShapelessOreRecipe) recipe;
                    if (sRecipe.getInput().size() == 1) {
                        if (sRecipe.getInput().get(0) == OreDictionary.getOres("blockBlaze")) {
                            output.stackSize = 4;
                            break;
                        }
                    }
                }
            }
        }


        // Log the available levels
        //LogHelper.info("Overworld Harvest Levels: " + BotaniaHelper.tieredOreWeightOverworld.keySet().toString());
        LogHelper.info("Nether Harvest Levels: " + BotaniaHelper.tieredOreWeightNether.keySet().toString());
        LogHelper.info("End Harvest Levels: " + BotaniaHelper.tieredOreWeightEnd.keySet().toString());
    }

//    public void afterGregPostInit() {
//        // Inject dynamic lexicon pages into language registry
//        HashMap<String, String> langMap = new HashMap<>();
//
//        String[] eyes = {"I", "II", "III", "IV"};
//        for(int i=0; i<4; i++) {
//            if (!BotaniaHelper.wgWeightsStones.containsKey(i)) continue;
//
//            List<BotaniaHelper.BlockRandomItem> stones = BotaniaHelper.wgWeightsStones.get(i);
//            String stoneNames = stones.stream()
//                    .map(b -> new ItemStack(b.b.getKey(), 1, b.b.getValue()).getDisplayName())
//                    .collect(Collectors.joining(""));
//            langMap.put("gregtania.dynamic:stratodendron" + eyes[i] + ".page",
//                    "The following stones are created by this tier of Stratodendron:<br><br>" + stoneNames + "\n");
//
//        }
//        LanguageRegistry.instance().injectLanguage("en_US", langMap);
//
//    }
}
