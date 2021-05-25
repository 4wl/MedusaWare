// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.movement;

import medusa.ware.utils.MoveUtils;
import medusa.ware.utils.Game;
import medusa.ware.settings.Setting;
import medusa.ware.MedusaWare;
import medusa.ware.module.Category;
import medusa.ware.module.Module;

public class Fly extends Module
{
    public Fly() {
        super("Flight", 0, Category.MOVEMENT, "Allows you to fly like in creative mode");
    }
    
    @Override
    public void setup() {
        MedusaWare.instance.sm.rSetting(new Setting("Speed", this, 1.0, 0.05, 5.0, false));
    }
    
    @Override
    public void onTick() {
        final boolean keyDown = Fly.mc.gameSettings.keyBindJump.isKeyDown();
        final boolean keyDown2 = Fly.mc.gameSettings.keyBindSneak.isKeyDown();
        final double valDouble = MedusaWare.instance.sm.getSettingByName(this, "Speed").getValDouble();
        if (!keyDown && !keyDown2) {
            Game.Player().motionY = 0.0;
        }
        else if (keyDown) {
            Game.Player().motionY = valDouble;
        }
        else if (keyDown2) {
            Game.Player().motionY = -valDouble;
        }
        MoveUtils.setMoveSpeed(valDouble);
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        Game.Player().capabilities.allowFlying = false;
    }
}
