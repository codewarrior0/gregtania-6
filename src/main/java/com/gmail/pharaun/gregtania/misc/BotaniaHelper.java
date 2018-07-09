package com.gmail.pharaun.gregtania.misc;

import gregapi.data.CS;
import gregapi.data.MT;
import gregapi.data.OP;

import gregapi.oredict.OreDictMaterial;
import gregapi.worldgen.StoneLayer;
import gregapi.worldgen.StoneLayerOres;
import gregapi.worldgen.WorldgenOresLarge;
import javafx.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.oredict.OreDictionary;

import java.util.*;
import java.util.stream.Collectors;

import static com.gmail.pharaun.gregtania.misc.Config.stackedOreInTiers;
import static com.gmail.pharaun.gregtania.misc.OrechidYieldConfig.oreWeightEnd;
import static com.gmail.pharaun.gregtania.misc.OrechidYieldConfig.oreWeightNether;

// Credit - Botania - BotaniaAPI
public class BotaniaHelper {
    // The ore weights sorted by tiers and dimensions
    //public static Map<Integer, Collection<StringRandomItem>> tieredOreWeightOverworld;
    public static Map<Integer, Collection<StringRandomItem>> tieredOreWeightNether;
    public static Map<Integer, Collection<StringRandomItem>> tieredOreWeightEnd;
    public static Map<Pair<Block, Byte>, List<StringRandomItem>> wgLayerOres;
    public static Collection<BlockRandomItem> wgWeightsStones;

    public static void initOreTables() {
        initWorldgenWeights();

        //Map<Integer, Map<String, Integer>> rawTieredOreWeightOverworld = initTieredOreWeight(oreWeightOverworld);
        Map<Integer, Map<String, Integer>> rawTieredOreWeightNether = initTieredOreWeight(oreWeightNether);
        Map<Integer, Map<String, Integer>> rawTieredOreWeightEnd = initTieredOreWeight(oreWeightEnd);

        // Sanity check
        //rawTieredOreWeightOverworld = sanityCheck(rawTieredOreWeightOverworld, 0, 3);
        rawTieredOreWeightNether = sanityCheck(rawTieredOreWeightNether, 1, 3);
        rawTieredOreWeightEnd = sanityCheck(rawTieredOreWeightEnd, 1, 4);

        if (stackedOreInTiers) {
            //rawTieredOreWeightOverworld = stackTieredOreWeight(rawTieredOreWeightOverworld);
            rawTieredOreWeightNether = stackTieredOreWeight(rawTieredOreWeightNether);
            rawTieredOreWeightEnd = stackTieredOreWeight(rawTieredOreWeightEnd);
        }

        //tieredOreWeightOverworld = cookTieredOreWeight(rawTieredOreWeightOverworld);
        tieredOreWeightNether = cookTieredOreWeight(rawTieredOreWeightNether);
        tieredOreWeightEnd = cookTieredOreWeight(rawTieredOreWeightEnd);

    }

    public static void initWorldgenWeights() {
        Map<String, Integer> wgWeightsNether = new HashMap<>();
        Map<String, Integer> wgWeightsEnd = new HashMap<>();

        initWorldgenLayerWeights();
        initWorldgenWeightsDim(CS.ORE_NETHER, wgWeightsNether);
        initWorldgenWeightsDim(CS.ORE_END, wgWeightsEnd);

        wgWeightsNether.putAll(oreWeightNether);
        oreWeightNether.putAll(wgWeightsNether);

        wgWeightsEnd.putAll(oreWeightEnd);
        oreWeightEnd.putAll(wgWeightsEnd);

        if (Config.overrideOrechidWeight) {
            Config.orechidConfig.save(Config.orechidConfigFile);
        }
    }

    public static void initWorldgenLayerWeights() {

        Map<Pair<Block, Byte>, List<StringRandomItem>> oresByLayer = new HashMap<>();
        Map<Pair<Block, Byte>, Integer> stoneWeights = new HashMap<>();

        for (StoneLayer layer: StoneLayer.LAYERS) {
            Pair<Block, Byte> stone = new Pair<>(layer.mStone, layer.mMetaStone);
            int weight = stoneWeights.getOrDefault(stone, 0);
            stoneWeights.put(stone, weight + 1);

            List<StringRandomItem> layerOreNames = oresByLayer.computeIfAbsent(stone, k -> new ArrayList<>());

            for (StoneLayerOres layerOres: layer.mOres) {
                layerOreNames.add(new StringRandomItem((int)(layerOres.mChance / 384), "ore" + layerOres.mMaterial.mNameInternal));
            }
        }

        wgWeightsStones = stoneWeights.entrySet().stream()
                .map(e -> new BlockRandomItem(e.getValue(), e.getKey()))
                .collect(Collectors.toList());
        wgLayerOres = oresByLayer;
    }


