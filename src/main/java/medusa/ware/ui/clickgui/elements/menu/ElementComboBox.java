// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.ui.clickgui.elements.menu;

import medusa.ware.ui.clickgui.util.FontUtil;
import medusa.ware.settings.Setting;
import medusa.ware.ui.clickgui.elements.Element;
import medusa.ware.ui.clickgui.elements.ModuleButton;
import medusa.ware.ui.clickgui.util.ColorUtil;
import net.minecraft.client.gui.Gui;
import java.awt.Color;

public class ElementComboBox extends Element
{
    public ElementComboBox(final ModuleButton parent, final Setting set) {
        this.parent = parent;
        this.set = set;
        super.setup();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        final Color clickGUIColor = ColorUtil.getClickGUIColor();
        new Color(clickGUIColor.getRed(), clickGUIColor.getGreen(), clickGUIColor.getBlue(), 200).getRGB();
        final int rgb = new Color(clickGUIColor.getRed(), clickGUIColor.getGreen(), clickGUIColor.getBlue(), 200).darker().getRGB();
        new Color(clickGUIColor.getRed(), clickGUIColor.getGreen(), clickGUIColor.getBlue(), 200).darker().darker().darker().darker().getRGB();
        Gui.drawRect((int)this.x, (int)this.y, (int)this.x + (int)this.width, (int)this.y + (int)this.height, new Color(ColorUtil.getClickGUIColor().getRed(), ColorUtil.getClickGUIColor().getGreen(), ColorUtil.getClickGUIColor().getBlue(), 100).darker().getRGB());
        FontUtil.drawTotalCenteredString(this.setstrg, (float)(this.x + this.width / 2.0), (float)(this.y + 7.0), -1);
        clickGUIColor.getRGB();
        final int n4 = rgb;
        final int rgb2 = new Color(clickGUIColor.getRed(), clickGUIColor.getGreen(), clickGUIColor.getBlue(), 50).getRGB();
        Gui.drawRect((int)this.x, (int)this.y + 14, (int)this.x + (int)this.width, (int)this.y + 15, 1996488704);
        if (this.comboextended) {
            Gui.drawRect((int)this.x, (int)this.y + 15, (int)this.x + (int)this.width, (int)this.y + (int)this.height, -1441656302);
            double n5 = this.y + 15.0;
            for (final String s : this.set.getOptions()) {
                final String value = String.valueOf(new StringBuilder().append(s.substring(0, 1).toUpperCase()).append(s.substring(1, s.length())));
                if (s.equalsIgnoreCase(this.set.getValString())) {
                    Gui.drawRect((int)this.x + (int)this.width, (int)n5, (int)this.x, (int)(n5 + FontUtil.getFontHeight() + 2.0), n4);
                }
                if (n >= this.x && n <= this.x + this.width && n2 >= n5 && n2 < n5 + FontUtil.getFontHeight() + 2.0) {
                    Gui.drawRect((int)this.x, (int)n5, (int)this.x + (int)this.width, (int)(n5 + FontUtil.getFontHeight() + 2.0), rgb2);
                }
                FontUtil.drawCenteredString(value, (float)(this.x + this.width / 2.0), (float)(n5 + 1.0), -1);
                n5 += FontUtil.getFontHeight() + 2;
            }
        }
    }
    
    @Override
    public boolean mouseClicked(final int n, final int n2, final int n3) {
        if (n3 == 0) {
            if (this.isButtonHovered(n, n2)) {
                this.comboextended = !this.comboextended;
                return true;
            }
            if (!this.comboextended) {
                return false;
            }
            double n4 = this.y + 15.0;
            for (final String s : this.set.getOptions()) {
                if (n >= this.x && n <= this.x + this.width && n2 >= n4 && n2 <= n4 + FontUtil.getFontHeight() + 2.0) {
                    if (this.clickgui != null && this.clickgui.setmgr != null) {
                        this.clickgui.setmgr.getSettingByName(this.parent.mod, this.set.getName()).setValString(s.toLowerCase());
                    }
                    return true;
                }
                n4 += FontUtil.getFontHeight() + 2;
            }
        }
        return super.mouseClicked(n, n2, n3);
    }
    
    public boolean isButtonHovered(final int n, final int n2) {
        return n >= this.x && n <= this.x + this.width && n2 >= this.y && n2 <= this.y + 15.0;
    }
}
