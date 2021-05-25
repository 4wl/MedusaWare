
package medusa.ware.utils;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;

public class RotationUtils
{
    public static float getDistanceBetweenAngles(final float n, final float n2) {
        float n3;
        for (n3 = n - n2; n3 < -180.0f; n3 += 360.0f) {}
        while (n3 > 180.0f) {
            n3 -= 360.0f;
        }
        return n3;
    }
    
    public static float[] getRotations(final Entity entity) {
        if (entity == null) {
            return null;
        }
        final double x = entity.posX - Game.Player().posX;
        final double y = entity.posZ - Game.Player().posZ;
        final EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
        return new float[] { (float)(Math.atan2(y, x) * 180.0 / 3.141592653589793) - 90.0f, (float)(-(Math.atan2(entityLivingBase.posY + (entityLivingBase.getEyeHeight() - 0.4) - (Game.Player().posY + Game.Player().getEyeHeight()), MathHelper.sqrt_double(x * x + y * y)) * 180.0 / 3.141592653589793)) };
    }
    
    public static float getFovDistanceToEntity(final Entity entity) {
        float rotationYaw;
        for (rotationYaw = Game.Player().rotationYaw; rotationYaw > 360.0f; rotationYaw -= 360.0f) {}
        while (rotationYaw < -360.0f) {
            rotationYaw += 360.0f;
        }
        return getDistanceBetweenAngles(rotationYaw, getRotations(entity)[0]);
    }
    
    public static boolean canEntityBeSeen(final Entity entity) {
        final Vec3 vec3 = new Vec3(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight(), Minecraft.getMinecraft().thePlayer.posZ);
        final AxisAlignedBB entityBoundingBox = entity.getEntityBoundingBox();
        final Vec3 vec4 = new Vec3(entity.posX, entity.posY + entity.getEyeHeight() / 1.32f, entity.posZ);
        final double n = entity.posX - 0.25;
        final double n2 = entity.posX + 0.25;
        final double posY = entity.posY;
        final double n3 = entity.posY + Math.abs(entity.posY - entityBoundingBox.maxY);
        final double n4 = entity.posZ - 0.25;
        final double n5 = entity.posZ + 0.25;
        return Minecraft.getMinecraft().theWorld.rayTraceBlocks(vec3, vec4) == null || Minecraft.getMinecraft().theWorld.rayTraceBlocks(vec3, new Vec3(n2, posY, n4)) == null || Minecraft.getMinecraft().theWorld.rayTraceBlocks(vec3, new Vec3(n, posY, n4)) == null || Minecraft.getMinecraft().theWorld.rayTraceBlocks(vec3, new Vec3(n, posY, n5)) == null || Minecraft.getMinecraft().theWorld.rayTraceBlocks(vec3, new Vec3(n2, posY, n5)) == null || Minecraft.getMinecraft().theWorld.rayTraceBlocks(vec3, new Vec3(n2, n3, n4)) == null || Minecraft.getMinecraft().theWorld.rayTraceBlocks(vec3, new Vec3(n, n3, n4)) == null || Minecraft.getMinecraft().theWorld.rayTraceBlocks(vec3, new Vec3(n, n3, n5 - 0.1)) == null || Minecraft.getMinecraft().theWorld.rayTraceBlocks(vec3, new Vec3(n2, n3, n5)) == null;
    }
}
