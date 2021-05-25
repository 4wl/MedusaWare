
package medusa.ware.ui.clickgui.elements;

import java.io.IOException;
import medusa.ware.utils.ChatUtils;
import medusa.ware.MedusaWare;
import medusa.ware.settings.Setting;
import medusa.ware.ui.clickgui.ClickGUI;
import medusa.ware.ui.clickgui.Panel;
import medusa.ware.ui.clickgui.util.ColorUtil;
import org.lwjgl.input.Keyboard;
import medusa.ware.ui.clickgui.util.FontUtil;
import net.minecraft.client.gui.Gui;
import java.awt.Color;

import medusa.ware.ui.clickgui.elements.menu.ElementComboBox;
import medusa.ware.ui.clickgui.elements.menu.ElementSlider;
import medusa.ware.ui.clickgui.elements.menu.ElementCheckBox;
import net.minecraft.client.Minecraft;
import medusa.ware.utils.TimerUtils;

import java.util.ArrayList;
import medusa.ware.module.Module;

public class ModuleButton
{
    public Module mod;
    public ArrayList<Element> menuelements;
    public Panel parent;
    public double x;
    public double y;
    public double width;
    public double height;
    public boolean extended;
    public boolean listening;
    private TimerUtils timer;
    
    public ModuleButton(final Module mod, final Panel parent) {
        this.extended = false;
        this.listening = false;
        this.mod = mod;
        this.height = Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 6;
        this.parent = parent;
        (this.menuelements = new ArrayList<Element>()).clear();
        if (MedusaWare.instance.sm.getSettingsByMod(mod) != null) {
            for (final Setting setting : MedusaWare.instance.sm.getSettingsByMod(mod)) {
                if (setting.isCheck()) {
                    this.menuelements.add(new ElementCheckBox(this, setting));
                }
                else if (setting.isSlider()) {
                    this.menuelements.add(new ElementSlider(this, setting));
                }
                else {
                    if (!setting.isCombo()) {
                        continue;
                    }
                    this.menuelements.add(new ElementComboBox(this, setting));
                }
            }
        }
        this.timer = new TimerUtils();
    }
    
    public void drawScreen(final int n, final int n2, final float n3) {
        final Color clickGUIColor = ColorUtil.getClickGUIColor();
        final int rgb = new Color(clickGUIColor.getRed(), clickGUIColor.getGreen(), clickGUIColor.getBlue(), 150).getRGB();
        ColorUtil.getClickGUIColor().getRGB();
        if (this.mod.isToggled()) {
            Gui.drawRect((int)this.x - 2, (int)this.y, (int)this.x + (int)this.width + 2, (int)this.y + (int)this.height, rgb);
            ColorUtil.getClickGUIColor().brighter().getRGB();
        }
        if (this.isHovered(n, n2)) {
            Gui.drawRect((int)this.x - 2, (int)this.y, (int)this.x + (int)this.width + 2, (int)this.y + (int)this.height + 1, 1427181841);
            if (this.timer.hasReached(1000.0)) {
                final ClickGUI clickGui = MedusaWare.instance.clickGui;
                ClickGUI.drawRect(n - 1, n2, (int)(n + 1 + FontUtil.getStringWidth(this.mod.getDescription())), (int)(n2 - FontUtil.getFontHeight() - 2.5), ColorUtil.getClickGUIColor().darker().darker().getRGB());
                FontUtil.drawString(this.mod.getDescription(), (float)(n + 1), (float)(n2 - FontUtil.getFontHeight() * 1.25), ColorUtil.getClickGUIColor().brighter().brighter().getRGB());
            }
        }
        else {
            this.timer.reset();
        }
    }
    
    public void onUpdate() {
        FontUtil.drawTotalCenteredStringWithShadow(this.mod.getName(), (float)(this.x + FontUtil.getStringWidth(this.mod.getName()) / 2.0f), (float)(this.y + 1.0 + this.height / 2.0), ColorUtil.getClickGUIColor().brighter().getRGB());
    }
    
    public boolean mouseClicked(final int n, final int n2, final int n3) {
        if (!this.isHovered(n, n2)) {
            return false;
        }
        if (n3 == 0) {
            this.mod.toggle();
        }
        else if (n3 == 1) {
            if (this.menuelements != null && this.menuelements.size() > 0) {
                this.extended = !this.extended;
            }
        }
        else if (n3 == 2) {
            this.listening = true;
        }
        return true;
    }
    
    public boolean keyTyped(final char c, final int key) throws IOException {
        if (this.listening) {
            if (key != 1) {
                ChatUtils.sendMessage(String.valueOf(new StringBuilder().append("Bound '").append(this.mod.getName()).append("' to '").append(Keyboard.getKeyName(key)).append("'")));
                this.mod.setKey(key);
            }
            else {
                ChatUtils.sendMessage(String.valueOf(new StringBuilder().append("Unbound '").append(this.mod.getName()).append("'")));
                this.mod.setKey(0);
            }
            this.listening = false;
            return true;
        }
        return false;
    }
    
    public boolean isHovered(final int n, final int n2) {
        return n >= this.x && n <= this.x + this.width && n2 >= this.y && n2 <= this.y + this.height;
    }
}
