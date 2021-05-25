// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.combat;

import java.util.List;
import medusa.ware.utils.CombatUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import io.netty.util.internal.ThreadLocalRandom;
import medusa.ware.utils.RotationUtils;
import medusa.ware.utils.Game;
import medusa.ware.settings.Setting;
import medusa.ware.MedusaWare;
import medusa.ware.module.Category;
import medusa.ware.module.Module;

public class RodAimbot extends Module
{
    public RodAimbot() {
        super("RodAimAssist", 0, Category.COMBAT, "Helps with aiming the rod");
    }
    
    @Override
    public void setup() {
        MedusaWare.instance.sm.rSetting(new Setting("HSpeed", this, 30.0, 1.0, 250.0, true));
        MedusaWare.instance.sm.rSetting(new Setting("VSpeed", this, 30.0, 1.0, 250.0, true));
        MedusaWare.instance.sm.rSetting(new Setting("Range", this, 8.0, 1.0, 17.5, false));
        MedusaWare.instance.sm.rSetting(new Setting("FOV", this, 100.0, 1.0, 360.0, true));
    }
    
    @Override
    public void onUpdate() {
        if (this.getTarget() != null) {
            this.Aim();
        }
    }
    
    private void Aim() {
        if (this.getTarget() == null) {
            return;
        }
        final int valInt = MedusaWare.instance.sm.getSettingByName(this, "HSpeed").getValInt();
        final int valInt2 = MedusaWare.instance.sm.getSettingByName(this, "VSpeed").getValInt();
        final Entity target = this.getTarget();
        if (target != null && (this.getY(target) > 1.0 || this.getY(target) < -1.0)) {
            final boolean b = this.getY(target) > 0.0;
            final EntityPlayerSP player;
            final EntityPlayerSP entityPlayerSP = player = Game.Player();
            player.rotationYaw += (float)(b ? (-(Math.abs(this.getY(target)) / valInt)) : (Math.abs(this.getY(target)) / valInt));
            final float n = RotationUtils.getRotations(target)[1] - Game.Player().getDistanceToEntity(this.getTarget()) + Game.Player().getDistanceToEntity(this.getTarget()) / 4.0f - Game.Player().rotationPitch;
            final EntityPlayerSP entityPlayerSP2 = entityPlayerSP;
            entityPlayerSP2.rotationPitch += (float)(n / (valInt2 + ThreadLocalRandom.current().nextDouble()));
        }
    }
    
    public Entity getTarget() {
        Entity entity = null;
        int n = (int) MedusaWare.instance.sm.getSettingByName(this, "FOV").getValDouble();
        if (Game.World().loadedEntityList == null || Game.Player().getCurrentEquippedItem() == null || !Game.Player().getCurrentEquippedItem().getItem().getUnlocalizedName().toLowerCase().contains("rod")) {
            return null;
        }
        final List loadedEntityList = Game.World().loadedEntityList;
        for (int i = 0; i < loadedEntityList.size(); ++i) {
            final Entity entity2 = (Entity) loadedEntityList.get(i);
            if (RotationUtils.canEntityBeSeen(entity2) && entity2.isEntityAlive() && entity2 != Game.Player() && Game.Player().getDistanceToEntity(entity2) <= MedusaWare.instance.sm.getSettingByName(this, "Range").getValDouble() && CombatUtils.canTarget(entity2)) {
                if (this.can(entity2, (float)n)) {
                    entity = entity2;
                    n = (int)this.getY(entity2);
                }
            }
        }
        return entity;
    }
    
    public boolean can(final Entity entity, float n) {
        n *= 0.5;
        final double n2 = ((Game.Player().rotationYaw - this.yaw(entity)) % 360.0 + 540.0) % 360.0 - 180.0;
        return (n2 > 0.0 && n2 < n) || (-n < n2 && n2 < 0.0);
    }
    
    public float yaw(final Entity entity) {
        final double y = entity.posX - Game.Player().posX;
        final double n = entity.posY - Game.Player().posY;
        final double x = entity.posZ - Game.Player().posZ;
        final double n2 = -(Math.atan2(y, x) * 57.2957795);
        final double n3 = -(Math.asin(n / Math.sqrt(y * y + n * n + x * x)) * 57.2957795);
        return (float)n2;
    }
    
    public double getY(final Entity entity) {
        return ((Game.Player().rotationYaw - this.yaw(entity)) % 360.0 + 540.0) % 360.0 - 180.0;
    }
}
