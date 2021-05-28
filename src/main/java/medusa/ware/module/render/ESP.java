// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.render;

import java.awt.Color;

import medusa.ware.utils.RenderUtils;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import medusa.ware.utils.CombatUtils;
import net.minecraft.entity.Entity;
import medusa.ware.utils.Game;
import medusa.ware.settings.Setting;
import medusa.ware.MedusaWare;
import java.util.ArrayList;
import medusa.ware.module.Category;
import medusa.ware.module.Module;

public class ESP extends Module
{
    private String color;
    private int red;
    private int green;
    private int blue;
    
    public ESP() {
        super("EntityESP", 0, Category.RENDER, "Shows entities through walls");
    }
    
    @Override
    public void setup() {
        final ArrayList<String> list;
        (list = new ArrayList<String>()).add("Box");
        list.add("2D");
        MedusaWare.instance.sm.rSetting(new Setting("Mode", this, "2D", list));
        final ArrayList<String> list2;
        (list2 = new ArrayList<String>()).add("Rainbow");
        list2.add("Select");
        MedusaWare.instance.sm.rSetting(new Setting("Color", this, "Rainbow", list2));
        MedusaWare.instance.sm.rSetting(new Setting("Players", this, true));
        MedusaWare.instance.sm.rSetting(new Setting("Invisibles", this, false));
        MedusaWare.instance.sm.rSetting(new Setting("Teams", this, false));
        MedusaWare.instance.sm.rSetting(new Setting("Animals", this, false));
        MedusaWare.instance.sm.rSetting(new Setting("Mobs", this, false));
        MedusaWare.instance.sm.rSetting(new Setting("Red", this, 150.0, 0.0, 255.0, true));
        MedusaWare.instance.sm.rSetting(new Setting("Green", this, 0.0, 0.0, 255.0, true));
        MedusaWare.instance.sm.rSetting(new Setting("Blue", this, 0.0, 0.0, 255.0, true));
    }
    
    @Override
    public void onRender3D() {
        this.color = MedusaWare.instance.sm.getSettingByName(this, "Color").getValString();
        this.red = (int) MedusaWare.instance.sm.getSettingByName(this, "Red").getValDouble();
        this.green = (int) MedusaWare.instance.sm.getSettingByName(this, "Green").getValDouble();
        this.blue = (int) MedusaWare.instance.sm.getSettingByName(this, "Blue").getValDouble();
        if (this.color.equalsIgnoreCase("Rainbow")) {
            MedusaWare.instance.sm.getSettingByName(this, "Red").setVisible(false);
            MedusaWare.instance.sm.getSettingByName(this, "Blue").setVisible(false);
            MedusaWare.instance.sm.getSettingByName(this, "Green").setVisible(false);
        }
        else {
            MedusaWare.instance.sm.getSettingByName(this, "Red").setVisible(true);
            MedusaWare.instance.sm.getSettingByName(this, "Green").setVisible(true);
            MedusaWare.instance.sm.getSettingByName(this, "Blue").setVisible(true);
        }
        for (final Entity entity : Game.World().loadedEntityList) {
            boolean b = true;
            final boolean valBoolean = MedusaWare.instance.sm.getSettingByName(this, "Teams").getValBoolean();
            final boolean valBoolean2 = MedusaWare.instance.sm.getSettingByName(this, "Players").getValBoolean();
            final boolean valBoolean3 = MedusaWare.instance.sm.getSettingByName(this, "Invisibles").getValBoolean();
            if (!CombatUtils.isTeam((EntityPlayer)Game.Player(), entity) && valBoolean && valBoolean2 && entity instanceof EntityPlayer) {
                b = false;
            }
            else if (entity.isInvisible() && !valBoolean3 && valBoolean2 && entity instanceof EntityPlayer) {
                b = false;
            }
            else if (!valBoolean2 || !(entity instanceof EntityPlayer)) {
                b = false;
            }
            if (entity != Game.Player() && (b || (entity instanceof EntityMob && MedusaWare.instance.sm.getSettingByName(this, "Mobs").getValBoolean()) || (entity instanceof EntityAnimal && MedusaWare.instance.sm.getSettingByName(this, "Animals").getValBoolean()))) {
                if (MedusaWare.instance.sm.getSettingByName(this, "Mode").getValString().equalsIgnoreCase("Box")) {
                    RenderUtils.renderEntity(entity, this.getColor().getRGB(), 2);
                }
                else {
                    if (!MedusaWare.instance.sm.getSettingByName(this, "Mode").getValString().equalsIgnoreCase("2D")) {
                        continue;
                    }
                    RenderUtils.renderEntity(entity, this.getColor().getRGB(), 3);
                }
            }
        }
    }
    
    private Color getColor() {
        if (this.color.equalsIgnoreCase("Rainbow")) {
            return MedusaWare.instance.cm.cc.getColor(0);
        }
        return new Color(this.red, this.green, this.blue, 255);
    }
}
