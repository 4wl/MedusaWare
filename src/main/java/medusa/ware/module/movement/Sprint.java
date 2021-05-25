// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import medusa.ware.utils.MoveUtils;
import medusa.ware.utils.Game;
import medusa.ware.settings.Setting;
import medusa.ware.MedusaWare;
import medusa.ware.module.Category;
import medusa.ware.module.Module;

public class Sprint extends Module
{
    public Sprint() {
        super("Sprint", 0, Category.MOVEMENT, "Allows you to always sprint");
    }
    
    @Override
    public void setup() {
        MedusaWare.instance.sm.rSetting(new Setting("Omni", this, false));
    }
    
    @Override
    public void onTick() {
        if (Sprint.mc.inGameHasFocus && !Game.Player().isSneaking()) {
            final EntityPlayerSP player = Game.Player();
            if (MedusaWare.instance.sm.getSettingByName(this, "Omni").getValBoolean()) {
                if (MoveUtils.PlayerMoving() && player.getFoodStats().getFoodLevel() > 6) {
                    player.setSprinting(true);
                }
            }
            else {
                KeyBinding.setKeyBindState(Sprint.mc.gameSettings.keyBindSprint.getKeyCode(), true);
            }
        }
    }
}
