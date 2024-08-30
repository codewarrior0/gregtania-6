package com.gmail.pharaun.gregtania.mixins;

import gregapi.block.multitileentity.MultiTileEntityBlock;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.botania.common.block.BlockSpecialFlower;

@Mixin(Block.class)
public abstract class BlockMixin {

    @SuppressWarnings("all")
    @Inject(method = "canSustainPlant", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockBush;canPlaceBlockOn(Lnet/minecraft/block/Block;)Z"), cancellable = true)
    public void goober(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable, CallbackInfoReturnable<Boolean> ctx) {
        if (plantable instanceof BlockSpecialFlower && ((Object) this) instanceof MultiTileEntityBlock) {
            ctx.setReturnValue(true);
        }
    }
}
