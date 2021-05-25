

package medusa.ware.utils;

import java.lang.reflect.Method;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import java.awt.Color;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.BlockPos;

public class RenderUtils
{
    public static void renderBlockPos(final BlockPos blockPos, final int n) {
        if (blockPos == null) {
            return;
        }
        final double n2 = blockPos.getX() - Game.Minecraft().getRenderManager().viewerPosX;
        final double n3 = blockPos.getY() - Game.Minecraft().getRenderManager().viewerPosY;
        final double n4 = blockPos.getZ() - Game.Minecraft().getRenderManager().viewerPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        final float n5 = (n >> 24 & 0xFF) / 255.0f;
        final float n6 = (n >> 16 & 0xFF) / 255.0f;
        final float n7 = (n >> 8 & 0xFF) / 255.0f;
        final float n8 = (n & 0xFF) / 255.0f;
        GL11.glColor4d((double)n6, (double)n7, (double)n8, (double)n5);
        RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(n2, n3, n4, n2 + 1.0, n3 + 1.0, n4 + 1.0));
        dbb(new AxisAlignedBB(n2, n3, n4, n2 + 1.0, n3 + 1.0, n4 + 1.0), n6, n7, n8);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }
    
    public static void renderEntity(final Entity entity, int rgb, final int n) {
        if (entity == null) {
            return;
        }
        final double n2 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * PrivateUtils.timer().renderPartialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosX;
        final double n3 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * PrivateUtils.timer().renderPartialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosY;
        final double n4 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * PrivateUtils.timer().renderPartialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosZ;
        if (entity instanceof EntityPlayer && ((EntityPlayer)entity).hurtTime != 0) {
            rgb = Color.RED.getRGB();
        }
        if (n == 1) {
            GlStateManager.pushMatrix();
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glLineWidth(2.0f);
            final float n5 = (rgb >> 24 & 0xFF) / 255.0f;
            final float n6 = (rgb >> 16 & 0xFF) / 255.0f;
            final float n7 = (rgb >> 8 & 0xFF) / 255.0f;
            final float n8 = (rgb & 0xFF) / 255.0f;
            GL11.glColor4f(n6, n7, n8, n5);
            RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + (entity.posX - Minecraft.getMinecraft().getRenderManager().viewerPosX), entity.getEntityBoundingBox().minY - entity.posY + (entity.posY - Minecraft.getMinecraft().getRenderManager().viewerPosY), entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + (entity.posZ - Minecraft.getMinecraft().getRenderManager().viewerPosZ), entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + (entity.posX - Minecraft.getMinecraft().getRenderManager().viewerPosX), entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + (entity.posY - Minecraft.getMinecraft().getRenderManager().viewerPosY), entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + (entity.posZ - Minecraft.getMinecraft().getRenderManager().viewerPosZ)));
            dbb(new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + (entity.posX - Minecraft.getMinecraft().getRenderManager().viewerPosX), entity.getEntityBoundingBox().minY - entity.posY + (entity.posY - Minecraft.getMinecraft().getRenderManager().viewerPosY), entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + (entity.posZ - Minecraft.getMinecraft().getRenderManager().viewerPosZ), entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + (entity.posX - Minecraft.getMinecraft().getRenderManager().viewerPosX), entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + (entity.posY - Minecraft.getMinecraft().getRenderManager().viewerPosY), entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + (entity.posZ - Minecraft.getMinecraft().getRenderManager().viewerPosZ)), n6, n7, n8);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glDisable(3042);
            GlStateManager.popMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        else if (n == 2) {
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glLineWidth(2.0f);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glColor4d((double)((rgb >> 16 & 0xFF) / 255.0f), (double)((rgb >> 8 & 0xFF) / 255.0f), (double)((rgb & 0xFF) / 255.0f), (double)((rgb >> 24 & 0xFF) / 255.0f));
            RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + (entity.posX - Minecraft.getMinecraft().getRenderManager().viewerPosX), entity.getEntityBoundingBox().minY - entity.posY + (entity.posY - Minecraft.getMinecraft().getRenderManager().viewerPosY), entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + (entity.posZ - Minecraft.getMinecraft().getRenderManager().viewerPosZ), entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + (entity.posX - Minecraft.getMinecraft().getRenderManager().viewerPosX), entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + (entity.posY - Minecraft.getMinecraft().getRenderManager().viewerPosY), entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + (entity.posZ - Minecraft.getMinecraft().getRenderManager().viewerPosZ)));
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glDisable(3042);
        }
        else if (n == 3) {
            GL11.glPushMatrix();
            GL11.glTranslated(n2, n3 - 0.2, n4);
            GL11.glScalef(0.03f, 0.03f, 0.03f);
            GL11.glRotated((double)(-Minecraft.getMinecraft().getRenderManager().playerViewY), 0.0, 1.0, 0.0);
            GlStateManager.disableDepth();
            Gui.drawRect(-20, -1, -26, 15, Color.black.getRGB());
            Gui.drawRect(-21, 0, -25, 14, rgb);
            Gui.drawRect(-20, 61, -26, 75, Color.black.getRGB());
            Gui.drawRect(-21, 62, -25, 74, rgb);
            Gui.drawRect(20, 61, 26, 75, Color.black.getRGB());
            Gui.drawRect(21, 62, 25, 74, rgb);
            Gui.drawRect(20, -1, 26, 15, Color.black.getRGB());
            Gui.drawRect(21, 0, 25, 14, rgb);
            Gui.drawRect(-20, -1, -9, 5, Color.black.getRGB());
            Gui.drawRect(-21, 0, -10, 4, rgb);
            Gui.drawRect(20, -1, 9, 5, Color.black.getRGB());
            Gui.drawRect(21, 0, 10, 4, rgb);
            Gui.drawRect(20, 70, 9, 75, Color.black.getRGB());
            Gui.drawRect(21, 71, 10, 74, rgb);
            Gui.drawRect(-20, 70, -9, 75, Color.black.getRGB());
            Gui.drawRect(-21, 71, -10, 74, rgb);
            GlStateManager.enableDepth();
            GL11.glPopMatrix();
        }
    }
    
    public static void dbb(final AxisAlignedBB axisAlignedBB, final float n, final float n2, final float n3) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        instance.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        instance.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        instance.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        instance.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        instance.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, 0.25f).endVertex();
        instance.draw();
    }
    
    public static void stopDrawing() {
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }
    
    public static void startDrawing() {
        GL11.glEnable(3042);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        try {
            final Method declaredMethod = Minecraft.getMinecraft().entityRenderer.getClass().getDeclaredMethod("setupCameraTransform", Float.TYPE, Integer.TYPE);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(Minecraft.getMinecraft().entityRenderer, PrivateUtils.timer().renderPartialTicks, 0);
        }
        catch (Exception ex) {
            try {
                final Method declaredMethod2 = Minecraft.getMinecraft().entityRenderer.getClass().getDeclaredMethod("func_78479_a", Float.TYPE, Integer.TYPE);
                declaredMethod2.setAccessible(true);
                declaredMethod2.invoke(Minecraft.getMinecraft().entityRenderer, PrivateUtils.timer().renderPartialTicks, 0);
            }
            catch (Exception ex2) {}
        }
    }
    
    public static void blockESPBox(final BlockPos blockPos, final float n, final float n2, final float n3, final float n4) {
        final double n5 = blockPos.getX() - Game.Minecraft().getRenderManager().viewerPosX;
        final double n6 = blockPos.getY() - Game.Minecraft().getRenderManager().viewerPosY;
        final double n7 = blockPos.getZ() - Game.Minecraft().getRenderManager().viewerPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(n4);
        GL11.glColor4d(0.0, 1.0, 0.0, 0.15000000596046448);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4d((double)n, (double)n2, (double)n3, 1000.0);
        RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(n5, n6, n7, n5 + 1.0, n6 + 1.0, n7 + 1.0));
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }
    
    public static void drawBorderedRect(final float n, final float n2, final float n3, final float n4, final float n5, final int n6, final int n7) {
        final float n8 = (n6 >> 24 & 0xFF) / 255.0f;
        final float n9 = (n6 >> 16 & 0xFF) / 255.0f;
        final float n10 = (n6 >> 8 & 0xFF) / 255.0f;
        final float n11 = (n6 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(n9, n10, n11, n8);
        GL11.glLineWidth(n5);
        GL11.glBegin(1);
        GL11.glVertex2d((double)n, (double)n2);
        GL11.glVertex2d((double)n, (double)n4);
        GL11.glVertex2d((double)n3, (double)n4);
        GL11.glVertex2d((double)n3, (double)n2);
        GL11.glVertex2d((double)n, (double)n2);
        GL11.glVertex2d((double)n3, (double)n2);
        GL11.glVertex2d((double)n, (double)n4);
        GL11.glVertex2d((double)n3, (double)n4);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
    
    public static Color blend(final Color color, final Color color2, final double n) {
        final float n2 = (float)n;
        final float n3 = 1.0f - n2;
        final float[] compArray = new float[3];
        final float[] compArray2 = new float[3];
        color.getColorComponents(compArray);
        color2.getColorComponents(compArray2);
        return new Color(compArray[0] * n2 + compArray2[0] * n3, compArray[1] * n2 + compArray2[1] * n3, compArray[2] * n2 + compArray2[2] * n3);
    }
}
