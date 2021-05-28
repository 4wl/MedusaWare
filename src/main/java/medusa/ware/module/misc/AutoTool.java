package medusa.ware.module.misc;

import medusa.ware.module.Category;
import medusa.ware.module.Module;
import medusa.ware.utils.Enchantments;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class AutoTool extends Module {

    public AutoTool() {
        super("AutoTool", 0, Category.MISC, "Helps with aiming the rod");
    }

    @SubscribeEvent
    public void leftClickListener(PlayerInteractEvent event) {
        equipBestTool((IBlockState) Minecraft.getMinecraft().thePlayer);
    }

    @SubscribeEvent
    public void attackListener (AttackEntityEvent event){
        equipBestWeapon();
    }

    private void equipBestTool(IBlockState blockState) {
        int bestSlot = -1;
        double max = 0;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i);
            float speed = stack.getItemDamage();
            int eff;
            if (speed > 1) {
                speed += ((eff = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack)) > 0 ? (Math.pow(eff, 2) + 1) : 0);
                if (speed > max) {
                    max = speed;
                    bestSlot = i;
                }
            }
        }
        if (bestSlot != -1) equip(bestSlot);
    }

    public static void equipBestWeapon() {
        int bestSlot = -1;
        double maxDamage = 0;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i);
            if (stack.getItem() instanceof ItemTool) {
                double damage = (((ItemTool) stack.getItem()).getMaxDamage());
                if (damage > maxDamage) {
                    maxDamage = damage;
                    bestSlot = i;
                }
            } else if (stack.getItem() instanceof ItemSword) {
                double damage = (((ItemSword) stack.getItem()).getItemEnchantability());
                if (damage > maxDamage) {
                    maxDamage = damage;
                    bestSlot = i;
                }
            }
        }
        if (bestSlot != -1) equip(bestSlot);
    }

    private static void equip(int slot) {
        Minecraft.getMinecraft().thePlayer.inventory.currentItem = slot;
    }



}
