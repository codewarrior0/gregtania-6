/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 *
 * File Created @ [Jan 31, 2014, 3:02:58 PM (GMT)]
 * Modified by Codewarrior0
 */
package com.gmail.pharaun.gregtania.botania;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import vazkii.botania.api.internal.IManaBurst;
import vazkii.botania.api.mana.BurstProperties;
import vazkii.botania.api.mana.ILensControl;
import vazkii.botania.api.mana.IManaSpreader;
import vazkii.botania.api.mana.ITinyPlanetExcempt;

public class GTItemLens extends Item implements ILensControl, ITinyPlanetExcempt {

    private final LensIridiumBore BORE_LENS;
    static IIcon iconIridiumBoreGlass;

    public GTItemLens() {
        super();
        setUnlocalizedName("lensIridiumBore");
        GameRegistry.registerItem(this, "lensIridiumBore");
        setMaxStackSize(1);
        setHasSubtypes(true);
        BORE_LENS = new LensIridiumBore();
    }

    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
        iconIridiumBoreGlass = par1IconRegister.registerIcon("gregtania:lensIridiumBoreGlass");
        this.itemIcon = par1IconRegister.registerIcon("gregtania:lensIridiumBore");
    }

    @Override
    public void apply(ItemStack stack, BurstProperties props) {
        BORE_LENS.apply(stack, props);
    }

    @Override
    public boolean collideBurst(IManaBurst burst, MovingObjectPosition pos, boolean isManaBlock, boolean dead, ItemStack stack) {
        EntityThrowable entity = (EntityThrowable) burst;
        return BORE_LENS.collideBurst(burst, entity, pos, isManaBlock, dead, stack);
    }

    @Override
    public void updateBurst(IManaBurst burst, ItemStack stack) {
        EntityThrowable entity = (EntityThrowable) burst;
        BORE_LENS.updateBurst(burst, entity, stack);

    }



    @Override
    public int getLensColor(ItemStack stack) {
        return 0xFFFFFF;
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public IIcon getIconFromDamageForRenderPass(int par1, int par2) {
        return par2 == 1 ? itemIcon : iconIridiumBoreGlass;
    }

    @Override
    public boolean doParticles(IManaBurst burst, ItemStack stack) {
        return true;
    }

    @Override
    public boolean canCombineLenses(ItemStack itemStack, ItemStack itemStack1) {
        return false;
    }

    @Override
    public ItemStack getCompositeLens(ItemStack stack) {
        return null;
    }

    @Override
    public ItemStack setCompositeLens(ItemStack sourceLens, ItemStack compositeLens) {
        return sourceLens;
    }

    @Override
    public boolean shouldPull(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isControlLens(ItemStack stack) {
        return false;
    }

    @Override
    public boolean allowBurstShooting(ItemStack stack, IManaSpreader spreader, boolean redstone) {
        return BORE_LENS.allowBurstShooting(stack, spreader, redstone);
    }

    @Override
    public void onControlledSpreaderTick(ItemStack stack, IManaSpreader spreader, boolean redstone) {
        BORE_LENS.onControlledSpreaderTick(stack, spreader, redstone);
    }

    @Override
    public void onControlledSpreaderPulse(ItemStack stack, IManaSpreader spreader, boolean redstone) {
        BORE_LENS.onControlledSpreaderPulse(stack, spreader, redstone);
    }
}
