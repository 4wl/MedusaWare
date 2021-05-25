// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.player;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Mouse;
import medusa.ware.utils.Game;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import medusa.ware.module.Category;
import medusa.ware.utils.TimerUtils;
import medusa.ware.module.Module;

public class AutoMine extends Module
{
    private TimerUtils timer;
    
    public AutoMine() {
        super("AutoMine", 0, Category.PLAYER, "Automatically mines for you");
        this.timer = new TimerUtils();
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.timer.reset();
        System.out.println();
    }
    
    @SubscribeEvent
    public void rty(final TickEvent.RenderTickEvent renderTickEvent) {
        if (AutoMine.mc.objectMouseOver != null && AutoMine.mc.objectMouseOver.getBlockPos() != null && !Game.World().isAirBlock(AutoMine.mc.objectMouseOver.getBlockPos()) && !Mouse.isButtonDown(0)) {
            final int keyCode = AutoMine.mc.gameSettings.keyBindAttack.getKeyCode();
            KeyBinding.setKeyBindState(keyCode, true);
            KeyBinding.onTick(keyCode);
            this.timer.reset();
        }
        else if (this.timer.hasReached(30.0)) {
            KeyBinding.setKeyBindState(AutoMine.mc.gameSettings.keyBindAttack.getKeyCode(), false);
        }
    }
}
