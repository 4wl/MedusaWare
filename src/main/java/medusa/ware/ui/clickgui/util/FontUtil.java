// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.ui.clickgui.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.StringUtils;
import medusa.ware.utils.Game;

public class FontUtil
{
    public static void setupFontUtils() {
    }
    
    public static float getStringWidth(final String s) {
        return (float)Game.Minecraft().fontRendererObj.getStringWidth(StringUtils.stripControlCodes(s));
    }
    
    public static int getFontHeight() {
        return Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
    }
    
    public static void drawString(final String s, final float n, final float n2, final int n3) {
        Game.Minecraft().fontRendererObj.drawStringWithShadow(s, n, n2, n3);
    }
    
    public static void drawStringWithShadow(final String s, final float n, final float n2, final int n3) {
        Game.Minecraft().fontRendererObj.drawStringWithShadow(s, n, n2, n3);
    }
    
    public static void drawCenteredString(final String s, final float n, final float n2, final int n3) {
        Game.Minecraft().fontRendererObj.drawStringWithShadow(s, n - getStringWidth(s) / 2.0f, n2, n3);
    }
    
    public static void drawCenteredStringWithShadow(final String s, final float n, final float n2, final int n3) {
        drawStringWithShadow(s, n - getStringWidth(s) / 2.0f, n2, n3);
    }
    
    public static void drawTotalCenteredString(final String s, final float n, final float n2, final int n3) {
        Game.Minecraft().fontRendererObj.drawString(s, (int)(n - getStringWidth(s) / 2.0f), (int)(n2 - getFontHeight() / 2), n3);
    }
    
    public static void drawTotalCenteredStringWithShadow(final String s, final float n, final float n2, final int n3) {
        drawStringWithShadow(s, n - getStringWidth(s) / 2.0f, n2 - getFontHeight() / 2.0f, n3);
    }
    
    public static void drawBigTotalCenteredStringWithShadow(final String s, final float n, final float n2, final int n3) {
        Game.Minecraft().fontRendererObj.drawStringWithShadow(s, n - getStringWidth(s) / 2.0f, n2 - Game.Minecraft().fontRendererObj.FONT_HEIGHT / 2.0f, n3);
    }
}
