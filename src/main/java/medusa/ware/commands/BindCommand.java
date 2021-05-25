// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.commands;

import net.minecraft.util.BlockPos;
import net.minecraft.command.CommandException;
import java.util.Iterator;
import medusa.ware.module.Module;
import org.lwjgl.input.Keyboard;
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

public class BindCommand implements ICommand
{
    public int compareTo(final ICommand command) {
        return 0;
    }
    
    public String getCommandName() {
        return "-bind";
    }
    
    public String getCommandUsage(final ICommandSender commandSender) {
        return "/-bind";
    }
    
    public List<String> getCommandAliases() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("-b");
        return list;
    }
    
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        final MedusaWare instance = MedusaWare.instance;
        if (MedusaWare.destructed) {
            Game.Player().addChatMessage((IChatComponent)new ChatComponentText(String.valueOf(new StringBuilder().append(EnumChatFormatting.RED).append("Unknown command. Try /help for a list of commands"))));
            return;
        }
        if (array.length == 0) {
            ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Arguments: ")));
            ChatUtils.sendMessage("Set");
            ChatUtils.sendMessage("Remove");
            ChatUtils.sendMessage("RemoveAll");
        }
        else if (array[0].equalsIgnoreCase("Set")) {
            if (array.length >= 2 && MedusaWare.instance.mm.getModuleByName(array[1]) != null) {
                if (array.length == 3 && Keyboard.getKeyIndex(array[2].toUpperCase()) != 0) {
                    MedusaWare.instance.mm.getModuleByName(array[1]).setKey(Keyboard.getKeyIndex(array[2].toUpperCase()));
                    ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Successfully bound '").append(array[1]).append("' to '").append(array[2].toUpperCase()).append("'")));
                }
                else if (Keyboard.getKeyIndex(array[2].toUpperCase()) == 0) {
                    ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Sorry, the key '").append(array[2].toUpperCase()).append("' does not exist")));
                }
                else {
                    ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Usage: /bind set <Module> <Key>")));
                }
            }
            else if (array.length >= 2 && MedusaWare.instance.mm.getModuleByName(array[1]) == null) {
                ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Sorry, the module '").append(array[1].toUpperCase()).append("' does not exist")));
            }
            else if (array.length == 1) {
                ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Usage: /bind set <Module> <Key>")));
            }
        }
        else if (array[0].equalsIgnoreCase("RemoveAll")) {
            final Iterator<Module> iterator = MedusaWare.instance.mm.getModules().iterator();
            while (iterator.hasNext()) {
                iterator.next().setKey(0);
            }
            ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Successfully unbound all modules")));
        }
        else if (array[0].equalsIgnoreCase("Remove")) {
            if (array.length >= 2 && MedusaWare.instance.mm.getModuleByName(array[1]) != null) {
                MedusaWare.instance.mm.getModuleByName(array[1]).setKey(0);
                ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Successfully unbound '").append(array[1]).append("'")));
            }
            else if (array.length >= 2) {
                ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Sorry, the module '").append(array[1]).append("' does not exist")));
            }
            else {
                ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Usage: /bind remove <Module>")));
            }
        }
        else {
            ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Arguments: ")));
            ChatUtils.sendMessage("Set");
            ChatUtils.sendMessage("Remove");
            ChatUtils.sendMessage("RemoveAll");
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
