package com.gmail.pharaun.gregtania.botania;

import com.gmail.pharaun.gregtania.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.lexicon.LexiconPage;
import vazkii.botania.api.recipe.RecipePetals;
import vazkii.botania.api.recipe.RecipeRuneAltar;
import vazkii.botania.api.subtile.SubTileEntity;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;
import vazkii.botania.common.lexicon.page.PagePetalRecipe;
import vazkii.botania.common.lexicon.page.PageRuneRecipe;
import vazkii.botania.common.lexicon.page.PageText;

import java.util.Iterator;

// Thanks Magical Beees and ExCompressium for various hints on how to disable a flower/add a new flower
public class Util {
    public static class BlockType {
        public Block block;
        public int meta;
        public BlockType(Block _block, int _meta) {
            block = _block;
            meta = _meta;
        }

        @Override
        public int hashCode() {
            return block.hashCode() ^ (meta << 12);
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof BlockType)) return false;
            BlockType rhs = (BlockType) obj;
            return block.equals(rhs.block) && meta == rhs.meta;
        }
    }

    public static void registerFlower(String name, Class<? extends SubTileEntity> klass) {
        BotaniaAPI.registerSubTile(name, klass);
        BotaniaAPI.registerSubTileSignature(klass, new CustomSubTileSignature(name));
        BotaniaAPI.addSubTileToCreativeMenu(name);
    }

    /*
     * TODO: doesn't remove the flower from nei or creative panel, still need to mark em as disabled with minetweaker
     */
    public static void disableBotaniaFunctionalFlower(String name) {
        Iterator<LexiconEntry> it = BotaniaAPI.getAllEntries().iterator();
        while(it.hasNext()) {
            if(it.next().getUnlocalizedName().equals("botania.entry." + name)) {
                it.remove();
                break;
            }
        }
        it = BotaniaAPI.categoryFunctionalFlowers.entries.iterator();
        while(it.hasNext()) {
            if(it.next().getUnlocalizedName().equals("botania.entry." + name)) {
                it.remove();
                break;
            }
        }
        Iterator<RecipePetals> it2 = BotaniaAPI.petalRecipes.iterator();
        ItemStack itemStack = ItemBlockSpecialFlower.ofType(name);
        while(it2.hasNext()) {
            ItemStack output = it2.next().getOutput();
            if(ItemStack.areItemStacksEqual(itemStack, output)) {
                it2.remove();
                break;
            }
        }
    }
}
