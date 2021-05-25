// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.combat;

import net.minecraft.util.AxisAlignedBB;
import java.util.List;
import net.minecraft.util.Vec3;
import net.minecraft.entity.item.EntityItemFrame;
import medusa.ware.utils.RotationUtils;
import medusa.ware.utils.CombatUtils;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import medusa.ware.utils.Game;
import net.minecraftforge.client.event.MouseEvent;
import medusa.ware.settings.Setting;
import medusa.ware.MedusaWare;
import medusa.ware.module.Category;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.entity.Entity;
import medusa.ware.module.Module;

public class HitBoxes extends Module
{
    private Entity pointedEntity;
    private MovingObjectPosition moving;
    public static float hitBoxMultiplier;
    
    public HitBoxes() {
        super("Hitbox", 0, Category.COMBAT, "Expands entities hitboxes");
    }
    
    @Override
    public void setup() {
        MedusaWare.instance.sm.rSetting(new Setting("Distance", this, 30.0, 1.0, 180.0, true));
    }
    
    @SubscribeEvent
    public void mouse(final MouseEvent mouseEvent) {
        if (this.moving != null && mouseEvent.button == 0 && (HitBoxes.mc.objectMouseOver.getBlockPos() == null || Game.World().isAirBlock(HitBoxes.mc.objectMouseOver.getBlockPos()))) {
            HitBoxes.mc.objectMouseOver = this.moving;
        }
    }
    
    public void onClick() {
    }
    
    @SubscribeEvent
    public void tick(final TickEvent.RenderTickEvent renderTickEvent) {
        if (Game.World() != null) {
            HitBoxes.hitBoxMultiplier = (float) MedusaWare.instance.sm.getSettingByName(this, "Distance").getValDouble();
        }
        this.getMouseOver(1.0f);
    }
    
    @Override
    public void onUpdate() {
        if (Game.World() != null) {
            HitBoxes.hitBoxMultiplier = (float) MedusaWare.instance.sm.getSettingByName(this, "Distance").getValDouble();
        }
        this.getMouseOver(1.0f);
    }
    
    private void getMouseOver(final float n) {
        if (HitBoxes.mc.getRenderViewEntity() != null && Game.World() != null) {
            HitBoxes.mc.pointedEntity = null;
            double nextDouble = 3.0;
            if (MedusaWare.instance.mm.getModuleByName("Reach").isToggled()) {
                final double valDouble = MedusaWare.instance.sm.getSettingByName(MedusaWare.instance.mm.getModuleByName("Reach"), "MinReach").getValDouble();
                final double valDouble2 = MedusaWare.instance.sm.getSettingByName(MedusaWare.instance.mm.getModuleByName("Reach"), "MaxReach").getValDouble();
                if (valDouble > valDouble2 || valDouble == valDouble2) {
                    nextDouble = valDouble;
                }
                else {
                    nextDouble = ThreadLocalRandom.current().nextDouble(valDouble, valDouble2);
                }
            }
            this.moving = HitBoxes.mc.getRenderViewEntity().rayTrace(nextDouble, n);
            double distanceTo = nextDouble;
            final Vec3 positionEyes = HitBoxes.mc.getRenderViewEntity().getPositionEyes(n);
            if (this.moving != null) {
                distanceTo = this.moving.hitVec.distanceTo(positionEyes);
            }
            final Vec3 look = HitBoxes.mc.getRenderViewEntity().getLook(n);
            final Vec3 addVector = positionEyes.addVector(look.xCoord * nextDouble, look.yCoord * nextDouble, look.zCoord * nextDouble);
            this.pointedEntity = null;
            Vec3 vec3 = null;
            final float n2 = 1.0f;
            final List entitiesWithinAABBExcludingEntity = Game.World().getEntitiesWithinAABBExcludingEntity(HitBoxes.mc.getRenderViewEntity(), HitBoxes.mc.getRenderViewEntity().getEntityBoundingBox().addCoord(look.xCoord * nextDouble, look.yCoord * nextDouble, look.zCoord * nextDouble).expand((double)n2, (double)n2, (double)n2));
            double n3 = distanceTo;
            for (int i = 0; i < entitiesWithinAABBExcludingEntity.size(); ++i) {
                final Entity pointedEntity = (Entity) entitiesWithinAABBExcludingEntity.get(i);
                if (pointedEntity.canBeCollidedWith() && CombatUtils.canTarget(pointedEntity) && RotationUtils.canEntityBeSeen(pointedEntity)) {
                    final float n4 = 0.13f * HitBoxes.hitBoxMultiplier;
                    final AxisAlignedBB expand = pointedEntity.getEntityBoundingBox().expand((double)n4, (double)n4, (double)n4);
                    final MovingObjectPosition calculateIntercept = expand.calculateIntercept(positionEyes, addVector);
                    if (expand.isVecInside(positionEyes)) {
                        if (0.0 < n3 || n3 == 0.0) {
                            this.pointedEntity = pointedEntity;
                            vec3 = ((calculateIntercept == null) ? positionEyes : calculateIntercept.hitVec);
                            n3 = 0.0;
                        }
                    }
                    else if (calculateIntercept != null) {
                        final double distanceTo2 = positionEyes.distanceTo(calculateIntercept.hitVec);
                        if (distanceTo2 < n3 || n3 == 0.0) {
                            if (pointedEntity == HitBoxes.mc.getRenderViewEntity().ridingEntity && !pointedEntity.canRiderInteract()) {
                                if (n3 == 0.0) {
                                    this.pointedEntity = pointedEntity;
                                    vec3 = calculateIntercept.hitVec;
                                }
                            }
                            else {
                                this.pointedEntity = pointedEntity;
                                vec3 = calculateIntercept.hitVec;
                                n3 = distanceTo2;
                            }
                        }
                    }
                }
            }
            if (this.pointedEntity != null && (n3 < distanceTo || this.moving == null)) {
                this.moving = new MovingObjectPosition(this.pointedEntity, vec3);
                if (CombatUtils.canTarget(this.pointedEntity) || this.pointedEntity instanceof EntityItemFrame) {
                    HitBoxes.mc.pointedEntity = this.pointedEntity;
                }
            }
        }
    }
    
    static {
        HitBoxes.hitBoxMultiplier = 1.0f;
    }
}
