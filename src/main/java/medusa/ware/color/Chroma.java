// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.color;

import java.awt.Color;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import medusa.ware.utils.TimerUtils;

public class Chroma
{
    private TimerUtils timer;
    private int lastHue;
    
    public Chroma() {
        this.timer = new TimerUtils();
    }
    
    @SubscribeEvent
    public void render(final RenderGameOverlayEvent renderGameOverlayEvent) {
        final RenderGameOverlayEvent.ElementType type = renderGameOverlayEvent.type;
        final RenderGameOverlayEvent.ElementType type2 = renderGameOverlayEvent.type;
        if (type.equals((Object)RenderGameOverlayEvent.ElementType.TEXT) && this.timer.hasReached(33.0)) {
            this.changeColor();
            this.timer.reset();
        }
    }
    
    public Color getColor(final int n) {
        int i;
        for (i = this.lastHue + n * 16; i > 1000; i -= 1000) {}
        return Color.getHSBColor(i / 1000.0f, 1.0f, 1.0f);
    }
    
    private void changeColor() {
        this.lastHue += 5;
        if (this.lastHue > 1000) {
            this.lastHue = 0;
        }
    }
}
