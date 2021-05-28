// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.utils;

import medusa.ware.MedusaWare;
import medusa.ware.module.combat.AntiBot;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;

public class CombatUtils
{
    public static boolean canTarget(final Entity entity) {
        EntityLivingBase entityLivingBase = null;
        if (entity instanceof EntityPlayer) {
            final EntityPlayer entityPlayer = (EntityPlayer)entity;
        }
        if (entity instanceof EntityLivingBase) {
            entityLivingBase = (EntityLivingBase)entity;
        }
        final boolean toggled = MedusaWare.instance.mm.getModuleByName("Players").isToggled();
        final boolean toggled2 = MedusaWare.instance.mm.getModuleByName("Animals").isToggled();
        final boolean toggled3 = MedusaWare.instance.mm.getModuleByName("Mobs").isToggled();
        final boolean toggled4 = MedusaWare.instance.mm.getModuleByName("Invisibles").isToggled();
        final boolean toggled5 = MedusaWare.instance.mm.getModuleByName("Others").isToggled();
        final boolean toggled6 = MedusaWare.instance.mm.getModuleByName("Teams").isToggled();
        boolean b = false;
        if (toggled6 && isTeam((EntityPlayer)Game.Player(), entity)) {
            b = true;
        }
        else if (!toggled6) {
            b = false;
        }
        boolean b2 = true;
        if (entity.isInvisible() && !toggled4) {
            b2 = false;
        }
        return !AntiBot.getBots().contains(entity) && !(entity instanceof EntityArmorStand) && !MedusaWare.instance.friendManager.getFriends().contains(entity.getName()) && b2 && ((entity instanceof EntityPlayer && toggled && !b) || (entity instanceof EntityAnimal && toggled2) || (entity instanceof EntityMob && toggled3) || (entity instanceof EntityLivingBase && toggled5 && !(entity instanceof EntityMob) && !(entity instanceof EntityAnimal) && !(entity instanceof EntityPlayer) && entity instanceof EntityLivingBase && entityLivingBase.isEntityAlive()));
    }
    
    public static boolean isTeam(final EntityPlayer entityPlayer, final Entity entity) {
        if (entity instanceof EntityPlayer && ((EntityPlayer)entity).getTeam() != null && entityPlayer.getTeam() != null) {
            final Character value = entity.getDisplayName().getFormattedText().charAt(3);
            final Character value2 = entityPlayer.getDisplayName().getFormattedText().charAt(3);
            final Character value3 = entity.getDisplayName().getFormattedText().charAt(2);
            final Character value4 = entityPlayer.getDisplayName().getFormattedText().charAt(2);
            boolean b = false;
            if (value.equals(value2) && value3.equals(value4)) {
                b = true;
            }
            else {
                final Character value5 = entity.getDisplayName().getFormattedText().charAt(1);
                final Character value6 = entityPlayer.getDisplayName().getFormattedText().charAt(1);
                final Character value7 = entity.getDisplayName().getFormattedText().charAt(0);
                final Character value8 = entityPlayer.getDisplayName().getFormattedText().charAt(0);
                if (value5.equals(value6) && Character.isDigit(0) && value7.equals(value8)) {
                    b = true;
                }
            }
            return b;
        }
        return true;
    }
}
