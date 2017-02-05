package com.gmail.pharaun.gregtania.botania;

import com.gmail.pharaun.gregtania.Reference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import vazkii.botania.api.subtile.signature.SubTileSignature;

import java.util.List;

// Credit: Magic Bees - magicbees.main.utils.compat.botania
public class CustomSubTileSignature extends SubTileSignature {
    private final String name;
    private IIcon icon;

    public CustomSubTileSignature(String name) {
        this.name = name;
    }

    @Override
    public void registerIcons(IIconRegister register) {
        icon = register.registerIcon(Reference.MODID + ":" + this.name);
    }

    @Override
    public IIcon getIconForStack(ItemStack itemStack) {
        return icon;
    }

    @Override
    public String getUnlocalizedNameForStack(ItemStack itemStack) {
        return "tile.botania:flower." + Reference.MODID + "." + this.name;
    }

    @Override
    public String getUnlocalizedLoreTextForStack(ItemStack itemStack) {
        return "tile.botania:flower." + Reference.MODID + "." + this.name + ".reference";
    }

    @Override
    public void addTooltip(ItemStack stack, EntityPlayer player, List<String> tooltip) {
        //tooltip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("botania.flowerType.functional"));
        super.addTooltip(stack, player, tooltip);
    }
}
