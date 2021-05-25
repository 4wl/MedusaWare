// 
// Decompiled by Procyon v0.5.36
// 

package me.aycy.blockoverlay;

import net.minecraft.command.CommandException;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import medusa.ware.utils.Game;
import net.minecraft.command.ICommandSender;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.CommandBase;

public class MedusaWareCommand extends CommandBase
{
    public String getCommandName() {
        return "MedusaWare";
    }
    
    public List<String> getCommandAliases() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("boverlay");
        return list;
    }
    
    public String getCommandUsage(final ICommandSender commandSender) {
        return "/MedusaWare";
    }
    
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length == 0) {
            MedusaWare.openGui = true;
        }
        else {
            Game.Player().addChatMessage((IChatComponent)new ChatComponentText(String.valueOf(new StringBuilder().append(EnumChatFormatting.RED).append(this.getCommandUsage(commandSender)))));
        }
    }
    
    public boolean canCommandSenderUseCommand(final ICommandSender commandSender) {
        return true;
    }
}
