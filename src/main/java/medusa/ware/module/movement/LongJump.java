// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.movement;

import medusa.ware.utils.MoveUtils;
import medusa.ware.utils.Game;
import medusa.ware.module.Category;
import medusa.ware.module.Module;

public class LongJump extends Module
{
    public long enabled;
    
    public LongJump() {
        super("LongJump", 0, Category.MOVEMENT, "Automatically jump very far. Best used with a hotkey");
    }
    
    @Override
    public void onMove() {
        if (Game.Player().onGround && System.currentTimeMillis() - this.enabled > 500L) {
            this.setToggled(false);
            return;
        }
        if (!Game.Player().onGround) {
            MoveUtils.setMoveSpeed(2.0);
        }
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (Game.Player().onGround) {
            LongJump.mc.thePlayer.jump();
        }
        else {
            this.setToggled(false);
        }
        this.enabled = System.currentTimeMillis();
    }
}
