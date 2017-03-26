package com.gmail.pharaun.gregtania.command;

import com.gmail.pharaun.gregtania.misc.BotaniaHelper;
import com.gmail.pharaun.gregtania.misc.Config;
import gregtech.common.blocks.GT_Block_Ores_Abstract;
import gregtech.common.blocks.GT_TileEntity_Ores;
import net.minecraft.block.Block;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.gmail.pharaun.gregtania.misc.BotaniaHelper.acquireHarvestData;

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
        ArrayList<String> alias = new ArrayList();
        alias.add("debugOrechidSpawn");
        return alias;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] argString) {
        World world = sender.getEntityWorld();

        if (!world.isRemote) {
            if(Config.stackedOreInTiers) {
                // Just need to test the last tier of the 3 orechids
                processOrechid(sender, BotaniaHelper.tieredOreWeightOverworld, "Overworld", 3, 3);
                processOrechid(sender, BotaniaHelper.tieredOreWeightNether, "Nether", 3, 3);
                processOrechid(sender, BotaniaHelper.tieredOreWeightEnd, "End",4, 4);

            } else {
                // Test stuff in each tier for each orechid
                processOrechid(sender, BotaniaHelper.tieredOreWeightOverworld, "Overworld", 0, 3);
                processOrechid(sender, BotaniaHelper.tieredOreWeightNether, "Nether", 1, 3);
                processOrechid(sender, BotaniaHelper.tieredOreWeightEnd, "End", 1, 4);
            }
        }
    }

    private void processOrechid(ICommandSender sender, Map<Integer, Map<String, Integer>> tieredOreWeight, String dimension, int lower, int upper) {
        sender.addChatMessage(new ChatComponentText("Orechid: " + dimension));
        for(int i = lower; i <= upper; i++) {
            Map<String, Integer> tier = tieredOreWeight.get(i);

            if(tier != null) {
                if(lower != upper) {
                    sender.addChatMessage(new ChatComponentText("  Tier: " + i));
                }

                for (String oredict : tier.keySet()) {
                    // Grab a list of ores itemstack
                    List<ItemStack> ores = OreDictionary.getOres(oredict);

                    if(ores.isEmpty()) {
                        sender.addChatMessage(new ChatComponentText("    " + oredict + " - Empty"));
                        continue;
                    }

                    // Search for gregtech ores and if one doesn't exist emit that it doesnt
                    ItemStack match = null;

                    for(ItemStack stack : ores) {
                        Item item = stack.getItem();
                        String clname = item.getClass().getName();

                        if(clname.startsWith("gregtech") || clname.startsWith("gregapi")) {
                            // Set match
                            match = stack;
                        }
                    }

                    if(match == null) {
                        sender.addChatMessage(new ChatComponentText("    " + oredict + " - No Gregtech Ore Equiv"));
                    } else {
                        // Gregtech, let's validate it
                        Block block = Block.getBlockFromItem(match.getItem());
                        int meta = match.getItemDamage();

                        try {
                            int harvestData = acquireHarvestData(block, meta);
                            sender.addChatMessage(new ChatComponentText("    " + oredict + " - HarvestLevel: " + harvestData));
                        } catch (NoSuchMethodError e) {
                            sender.addChatMessage(new ChatComponentText("    " + oredict + " - Missing getBaseBlockHarvestLevel(), - " + block.getClass().getTypeName()));
                        }
                    }
                }
            }
        }
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
    public int compareTo(@NotNull Object o) {
        return 0;
    }
}
