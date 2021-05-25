// 
// Decompiled by Procyon v0.5.36
// 

package me.aycy.blockoverlay;

import java.io.IOException;
import medusa.ware.utils.Game;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class MedusaWareGui extends GuiScreen
{
    private GuiButton buttonMode;
    private GuiButton buttonSettings;
    
    public void initGui() {
        super.buttonList.add(this.buttonMode = new GuiButton(0, super.width / 2 - 100, super.height / 2 - 15, String.valueOf(new StringBuilder().append("Mode: ").append(MedusaWare.mode.name))));
        super.buttonList.add(this.buttonSettings = new GuiButton(1, super.width / 2 - 100, super.height / 2 + 15, "Settings"));
    }
    
    public void drawScreen(final int n, final int n2, final float n3) {
        super.drawDefaultBackground();
        GlStateManager.pushMatrix();
        GlStateManager.scale(1.2f, 1.2f, 1.2f);
        super.drawCenteredString(super.fontRendererObj, "Medusa Ware", Math.round(super.width / 2 / 1.2f), Math.round(super.height / 2 / 1.2f) - 50, -1);
        GlStateManager.popMatrix();
        this.buttonSettings.enabled = (!MedusaWare.mode.equals(MedusaWareMode.NONE) && !MedusaWare.mode.equals(MedusaWareMode.DEFAULT));
        this.buttonMode.drawButton(super.mc, n, n2);
        this.buttonSettings.drawButton(super.mc, n, n2);
    }
    
    public void actionPerformed(final GuiButton guiButton) throws IOException {
        switch (guiButton.id) {
            case 0: {
                MedusaWare.mode = MedusaWareMode.getNextMode(MedusaWare.mode);
                this.buttonMode.displayString = String.valueOf(new StringBuilder().append("Mode: ").append(MedusaWare.mode.name));
                break;
            }
            case 1: {
                Game.Minecraft().displayGuiScreen((GuiScreen)new MedusaWareSettings());
                break;
            }
        }
    }
    
    public void onGuiClosed() {
        MedusaWare.saveConfig();
    }
}
