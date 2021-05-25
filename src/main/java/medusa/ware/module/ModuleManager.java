// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module;

import medusa.ware.module.render.Search;
import medusa.ware.module.player.AutoHotbar;
import medusa.ware.module.movement.LongJump;
import medusa.ware.module.combat.STap;
import medusa.ware.module.combat.WTap;
import medusa.ware.module.render.StorageESP;
import medusa.ware.module.player.AutoMine;
import medusa.ware.module.combat.RodAimbot;
import medusa.ware.module.render.NameTags;
import medusa.ware.module.misc.MCF;
import medusa.ware.module.player.Fall;
import medusa.ware.module.movement.Eagle;
import medusa.ware.module.movement.Step;
import medusa.ware.module.player.ChestStealer;
import medusa.ware.module.player.AutoMLG;
import medusa.ware.module.combat.HitBoxes;
import medusa.ware.module.render.ESP;
import medusa.ware.module.misc.Timer;
import medusa.ware.module.movement.Speed;
import medusa.ware.module.player.AutoArmor;
import medusa.ware.module.misc.SelfDestruct;
import medusa.ware.module.render.HUD;
import medusa.ware.module.movement.Fly;
import medusa.ware.module.combat.AntiBot;
import medusa.ware.module.combat.Reach;
import medusa.ware.module.combat.Killaura;
import medusa.ware.module.combat.Velocity;
import medusa.ware.module.combat.AutoClicker;
import medusa.ware.module.combat.AimAssist;
import medusa.ware.module.render.ClickGUI;
import medusa.ware.module.movement.Strafe;
import medusa.ware.module.movement.Sprint;
import medusa.ware.module.player.FastPlace;
import medusa.ware.module.values.ValueOthers;
import medusa.ware.module.values.ValueMobs;
import medusa.ware.module.values.ValueAnimals;
import medusa.ware.module.values.ValueInvisibles;
import medusa.ware.module.values.ValueTeams;
import medusa.ware.module.values.ValuePlayers;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager
{
    public List<Module> modules;
    
    public ModuleManager() {
        (this.modules = new ArrayList<Module>()).add(new ValuePlayers());
        this.modules.add(new ValueTeams());
        this.modules.add(new ValueInvisibles());
        this.modules.add(new ValueAnimals());
        this.modules.add(new ValueMobs());
        this.modules.add(new ValueOthers());
        this.modules.add(new FastPlace());
        this.modules.add(new Sprint());
        this.modules.add(new Strafe());
        this.modules.add(new ClickGUI());
        this.modules.add(new AimAssist());
        this.modules.add(new AutoClicker());
        this.modules.add(new Velocity());
        this.modules.add(new Killaura());
        this.modules.add(new Reach());
        this.modules.add(new AntiBot());
        this.modules.add(new Fly());
        this.modules.add(new HUD());
        this.modules.add(new SelfDestruct());
        this.modules.add(new AutoArmor());
        this.modules.add(new Speed());
        this.modules.add(new Timer());
        this.modules.add(new ESP());
        this.modules.add(new HitBoxes());
        this.modules.add(new AutoMLG());
        this.modules.add(new ChestStealer());
        this.modules.add(new Step());
        this.modules.add(new Eagle());
        this.modules.add(new Fall());
        this.modules.add(new MCF());
        this.modules.add(new NameTags());
        this.modules.add(new RodAimbot());
        this.modules.add(new AutoMine());
        this.modules.add(new StorageESP());
        this.modules.add(new WTap());
        this.modules.add(new STap());
        this.modules.add(new LongJump());
        this.modules.add(new AutoHotbar());
        this.modules.add(new Search());
    }
    
    public List<Module> getEnabledModules() {
        final ArrayList<Module> list = new ArrayList<Module>();
        for (int i = 0; i < this.modules.size(); ++i) {
            if (this.modules.get(i).isToggled()) {
                list.add(this.modules.get(i));
            }
        }
        return list;
    }
    
    public List<Module> getModules() {
        return this.modules;
    }
    
    public Module getModuleByName(final String anotherString) {
        Module module = null;
        for (final Module module2 : this.modules) {
            if (module2.getName().equalsIgnoreCase(anotherString)) {
                module = module2;
            }
        }
        return module;
    }
}
