// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.combat;

import net.minecraft.util.AxisAlignedBB;
import medusa.ware.utils.CombatUtils;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import medusa.ware.utils.Game;
import net.minecraftforge.client.event.MouseEvent;
import medusa.ware.settings.Setting;
import medusa.ware.MedusaWare;
import java.util.ArrayList;
import medusa.ware.module.Category;
import net.minecraft.util.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import java.util.List;
import java.util.Random;
import medusa.ware.module.Module;

public class Reach extends Module
{
    float ab;
    float bb;
    boolean cb;
    boolean d;
    int timeout;
    int hits;
    boolean misplace;
    public Random e;
    private List<EntityPlayer> Players;
    private List<BlockPos> oldPos;
    private List<BlockPos> currentPos;
    
    public Reach() {
        super("Reach", 0, Category.COMBAT, "Increases your reach distance");
        this.e = new Random();
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (this.Players == null) {
            (this.Players = new ArrayList<EntityPlayer>()).clear();
        }
        else {
            this.Players.clear();
        }
        if (this.oldPos == null) {
            (this.oldPos = new ArrayList<BlockPos>()).clear();
        }
        else {
            this.oldPos.clear();
        }
        if (this.currentPos == null) {
            (this.currentPos = new ArrayList<BlockPos>()).clear();
        }
        else {
            this.currentPos.clear();
        }
    }
    
    @Override
    public void setup() {
        MedusaWare.instance.sm.rSetting(new Setting("MinReach", this, 3.0, 3.0, 6.0, false));
        MedusaWare.instance.sm.rSetting(new Setting("MaxReach", this, 3.8, 3.0, 6.0, false));
        MedusaWare.instance.sm.rSetting(new Setting("TimeoutHits", this, 0.0, 0.0, 20.0, true));
        MedusaWare.instance.sm.rSetting(new Setting("Misplace", this, false));
        MedusaWare.instance.sm.rSetting(new Setting("WeaponOnly", this, false));
        MedusaWare.instance.sm.rSetting(new Setting("SprintOnly", this, true));
        MedusaWare.instance.sm.rSetting(new Setting("GroundOnly", this, false));
    }
    
    @Override
    public void onUpdateNoToggle() {
        this.misplace = MedusaWare.instance.sm.getSettingByName(this, "Misplace").getValBoolean();
        if (this.misplace) {
            MedusaWare.instance.sm.getSettingByName(this, "TimeoutHits").setVisible(false);
            MedusaWare.instance.sm.getSettingByName(this, "WeaponOnly").setVisible(false);
            MedusaWare.instance.sm.getSettingByName(this, "SprintOnly").setVisible(false);
            MedusaWare.instance.sm.getSettingByName(this, "GroundOnly").setVisible(false);
        }
        else {
            MedusaWare.instance.sm.getSettingByName(this, "TimeoutHits").setVisible(true);
            MedusaWare.instance.sm.getSettingByName(this, "WeaponOnly").setVisible(true);
            MedusaWare.instance.sm.getSettingByName(this, "SprintOnly").setVisible(true);
            MedusaWare.instance.sm.getSettingByName(this, "GroundOnly").setVisible(true);
        }
    }
    
    @SubscribeEvent
    public void mous(final MouseEvent mouseEvent) {
        final MedusaWare instance = MedusaWare.instance;
        if (MedusaWare.destructed) {
            return;
        }
        this.ab = (float) MedusaWare.instance.sm.getSettingByName(this, "MinReach").getValDouble();
        this.bb = (float) MedusaWare.instance.sm.getSettingByName(this, "MaxReach").getValDouble();
        this.cb = MedusaWare.instance.sm.getSettingByName(this, "WeaponOnly").getValBoolean();
        this.timeout = (int) MedusaWare.instance.sm.getSettingByName(this, "TimeoutHits").getValDouble();
        this.misplace = MedusaWare.instance.sm.getSettingByName(this, "Misplace").getValBoolean();
        this.d = false;
        if (!this.canReach()) {
            return;
        }
        if (Reach.mc.objectMouseOver != null) {
            final BlockPos blockPos = Reach.mc.objectMouseOver.getBlockPos();
            if (blockPos != null && Game.World().getBlockState(blockPos).getBlock() != Blocks.air) {
                return;
            }
        }
        final Object[] add = add(this.ab + this.e.nextDouble() * (this.bb - this.ab), 0.0, 0.0f);
        if (add == null) {
            return;
        }
        Reach.mc.objectMouseOver = new MovingObjectPosition((Entity)add[0], (Vec3)add[1]);
        Reach.mc.pointedEntity = (Entity)add[0];
    }
    
