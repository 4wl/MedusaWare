// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.player;

import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFireball;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemBlock;
import medusa.ware.utils.Game;
import medusa.ware.settings.Setting;
import medusa.ware.MedusaWare;
import medusa.ware.module.Category;
import medusa.ware.module.Module;

public class FastPlace extends Module
{
    public FastPlace() {
        super("FastPlace", 0, Category.PLAYER, "Places blocks really fast");
    }
    
    @Override
    public void setup() {
        MedusaWare.instance.sm.rSetting(new Setting("Blocks", this, true));
        MedusaWare.instance.sm.rSetting(new Setting("Projectiles", this, false));
        MedusaWare.instance.sm.rSetting(new Setting("Other", this, false));
        MedusaWare.instance.sm.rSetting(new Setting("Speed", this, 0.0, 0.0, 3.0, true));
    }
    
    @Override
    public void onTick() {
        final boolean valBoolean = MedusaWare.instance.sm.getSettingByName(this, "Blocks").getValBoolean();
        final boolean valBoolean2 = MedusaWare.instance.sm.getSettingByName(this, "Projectiles").getValBoolean();
        final boolean valBoolean3 = MedusaWare.instance.sm.getSettingByName(this, "Other").getValBoolean();
        final int valInt = MedusaWare.instance.sm.getSettingByName(this, "Speed").getValInt();
        if (Game.Player().inventory.getCurrentItem() != null) {
            final Item item = Game.Player().inventory.getCurrentItem().getItem();
            if ((item instanceof ItemBlock && valBoolean) || ((item instanceof ItemSnowball || item instanceof ItemEgg || item instanceof ItemFireball) && valBoolean2)) {
                this.setFastPlace(valInt);
            }
            else if (valBoolean3 && (!(item instanceof ItemBlock) || !valBoolean) && (item instanceof ItemSnowball || item instanceof ItemEgg || item instanceof ItemFireball) && valBoolean2) {
                this.setFastPlace(valInt);
            }
            else if ((!(item instanceof ItemBlock) || !valBoolean) && (item instanceof ItemSnowball || item instanceof ItemEgg || item instanceof ItemFireball) && valBoolean2) {
                this.setFastPlace(4);
            }
        }
        else if (Game.Player().inventory.getCurrentItem() == null) {
            this.setFastPlace(4);
        }
    }
    
    @Override
    public void onDisable() {
        this.setFastPlace(4);
        super.onDisable();
    }
    
    public void setFastPlace(final int n) {
        try {
            final Field declaredField = Minecraft.class.getDeclaredField("field_71467_ac");
            declaredField.setAccessible(true);
            declaredField.set(FastPlace.mc, n);
        }
        catch (Exception ex) {
            try {
                final Field declaredField2 = Minecraft.class.getDeclaredField("rightClickDelayTimer");
                declaredField2.setAccessible(true);
                declaredField2.set(FastPlace.mc, n);
            }
            catch (Exception ex2) {}
        }
    }
}
