package com.gmail.pharaun.gregtania.proxies;

import com.gmail.pharaun.gregtania.client.particle.GlyphParticle;
import com.gmail.pharaun.gregtania.client.particle.GregtaniaClientEventListeners;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        MinecraftForge.EVENT_BUS.register(new GregtaniaClientEventListeners());
    }

    public static void glyphParticle(World world, double x, double y, double z, float size, float red, float green, float blue, byte letter) {
        GlyphParticle glyph = new GlyphParticle(world, x, y, z, size, red, green, blue, letter);
        Minecraft.getMinecraft().effectRenderer.addEffect(glyph);
    }
}
