package com.gmail.pharaun.gregtania.lexicon;

import com.gmail.pharaun.gregtania.misc.BotaniaHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregapi.code.ItemStackContainer;
import gregapi.worldgen.StoneLayer;
import javafx.util.Pair;
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
public class PageStackedOres extends LexiconPage {

    static int count = 0;
    private int startLine;
    private String flowerName;
    private static List<String> stacks = new ArrayList<>();

    public static List<PageStackedOres> createPages(String flowerName) {
        List<PageStackedOres> pages = new ArrayList<>();

        StoneLayer.MAP.forEach((above, belows) -> belows.forEach((below, stones) -> {
            stacks.add(above.mNameLocal + " over " + below.mNameLocal);
            stones.forEach(layer -> stacks.add("  " + layer.mMaterial.mNameLocal));
        }));
        int i = 0;
        while (i < stacks.size()) {
            pages.add(new PageStackedOres(flowerName, i));
            i += 16;
        }
        return pages;
    }

    public PageStackedOres(String flowerName, int startLine) {
        super("gregtania:stackedOres" + count);
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

        lines.add(StatCollector.translateToLocal("tile.botania:flower.gregtania." + flowerName + ".name") + " will generate:");
        lines.add("");

        StoneLayer.MAP.forEach((above, belows) -> belows.forEach((below, stones) -> {
            lines.add(above.mNameLocal + " over " + below.mNameLocal);
            stones.forEach(layer -> lines.add("  " + layer.mMaterial.mNameLocal));
        }));

        List<String> text = lines.stream()
                .skip(startLine)
                .limit(16)
                .collect(Collectors.toList());

        renderText(x, y, text);
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
