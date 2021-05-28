// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.utils;

import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class ChatUtils
{
    public static void sendMessage(final String s) {
        Game.Player().addChatMessage((IChatComponent)new ChatComponentText(String.format("%s%s", String.valueOf(new StringBuilder().append(EnumChatFormatting.DARK_AQUA).append("Explicit").append(EnumChatFormatting.GRAY).append(": ")), s)));
    }
}
