// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.player;

import net.minecraft.util.BlockPos;
import net.minecraft.block.BlockAir;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import medusa.ware.utils.Game;
import medusa.ware.module.Category;
import medusa.ware.module.Module;

public class Fall extends Module
{
    public Fall() {
        super("NoFall", 0, Category.PLAYER, "Completely removes your fall damage");
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void setup() {
    }
    
    @Override
    public void onTick() {
        if (Game.Player().fallDistance > 3.0f && this.isBlockUnderneath()) {
            Fall.mc.getNetHandler().addToSendQueue((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(Game.Player().posX, Game.Player().posY, Game.Player().posZ, Game.Player().rotationYaw, Game.Player().rotationPitch, true));
            Game.Player().fallDistance = 0.0f;
        }
    }
    
    private boolean isBlockUnderneath() {
        boolean b = false;
        for (int n = 0; n < Game.Player().posY + 2.0; ++n) {
            if (!(Fall.mc.theWorld.getBlockState(new BlockPos(Game.Player().posX, (double)n, Game.Player().posZ)).getBlock() instanceof BlockAir)) {
                b = true;
            }
        }
        return b;
    }
    
    public double getDistanceToGround() {
        int n = 0;
        for (int n2 = 0; n2 < Game.Player().posY; ++n2) {
            if (Game.World().isAirBlock(new BlockPos(Game.Player().posX, Game.Player().posY - n2, Game.Player().posZ))) {
                ++n;
            }
        }
        return n;
    }
}
