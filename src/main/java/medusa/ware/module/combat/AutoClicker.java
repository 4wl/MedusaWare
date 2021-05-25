// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.combat;

import java.nio.ByteBuffer;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.BlockPos;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemBow;
import medusa.ware.utils.Game;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import medusa.ware.settings.Setting;
import medusa.ware.MedusaWare;
import medusa.ware.module.Category;
import java.util.Random;
import java.lang.reflect.Field;
import medusa.ware.module.Module;

public class AutoClicker extends Module
{
    public double minLeft;
    public double maxLeft;
    public double minBlock;
    public double maxBlock;
    public double jitterLeft;
    public double jitterRight;
    public boolean sword;
    public boolean axe;
    public boolean blocks;
    public boolean rightClick;
    public boolean blockHit;
    public boolean noShift;
    private long time1Left;
    private long timeLeft;
    private long time2Left;
    private long time3Left;
    private double time4Left;
    private boolean shouldLeft;
    private long time1Right;
    private long timeRight;
    private long time2Right;
    private long time3Right;
    private double time4Right;
    private boolean shouldRight;
    private static Field buttonstate;
    private static Field button;
    private static Field buttons;
    private Random rando;
    
    public AutoClicker() {
        super("AutoClicker", 0, Category.COMBAT, "Automatically clicks for you");
        this.rando = new Random();
    }
    
