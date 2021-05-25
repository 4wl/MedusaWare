
package medusa.ware.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.entity.EntityPlayerSP;

public class Game
{
    public static EntityPlayerSP Player() {
        return Minecraft().thePlayer;
    }
    
    public static WorldClient World() {
        return Minecraft().theWorld;
    }
    
    public static Minecraft Minecraft() {
        return Minecraft.getMinecraft();
    }
}
