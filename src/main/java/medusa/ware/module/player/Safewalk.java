// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.player;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraft.init.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import medusa.ware.utils.Game;
import medusa.ware.module.Category;
import medusa.ware.module.Module;

public class Safewalk extends Module
{
    public Safewalk() {
        super("Safewalk", 0, Category.PLAYER, "Auto Bridge");
    }
    
    @Override
    public void onTick() {
        if (Safewalk.mc.thePlayer == null || Game.World() == null || !Game.Player().onGround) {
            return;
        }
        System.out.println(String.valueOf(new StringBuilder().append((int)Game.Player().posX - Game.Player().posX).append(" ").append(Game.Player().posZ - (int)Game.Player().posZ)));
        if (Game.World().getBlockState(new BlockPos((Entity)Game.Player()).add(0, -1, 0)).getBlock() == Blocks.air) {
            Safewalk.mc.thePlayer.motionX = 0.0;
            Safewalk.mc.thePlayer.motionY = 0.0;
            Safewalk.mc.thePlayer.motionZ = 0.0;
            Safewalk.mc.thePlayer.jumpMovementFactor = 0.0f;
            Safewalk.mc.thePlayer.noClip = true;
            Safewalk.mc.thePlayer.onGround = false;
        }
    }
    
    @SubscribeEvent
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent livingUpdateEvent) {
    }
    
    @Override
    public void onDisable() {
    }
}
