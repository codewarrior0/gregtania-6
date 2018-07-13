package com.gmail.pharaun.gregtania.lexicon;

/**
 * Created by Rio on 7/12/2018.
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.ITwoNamedPage;
import vazkii.botania.api.lexicon.LexiconCategory;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.lexicon.LexiconPage;
import vazkii.botania.common.lexicon.WLexiconEntry;

public class GTLexiconEntry extends LexiconEntry {
    public GTLexiconEntry(String unlocalizedName, LexiconCategory category) {
        super(unlocalizedName, category);
        BotaniaAPI.addEntry(this, category);
    }

    public LexiconEntry setLexiconPages(LexiconPage... pages) {
        for(LexiconPage page: pages) {
            page.unlocalizedName = "gregtania.page." + this.getLazyUnlocalizedName() + page.unlocalizedName;
            if(page instanceof ITwoNamedPage) {
                ITwoNamedPage dou = (ITwoNamedPage)page;
                dou.setSecondUnlocalizedName("gregtania.page." + this.getLazyUnlocalizedName() + dou.getSecondUnlocalizedName());
            }
        }

        return super.setLexiconPages(pages);
    }

    public String getUnlocalizedName() {
        return "gregtania.entry." + super.getUnlocalizedName();
    }

    public String getTagline() {
        return "gregtania.tagline." + super.getUnlocalizedName();
    }

    public String getLazyUnlocalizedName() {
        return super.getUnlocalizedName();
    }

    public int compareTo(LexiconEntry o) {
        return o instanceof WLexiconEntry ?1:super.compareTo(o);
    }
}
