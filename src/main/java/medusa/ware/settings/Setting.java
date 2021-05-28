// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.settings;

import medusa.ware.config.ConfigManager;
import java.util.ArrayList;
import java.util.List;
import medusa.ware.module.Module;

public class Setting
{
    private String name;
    private Module parent;
    private String mode;
    private String sval;
    private List<String> options;
    private boolean bval;
    private double dval;
    private double min;
    private double max;
    private boolean onlyint;
    public boolean percentage;
    boolean visible;
    
    public Setting(final String name, final Module parent, final String sval, final ArrayList<String> options) {
        this.onlyint = false;
        this.percentage = false;
        this.visible = false;
        this.name = name;
        this.parent = parent;
        this.sval = sval;
        this.options = options;
        this.mode = "Combo";
        this.visible = false;
    }
    
    public Setting(final String name, final Module parent, final boolean bval) {
        this.onlyint = false;
        this.percentage = false;
        this.visible = false;
        this.name = name;
        this.parent = parent;
        this.bval = bval;
        this.mode = "Check";
        this.visible = false;
    }
    
    public Setting(final String name, final Module parent, final double dval, final double min, final double max, final boolean onlyint) {
        this.onlyint = false;
        this.percentage = false;
        this.visible = false;
        this.name = name;
        this.parent = parent;
        this.dval = dval;
        this.min = min;
        this.max = max;
        this.onlyint = onlyint;
        this.mode = "Slider";
        this.visible = false;
    }
    
    public Setting(final String name, final Module parent, final double dval, final double min, final double max, final boolean onlyint, final boolean percentage) {
        this.onlyint = false;
        this.percentage = false;
        this.visible = false;
        this.name = name;
        this.parent = parent;
        this.dval = dval;
        this.min = min;
        this.max = max;
        this.onlyint = onlyint;
        this.percentage = percentage;
        this.mode = "Slider";
        this.visible = false;
    }
    
    public void setVisible(final boolean b) {
        this.visible = !b;
    }
    
    public boolean isVisible() {
        return this.visible;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Module getParentMod() {
        return this.parent;
    }
    
    public String getValString() {
        return this.sval;
    }
    
    public void setValString(final String sval) {
        ConfigManager.SaveConfigFile("Default");
        this.sval = sval;
    }
    
    public void setValStringNoSave(final String sval) {
        this.sval = sval;
    }
    
    public List<String> getOptions() {
        return this.options;
    }
    
    public boolean getValBoolean() {
        return this.bval;
    }
    
    public void setValBoolean(final boolean bval) {
        ConfigManager.SaveConfigFile("Default");
        this.bval = bval;
    }
    
    public void setValBooleanNoSave(final boolean bval) {
        this.bval = bval;
    }
    
    public double getValDouble() {
        if (this.onlyint) {
            this.dval = (int)this.dval;
        }
        return this.dval;
    }
    
    public int getValInt() {
        if (this.onlyint) {
            this.dval = (int)this.dval;
        }
        return (int)this.dval;
    }
    
    public void setValDouble(final double dval) {
        ConfigManager.SaveConfigFile("Default");
        this.dval = dval;
    }
    
    public void setValDoubleNoSave(final double dval) {
        this.dval = dval;
    }
    
    public double getMin() {
        return this.min;
    }
    
    public double getMax() {
        return this.max;
    }
    
    public boolean isCombo() {
        return this.mode.equalsIgnoreCase("Combo");
    }
    
    public boolean isCheck() {
        return this.mode.equalsIgnoreCase("Check");
    }
    
    public boolean isSlider() {
        return this.mode.equalsIgnoreCase("Slider");
    }
    
    public boolean onlyInt() {
        return this.onlyint;
    }
}
