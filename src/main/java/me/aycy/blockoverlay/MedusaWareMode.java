// 
// Decompiled by Procyon v0.5.36
// 

package me.aycy.blockoverlay;

public enum MedusaWareMode
{
    NONE("None"), 
    DEFAULT("Default"), 
    OUTLINE("Outline"), 
    FULL("Full");
    
    protected String name;
    
    private MedusaWareMode(final String name2) {
        this.name = name2;
    }
    
    public static MedusaWareMode getNextMode(final MedusaWareMode medusaWareMode) {
        return values()[(medusaWareMode.ordinal() + 1) % values().length];
    }
}
