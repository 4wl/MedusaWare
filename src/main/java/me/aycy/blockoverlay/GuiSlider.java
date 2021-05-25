// 
// Decompiled by Procyon v0.5.36
// 

package me.aycy.blockoverlay;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.Minecraft;
import java.text.DecimalFormat;
import net.minecraft.client.gui.GuiButton;

public class GuiSlider extends GuiButton
{
    private boolean isDragging;
    private double minValue;
    private double maxValue;
    private double sliderValue;
    private DecimalFormat decimalFormat;
    private String sliderPrefix;
    
    public GuiSlider(final int n, final int n2, final int n3, final String s, final int n4, final int n5, final int n6) {
        this(n, n2, n3, s, (float)n4, (float)n5, (float)n6, "#");
    }
    
    public GuiSlider(final int n, final int n2, final int n3, final String s, final float n4, final float n5, final float n6) {
        this(n, n2, n3, s, n4, n5, n6, "#.00");
    }
    
    public GuiSlider(final int n, final int n2, final int n3, final String sliderPrefix, final float n4, final float n5, final float n6, final String pattern) {
        super(n, n2, n3, sliderPrefix);
        this.isDragging = false;
        this.minValue = n4;
        this.maxValue = n5;
        this.sliderValue = (n6 - n4) / (double)(n5 - n4);
        this.decimalFormat = new DecimalFormat(pattern);
        this.sliderPrefix = sliderPrefix;
        super.displayString = String.valueOf(new StringBuilder().append(this.sliderPrefix).append(this.decimalFormat.format(this.sliderValue * (this.maxValue - this.minValue) + this.minValue)));
    }
    
    protected int getHoverState(final boolean b) {
        return 0;
    }
    
    protected void mouseDragged(final Minecraft minecraft, final int n, final int n2) {
        if (super.visible) {
            if (this.isDragging) {
                this.sliderValue = (n - (super.xPosition + 4)) / (double)(super.width - 8);
                this.updateSlider();
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(super.xPosition + (int)(this.sliderValue * (super.width - 8)), super.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(super.xPosition + (int)(this.sliderValue * (super.width - 8)) + 4, super.yPosition, 196, 66, 4, 20);
        }
    }
    
    public boolean mousePressed(final Minecraft minecraft, final int n, final int n2) {
        if (super.mousePressed(minecraft, n, n2)) {
            this.sliderValue = (n - (super.xPosition + 4)) / (double)(super.width - 8);
            this.updateSlider();
            return this.isDragging = true;
        }
        return false;
    }
    
    public void mouseReleased(final int n, final int n2) {
        this.isDragging = false;
    }
    
    public void updateSlider() {
        if (this.sliderValue < 0.0) {
            this.sliderValue = 0.0;
        }
        else if (this.sliderValue > 1.0) {
            this.sliderValue = 1.0;
        }
        super.displayString = String.valueOf(new StringBuilder().append(this.sliderPrefix).append(this.decimalFormat.format(this.sliderValue * (this.maxValue - this.minValue) + this.minValue)));
    }
    
    public float getSliderValue() {
        return (float)this.sliderValue;
    }
}