    public boolean canReach() {
        final boolean valBoolean = MedusaWare.instance.sm.getSettingByName(this, "SprintOnly").getValBoolean();
        if (MedusaWare.instance.sm.getSettingByName(this, "GroundOnly").getValBoolean() && !Game.Player().onGround) {
            return false;
        }
        if (valBoolean && !Game.Player().isSprinting()) {
            return false;
        }
        if (this.misplace) {
            return false;
        }
        if (this.cb) {
            if (Game.Player().getCurrentEquippedItem() == null) {
                return false;
            }
            if (!(Game.Player().getCurrentEquippedItem().getItem() instanceof ItemSword) && !(Game.Player().getCurrentEquippedItem().getItem() instanceof ItemAxe)) {
                return false;
            }
        }
        return this.hits >= this.timeout;
    }
    
    @SubscribeEvent
    public void onAttack(final LivingAttackEvent livingAttackEvent) {
        if (livingAttackEvent.entityLiving != null && livingAttackEvent.source.getEntity() != null && livingAttackEvent.source.getEntity() == Reach.mc.thePlayer && livingAttackEvent.ammount > 0.0f && CombatUtils.canTarget((Entity)livingAttackEvent.entityLiving)) {
            if (Reach.mc.thePlayer.getDistanceToEntity((Entity)livingAttackEvent.entityLiving) >= Math.min(this.ab, this.bb)) {
                if (this.hits < this.timeout) {
                    ++this.hits;
                }
                else if (this.hits >= this.timeout) {
                    this.hits = 0;
                }
            }
            else {
                ++this.hits;
            }
        }
    }
    
    public static Object[] add(final double n, final double n2, final float n3) {
        final Entity renderViewEntity = Reach.mc.getRenderViewEntity();
        Entity entity = null;
        if (renderViewEntity == null || Game.World() == null) {
            return null;
        }
        Reach.mc.mcProfiler.startSection("pick");
        final Vec3 positionEyes = renderViewEntity.getPositionEyes(0.0f);
        final Vec3 look = renderViewEntity.getLook(0.0f);
        final Vec3 addVector = positionEyes.addVector(look.xCoord * n, look.yCoord * n, look.zCoord * n);
        Object o = null;
        final List entitiesWithinAABBExcludingEntity = Game.World().getEntitiesWithinAABBExcludingEntity(renderViewEntity, renderViewEntity.getEntityBoundingBox().addCoord(look.xCoord * n, look.yCoord * n, look.zCoord * n).expand(1.0, 1.0, 1.0));
        double n4 = n;
        for (int i = 0; i < entitiesWithinAABBExcludingEntity.size(); ++i) {
            final Entity entity2 = (Entity) entitiesWithinAABBExcludingEntity.get(i);
            if (entity2.canBeCollidedWith()) {
                final float collisionBorderSize = entity2.getCollisionBorderSize();
                final AxisAlignedBB expand = entity2.getEntityBoundingBox().expand((double)collisionBorderSize, (double)collisionBorderSize, (double)collisionBorderSize).expand(n2, n2, n2);
                final MovingObjectPosition calculateIntercept = expand.calculateIntercept(positionEyes, addVector);
                if (expand.isVecInside(positionEyes)) {
                    if (0.0 < n4 || n4 == 0.0) {
                        entity = entity2;
                        o = ((calculateIntercept == null) ? positionEyes : calculateIntercept.hitVec);
                        n4 = 0.0;
                    }
                }
                else if (calculateIntercept != null) {
                    final double distanceTo = positionEyes.distanceTo(calculateIntercept.hitVec);
                    if (distanceTo < n4 || n4 == 0.0) {
                        if (entity2 == renderViewEntity.ridingEntity) {
                            if (n4 == 0.0) {
                                entity = entity2;
                                o = calculateIntercept.hitVec;
                            }
                        }
                        else {
                            entity = entity2;
                            o = calculateIntercept.hitVec;
                            n4 = distanceTo;
                        }
                    }
                }
            }
        }
        if (n4 < n && !CombatUtils.canTarget(entity)) {
            entity = null;
        }
        Reach.mc.mcProfiler.endSection();
        if (entity == null || o == null) {
            return null;
        }
        return new Object[] { entity, o };
    }
}
