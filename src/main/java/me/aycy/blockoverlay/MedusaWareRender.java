// 
// Decompiled by Procyon v0.5.36
// 

package me.aycy.blockoverlay;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderGlobal;
import java.awt.Color;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.world.WorldSettings;
import medusa.ware.utils.Game;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;

public class MedusaWareRender
{
    @SubscribeEvent
    public void onDrawBlockHighlight(final DrawBlockHighlightEvent drawBlockHighlightEvent) {
        if (Game.Player() == null || Game.World() == null || (!Game.Minecraft().playerController.getCurrentGameType().equals((Object)WorldSettings.GameType.SURVIVAL) && !Game.Minecraft().playerController.getCurrentGameType().equals((Object)WorldSettings.GameType.CREATIVE))) {
            return;
        }
        if (MedusaWare.mode.equals(MedusaWareMode.DEFAULT)) {
            return;
        }
        drawBlockHighlightEvent.setCanceled(true);
        if (MedusaWare.mode.equals(MedusaWareMode.NONE)) {
            return;
        }
        this.drawOverlay();
    }
    
    @SubscribeEvent
    public void onRenderWorldLast(final RenderWorldLastEvent renderWorldLastEvent) {
        if (Game.Player() == null || Game.World() == null || (!Game.Minecraft().playerController.getCurrentGameType().equals((Object)WorldSettings.GameType.ADVENTURE) && !Game.Minecraft().playerController.getCurrentGameType().equals((Object)WorldSettings.GameType.SPECTATOR))) {
            return;
        }
        if (MedusaWare.mode.equals(MedusaWareMode.NONE) || MedusaWare.mode.equals(MedusaWareMode.DEFAULT)) {
            return;
        }
        if (MedusaWare.alwaysRender) {
            this.drawOverlay();
        }
    }
    
    public void drawOverlay() {
        if (Game.Minecraft().objectMouseOver == null || !Game.Minecraft().objectMouseOver.typeOfHit.equals((Object)MovingObjectPosition.MovingObjectType.BLOCK)) {
            return;
        }
        final MovingObjectPosition rayTrace = Game.Player().rayTrace(6.0, 0.0f);
        if (rayTrace == null || !rayTrace.typeOfHit.equals((Object)MovingObjectPosition.MovingObjectType.BLOCK)) {
            return;
        }
        final Block block = Game.Player().worldObj.getBlockState(rayTrace.getBlockPos()).getBlock();
        if (block == null || block.equals(Blocks.air) || block.equals(Blocks.barrier) || block.equals(Blocks.water) || block.equals(Blocks.flowing_water) || block.equals(Blocks.lava) || block.equals(Blocks.flowing_lava)) {
            return;
        }
        GlStateManager.pushMatrix();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glLineWidth(MedusaWare.lineWidth);
        final AxisAlignedBB expand = block.getSelectedBoundingBox((World)Game.World(), rayTrace.getBlockPos()).offset(-Game.Minecraft().getRenderManager().viewerPosX, -Game.Minecraft().getRenderManager().viewerPosY, -Game.Minecraft().getRenderManager().viewerPosZ).expand(0.0010000000474974513, 0.0010000000474974513, 0.0010000000474974513);
        if (MedusaWare.mode.equals(MedusaWareMode.OUTLINE)) {
            if (MedusaWare.isChroma) {
                final Color hsbColor = Color.getHSBColor(System.currentTimeMillis() % (10000L / MedusaWare.chromaSpeed) / (10000.0f / MedusaWare.chromaSpeed), 0.8f, 0.8f);
                GL11.glColor4f(hsbColor.getRed() / 255.0f, hsbColor.getGreen() / 255.0f, hsbColor.getBlue() / 255.0f, MedusaWare.alpha);
            }
            else {
                GL11.glColor4f(MedusaWare.red, MedusaWare.green, MedusaWare.blue, MedusaWare.alpha);
            }
            RenderGlobal.drawSelectionBoundingBox(expand);
        }
        else if (MedusaWare.isChroma) {
            final Color hsbColor2 = Color.getHSBColor(System.currentTimeMillis() % (10000L / MedusaWare.chromaSpeed) / (10000.0f / MedusaWare.chromaSpeed), 0.8f, 0.8f);
            GL11.glColor4f(hsbColor2.getRed() / 255.0f, hsbColor2.getGreen() / 255.0f, hsbColor2.getBlue() / 255.0f, 1.0f);
            RenderGlobal.drawSelectionBoundingBox(expand);
            GL11.glColor4f(hsbColor2.getRed() / 255.0f, hsbColor2.getGreen() / 255.0f, hsbColor2.getBlue() / 255.0f, MedusaWare.alpha);
            this.drawFilledBoundingBox(expand);
        }
        else {
            GL11.glColor4f(MedusaWare.red, MedusaWare.green, MedusaWare.blue, 1.0f);
            RenderGlobal.drawSelectionBoundingBox(expand);
            GL11.glColor4f(MedusaWare.red, MedusaWare.green, MedusaWare.blue, MedusaWare.alpha);
            this.drawFilledBoundingBox(expand);
        }
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.popMatrix();
    }
    
    public void drawFilledBoundingBox(final AxisAlignedBB axisAlignedBB) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        instance.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        instance.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        instance.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        instance.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        instance.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        instance.draw();
    }
}
