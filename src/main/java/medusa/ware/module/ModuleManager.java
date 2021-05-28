// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module;

import medusa.ware.module.misc.*;
import medusa.ware.module.render.*;
import medusa.ware.module.player.*;
import medusa.ware.module.movement.*;
import medusa.ware.module.combat.*;
import medusa.ware.module.values.*;

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
        this.modules.add(new AutoTool());
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
