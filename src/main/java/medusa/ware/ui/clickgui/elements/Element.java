
package medusa.ware.ui.clickgui.elements;

import java.util.Iterator;

import medusa.ware.settings.Setting;
import medusa.ware.ui.clickgui.ClickGUI;
import medusa.ware.ui.clickgui.util.FontUtil;
import net.minecraft.client.gui.Gui;
import java.awt.Color;

public class Element
{
    public ClickGUI clickgui;
    public ModuleButton parent;
    public Setting set;
    public double offset;
    public double x;
    public double y;
    public double width;
    public double height;
    private boolean isEnd;
    public String setstrg;
    public boolean comboextended;
    
    public void setup() {
        this.clickgui = this.parent.parent.clickgui;
    }
    
    public void update() {
        this.x = this.parent.x - 2.0;
        this.y = this.parent.y + this.offset + this.parent.height + 1.0;
        this.width = this.parent.width + 4.0;
        this.height = 15.0;
        final String name = this.set.getName();
        if (this.set.isCheck()) {
            this.setstrg = String.valueOf(new StringBuilder().append(name.substring(0, 1).toUpperCase()).append(name.substring(1, name.length())));
            final double n = this.x + this.width - FontUtil.getStringWidth(this.setstrg);
            if (n < this.x + 13.0) {
                this.width += this.x + 13.0 - n + 1.0;
            }
        }
        else if (this.set.isCombo()) {
            this.height = (this.comboextended ? (this.set.getOptions().size() * (FontUtil.getFontHeight() + 2) + 15) : 15.0);
            this.setstrg = String.valueOf(new StringBuilder().append(name.substring(0, 1).toUpperCase()).append(name.substring(1, name.length())));
            float stringWidth = FontUtil.getStringWidth(this.setstrg);
            final Iterator<String> iterator = this.set.getOptions().iterator();
            while (iterator.hasNext()) {
                final float stringWidth2 = FontUtil.getStringWidth(iterator.next());
                if (stringWidth2 > stringWidth) {
                    stringWidth = stringWidth2;
                }
            }
            final double n2 = this.x + this.width - stringWidth;
            if (n2 < this.x) {
                this.width += this.x - n2 + 1.0;
            }
        }
        else if (this.set.isSlider()) {
            this.setstrg = String.valueOf(new StringBuilder().append(name.substring(0, 1).toUpperCase()).append(name.substring(1, name.length())));
            String.valueOf(new StringBuilder().append("").append(Math.round(this.set.getValDouble() * 100.0) / 100.0));
            final double n3 = this.x + this.width - FontUtil.getStringWidth(this.setstrg) - FontUtil.getStringWidth(String.valueOf(new StringBuilder().append("").append(Math.round(this.set.getMax() * 100.0) / 100.0))) - 4.0;
            if (n3 < this.x - 13.0) {
                this.width += this.x - n3 + 13.0;
            }
        }
        if (this.isEnd) {
            Gui.drawRect((int)this.x, (int)this.y + 14, (int)this.x + (int)this.width, (int)this.y + 16, new Color(0, 0, 0, 175).getRGB());
        }
    }
    
    public void setEndEl(final boolean isEnd) {
        this.isEnd = isEnd;
    }
    
    public void drawScreen(final int n, final int n2, final float n3) {
    }
    
    public boolean mouseClicked(final int n, final int n2, final int n3) {
        return this.isHovered(n, n2);
    }
    
    public void mouseReleased(final int n, final int n2, final int n3) {
    }
    
    public boolean isHovered(final int n, final int n2) {
        return n >= this.x && n <= this.x + this.width && n2 >= this.y && n2 <= this.y + this.height;
    }
}
