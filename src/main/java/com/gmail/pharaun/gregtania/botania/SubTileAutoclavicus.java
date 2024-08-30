package com.gmail.pharaun.gregtania.botania;

import com.gmail.pharaun.gregtania.proxies.ClientProxy;
import gregapi.data.RM;
import gregapi.fluid.FluidTankGT;
import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import gregapi.util.ST;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileGenerating;
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
                if (this.supertile.getWorldObj().rand.nextInt(20) == 0) {
                    float scale = (7 + this.supertile.getWorldObj().rand.nextInt(4)) * 0.02f;
                    float color = (2 + supertile.getWorldObj().rand.nextInt(4)) * 0.1f;
                    byte letter = (byte) this.supertile.getWorldObj().rand.nextInt(26);
                    ClientProxy.glyphParticle(this.supertile.getWorldObj(), (double) this.supertile.xCoord + 0.4 + Math.random() * 0.2, (double) this.supertile.yCoord + 0.65, (double) this.supertile.zCoord + 0.4 + Math.random() * 0.2, scale, color, color, color, letter);
                }
            }
        } else if (this.linkedCollector != null) {
            if (this.lazy <= 0) {
                if (this.autoclave == null || this.autoclave.isDead()) {
                    TileEntity tile = supertile.getWorldObj().getTileEntity(supertile.xCoord, supertile.yCoord - 1, supertile.zCoord);
                    if (tile instanceof MultiTileEntityBasicMachine && ((MultiTileEntityBasicMachine) tile).mRecipes == RM.Autoclave) {
                        this.autoclave = (MultiTileEntityBasicMachine) tile;
                    } else {
                        this.supertile.getWorldObj().playSoundEffect(supertile.xCoord, supertile.yCoord, supertile.zCoord, "gregtania:autoclavicusBreak", 0.4f, 0.6f);
                        this.supertile.getBlockType().dropBlockAsItem(supertile.getWorldObj(), supertile.xCoord, supertile.yCoord, supertile.zCoord, supertile.blockMetadata, 0);
                        this.supertile.getBlockType().breakBlock(supertile.getWorldObj(), supertile.xCoord, supertile.yCoord, supertile.zCoord, supertile.blockType, supertile.blockMetadata);
                        supertile.getWorldObj().setBlockToAir(supertile.xCoord, supertile.yCoord, supertile.zCoord);
                        return;
                    }
                }
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
                            this.supertile.getWorldObj().playSoundEffect(this.supertile.xCoord, autoclave.yCoord, autoclave.zCoord, "gregtania:autoclavicusVile", 0.2f, 0.6f + (0.1f * supertile.getWorldObj().rand.nextInt(4)));
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
