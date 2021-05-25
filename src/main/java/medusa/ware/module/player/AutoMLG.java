// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.player;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import net.minecraft.item.ItemBucket;
import net.minecraft.util.BlockPos;
import net.minecraft.block.BlockAir;
import net.minecraft.client.settings.KeyBinding;
import medusa.ware.utils.PrivateUtils;
import medusa.ware.utils.Game;
import medusa.ware.settings.Setting;
import medusa.ware.MedusaWare;
import medusa.ware.module.Category;
import medusa.ware.module.Module;

public class AutoMLG extends Module
{
    private int oldSlot;
    private boolean lastFell;
    
    public AutoMLG() {
        super("AutoMLG", 0, Category.PLAYER, "Automatically places water to prevent you from dying");
    }
    
    @Override
    public void setup() {
        MedusaWare.instance.sm.rSetting(new Setting("MinFall", this, 4.0, 3.0, 10.0, false));
        MedusaWare.instance.sm.rSetting(new Setting("AutoAim", this, false));
        MedusaWare.instance.sm.rSetting(new Setting("AutoSwitch", this, true));
    }
    
    @Override
    public void onUpdate() {
        final boolean valBoolean = MedusaWare.instance.sm.getSettingByName(this, "AutoAim").getValBoolean();
        final boolean valBoolean2 = MedusaWare.instance.sm.getSettingByName(this, "AutoSwitch").getValBoolean();
        if (Game.Player().fallDistance > MedusaWare.instance.sm.getSettingByName(this, "MinFall").getValDouble() && this.isBlockUnderneath() && !AutoMLG.mc.thePlayer.onGround && this.getWaterBucketSlot() != -1) {
            if (valBoolean2) {
                AutoMLG.mc.thePlayer.inventory.currentItem = this.getWaterBucketSlot();
            }
            if (this.isHoldingWaterBucket()) {
                if (valBoolean) {
                    Game.Player().rotationPitch = 90.0f;
                }
                PrivateUtils.setRightClickDelayTimer(0);
                KeyBinding.setKeyBindState(AutoMLG.mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                this.lastFell = true;
            }
            else {
                this.StopMLG();
            }
        }
        else {
            this.StopMLG();
        }
    }
    
    private void StopMLG() {
        if (!this.lastFell) {
            this.oldSlot = AutoMLG.mc.thePlayer.inventory.currentItem;
        }
        else {
            AutoMLG.mc.thePlayer.inventory.currentItem = this.oldSlot;
            KeyBinding.setKeyBindState(AutoMLG.mc.gameSettings.keyBindUseItem.getKeyCode(), this.isKeyDown(AutoMLG.mc.gameSettings.keyBindUseItem.getKeyCode()));
            PrivateUtils.setRightClickDelayTimer(4);
        }
        this.lastFell = false;
    }
    
    private boolean isBlockUnderneath() {
        boolean b = false;
        for (int n = 0; n < AutoMLG.mc.thePlayer.posY + 2.0; ++n) {
            if (!(AutoMLG.mc.theWorld.getBlockState(new BlockPos(AutoMLG.mc.thePlayer.posX, (double)n, AutoMLG.mc.thePlayer.posZ)).getBlock() instanceof BlockAir)) {
                b = true;
            }
        }
        return b;
    }
    
    private boolean isHoldingWaterBucket() {
        return Game.Player().getCurrentEquippedItem() != null && Game.Player().getCurrentEquippedItem().getItem().getUnlocalizedName().toLowerCase().contains("water");
    }
    
    private boolean isHoldingEmptyBucket() {
        return Game.Player().getCurrentEquippedItem() != null && Game.Player().getCurrentEquippedItem().getItem() instanceof ItemBucket && !Game.Player().getCurrentEquippedItem().getItem().getUnlocalizedName().toLowerCase().contains("water") && !Game.Player().getCurrentEquippedItem().getItem().getUnlocalizedName().toLowerCase().contains("milk") && !Game.Player().getCurrentEquippedItem().getItem().getUnlocalizedName().toLowerCase().contains("lava");
    }
    
    private int getWaterBucketSlot() {
        for (int i = 0; i < 9; ++i) {
            if (Game.Player().inventory.getStackInSlot(i) != null && ((Game.Player().inventory.getStackInSlot(i).getItem() instanceof ItemBucket && !Game.Player().inventory.getStackInSlot(i).getItem().getUnlocalizedName().toLowerCase().contains("lava") && Game.Player().inventory.getStackInSlot(i).getItem().getUnlocalizedName().toLowerCase().contains("water")) || Game.Player().inventory.getStackInSlot(i).getItem().getUnlocalizedName().toLowerCase().contains("web"))) {
                return i;
            }
        }
        return -1;
    }
    
    private int getEmptyBucketSlot() {
        for (int i = 0; i < 9; ++i) {
            if (Game.Player().inventory.getStackInSlot(i) != null && Game.Player().inventory.getStackInSlot(i).getItem() instanceof ItemBucket && !Game.Player().inventory.getStackInSlot(i).getItem().getUnlocalizedName().toLowerCase().contains("lava") && !Game.Player().inventory.getStackInSlot(i).getItem().getUnlocalizedName().toLowerCase().contains("water") && !Game.Player().inventory.getStackInSlot(i).getItem().getUnlocalizedName().toLowerCase().contains("milk")) {
                return i;
            }
        }
        return -1;
    }
    
    private boolean isKeyDown(final int n) {
        if (n < 0) {
            return Mouse.isButtonDown(n + 100);
        }
        return Keyboard.isKeyDown(n);
    }
}
