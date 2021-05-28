// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.render;

import java.awt.Color;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.tileentity.TileEntityEnderChest;
import medusa.ware.utils.RenderUtils;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntity;
import medusa.ware.settings.Setting;
import medusa.ware.MedusaWare;
import medusa.ware.module.Category;
import medusa.ware.module.Module;

public class StorageESP extends Module
{
    private boolean rainbow;
    private int red;
    private int green;
    private int blue;
    
    public StorageESP() {
        super("StorageESP", 0, Category.RENDER, "Allows you to see storages");
    }
    
    @Override
    public void setup() {
        MedusaWare.instance.sm.rSetting(new Setting("Rainbow", this, true));
        MedusaWare.instance.sm.rSetting(new Setting("LineSize", this, 2.0, 0.1, 5.0, false));
        MedusaWare.instance.sm.rSetting(new Setting("Red", this, 150.0, 0.0, 255.0, true));
        MedusaWare.instance.sm.rSetting(new Setting("Green", this, 0.0, 0.0, 255.0, true));
        MedusaWare.instance.sm.rSetting(new Setting("Blue", this, 0.0, 0.0, 255.0, true));
        MedusaWare.instance.sm.rSetting(new Setting("CheckRange", this, false));
        MedusaWare.instance.sm.rSetting(new Setting("Range", this, 20.0, 1.0, 512.0, true));
        MedusaWare.instance.sm.rSetting(new Setting("Chest", this, true));
        MedusaWare.instance.sm.rSetting(new Setting("EnderChest", this, true));
        MedusaWare.instance.sm.rSetting(new Setting("Dropper", this, false));
        MedusaWare.instance.sm.rSetting(new Setting("Dispenser", this, false));
        MedusaWare.instance.sm.rSetting(new Setting("Furnace", this, false));
        MedusaWare.instance.sm.rSetting(new Setting("Hopper", this, false));
    }
    
    @Override
    public void onUpdateNoToggle() {
        this.rainbow = MedusaWare.instance.sm.getSettingByName(this, "Rainbow").getValBoolean();
        this.red = (int) MedusaWare.instance.sm.getSettingByName(this, "Red").getValDouble();
        this.green = (int) MedusaWare.instance.sm.getSettingByName(this, "Green").getValDouble();
        this.blue = (int) MedusaWare.instance.sm.getSettingByName(this, "Blue").getValDouble();
        if (this.rainbow) {
            MedusaWare.instance.sm.getSettingByName(this, "Red").setValDoubleNoSave(MedusaWare.instance.cm.cc.getColor(0).getRed());
            MedusaWare.instance.sm.getSettingByName(this, "Green").setValDoubleNoSave(MedusaWare.instance.cm.cc.getColor(0).getGreen());
            MedusaWare.instance.sm.getSettingByName(this, "Blue").setValDoubleNoSave(MedusaWare.instance.cm.cc.getColor(0).getBlue());
        }
    }
    
    @Override
    public void onRender3D() {
        final int n = (int) MedusaWare.instance.sm.getSettingByName(this, "Range").getValDouble();
        final boolean valBoolean = MedusaWare.instance.sm.getSettingByName(this, "CheckRange").getValBoolean();
        for (final TileEntity next : StorageESP.mc.theWorld.loadedTileEntityList) {
            if (valBoolean) {
                if (next.getPos().getX() - StorageESP.mc.thePlayer.getPosition().getX() <= -(n + 1) || next.getPos().getX() - StorageESP.mc.thePlayer.getPosition().getX() >= n + 1 || next.getPos().getY() - StorageESP.mc.thePlayer.getPosition().getY() <= -(n + 1) || next.getPos().getY() - StorageESP.mc.thePlayer.getPosition().getY() >= n + 1 || next.getPos().getZ() - StorageESP.mc.thePlayer.getPosition().getZ() <= -(n + 1) || next.getPos().getZ() - StorageESP.mc.thePlayer.getPosition().getZ() >= n + 1) {
                    continue;
                }
                this.checkCorrectBlock(next);
            }
            else {
                this.checkCorrectBlock(next);
            }
        }
    }
    
    private void checkCorrectBlock(final TileEntity tileEntity) {
        final boolean valBoolean = MedusaWare.instance.sm.getSettingByName(this, "Chest").getValBoolean();
        final boolean valBoolean2 = MedusaWare.instance.sm.getSettingByName(this, "EnderChest").getValBoolean();
        final boolean valBoolean3 = MedusaWare.instance.sm.getSettingByName(this, "Dropper").getValBoolean();
        final boolean valBoolean4 = MedusaWare.instance.sm.getSettingByName(this, "Dispenser").getValBoolean();
        final boolean valBoolean5 = MedusaWare.instance.sm.getSettingByName(this, "Furnace").getValBoolean();
        final boolean valBoolean6 = MedusaWare.instance.sm.getSettingByName(this, "Hopper").getValBoolean();
        final float n = (float) MedusaWare.instance.sm.getSettingByName(this, "LineSize").getValDouble();
        if (tileEntity instanceof TileEntityChest && valBoolean) {
            RenderUtils.blockESPBox(((TileEntityChest)tileEntity).getPos(), this.getColor().getRed() / 255.0f, this.getColor().getGreen() / 255.0f, this.getColor().getBlue() / 255.0f, n);
        }
        if (tileEntity instanceof TileEntityEnderChest && valBoolean2) {
            RenderUtils.blockESPBox(((TileEntityEnderChest)tileEntity).getPos(), this.getColor().getRed() / 255.0f, this.getColor().getGreen() / 255.0f, this.getColor().getBlue() / 255.0f, n);
        }
        if (tileEntity instanceof TileEntityDropper && valBoolean3) {
            RenderUtils.blockESPBox(((TileEntityDropper)tileEntity).getPos(), this.getColor().getRed() / 255.0f, this.getColor().getGreen() / 255.0f, this.getColor().getBlue() / 255.0f, n);
        }
        if (tileEntity instanceof TileEntityDispenser && valBoolean4) {
            RenderUtils.blockESPBox(((TileEntityDispenser)tileEntity).getPos(), this.getColor().getRed() / 255.0f, this.getColor().getGreen() / 255.0f, this.getColor().getBlue() / 255.0f, n);
        }
        if (tileEntity instanceof TileEntityFurnace && valBoolean5) {
            RenderUtils.blockESPBox(((TileEntityFurnace)tileEntity).getPos(), this.getColor().getRed() / 255.0f, this.getColor().getGreen() / 255.0f, this.getColor().getBlue() / 255.0f, n);
        }
        if (tileEntity instanceof TileEntityHopper && valBoolean6) {
            RenderUtils.blockESPBox(((TileEntityHopper)tileEntity).getPos(), this.getColor().getRed() / 255.0f, this.getColor().getGreen() / 255.0f, this.getColor().getBlue() / 255.0f, n);
        }
    }
    
    private Color getColor() {
        return new Color(this.red, this.green, this.blue, 255);
    }
}
