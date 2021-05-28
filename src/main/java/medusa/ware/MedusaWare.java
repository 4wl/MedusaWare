// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware;

import medusa.ware.commands.BindCommand;
import medusa.ware.commands.ConfigCommand;
import medusa.ware.commands.FriendCommand;
import medusa.ware.commands.ToggleCommand;
import medusa.ware.friends.FriendManager;
import medusa.ware.settings.SettingsManager;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.input.Keyboard;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.INetHandler;
import net.minecraft.client.network.NetHandlerPlayClient;
import medusa.ware.net.NetHandler;
import medusa.ware.utils.Game;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.File;

import medusa.ware.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommand;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import medusa.ware.config.ConfigManager;
import medusa.ware.ui.hud.HUDRenderer;
import medusa.ware.color.ColorManager;
import medusa.ware.ui.clickgui.ClickGUI;
import medusa.ware.module.ModuleManager;

public class MedusaWare
{
    public static final String MODID;
    public static final String NAME;
    public static final String VERSION;
    public static MedusaWare instance;
    public ModuleManager mm;
    public SettingsManager sm;
    public ClickGUI clickGui;
    public ColorManager cm;
    public HUDRenderer uiRenderer;
    public ConfigManager configManager;
    public FriendManager friendManager;
    public static boolean destructed;
    
    public void onInit() {
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.sm = new SettingsManager();
        this.mm = new ModuleManager();
        this.clickGui = new ClickGUI();
        this.cm = new ColorManager();
        this.uiRenderer = new HUDRenderer();
        this.configManager = new ConfigManager();
        final ConfigManager configManager = this.configManager;
        ConfigManager.init();
        this.friendManager = new FriendManager();
        if (!medusa.ware.MedusaWare.destructed) {
            this.registerCommands();
        }
    }
    
    public void registerCommands() {
        ClientCommandHandler.instance.registerCommand((ICommand)new FriendCommand());
        ClientCommandHandler.instance.registerCommand((ICommand)new ToggleCommand());
        ClientCommandHandler.instance.registerCommand((ICommand)new BindCommand());
        ClientCommandHandler.instance.registerCommand((ICommand)new ConfigCommand());
    }
    
    public void onSelfDestruct() {
        Minecraft.getMinecraft().currentScreen = null;
        for (final Module module : this.mm.modules) {
            MinecraftForge.EVENT_BUS.unregister((Object)module);
            module.setToggledNoSave(false);
        }
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        medusa.ware.MedusaWare.destructed = true;
        this.configManager = null;
        this.uiRenderer = null;
        this.clickGui = null;
        this.mm = null;
        this.sm = null;
        me.aycy.blockoverlay.MedusaWare.saveConfig();
        final File file = new File(Minecraft.getMinecraft().mcDataDir, "config/MedusaWare");
        final String[] list = file.list();
        for (int i = 0; i < list.length; ++i) {
            new File(file.getPath(), list[i]).delete();
        }
        file.delete();
    }
    
    @SubscribeEvent
    public void ClientTick(final TickEvent.ClientTickEvent clientTickEvent) {
        if (Game.World() != null) {
            final INetHandler netHandler = Game.Player().sendQueue.getNetworkManager().getNetHandler();
            if (!(netHandler instanceof NetHandler)) {
                Game.Player().sendQueue.getNetworkManager().setNetHandler((INetHandler)new NetHandler((NetHandlerPlayClient)netHandler));
            }
        }
        if (medusa.ware.MedusaWare.destructed) {
            return;
        }
        for (final Module module : this.mm.getModules()) {
            if (Game.World() != null && Game.Player() != null) {
                module.onUpdateNoToggle();
                if (!module.isToggled()) {
                    continue;
                }
                module.onUpdate();
            }
        }
    }
    
    @SubscribeEvent
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent livingUpdateEvent) {
        if (livingUpdateEvent.entityLiving != null && livingUpdateEvent.entityLiving == Game.Player()) {
            for (final Module module : this.mm.getModules()) {
                if (module.isToggled() && Game.World() != null && Game.Player() != null) {
                    module.onMove();
                }
            }
        }
    }
    
    @SubscribeEvent
    public void PlayerTick(final TickEvent.PlayerTickEvent playerTickEvent) {
        if (medusa.ware.MedusaWare.destructed) {
            return;
        }
        for (final Module module : this.mm.getModules()) {
            if (module.isToggled() && Game.World() != null && Game.Player() != null) {
                module.onTick();
            }
        }
    }
    
    @SubscribeEvent
    public void key(final InputEvent.KeyInputEvent keyInputEvent) {
        if (medusa.ware.MedusaWare.destructed || Game.World() == null || Game.Player() == null) {
            return;
        }
        try {
            if (Keyboard.isCreated() && Keyboard.getEventKeyState()) {
                final int eventKey = Keyboard.getEventKey();
                if (eventKey <= 0) {
                    return;
                }
                for (final Module module : this.mm.modules) {
                    if (module.getKey() == eventKey && eventKey > 0) {
                        module.toggle();
                    }
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @SubscribeEvent
    public void render(final RenderGameOverlayEvent renderGameOverlayEvent) {
        final RenderGameOverlayEvent.ElementType type = renderGameOverlayEvent.type;
        final RenderGameOverlayEvent.ElementType type2 = renderGameOverlayEvent.type;
        if (!type.equals((Object)RenderGameOverlayEvent.ElementType.TEXT) || medusa.ware.MedusaWare.destructed) {
            return;
        }
        this.uiRenderer.draw();
        for (final Module module : this.mm.getModules()) {
            if (module.isToggled() && Game.World() != null && Game.Player() != null) {
                module.onRender2D();
            }
        }
    }
    
    @SubscribeEvent
    public void render3d(final RenderWorldLastEvent renderWorldLastEvent) {
        if (medusa.ware.MedusaWare.destructed) {
            return;
        }
        for (final Module module : this.mm.getModules()) {
            if (module.isToggled() && Game.World() != null && Game.Player() != null) {
                module.onRender3D();
            }
        }
    }
    
    static {
        NAME = "Explicit";
        VERSION = "B6";
        MODID = "Explicit";
        medusa.ware.MedusaWare.destructed = false;
    }
}
