// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.ui.clickgui;

import medusa.ware.ui.clickgui.elements.menu.ElementSlider;
import java.io.IOException;

import medusa.ware.MedusaWare;
import medusa.ware.settings.SettingsManager;
import org.lwjgl.input.Keyboard;
import medusa.ware.ui.clickgui.elements.Element;
import java.awt.Color;
import medusa.ware.ui.clickgui.util.ColorUtil;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.gui.ScaledResolution;

import java.util.Collections;
import java.util.Iterator;
import medusa.ware.module.Module;
import medusa.ware.module.Category;
import medusa.ware.ui.clickgui.util.FontUtil;
import medusa.ware.ui.clickgui.elements.ModuleButton;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;

public class ClickGUI extends GuiScreen
{
    public static ArrayList<Panel> panels;
    public static ArrayList<Panel> rpanels;
    private ModuleButton mb;
    public SettingsManager setmgr;
    boolean wasFastRenderOn;
    public static boolean enabled;
    
    public ClickGUI() {
        this.mb = null;
        this.setmgr = MedusaWare.instance.sm;
        FontUtil.setupFontUtils();
        (ClickGUI.panels = new ArrayList<Panel>()).clear();
        final double n = 80.0;
        final double n2 = 20.0;
        final double n3 = 10.0;
        double n4 = 10.0;
        final double n5 = n2;
        for (final Category category : Category.values()) {
            ClickGUI.panels.add(new Panel(String.valueOf(new StringBuilder().append(Character.toUpperCase(category.name().toLowerCase().charAt(0))).append(category.name().toLowerCase().substring(1))), n3, n4, n, n2, false, this) {
                @Override
                public void setup() {
                    for (final Module module : MedusaWare.instance.mm.modules) {
                        if (!module.getCategory().equals(category)) {
                            continue;
                        }
                        this.Elements.add(new ModuleButton(module, this));
                    }
                }
            });
            n4 += n5;
        }
        ClickGUI.rpanels = new ArrayList<Panel>();
        final Iterator<Panel> iterator = ClickGUI.panels.iterator();
        while (iterator.hasNext()) {
            ClickGUI.rpanels.add(iterator.next());
        }
        Collections.reverse(ClickGUI.rpanels);
    }
    
