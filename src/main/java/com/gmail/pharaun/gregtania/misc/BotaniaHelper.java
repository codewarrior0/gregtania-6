package com.gmail.pharaun.gregtania.misc;

import gregapi.data.CS;
import gregapi.data.MT;
import gregapi.data.OP;

import gregapi.oredict.OreDictMaterial;
import gregapi.worldgen.WorldgenOresLarge;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.lang.reflect.Method;
import java.util.*;

import static com.gmail.pharaun.gregtania.misc.Config.stackedOreInTiers;
import static com.gmail.pharaun.gregtania.misc.OrechidYieldConfig.oreWeightEnd;
import static com.gmail.pharaun.gregtania.misc.OrechidYieldConfig.oreWeightNether;
import static com.gmail.pharaun.gregtania.misc.OrechidYieldConfig.oreWeightOverworld;

// Credit - Botania - BotaniaAPI
public class BotaniaHelper {
    // The ore weights sorted by tiers and dimensions
    public static Map<Integer, Map<String, Integer>> tieredOreWeightOverworld;
    public static Map<Integer, Map<String, Integer>> tieredOreWeightNether;
    public static Map<Integer, Map<String, Integer>> tieredOreWeightEnd;

    private static Boolean oldMethod = null;
    private static Method cachedMethod = null;

    public static void initOreTables() {
        initWorldgenWeights();

        tieredOreWeightOverworld = initTieredOreWeight(oreWeightOverworld);
        tieredOreWeightNether = initTieredOreWeight(oreWeightNether);
        tieredOreWeightEnd = initTieredOreWeight(oreWeightEnd);

        // Sanity check
        tieredOreWeightOverworld = sanityCheck(tieredOreWeightOverworld, 0, 3);
        tieredOreWeightNether = sanityCheck(tieredOreWeightNether, 1, 3);
        tieredOreWeightEnd = sanityCheck(tieredOreWeightEnd, 1, 4);

        if (stackedOreInTiers) {
            tieredOreWeightOverworld = stackTieredOreWeight(tieredOreWeightOverworld);
            tieredOreWeightNether = stackTieredOreWeight(tieredOreWeightNether);
            tieredOreWeightEnd = stackTieredOreWeight(tieredOreWeightEnd);
        }
    }

    public static void initWorldgenWeights() {
        Map<String, Integer> wgWeightsOverworld = new HashMap<>();
        Map<String, Integer> wgWeightsNether = new HashMap<>();
        Map<String, Integer> wgWeightsEnd = new HashMap<>();

        initWorldgenWeightsDim(CS.ORE_OVERWORLD, wgWeightsOverworld);
        initWorldgenWeightsDim(CS.ORE_NETHER, wgWeightsNether);
        initWorldgenWeightsDim(CS.ORE_END, wgWeightsEnd);

        // Allow config weights to override worldgen weights, but default config to worldgen
        wgWeightsOverworld.putAll(oreWeightOverworld);
        oreWeightOverworld.putAll(wgWeightsOverworld);

        wgWeightsNether.putAll(oreWeightNether);
        oreWeightNether.putAll(wgWeightsNether);

        wgWeightsEnd.putAll(oreWeightEnd);
        oreWeightEnd.putAll(wgWeightsEnd);

        if (Config.overrideOrechidWeight) {
            Config.orechidConfig.save(Config.orechidConfigFile);
        }
    }


    public static void initWorldgenWeightsDim(List<WorldgenOresLarge> dimLayers, Map<String, Integer> weights) {
        for (WorldgenOresLarge layer: dimLayers) {
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
     * Sanity Check to prevent Crash, if there is missing ores in various tiers, insert oreCoal
     */
    private static Map<Integer, Map<String, Integer>> sanityCheck(Map<Integer, Map<String, Integer>> tieredOreWeight, int lower, int upper) {
        Map<Integer, Map<String, Integer>> ret = new Hashtable<>();

        Map<String, Integer> dummy = new Hashtable<>();
        dummy.put("oreCoal", 9999999);

        for (int i = lower; i <= upper; i++) {
            ret.put(i, tieredOreWeight.getOrDefault(i, dummy));
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
                ItemStack stack = OP.ore.mat(mat, 1);
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
}
