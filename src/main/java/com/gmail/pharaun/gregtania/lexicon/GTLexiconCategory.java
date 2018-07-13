package com.gmail.pharaun.gregtania.lexicon;

import net.minecraft.util.ResourceLocation;
import vazkii.botania.api.lexicon.LexiconCategory;

/**
 * Created by Rio on 7/12/2018.
 */

public class GTLexiconCategory extends LexiconCategory {
    public GTLexiconCategory(String unlocalizedName) {
        super(unlocalizedName);
        this.setIcon(new ResourceLocation("gregtania:textures/gui/categories/gregtania.png"));
        this.setPriority(1);
    }
}
