// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module;

import medusa.ware.utils.Game;
import medusa.ware.config.ConfigManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.client.Minecraft;

public class Module
{
    protected static Minecraft mc;
    private String name;
    private String displayName;
    private String description;
    private int key;
    private Category category;
    private boolean toggled;
    
    public Module(final String name, final int key, final Category category, final String description) {
        this.name = name;
        this.key = key;
        this.category = category;
        this.toggled = false;
        this.description = description;
        this.setup();
    }
    
    public void onUpdate() {
    }
    
    public void onUpdateNoToggle() {
    }
    
    public void onTick() {
    }
    
    public void onMove() {
    }
    
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    public void onToggle() {
    }
    
    public void setToggled(final boolean toggled) {
        this.toggled = toggled;
        this.onToggle();
        ConfigManager.SaveConfigFile("Default");
        MinecraftForge.EVENT_BUS.register((Object)this);
        if (this.toggled) {
            this.onEnable();
        }
        else {
            MinecraftForge.EVENT_BUS.unregister((Object)this);
            this.onDisable();
        }
    }
    
    public void setToggledNoSave(final boolean toggled) {
        this.toggled = toggled;
        this.onToggle();
        MinecraftForge.EVENT_BUS.register((Object)this);
        if (this.toggled) {
            this.onEnable();
        }
        else {
            MinecraftForge.EVENT_BUS.unregister((Object)this);
            this.onDisable();
        }
    }
    
    public void toggle() {
        this.toggled = !this.toggled;
        this.onToggle();
        ConfigManager.SaveConfigFile("Default");
        if (this.toggled) {
            this.onEnable();
        }
        else {
            this.onDisable();
        }
    }
    
    public void toggleNoSave() {
        this.toggled = !this.toggled;
        this.onToggle();
        if (this.toggled) {
            this.onEnable();
        }
        else {
            this.onDisable();
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public int getKey() {
        return this.key;
    }
    
    public void setKey(final int key) {
        ConfigManager.SaveConfigFile("Default");
        this.key = key;
    }
    
    public void setKeyNoSave(final int key) {
        this.key = key;
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public void setCategory(final Category category) {
        this.category = category;
    }
    
    public boolean isToggled() {
        return this.toggled;
    }
    
    public String getDisplayName() {
        return (this.displayName == null) ? this.name : this.displayName;
    }
    
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }
    
    public void setup() {
    }
    
    public void onRender2D() {
    }
    
    public void onRender3D() {
    }
    
    static {
        Module.mc = Game.Minecraft();
    }
}
