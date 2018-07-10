package com.gmail.pharaun.gregtania.command;

import com.gmail.pharaun.gregtania.misc.BotaniaHelper;
import com.gmail.pharaun.gregtania.misc.Config;
import gregapi.oredict.OreDictMaterial;
import javafx.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DebugSpawnListCommand implements ICommand {
    @Override
    public String getCommandName() {
        return "debugOrechidSpawn";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "debugOrechidSpawn";
    }

    @Override
    public List getCommandAliases() {
        ArrayList<String> alias = new ArrayList<>();
        alias.add("debugOrechidSpawn");
        return alias;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] argString) {
        World world = sender.getEntityWorld();
        Set<String> layerOres = new HashSet<>();
        Set<String> veinOres = new HashSet<>();
        if (!world.isRemote) {
            BotaniaHelper.wgWeightsStones.forEach((k, v) -> {
                v.forEach(b -> sender.addChatMessage(new ChatComponentText(
                        b.itemWeight + ": " +
                        new ItemStack(b.b.block, 1, b.b.meta).getDisplayName() +
                        " (" + k + "): ")));
            });

            BotaniaHelper.wgLayerOres.forEach((k, v) -> {
                String name = new ItemStack(k.block, 1, k.meta).getDisplayName();
                for (BotaniaHelper.StringRandomItem o : v) {
                    sender.addChatMessage(new ChatComponentText(name + "(" + k.block.getHarvestLevel(k.meta) + "): " + o.s));
                    layerOres.add(o.s);
                }
            });

            if(Config.stackedOreInTiers) {
                // Just need to test the last tier of the 3 orechids
                //processOrechid(sender, BotaniaHelper.tieredOreWeightOverworld, "Overworld", 3, 3);
                veinOres.addAll(
                        processOrechid(sender, BotaniaHelper.tieredOreWeightNether, "Nether", 3, 3)
                );
                veinOres.addAll(
                        processOrechid(sender, BotaniaHelper.tieredOreWeightEnd, "End",4, 4)
                );
            } else {
                // Test stuff in each tier for each orechid
                //processOrechid(sender, BotaniaHelper.tieredOreWeightOverworld, "Overworld", 0, 3);
                veinOres.addAll(
                        processOrechid(sender, BotaniaHelper.tieredOreWeightNether, "Nether", 1, 3)
                );
                veinOres.addAll(
                        processOrechid(sender, BotaniaHelper.tieredOreWeightEnd, "End", 1, 4)
                );
            }
        }
        veinOres.removeAll(layerOres);
        sender.addChatMessage(new ChatComponentText("Ores not found in layers: "));
        veinOres.forEach(s->sender.addChatMessage(new ChatComponentText("    " + s)) );
    }


    private Set<String> processOrechid(ICommandSender sender, Map<Integer, Collection<BotaniaHelper.StringRandomItem>> tieredOreWeight, String dimension, int lower, int upper) {
        sender.addChatMessage(new ChatComponentText("Orechid: " + dimension));
        Set<String> allOres = new HashSet<>();
        for(int i = lower; i <= upper; i++) {
            Collection<BotaniaHelper.StringRandomItem> tier = tieredOreWeight.get(i);

            if(tier != null) {
                if(lower != upper) {
                    sender.addChatMessage(new ChatComponentText("  Tier: " + i));
                }

                for (BotaniaHelper.StringRandomItem item : tier) {
                    String oredict = item.s;
                    allOres.add(oredict);
                    // Get the GT ore material for the oredict

                    OreDictMaterial mat = OreDictMaterial.get(oredict.substring(3));

                    if(mat == null) {
                        sender.addChatMessage(new ChatComponentText("    " + oredict + " - No Gregtech Ore Equiv"));
                    } else {
                        int harvestData = mat.mToolQuality;
                        sender.addChatMessage(new ChatComponentText("    " + oredict + " - HarvestLevel: " + harvestData));
                    }
                }
            }
        }
        return allOres;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
        return true;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
        return false;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
