package com.gmail.pharaun.gregtania.proxies;

import com.gmail.pharaun.gregtania.botania.GTItemLens;
import com.gmail.pharaun.gregtania.botania.Util;
import com.gmail.pharaun.gregtania.lexicon.GTLexiconData;
import com.gmail.pharaun.gregtania.misc.BotaniaHelper;
import com.gmail.pharaun.gregtania.misc.Config;
import com.gmail.pharaun.gregtania.misc.LogHelper;
import com.gmail.pharaun.gregtania.misc.ModCraftingRecipes;
import com.gmail.pharaun.gregtania.misc.ModFlowers;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import gregapi.data.CS;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import team.chisel.carving.Carving;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeManaInfusion;
import vazkii.botania.common.Botania;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.ModFluffBlocks;
import vazkii.botania.common.crafting.ModManaAlchemyRecipes;
import vazkii.botania.common.item.ModItems;

import java.util.ArrayList;
import java.util.List;

public class CommonProxy {

    public static GTItemLens iridiumBoreLens;

    public void preInit(FMLPreInitializationEvent event) {
        CS.GT.mAfterPostInit.add(this::afterGregPostInit);
        iridiumBoreLens = new GTItemLens();
    }

    public void init(FMLInitializationEvent event) {
        // Disable the two vanilla botania orechids
        if (Config.disableVanillaOrechid) {
            Util.disableBotaniaFunctionalFlower("orechid");
            Util.disableBotaniaFunctionalFlower("orechidIgnem");
        }

        ModFlowers.init();
        ModCraftingRecipes.init();
        GTLexiconData.init();

        if (Botania.gardenOfGlassLoaded) {
            // This looks like it allows Botania pebbles to be used as GT pebbles
            // But it actually makes GT pebbles drop instead of Botania pebbles from right-clicking dirt

            OreDictionary.registerOre("rockGtStone", new ItemStack(ModItems.manaResource, 1, 21));
            OreDictionary.registerOre("rockGtAnyStone", new ItemStack(ModItems.manaResource, 1, 21));

        }

        // Replace mana catalyst recipes for 1.8 stones with greg stones (Granite, Diorite, Andesite, Basalt only)
        List<RecipeManaInfusion> newRecipes = new ArrayList<>();
        for (RecipeManaInfusion recipe : ModManaAlchemyRecipes.stoneRecipes) {
            Object input = recipe.getInput();
            if ((input instanceof ItemStack)) {
                input = convertStoneToGreg((ItemStack) input);
            } else if (input.equals("stone")) {
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

        for (int i = 0; i < 8; i++) {
            ItemStack output = i < 7 ? new ItemStack(CS.BlocksGT.Sapling, 1, i + 1) : new ItemStack(Blocks.sapling, 1, 0);
            ItemStack input = i > 0 ? new ItemStack(CS.BlocksGT.Sapling, 1, i) : new ItemStack(Blocks.sapling, 1, 5);
            RecipeManaInfusion newRecipe = new RecipeManaInfusion(
                    output, input, saplingMana
            );
            newRecipe.setAlchemy(true);
            ModManaAlchemyRecipes.saplingRecipes.add(newRecipe);
            BotaniaAPI.manaInfusionRecipes.add(newRecipe);
        }

        // Chisel thinks all cobblestone is equal, but it is important that all GregTech cobblestone be distinct.
        // Disable oredict equivalence for chiseling cobblestone.
        if (Loader.isModLoaded("chisel")) {
            Carving c = (Carving) Carving.chisel;
            c.getGroup("cobblestone").setOreName(null);
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

    public void afterGregPostInit() {
        GTLexiconData.postInit();
    }
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
