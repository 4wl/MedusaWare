// 
// Decompiled by Procyon v0.5.36
// 

package me.aycy.blockoverlay;

import java.io.IOException;
import medusa.ware.utils.Game;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class MedusaWareSettings extends GuiScreen
{
    private GuiButton buttonColor;
    private GuiButton buttonRender;
    private GuiButton buttonBack;
    private GuiSlider sliderWidth;
    
    public void initGui() {
        super.buttonList.add(this.sliderWidth = new GuiSlider(0, super.width / 2 - 100, super.height / 2 - 30, "Line width: ", 2.0f, 5.0f, MedusaWare.lineWidth));
        super.buttonList.add(this.buttonColor = new GuiButton(1, super.width / 2 - 100, super.height / 2, "Color"));
        super.buttonList.add(this.buttonRender = new GuiButton(2, super.width / 2 - 100, super.height / 2 + 30, String.valueOf(new StringBuilder().append("Always render: ").append(MedusaWare.alwaysRender ? "On" : "Off"))));
        super.buttonList.add(this.buttonBack = new GuiButton(3, super.width / 2 - 100, super.height / 2 + 80, "Back"));
    }
    
    public void drawScreen(final int n, final int n2, final float n3) {
        super.drawDefaultBackground();
        this.sliderWidth.drawButton(super.mc, n, n2);
        this.buttonColor.drawButton(super.mc, n, n2);
        this.buttonRender.drawButton(super.mc, n, n2);
        this.buttonBack.drawButton(super.mc, n, n2);
    }
    
    public void actionPerformed(final GuiButton guiButton) throws IOException {
        switch (guiButton.id) {
            case 0: {
                MedusaWare.lineWidth = this.sliderWidth.getSliderValue() * 3.0f + 2.0f;
                break;
            }
            case 1: {
                Game.Minecraft().displayGuiScreen((GuiScreen)new MedusaWareColor());
                break;
            }
            case 2: {
                MedusaWare.alwaysRender = !MedusaWare.alwaysRender;
                this.buttonRender.displayString = String.valueOf(new StringBuilder().append("Always render: ").append(MedusaWare.alwaysRender ? "On" : "Off"));
                break;
            }
            case 3: {
                Game.Minecraft().displayGuiScreen((GuiScreen)new MedusaWareGui());
                break;
            }
        }
    }
    
    public void mouseClickMove(final int n, final int n2, final int n3, final long n4) {
        MedusaWare.lineWidth = this.sliderWidth.getSliderValue() * 3.0f + 2.0f;
    }
    
    public void onGuiClosed() {
        MedusaWare.saveConfig();
    }
}
