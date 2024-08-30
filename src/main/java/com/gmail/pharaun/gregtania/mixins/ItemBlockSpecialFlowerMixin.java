package com.gmail.pharaun.gregtania.mixins;

import gregapi.data.RM;
import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;

@Mixin(ItemBlockSpecialFlower.class)
public abstract class ItemBlockSpecialFlowerMixin {

    @Inject(method = "placeBlockAt", at = @At("HEAD"), cancellable = true, remap = false)
    public void goober(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata, CallbackInfoReturnable<Boolean> ctx) {
        boolean bl = ItemBlockSpecialFlower.getType(stack).equals("autoclavicusVile");
        TileEntity tile = world.getTileEntity(x, y - 1, z);
        boolean bl2 = tile instanceof MultiTileEntityBasicMachine && ((MultiTileEntityBasicMachine) tile).mRecipes == RM.Autoclave;
        if (bl != bl2) ctx.setReturnValue(false);
    }
}
