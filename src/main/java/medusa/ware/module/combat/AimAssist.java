// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.combat;

import net.minecraft.util.MathHelper;
import net.minecraft.entity.EntityLivingBase;
import medusa.ware.utils.CombatUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import io.netty.util.internal.ThreadLocalRandom;
import medusa.ware.utils.RotationUtils;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import medusa.ware.utils.Game;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import medusa.ware.settings.Setting;
import medusa.ware.MedusaWare;
import medusa.ware.module.Category;
import medusa.ware.module.Module;

public class AimAssist extends Module
{
    float a;
    float b;
    float c;
    float h;
    boolean d;
    boolean e;
    boolean f;
    boolean g;
    boolean i;
    boolean j;
    
    public AimAssist() {
        super("AimAssist", 0, Category.COMBAT, "Helps with aiming");
    }
    
    @Override
    public void setup() {
        MedusaWare.instance.sm.rSetting(new Setting("HSpeed", this, 50.0, 10.0, 250.0, true));
        MedusaWare.instance.sm.rSetting(new Setting("VSpeed", this, 50.0, 10.0, 250.0, true));
        MedusaWare.instance.sm.rSetting(new Setting("FOV", this, 90.0, 15.0, 360.0, true));
        MedusaWare.instance.sm.rSetting(new Setting("Reach", this, 4.2, 1.0, 10.0, false));
        MedusaWare.instance.sm.rSetting(new Setting("ClickAim", this, true));
        MedusaWare.instance.sm.rSetting(new Setting("BreakBlocks", this, true));
        MedusaWare.instance.sm.rSetting(new Setting("VerticalAim", this, true));
        MedusaWare.instance.sm.rSetting(new Setting("WeaponOnly", this, false));
        MedusaWare.instance.sm.rSetting(new Setting("Blatant", this, false));
    }
    
    @Override
    public void onUpdateNoToggle() {
        this.a = (float) MedusaWare.instance.sm.getSettingByName(this, "HSpeed").getValDouble();
        this.h = (float) MedusaWare.instance.sm.getSettingByName(this, "VSpeed").getValDouble();
        this.b = (float) MedusaWare.instance.sm.getSettingByName(this, "FOV").getValDouble();
        this.c = (float) MedusaWare.instance.sm.getSettingByName(this, "Reach").getValDouble();
        this.d = MedusaWare.instance.sm.getSettingByName(this, "ClickAim").getValBoolean();
        this.e = MedusaWare.instance.sm.getSettingByName(this, "WeaponOnly").getValBoolean();
        this.g = MedusaWare.instance.sm.getSettingByName(this, "Blatant").getValBoolean();
        this.i = MedusaWare.instance.sm.getSettingByName(this, "VerticalAim").getValBoolean();
        this.j = MedusaWare.instance.sm.getSettingByName(this, "BreakBlocks").getValBoolean();
        if (!this.i) {
            MedusaWare.instance.sm.getSettingByName(this, "VSpeed").setVisible(false);
        }
        else if (!this.g) {
            MedusaWare.instance.sm.getSettingByName(this, "VSpeed").setVisible(true);
            MedusaWare.instance.sm.getSettingByName(this, "HSpeed").setVisible(true);
            MedusaWare.instance.sm.getSettingByName(this, "FOV").setVisible(true);
        }
        else {
            MedusaWare.instance.sm.getSettingByName(this, "VSpeed").setVisible(false);
            MedusaWare.instance.sm.getSettingByName(this, "HSpeed").setVisible(false);
            MedusaWare.instance.sm.getSettingByName(this, "FOV").setVisible(false);
        }
    }
    
    private boolean isKeyDown(final int n) {
        return (n < 0) ? Mouse.isButtonDown(n + 100) : Keyboard.isKeyDown(n);
    }
    