    public static void initWorldgenWeightsDim(List dimLayers, Map<String, Integer> weights) {
        for (Object _layer: dimLayers) {
            if (!(_layer instanceof WorldgenOresLarge)) continue;
            WorldgenOresLarge layer = (WorldgenOresLarge)_layer;

            if (!layer.mEnabled) continue;

            OreDictMaterial mat;
            int weight = 16 + layer.mSize;
            weight *= layer.mWeight;
            weight *= layer.mDensity;
            weight /= 80;

            for (int i=0; i<4; i++) {
                switch(i) {
                    case 0:
                        mat = layer.mTop;
                        weight *= 3;
                        break;
                    case 1:
                        mat = layer.mBottom;
                        weight *= 3;
                        break;
                    case 2:
                        mat = layer.mBetween;
                        break;
                    case 3:
                        mat = layer.mSpread;
                        break;
                    default:
                        continue;
                }

                if (mat == MT.NULL) continue;

                String oreDictEntry = "ore" + mat.mNameInternal;

                int oldWeight = weights.getOrDefault(oreDictEntry, 0);
                weights.put(oreDictEntry, weight + oldWeight);

            }
        }
    }

    /**
     * Sanity Check to prevent Crash, if there are missing ores in various tiers, insert oreCoal; if there are tiers
     * without an orechid, merge with the nearest tier.
     */
    private static Map<Integer, Map<String, Integer>> sanityCheck(Map<Integer, Map<String, Integer>> tieredOreWeight, int lower, int upper) {
        Map<Integer, Map<String, Integer>> ret = new Hashtable<>();

        Map<String, Integer> dummy = new Hashtable<>();
        dummy.put("oreCoal", 9999999);

        for (int i = lower; i <= upper; i++) {
            ret.put(i, tieredOreWeight.getOrDefault(i, dummy));
        }
        for (int i: tieredOreWeight.keySet()) {
            if (i < lower) {
                ret.get(lower).putAll(tieredOreWeight.get(i));
            }
            if (i > upper) {
                ret.get(upper).putAll(tieredOreWeight.get(i));
            }
        }

        return ret;
    }

    /**
     * Stack the lower tier materials into the higher tier
     */
    private static Map<Integer, Map<String, Integer>> stackTieredOreWeight(Map<Integer, Map<String, Integer>> tieredOreWeight) {
        Map<Integer, Map<String, Integer>> ret = new Hashtable<>();

        ArrayList<Integer> tiers = new ArrayList<>(tieredOreWeight.keySet());
        Collections.sort(tiers);

        // Put in the first one
        ret.put(tiers.get(0), tieredOreWeight.get(tiers.get(0)));

        for (int i = 0; i < (tiers.size() - 1); i++) {
            int first = tiers.get(i);
            int second = tiers.get(i + 1);

            Map<String, Integer> melded = new HashMap<>();
            melded.putAll(ret.get(first));
            melded.putAll(tieredOreWeight.get(second));

            ret.put(second, melded);
        }
        return ret;
    }

    private static Map<Integer, Collection<StringRandomItem>>
    cookTieredOreWeight(Map<Integer, Map<String, Integer>> tieredOreWeight) {
        return tieredOreWeight.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().entrySet().stream()
                                .map(we -> new StringRandomItem(we.getValue(), we.getKey()))
                                .collect(
                                Collectors.toList()
                        )
                ));
    }

    /**
     * Iterates through the weighted ore maps and attempt to find the hardness of each entry to populate
     * the tiered weighted ore maps.
     */
    private static Map<Integer, Map<String, Integer>> initTieredOreWeight(Map<String, Integer> oreWeight) {
        Map<Integer, Map<String, Integer>> ret = new Hashtable<>();

        for (String oreDictEntry : oreWeight.keySet()) {
            // Search by GT OreDictMaterial first, make sure the material can be an ore

            OreDictMaterial mat = OreDictMaterial.get(oreDictEntry.substring(3));
            if (mat != null) {
                ItemStack stack = OP.oreVanillastone.mat(mat, 1);
                if (stack != null) {
                    int harvestLevel = mat.mToolQuality;
                    ret.putIfAbsent(harvestLevel, new HashMap<>());
                    ret.get(harvestLevel).put(oreDictEntry, oreWeight.getOrDefault(oreDictEntry, 0));

                    continue;
                }
            }

            // Next, check the forge OreDictionary for non-GT ores

            List<ItemStack> ores = OreDictionary.getOres(oreDictEntry);

            if (ores.isEmpty()) {
                LogHelper.error("No blocks available for this oredict entry: " + oreDictEntry + " - SKIPPING!");
                continue;
            }

            int harvestLevel = getHarvestLevel(ores.get(0));

            ret.putIfAbsent(harvestLevel, new HashMap<>());
            ret.get(harvestLevel).put(oreDictEntry, oreWeight.getOrDefault(oreDictEntry, 0));

        }

        return ret;
    }

    private static int getHarvestLevel(ItemStack stack) {
        Block block = Block.getBlockFromItem(stack.getItem());
        int meta = stack.getItemDamage();
        return block.getHarvestLevel(meta);
    }

    public static class StringRandomItem extends WeightedRandom.Item {

        public String s;

        public StringRandomItem(int par1, String s) {
            super(par1);
            this.s = s;
        }

    }

    public static class BlockRandomItem extends WeightedRandom.Item {

        public Pair<Block, Byte> b;

        public BlockRandomItem(int par1, Pair<Block, Byte> b) {
            super(par1);
            this.b = b;
        }

    }

}
