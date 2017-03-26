package com.gmail.pharaun.gregtania.misc;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.common.blocks.GT_Block_Ores;
import gregtech.common.blocks.GT_Block_Ores_Abstract;
import gregtech.common.blocks.GT_TileEntity_Ores;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.io.UncheckedIOException;
import java.lang.reflect.InvocationTargetException;
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

    /**
     * Sanity Check to prevent Crash, if there is missing ores in various tiers, insert oreCoal
     */
    private static Map<Integer, Map<String, Integer>> sanityCheck(Map<Integer, Map<String, Integer>> tieredOreWeight, int lower, int upper) {
        Map<Integer, Map<String, Integer>> ret = new Hashtable<>();

        Map<String, Integer> dummy = new Hashtable<>();
        dummy.put("oreCoal", 9999999);

        for(int i = lower; i <= upper; i++) {
            ret.put(i, tieredOreWeight.getOrDefault(i, dummy));
        }

        return ret;
    }

    /**
     * Stack the lower tier materials into the higher tier
     */
    private static Map<Integer, Map<String, Integer>> stackTieredOreWeight(Map<Integer, Map<String, Integer>> tieredOreWeight) {
        Map<Integer, Map<String, Integer>> ret = new Hashtable<>();

        ArrayList<Integer> tiers = new ArrayList(tieredOreWeight.keySet());
        Collections.sort(tiers);

        // Put in the first one
        ret.put(tiers.get(0), tieredOreWeight.get(tiers.get(0)));

        for (int i = 0; i < (tiers.size() - 1); i++) {
            int first = tiers.get(i);
            int second = tiers.get(i + 1);

            Map<String, Integer> melded = new HashMap();
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
            List<ItemStack> ores = OreDictionary.getOres(oreDictEntry);

            if (ores.isEmpty()) {
                LogHelper.error("No blocks available for this oredict entry: " + oreDictEntry + " - SKIPPING!");
                continue;
            }

            found : {
                // Search specifically for a Gregtech ore first, then fallback
                for (ItemStack stack : ores) {
                    String className = stack.getItem().getClass().getName();

                    if (className.startsWith("gregtech") || className.startsWith("gregapi")) {
                        // Found, let's process this entry
                        int harvestLevel = getHarvestLevelGregtech(stack);

                        ret.putIfAbsent(harvestLevel, new HashMap());
                        ret.get(harvestLevel).put(oreDictEntry, oreWeight.getOrDefault(oreDictEntry, 0));

                        break found;
                    }
                }

                // Didn't find any, grab the first and go
                int harvestLevel = getHarvestLevel(ores.get(0));

                ret.putIfAbsent(harvestLevel, new HashMap());
                ret.get(harvestLevel).put(oreDictEntry, oreWeight.getOrDefault(oreDictEntry, 0));
            }
        }

        return ret;
    }

    private static int getHarvestLevelGregtech(ItemStack stack) {
        // TODO: can we simplify this further?
        // This is a little tricky cos its borrowed from gregtech code in: GT_TileEntity_Ores.java, GT_Item_Ores.java, GT_Block_Ores.java
        int itemMeta = stack.getItemDamage();  // According to ItemBlock in minecraft itemMeta ~= itemDamage for blocks
        Materials material = GregTech_API.sGeneratedMaterials[(itemMeta % 1000)];

        int blockMeta = 0;

        if (material != null) {
            int tool = Math.min(7, material.mToolQuality - (itemMeta < 16000 ? 0 : 1));
            // UBC and black or red granite
            if ((itemMeta % 16000 / 1000 == 3) || (itemMeta % 16000 / 1000 == 4)) {
                blockMeta = Math.max(3, tool);
            } else {
                blockMeta = Math.max(0, tool);
            }
        }

        return blockMeta % 8;
    }

    private static int getHarvestLevel(ItemStack stack) {
        Block block = Block.getBlockFromItem(stack.getItem());
        int meta = stack.getItemDamage();
        return block.getHarvestLevel(meta);
    }

    /**
     * Since Gregtech 5.09.27(ish) and before used one way of handling this, and 5.09.27(ish) and after uses another way
     * of handling this: https://github.com/Blood-Asp/GT5-Unofficial/commit/d51f43f97bfdda3bf7b024d141225e249cdc36bf#diff-c5b626909eed3bb83cf6340aadef8f1b
     *
     * This method is for handling... this
     */
    public static int acquireHarvestData(Block block, int meta) {
        // getHarvestData(short) - or - getHarvestData(short, int)
        if(oldMethod == null) {
            // Identify which one to use
            Class<?> c = GT_TileEntity_Ores.class;
            try {
                // Try new first
                cachedMethod = c.getDeclaredMethod ("getHarvestData", short.class);
                oldMethod = Boolean.TRUE;
            } catch (NoSuchMethodException e) {
                oldMethod = Boolean.FALSE;
            }
        }

        if(oldMethod.booleanValue()) {
            try {
                Byte harvestData = (Byte)cachedMethod.invoke(GT_Block_Ores.class, (short)meta);
                return harvestData.intValue();
            } catch (Exception e) {
                throw (new RuntimeException(e));
            }
        } else {
            return GT_TileEntity_Ores.getHarvestData((short) meta, ((GT_Block_Ores_Abstract) block).getBaseBlockHarvestLevel(meta % 16000 / 1000));
        }
    }
}
