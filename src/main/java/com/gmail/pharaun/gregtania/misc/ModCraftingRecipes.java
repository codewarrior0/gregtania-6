package com.gmail.pharaun.gregtania.misc;

import com.gmail.pharaun.gregtania.botania.SubTileClayconiaAlluvia;
import com.gmail.pharaun.gregtania.botania.Util;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipePetals;
import vazkii.botania.api.recipe.RecipeRuneAltar;
import vazkii.botania.common.Botania;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;

import static com.gmail.pharaun.gregtania.misc.ModFlowers.*;

/**
 * Created by Rio on 7/12/2018.
 */
public class ModCraftingRecipes {
    public static RecipePetals recipeOrechidEvolved;

    public static RecipeRuneAltar recipeOrechidIgnemI;
    public static RecipeRuneAltar recipeOrechidIgnemII;
    public static RecipeRuneAltar recipeOrechidIgnemIII;
    ///public static RecipeRuneAltar recipeOrechidIgnemIV;

    public static RecipeRuneAltar recipeOrechidEndiumI;
    public static RecipeRuneAltar recipeOrechidEndiumII;
    public static RecipeRuneAltar recipeOrechidEndiumIII;
    public static RecipeRuneAltar recipeOrechidEndiumIV;

    public static RecipePetals recipeStratodendronI;
    public static RecipeRuneAltar recipeStratodendronII;
    public static RecipeRuneAltar recipeStratodendronIII;
    public static RecipeRuneAltar recipeStratodendronIV;

    public static RecipePetals recipeBumbleiscus;
    public static RecipePetals recipeClayconiaAlluvia;

    private static final int costTier1 = 5200;
    private static final int costTier2 = 8000;
    private static final int costTier3 = 12000;
    private static final int costTier4 = 16000;

    public static void init() {
        recipeOrechidEvolved = BotaniaAPI.registerPetalRecipe(
                ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID),
                "petalGray", "petalGray", "petalYellow", "petalYellow", "petalGreen", "petalGreen", "petalRed", "petalRed"
        );

        recipeOrechidIgnemI = BotaniaAPI.registerRuneAltarRecipe(
                ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_IGNEM + "I"), costTier1,
                ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID),
                ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID),
                ItemBlockSpecialFlower.ofType(SUBTILE_STRATODENDRON + "IV"),
                ItemBlockSpecialFlower.ofType(SUBTILE_STRATODENDRON + "IV"),
                "ingotManasteel", "ingotManasteel", "runeFireB", "runeFireB"
        );

        recipeOrechidIgnemII = BotaniaAPI.registerRuneAltarRecipe(
                ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_IGNEM + "II"), costTier2,
                ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_IGNEM + "I"),
                ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_IGNEM + "I"),
                "ingotElvenElementium", "ingotElvenElementium", "runeFireB", "runeFireB"
        );

        recipeOrechidIgnemIII = BotaniaAPI.registerRuneAltarRecipe(
                ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_IGNEM + "III"), costTier3,
                ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_IGNEM + "II"),
                ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_IGNEM + "II"),
                "ingotTerrasteel", "ingotTerrasteel", "runeGreedB", "runeGreedB"
        );

        recipeOrechidEndiumI = BotaniaAPI.registerRuneAltarRecipe(
                ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_ENDIUM + "I"), costTier3,
                ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID),
                ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_IGNEM + "III"),
                ItemBlockSpecialFlower.ofType(SUBTILE_STRATODENDRON + "IV"),
                ItemBlockSpecialFlower.ofType(SUBTILE_STRATODENDRON + "IV"),
                "ingotTerrasteel", "ingotTerrasteel", "runeGreedB", "runeSlothB"
        );

        recipeOrechidEndiumII = BotaniaAPI.registerRuneAltarRecipe(
                ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_ENDIUM + "II"), costTier3,
                ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_ENDIUM + "I"),
                ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_ENDIUM + "I"),
                "gaiaIngot", "gaiaIngot", "runePrideB", "runePrideB"
        );

        recipeOrechidEndiumIII = BotaniaAPI.registerRuneAltarRecipe(
                ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_ENDIUM + "III"), costTier4,
                ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_ENDIUM + "II"),
                ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_ENDIUM + "II"),
                new ItemStack(Items.nether_star, 1),
                "runePrideB", "runePrideB"
        );

        recipeOrechidEndiumIV = BotaniaAPI.registerRuneAltarRecipe(
                ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_ENDIUM + "IV"), costTier4,
                ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_ENDIUM + "III"),
                ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_ENDIUM + "III"),
                ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_ENDIUM + "III"),
                ItemBlockSpecialFlower.ofType(SUBTILE_EVOLVED_ORECHID_ENDIUM + "III")
        );

        recipeStratodendronI = BotaniaAPI.registerPetalRecipe(
                ItemBlockSpecialFlower.ofType(SUBTILE_STRATODENDRON + "I"),
                "petalGray", "petalYellow", "petalGreen", "petalRed"
        );

        recipeStratodendronII = BotaniaAPI.registerRuneAltarRecipe(
                ItemBlockSpecialFlower.ofType(SUBTILE_STRATODENDRON + "II"), costTier1,
                ItemBlockSpecialFlower.ofType(SUBTILE_STRATODENDRON + "I"),
                ItemBlockSpecialFlower.ofType(SUBTILE_STRATODENDRON + "I"),
                new ItemStack(ModBlocks.livingrock, 1, 1),
                new ItemStack(ModBlocks.livingrock, 1, 1),
                new ItemStack(ModBlocks.livingwood, 1, 3),
                new ItemStack(ModBlocks.livingwood, 1, 3)
        );

        recipeStratodendronIII = BotaniaAPI.registerRuneAltarRecipe(
                ItemBlockSpecialFlower.ofType(SUBTILE_STRATODENDRON + "III"), costTier1,
                ItemBlockSpecialFlower.ofType(SUBTILE_STRATODENDRON + "II"),
                ItemBlockSpecialFlower.ofType(SUBTILE_STRATODENDRON + "II"),
                "ingotManasteel", "ingotManasteel", "runeEarthB", "runeEarthB"
        );

        recipeStratodendronIV = BotaniaAPI.registerRuneAltarRecipe(
                ItemBlockSpecialFlower.ofType(SUBTILE_STRATODENDRON + "IV"), costTier2,
                ItemBlockSpecialFlower.ofType(SUBTILE_STRATODENDRON + "III"),
                ItemBlockSpecialFlower.ofType(SUBTILE_STRATODENDRON + "III"),
                "ingotElvenElementium", "ingotElvenElementium", "runeSlothB", "runeSlothB"
        );

        recipeBumbleiscus = BotaniaAPI.registerPetalRecipe(
                ItemBlockSpecialFlower.ofType(SUBTILE_BUMBLEBISCUS),
                "petalBlack", "petalYellow", "petalBlack", "petalYellow",
                "petalBlack", "petalYellow", "petalBlack", "petalYellow"
        );


        if (Botania.gardenOfGlassLoaded) {
            recipeClayconiaAlluvia = BotaniaAPI.registerPetalRecipe(
                    ItemBlockSpecialFlower.ofType(SUBTILE_CLAYCONIA_ALLUVIA),
                    "petalGray", "petalLightGray", "petalLightGray", "petalCyan"
            );
        }
    }
}
