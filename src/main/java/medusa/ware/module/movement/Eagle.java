// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.movement;

import net.minecraft.init.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.settings.KeyBinding;
import medusa.ware.utils.Game;
import medusa.ware.settings.Setting;
import medusa.ware.MedusaWare;
import medusa.ware.module.Category;
import medusa.ware.utils.TimerUtils;
import medusa.ware.module.Module;

public class Eagle extends Module
{
    private TimerUtils sneakTimer;
    
    public Eagle() {
        super("Eagle", 0, Category.MOVEMENT, "Auto Bridge");
        this.sneakTimer = new TimerUtils();
    }
    
    @Override
    public void setup() {
        MedusaWare.instance.sm.rSetting(new Setting("OnSneak", this, true));
        MedusaWare.instance.sm.rSetting(new Setting("SneakTime", this, 50.0, 500.0, 1000.0, true));
    }
    
    @Override
    public void onTick() {
        if (!Game.Player().onGround || Game.Player() == null || Game.World() == null) {
            KeyBinding.setKeyBindState(Eagle.mc.gameSettings.keyBindSneak.getKeyCode(), false);
            return;
        }
        if (MedusaWare.instance.sm.getSettingByName(this, "OnSneak").getValBoolean() && !Keyboard.isKeyDown(Eagle.mc.gameSettings.keyBindSneak.getKeyCode())) {
            return;
        }
        if (Game.World().getBlockState(new BlockPos((Entity)Game.Player()).add(0, -1, 0)).getBlock() == Blocks.air) {
            KeyBinding.setKeyBindState(Eagle.mc.gameSettings.keyBindSneak.getKeyCode(), true);
            this.sneakTimer.reset();
        }
        else if (this.sneakTimer.hasReached(MedusaWare.instance.sm.getSettingByName(this, "SneakTime").getValInt())) {
            KeyBinding.setKeyBindState(Eagle.mc.gameSettings.keyBindSneak.getKeyCode(), Keyboard.isKeyDown(Eagle.mc.gameSettings.keyBindSneak.getKeyCode()));
        }
        else {
            KeyBinding.setKeyBindState(Eagle.mc.gameSettings.keyBindSneak.getKeyCode(), false);
        }
    }
    
    @Override
    public void onDisable() {
        KeyBinding.setKeyBindState(Eagle.mc.gameSettings.keyBindSneak.getKeyCode(), false);
    }
}
