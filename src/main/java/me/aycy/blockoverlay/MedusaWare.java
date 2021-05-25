// 
// Decompiled by Procyon v0.5.36
// 

package me.aycy.blockoverlay;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.command.ICommand;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = "MedusaWare", useMetadata = true, acceptedMinecraftVersions = "[1.8.9]")
public class MedusaWare
{
    protected static boolean alwaysRender;
    protected static boolean isChroma;
    protected static boolean openGui;
    protected static float lineWidth;
    protected static float red;
    protected static float green;
    protected static float blue;
    protected static float alpha;
    protected static int chromaSpeed;
    protected static MedusaWareMode mode;
    
    @Mod.EventHandler
    public void onInit(final FMLInitializationEvent fmlInitializationEvent) {
        MedusaWare.alwaysRender = false;
        MedusaWare.isChroma = false;
        MedusaWare.openGui = false;
        MedusaWare.lineWidth = 2.0f;
        MedusaWare.red = 1.0f;
        MedusaWare.green = 1.0f;
        MedusaWare.blue = 1.0f;
        MedusaWare.alpha = 1.0f;
        MedusaWare.chromaSpeed = 1;
        MedusaWare.mode = MedusaWareMode.DEFAULT;
        MinecraftForge.EVENT_BUS.register((Object)this);
        MinecraftForge.EVENT_BUS.register((Object)new MedusaWareRender());
        ClientCommandHandler.instance.registerCommand((ICommand)new MedusaWareCommand());
        medusa.ware.MedusaWare.instance = new medusa.ware.MedusaWare();
        this.loadConfig();
        medusa.ware.MedusaWare.instance.onInit();
    }
    
    @SubscribeEvent
    public void onClientTick(final TickEvent.ClientTickEvent clientTickEvent) {
        if (clientTickEvent.phase.equals((Object)TickEvent.Phase.END) && MedusaWare.openGui) {
            MedusaWare.openGui = false;
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new MedusaWareGui());
        }
    }
    
    public void loadConfig() {
        try {
            final File file = new File(String.valueOf(new StringBuilder().append(Minecraft.getMinecraft().mcDataDir).append("/config/MedusaWare.cfg")));
            if (file.exists()) {
                final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                final String line = bufferedReader.readLine();
                for (final MedusaWareMode mode : MedusaWareMode.values()) {
                    if (mode.name.equals(line)) {
                        MedusaWare.mode = mode;
                        break;
                    }
                }
                MedusaWare.red = Float.parseFloat(bufferedReader.readLine());
                MedusaWare.green = Float.parseFloat(bufferedReader.readLine());
                MedusaWare.blue = Float.parseFloat(bufferedReader.readLine());
                MedusaWare.alpha = Float.parseFloat(bufferedReader.readLine());
                MedusaWare.alwaysRender = bufferedReader.readLine().equals("true");
                MedusaWare.isChroma = bufferedReader.readLine().equals("true");
                MedusaWare.chromaSpeed = Integer.parseInt(bufferedReader.readLine());
                MedusaWare.lineWidth = Float.parseFloat(bufferedReader.readLine());
                if (Boolean.parseBoolean(bufferedReader.readLine())) {
                    medusa.ware.MedusaWare.instance.onSelfDestruct();
                }
                bufferedReader.close();
            }
        }
        catch (Exception ex) {
            System.out.println("Error occurred while loading MedusaWare configuration");
            ex.printStackTrace();
        }
    }
    
    public static void saveConfig() {
        try {
            final File file = new File(String.valueOf(new StringBuilder().append(Minecraft.getMinecraft().mcDataDir).append("/config/MedusaWare.cfg")));
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            final StringBuilder append = new StringBuilder().append(MedusaWare.mode.name).append("\r\n").append(MedusaWare.red).append("\r\n").append(MedusaWare.green).append("\r\n").append(MedusaWare.blue).append("\r\n").append(MedusaWare.alpha).append("\r\n").append(MedusaWare.alwaysRender).append("\r\n").append(MedusaWare.isChroma).append("\r\n").append(MedusaWare.chromaSpeed).append("\r\n").append(MedusaWare.lineWidth).append("\r\n");
            final medusa.ware.MedusaWare instance = medusa.ware.MedusaWare.instance;
            bufferedWriter.write(String.valueOf(append.append(medusa.ware.MedusaWare.destructed)));
            bufferedWriter.close();
        }
        catch (Exception ex) {
            System.out.println("Error occurred while saving MedusaWare configuration");
            ex.printStackTrace();
        }
    }
}
