package com.gmail.pharaun.gregtania.botania;

import gregapi.data.RM;
import gregapi.fluid.FluidTankGT;
import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import gregapi.util.ST;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileGenerating;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.ModItems;

import static com.gmail.pharaun.gregtania.proxies.CommonProxy.AUTOCLAVICUS_RECIPE;

public class SubTileAutoclavicus extends SubTileGenerating {
    public MultiTileEntityBasicMachine autoclave = null;
    public boolean aToggle = false;
    public int lazy = 0;

    @Override
    public void readFromPacketNBT(NBTTagCompound cmp) {
        super.readFromPacketNBT(cmp);
        this.aToggle = cmp.getBoolean("aToggle");

    }

    @Override
    public void writeToPacketNBT(NBTTagCompound cmp) {
        super.writeToPacketNBT(cmp);
        cmp.setBoolean("aToggle", this.aToggle);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.supertile.getWorldObj().isRemote) {
            if (this.aToggle) {
                if (this.supertile.getWorldObj().rand.nextInt(10) == 0)
                    this.supertile.getWorldObj().spawnParticle("largesmoke", (double) this.supertile.xCoord + 0.4 + Math.random() * 0.2, (double) this.supertile.yCoord + 0.65, (double) this.supertile.zCoord + 0.4 + Math.random() * 0.2, 0.0, 0.0, 0.0);
            }
        } else if (this.linkedCollector != null) {
            if (this.autoclave == null) {
                TileEntity tile = supertile.getWorldObj().getTileEntity(supertile.xCoord, supertile.yCoord - 1, supertile.zCoord);
                if (tile instanceof MultiTileEntityBasicMachine && ((MultiTileEntityBasicMachine) tile).mRecipes == RM.Autoclave) {
                    this.autoclave = (MultiTileEntityBasicMachine) tile;
                } else {
                    supertile.getWorldObj().setBlockToAir(supertile.xCoord, supertile.yCoord, supertile.zCoord);
                    ModBlocks.specialFlower.getDrops(supertile.getWorldObj(), supertile.xCoord, supertile.yCoord, supertile.zCoord, supertile.blockMetadata, 0);
                    ModBlocks.specialFlower.breakBlock(supertile.getWorldObj(), supertile.xCoord, supertile.yCoord, supertile.zCoord, supertile.blockType, supertile.blockMetadata);
                }
            } else if (this.lazy <= 0) {
                if (!this.autoclave.mActive) {
                    ItemStack in0 = this.autoclave.getStackInSlot(0);
                    ItemStack in1 = this.autoclave.getStackInSlot(1);
                    if (in0 == null ^ in1 == null) {
                        if (in1 == null && in0.getItem() == ModItems.vineBall && in0.stackSize == 0)
                            this.autoclave.setInventorySlotContents(0, null);
                        else if (in0 == null && in1.getItem() == ModItems.virus && in1.stackSize == 0)
                            this.autoclave.setInventorySlotContents(1, null);
                    }
                    if (this.autoclave.getStackInSlot(0) == null && this.autoclave.getStackInSlot(1) == null && this.autoclave.mTanksInput[0].amount() > 79_999) {
                        FluidTankGT out = this.autoclave.mTanksOutput[0];
                        //see MultiTileEntityBasicMachine#canOutput
                        if (out.amount() < 16_000 - AUTOCLAVICUS_RECIPE.mFluidOutputs[0].amount) {
                            this.autoclave.addStackToSlot(0, ST.make(ModItems.vineBall, 0, 0));
                            this.autoclave.addStackToSlot(1, ST.make(ModItems.virus, 0, 0));
                            this.aToggle = true;
                            this.sync();
                        }
                    }
                }
                if (this.aToggle != this.autoclave.mActive) {
                    this.aToggle = this.autoclave.mActive;
                    this.sync();
                }
                this.lazy = 20;
            } else {
                --this.lazy;
            }
        }
    }

    public RadiusDescriptor getRadius() {
        return new RadiusDescriptor.Square(this.toChunkCoordinates(), 3);
    }

    @Override
    public int getMaxMana() {
        return 300;
    }

    @Override
    public boolean canGeneratePassively() {
        return this.autoclave != null && this.autoclave.mActive && this.autoclave.mCurrentRecipe == AUTOCLAVICUS_RECIPE;
    }

    @Override
    public int getValueForPassiveGeneration() {
        return 5;
    }

    @Override
    public int getDelayBetweenPassiveGeneration() {
        return 6;
    }
}
