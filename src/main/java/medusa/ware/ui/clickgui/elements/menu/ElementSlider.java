
package medusa.ware.ui.clickgui.elements.menu;

import medusa.ware.settings.Setting;
import medusa.ware.ui.clickgui.elements.Element;
import medusa.ware.ui.clickgui.elements.ModuleButton;
import medusa.ware.ui.clickgui.util.ColorUtil;
import medusa.ware.ui.clickgui.util.FontUtil;
import net.minecraft.util.MathHelper;
import net.minecraft.client.gui.Gui;
import java.awt.Color;

public class ElementSlider extends Element
{
    public boolean dragging;
    
    public ElementSlider(final ModuleButton parent, final Setting set) {
        this.parent = parent;
        this.set = set;
        this.dragging = false;
        super.setup();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        final String value = String.valueOf(new StringBuilder().append("").append(Math.round(this.set.getValDouble() * 100.0) / 100.0));
        final boolean b = this.isSliderHovered(n, n2) || this.dragging;
        final Color clickGUIColor = ColorUtil.getClickGUIColor();
        final int rgb = new Color(clickGUIColor.getRed(), clickGUIColor.getGreen(), clickGUIColor.getBlue(), b ? 250 : 200).getRGB();
        new Color(clickGUIColor.getRed(), clickGUIColor.getGreen(), clickGUIColor.getBlue(), b ? 255 : 230).getRGB();
        new Color(clickGUIColor.getRed(), clickGUIColor.getGreen(), clickGUIColor.getBlue(), 200).darker().darker().darker().darker().getRGB();
        final int rgb2 = new Color(ColorUtil.getClickGUIColor().getRed(), ColorUtil.getClickGUIColor().getGreen(), ColorUtil.getClickGUIColor().getBlue(), 100).darker().getRGB();
        final double n4 = (this.set.getValDouble() - this.set.getMin()) / (this.set.getMax() - this.set.getMin());
        Gui.drawRect((int)this.x, (int)this.y, (int)this.x + (int)this.width, (int)this.y + (int)this.height, rgb2);
        Gui.drawRect((int)this.x, (int)this.y + 12, (int)this.x + (int)this.width, (int)this.y + 2, -15724528);
        Gui.drawRect((int)this.x, (int)this.y + 12, (int)this.x + (int)(n4 * this.width), (int)this.y + 2, rgb);
        FontUtil.drawString(this.setstrg, (float)(this.x + 1.0), (float)(this.y + 3.0), -1);
        String s = "";
        if (value.endsWith(".0")) {
            for (int index = 0; index < value.chars().count() - 2L; ++index) {
                s = String.valueOf(new StringBuilder().append(s).append(value.charAt(index)));
            }
        }
        else {
            s = value;
        }
        if (this.set.percentage) {
            s = String.valueOf(new StringBuilder().append(s).append("%"));
        }
        FontUtil.drawString(s, (float)(this.x + this.width - FontUtil.getStringWidth(s)), (float)(this.y + 3.0), -1);
        if (this.dragging) {
            this.set.setValDouble(this.set.getMin() + MathHelper.clamp_double((n - this.x) / this.width, 0.0, 1.0) * (this.set.getMax() - this.set.getMin()));
        }
    }
    
    @Override
    public boolean mouseClicked(final int n, final int n2, final int n3) {
        if (n3 == 0 && this.isSliderHovered(n, n2)) {
            return this.dragging = true;
        }
        return super.mouseClicked(n, n2, n3);
    }
    
    @Override
    public void mouseReleased(final int n, final int n2, final int n3) {
        this.dragging = false;
    }
    
    public boolean isSliderHovered(final int n, final int n2) {
        return n >= this.x && n <= this.x + this.width && n2 >= this.y + 2.0 && n2 <= this.y + 14.0;
    }
}
