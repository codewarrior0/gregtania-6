package com.gmail.pharaun.gregtania.botania;

import com.gmail.pharaun.gregtania.misc.BotaniaHelper;
import gregapi.block.IBlockPlacable;
import gregapi.code.ItemStackContainer;
import gregapi.code.ItemStackMap;
import gregapi.data.CS;
import gregapi.oredict.OreDictItemData;
import gregapi.oredict.OreDictManager;
import gregapi.oredict.OreDictMaterial;
import gregapi.worldgen.StoneLayer;
import gregapi.worldgen.StoneLayerOres;
import javafx.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.oredict.OreDictionary;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileFunctional;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.handler.ConfigHandler;
import vazkii.botania.common.lexicon.LexiconData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Rio on 7/9/2018.
 */
public class SubTileLayeredOrechid extends SubTileFunctional {

    private static final int COST = 17500;
    private static final int COST_GOG = 700;
    private static final int DELAY = 100;
    private static final int DELAY_GOG = 2;
    private static final int RANGE = 5;
    private static final int RANGE_Y = 3;

    public static final Set<Block> sourceBlocks = new HashSet<>(Collections.singletonList(Blocks.sand));
    public static LexiconEntry lexiconEntry = null;

    public Set<Block> getSourceBlocks() {
        return sourceBlocks;
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
        return lexiconEntry;
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

        if (redstoneSignal > 0)
            return;

        int cost = getCost();
        if (!(!supertile.getWorldObj().isRemote && mana >= cost && ticksExisted % getDelay() == 0)) return;

        ChunkCoordinates coords = getCoordsToPut();
        if (coords == null) return;

        Block block = null;
        int meta = 0;

        int x = coords.posX;
        int y = coords.posY;
        int z = coords.posZ;
        do {
            if (supertile.getWorldObj().getBlock(x, y, z) == Blocks.sand) {
                block = CS.BlocksGT.Sands;
                meta = 0;
                supertile.getWorldObj().setBlock(x, y, z, block, meta, 1 | 2);
                break;
            }

            OreDictMaterial mat = getStackedMaterialToPut(x, y, z);
            if (mat == null && y > 1) {
                mat = getStackedMaterialToPut(x, y - 1, z);
                if (mat != null)
                    y -= 1;
            }
            if (mat != null) {
                boolean placed = false;
                Block oldBlock = supertile.getWorldObj().getBlock(x, y, z);
                int oldMeta = supertile.getWorldObj().getBlockMetadata(x, y, z);
                IBlockPlacable oreBlock = CS.BlocksGT.stoneToNormalOres.get(new ItemStackContainer(oldBlock, 1, oldMeta));
                if (oreBlock != null) {
                    oreBlock.placeBlock(supertile.getWorldObj(), x, y, z, (byte) 6,
                            mat.mID, null, true, false);
                    placed = true;
                }
                oreBlock = CS.BlocksGT.stoneToNormalOres.get(new ItemStackContainer(
                        supertile.getWorldObj().getBlock(x, y + 1, z), 1,
                        supertile.getWorldObj().getBlockMetadata(x, y + 1, z)));
                if (oreBlock != null) {
                    oreBlock.placeBlock(supertile.getWorldObj(), x, y + 1, z, (byte) 6,
                            mat.mID, null, true, false);
                    placed = true;
                }
                if (placed) {
                    block = oldBlock;
                    meta = oldMeta;
                    break;
                }
            }
            mat = getMaterialToPut(x, y, z);
            if (mat != null) {
                Block oldBlock = supertile.getWorldObj().getBlock(x, y, z);
                int oldMeta = supertile.getWorldObj().getBlockMetadata(x, y, z);
                IBlockPlacable oreBlock = CS.BlocksGT.stoneToNormalOres.get(new ItemStackContainer(oldBlock, 1, oldMeta));
                if (oreBlock != null)
                    oreBlock.placeBlock(supertile.getWorldObj(), x, y, z, (byte) 6, mat.mID, null, true, false);

                block = oldBlock;
                meta = oldMeta;
                break;
            }
        } while (false);

        if (block != null) {
            if (ConfigHandler.blockBreakParticles)
                supertile.getWorldObj().playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (meta << 12));
            supertile.getWorldObj().playSoundEffect(supertile.xCoord, supertile.yCoord, supertile.zCoord, "botania:orechid", 2F, 1F);

            mana -= cost;
            sync();
        }
    }

    public OreDictMaterial getStackedMaterialToPut(int x, int y, int z) {
        if (y < supertile.getWorldObj().getHeight() - 1) {
            ItemStackContainer above = new ItemStackContainer(
                    supertile.getWorldObj().getBlock(x, y+1, z), 1,
                    supertile.getWorldObj().getBlockMetadata(x, y+1, z));
            ItemStackContainer below = new ItemStackContainer(
                    supertile.getWorldObj().getBlock(x, y, z), 1,
                    supertile.getWorldObj().getBlockMetadata(x, y, z));

            List<StoneLayerOres> ores = StoneLayer.get(above, below);
            if (ores.size() > 0) {
                StoneLayerOres ore = ores.get(supertile.getWorldObj().rand.nextInt(ores.size()));
                return ore.mMaterial;
            }
        }
        return null;
    }

    public OreDictMaterial getMaterialToPut(int x, int y, int z) {
        Util.BlockType bt = new Util.BlockType(
            supertile.getWorldObj().getBlock(x, y, z),
            supertile.getWorldObj().getBlockMetadata(x, y, z)
        );
        List<BotaniaHelper.MaterialRandomItem> oreDicts = BotaniaHelper.wgLayerOres.get(bt);
        if (oreDicts != null && oreDicts.size() > 0) {
            return ((BotaniaHelper.MaterialRandomItem)WeightedRandom.getRandomItem(supertile.getWorldObj().rand, oreDicts)).m;
        }
        return null;
    }

    @Override
    public RadiusDescriptor getRadius() {
        return new RadiusDescriptor.Square(toChunkCoordinates(), RANGE);
    }

    @Override
    public boolean acceptsRedstone() {
        return true;
    }

    @Override
    public int getMaxMana() {
        return getCost();
    }
}
