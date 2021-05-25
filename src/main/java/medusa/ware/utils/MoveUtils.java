

package medusa.ware.utils;

import medusa.ware.MedusaWare;
import net.minecraft.entity.Entity;
import net.minecraft.potion.Potion;

public class MoveUtils
{
    public static boolean PlayerMoving() {
        return Game.Player().movementInput.moveForward != 0.0f || Game.Player().movementInput.moveStrafe != 0.0f;
    }
    
    public static void setMoveSpeed(final double n) {
        double n2 = Game.Player().movementInput.moveForward;
        double n3 = Game.Player().movementInput.moveStrafe;
        double n4 = Game.Player().rotationYaw;
        if (n2 != 0.0 || n3 != 0.0) {
            if (n2 != 0.0) {
                if (n3 > 0.0) {
                    n4 += ((n2 > 0.0) ? -45.0 : 45.0);
                }
                else if (n3 < 0.0) {
                    n4 += ((n2 > 0.0) ? 45.0 : -45.0);
                }
                n3 = 0.0;
                if (n2 > 0.0) {
                    n2 = 1.0;
                }
                else if (n2 < 0.0) {
                    n2 = -1.0;
                }
            }
            final double n5 = n2 * n * Math.cos(Math.toRadians(n4 + 88.0)) + n3 * n * Math.sin(Math.toRadians(n4 + 87.9000015258789));
            final double n6 = n2 * n * Math.sin(Math.toRadians(n4 + 88.0)) - n3 * n * Math.cos(Math.toRadians(n4 + 87.9000015258789));
            if (MedusaWare.instance.mm.getModuleByName("Flight").isToggled()) {
                Game.Player().motionX = n5 / 1.5;
                Game.Player().motionZ = n6 / 1.5;
            }
            else {
                Game.Player().motionX = n5 / 1.25;
                Game.Player().motionZ = n6 / 1.25;
            }
        }
    }
    
    public static double getBaseMovementSpeed() {
        double n = 0.2873;
        if (Game.Player().isPotionActive(Potion.moveSpeed)) {
            n *= 1.0 + 0.2 * (Game.Player().getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return n;
    }
    
    public static float getSpeed() {
        return (float)Math.sqrt(Game.Player().motionX * Game.Player().motionX + Game.Player().motionZ * Game.Player().motionZ);
    }
    
    public static boolean isOnGround(final double n) {
        return !Game.World().getCollidingBoundingBoxes((Entity)Game.Player(), Game.Player().getEntityBoundingBox().offset(0.0, -n, 0.0)).isEmpty();
    }
    
    public static int getJumpEffect() {
        if (Game.Player().isPotionActive(Potion.jump)) {
            return Game.Player().getActivePotionEffect(Potion.jump).getAmplifier() + 1;
        }
        return 0;
    }
    
    public static int getSpeedEffect() {
        if (Game.Player().isPotionActive(Potion.moveSpeed)) {
            return Game.Player().getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
        }
        return 0;
    }
    
    public static float getDirection() {
        float rotationYaw = Game.Player().rotationYaw;
        if (Game.Player().moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float n;
        if (Game.Player().moveForward < 0.0f) {
            n = -0.5f;
        }
        else if (Game.Player().moveForward > 0.0f) {
            n = 0.5f;
        }
        else {
            n = 1.0f;
        }
        if (Game.Player().moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * n;
        }
        if (Game.Player().moveStrafing < 0.0f) {
            rotationYaw += 90.0f * n;
        }
        return rotationYaw * 0.017453292f;
    }
}
