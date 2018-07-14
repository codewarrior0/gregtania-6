package com.gmail.pharaun.gregtania.botania;

import com.gmail.pharaun.gregtania.lexicon.GTLexiconData;
import com.gmail.pharaun.gregtania.misc.BotaniaHelper;
import gregapi.block.IBlockPlacable;
import gregapi.code.ItemStackContainer;
import gregapi.data.CS;
import gregapi.oredict.OreDictMaterial;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.common.util.ForgeDirection;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileFunctional;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.handler.ConfigHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Rio on 7/9/2018.
 */
public class SubTileSmallOrechid extends SubTileFunctional {

    private static final int COST = 17500;
    private static final int COST_GOG = 700;
    private static final int DELAY = 100;
    private static final int DELAY_GOG = 10;
    private static final int RANGE = 4;
    private static final int RANGE_Y = 2;
    private static final int LIFESPAN = 72000;

    public Set<Block> getSourceBlocks() {
        return SubTileLayeredOrechid.sourceBlocks;
    }

    public int getCost() {
        return Botania.gardenOfGlassLoaded ? COST_GOG : COST;
    }

    public int getDelay() {
        return Botania.gardenOfGlassLoaded ? DELAY_GOG : DELAY;
    }

    @Override
    public int getColor() {
        return 0x818181;
    }

    @Override
    public LexiconEntry getEntry() {
        return GTLexiconData.entryEvolvedOrechidSmall;
    }

    public ChunkCoordinates getCoordsToPut() {
        List<ChunkCoordinates> possibleCoords = new ArrayList<>();

        Set<Block> sources = getSourceBlocks();
        for(int i = -RANGE; i < RANGE + 1; i++)
            for(int j = -RANGE_Y; j < RANGE_Y; j++)
                for(int k = -RANGE; k < RANGE + 1; k++) {
                    int x = supertile.xCoord + i;
                    int y = supertile.yCoord + j;
                    int z = supertile.zCoord + k;
                    Block block = supertile.getWorldObj().getBlock(x, y, z);
                    for (Block source: sources) {
                        if (block != null && block.isReplaceableOreGen(supertile.getWorldObj(), x, y, z, source))
                            possibleCoords.add(new ChunkCoordinates(x, y, z));
                    }
                }

        if(possibleCoords.isEmpty())
            return null;
        return possibleCoords.get(supertile.getWorldObj().rand.nextInt(possibleCoords.size()));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if(supertile.getWorldObj().isRemote) return;

        if(ticksExisted > LIFESPAN) {
            this.supertile.getWorldObj().playAuxSFX(2001, this.supertile.xCoord, this.supertile.yCoord, this.supertile.zCoord, Block.getIdFromBlock(this.supertile.getBlockType()));
            if(this.supertile.getWorldObj().getBlock(this.supertile.xCoord, this.supertile.yCoord - 1, this.supertile.zCoord).isSideSolid(this.supertile.getWorldObj(), this.supertile.xCoord, this.supertile.yCoord - 1, this.supertile.zCoord, ForgeDirection.UP)) {
                this.supertile.getWorldObj().setBlock(this.supertile.xCoord, this.supertile.yCoord, this.supertile.zCoord, Blocks.deadbush);
            } else {
                this.supertile.getWorldObj().setBlockToAir(this.supertile.xCoord, this.supertile.yCoord, this.supertile.zCoord);
            }
            return;
        }

        int cost = getCost();
        if (!(mana >= cost && ticksExisted % getDelay() == 0)) return;


        ChunkCoordinates coords = getCoordsToPut();
        if (coords == null) return;

        Block block = null;
        int meta = 0;

        int x = coords.posX;
        int y = coords.posY;
        int z = coords.posZ;

        OreDictMaterial mat = getMaterialToPut(x, y, z);
        if (mat != null) {
            Block oldBlock = supertile.getWorldObj().getBlock(x, y, z);
            int oldMeta = supertile.getWorldObj().getBlockMetadata(x, y, z);
            IBlockPlacable oreBlock = CS.BlocksGT.stoneToSmallOres.get(new ItemStackContainer(oldBlock, 1, oldMeta));
            if (oreBlock != null)
                oreBlock.placeBlock(supertile.getWorldObj(), x, y, z, (byte) 6, mat.mID, null, true, false);

            block = oldBlock;
            meta = oldMeta;
        }

        if (block != null) {
            if (ConfigHandler.blockBreakParticles)
                supertile.getWorldObj().playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (meta << 12));
            supertile.getWorldObj().playSoundEffect(supertile.xCoord, supertile.yCoord, supertile.zCoord, "botania:orechid", 2F, 1F);

            mana -= cost;
            sync();
        }
    }

    public OreDictMaterial getMaterialToPut(int x, int y, int z) {
        return ((BotaniaHelper.MaterialRandomItem)WeightedRandom.getRandomItem(supertile.getWorldObj().rand, BotaniaHelper.wgSmallOres)).m;
    }

    @Override
    public RadiusDescriptor getRadius() {
        return new RadiusDescriptor.Square(toChunkCoordinates(), RANGE);
    }

    @Override
    public int getMaxMana() {
        return getCost();
    }
}
