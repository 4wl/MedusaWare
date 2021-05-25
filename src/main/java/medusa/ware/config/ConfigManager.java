// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.config;

import medusa.ware.MedusaWare;
import medusa.ware.settings.Setting;
import net.minecraft.client.Minecraft;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import medusa.ware.module.Module;

import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;

public class ConfigManager
{
    public static File dir;
    private static final File DEFAULT;
    private static final File FRIENDS;
    private static final File LIST;
    
    public static File getConfigFile(final String s) {
        final File file = new File(ConfigManager.dir, String.format("%s.cfg", s));
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException ex) {}
        }
        return file;
    }
    
    public static void init() {
        if (!ConfigManager.dir.exists()) {
            ConfigManager.dir.mkdir();
        }
        LoadConfig("Default");
        LoadFriends();
    }
    
    public static void SaveConfigFile(final String s) {
        try {
            if (MedusaWare.instance.sm == null || MedusaWare.instance.sm.getSettings() == null) {
                return;
            }
            final PrintWriter printWriter = new PrintWriter(getConfigFile(s));
            for (final Setting setting : MedusaWare.instance.sm.getSettings()) {
                if (setting.isCheck()) {
                    printWriter.write(String.valueOf(new StringBuilder().append("SET:").append(setting.getParentMod().getName()).append(":").append(setting.getName()).append(":").append(setting.getValBoolean())));
                }
                else if (setting.isCombo()) {
                    printWriter.write(String.valueOf(new StringBuilder().append("SET:").append(setting.getParentMod().getName()).append(":").append(setting.getName()).append(":").append(setting.getValString())));
                }
                else if (setting.isSlider()) {
                    printWriter.write(String.valueOf(new StringBuilder().append("SET:").append(setting.getParentMod().getName()).append(":").append(setting.getName()).append(":").append(setting.getValDouble())));
                }
                else {
                    printWriter.write(String.valueOf(new StringBuilder().append("SET:").append(setting.getParentMod().getName()).append(":").append(setting.getName())));
                }
                printWriter.println();
            }
            for (int i = 0; i < MedusaWare.instance.mm.getModules().size(); ++i) {
                final Module module = MedusaWare.instance.mm.getModules().get(i);
                printWriter.write(String.valueOf(new StringBuilder().append("MOD:").append(module.getName()).append(":").append(module.isToggled()).append(":").append(module.getKey())));
                if (MedusaWare.instance.mm.getModules().size() != i) {
                    printWriter.println();
                }
            }
            printWriter.close();
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void AddListFile(final String x) {
        if (GetConfigs().contains(x)) {
            return;
        }
        try {
            final ArrayList<String> list = new ArrayList<String>();
            try {
                final BufferedReader bufferedReader = new BufferedReader(new FileReader(ConfigManager.LIST));
                if (ConfigManager.LIST.exists()) {
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        list.add(line);
                    }
                }
                bufferedReader.close();
            }
            catch (Exception ex2) {}
            final PrintWriter printWriter = new PrintWriter(ConfigManager.LIST);
            for (int i = 0; i < list.size(); ++i) {
                printWriter.println((String)list.get(i));
            }
            printWriter.println(x);
            printWriter.close();
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void DeleteListFile(final String anotherString) {
        try {
            final ArrayList<String> list = new ArrayList<String>();
            try {
                final BufferedReader bufferedReader = new BufferedReader(new FileReader(ConfigManager.LIST));
                if (ConfigManager.LIST.exists()) {
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        list.add(line);
                    }
                }
                bufferedReader.close();
            }
            catch (Exception ex2) {}
            final PrintWriter printWriter = new PrintWriter(ConfigManager.LIST);
            for (int i = 0; i < list.size(); ++i) {
                if (!((String)list.get(i)).equalsIgnoreCase(anotherString)) {
                    printWriter.println((String)list.get(i));
                }
            }
            printWriter.close();
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void SaveFriendsFile() {
        try {
            final PrintWriter printWriter = new PrintWriter(ConfigManager.FRIENDS);
            for (int i = 0; i < MedusaWare.instance.friendManager.getFriends().size(); ++i) {
                printWriter.println(MedusaWare.instance.friendManager.getFriends().get(i));
            }
            printWriter.close();
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void LoadFriends() {
        try {
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(ConfigManager.FRIENDS));
            if (ConfigManager.FRIENDS.exists()) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    MedusaWare.instance.friendManager.addFriendNoSave(line);
                }
            }
            bufferedReader.close();
        }
        catch (Exception ex) {}
    }
    
    public static List<String> GetConfigs() {
        final ArrayList<String> list = new ArrayList<String>();
        try {
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(ConfigManager.LIST));
            if (ConfigManager.LIST.exists()) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    list.add(line);
                }
            }
            bufferedReader.close();
        }
        catch (Exception ex) {}
        return list;
    }
    
    public static void LoadConfig(final String s) {
        try {
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(getConfigFile(s)));
            if (getConfigFile(s).exists()) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    final String[] split = line.split(":");
                    final Module moduleByName = MedusaWare.instance.mm.getModuleByName(split[1]);
                    if (moduleByName != null) {
                        System.out.println(line);
                        if (split[0].equalsIgnoreCase("set")) {
                            final Setting settingByName = MedusaWare.instance.sm.getSettingByName(moduleByName, split[2]);
                            if (settingByName.isCheck()) {
                                settingByName.setValBooleanNoSave(Boolean.parseBoolean(split[3]));
                            }
                            else if (settingByName.isCombo()) {
                                settingByName.setValStringNoSave(split[3]);
                            }
                            else {
                                if (!settingByName.isSlider()) {
                                    continue;
                                }
                                settingByName.setValDoubleNoSave(Double.parseDouble(split[3]));
                            }
                        }
                        else {
                            if (!split[0].equalsIgnoreCase("mod")) {
                                continue;
                            }
                            if (Boolean.parseBoolean(split[2])) {
                                moduleByName.setToggledNoSave(true);
                            }
                            final int int1 = Integer.parseInt(split[3]);
                            if (int1 == 0) {
                                continue;
                            }
                            moduleByName.setKey(int1);
                        }
                    }
                }
            }
            bufferedReader.close();
        }
        catch (Exception ex) {}
    }
    
    static {
        ConfigManager.dir = new File(Minecraft.getMinecraft().mcDataDir, "config/MedusaWare");
        DEFAULT = getConfigFile("Default");
        FRIENDS = getConfigFile("Friends");
        LIST = getConfigFile("Configs");
    }
}
