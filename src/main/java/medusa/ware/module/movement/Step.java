// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.movement;

import medusa.ware.utils.Game;
import medusa.ware.settings.Setting;
import medusa.ware.MedusaWare;
import medusa.ware.module.Category;
import medusa.ware.module.Module;

public class Step extends Module
{
    private float oldStep;
    
    public Step() {
        super("Step", 0, Category.MOVEMENT, "Allows you to step up blocks");
    }
    
    @Override
    public void setup() {
        MedusaWare.instance.sm.rSetting(new Setting("StepHeight", this, 3.0, 1.0, 10.0, false));
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.oldStep = Game.Player().stepHeight;
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        Game.Player().stepHeight = this.oldStep;
    }
    
    @Override
    public void onTick() {
        Game.Player().stepHeight = (float) MedusaWare.instance.sm.getSettingByName(this, "StepHeight").getValInt();
    }
}
