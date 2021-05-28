// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.render;

import net.minecraft.client.gui.GuiScreen;
import medusa.ware.settings.Setting;
import medusa.ware.MedusaWare;
import medusa.ware.module.Category;
import medusa.ware.module.Module;

public class ClickGUI extends Module
{
    public ClickGUI() {
        super("ClickGUI", 54, Category.RENDER, "The thing you're looking at right now");
    }
    
    @Override
    public void setup() {
        MedusaWare.instance.sm.rSetting(new Setting("Rainbow", this, true));
        MedusaWare.instance.sm.rSetting(new Setting("Red", this, 170.0, 0.0, 255.0, true));
        MedusaWare.instance.sm.rSetting(new Setting("Blue", this, 0.0, 0.0, 255.0, true));
        MedusaWare.instance.sm.rSetting(new Setting("Green", this, 0.0, 0.0, 255.0, true));
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        ClickGUI.mc.displayGuiScreen((GuiScreen) MedusaWare.instance.clickGui);
        this.toggle();
    }
}
