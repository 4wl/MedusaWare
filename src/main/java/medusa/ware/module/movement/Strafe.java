// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.movement;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import medusa.ware.utils.PrivateUtils;
import medusa.ware.utils.MoveUtils;
import medusa.ware.utils.Game;
import net.minecraftforge.event.entity.living.LivingEvent;
import medusa.ware.settings.Setting;
import medusa.ware.MedusaWare;
import java.util.ArrayList;
import medusa.ware.module.Category;
import medusa.ware.module.Module;

public class Strafe extends Module
{
    public Strafe() {
        super("Strafe", 0, Category.MOVEMENT, "Strafe faster");
    }
    
    @Override
    public void setup() {
        final ArrayList<String> list;
        (list = new ArrayList<String>()).add("Timer");
        list.add("Motion");
        MedusaWare.instance.sm.rSetting(new Setting("Mode", this, "Timer", list));
        MedusaWare.instance.sm.rSetting(new Setting("Speed", this, 1.08, 0.1, 3.0, false));
    }
    
    @SubscribeEvent
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent livingUpdateEvent) {
        final double valDouble = MedusaWare.instance.sm.getSettingByName(this, "Speed").getValDouble();
        final String valString = MedusaWare.instance.sm.getSettingByName(this, "Mode").getValString();
        if (livingUpdateEvent.entityLiving != null && livingUpdateEvent.entityLiving == Game.Player()) {
            if (valString.equalsIgnoreCase("Motion")) {
                if (Game.Player().moveStrafing != 0.0f && Game.Player().onGround && Game.Player().ticksExisted % 2 == 0) {
                    MoveUtils.setMoveSpeed(valDouble / 3.0);
                }
            }
            else if (valString.equalsIgnoreCase("Timer")) {
                if (Game.Player().moveStrafing != 0.0f && Game.Player().onGround) {
                    PrivateUtils.timer().timerSpeed = (float)valDouble;
                }
                else if (!MedusaWare.instance.mm.getModuleByName("Timer").isToggled()) {
                    PrivateUtils.timer().timerSpeed = 1.0f;
                }
            }
        }
    }
}
