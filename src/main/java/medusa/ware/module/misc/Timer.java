// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.misc;

import medusa.ware.utils.PrivateUtils;
import medusa.ware.settings.Setting;
import medusa.ware.MedusaWare;
import medusa.ware.module.Category;
import medusa.ware.module.Module;

public class Timer extends Module
{
    public Timer() {
        super("Timer", 0, Category.MISC, "Increases the speed of your Minecraft client");
    }
    
    @Override
    public void setup() {
        MedusaWare.instance.sm.rSetting(new Setting("TimerSpeed", this, 1.25, 0.1, 10.0, false));
    }
    
    @Override
    public void onUpdate() {
        PrivateUtils.timer().timerSpeed = (float) MedusaWare.instance.sm.getSettingByName(this, "TimerSpeed").getValDouble();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        PrivateUtils.timer().timerSpeed = 1.0f;
    }
}
