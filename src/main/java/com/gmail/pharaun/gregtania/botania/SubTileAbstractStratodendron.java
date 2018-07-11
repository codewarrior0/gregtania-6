
package com.gmail.pharaun.gregtania.botania;

import java.util.ArrayList;
import java.util.List;

import com.gmail.pharaun.gregtania.misc.BotaniaHelper;
import javafx.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.WeightedRandom;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileFunctional;
import vazkii.botania.common.core.handler.ConfigHandler;

public abstract class SubTileAbstractStratodendron extends SubTileFunctional {

    private static final int COST = 12;
    private static final int RANGE = 8;
    private static final int RANGE_Y = 5;

    @Override
    public void onUpdate() {
        super.onUpdate();
        if(redstoneSignal > 0)
            return;

        if(!supertile.getWorldObj().isRemote && mana >= COST && ticksExisted % 2 == 0) {
            ChunkCoordinates coords = getCoordsToPut();
            if(coords != null) {
                Util.BlockType blockType = getStoneToPut();
                if(blockType != null) {
                    supertile.getWorldObj().setBlock(coords.posX, coords.posY, coords.posZ, blockType.block, blockType.meta, 1 | 2);
                    if(ConfigHandler.blockBreakParticles)
                        supertile.getWorldObj().playAuxSFX(2001, coords.posX, coords.posY, coords.posZ, Block.getIdFromBlock(blockType.block) + (blockType.meta << 12));

                    mana -= COST;
                    sync();
                }
            }
        }
    }

    abstract public int getStoneTier();

    public Util.BlockType getStoneToPut() {
        return ((BotaniaHelper.BlockRandomItem)WeightedRandom.getRandomItem(supertile.getWorldObj().rand, BotaniaHelper.wgWeightsStones.get(getStoneTier()))).b;
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
                    if(block != null && block == Blocks.stone)
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
        return 1000;
    }

}