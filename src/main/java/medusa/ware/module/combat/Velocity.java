// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.combat;

import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import io.netty.util.internal.ThreadLocalRandom;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import medusa.ware.utils.Game;
import net.minecraftforge.event.entity.living.LivingEvent;
import medusa.ware.settings.Setting;
import medusa.ware.MedusaWare;
import medusa.ware.module.Category;
import medusa.ware.module.Module;

public class Velocity extends Module
{
    public Velocity() {
        super("Velocity", 0, Category.COMBAT, "Reduces your knockback");
    }
    
    @Override
    public void setup() {
        MedusaWare.instance.sm.rSetting(new Setting("Horizontal", this, 90.0, 0.0, 100.0, true, true));
        MedusaWare.instance.sm.rSetting(new Setting("Vertical", this, 100.0, 0.0, 100.0, true, true));
        MedusaWare.instance.sm.rSetting(new Setting("Chance", this, 60.0, 0.0, 100.0, true, true));
        MedusaWare.instance.sm.rSetting(new Setting("SprintOnly", this, true));
        MedusaWare.instance.sm.rSetting(new Setting("TargetingOnly", this, true));
        MedusaWare.instance.sm.rSetting(new Setting("WeaponOnly", this, false));
        MedusaWare.instance.sm.rSetting(new Setting("NoLiquid", this, false));
    }
    
    @SubscribeEvent
    public void onEv(final LivingEvent.LivingUpdateEvent livingUpdateEvent) {
        final float n = (float) MedusaWare.instance.sm.getSettingByName(this, "Horizontal").getValDouble();
        final float n2 = (float) MedusaWare.instance.sm.getSettingByName(this, "Vertical").getValDouble();
        if (this.canVelocity() && Game.Player().hurtTime == Game.Player().maxHurtTime && Game.Player().maxHurtTime > 0) {
            final EntityPlayerSP player = Game.Player();
            player.motionX *= n / 100.0;
            final EntityPlayerSP player2 = Game.Player();
            player2.motionY *= n2 / 100.0;
            final EntityPlayerSP player3 = Game.Player();
            player3.motionZ *= n / 100.0;
        }
    }
    
    public boolean canVelocity() {
        final int n = 100 - (int) MedusaWare.instance.sm.getSettingByName(this, "Chance").getValDouble();
        final boolean valBoolean = MedusaWare.instance.sm.getSettingByName(this, "SprintOnly").getValBoolean();
        final boolean valBoolean2 = MedusaWare.instance.sm.getSettingByName(this, "TargetingOnly").getValBoolean();
        final boolean valBoolean3 = MedusaWare.instance.sm.getSettingByName(this, "WeaponOnly").getValBoolean();
        final boolean valBoolean4 = MedusaWare.instance.sm.getSettingByName(this, "NoLiquid").getValBoolean();
        if (Game.World() == null || Game.Player() == null || (valBoolean && !Game.Player().isSprinting()) || n >= ThreadLocalRandom.current().nextInt(0, 101)) {
            return false;
        }
        if (valBoolean2 && Velocity.mc.objectMouseOver != null && Velocity.mc.objectMouseOver.entityHit == null) {
            return false;
        }
        if (valBoolean3) {
            if (Game.Player().getCurrentEquippedItem() == null) {
                return false;
            }
            if (!(Game.Player().getCurrentEquippedItem().getItem() instanceof ItemSword) && !(Game.Player().getCurrentEquippedItem().getItem() instanceof ItemAxe)) {
                return false;
            }
        }
        return (!Game.Player().isInWater() && !Game.Player().isInLava()) || !valBoolean4;
    }
}
