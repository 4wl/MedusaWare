// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.player;

import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemBlock;
import medusa.ware.utils.ItemUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import medusa.ware.utils.Game;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.client.gui.inventory.GuiChest;
import medusa.ware.settings.Setting;
import medusa.ware.MedusaWare;
import medusa.ware.module.Category;
import medusa.ware.utils.TimerUtils;
import medusa.ware.module.Module;

public class ChestStealer extends Module
{
    TimerUtils timer;
    double delay;
    private boolean checkName;
    
    public ChestStealer() {
        super("ChestStealer", 0, Category.PLAYER, "Automatically steals items from chests");
        this.timer = new TimerUtils();
    }
    
    @Override
    public void setup() {
        MedusaWare.instance.sm.rSetting(new Setting("MinDelay", this, 100.0, 0.0, 500.0, true));
        MedusaWare.instance.sm.rSetting(new Setting("MaxDelay", this, 150.0, 0.0, 500.0, true));
        MedusaWare.instance.sm.rSetting(new Setting("IgnoreItems", this, true));
        MedusaWare.instance.sm.rSetting(new Setting("CheckName", this, false));
    }
    
    @Override
    public void onEnable() {
        this.setDelay();
        super.onEnable();
    }
    
    @Override
    public void onUpdate() {
        this.checkName = MedusaWare.instance.sm.getSettingByName(this, "CheckName").getValBoolean();
        MedusaWare.instance.sm.getSettingByName(this, "MinDelay").getValInt();
        MedusaWare.instance.sm.getSettingByName(this, "MaxDelay").getValInt();
        if (ChestStealer.mc.currentScreen instanceof GuiChest) {
            final GuiChest guiChest = (GuiChest)ChestStealer.mc.currentScreen;
            final ContainerChest containerChest = (ContainerChest)Game.Player().openContainer;
            if ((containerChest.getLowerChestInventory().getName().toLowerCase().contains("menu") || containerChest.getLowerChestInventory().getName().toLowerCase().contains("play") || containerChest.getLowerChestInventory().getName().toLowerCase().contains("select") || containerChest.getLowerChestInventory().getName().toLowerCase().contains("teleport")) && this.checkName) {
                return;
            }
            if (this.isChestEmpty(guiChest) || this.isInventoryFull()) {
                ChestStealer.mc.thePlayer.closeScreen();
                return;
            }
            for (int i = 0; i < containerChest.getLowerChestInventory().getSizeInventory(); ++i) {
                final ItemStack stackInSlot = containerChest.getLowerChestInventory().getStackInSlot(i);
                if (stackInSlot != null && this.timer.hasReached(this.delay) && (this.isValidItem(stackInSlot) || !MedusaWare.instance.sm.getSettingByName(this, "IgnoreItems").getValBoolean())) {
                    ChestStealer.mc.playerController.windowClick(guiChest.inventorySlots.windowId, i, 0, 1, (EntityPlayer)ChestStealer.mc.thePlayer);
                    this.setDelay();
                    this.timer.reset();
                    break;
                }
            }
        }
    }
    
    private boolean isValidItem(final ItemStack itemStack) {
        return itemStack != null && ((ItemUtils.compareDamage(itemStack, ItemUtils.bestSword()) != null && ItemUtils.compareDamage(itemStack, ItemUtils.bestSword()) == itemStack) || itemStack.getItem() instanceof ItemBlock || (itemStack.getItem() instanceof ItemPotion && !ItemUtils.isBadPotion(itemStack)) || itemStack.getItem() instanceof ItemArmor || itemStack.getItem() instanceof ItemAppleGold || itemStack.getItem() instanceof ItemFood);
    }
    
    public void setDelay() {
        final double valDouble = MedusaWare.instance.sm.getSettingByName(this, "MinDelay").getValDouble();
        double valDouble2 = MedusaWare.instance.sm.getSettingByName(this, "MaxDelay").getValDouble();
        if (valDouble == valDouble2) {
            valDouble2 = valDouble * 1.1;
        }
        this.delay = ThreadLocalRandom.current().nextDouble(Math.min(valDouble, valDouble2), Math.max(valDouble, valDouble2));
    }
    
    private boolean isChestEmpty(final GuiChest guiChest) {
        final ContainerChest containerChest = (ContainerChest)Game.Player().openContainer;
        for (int i = 0; i < containerChest.getLowerChestInventory().getSizeInventory(); ++i) {
            final ItemStack stackInSlot = containerChest.getLowerChestInventory().getStackInSlot(i);
            if (stackInSlot != null && (this.isValidItem(stackInSlot) || !MedusaWare.instance.sm.getSettingByName(this, "IgnoreItems").getValBoolean())) {
                return false;
            }
        }
        return true;
    }
    
    private boolean isInventoryFull() {
        for (int i = 9; i <= 44; ++i) {
            if (ChestStealer.mc.thePlayer.inventoryContainer.getSlot(i).getStack() == null) {
                return false;
            }
        }
        return true;
    }
}
