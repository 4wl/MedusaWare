// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.combat;

import medusa.ware.utils.CombatUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import medusa.ware.utils.Game;
import io.netty.util.internal.ThreadLocalRandom;
import medusa.ware.settings.Setting;
import medusa.ware.MedusaWare;
import medusa.ware.module.Category;
import medusa.ware.utils.TimerUtils;
import medusa.ware.module.Module;

public class Killaura extends Module
{
    private TimerUtils timer;
    
    public Killaura() {
        super("Killaura", 0, Category.COMBAT, "Automatically hits entities around you");
        this.timer = new TimerUtils();
    }
    
    @Override
    public void setup() {
        MedusaWare.instance.sm.rSetting(new Setting("APS", this, 8.0, 1.0, 20.0, false));
        MedusaWare.instance.sm.rSetting(new Setting("Reach", this, 3.0, 1.0, 6.0, false));
    }
    
    @Override
    public void onUpdate() {
        final EntityLivingBase target = this.getTarget(MedusaWare.instance.sm.getSettingByName(this, "Reach").getValDouble());
        if (target != null && Killaura.mc.objectMouseOver != null) {
            Killaura.mc.objectMouseOver.entityHit = (Entity)target;
        }
        if (target != null && this.timer.hasReached(1000.0 / (MedusaWare.instance.sm.getSettingByName(this, "APS").getValDouble() + ThreadLocalRandom.current().nextDouble()))) {
            Game.Player().swingItem();
            Killaura.mc.playerController.attackEntity((EntityPlayer)Game.Player(), (Entity)target);
            this.timer.reset();
        }
    }
    
    public EntityLivingBase getTarget(final double n) {
        EntityLivingBase entityLivingBase = null;
        double n2 = 100.0;
        for (final Entity entity : Game.World().loadedEntityList) {
            final double n3 = Game.Player().getDistanceToEntity(entity);
            if (n3 < n && entity != Game.Player() && CombatUtils.canTarget(entity) && n2 > n3) {
                entityLivingBase = (EntityLivingBase)entity;
                n2 = n3;
            }
        }
        return entityLivingBase;
    }
}
