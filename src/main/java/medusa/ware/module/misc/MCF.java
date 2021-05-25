// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.misc;

import java.util.Iterator;
import medusa.ware.utils.ChatUtils;
import net.minecraft.util.EnumChatFormatting;
import medusa.ware.MedusaWare;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Mouse;
import medusa.ware.module.Category;
import medusa.ware.module.Module;

public class MCF extends Module
{
    boolean waitingForRelease;
    
    public MCF() {
        super("MCF", 0, Category.MISC, "MiddleClickFriends");
        this.waitingForRelease = false;
    }
    
    @Override
    public void onTick() {
        if (Mouse.isButtonDown(2) && !this.waitingForRelease) {
            if (MCF.mc.objectMouseOver != null && MCF.mc.objectMouseOver.entityHit != null && MCF.mc.objectMouseOver.entityHit instanceof EntityPlayer) {
                boolean b = false;
                final Iterator<String> iterator = MedusaWare.instance.friendManager.getFriends().iterator();
                while (iterator.hasNext()) {
                    if (iterator.next().equalsIgnoreCase(MCF.mc.objectMouseOver.entityHit.getName())) {
                        b = true;
                    }
                }
                if (!b) {
                    MedusaWare.instance.friendManager.addFriend(MCF.mc.objectMouseOver.entityHit.getName());
                    ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Added '").append(getTeamColor((EntityPlayer)MCF.mc.objectMouseOver.entityHit)).append(MCF.mc.objectMouseOver.entityHit.getName()).append(EnumChatFormatting.GOLD).append("' as a friend")));
                }
                else {
                    MedusaWare.instance.friendManager.getFriends().remove(MCF.mc.objectMouseOver.entityHit.getName());
                    ChatUtils.sendMessage(String.valueOf(new StringBuilder().append(EnumChatFormatting.GOLD).append("Removed '").append(getTeamColor((EntityPlayer)MCF.mc.objectMouseOver.entityHit)).append(MCF.mc.objectMouseOver.entityHit.getName()).append(EnumChatFormatting.GOLD).append("' from your friends list")));
                }
            }
            this.waitingForRelease = true;
        }
        else if (!Mouse.isButtonDown(2)) {
            this.waitingForRelease = false;
        }
    }
    
    public static String getTeamColor(final EntityPlayer entityPlayer) {
        return String.valueOf(new StringBuilder().append("\u00ef¿½").append((Object)entityPlayer.getDisplayName().getFormattedText().charAt(1)));
    }
}
