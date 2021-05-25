// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.values;

import medusa.ware.module.Category;
import medusa.ware.module.Module;

public class ValuePlayers extends Module
{
    public ValuePlayers() {
        super("Players", 0, Category.VALUES, "Target players");
        this.setToggled(true);
    }
}
