package com.gmail.pharaun.gregtania.client.particle;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class GregtaniaClientEventListeners {
    @SubscribeEvent
    public void busterBrown(RenderWorldLastEvent event) {
        GlyphParticle.renderAll(Tessellator.instance);
    }
}