    public void drawScreen(final int n, final int n2, final float n3) {
        final Iterator<Panel> iterator = ClickGUI.panels.iterator();
        while (iterator.hasNext()) {
            iterator.next().drawScreen(n, n2, n3);
        }
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        GL11.glPushMatrix();
        GL11.glTranslated((double)scaledResolution.getScaledWidth(), (double)scaledResolution.getScaledHeight(), 0.0);
        GL11.glScaled(0.5, 0.5, 0.5);
        GL11.glPopMatrix();
        this.mb = null;
    Label_0218:
        for (final Panel panel : ClickGUI.panels) {
            if (panel != null && panel.visible && panel.extended && panel.Elements != null && panel.Elements.size() > 0) {
                for (final ModuleButton mb : panel.Elements) {
                    if (mb.listening) {
                        this.mb = mb;
                        break Label_0218;
                    }
                }
            }
        }
        for (final Panel panel2 : ClickGUI.panels) {
            if (panel2.extended && panel2.visible && panel2.Elements != null) {
                for (final ModuleButton moduleButton : panel2.Elements) {
                    if (moduleButton.extended && moduleButton.menuelements != null && !moduleButton.menuelements.isEmpty()) {
                        double offset = -1.0;
                        final Color darker = ColorUtil.getClickGUIColor().darker();
                        new Color(darker.getRed(), darker.getGreen(), darker.getBlue(), 255).getRGB();
                        for (final Element element : moduleButton.menuelements) {
                            if (!element.set.isVisible()) {
                                element.offset = offset;
                                element.update();
                                element.drawScreen(n, n2, n3);
                                offset += element.height;
                            }
                        }
                    }
                }
            }
        }
        if (this.mb != null) {
            drawRect(0, 0, this.width, this.height, -2012213232);
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(scaledResolution.getScaledWidth() / 2), (float)(scaledResolution.getScaledHeight() / 2), 0.0f);
            FontUtil.drawBigTotalCenteredStringWithShadow("Listening...", 0.0f, -20.0f, -1);
            FontUtil.drawBigTotalCenteredStringWithShadow(String.valueOf(new StringBuilder().append("Press 'ESCAPE' to unbind ").append(this.mb.mod.getName()).append((this.mb.mod.getKey() > -1) ? String.valueOf(new StringBuilder().append(" (").append(Keyboard.getKeyName(this.mb.mod.getKey())).append(")")) : "")), 0.0f, 0.0f, -1);
            GL11.glPopMatrix();
        }
        super.drawScreen(n, n2, n3);
    }
    
    public void mouseClicked(final int n, final int n2, final int n3) {
        if (this.mb != null) {
            return;
        }
        for (final Panel panel : ClickGUI.rpanels) {
            if (panel.extended && panel.visible && panel.Elements != null) {
                for (final ModuleButton moduleButton : panel.Elements) {
                    if (moduleButton.extended) {
                        for (final Element element : moduleButton.menuelements) {
                            if (!element.set.isVisible() && element.mouseClicked(n, n2, n3)) {
                                return;
                            }
                        }
                    }
                }
            }
        }
        final Iterator<Panel> iterator4 = ClickGUI.rpanels.iterator();
        while (iterator4.hasNext()) {
            if (iterator4.next().mouseClicked(n, n2, n3)) {
                return;
            }
        }
        try {
            super.mouseClicked(n, n2, n3);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void mouseReleased(final int n, final int n2, final int n3) {
        if (this.mb != null) {
            return;
        }
        for (final Panel panel : ClickGUI.rpanels) {
            if (panel.extended && panel.visible && panel.Elements != null) {
                for (final ModuleButton moduleButton : panel.Elements) {
                    if (moduleButton.extended) {
                        final Iterator<Element> iterator3 = moduleButton.menuelements.iterator();
                        while (iterator3.hasNext()) {
                            iterator3.next().mouseReleased(n, n2, n3);
                        }
                    }
                }
            }
        }
        final Iterator<Panel> iterator4 = ClickGUI.rpanels.iterator();
        while (iterator4.hasNext()) {
            iterator4.next().mouseReleased(n, n2, n3);
        }
        super.mouseReleased(n, n2, n3);
    }
    
    protected void keyTyped(final char c, final int n) {
        for (final Panel panel : ClickGUI.rpanels) {
            if (panel != null && panel.visible && panel.extended && panel.Elements != null && panel.Elements.size() > 0) {
                for (final ModuleButton moduleButton : panel.Elements) {
                    try {
                        if (moduleButton.keyTyped(c, n)) {
                            return;
                        }
                        continue;
                    }
                    catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        try {
            super.keyTyped(c, n);
        }
        catch (IOException ex2) {
            ex2.printStackTrace();
        }
    }
    
    public void initGui() {
    }
    
    public void onGuiClosed() {
        for (final Panel panel : ClickGUI.rpanels) {
            if (panel.extended && panel.visible && panel.Elements != null) {
                for (final ModuleButton moduleButton : panel.Elements) {
                    if (moduleButton.extended) {
                        for (final Element element : moduleButton.menuelements) {
                            if (element instanceof ElementSlider) {
                                ((ElementSlider)element).dragging = false;
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void closeAllSettings() {
        for (final Panel panel : ClickGUI.rpanels) {
            if (panel != null && panel.visible && panel.extended && panel.Elements != null && panel.Elements.size() > 0) {
                final Iterator<ModuleButton> iterator2 = panel.Elements.iterator();
                while (iterator2.hasNext()) {
                    iterator2.next().extended = false;
                }
            }
        }
    }
    
    static {
        ClickGUI.enabled = false;
    }
}
