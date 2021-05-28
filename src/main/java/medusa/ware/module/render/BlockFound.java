// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.module.render;

import java.awt.Color;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;

class BlockFound
{
    public BlockPos pos;
    public Block block;
    public Color color;
    
    public BlockFound(final BlockPos pos, final Block block, final Color color) {
        this.pos = pos;
        this.block = block;
        this.color = color;
    }
}
