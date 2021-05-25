

package medusa.ware.ui.clickgui.util;

import medusa.ware.MedusaWare;

import java.awt.Color;

public class ColorUtil
{
    public static Color getClickGUIColor() {
        if (MedusaWare.instance.sm.getSettingByName(MedusaWare.instance.mm.getModuleByName("ClickGUI"), "Rainbow").getValBoolean()) {
            MedusaWare.instance.sm.getSettingByName(MedusaWare.instance.mm.getModuleByName("ClickGUI"), "Red").setValDoubleNoSave(MedusaWare.instance.cm.cc.getColor(0).getRed());
            MedusaWare.instance.sm.getSettingByName(MedusaWare.instance.mm.getModuleByName("ClickGUI"), "Green").setValDoubleNoSave(MedusaWare.instance.cm.cc.getColor(0).getGreen());
            MedusaWare.instance.sm.getSettingByName(MedusaWare.instance.mm.getModuleByName("ClickGUI"), "Blue").setValDoubleNoSave(MedusaWare.instance.cm.cc.getColor(0).getBlue());
        }
        return new Color((int) MedusaWare.instance.sm.getSettingByName(MedusaWare.instance.mm.getModuleByName("ClickGUI"), "Red").getValDouble(), (int) MedusaWare.instance.sm.getSettingByName(MedusaWare.instance.mm.getModuleByName("ClickGUI"), "Green").getValDouble(), (int) MedusaWare.instance.sm.getSettingByName(MedusaWare.instance.mm.getModuleByName("ClickGUI"), "Blue").getValDouble(), 255);
    }
}
