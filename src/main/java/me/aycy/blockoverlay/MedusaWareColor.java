// 
// Decompiled by Procyon v0.5.36
// 

package me.aycy.blockoverlay;

import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class MedusaWareColor extends GuiScreen
{
    private GuiButton buttonChroma;
    private GuiButton buttonBack;
    private GuiSlider sliderChroma;
    private GuiSlider sliderRed;
    private GuiSlider sliderGreen;
    private GuiSlider sliderBlue;
    private GuiSlider sliderAlpha;
    
    public void initGui() {
        super.buttonList.add(this.buttonChroma = new GuiButton(0, super.width / 2 - 100, super.height / 2 - 90, String.valueOf(new StringBuilder().append("Chroma: ").append(MedusaWare.isChroma ? "On" : "Off"))));
        super.buttonList.add(this.sliderChroma = new GuiSlider(1, super.width / 2 - 100, super.height / 2 - 60, "Speed: ", 1, 10, MedusaWare.chromaSpeed));
        super.buttonList.add(this.sliderRed = new GuiSlider(2, super.width / 2 - 100, super.height / 2 - 60, "Red: ", 0.0f, 1.0f, MedusaWare.red));
        super.buttonList.add(this.sliderGreen = new GuiSlider(3, super.width / 2 - 100, super.height / 2 - 30, "Green: ", 0.0f, 1.0f, MedusaWare.green));
        super.buttonList.add(this.sliderBlue = new GuiSlider(4, super.width / 2 - 100, super.height / 2, "Blue: ", 0.0f, 1.0f, MedusaWare.blue));
        super.buttonList.add(this.sliderAlpha = new GuiSlider(5, super.width / 2 - 100, super.height / 2 + 30, "Alpha: ", 0.0f, 1.0f, MedusaWare.alpha));
        super.buttonList.add(this.buttonBack = new GuiButton(6, super.width / 2 - 100, super.height / 2 + 80, "Back"));
    }
    
    public void drawDefaultBackground(final int n, final int n2, final float n3) {
        super.drawDefaultBackground();
        if (MedusaWare.isChroma) {
            this.sliderRed.enabled = false;
            this.sliderGreen.enabled = false;
            this.sliderBlue.enabled = false;
            this.sliderAlpha.yPosition = super.height / 2 - 30;
            this.sliderChroma.drawButton(super.mc, n, n2);
        }
        else {
            this.sliderRed.enabled = true;
            this.sliderGreen.enabled = true;
            this.sliderBlue.enabled = true;
            this.sliderAlpha.yPosition = super.height / 2 + 30;
            this.sliderRed.drawButton(super.mc, n, n2);
            this.sliderGreen.drawButton(super.mc, n, n2);
            this.sliderBlue.drawButton(super.mc, n, n2);
        }
        this.buttonChroma.drawButton(super.mc, n, n2);
        this.sliderAlpha.drawButton(super.mc, n, n2);
        this.buttonBack.drawButton(super.mc, n, n2);
    }
    
    public void actionPerformed(final GuiButton guiButton) throws IOException {
        switch (guiButton.id) {
            case 0: {
                MedusaWare.isChroma = !MedusaWare.isChroma;
                this.buttonChroma.displayString = String.valueOf(new StringBuilder().append("Chroma: ").append(MedusaWare.isChroma ? "On" : "Off"));
                break;
            }
            case 1: {
                MedusaWare.chromaSpeed = Math.round(this.sliderChroma.getSliderValue() * 9.0f + 1.0f);
                break;
            }
            case 2: {
                MedusaWare.red = this.sliderRed.getSliderValue();
                break;
            }
            case 3: {
                MedusaWare.green = this.sliderGreen.getSliderValue();
                break;
            }
            case 4: {
                MedusaWare.blue = this.sliderBlue.getSliderValue();
                break;
            }
            case 5: {
                MedusaWare.alpha = this.sliderAlpha.getSliderValue();
                break;
            }
            case 6: {
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new MedusaWareSettings());
                break;
            }
        }
    }
    
    public void mouseClickMove(final int n, final int n2, final int n3, final long n4) {
        MedusaWare.chromaSpeed = Math.round(this.sliderChroma.getSliderValue() * 9.0f + 1.0f);
        MedusaWare.red = this.sliderRed.getSliderValue();
        MedusaWare.green = this.sliderGreen.getSliderValue();
        MedusaWare.blue = this.sliderBlue.getSliderValue();
        MedusaWare.alpha = this.sliderAlpha.getSliderValue();
    }
    
    public void onGuiClosed() {
        MedusaWare.saveConfig();
    }
}
