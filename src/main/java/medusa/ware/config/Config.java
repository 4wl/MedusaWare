// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.config;

import java.io.File;

public class Config
{
    private static File file;
    private static String name;
    
    public Config(final File file, final String name) {
        Config.file = file;
        Config.name = name;
    }
    
    public File getFile() {
        return Config.file;
    }
    
    public String getName() {
        return Config.name;
    }
}
