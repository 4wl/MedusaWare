

package medusa.ware.ui.clickgui;

import java.util.Iterator;
import medusa.ware.ui.clickgui.util.FontUtil;
import medusa.ware.ui.clickgui.elements.Element;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import medusa.ware.ui.clickgui.util.ColorUtil;
import medusa.ware.ui.clickgui.elements.ModuleButton;
import java.util.ArrayList;

public class Panel
{
    public String title;
    public double x;
    public double y;
    private double x2;
    private double y2;
    public double width;
    public double height;
    public boolean dragging;
    public boolean extended;
    public boolean visible;
    public ArrayList<ModuleButton> Elements;
    public ClickGUI clickgui;
    
    public Panel(final String title, final double x, final double y, final double width, final double height, final boolean extended, final ClickGUI clickgui) {
        this.title = title;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.extended = extended;
        this.dragging = false;
        this.visible = true;
        this.clickgui = clickgui;
        (this.Elements = new ArrayList<ModuleButton>()).clear();
        this.setup();
    }
    
    public void setup() {
    }
    
    public void drawScreen(final int n, final int n2, final float n3) {
        if (!this.visible) {
            return;
        }
        if (this.dragging) {
            this.x = this.x2 + n;
            this.y = this.y2 + n2;
        }
        final Color darker = new Color(ColorUtil.getClickGUIColor().getRed(), ColorUtil.getClickGUIColor().getGreen(), ColorUtil.getClickGUIColor().getBlue(), 0).darker();
        final int rgb = new Color(darker.getRed(), darker.getGreen(), darker.getBlue(), 200).getRGB();
        Gui.drawRect((int)this.x, (int)this.y, (int)this.x + (int)this.width, (int)this.y + (int)this.height, -15592942);
        Gui.drawRect((int)this.x, (int)this.y, (int)this.x + 80, (int)this.y + (int)this.height, rgb);
        if (this.extended && !this.Elements.isEmpty()) {
            double n4 = this.y + this.height;
            final int rgb2 = new Color(ColorUtil.getClickGUIColor().getRed(), ColorUtil.getClickGUIColor().getGreen(), ColorUtil.getClickGUIColor().getBlue(), 100).darker().darker().getRGB();
            for (final ModuleButton moduleButton : this.Elements) {
                Element element = null;
                Gui.drawRect((int)this.x, (int)n4, (int)this.x + (int)this.width, (int)n4 + (int)moduleButton.height + 1, rgb2);
                moduleButton.x = this.x + 2.0;
                moduleButton.y = n4 + 1.0;
                moduleButton.width = this.width - 4.0;
                moduleButton.drawScreen(n, n2, n3);
                if (moduleButton.extended) {
                    int n5 = 1;
                    for (final Element element2 : moduleButton.menuelements) {
                        if (!element2.set.isVisible()) {
                            n5 += (int)element2.height;
                            element = element2;
                        }
                    }
                    n4 += moduleButton.height + n5;
                }
                else {
                    n4 += moduleButton.height + 1.0;
                }
                if (element != null) {
                    element.setEndEl(true);
                    for (final Element element3 : moduleButton.menuelements) {
                        if (element3 != element) {
                            element3.setEndEl(false);
                        }
                    }
                }
            }
            FontUtil.drawStringWithShadow(this.title, (float)(this.x + 5.0), (float)(this.y + this.height / 2.0 - FontUtil.getFontHeight() / 2), -1052689);
            final Iterator<ModuleButton> iterator4 = this.Elements.iterator();
            while (iterator4.hasNext()) {
                iterator4.next().onUpdate();
            }
        }
        else {
            FontUtil.drawStringWithShadow(this.title, (float)(this.x + 5.0), (float)(this.y + this.height / 2.0 - FontUtil.getFontHeight() / 2), -1052689);
        }
    }
    
    public boolean mouseClicked(final int n, final int n2, final int n3) {
        if (!this.visible) {
            return false;
        }
        if (n3 == 0 && this.isHovered(n, n2)) {
            this.x2 = this.x - n;
            this.y2 = this.y - n2;
            return this.dragging = true;
        }
        if (n3 == 1 && this.isHovered(n, n2)) {
            this.extended = !this.extended;
            return true;
        }
        if (this.extended) {
            final Iterator<ModuleButton> iterator = this.Elements.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().mouseClicked(n, n2, n3)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void mouseReleased(final int n, final int n2, final int n3) {
        if (!this.visible) {
            return;
        }
        if (n3 == 0) {
            this.dragging = false;
        }
    }
    
    public boolean isHovered(final int n, final int n2) {
        return n >= this.x && n <= this.x + this.width && n2 >= this.y && n2 <= this.y + this.height;
    }
}
