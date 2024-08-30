package com.gmail.pharaun.gregtania.events;

import com.gmail.pharaun.gregtania.botania.SubTileAutoclavicus;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import gregapi.data.CS;
import gregapi.data.RM;
import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.tile.TileSpecialFlower;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;

import static gregapi.data.CS.ZL_FS;
import static gregapi.data.CS.ZL_IS;

public class GregtaniaEventListeners {

    @SubscribeEvent
    public void preventBlockPlacement(PlayerInteractEvent event) {
        if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            if (event.entityPlayer.getHeldItem() != null && event.entityPlayer.getHeldItem().getItem() instanceof ItemBlockSpecialFlower) {
                boolean bl = ItemBlockSpecialFlower.getType(event.entityPlayer.getHeldItem()).equals("autoclavicusVile");
                TileEntity tile = event.world.getTileEntity(event.x, event.y, event.z);
                boolean bl2 = tile instanceof MultiTileEntityBasicMachine;
                if (bl && bl2 && ((MultiTileEntityBasicMachine) tile).mRecipes == RM.Autoclave) {
                    event.setCanceled(event.face != CS.SIDE_TOP);
                } else if (bl ^ bl2) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public void resetAutoclave(BlockEvent.BreakEvent event) {
        if (event.block == ModBlocks.specialFlower) {
            TileEntity tile = event.world.getTileEntity(event.x, event.y-1, event.z);
            if (tile instanceof MultiTileEntityBasicMachine) {
                MultiTileEntityBasicMachine autoclave = (MultiTileEntityBasicMachine) tile;
                TileSpecialFlower flower = (TileSpecialFlower) event.world.getTileEntity(event.x, event.y, event.z);
                if (flower.getSubTile() instanceof SubTileAutoclavicus && autoclave.mRecipes == RM.Autoclave) {
                    autoclave.mProgress = autoclave.mMinEnergy = autoclave.mMaxProgress = autoclave.mOutputEnergy = autoclave.mChargeRequirement = 0;
                    autoclave.mOutputFluids = ZL_FS;
                    autoclave.mOutputItems = ZL_IS;
                    autoclave.updateInventory();
                }
            }
        }
    }
}
