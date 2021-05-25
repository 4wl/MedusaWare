

package medusa.ware.module.render;

import medusa.ware.settings.Setting;
import medusa.ware.MedusaWare;
import medusa.ware.module.Category;
import medusa.ware.module.Module;

public class HUD extends Module
{
    public HUD() {
        super("HUD", 0, Category.RENDER, "Allows you to see UI");
    }
    
    @Override
    public void setup() {
        MedusaWare.instance.sm.rSetting(new Setting("Rainbow", this, true));
        MedusaWare.instance.sm.rSetting(new Setting("Red", this, 170.0, 0.0, 255.0, true));
        MedusaWare.instance.sm.rSetting(new Setting("Blue", this, 0.0, 0.0, 255.0, true));
        MedusaWare.instance.sm.rSetting(new Setting("Green", this, 0.0, 0.0, 255.0, true));
    }
}
