// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.player;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import java.util.concurrent.ThreadLocalRandom;
import medusa.ware.settings.Setting;
import medusa.ware.MedusaWare;
import medusa.ware.module.Category;
import medusa.ware.utils.TimerUtils;
import medusa.ware.module.Module;

public class AutoArmor extends Module
{
    private TimerUtils timer;
    private TimerUtils dropTimer;
    private boolean dropping;
    private double delay;
    
    public AutoArmor() {
        super("AutoArmor", 0, Category.PLAYER, "Automatically equips armor");
        this.timer = new TimerUtils();
        this.dropTimer = new TimerUtils();
    }
    
    @Override
    public void setup() {
        MedusaWare.instance.sm.rSetting(new Setting("MinDelay", this, 100.0, 0.0, 1000.0, true));
        MedusaWare.instance.sm.rSetting(new Setting("MaxDelay", this, 150.0, 0.0, 1000.0, true));
        MedusaWare.instance.sm.rSetting(new Setting("Drop", this, true));
        MedusaWare.instance.sm.rSetting(new Setting("OpenInv", this, false));
    }
    
    @Override
    public void onTick() {
        if (!this.isToggled()) {
            return;
        }
        String s;
        if (MedusaWare.instance.sm.getSettingByName(this, "OpenInv").getValBoolean()) {
            s = "OpenInv";
        }
        else {
            s = "Basic";
        }
        final double valDouble = MedusaWare.instance.sm.getSettingByName(this, "MinDelay").getValDouble();
        double valDouble2 = MedusaWare.instance.sm.getSettingByName(this, "MaxDelay").getValDouble();
        if (valDouble == valDouble2) {
            valDouble2 = valDouble * 1.1;
        }
        this.delay = ThreadLocalRandom.current().nextDouble(Math.min(valDouble, valDouble2), Math.max(valDouble, valDouble2));
        if (s.equalsIgnoreCase("OpenInv") && !(AutoArmor.mc.currentScreen instanceof GuiInventory)) {
            return;
        }
        if ((AutoArmor.mc.currentScreen == null || AutoArmor.mc.currentScreen instanceof GuiInventory || AutoArmor.mc.currentScreen instanceof GuiChat) && this.timer.hasReached(this.delay)) {
            this.getBestArmor();
        }
        if (!this.dropping) {
            if ((AutoArmor.mc.currentScreen == null || AutoArmor.mc.currentScreen instanceof GuiInventory || AutoArmor.mc.currentScreen instanceof GuiChat) && this.timer.hasReached(this.delay)) {
                this.getBestArmor();
            }
        }
        else if (this.dropTimer.hasReached(this.delay)) {
            AutoArmor.mc.playerController.windowClick(AutoArmor.mc.thePlayer.inventoryContainer.windowId, -999, 0, 0, (EntityPlayer)AutoArmor.mc.thePlayer);
            this.dropTimer.reset();
            this.dropping = false;
        }
    }
    
    public void getBestArmor() {
        for (int i = 1; i < 5; ++i) {
            if (AutoArmor.mc.thePlayer.inventoryContainer.getSlot(4 + i).getHasStack()) {
                if (isBestArmor(AutoArmor.mc.thePlayer.inventoryContainer.getSlot(4 + i).getStack(), i)) {
                    continue;
                }
                if (MedusaWare.instance.sm.getSettingByName(this, "Drop").getValBoolean()) {
                    this.drop(4 + i);
                }
                else {
                    this.shiftClick(4 + i);
                }
            }
            for (int j = 9; j < 45; ++j) {
                if (AutoArmor.mc.thePlayer.inventoryContainer.getSlot(j).getHasStack()) {
                    final ItemStack stack = AutoArmor.mc.thePlayer.inventoryContainer.getSlot(j).getStack();
                    if (isBestArmor(stack, i) && getProtection(stack) > 0.0f) {
                        this.shiftClick(j);
                        this.timer.reset();
                        if (this.delay > 0.0) {
                            return;
                        }
                    }
                }
            }
        }
        if (MedusaWare.instance.sm.getSettingByName(this, "Drop").getValBoolean()) {
            for (int k = 9; k < 45; ++k) {
                if (AutoArmor.mc.thePlayer.inventoryContainer.getSlot(k).getHasStack()) {
                    final ItemStack stack2 = AutoArmor.mc.thePlayer.inventoryContainer.getSlot(k).getStack();
                    if ((getProtection(stack2) > 0.0f || this.isDuplicate(stack2, k)) && !this.dropping && stack2.getItem() instanceof ItemArmor) {
                        this.drop(k);
                    }
                }
            }
        }
    }
    
    public boolean isDuplicate(final ItemStack itemStack, final int n) {
        for (int i = 0; i < 45; ++i) {
            if (AutoArmor.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack stack = AutoArmor.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (stack != itemStack && n != i && getProtection(itemStack) == getProtection(stack) && stack.getItem() instanceof ItemArmor && !(stack.getItem() instanceof ItemPotion) && !(stack.getItem() instanceof ItemBlock)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean isBestArmor(final ItemStack itemStack, final int n) {
        final float protection = getProtection(itemStack);
        String s = "";
        if (n == 1) {
            s = "helmet";
        }
        else if (n == 2) {
            s = "chestplate";
        }
        else if (n == 3) {
            s = "leggings";
        }
        else if (n == 4) {
            s = "boots";
        }
        if (!itemStack.getUnlocalizedName().contains(s)) {
            return false;
        }
        for (int i = 5; i < 45; ++i) {
            if (AutoArmor.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack stack = AutoArmor.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (getProtection(stack) > protection && stack.getUnlocalizedName().contains(s)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public void shiftClick(final int n) {
        AutoArmor.mc.playerController.windowClick(AutoArmor.mc.thePlayer.inventoryContainer.windowId, n, 0, 1, (EntityPlayer)AutoArmor.mc.thePlayer);
    }
    
    public void drop(final int n) {
        if (MedusaWare.instance.sm.getSettingByName(this, "Drop").getValBoolean() && this.dropTimer.hasReached(this.delay) && !this.dropping) {
            AutoArmor.mc.playerController.windowClick(AutoArmor.mc.thePlayer.inventoryContainer.windowId, n, 0, 0, (EntityPlayer)AutoArmor.mc.thePlayer);
            this.dropping = true;
            this.dropTimer.reset();
        }
    }
    
    public static float getProtection(final ItemStack itemStack) {
        float n = 0.0f;
        if (itemStack.getItem() instanceof ItemArmor) {
            final ItemArmor itemArmor = (ItemArmor)itemStack.getItem();
            n = (float)((float)((float)((float)((float)((float)(n + (itemArmor.damageReduceAmount + (100 - itemArmor.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, itemStack) * 0.0075)) + EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, itemStack) / 100.0) + EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, itemStack) / 100.0) + EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, itemStack) / 100.0) + EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack) / 50.0) + EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, itemStack) / 100.0);
        }
        return n;
    }
}
