package medusa.ware.color;

import net.minecraftforge.common.MinecraftForge;

public class ColorManager
{
    public Chroma cc;
    
    public ColorManager() {
        this.cc = new Chroma();
        MinecraftForge.EVENT_BUS.register((Object)this.cc);
    }
}
