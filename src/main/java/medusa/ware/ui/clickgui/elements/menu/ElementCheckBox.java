
package medusa.ware.ui.clickgui.elements.menu;

import medusa.ware.ui.clickgui.util.FontUtil;
import medusa.ware.settings.Setting;
import medusa.ware.ui.clickgui.elements.Element;
import medusa.ware.ui.clickgui.elements.ModuleButton;
import medusa.ware.ui.clickgui.util.ColorUtil;
import net.minecraft.client.gui.Gui;
import java.awt.Color;

public class ElementCheckBox extends Element
{
    private long prevTime;
    private long prevTimeHover;
    private boolean wasChecked;
    
    public ElementCheckBox(final ModuleButton parent, final Setting set) {
        this.prevTime = System.currentTimeMillis();
        this.prevTimeHover = System.currentTimeMillis();
        this.wasChecked = false;
        this.parent = parent;
        this.set = set;
        super.setup();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        final Color clickGUIColor = ColorUtil.getClickGUIColor();
        final int rgb = new Color(clickGUIColor.getRed(), clickGUIColor.getGreen(), clickGUIColor.getBlue(), 200).getRGB();
        final int rgb2 = new Color(clickGUIColor.getRed(), clickGUIColor.getGreen(), clickGUIColor.getBlue(), 100).darker().getRGB();
        new Color(clickGUIColor.getRed(), clickGUIColor.getGreen(), clickGUIColor.getBlue(), 200).darker().darker().darker().darker().getRGB();
        final int rgb3 = new Color(clickGUIColor.getRed(), clickGUIColor.getGreen(), clickGUIColor.getBlue(), 200).darker().darker().darker().darker().darker().darker().darker().darker().getRGB();
        Gui.drawRect((int)this.x, (int)this.y, (int)(this.x + this.width), (int)(this.y + this.height), new Color(ColorUtil.getClickGUIColor().getRed(), ColorUtil.getClickGUIColor().getGreen(), ColorUtil.getClickGUIColor().getBlue(), 100).darker().getRGB());
        FontUtil.drawString(this.setstrg, (float)(this.x + this.width - FontUtil.getStringWidth(this.setstrg)), (float)(this.y + FontUtil.getFontHeight() / 2 - 0.5), -1);
        Gui.drawRect((int)this.x + 1, (int)this.y + 2, (int)this.x + 12, (int)this.y + 13, rgb3);
        final long n4 = System.currentTimeMillis() - this.prevTime;
        if (this.set.getValBoolean()) {
            if (n4 > 250L) {
                Gui.drawRect((int)this.x + 2, (int)this.y + 3, (int)this.x + 11, (int)this.y + 12, rgb);
            }
            else {
                Gui.drawRect((int)this.x + 2, (int)this.y + 3, (int)this.x + 11, (int)(this.y + 3.0 + n4 / 25L), rgb);
            }
        }
        else if (n4 <= 250L) {
            if (n4 > 250L) {
                Gui.drawRect((int)this.x + 2, (int)this.y + 3, (int)this.x + 11, (int)this.y + 12, rgb);
            }
            else {
                Gui.drawRect((int)this.x + 2, (int)this.y + 3, (int)this.x + 11, (int)(this.y + 12.0 - n4 / 25L), rgb);
            }
        }
        if (this.isCheckHovered(n, n2)) {
            if (!this.wasChecked) {
                this.prevTimeHover = System.currentTimeMillis();
            }
            final long n5 = System.currentTimeMillis() - this.prevTimeHover;
            if (n5 <= 100L && !this.set.getValBoolean()) {
                Gui.drawRect((int)this.x + 2, (int)this.y + 3, (int)this.x + 11, (int)(this.y + 3.0 + n5 / 10.7777778), rgb2);
            }
            else if (n4 > 250L) {
                Gui.drawRect((int)this.x + 2, (int)this.y + 3, (int)this.x + 11, (int)this.y + 12, rgb2);
            }
            this.wasChecked = true;
        }
        else {
            this.wasChecked = false;
        }
        if (this.set.getValBoolean()) {
            this.prevTimeHover = System.currentTimeMillis();
        }
    }
    
    @Override
    public boolean mouseClicked(final int n, final int n2, final int n3) {
        if (n3 == 0 && this.isCheckHovered(n, n2)) {
            final long n4 = System.currentTimeMillis() - this.prevTime;
            final long n5 = System.currentTimeMillis() - this.prevTimeHover;
            if (n4 > 250L) {
                this.set.setValBoolean(!this.set.getValBoolean());
                this.prevTime = System.currentTimeMillis();
                return super.mouseClicked(n, n2, n3);
            }
        }
        return super.mouseClicked(n, n2, n3);
    }
    
    public boolean isCheckHovered(final int n, final int n2) {
        final boolean b = n >= this.x + 1.0 && n <= this.x + 12.0 && n2 >= this.y + 2.0 && n2 <= this.y + 13.0;
        if (this.set.getName().equalsIgnoreCase("clickaim")) {}
        return b;
    }
}
