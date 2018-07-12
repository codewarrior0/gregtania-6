
package com.gmail.pharaun.gregtania.botania;

import com.gmail.pharaun.gregtania.misc.BotaniaHelper;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.CS;
import gregtech.worldgen.WorldgenHives;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.WeightedRandom;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileFunctional;
import vazkii.botania.common.core.handler.ConfigHandler;

import java.util.ArrayList;
import java.util.List;

public class SubTileBumblebiscus extends SubTileFunctional {

    private static final int COST = 8000;
    private static final int RANGE = 3;
    private static final int RANGE_Y = 2;

    private static final WorldgenHives wgHives = new WorldgenHives("bumblebiscus", false);

    private static final int[][] species = {
            {CS.DYE_INT_Brown, 0},
            {CS.DYE_INT_LightBlue, 100},
            {CS.DYE_INT_Purple, 200},
            {11141120, 300},
            {43690, 400},
            {CS.DYE_INT_LightGray, 500},
            {CS.DYE_INT_Green, 600},
            {CS.DYE_INT_White, 700},
            {CS.DYE_INT_Pink, 800},
            {CS.DYE_INT_Red, 900}
    };

    public static LexiconEntry lexiconEntry;

    @Override
    public LexiconEntry getEntry() {
        return lexiconEntry;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if(redstoneSignal > 0)
            return;

        if(!supertile.getWorldObj().isRemote && mana >= COST && ticksExisted % 2 == 0) {
            ChunkCoordinates coords = getCoordsToPut();
            if(coords != null) {
                MultiTileEntityRegistry tRegistry = MultiTileEntityRegistry.getRegistry("gt.multitileentity");
                int[] spec = species[supertile.getWorldObj().rand.nextInt(species.length)];

                wgHives.placeHive(tRegistry, supertile.getWorldObj(), coords.posX, coords.posY, coords.posZ,
                        spec[0], spec[1], supertile.getWorldObj().rand);

                if(ConfigHandler.blockBreakParticles) {
                    Block block = supertile.getWorldObj().getBlock(coords.posX, coords.posY, coords.posZ);
                    int meta = supertile.getWorldObj().getBlockMetadata(coords.posX, coords.posY, coords.posZ);

                    supertile.getWorldObj().playAuxSFX(2001, coords.posX, coords.posY, coords.posZ, Block.getIdFromBlock(block) + (meta << 12));

                }

                mana -= COST;
                sync();

            }
        }
    }

    public ChunkCoordinates getCoordsToPut() {
        List<ChunkCoordinates> possibleCoords = new ArrayList<>();

        int range = getRange();
        int rangeY = getRangeY();

        for(int i = -range; i < range + 1; i++)
            for(int j = -rangeY; j < rangeY; j++)
                for(int k = -range; k < range + 1; k++) {
                    int x = supertile.xCoord + i;
                    int y = supertile.yCoord + j;
                    int z = supertile.zCoord + k;
                    Block block = supertile.getWorldObj().getBlock(x, y, z);
                    int meta = supertile.getWorldObj().getBlockMetadata(x, y, z);
                    if(block != null && block == CS.BlocksGT.BalesGrass && (meta & 1) == 1)
                        possibleCoords.add(new ChunkCoordinates(x, y, z));
                }

        if(possibleCoords.isEmpty())
            return null;
        return possibleCoords.get(supertile.getWorldObj().rand.nextInt(possibleCoords.size()));
    }

    @Override
    public RadiusDescriptor getRadius() {
        return new RadiusDescriptor.Square(toChunkCoordinates(), getRange());
    }

    public int getRange() {
        return RANGE;
    }

    public int getRangeY() {
        return RANGE_Y;
    }

    @Override
    public int getColor() {
        return 0x769897;
    }

    @Override
    public boolean acceptsRedstone() {
        return true;
    }

    @Override
    public int getMaxMana() {
        return COST * 2;
    }

}