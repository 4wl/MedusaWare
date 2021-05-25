
package medusa.ware.ui.hud;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import medusa.ware.settings.SettingsManager;
import java.awt.Color;
import medusa.ware.module.Category;
import medusa.ware.module.Module;
import medusa.ware.MedusaWare;
import net.minecraft.client.gui.ScaledResolution;
import medusa.ware.ui.clickgui.ClickGUI;
import medusa.ware.utils.Game;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class HUDRenderer extends Gui
{
    protected Minecraft mc;
    
    public HUDRenderer() {
        this.mc = Game.Minecraft();
    }
    
    public void draw() {
        if (!MedusaWare.instance.mm.getModuleByName("HUD").isToggled()) {
            return;
        }
        this.renderModules();
    }
    
    public void renderModules() {
        if (this.mc.currentScreen instanceof ClickGUI || Game.World() == null || Game.Player() == null) {
            return;
        }
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        this.getOrder();
        int n = 1;
        for (int i = 0; i < this.getOrder().size(); ++i) {
            final String s = this.getOrder().get(i);
            if (!s.equalsIgnoreCase("HUD") && !this.getOrderModule().get(i).getCategory().equals(Category.VALUES)) {
                Gui.drawRect(scaledResolution.getScaledWidth() - this.mc.fontRendererObj.getStringWidth(MedusaWare.instance.mm.getModuleByName(s).getDisplayName()) - 5, 10 * n - this.mc.fontRendererObj.FONT_HEIGHT + 9, scaledResolution.getScaledWidth(), 10 * n - this.mc.fontRendererObj.FONT_HEIGHT - 1, new Color(0, 0, 0, 200).getRGB());
                this.mc.fontRendererObj.drawString(s, scaledResolution.getScaledWidth() - this.mc.fontRendererObj.getStringWidth(s) - 2, 10 * n - this.mc.fontRendererObj.FONT_HEIGHT, this.getColor(i).getRGB());
                ++n;
            }
        }
    }
    
    public Color getColor(final int n) {
        final Module moduleByName = MedusaWare.instance.mm.getModuleByName("HUD");
        final SettingsManager sm = MedusaWare.instance.sm;
        if (sm.getSettingByName(moduleByName, "Rainbow").getValBoolean()) {
            return MedusaWare.instance.cm.cc.getColor(n);
        }
        return new Color((int)sm.getSettingByName(moduleByName, "Red").getValDouble(), (int)sm.getSettingByName(moduleByName, "Green").getValDouble(), (int)sm.getSettingByName(moduleByName, "Blue").getValDouble(), 255);
    }
    
    private List<String> getOrder() {
        final List<Module> enabledModules = MedusaWare.instance.mm.getEnabledModules();
        List<Object> list = null;
        final Comparator<String> c = new Comparator<String>() {
            @Override
            public int compare(final String s, final String s2) {
                return Float.compare((float)HUDRenderer.this.mc.fontRendererObj.getStringWidth(s2), (float)HUDRenderer.this.mc.fontRendererObj.getStringWidth(s));
            }
        };
        if (list == null) {
            (list = new ArrayList<Object>()).clear();
        }
        for (int i = 0; i < enabledModules.size(); ++i) {
            list.add(enabledModules.get(i).getDisplayName());
        }

        return null;
    }
    
    private List<Module> getOrderModule() {
        final ArrayList<Module> list;
        (list = new ArrayList<Module>()).clear();
        for (final String s : this.getOrder()) {
            if (MedusaWare.instance.mm.getModuleByName(s) != null) {
                list.add(MedusaWare.instance.mm.getModuleByName(s));
            }
        }
        return list;
    }
}
