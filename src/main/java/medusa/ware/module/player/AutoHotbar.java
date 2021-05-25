// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.player;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import medusa.ware.utils.Game;
import net.minecraft.client.gui.inventory.GuiInventory;
import io.netty.util.internal.ThreadLocalRandom;
import medusa.ware.settings.Setting;
import medusa.ware.MedusaWare;
import medusa.ware.module.Category;
import medusa.ware.utils.TimerUtils;
import medusa.ware.module.Module;

public class AutoHotbar extends Module
{
    private TimerUtils timer;
    private double speed;
    
    public AutoHotbar() {
        super("AutoHotbar", 0, Category.PLAYER, "Automatically moves items to your hotbar");
        this.timer = new TimerUtils();
    }
    
    @Override
    public void setup() {
        MedusaWare.instance.sm.rSetting(new Setting("Projectiles", this, false));
        MedusaWare.instance.sm.rSetting(new Setting("SplashPotions", this, true));
        MedusaWare.instance.sm.rSetting(new Setting("OtherPotions", this, false));
        MedusaWare.instance.sm.rSetting(new Setting("MinSpeed", this, 0.2, 0.0, 1.0, false));
        MedusaWare.instance.sm.rSetting(new Setting("MaxSpeed", this, 0.4, 0.0, 1.001, false));
    }
    
    @Override
    public void onEnable() {
        this.updateSpeed();
    }
    
    private void updateSpeed() {
        final double valDouble = MedusaWare.instance.sm.getSettingByName(this, "MinSpeed").getValDouble();
        final double valDouble2 = MedusaWare.instance.sm.getSettingByName(this, "MaxSpeed").getValDouble();
        this.speed = ThreadLocalRandom.current().nextDouble(Math.min(valDouble, valDouble2), Math.max(valDouble, valDouble2));
    }
    
    @Override
    public void onUpdate() {
        if (AutoHotbar.mc.currentScreen instanceof GuiInventory && this.timer.hasReached(this.speed * 1000.0)) {
            if (!this.isHotbarFull() && this.getItem() != -1) {
                this.shiftClick(this.getItem());
                this.updateSpeed();
            }
            this.timer.reset();
            this.updateSpeed();
        }
    }
    
    private int getItem() {
        for (int i = 9; i < 36; ++i) {
            if (this.isValidItem(Game.Player().inventory.mainInventory[i])) {
                return i;
            }
        }
        return -1;
    }
    
    private boolean isHotbarFull() {
        for (int i = 0; i < 9; ++i) {
            if (Game.Player().inventory.mainInventory[i] == null) {
                return false;
            }
        }
        return true;
    }
    
    private void shiftClick(final int n) {
        AutoHotbar.mc.playerController.windowClick(AutoHotbar.mc.thePlayer.inventoryContainer.windowId, n, 0, 1, (EntityPlayer)AutoHotbar.mc.thePlayer);
    }
    
    private boolean isValidItem(final ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }
        final boolean valBoolean = MedusaWare.instance.sm.getSettingByName(this, "SplashPotions").getValBoolean();
        final boolean valBoolean2 = MedusaWare.instance.sm.getSettingByName(this, "OtherPotions").getValBoolean();
        final boolean valBoolean3 = MedusaWare.instance.sm.getSettingByName(this, "Projectiles").getValBoolean();
        return (Item.getIdFromItem(itemStack.getItem()) == 373 && valBoolean && itemStack.getItem().getItemStackDisplayName(itemStack).toLowerCase().contains("splash")) || (Item.getIdFromItem(itemStack.getItem()) == 373 && valBoolean2 && !itemStack.getItem().getItemStackDisplayName(itemStack).toLowerCase().contains("splash")) || ((Item.getIdFromItem(itemStack.getItem()) == 332 || Item.getIdFromItem(itemStack.getItem()) == 344) && valBoolean3);
    }
}
