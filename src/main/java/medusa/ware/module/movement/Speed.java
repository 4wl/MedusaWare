// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.movement;

import medusa.ware.utils.MoveUtils;
import medusa.ware.utils.Game;
import medusa.ware.utils.PrivateUtils;
import medusa.ware.module.Category;
import medusa.ware.utils.TimerUtils;
import medusa.ware.module.Module;

public class Speed extends Module
{
    boolean collided;
    double stair;
    double less;
    boolean lessSlow;
    public TimerUtils timer;
    private TimerUtils lastCheck;
    public boolean shouldslow;
    private double movementSpeed;
    private int stage;
    
    public Speed() {
        super("Speed", 0, Category.MOVEMENT, "Allows you to bunny hop");
        this.collided = false;
        this.shouldslow = false;
        this.timer = new TimerUtils();
        this.lastCheck = new TimerUtils();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        PrivateUtils.timer().timerSpeed = 1.0f;
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.collided = (Game.Player() != null && Game.Player().isCollidedHorizontally);
        this.less = 0.0;
        this.lessSlow = false;
        if (Game.Player() != null) {
            this.movementSpeed = MoveUtils.getBaseMovementSpeed();
        }
        this.shouldslow = false;
    }
    
    private boolean canZoom() {
        return MoveUtils.PlayerMoving() && Game.Player().onGround;
    }
    
    @Override
    public void onUpdate() {
    }
    
    @Override
    public void onMove() {
        this.run();
    }
    
    public void run() {
        if (Game.Player().isCollidedHorizontally) {
            this.collided = true;
        }
        if (this.collided) {
            PrivateUtils.timer().timerSpeed = 1.0f;
            this.stage = -1;
        }
        if (this.stair > 0.0) {
            this.stair -= 0.25;
        }
        this.less -= ((this.less > 1.0) ? 0.12 : 0.11);
        if (this.less < 0.0) {
            this.less = 0.0;
        }
        if (MoveUtils.isOnGround(0.01) && MoveUtils.PlayerMoving()) {
            this.collided = Game.Player().isCollidedHorizontally;
            if (this.stage >= 0 || this.collided) {
                this.stage = 0;
                final double motionY = 0.407 + MoveUtils.getJumpEffect() * 0.1;
                if (this.stair == 0.0) {
                    Game.Player().jump();
                    Game.Player().motionY = motionY;
                }
                ++this.less;
                if (this.less > 1.0 && !this.lessSlow) {
                    this.lessSlow = true;
                }
                else {
                    this.lessSlow = false;
                }
                if (this.less > 1.12) {
                    this.less = 1.12;
                }
            }
        }
        this.movementSpeed = this.getHypixelSpeed(this.stage) + 0.0331;
        this.movementSpeed *= 0.91;
        if (this.stair > 0.0) {
            this.movementSpeed *= 0.7 - MoveUtils.getSpeedEffect() * 0.1;
        }
        if (this.stage < 0) {
            this.movementSpeed = MoveUtils.getBaseMovementSpeed();
        }
        if (this.lessSlow) {
            this.movementSpeed *= 0.95;
        }
        if (Game.Player().moveForward != 0.0f || Game.Player().moveStrafing != 0.0f) {
            MoveUtils.setMoveSpeed(this.movementSpeed);
            ++this.stage;
        }
    }
    
    @Override
    public void onTick() {
    }
    
    private double getHypixelSpeed(final int n) {
        double a = MoveUtils.getBaseMovementSpeed() + 0.028 * MoveUtils.getSpeedEffect() + MoveUtils.getSpeedEffect() / 15.0;
        final double n2 = 0.4145 + MoveUtils.getSpeedEffect() / 12.5;
        final double n3 = n / 500.0 * 2.0;
        if (n == 0) {
            if (this.timer.delay(300.0f)) {
                this.timer.reset();
            }
            if (!this.lastCheck.delay(500.0f)) {
                if (!this.shouldslow) {
                    this.shouldslow = true;
                }
            }
            else if (this.shouldslow) {
                this.shouldslow = false;
            }
            a = 0.64 + (MoveUtils.getSpeedEffect() + 0.028 * MoveUtils.getSpeedEffect()) * 0.134;
        }
        else if (n == 1) {
            if (PrivateUtils.timer().timerSpeed == 1.354f) {}
            a = n2;
        }
        else if (n >= 2) {
            if (PrivateUtils.timer().timerSpeed == 1.254f) {}
            a = n2 - n3;
        }
        if (this.shouldslow || !this.lastCheck.delay(500.0f) || this.collided) {
            a = 0.2;
            if (n == 0) {
                a = 0.0;
            }
        }
        return Math.max(a, this.shouldslow ? a : (MoveUtils.getBaseMovementSpeed() + 0.028 * MoveUtils.getSpeedEffect()));
    }
    
    public void setSpeed(final double n) {
        Game.Player().motionX = -Math.sin(MoveUtils.getDirection()) * n;
        Game.Player().motionZ = Math.cos(MoveUtils.getDirection()) * n;
    }
}
