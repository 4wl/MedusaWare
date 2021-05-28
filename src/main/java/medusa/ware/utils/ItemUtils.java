// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.utils;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.item.ItemPotion;
import java.util.Map;
import net.minecraft.util.DamageSource;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.client.Minecraft;

public class ItemUtils
{
    private static final Minecraft mc;
    
    public static int bestSwordSlot() {
        for (int i = 9; i < 45; ++i) {
            if (ItemUtils.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack stack = ItemUtils.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (stack.getItem() instanceof ItemSword && getItemDamage(stack) > 0.0f) {
                    return i;
                }
            }
        }
        return 0;
    }
    
    public static ItemStack bestSword() {
        ItemStack itemStack = null;
        float n = 0.0f;
        for (int i = 9; i < 45; ++i) {
            if (ItemUtils.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack stack = ItemUtils.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (stack.getItem() instanceof ItemSword) {
                    final float itemDamage = getItemDamage(stack);
                    if (itemDamage > n) {
                        n = itemDamage;
                        itemStack = stack;
                    }
                }
            }
        }
        return itemStack;
    }
    
    public static ItemStack compareDamage(final ItemStack itemStack, final ItemStack itemStack2) {
        try {
            if (itemStack == null || itemStack2 == null) {
                return null;
            }
            if (!(itemStack.getItem() instanceof ItemSword) && itemStack2.getItem() instanceof ItemSword) {
                return null;
            }
            if (getItemDamage(itemStack) > getItemDamage(itemStack2)) {
                return itemStack;
            }
            if (getItemDamage(itemStack2) > getItemDamage(itemStack)) {
                return itemStack2;
            }
            return itemStack;
        }
        catch (NullPointerException ex) {
            ex.printStackTrace();
            return itemStack;
        }
    }
    
    public static boolean isBad(final ItemStack itemStack) {
        return itemStack.getItem().getUnlocalizedName().contains("tnt") || itemStack.getItem().getUnlocalizedName().contains("stick") || itemStack.getItem().getUnlocalizedName().equalsIgnoreCase("egg") || itemStack.getItem().getUnlocalizedName().contains("string") || itemStack.getItem().getUnlocalizedName().contains("flint") || itemStack.getItem().getUnlocalizedName().contains("feather") || itemStack.getItem().getUnlocalizedName().contains("bucket") || (itemStack.getItem().getUnlocalizedName().equalsIgnoreCase("chest") && !itemStack.getDisplayName().toLowerCase().contains("collect")) || itemStack.getItem().getUnlocalizedName().contains("snow") || itemStack.getItem().getUnlocalizedName().contains("enchant") || itemStack.getItem().getUnlocalizedName().contains("exp") || itemStack.getItem().getUnlocalizedName().contains("shears") || itemStack.getItem().getUnlocalizedName().contains("arrow") || itemStack.getItem().getUnlocalizedName().contains("anvil") || itemStack.getItem().getUnlocalizedName().contains("torch") || itemStack.getItem().getUnlocalizedName().contains("skull") || itemStack.getItem().getUnlocalizedName().contains("seeds") || itemStack.getItem().getUnlocalizedName().contains("leather") || itemStack.getItem().getUnlocalizedName().contains("boat") || itemStack.getItem().getUnlocalizedName().contains("fishing") || itemStack.getItem().getUnlocalizedName().contains("wheat") || itemStack.getItem().getUnlocalizedName().contains("flower") || itemStack.getItem().getUnlocalizedName().contains("record") || itemStack.getItem().getUnlocalizedName().contains("note") || itemStack.getItem().getUnlocalizedName().contains("sugar") || itemStack.getItem().getUnlocalizedName().contains("wire") || itemStack.getItem().getUnlocalizedName().contains("trip") || itemStack.getItem().getUnlocalizedName().contains("slime") || itemStack.getItem().getUnlocalizedName().contains("web") || itemStack.getItem() instanceof ItemGlassBottle || (itemStack.getItem() instanceof ItemArmor && !getBest().contains(itemStack)) || (itemStack.getItem() instanceof ItemSword && itemStack != bestSword()) || itemStack.getItem().getUnlocalizedName().contains("piston") || (itemStack.getItem().getUnlocalizedName().contains("potion") && isBadPotion(itemStack));
    }
    
    public static List<ItemStack> getBest() {
        final ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        for (int i = 0; i < 4; ++i) {
            ItemStack itemStack = null;
            ItemStack[] armorInventory;
            for (int length = (armorInventory = ItemUtils.mc.thePlayer.inventory.armorInventory).length, j = 0; j < length; ++j) {
                final ItemStack itemStack2 = armorInventory[j];
                if (itemStack2 != null && itemStack2.getItem() instanceof ItemArmor && ((ItemArmor)itemStack2.getItem()).armorType == i) {
                    itemStack = itemStack2;
                }
            }
            final double n = (itemStack == null) ? -1.0 : getArmorStrength(itemStack);
            ItemStack bestArmor = findBestArmor(i);
            if (bestArmor != null && getArmorStrength(bestArmor) <= n) {
                bestArmor = itemStack;
            }
            if (bestArmor != null) {
                list.add(bestArmor);
            }
        }
        return list;
    }
    
    public static ItemStack findBestArmor(final int n) {
        ItemStack itemStack = null;
        double n2 = 0.0;
        for (int i = 0; i < 36; ++i) {
            final ItemStack itemStack2 = ItemUtils.mc.thePlayer.inventory.mainInventory[i];
            if (itemStack2 != null) {
                final double armorStrength = getArmorStrength(itemStack2);
                if (armorStrength != -1.0 && ((ItemArmor)itemStack2.getItem()).armorType == n && armorStrength >= n2) {
                    n2 = armorStrength;
                    itemStack = itemStack2;
                }
            }
        }
        return itemStack;
    }
    
    public static double getArmorStrength(final ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof ItemArmor)) {
            return -1.0;
        }
        float n = (float)((ItemArmor)itemStack.getItem()).damageReduceAmount;
        final Map enchantments = EnchantmentHelper.getEnchantments(itemStack);
        if (enchantments.containsKey(Enchantment.protection.effectId)) {
        }
        return n;
    }
    
    public static boolean isBadPotion(final ItemStack itemStack) {
        if (itemStack != null && itemStack.getItem() instanceof ItemPotion) {
            final ItemPotion itemPotion = (ItemPotion)itemStack.getItem();
            if (ItemPotion.isSplash(itemStack.getItemDamage())) {
                for (final PotionEffect potionEffect : itemPotion.getEffects(itemStack)) {
                    if (potionEffect.getPotionID() == Potion.poison.getId() || potionEffect.getPotionID() == Potion.harm.getId() || potionEffect.getPotionID() == Potion.moveSlowdown.getId() || potionEffect.getPotionID() == Potion.weakness.getId()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static float getItemDamage(final ItemStack itemStack) {
        if (itemStack == null) {
            return 0.0f;
        }
        if (!(itemStack.getItem() instanceof ItemSword)) {
            return 0.0f;
        }
        return ((ItemSword)itemStack.getItem()).getDamageVsEntity() + EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack) * 1.25f + EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemStack) * 0.01f;
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
