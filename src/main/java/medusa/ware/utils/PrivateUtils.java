// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.utils;

import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Timer;

public class PrivateUtils
{
    public static Timer timer() {
        try {
            final Field declaredField = Minecraft.class.getDeclaredField(new String(new char[] { 't', 'i', 'm', 'e', 'r' }));
            declaredField.setAccessible(true);
            return (Timer)declaredField.get(Game.Minecraft());
        }
        catch (Exception ex) {
            try {
                final Field declaredField2 = Minecraft.class.getDeclaredField(new String(new char[] { 'f', 'i', 'e', 'l', 'd', '_', '7', '1', '4', '2', '8', '_', 'T' }));
                declaredField2.setAccessible(true);
                return (Timer)declaredField2.get(Game.Minecraft());
            }
            catch (Exception ex2) {
                return null;
            }
        }
    }
    
    public static void setRightClickDelayTimer(final int n) {
        try {
            final Field declaredField = Minecraft.class.getDeclaredField("field_71467_ac");
            declaredField.setAccessible(true);
            declaredField.set(Game.Minecraft(), n);
        }
        catch (Exception ex) {
            try {
                final Field declaredField2 = Minecraft.class.getDeclaredField("rightClickDelayTimer");
                declaredField2.setAccessible(true);
                declaredField2.set(Game.Minecraft(), n);
            }
            catch (Exception ex2) {}
        }
    }
}
