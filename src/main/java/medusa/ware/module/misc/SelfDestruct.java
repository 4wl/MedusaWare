// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.misc;

import medusa.ware.MedusaWare;
import medusa.ware.module.Category;
import medusa.ware.module.Module;

public class SelfDestruct extends Module
{
    public SelfDestruct() {
        super("SelfDestruct", 0, Category.MISC, "BYE BYE");
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        MedusaWare.instance.onSelfDestruct();
        this.setToggled(false);
    }
    
    @Override
    public void onTick() {
        MedusaWare.instance.onSelfDestruct();
        this.setToggled(false);
    }
}
