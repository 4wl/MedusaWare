

package medusa.ware.module.render;

import java.awt.Color;
import medusa.ware.utils.RenderUtils;
import net.minecraft.util.BlockPos;
import medusa.ware.utils.Game;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraft.init.Blocks;
import java.util.ArrayList;
import medusa.ware.settings.Setting;
import medusa.ware.MedusaWare;
import medusa.ware.module.Category;
import medusa.ware.utils.TimerUtils;
import net.minecraft.block.Block;
import java.util.List;
import medusa.ware.module.Module;

public class Search extends Module
{
    public int r;
    public boolean iron;
    public boolean gold;
    public boolean diamond;
    public boolean emerald;
    public boolean lapis;
    public boolean redstone;
    public boolean coal;
    public boolean spawner;
    public List<Block> ores;
    public List<BlockFound> blocksFound;
    private TimerUtils updateTimer;
    
    public Search() {
        super("Search", 0, Category.RENDER, "Allows you to find ores");
        this.updateTimer = new TimerUtils();
    }
    
    @Override
    public void setup() {
        MedusaWare.instance.sm.rSetting(new Setting("Range", this, 50.0, 2.0, 256.0, true));
        MedusaWare.instance.sm.rSetting(new Setting("Update", this, 4000.0, 1000.0, 9999.0, true));
        MedusaWare.instance.sm.rSetting(new Setting("Iron", this, true));
        MedusaWare.instance.sm.rSetting(new Setting("Coal", this, false));
        MedusaWare.instance.sm.rSetting(new Setting("Gold", this, false));
        MedusaWare.instance.sm.rSetting(new Setting("Diamond", this, true));
        MedusaWare.instance.sm.rSetting(new Setting("Redstone", this, false));
        MedusaWare.instance.sm.rSetting(new Setting("Emerald", this, false));
        MedusaWare.instance.sm.rSetting(new Setting("Lapis", this, false));
        MedusaWare.instance.sm.rSetting(new Setting("Spawner", this, false));
        (this.blocksFound = new ArrayList<BlockFound>()).clear();
        (this.ores = new ArrayList<Block>()).add(Blocks.iron_ore);
        this.ores.add(Blocks.gold_ore);
        this.ores.add(Blocks.diamond_ore);
        this.ores.add(Blocks.emerald_ore);
        this.ores.add(Blocks.lapis_ore);
        this.ores.add(Blocks.redstone_ore);
        this.ores.add(Blocks.coal_ore);
        this.ores.add(Blocks.mob_spawner);
    }
    
    @Override
    public void onUpdateNoToggle() {
        this.r = MedusaWare.instance.sm.getSettingByName(this, "Range").getValInt();
        this.iron = MedusaWare.instance.sm.getSettingByName(this, "Iron").getValBoolean();
        this.gold = MedusaWare.instance.sm.getSettingByName(this, "Gold").getValBoolean();
        this.diamond = MedusaWare.instance.sm.getSettingByName(this, "Diamond").getValBoolean();
        this.redstone = MedusaWare.instance.sm.getSettingByName(this, "Redstone").getValBoolean();
        this.coal = MedusaWare.instance.sm.getSettingByName(this, "Coal").getValBoolean();
        this.emerald = MedusaWare.instance.sm.getSettingByName(this, "Emerald").getValBoolean();
        this.lapis = MedusaWare.instance.sm.getSettingByName(this, "Lapis").getValBoolean();
        this.spawner = MedusaWare.instance.sm.getSettingByName(this, "Spawner").getValBoolean();
    }
    
    @SubscribeEvent
    public void orl(final RenderWorldLastEvent renderWorldLastEvent) {
        if (this.updateTimer.hasReached(MedusaWare.instance.sm.getSettingByName(this, "Update").getValDouble())) {
            this.updateBlocks();
            this.updateTimer.reset();
        }
        if (!this.iron && !this.gold && !this.coal && !this.diamond && !this.redstone && !this.emerald && !this.lapis && !this.spawner) {
            return;
        }
        for (int i = 0; i < this.blocksFound.size(); ++i) {
            this.draw(this.blocksFound.get(i));
        }
    }
    
    private void updateBlocks() {
        this.blocksFound.clear();
        final BlockPos position = Game.Player().getPosition();
        for (int r = this.r, i = position.getX() - r; i <= position.getX() + r; ++i) {
            for (int j = position.getZ() - r; j < position.getZ() + r; ++j) {
                for (int k = position.getY() - r; k < position.getY() + r; ++k) {
                    final Block block = Game.World().getBlockState(new BlockPos(i, k, j)).getBlock();
                    if (this.ores.contains(block) && (this.iron || !block.equals(Blocks.iron_ore)) && (this.gold || !block.equals(Blocks.gold_ore)) && (this.diamond || !block.equals(Blocks.diamond_ore)) && (this.emerald || !block.equals(Blocks.emerald_ore)) && (this.lapis || !block.equals(Blocks.lapis_ore)) && (this.redstone || !block.equals(Blocks.redstone_ore)) && (this.coal || !block.equals(Blocks.coal_ore)) && (this.spawner || !block.equals(Blocks.mob_spawner))) {
                        this.blocksFound.add(new BlockFound(new BlockPos(i, k, j), block, this.color(block)));
                    }
                }
            }
        }
    }
    
    private void draw(final BlockFound blockFound) {
        RenderUtils.blockESPBox(blockFound.pos, (float)blockFound.color.getRed(), (float)blockFound.color.getGreen(), (float)blockFound.color.getBlue(), 1.0f);
    }
    
    private Color color(final Block block) {
        int r = 0;
        int g = 0;
        int b = 0;
        if (block.equals(Blocks.iron_ore)) {
            r = 255;
            g = 255;
            b = 255;
        }
        else if (block.equals(Blocks.gold_ore)) {
            r = 255;
            g = 255;
        }
        else if (block.equals(Blocks.diamond_ore)) {
            g = 220;
            b = 255;
        }
        else if (block.equals(Blocks.emerald_ore)) {
            r = 35;
            g = 255;
        }
        else if (block.equals(Blocks.lapis_ore)) {
            g = 50;
            b = 255;
        }
        else if (block.equals(Blocks.redstone_ore)) {
            r = 255;
        }
        else if (block.equals(Blocks.mob_spawner)) {
            r = 30;
            b = 135;
        }
        return new Color(r, g, b);
    }
}
