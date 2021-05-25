// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.combat;

import net.minecraftforge.fml.common.eventhandler.EventPriority;
import medusa.ware.utils.CombatUtils;
import medusa.ware.utils.Game;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import io.netty.util.internal.ThreadLocalRandom;
import medusa.ware.settings.Setting;
import medusa.ware.MedusaWare;
import medusa.ware.module.Category;
import medusa.ware.utils.TimerUtils;
import medusa.ware.module.Module;

public class STap extends Module
{
    boolean shouldTap;
    private TimerUtils tDelay;
    private TimerUtils tHold;
    private TimerUtils updateData;
    private double delay;
    private double hold;
    boolean hasReached;
    
    public STap() {
        super("STap", 0, Category.MOVEMENT, "Automatically taps S.");
        this.shouldTap = false;
        this.hasReached = false;
        this.tDelay = new TimerUtils();
        this.tHold = new TimerUtils();
        this.updateData = new TimerUtils();
    }
    
    @Override
    public void setup() {
        MedusaWare.instance.sm.rSetting(new Setting("MinDelay", this, 100.0, 1.0, 500.0, true));
        MedusaWare.instance.sm.rSetting(new Setting("MaxDelay", this, 150.0, 1.0, 500.0, true));
        MedusaWare.instance.sm.rSetting(new Setting("MinHold", this, 10.0, 1.0, 250.0, true));
        MedusaWare.instance.sm.rSetting(new Setting("MaxHold", this, 11.0, 1.0, 250.0, true));
    }
    
    @Override
    public void onEnable() {
        this.shouldTap = false;
        this.hasReached = false;
        super.onEnable();
    }
    
    public void setData() {
        final double valDouble = MedusaWare.instance.sm.getSettingByName(this, "MinDelay").getValDouble();
        double valDouble2 = MedusaWare.instance.sm.getSettingByName(this, "MaxDelay").getValDouble();
        final double valDouble3 = MedusaWare.instance.sm.getSettingByName(this, "MinHold").getValDouble();
        double valDouble4 = MedusaWare.instance.sm.getSettingByName(this, "MaxHold").getValDouble();
        if (valDouble == valDouble2 || valDouble > valDouble2) {
            valDouble2 = valDouble * 1.1;
        }
        if (valDouble3 == valDouble4 || valDouble3 > valDouble4) {
            valDouble4 = valDouble3 * 1.1;
        }
        this.delay = ThreadLocalRandom.current().nextDouble(Math.min(valDouble, valDouble2), Math.max(valDouble, valDouble2));
        this.hold = ThreadLocalRandom.current().nextDouble(Math.min(valDouble3, valDouble4), Math.max(valDouble3, valDouble4));
    }
    
    @SubscribeEvent
    public void t34ff(final TickEvent.RenderTickEvent renderTickEvent) {
        if (this.updateData.hasReached(Math.max(MedusaWare.instance.sm.getSettingByName(this, "MinDelay").getValDouble(), MedusaWare.instance.sm.getSettingByName(this, "MaxDelay").getValDouble()) * ((ThreadLocalRandom.current().nextDouble() + ThreadLocalRandom.current().nextDouble()) * 2.0))) {
            this.setData();
            this.hasReached = true;
            this.updateData.reset();
        }
        if (!this.hasReached) {
            return;
        }
        if (this.tHold.hasReached(this.hold) && this.shouldTap && Keyboard.isKeyDown(STap.mc.gameSettings.keyBindBack.getKeyCode())) {
            KeyBinding.setKeyBindState(STap.mc.gameSettings.keyBindBack.getKeyCode(), true);
            this.shouldTap = false;
        }
        else if (!Keyboard.isKeyDown(STap.mc.gameSettings.keyBindBack.getKeyCode())) {
            KeyBinding.setKeyBindState(STap.mc.gameSettings.keyBindBack.getKeyCode(), false);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void asdfgbnv(final AttackEntityEvent attackEntityEvent) {
        if (this.tDelay.hasReached(this.delay) && !this.shouldTap && STap.mc.objectMouseOver != null && STap.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && STap.mc.objectMouseOver.entityHit.getEntityId() == attackEntityEvent.target.getEntityId() && CombatUtils.canTarget(Game.World().getEntityByID(attackEntityEvent.target.getEntityId()))) {
            this.shouldTap = true;
            this.tHold.reset();
            this.tDelay.reset();
            KeyBinding.setKeyBindState(STap.mc.gameSettings.keyBindBack.getKeyCode(), false);
        }
    }
}