    @Override
    public void onUpdate() {
        if (AimAssist.mc.currentScreen != null || (this.j && this.isKeyDown(AimAssist.mc.gameSettings.keyBindAttack.getKeyCode()) && AimAssist.mc.objectMouseOver != null && AimAssist.mc.objectMouseOver.getBlockPos() != null && !AimAssist.mc.theWorld.isAirBlock(AimAssist.mc.objectMouseOver.getBlockPos()))) {
            return;
        }
        if (this.e) {
            if (Game.Player().getCurrentEquippedItem() == null) {
                return;
            }
            if (!(Game.Player().getCurrentEquippedItem().getItem() instanceof ItemSword) && !(Game.Player().getCurrentEquippedItem().getItem() instanceof ItemAxe)) {
                return;
            }
        }
        if (this.d && !Mouse.isButtonDown(0)) {
            return;
        }
        final Entity ent = this.ent();
        if (!this.g) {
            if (ent != null && (getY(ent) > 1.0 || getY(ent) < -1.0)) {
                final boolean b = getY(ent) > 0.0;
                final EntityPlayerSP player;
                final EntityPlayerSP entityPlayerSP = player = Game.Player();
                player.rotationYaw += (float)(b ? (-(Math.abs(getY(ent)) / this.a)) : (Math.abs(getY(ent)) / this.a));
                final float[] rotations = RotationUtils.getRotations(ent);
                if (rotations[1] < -2.0f || (rotations[1] > 2.0f && this.i)) {
                    final float n = rotations[1] - entityPlayerSP.rotationPitch;
                    final EntityPlayerSP entityPlayerSP2 = entityPlayerSP;
                    entityPlayerSP2.rotationPitch += (float)(n / (this.h + ThreadLocalRandom.current().nextDouble()));
                }
            }
        }
        else {
            blatant(ent);
        }
    }
    
    public Entity ent() {
        Entity entity = null;
        int n = (int)this.b;
        for (final Entity entity2 : Game.World().loadedEntityList) {
            if (entity2.isEntityAlive() && entity2 != Game.Player() && Game.Player().getDistanceToEntity(entity2) <= this.c && CombatUtils.canTarget(entity2)) {
                if (!this.g) {
                    if (!can(entity2, (float)n)) {
                        continue;
                    }
                    entity = entity2;
                    n = (int)getY(entity2);
                }
                else {
                    entity = entity2;
                    n = (int)getY(entity2);
                }
            }
        }
        return entity;
    }
    
    public static float yaw(final Entity entity) {
        final double y = entity.posX - Game.Player().posX;
        final double n = entity.posY - Game.Player().posY;
        final double x = entity.posZ - Game.Player().posZ;
        final double n2 = -(Math.atan2(y, x) * 57.2957795);
        final double n3 = -(Math.asin(n / Math.sqrt(y * y + n * n + x * x)) * 57.2957795);
        return (float)n2;
    }
    
    public static double getY(final Entity entity) {
        return ((Game.Player().rotationYaw - yaw(entity)) % 360.0 + 540.0) % 360.0 - 180.0;
    }
    
    public static boolean can(final Entity entity, float n) {
        n *= 0.5;
        final double n2 = ((Game.Player().rotationYaw - yaw(entity)) % 360.0 + 540.0) % 360.0 - 180.0;
        return (n2 > 0.0 && n2 < n) || (-n < n2 && n2 < 0.0);
    }
    
    public static float[] rots(final Entity entity) {
        if (entity == null) {
            return null;
        }
        final double x = entity.posX - Game.Player().posX;
        double y;
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            y = entityLivingBase.posY + entityLivingBase.getEyeHeight() * 0.9 - (Game.Player().posY + Game.Player().getEyeHeight());
        }
        else {
            y = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().minY) / 2.0 - (Game.Player().posY + Game.Player().getEyeHeight());
        }
        final double y2 = entity.posZ - Game.Player().posZ;
        return new float[] { Game.Player().rotationYaw + MathHelper.wrapAngleTo180_float((float)(Math.atan2(y2, x) * 180.0 / 3.141592653589793) - 90.0f - Game.Player().rotationYaw), Game.Player().rotationPitch + MathHelper.wrapAngleTo180_float((float)(-(Math.atan2(y, MathHelper.sqrt_double(x * x + y2 * y2)) * 180.0 / 3.141592653589793)) - Game.Player().rotationPitch) };
    }
    
    public static void blatant(final Entity entity) {
        final float[] rots = rots(entity);
        if (rots != null) {
            Game.Player().rotationYaw = rots[0];
            Game.Player().rotationPitch = rots[1] + 4.0f;
        }
    }
}
