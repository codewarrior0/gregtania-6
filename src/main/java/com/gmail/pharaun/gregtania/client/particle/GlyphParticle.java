package com.gmail.pharaun.gregtania.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayDeque;
import java.util.Queue;

public class GlyphParticle extends EntityFX {
    public static final ResourceLocation ATLAS = new ResourceLocation("gregtania:textures/gui/glyphs.png");
    public static final Queue<GlyphParticle> PARTICLE_QUEUE = new ArrayDeque<>();
    public float gX;
    public float gY;
    public float tickDelta, rotX, rotXZ, rotZ, rotYZ, rotXY;

    public GlyphParticle(World world, double x, double y, double z, float size, float red, float green, float blue, byte letter) {
        super(world, x, y, z, 0f, 0f, 0f);
        this.particleMaxAge = 40;
        this.particleScale = size;
        this.particleRed = red;
        this.particleGreen = green;
        this.particleBlue = blue;
        this.gX = (letter % 8) / 8f;
        if (letter < 8) this.gY = 0f;
        else if (letter < 16) this.gY = 0.125f;
        else if (letter < 24) this.gY = 0.25f;
        else this.gY = 0.375f;

    }

    @Override
    public void renderParticle(Tessellator tessellator, float tickDelta, float rotX, float rotXZ, float rotZ, float rotYZ, float rotXY) {
        this.tickDelta = tickDelta;
        this.rotX = rotX;
        this.rotXZ = rotXZ;
        this.rotZ = rotZ;
        this.rotYZ = rotYZ;
        this.rotXY = rotXY;
        PARTICLE_QUEUE.add(this);
    }

    public static void renderAll(Tessellator tessellator) {
        Minecraft.getMinecraft().renderEngine.bindTexture(ATLAS);
        tessellator.setTextureUV(0f, 0f);
        tessellator.startDrawingQuads();
        for (GlyphParticle fx : PARTICLE_QUEUE) {
            fx.render(tessellator);
        }
        tessellator.draw();
        PARTICLE_QUEUE.clear();
    }

    public void render(Tessellator tessellator) {
        float scale = this.particleScale;
        float uX = gX + 0.125f, uY = gY + 0.125f;

        float lerpX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) this.tickDelta - interpPosX);
        float lerpY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) this.tickDelta - interpPosY);
        float lerpZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) this.tickDelta - interpPosZ);
        tessellator.setBrightness(240);
        tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, 1.0F);
        tessellator.addVertexWithUV(lerpX - this.rotX * scale - this.rotYZ * scale, lerpY - this.rotXZ * scale, lerpZ - this.rotZ * scale - this.rotXY * scale, uX, uY);
        tessellator.addVertexWithUV(lerpX - this.rotX * scale + this.rotYZ * scale, lerpY + this.rotXZ * scale, lerpZ - this.rotZ * scale + this.rotXY * scale, uX, gY);
        tessellator.addVertexWithUV(lerpX + this.rotX * scale + this.rotYZ * scale, lerpY + this.rotXZ * scale, lerpZ + this.rotZ * scale + this.rotXY * scale, gX, gY);
        tessellator.addVertexWithUV(lerpX + this.rotX * scale - this.rotYZ * scale, lerpY - this.rotXZ * scale, lerpZ + this.rotZ * scale - this.rotXY * scale, gX, uY);
    }
}
