// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.commands;

import net.minecraft.util.BlockPos;
import net.minecraft.command.CommandException;
import java.util.Iterator;
import java.io.IOException;
import java.io.File;
import medusa.ware.config.ConfigManager;
import medusa.ware.utils.ChatUtils;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import medusa.ware.utils.Game;
import medusa.ware.MedusaWare;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.ICommand;

public class ConfigCommand implements ICommand
{
    public int compareTo(final ICommand command) {
        return 0;
    }
    
    public String getCommandName() {
        return "-config";
    }
    
    public String getCommandUsage(final ICommandSender commandSender) {
        return "/-config";
    }
    
    public List<String> getCommandAliases() {
        return new ArrayList<String>();
    }
    
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        final MedusaWare instance = MedusaWare.instance;
        if (MedusaWare.destructed) {
            Game.Player().addChatMessage((IChatComponent)new ChatComponentText(String.valueOf(new StringBuilder().append(EnumChatFormatting.RED).append("Unknown command. Try /help for a list of commands"))));
            return;
        }
        if (array.length == 0) {
            ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Arguments: ")));
            ChatUtils.sendMessage("Save");
            ChatUtils.sendMessage("Load");
            ChatUtils.sendMessage("Delete");
            ChatUtils.sendMessage("List");
            return;
        }
        if (array.length >= 2 && array[1].equalsIgnoreCase("Default")) {
            ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Sorry the config with the name '").append(array[1]).append("' is inaccessible")));
            return;
        }
        if (array.length >= 1) {
            if (array[0].equalsIgnoreCase("List")) {
                final ConfigManager configManager = MedusaWare.instance.configManager;
                if (ConfigManager.GetConfigs().size() == 0) {
                    ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("You have no configs")));
                }
                else {
                    ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Configs: ")));
                    final ConfigManager configManager2 = MedusaWare.instance.configManager;
                    final Iterator<String> iterator = ConfigManager.GetConfigs().iterator();
                    while (iterator.hasNext()) {
                        ChatUtils.sendMessage(iterator.next());
                    }
                }
            }
            else if (array[0].equalsIgnoreCase("Save")) {
                if (array.length >= 2) {
                    final ConfigManager configManager3 = MedusaWare.instance.configManager;
                    ConfigManager.getConfigFile(array[1]);
                    final ConfigManager configManager4 = MedusaWare.instance.configManager;
                    ConfigManager.AddListFile(array[1]);
                    final ConfigManager configManager5 = MedusaWare.instance.configManager;
                    ConfigManager.SaveConfigFile(array[1]);
                    ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Successfully saved the config with the name '").append(array[1]).append("'")));
                }
                else {
                    ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Usage: /config save <Config Name>")));
                }
            }
            else if (array[0].equalsIgnoreCase("Load")) {
                if (array.length >= 2) {
                    boolean b = true;
                    final File file = new File(ConfigManager.dir, String.format("%s.cfg", array[1]));
                    if (!file.exists()) {
                        b = false;
                        try {
                            file.createNewFile();
                        }
                        catch (IOException ex) {}
                    }
                    if (b) {
                        final ConfigManager configManager6 = MedusaWare.instance.configManager;
                        ConfigManager.LoadConfig(array[1]);
                        ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Successfully loaded the config with the name '").append(array[1]).append("'")));
                    }
                    else {
                        ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("The config with the name '").append(array[1]).append(EnumChatFormatting.GOLD).append("' does not exist")));
                    }
                }
                else {
                    ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Usage: /config load <Config Name>")));
                }
            }
            else if (array[0].equalsIgnoreCase("Delete")) {
                if (array.length >= 2) {
                    final ConfigManager configManager7 = MedusaWare.instance.configManager;
                    ConfigManager.DeleteListFile(array[1]);
                    boolean b2 = true;
                    final File file2 = new File(ConfigManager.dir, String.format("%s.cfg", array[1]));
                    if (!file2.exists()) {
                        b2 = false;
                    }
                    if (b2) {
                        if (file2.delete()) {
                            ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Successfully deleted the config with the name '").append(array[1]).append("'")));
                        }
                        else {
                            ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Deleting the config with the name '").append(array[1]).append("' failed")));
                        }
                    }
                    else {
                        ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("The config with the name '").append(array[1]).append(EnumChatFormatting.GOLD).append("' does not exist")));
                    }
                }
                else {
                    ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Usage: /config delete <Config Name>")));
                }
            }
            else {
                ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Arguments: ")));
                ChatUtils.sendMessage("Save");
                ChatUtils.sendMessage("Load");
                ChatUtils.sendMessage("Delete");
                ChatUtils.sendMessage("List");
            }
        }
    }
    
    public boolean canCommandSenderUseCommand(final ICommandSender commandSender) {
        return true;
    }
    
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return null;
    }
    
    public boolean isUsernameIndex(final String[] array, final int n) {
        return false;
    }
}
