// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.combat;

import java.lang.reflect.Field;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import com.google.common.collect.Ordering;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import medusa.ware.settings.Setting;
import medusa.ware.MedusaWare;
import java.util.ArrayList;
import medusa.ware.module.Category;
import net.minecraft.entity.player.EntityPlayer;
import java.util.List;
import medusa.ware.utils.TimerUtils;
import medusa.ware.module.Module;

public class AntiBot extends Module
{
    private TimerUtils notificationTimer;
    private int botsFound;
    private static List<EntityPlayer> bots;
    boolean remove;
    String mode;
    
    public AntiBot() {
        super("AntiBot", 0, Category.COMBAT, "Prevents you from attacking bots");
        this.notificationTimer = new TimerUtils();
    }
    
    @Override
    public void setup() {
        final ArrayList<String> list;
        (list = new ArrayList<String>()).add("Hypixel");
        list.add("Mineplex");
        MedusaWare.instance.sm.rSetting(new Setting("Mode", this, "Hypixel", list));
        MedusaWare.instance.sm.rSetting(new Setting("Remove", this, true));
    }
    
    @Override
    public void onUpdate() {
        this.mode = MedusaWare.instance.sm.getSettingByName(this, "Mode").getValString();
        this.remove = MedusaWare.instance.sm.getSettingByName(this, "Remove").getValBoolean();
        if (this.botsFound > 0 && this.notificationTimer.hasReached(1000.0)) {
            this.notificationTimer.reset();
            this.botsFound = 0;
        }
        if (AntiBot.mc.thePlayer.ticksExisted <= 0) {
            this.notificationTimer.reset();
            this.botsFound = 0;
            AntiBot.bots.clear();
        }
        if (this.mode.equalsIgnoreCase("Mineplex")) {
            for (final EntityPlayer next : AntiBot.mc.theWorld.playerEntities) {
                if (next instanceof EntityPlayer) {
                    final EntityPlayer entityPlayer = next;
                    if (entityPlayer.ticksExisted >= 2 || entityPlayer.getHealth() >= 20.0f || entityPlayer.getHealth() <= 0.0f) {
                        continue;
                    }
                    if (entityPlayer == AntiBot.mc.thePlayer) {
                        continue;
                    }
                    if (this.remove) {
                        AntiBot.mc.theWorld.removeEntity((Entity)entityPlayer);
                    }
                    this.add(entityPlayer, false);
                    ++this.botsFound;
                }
            }
        }
        else if (this.mode.equalsIgnoreCase("Hypixel")) {
            for (final Entity next2 : AntiBot.mc.theWorld.getLoadedEntityList()) {
                if (next2 instanceof EntityPlayer && next2 != AntiBot.mc.thePlayer && !AntiBot.bots.contains(next2)) {
                    final EntityPlayer entityPlayer2 = (EntityPlayer)next2;
                    final String formattedText = entityPlayer2.getDisplayName().getFormattedText();
                    final String customNameTag = entityPlayer2.getCustomNameTag();
                    final String name = entityPlayer2.getName();
                    if (formattedText.contains("[NPC]") && entityPlayer2 != AntiBot.mc.thePlayer) {
                        this.add(entityPlayer2, false);
                    }
                    if (customNameTag.equalsIgnoreCase(name) && entityPlayer2.getMaxHealth() == 20.0f && (this.getTabPlayerList() == null || !this.getTabPlayerList().contains(entityPlayer2)) && formattedText.contains("\u00ef¿½c") && formattedText.contains("\u00ef¿½r")) {
                        this.add(entityPlayer2, true);
                    }
                    if (!customNameTag.equals("") || AntiBot.mc.thePlayer.getDistanceToEntity((Entity)entityPlayer2) >= 6.0f || entityPlayer2.ticksExisted >= 10 || entityPlayer2.getHealth() != 20.0f || entityPlayer2.getMaxHealth() != 20.0f || (this.getTabPlayerList() != null && !this.getTabPlayerList().contains(entityPlayer2)) || !entityPlayer2.isSwingInProgress || !formattedText.contains("\u00ef¿½c") || !formattedText.contains("\u00ef¿½r") || !entityPlayer2.isEntityAlive() || !entityPlayer2.isBlocking()) {}
                    if (!entityPlayer2.isInvisible() || entityPlayer2 == AntiBot.mc.thePlayer || customNameTag.equalsIgnoreCase("") || !customNameTag.toLowerCase().contains("\u00ef¿½c\u00ef¿½c") || !name.contains("\u00ef¿½c") || (this.getTabPlayerList() != null && this.getTabPlayerList().contains(entityPlayer2))) {
                        continue;
                    }
                    this.add(entityPlayer2, true);
                }
            }
        }
    }

    public void getGameProfile(){

    }

    public void add(final EntityPlayer entityPlayer, final boolean b) {
        if (this.remove && b) {
            AntiBot.mc.theWorld.removeEntity((Entity)entityPlayer);
        }
        if (!AntiBot.bots.contains(entityPlayer)) {
            ++this.botsFound;
            AntiBot.bots.add(entityPlayer);
        }
    }
    
    public List<EntityPlayer> getTabPlayerList() {
        final NetHandlerPlayClient sendQueue = AntiBot.mc.thePlayer.sendQueue;
        final ArrayList<EntityPlayer> list;
        (list = new ArrayList<EntityPlayer>()).clear();
        final Ordering<NetworkPlayerInfo> field_175252_a = this.field_175252_a();
        if (field_175252_a == null) {
            return null;
        }
        for (final Object networkPlayerInfo : field_175252_a.sortedCopy((Iterable)sendQueue.getPlayerInfoMap())) {
            if (networkPlayerInfo == null) {
                continue;
            }
        }
        return list;
    }
    
    public Ordering<NetworkPlayerInfo> field_175252_a() {
        try {
            final Field declaredField = GuiPlayerTabOverlay.class.getDeclaredField("field_175252_a");
            declaredField.setAccessible(true);
            return (Ordering<NetworkPlayerInfo>)declaredField.get(GuiPlayerTabOverlay.class);
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        AntiBot.bots.clear();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        AntiBot.bots.clear();
    }
    
    public static List<EntityPlayer> getBots() {
        return AntiBot.bots;
    }
    
    static {
        AntiBot.bots = new ArrayList<EntityPlayer>();
    }
}