    @Override
    public void setup() {
        MedusaWare.instance.sm.rSetting(new Setting("MinCPS", this, 8.0, 1.0, 20.0, false));
        MedusaWare.instance.sm.rSetting(new Setting("MaxCPS", this, 12.0, 1.0, 20.0, false));
        MedusaWare.instance.sm.rSetting(new Setting("MinBPS", this, 3.0, 1.0, 10.0, false));
        MedusaWare.instance.sm.rSetting(new Setting("MaxBPS", this, 5.0, 1.0, 10.0, false));
        MedusaWare.instance.sm.rSetting(new Setting("LeftJitter", this, 0.0, 0.0, 4.0, false));
        MedusaWare.instance.sm.rSetting(new Setting("RightJitter", this, 0.0, 0.0, 4.0, false));
        MedusaWare.instance.sm.rSetting(new Setting("BreakBlocks", this, false));
        MedusaWare.instance.sm.rSetting(new Setting("RightClick", this, false));
        MedusaWare.instance.sm.rSetting(new Setting("BlockHit", this, true));
        MedusaWare.instance.sm.rSetting(new Setting("SwordOnly", this, false));
        MedusaWare.instance.sm.rSetting(new Setting("AxeOnly", this, false));
        MedusaWare.instance.sm.rSetting(new Setting("NoSneak", this, false));
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onTick() {
    }
    
    @Override
    public void onUpdateNoToggle() {
        this.minLeft = MedusaWare.instance.sm.getSettingByName(this, "MinCPS").getValDouble();
        this.maxLeft = MedusaWare.instance.sm.getSettingByName(this, "MaxCPS").getValDouble();
        this.jitterLeft = MedusaWare.instance.sm.getSettingByName(this, "LeftJitter").getValDouble();
        this.jitterRight = MedusaWare.instance.sm.getSettingByName(this, "RightJitter").getValDouble();
        this.sword = MedusaWare.instance.sm.getSettingByName(this, "SwordOnly").getValBoolean();
        this.axe = MedusaWare.instance.sm.getSettingByName(this, "AxeOnly").getValBoolean();
        this.blocks = MedusaWare.instance.sm.getSettingByName(this, "BreakBlocks").getValBoolean();
        this.rightClick = MedusaWare.instance.sm.getSettingByName(this, "RightClick").getValBoolean();
        this.blockHit = MedusaWare.instance.sm.getSettingByName(this, "BlockHit").getValBoolean();
        this.noShift = MedusaWare.instance.sm.getSettingByName(this, "NoSneak").getValBoolean();
        this.minBlock = MedusaWare.instance.sm.getSettingByName(this, "MinBPS").getValDouble();
        this.maxBlock = MedusaWare.instance.sm.getSettingByName(this, "MaxBPS").getValDouble();
        if (this.rightClick) {
            MedusaWare.instance.sm.getSettingByName(this, "BlockHit").setVisible(true);
            MedusaWare.instance.sm.getSettingByName(this, "RightJitter").setVisible(true);
        }
        else {
            MedusaWare.instance.sm.getSettingByName(this, "BlockHit").setVisible(false);
            MedusaWare.instance.sm.getSettingByName(this, "RightJitter").setVisible(false);
        }
        if (this.blockHit && this.rightClick) {
            MedusaWare.instance.sm.getSettingByName(this, "MinBPS").setVisible(true);
            MedusaWare.instance.sm.getSettingByName(this, "MaxBPS").setVisible(true);
        }
        else {
            MedusaWare.instance.sm.getSettingByName(this, "MinBPS").setVisible(false);
            MedusaWare.instance.sm.getSettingByName(this, "MaxBPS").setVisible(false);
        }
    }
    
    @SubscribeEvent
    public void tick(final TickEvent.RenderTickEvent renderTickEvent) {
        boolean b = false;
        if (Game.World() == null || Game.Player() == null) {
            return;
        }
        if (Game.Player().getCurrentEquippedItem() != null && (Game.Player().getCurrentEquippedItem().getItem() instanceof ItemBow || Game.Player().getCurrentEquippedItem().getItem() instanceof ItemFood || Game.Player().getCurrentEquippedItem().getItem() instanceof ItemPotion)) {
            b = true;
        }
        if (AutoClicker.mc.currentScreen != null || (AutoClicker.mc.gameSettings.keyBindSneak.isKeyDown() && this.noShift)) {
            return;
        }
        Mouse.poll();
        if (Mouse.isButtonDown(0)) {
            this.clickLeft();
        }
        else {
            this.time1Left = 0L;
            this.timeLeft = 0L;
        }
        Mouse.poll();
        Label_0175: {
            if (Mouse.isButtonDown(1) && this.rightClick && !b) {
                if (!this.blockHit) {
                    if (Mouse.isButtonDown(0)) {
                        break Label_0175;
                    }
                }
                this.clickRight();
                return;
            }
        }
        this.time1Right = 0L;
        this.timeRight = 0L;
    }
    
    public void clickLeft() {
        if (!AutoClicker.mc.inGameHasFocus) {
            return;
        }
        if (this.sword || this.axe) {
            if (Game.Player().getCurrentEquippedItem() == null) {
                return;
            }
            if (!(Game.Player().getCurrentEquippedItem().getItem() instanceof ItemSword) && this.sword) {
                return;
            }
            if (!(Game.Player().getCurrentEquippedItem().getItem() instanceof ItemAxe) && this.axe) {
                return;
            }
        }
        if (this.blocks && AutoClicker.mc.objectMouseOver != null) {
            final BlockPos blockPos = AutoClicker.mc.objectMouseOver.getBlockPos();
            if (blockPos != null && Game.World().getBlockState(blockPos).getBlock() != Blocks.air) {
                final int keyCode = AutoClicker.mc.gameSettings.keyBindAttack.getKeyCode();
                KeyBinding.setKeyBindState(keyCode, true);
                KeyBinding.onTick(keyCode);
                return;
            }
        }
        if (this.jitterLeft > 0.0) {
            final double n = this.jitterLeft * 0.5;
            if (this.rando.nextBoolean()) {
                final EntityPlayerSP player = Game.Player();
                player.rotationYaw += (float)(this.rando.nextFloat() * n);
            }
            else {
                final EntityPlayerSP player2 = Game.Player();
                player2.rotationYaw -= (float)(this.rando.nextFloat() * n);
            }
            if (this.rando.nextBoolean()) {
                final EntityPlayerSP player3 = Game.Player();
                player3.rotationPitch += (float)(this.rando.nextFloat() * (n * 0.45));
            }
            else {
                final EntityPlayerSP player4 = Game.Player();
                player4.rotationPitch -= (float)(this.rando.nextFloat() * (n * 0.45));
            }
        }
        if (this.timeLeft > 0L && this.time1Left > 0L) {
            if (System.currentTimeMillis() > this.timeLeft) {
                final int keyCode2 = AutoClicker.mc.gameSettings.keyBindAttack.getKeyCode();
                KeyBinding.setKeyBindState(keyCode2, true);
                KeyBinding.onTick(keyCode2);
                pushEvent(0, true);
                this.getELeft();
            }
            else if (System.currentTimeMillis() > this.time1Left) {
                KeyBinding.setKeyBindState(AutoClicker.mc.gameSettings.keyBindAttack.getKeyCode(), false);
                pushEvent(0, false);
            }
        }
        else {
            this.getELeft();
        }
    }
    
    public void clickRight() {
        if (!AutoClicker.mc.inGameHasFocus) {
            return;
        }
        if (this.jitterRight > 0.0) {
            final double n = this.jitterRight * 0.5;
            if (this.rando.nextBoolean()) {
                final EntityPlayerSP player = Game.Player();
                player.rotationYaw += (float)(this.rando.nextFloat() * n);
            }
            else {
                final EntityPlayerSP player2 = Game.Player();
                player2.rotationYaw -= (float)(this.rando.nextFloat() * n);
            }
            if (this.rando.nextBoolean()) {
                final EntityPlayerSP player3 = Game.Player();
                player3.rotationPitch += (float)(this.rando.nextFloat() * (n * 0.45));
            }
            else {
                final EntityPlayerSP player4 = Game.Player();
                player4.rotationPitch -= (float)(this.rando.nextFloat() * (n * 0.45));
            }
        }
        if (this.timeRight > 0L && this.time1Right > 0L) {
            if (System.currentTimeMillis() > this.timeRight && !AutoClicker.mc.gameSettings.keyBindAttack.isKeyDown()) {
                final int keyCode = AutoClicker.mc.gameSettings.keyBindUseItem.getKeyCode();
                KeyBinding.setKeyBindState(keyCode, true);
                KeyBinding.onTick(keyCode);
                pushEvent(1, true);
                this.getERight();
            }
            else if (System.currentTimeMillis() > this.time1Right || AutoClicker.mc.gameSettings.keyBindAttack.isKeyDown()) {
                KeyBinding.setKeyBindState(AutoClicker.mc.gameSettings.keyBindUseItem.getKeyCode(), false);
                pushEvent(1, false);
            }
        }
        else {
            this.getERight();
        }
    }
    
    public void getELeft() {
        final double n = this.minLeft + this.rando.nextDouble() * (this.maxLeft - this.minLeft);
        long n2 = (int)Math.round(1000.0 / n) - (int)(Math.round(1000.0 / n) / 1000L);
        if (System.currentTimeMillis() > this.time2Left) {
            if (!this.shouldLeft && this.rando.nextInt(100) >= 85) {
                this.shouldLeft = true;
                this.time4Left = 1.1 + this.rando.nextDouble() * 0.15;
            }
            else {
                this.shouldLeft = false;
            }
            this.time2Left = System.currentTimeMillis() + 400L + this.rando.nextInt(1500);
        }
        if (this.shouldLeft) {
            n2 *= (long)this.time4Left;
        }
        if (System.currentTimeMillis() > this.time3Left) {
            if (this.rando.nextInt(100) >= 80) {
                n2 += 50L + this.rando.nextInt(100);
            }
            this.time3Left = System.currentTimeMillis() + 450L + this.rando.nextInt(100);
        }
        this.timeLeft = System.currentTimeMillis() + n2;
        this.time1Left = System.currentTimeMillis() + n2 / 2L - this.rando.nextInt(8);
    }
    
    public void getERight() {
        final double n = this.blockHit ? this.minBlock : this.minLeft;
        final double n2 = n + this.rando.nextDouble() * ((this.blockHit ? this.maxBlock : this.maxLeft) - n);
        long n3 = (int)Math.round(1000.0 / n2) - (int)(Math.round(1000.0 / n2) / 1000L);
        if (System.currentTimeMillis() > this.time2Right) {
            if (!this.shouldRight && this.rando.nextInt(100) >= 85) {
                this.shouldRight = true;
                this.time4Right = 1.1 + this.rando.nextDouble() * 0.15;
            }
            else {
                this.shouldRight = false;
            }
            this.time2Right = System.currentTimeMillis() + 400L + this.rando.nextInt(1500);
        }
        if (this.shouldRight) {
            n3 *= (long)this.time4Right;
        }
        if (System.currentTimeMillis() > this.time3Right) {
            if (this.rando.nextInt(100) >= 80) {
                n3 += 50L + this.rando.nextInt(100);
            }
            this.time3Right = System.currentTimeMillis() + 450L + this.rando.nextInt(100);
        }
        this.timeRight = System.currentTimeMillis() + n3;
        this.time1Right = System.currentTimeMillis() + n3 / 2L - this.rando.nextInt(8);
    }
    
    public static void pushEvent(final int i, final boolean b) {
        final MouseEvent mouseEvent = new MouseEvent();
        AutoClicker.button.setAccessible(true);
        try {
            AutoClicker.button.set(mouseEvent, i);
        }
        catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        AutoClicker.button.setAccessible(false);
        AutoClicker.buttonstate.setAccessible(true);
        try {
            AutoClicker.buttonstate.set(mouseEvent, b);
        }
        catch (IllegalAccessException ex2) {
            ex2.printStackTrace();
        }
        AutoClicker.buttonstate.setAccessible(false);
        MinecraftForge.EVENT_BUS.post((Event)mouseEvent);
        try {
            AutoClicker.buttons.setAccessible(true);
            final ByteBuffer byteBuffer = (ByteBuffer)AutoClicker.buttons.get(null);
            AutoClicker.buttons.setAccessible(false);
            byteBuffer.put(i, (byte)(b ? 1 : 0));
        }
        catch (IllegalAccessException ex3) {
            ex3.printStackTrace();
        }
    }
    
    static {
        try {
            AutoClicker.button = MouseEvent.class.getDeclaredField("button");
        }
        catch (NoSuchFieldException ex) {
            ex.printStackTrace();
        }
        try {
            AutoClicker.buttonstate = MouseEvent.class.getDeclaredField("buttonstate");
        }
        catch (NoSuchFieldException ex2) {
            ex2.printStackTrace();
        }
        try {
            AutoClicker.buttons = Mouse.class.getDeclaredField("buttons");
        }
        catch (NoSuchFieldException ex3) {
            ex3.printStackTrace();
        }
    }
}
