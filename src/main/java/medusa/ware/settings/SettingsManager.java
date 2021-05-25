
package medusa.ware.settings;

import medusa.ware.module.Module;
import java.util.ArrayList;
import java.util.List;

public class SettingsManager
{
    private List<Setting> settings;
    ArrayList<String> out;
    ArrayList<Setting> outSetting;
    
    public void rSetting(final Setting setting) {
        if (this.settings == null) {
            (this.settings = new ArrayList<Setting>()).add(setting);
        }
        else {
            this.settings.add(setting);
        }
    }
    
    public List<Setting> getSettings() {
        return this.settings;
    }
    
    public List<Setting> getSettingsByMod(final Module obj) {
        if (this.outSetting == null) {
            (this.outSetting = new ArrayList<Setting>()).clear();
        }
        else {
            this.outSetting.clear();
        }
        for (final Setting e : this.getSettings()) {
            if (e.getParentMod().equals(obj)) {
                this.outSetting.add(e);
            }
        }
        if (this.outSetting.isEmpty()) {
            return null;
        }
        return this.outSetting;
    }
    
    public List<String> getSettingsNameByMod(final Module obj) {
        if (this.out == null) {
            (this.out = new ArrayList<String>()).clear();
        }
        else {
            this.out.clear();
        }
        for (final Setting setting : this.getSettings()) {
            if (setting.getParentMod().equals(obj)) {
                this.out.add(setting.getName());
            }
        }
        if (this.out.isEmpty()) {
            return null;
        }
        return this.out;
    }
    
    public Setting getSettingByName(final Module obj, final String anotherString) {
        for (final Setting setting : this.getSettings()) {
            if (setting.getName().equalsIgnoreCase(anotherString) && setting.getParentMod().equals(obj)) {
                return setting;
            }
        }
        return null;
    }
}
