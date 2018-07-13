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
public class PageOreTables extends LexiconPage {

    static int count = 0;
    private int startLine;
    private String flowerName;

    public static List<PageOreTables> createPages(String flowerName) {
        List<PageOreTables> pages = new ArrayList<>();

        int stoneCount = BotaniaHelper.wgLayerOres.entrySet().stream().mapToInt(e -> e.getValue().size()).sum();

        int i = 14;
        pages.add(new PageOreTables(flowerName, 0));
        while (i < stoneCount) {
            pages.add(new PageOreTables(flowerName, i));
            i += 16;
        }
        return pages;
    }

    public PageOreTables(String flowerName, int startLine) {
        super("gregtania:oreTables" + count);
        count++;
        this.startLine = startLine;
        this.flowerName = flowerName;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderScreen(IGuiLexiconEntry gui, int mx, int my) {
        int x = gui.getLeft() + 16;
        int y = gui.getTop() + 2;

        List<String> lines = new ArrayList<>();
        int limit;

        if (startLine == 0) {
            lines.add(StatCollector.translateToLocal("tile.botania:flower.gregtania." + flowerName + ".name") + " will generate:");
            lines.add("");
            limit = 14;
        } else {
            limit = 16;
        }

        List<String> stoneNames = BotaniaHelper.wgLayerOres.entrySet().stream()
                .flatMap(e -> {
                    String stoneName = new ItemStack(e.getKey().block, 1, e.getKey().meta).getDisplayName();
                    return e.getValue().stream().map(item -> String.format("%s in %s", item.m.mNameLocal, stoneName));
                })
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
