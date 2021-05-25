// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.utils;

public class TimerUtils
{
    private long lastMS;
    
    private long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }
    
    public boolean hasReached(final double n) {
        return this.getCurrentMS() - this.lastMS >= n;
    }
    
    public void reset() {
        this.lastMS = this.getCurrentMS();
    }
    
    public boolean delay(final float n) {
        return this.getTime() - this.lastMS >= n;
    }
    
    public long getTime() {
        return this.getCurrentMS() - this.lastMS;
    }
}
