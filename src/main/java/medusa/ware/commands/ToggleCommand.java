// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.commands;

import net.minecraft.util.BlockPos;
import net.minecraft.command.CommandException;
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

public class ToggleCommand implements ICommand
{
    public int compareTo(final ICommand command) {
        return 0;
    }
    
    public String getCommandName() {
        return "-toggle";
    }
    
    public String getCommandUsage(final ICommandSender commandSender) {
        return "/-toggle";
    }
    
    public List<String> getCommandAliases() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("-t");
        return list;
    }
    
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        final MedusaWare instance = MedusaWare.instance;
        if (MedusaWare.destructed) {
            Game.Player().addChatMessage((IChatComponent)new ChatComponentText(String.valueOf(new StringBuilder().append(EnumChatFormatting.RED).append("Unknown command. Try /help for a list of commands"))));
            return;
        }
        if (array.length == 0) {
            ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Usage: '.t <Module>'")));
        }
        else if (MedusaWare.instance.mm.getModuleByName(array[0]) != null) {
            MedusaWare.instance.mm.getModuleByName(array[0]).toggle();
            ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Successfully toggled ").append(array[0])));
        }
        else {
            ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Sorry, the module '").append(array[0]).append("' doesn't exist")));
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
