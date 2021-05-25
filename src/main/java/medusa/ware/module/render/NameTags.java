

package medusa.ware.module.render;

import net.minecraft.init.Items;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemBow;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemTool;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import medusa.ware.utils.RenderUtils;
import java.awt.Color;
import java.text.DecimalFormat;

import medusa.ware.utils.PrivateUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import medusa.ware.utils.Game;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import medusa.ware.settings.Setting;
import medusa.ware.MedusaWare;
import medusa.ware.module.Category;
import net.minecraft.entity.Entity;
import java.util.ArrayList;
import medusa.ware.module.Module;

public class NameTags extends Module
{
    private float scale;
    private String mode;
    boolean armor;
    boolean dura;
    boolean players;
    boolean invis;
    boolean mobs;
    boolean animals;
    private ArrayList<Entity> entities;
    float _x;
    float _y;
    float _z;
    
    public NameTags() {
        super("NameTags", 0, Category.RENDER, "Display large nametags");
        this.players = true;
        this.mobs = false;
        this.animals = false;
    }
    
    @Override
    public void setup() {
        final ArrayList<String> list;
        (list = new ArrayList<String>()).add("Hearts");
        list.add("Percentage");
        MedusaWare.instance.sm.rSetting(new Setting("HealthMode", this, "Percentage", list));
        MedusaWare.instance.sm.rSetting(new Setting("Scale", this, 5.0, 0.1, 10.0, false));
        MedusaWare.instance.sm.rSetting(new Setting("Range", this, 0.0, 0.0, 512.0, true));
        MedusaWare.instance.sm.rSetting(new Setting("Armor", this, true));
        MedusaWare.instance.sm.rSetting(new Setting("Durability", this, false));
        MedusaWare.instance.sm.rSetting(new Setting("Invisibles", this, true));
    }
    
    @Override
    public void onUpdate() {
        this.scale = (float) MedusaWare.instance.sm.getSettingByName(this, "Scale").getValDouble();
        this.mode = MedusaWare.instance.sm.getSettingByName(this, "HealthMode").getValString();
        this.armor = MedusaWare.instance.sm.getSettingByName(this, "Armor").getValBoolean();
        this.dura = MedusaWare.instance.sm.getSettingByName(this, "Durability").getValBoolean();
        this.invis = MedusaWare.instance.sm.getSettingByName(this, "Invisibles").getValBoolean();
    }
    
