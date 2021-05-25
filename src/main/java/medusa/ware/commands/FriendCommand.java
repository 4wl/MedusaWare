// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.commands;

import net.minecraft.util.BlockPos;
import net.minecraft.command.CommandException;
import java.util.Iterator;
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

public class FriendCommand implements ICommand
{
    public int compareTo(final ICommand command) {
        return 0;
    }
    
    public String getCommandName() {
        return "-friend";
    }
    
    public String getCommandUsage(final ICommandSender commandSender) {
        return "/-friend";
    }
    
    public List<String> getCommandAliases() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("-friends");
        list.add("-f");
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
            ChatUtils.sendMessage("Add");
            ChatUtils.sendMessage("Remove");
            ChatUtils.sendMessage("Clear");
            ChatUtils.sendMessage("List");
            return;
        }
        if (array[0].equalsIgnoreCase("Clear")) {
            MedusaWare.instance.friendManager.getFriends().clear();
            final ConfigManager configManager = MedusaWare.instance.configManager;
            ConfigManager.SaveFriendsFile();
            ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Successfully cleared your friends list")));
        }
        else if (array.length > 0 && !array[0].equalsIgnoreCase("add") && !array[0].equalsIgnoreCase("remove") && !array[0].equalsIgnoreCase("clear") && !array[0].equalsIgnoreCase("list")) {
            ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Arguments: ")));
            ChatUtils.sendMessage("Add");
            ChatUtils.sendMessage("Remove");
            ChatUtils.sendMessage("Clear");
            ChatUtils.sendMessage("List");
        }
        else if (array[0].equalsIgnoreCase("add") && array.length > 1) {
            MedusaWare.instance.friendManager.addFriend(array[1]);
            ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Successfully added '").append(array[1]).append("' to your friends list")));
        }
        else if (array[0].equalsIgnoreCase("add")) {
            ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("/friends add {Name}")));
        }
        else if (array[0].equalsIgnoreCase("remove") && array.length > 1) {
            if (MedusaWare.instance.friendManager.getFriends().contains(array[1])) {
                MedusaWare.instance.friendManager.getFriends().remove(array[1]);
                final ConfigManager configManager2 = MedusaWare.instance.configManager;
                ConfigManager.SaveFriendsFile();
                ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Successfully removed '").append(array[1]).append(EnumChatFormatting.GOLD).append("' to your friends list")));
            }
            else {
                ChatUtils.sendMessage(String.valueOf(new StringBuilder().append("'").append(array[1]).append(EnumChatFormatting.GOLD).append("' was not found on your friends list")));
            }
        }
        else if (array[0].equalsIgnoreCase("list")) {
            if (MedusaWare.instance.friendManager.getFriends().size() == 0) {
                ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("You have no friends :(")));
            }
            else {
                ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Friends: ")));
            }
            final Iterator<String> iterator = MedusaWare.instance.friendManager.getFriends().iterator();
            while (iterator.hasNext()) {
                ChatUtils.sendMessage(iterator.next());
            }
        }
        else if (array[0].equalsIgnoreCase("remove")) {
            ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("/friends remove {Name}")));
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
