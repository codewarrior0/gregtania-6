package com.gmail.pharaun.gregtania.botania;

import gregapi.data.CS;
import gregapi.data.FL;
import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.IFluidTank;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileGenerating;

public class SubTileAutoclavicus extends SubTileGenerating {
    public MultiTileEntityBasicMachine autoclave = null;
    public int burnTime = 0;

    @Override
    public void readFromPacketNBT(NBTTagCompound cmp) {
        super.readFromPacketNBT(cmp);
        this.burnTime = cmp.getInteger("burnTime");
    }

    @Override
    public void writeToPacketNBT(NBTTagCompound cmp) {
        super.writeToPacketNBT(cmp);
        cmp.setInteger("burnTime", this.burnTime);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (autoclave == null) {
            TileEntity tile = supertile.getWorldObj().getTileEntity(supertile.xCoord, supertile.yCoord - 1, supertile.zCoord);
            if (tile instanceof MultiTileEntityBasicMachine) {
                autoclave = (MultiTileEntityBasicMachine) tile;
            }
        } else if (!autoclave.getStateRunningPossible()) {
            IFluidTank outTank = autoclave.getFluidTanks2(CS.SIDE_Z_NEG)[0];
            if (outTank.getFluidAmount() < outTank.getCapacity() - 4) {
                if (burnTime == 0) {
                    if (!this.supertile.getWorldObj().isRemote) {
                        IFluidTank tank = autoclave.getFluidTanks2(CS.SIDE_BOTTOM)[0];
                        if (tank.getFluidAmount() > 80000) {
                            tank.drain(80000, true);
                            burnTime = 1800;
                            this.sync();
                        }
                    }
                } else {
                    if (this.supertile.getWorldObj().rand.nextInt(10) == 0)
                        this.supertile.getWorldObj().spawnParticle("largesmoke", (double) this.supertile.xCoord + 0.4 + Math.random() * 0.2, (double) this.supertile.yCoord + 0.65, (double) this.supertile.zCoord + 0.4 + Math.random() * 0.2, 0.0, 0.0, 0.0);
                    --this.burnTime;
                    if (this.burnTime == 0) {
                        outTank.fill(FL.DistW.make(500), true);
                    }
                }
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
        return this.burnTime > 0;
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