    @SubscribeEvent
    public void nameTag(final RenderLivingEvent.Specials.Pre<EntityPlayer> pre) {
        if (pre.entity.getDisplayName().getFormattedText() != null && pre.entity.getDisplayName().getFormattedText() != "" && pre.entity instanceof EntityPlayer && (Game.Player().getDistanceToEntity((Entity)pre.entity) <= MedusaWare.instance.sm.getSettingByName(this, "Range").getValDouble() || MedusaWare.instance.sm.getSettingByName(this, "Range").getValDouble() == 0.0)) {
            pre.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void render3d(final RenderWorldLastEvent renderWorldLastEvent) {
        final ArrayList<EntityPlayer> list = new ArrayList<EntityPlayer>();
        if (list.size() > 100) {
            list.clear();
        }
        for (final EntityLivingBase o : NameTags.mc.theWorld.playerEntities) {
            if (Game.Player().getDistanceToEntity((Entity)o) > MedusaWare.instance.sm.getSettingByName(this, "Range").getValDouble() && MedusaWare.instance.sm.getSettingByName(this, "Range").getValDouble() != 0.0) {
                if (!list.contains(o)) {
                    continue;
                }
                list.remove(o);
            }
            else if (o.getName().contains("[NPC]")) {
                if (!list.contains(o)) {
                    continue;
                }
                list.remove(o);
            }
            else if (o.isEntityAlive()) {
                if (o.isInvisible() && !this.invis) {
                    if (!list.contains(o)) {
                        continue;
                    }
                    list.remove(o);
                }
                else if (o == NameTags.mc.thePlayer) {
                    if (!list.contains(o)) {
                        continue;
                    }
                    list.remove(o);
                }
                else {
                    if (list.size() > 100) {
                        break;
                    }
                    if (list.contains(o)) {
                        continue;
                    }
                    list.add((EntityPlayer)o);
                }
            }
            else {
                if (!list.contains(o)) {
                    continue;
                }
                list.remove(o);
            }
        }
        this._x = 0.0f;
        this._y = 0.0f;
        this._z = 0.0f;
        for (final EntityPlayer entityPlayer : list) {
            entityPlayer.setAlwaysRenderNameTag(false);
            this._x = (float)(entityPlayer.lastTickPosX + (entityPlayer.posX - entityPlayer.lastTickPosX) * PrivateUtils.timer().renderPartialTicks - Game.Minecraft().getRenderManager().viewerPosX);
            this._y = (float)(entityPlayer.lastTickPosY + (entityPlayer.posY - entityPlayer.lastTickPosY) * PrivateUtils.timer().renderPartialTicks - Game.Minecraft().getRenderManager().viewerPosY);
            this._z = (float)(entityPlayer.lastTickPosZ + (entityPlayer.posZ - entityPlayer.lastTickPosZ) * PrivateUtils.timer().renderPartialTicks - Game.Minecraft().getRenderManager().viewerPosZ);
            this.renderNametag(entityPlayer, this._x, this._y, this._z);
        }
    }
    
    private String getHealth(final EntityPlayer entityPlayer) {
        final DecimalFormat decimalFormat = new DecimalFormat("0.#");
        return this.mode.equalsIgnoreCase("Percentage") ? decimalFormat.format(entityPlayer.getHealth() * 5.0f + entityPlayer.getAbsorptionAmount() * 5.0f) : decimalFormat.format(entityPlayer.getHealth() / 2.0f + entityPlayer.getAbsorptionAmount() / 2.0f);
    }
    
    private void drawNames(final EntityPlayer entityPlayer) {
        final float n2;
        final float n = n2 = (float)(this.getWidth(this.getPlayerName(entityPlayer)) / 2.0f + 2.2f + (this.getWidth(String.valueOf(new StringBuilder().append(" ").append(this.getHealth(entityPlayer)))) / 2 + 2.5));
        final float n3 = -n2 - 2.2f;
        final float n4 = (float)(this.getWidth(this.getPlayerName(entityPlayer)) + 4);
        if (this.mode.equalsIgnoreCase("Percentage")) {
            RenderUtils.drawBorderedRect(n3, -3.0f, n2, 10.0f, 1.0f, new Color(20, 20, 20, 180).getRGB(), new Color(10, 10, 10, 200).getRGB());
        }
        else {
            RenderUtils.drawBorderedRect(n3 + 5.0f, -3.0f, n2, 10.0f, 1.0f, new Color(20, 20, 20, 180).getRGB(), new Color(10, 10, 10, 200).getRGB());
        }
        GlStateManager.disableDepth();
        float n5;
        if (this.mode.equalsIgnoreCase("Percentage")) {
            n5 = n4 + (this.getWidth(this.getHealth(entityPlayer)) + this.getWidth(" %") - 1);
        }
        else {
            n5 = n4 + (this.getWidth(this.getHealth(entityPlayer)) + this.getWidth(" ") - 1);
        }
        this.drawString(this.getPlayerName(entityPlayer), n - n5, 0.0f, 16777215);
        if (entityPlayer.getHealth() == 10.0f) {}
        int n6;
        if (entityPlayer.getHealth() > 10.0f) {
            n6 = RenderUtils.blend(new Color(-16711936), new Color(-256), 1.0f / entityPlayer.getHealth() / 2.0f * (entityPlayer.getHealth() - 10.0f)).getRGB();
        }
        else {
            n6 = RenderUtils.blend(new Color(-256), new Color(-65536), 0.1f * entityPlayer.getHealth()).getRGB();
        }
        if (this.mode.equalsIgnoreCase("Percentage")) {
            this.drawString(String.valueOf(new StringBuilder().append(String.valueOf(this.getHealth(entityPlayer))).append("%")), n - this.getWidth(String.valueOf(new StringBuilder().append(String.valueOf(this.getHealth(entityPlayer))).append(" %"))), 0.0f, n6);
        }
        else {
            this.drawString(this.getHealth(entityPlayer), n - this.getWidth(String.valueOf(new StringBuilder().append(String.valueOf(this.getHealth(entityPlayer))).append(" "))), 0.0f, n6);
        }
        GlStateManager.enableDepth();
    }
    
    private void drawString(final String s, final float n, final float n2, final int n3) {
        NameTags.mc.fontRendererObj.drawStringWithShadow(s, n, n2, n3);
    }
    
    private int getWidth(final String s) {
        return NameTags.mc.fontRendererObj.getStringWidth(s);
    }
    
    private void startDrawing(final float n, final float n2, final float n3, final EntityPlayer entityPlayer) {
        final float n4 = (NameTags.mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f;
        final double n5 = this.getSize(entityPlayer) / 10.0f * this.scale * 1.5;
        GL11.glPushMatrix();
        RenderUtils.startDrawing();
        GL11.glTranslatef(n, n2, n3);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-NameTags.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(NameTags.mc.getRenderManager().playerViewX, n4, 0.0f, 0.0f);
        GL11.glScaled(-0.01666666753590107 * n5, -0.01666666753590107 * n5, 0.01666666753590107 * n5);
    }
    
    private void stopDrawing() {
        RenderUtils.stopDrawing();
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        GlStateManager.popMatrix();
    }
    
    private void renderNametag(final EntityPlayer entityPlayer, final float n, float n2, final float n3) {
        n2 += (float)(1.55 + (entityPlayer.isSneaking() ? 0.5 : 0.7));
        this.startDrawing(n, n2, n3, entityPlayer);
        this.drawNames(entityPlayer);
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
        if (this.armor) {
            this.renderArmor(entityPlayer);
        }
        this.stopDrawing();
    }
    
    private void renderArmor(final EntityPlayer entityPlayer) {
        final ItemStack[] armorInventory = entityPlayer.inventory.armorInventory;
        final int length = armorInventory.length;
        int n = 0;
        ItemStack[] array;
        for (int length2 = (array = armorInventory).length, i = 0; i < length2; ++i) {
            if (array[i] != null) {
                n -= 8;
            }
        }
        if (entityPlayer.getHeldItem() != null) {
            n -= 8;
            final ItemStack copy = entityPlayer.getHeldItem().copy();
            if (copy.hasEffect() && (copy.getItem() instanceof ItemTool || copy.getItem() instanceof ItemArmor)) {
                copy.stackSize = 1;
            }
            this.renderItemStack(copy, n, -20);
            n += 16;
        }
        final ItemStack[] armorInventory2 = entityPlayer.inventory.armorInventory;
        for (int j = 3; j >= 0; --j) {
            final ItemStack itemStack = armorInventory2[j];
            if (itemStack != null) {
                this.renderItemStack(itemStack, n, -20);
                n += 16;
            }
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private String getPlayerName(final EntityPlayer entityPlayer) {
        return entityPlayer.getDisplayName().getFormattedText();
    }
    
    private float getSize(final EntityPlayer entityPlayer) {
        return (NameTags.mc.thePlayer.getDistanceToEntity((Entity)entityPlayer) / 4.0f <= 2.0f) ? 2.0f : (NameTags.mc.thePlayer.getDistanceToEntity((Entity)entityPlayer) / 4.0f);
    }
    
    private void renderItemStack(final ItemStack itemStack, final int n, final int n2) {
        GlStateManager.pushMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.clear(256);
        RenderHelper.enableStandardItemLighting();
        NameTags.mc.getRenderItem().zLevel = -150.0f;
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        NameTags.mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, n, n2);
        NameTags.mc.getRenderItem().renderItemOverlays(NameTags.mc.fontRendererObj, itemStack, n, n2);
        NameTags.mc.getRenderItem().zLevel = 0.0f;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.disableDepth();
        this.renderEnchantText(itemStack, n, n2);
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GlStateManager.popMatrix();
    }
    
    private void renderEnchantText(final ItemStack itemStack, final int n, final int n2) {
        int n3 = n2 - 24;
        if (itemStack.getEnchantmentTagList() != null && itemStack.getEnchantmentTagList().tagCount() >= 6) {
            NameTags.mc.fontRendererObj.drawStringWithShadow("god", (float)(n * 2), (float)n3, 16711680);
            return;
        }
        if (itemStack.getItem() instanceof ItemArmor) {
            final int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, itemStack);
            final int enchantmentLevel2 = EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, itemStack);
            final int enchantmentLevel3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, itemStack);
            final int enchantmentLevel4 = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, itemStack);
            final int enchantmentLevel5 = EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, itemStack);
            final int enchantmentLevel6 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack);
            final int i = itemStack.getMaxDamage() - itemStack.getItemDamage();
            if (this.dura) {
                NameTags.mc.fontRendererObj.drawStringWithShadow(String.valueOf(new StringBuilder().append(i)), (float)(n * 2), (float)n2, 16777215);
            }
            if (enchantmentLevel > 0) {
                NameTags.mc.fontRendererObj.drawStringWithShadow(String.valueOf(new StringBuilder().append("prot").append(enchantmentLevel)), (float)(n * 2), (float)n3, -1);
                n3 += 8;
            }
            if (enchantmentLevel2 > 0) {
                NameTags.mc.fontRendererObj.drawStringWithShadow(String.valueOf(new StringBuilder().append("proj").append(enchantmentLevel2)), (float)(n * 2), (float)n3, -1);
                n3 += 8;
            }
            if (enchantmentLevel3 > 0) {
                NameTags.mc.fontRendererObj.drawStringWithShadow(String.valueOf(new StringBuilder().append("bp").append(enchantmentLevel3)), (float)(n * 2), (float)n3, -1);
                n3 += 8;
            }
            if (enchantmentLevel4 > 0) {
                NameTags.mc.fontRendererObj.drawStringWithShadow(String.valueOf(new StringBuilder().append("frp").append(enchantmentLevel4)), (float)(n * 2), (float)n3, -1);
                n3 += 8;
            }
            if (enchantmentLevel5 > 0) {
                NameTags.mc.fontRendererObj.drawStringWithShadow(String.valueOf(new StringBuilder().append("th").append(enchantmentLevel5)), (float)(n * 2), (float)n3, -1);
                n3 += 8;
            }
            if (enchantmentLevel6 > 0) {
                NameTags.mc.fontRendererObj.drawStringWithShadow(String.valueOf(new StringBuilder().append("unb").append(enchantmentLevel6)), (float)(n * 2), (float)n3, -1);
                n3 += 8;
            }
        }
        if (itemStack.getItem() instanceof ItemBow) {
            final int enchantmentLevel7 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemStack);
            final int enchantmentLevel8 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemStack);
            final int enchantmentLevel9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemStack);
            final int enchantmentLevel10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack);
            if (enchantmentLevel7 > 0) {
                NameTags.mc.fontRendererObj.drawStringWithShadow(String.valueOf(new StringBuilder().append("pow").append(enchantmentLevel7)), (float)(n * 2), (float)n3, -1);
                n3 += 8;
            }
            if (enchantmentLevel8 > 0) {
                NameTags.mc.fontRendererObj.drawStringWithShadow(String.valueOf(new StringBuilder().append("pun").append(enchantmentLevel8)), (float)(n * 2), (float)n3, -1);
                n3 += 8;
            }
            if (enchantmentLevel9 > 0) {
                NameTags.mc.fontRendererObj.drawStringWithShadow(String.valueOf(new StringBuilder().append("flame").append(enchantmentLevel9)), (float)(n * 2), (float)n3, -1);
                n3 += 8;
            }
            if (enchantmentLevel10 > 0) {
                NameTags.mc.fontRendererObj.drawStringWithShadow(String.valueOf(new StringBuilder().append("unb").append(enchantmentLevel10)), (float)(n * 2), (float)n3, -1);
                n3 += 8;
            }
        }
        if (itemStack.getItem() instanceof ItemSword) {
            final int enchantmentLevel11 = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack);
            final int enchantmentLevel12 = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, itemStack);
            final int enchantmentLevel13 = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemStack);
            final int enchantmentLevel14 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack);
            if (enchantmentLevel11 > 0) {
                NameTags.mc.fontRendererObj.drawStringWithShadow(String.valueOf(new StringBuilder().append("sh").append(enchantmentLevel11)), (float)(n * 2), (float)n3, -1);
                n3 += 8;
            }
            if (enchantmentLevel12 > 0) {
                NameTags.mc.fontRendererObj.drawStringWithShadow(String.valueOf(new StringBuilder().append("kb").append(enchantmentLevel12)), (float)(n * 2), (float)n3, -1);
                n3 += 8;
            }
            if (enchantmentLevel13 > 0) {
                NameTags.mc.fontRendererObj.drawStringWithShadow(String.valueOf(new StringBuilder().append("fire").append(enchantmentLevel13)), (float)(n * 2), (float)n3, -1);
                n3 += 8;
            }
            if (enchantmentLevel14 > 0) {
                NameTags.mc.fontRendererObj.drawStringWithShadow(String.valueOf(new StringBuilder().append("unb").append(enchantmentLevel14)), (float)(n * 2), (float)n3, -1);
            }
        }
        if (itemStack.getItem() instanceof ItemTool) {
            final int enchantmentLevel15 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack);
            final int enchantmentLevel16 = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack);
            final int enchantmentLevel17 = EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, itemStack);
            final int enchantmentLevel18 = EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, itemStack);
            if (enchantmentLevel16 > 0) {
                NameTags.mc.fontRendererObj.drawStringWithShadow(String.valueOf(new StringBuilder().append("eff").append(enchantmentLevel16)), (float)(n * 2), (float)n3, -1);
                n3 += 8;
            }
            if (enchantmentLevel17 > 0) {
                NameTags.mc.fontRendererObj.drawStringWithShadow(String.valueOf(new StringBuilder().append("fo").append(enchantmentLevel17)), (float)(n * 2), (float)n3, -1);
                n3 += 8;
            }
            if (enchantmentLevel18 > 0) {
                NameTags.mc.fontRendererObj.drawStringWithShadow(String.valueOf(new StringBuilder().append("silk").append(enchantmentLevel18)), (float)(n * 2), (float)n3, -1);
                n3 += 8;
            }
            if (enchantmentLevel15 > 0) {
                NameTags.mc.fontRendererObj.drawStringWithShadow(String.valueOf(new StringBuilder().append("ub").append(enchantmentLevel15)), (float)(n * 2), (float)n3, -1);
            }
        }
        if (itemStack.getItem() == Items.golden_apple && itemStack.hasEffect()) {
            NameTags.mc.fontRendererObj.drawStringWithShadow("god", (float)(n * 2), (float)n3, -1);
        }
    }
}
