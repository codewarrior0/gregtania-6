package com.gmail.pharaun.gregtania.lexicon;

import com.gmail.pharaun.gregtania.misc.BotaniaHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import vazkii.botania.api.internal.IGuiLexiconEntry;
import vazkii.botania.api.lexicon.LexiconPage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Rio on 7/12/2018.
 */
public class PageStoneTables extends LexiconPage {

    static int count = 0;
    private int startLine, tier;
    private String flowerName;

    public static List<PageStoneTables> createPages(int tier, String flowerName) {
        List<PageStoneTables> pages = new ArrayList<>();
        if (!BotaniaHelper.wgWeightsStones.containsKey(tier)) return pages;

        int stoneCount = BotaniaHelper.wgWeightsStones.get(tier).size();

        int i = 14;
        pages.add(new PageStoneTables(tier, flowerName, 0));
        while (i < stoneCount) {
            pages.add(new PageStoneTables(tier, flowerName, i));
            i += 16;
        }
        return pages;
    }

    public PageStoneTables(int tier, String flowerName, int startLine) {
        super("gregtania:stoneTables" + count);
        count++;
        this.startLine = startLine;
        this.tier = tier;
        this.flowerName = flowerName;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderScreen(IGuiLexiconEntry gui, int mx, int my) {
        int x = gui.getLeft() + 16;
        int y = gui.getTop() + 2;

        List<String> lines = new ArrayList<>();
        int limit;

        if(startLine == 0) {
            lines.add(StatCollector.translateToLocal("tile.botania:flower.gregtania." + flowerName + ".name") + " will generate:");
            lines.add("");
            limit = 14;
        } else {
            limit = 16;
        }

        List<BotaniaHelper.BlockRandomItem> stones = BotaniaHelper.wgWeightsStones.get(tier);
        List<String> stoneNames = stones.stream()
                .map(b -> new ItemStack(b.b.block, 1, b.b.meta).getDisplayName())
                .skip(startLine)
                .limit(limit)
                .collect(Collectors.toList());

        lines.addAll(stoneNames);

        renderText(x, y, lines);
    }

    @SideOnly(Side.CLIENT)
    public static void renderText(int x, int y, List<String> entries) {
        x += 2;
        y += 10;

        FontRenderer font = Minecraft.getMinecraft().fontRenderer;
        boolean unicode = font.getUnicodeFlag();
        font.setUnicodeFlag(true);

        //font.drawString("This flower will generate:", x, y, 0);
        //y += 20;

        for(String word : entries) {
            font.drawString(word, x, y, 0);
            y += 10;
        }

        font.setUnicodeFlag(unicode);
    }

}
