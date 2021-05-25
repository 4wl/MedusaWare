// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.utils;

import medusa.ware.module.Category;
import medusa.ware.module.Module;
import medusa.ware.MedusaWare;

public class ModuleUtils
{
    public static void setAllModulesToggled(final boolean toggled) {
        for (final Module module : MedusaWare.instance.mm.getModules()) {
            if (!module.getName().equalsIgnoreCase("HUD") && !module.getCategory().equals(Category.VALUES)) {
                module.setToggled(toggled);
            }
        }
    }
}
