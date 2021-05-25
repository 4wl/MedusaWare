

package medusa.ware.net;

import medusa.ware.MedusaWare;
import medusa.ware.module.combat.Reach;
import net.minecraft.network.play.server.S20PacketEntityProperties;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.network.play.server.S44PacketWorldBorder;
import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
import net.minecraft.network.play.server.S3CPacketUpdateScore;
import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.network.play.server.S3APacketTabComplete;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S37PacketStatistics;
import net.minecraft.network.play.server.S28PacketEffect;
import net.minecraft.network.play.server.S34PacketMaps;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S26PacketMapChunkBulk;
import net.minecraft.network.play.server.S25PacketBlockBreakAnim;
import net.minecraft.network.play.server.S24PacketBlockAction;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.network.play.server.S31PacketWindowProperty;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.play.server.S33PacketUpdateSign;
import net.minecraft.network.play.server.S36PacketSignEditorOpen;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.network.play.server.S06PacketUpdateHealth;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.network.play.server.S05PacketSpawnPosition;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S0FPacketSpawnMob;
import net.minecraft.network.play.server.S0APacketUseBed;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S0DPacketCollectItem;
import net.minecraft.util.IChatComponent;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.network.play.server.S19PacketEntityHeadLook;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S09PacketHeldItemChange;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import medusa.ware.utils.CombatUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S1CPacketEntityMetadata;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S10PacketSpawnPainting;
import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
import net.minecraft.network.play.server.S11PacketSpawnExperienceOrb;
import net.minecraft.network.play.server.S0EPacketSpawnObject;
import net.minecraft.network.play.server.S01PacketJoinGame;
import java.lang.reflect.Field;
import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.client.gui.GuiScreen;
import medusa.ware.utils.Game;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;

public class NetHandler extends NetHandlerPlayClient
{
    private NetHandlerPlayClient parent;
    
    public NetHandler(final NetHandlerPlayClient parent) {
        super(Minecraft.getMinecraft(), getGuiScreen(parent), parent.getNetworkManager(), Game.Player().getGameProfile());
        this.parent = parent;
    }
    
    private static GuiScreen getGuiScreen(final NetHandlerPlayClient obj) {
        for (final Field field : obj.getClass().getDeclaredFields()) {
            if (field.getType().equals(GuiScreen.class)) {
                field.setAccessible(true);
                try {
                    return (GuiScreen)field.get(obj);
                }
                catch (Exception ex) {
                    return null;
                }
            }
        }
        return null;
    }
    
    private double a(final float n, final float n2) {
        float n3 = Math.abs(n - n2) % 360.0f;
        if (n3 > 180.0f) {
            n3 = 360.0f - n3;
        }
        return n3;
    }
    
    private float a(final double n, final double n2) {
        final double n3 = n - Minecraft.getMinecraft().thePlayer.posX;
        final double n4 = n2 - Minecraft.getMinecraft().thePlayer.posZ;
        float n5 = (float)Math.toDegrees(-Math.atan(n3 / n4));
        if (n4 < 0.0 && n3 < 0.0) {
            n5 = (float)(90.0 + Math.toDegrees(Math.atan(n4 / n3)));
        }
        else if (n4 < 0.0 && n3 > 0.0) {
            n5 = (float)(-90.0 + Math.toDegrees(Math.atan(n4 / n3)));
        }
        return n5;
    }
    
    public void handleJoinGame(final S01PacketJoinGame s01PacketJoinGame) {
        this.parent.handleJoinGame(s01PacketJoinGame);
    }
    
    public void handleSpawnObject(final S0EPacketSpawnObject s0EPacketSpawnObject) {
        this.parent.handleSpawnObject(s0EPacketSpawnObject);
    }
    
    public void handleSpawnExperienceOrb(final S11PacketSpawnExperienceOrb s11PacketSpawnExperienceOrb) {
        this.parent.handleSpawnExperienceOrb(s11PacketSpawnExperienceOrb);
    }
    
    public void handleSpawnGlobalEntity(final S2CPacketSpawnGlobalEntity s2CPacketSpawnGlobalEntity) {
        this.parent.handleSpawnGlobalEntity(s2CPacketSpawnGlobalEntity);
    }
    
    public void handleSpawnPainting(final S10PacketSpawnPainting s10PacketSpawnPainting) {
        this.parent.handleSpawnPainting(s10PacketSpawnPainting);
    }
    
    public void handleEntityVelocity(final S12PacketEntityVelocity s12PacketEntityVelocity) {
        this.parent.handleEntityVelocity(s12PacketEntityVelocity);
    }
    
    public void handleEntityMetadata(final S1CPacketEntityMetadata s1CPacketEntityMetadata) {
        this.parent.handleEntityMetadata(s1CPacketEntityMetadata);
    }
    
    public void handleSpawnPlayer(final S0CPacketSpawnPlayer s0CPacketSpawnPlayer) {
        this.parent.handleSpawnPlayer(s0CPacketSpawnPlayer);
    }
    
    public void handleEntityTeleport(final S18PacketEntityTeleport s18PacketEntityTeleport) {
        final Entity entityByID = Minecraft.getMinecraft().theWorld.getEntityByID(s18PacketEntityTeleport.getEntityId());
        if (MedusaWare.destructed) {
            this.parent.handleEntityTeleport(s18PacketEntityTeleport);
            return;
        }
        final Reach reach = (Reach) MedusaWare.instance.mm.getModuleByName("Reach");
        if (entityByID instanceof EntityPlayer && CombatUtils.canTarget(entityByID) && reach.isToggled() && MedusaWare.instance.sm.getSettingByName(reach, "Misplace").getValBoolean()) {
            final double n = s18PacketEntityTeleport.getX() / 32.0;
            final double n2 = s18PacketEntityTeleport.getZ() / 32.0;
            final double n3 = MedusaWare.instance.sm.getSettingByName(reach, "MinReach").getValDouble() - 3.0;
            final double n4 = MedusaWare.instance.sm.getSettingByName(reach, "MaxReach").getValDouble() - 3.0;
            double nextDouble = ThreadLocalRandom.current().nextDouble(Math.min(n3, n4), Math.max(n3, n4));
            if (nextDouble == 0.0) {
                this.parent.handleEntityTeleport(s18PacketEntityTeleport);
                return;
            }
            final double hypot = Math.hypot(Minecraft.getMinecraft().thePlayer.posX - n, Minecraft.getMinecraft().thePlayer.posZ - n2);
            if (nextDouble > hypot) {
                nextDouble -= hypot;
            }
            final float a = this.a(n, n2);
            if (this.a(Minecraft.getMinecraft().thePlayer.rotationYaw, a) > 180.0) {
                this.parent.handleEntityTeleport(s18PacketEntityTeleport);
                return;
            }
            final double cos = Math.cos(Math.toRadians(a + 90.0f));
            final double sin = Math.sin(Math.toRadians(a + 90.0f));
            final double n5 = n - cos * nextDouble;
            final double n6 = n2 - sin * nextDouble;
            final Class<? extends S18PacketEntityTeleport> class1 = s18PacketEntityTeleport.getClass();
            try {
                final Field declaredField = class1.getDeclaredField("field_149456_b");
                declaredField.setAccessible(true);
                declaredField.set(s18PacketEntityTeleport, MathHelper.floor_double(n5 * 32.0));
                final Field declaredField2 = class1.getDeclaredField("field_149454_d");
                declaredField2.setAccessible(true);
                declaredField2.set(s18PacketEntityTeleport, MathHelper.floor_double(n6 * 32.0));
            }
            catch (Exception ex) {}
            try {
                final Field declaredField3 = class1.getDeclaredField("posX");
                declaredField3.setAccessible(true);
                declaredField3.set(s18PacketEntityTeleport, MathHelper.floor_double(n5 * 32.0));
                final Field declaredField4 = class1.getDeclaredField("posZ");
                declaredField4.setAccessible(true);
                declaredField4.set(s18PacketEntityTeleport, MathHelper.floor_double(n6 * 32.0));
            }
            catch (Exception ex2) {}
        }
        this.parent.handleEntityTeleport(s18PacketEntityTeleport);
    }
    
    public void handleHeldItemChange(final S09PacketHeldItemChange s09PacketHeldItemChange) {
        this.parent.handleHeldItemChange(s09PacketHeldItemChange);
    }
    
    public void handleEntityMovement(final S14PacketEntity s14PacketEntity) {
        this.parent.handleEntityMovement(s14PacketEntity);
    }
    
    public void handleEntityHeadLook(final S19PacketEntityHeadLook s19PacketEntityHeadLook) {
        this.parent.handleEntityHeadLook(s19PacketEntityHeadLook);
    }
    
    public void handleDestroyEntities(final S13PacketDestroyEntities s13PacketDestroyEntities) {
        this.parent.handleDestroyEntities(s13PacketDestroyEntities);
    }
    
    public void handlePlayerPosLook(final S08PacketPlayerPosLook s08PacketPlayerPosLook) {
        this.parent.handlePlayerPosLook(s08PacketPlayerPosLook);
    }
    
    public void handleMultiBlockChange(final S22PacketMultiBlockChange s22PacketMultiBlockChange) {
        this.parent.handleMultiBlockChange(s22PacketMultiBlockChange);
    }
    
    public void handleChunkData(final S21PacketChunkData s21PacketChunkData) {
        this.parent.handleChunkData(s21PacketChunkData);
    }
    
    public void handleBlockChange(final S23PacketBlockChange s23PacketBlockChange) {
        this.parent.handleBlockChange(s23PacketBlockChange);
    }
    
    public void handleDisconnect(final S40PacketDisconnect s40PacketDisconnect) {
        this.parent.handleDisconnect(s40PacketDisconnect);
    }
    
    public void onDisconnect(final IChatComponent chatComponent) {
        this.parent.onDisconnect(chatComponent);
    }
    
    public void handleCollectItem(final S0DPacketCollectItem s0DPacketCollectItem) {
        this.parent.handleCollectItem(s0DPacketCollectItem);
    }
    
    public void handleChat(final S02PacketChat s02PacketChat) {
        this.parent.handleChat(s02PacketChat);
    }
    
    public void handleAnimation(final S0BPacketAnimation s0BPacketAnimation) {
        this.parent.handleAnimation(s0BPacketAnimation);
    }
    
    public void handleUseBed(final S0APacketUseBed s0APacketUseBed) {
        this.parent.handleUseBed(s0APacketUseBed);
    }
    
    public void handleSpawnMob(final S0FPacketSpawnMob s0FPacketSpawnMob) {
        this.parent.handleSpawnMob(s0FPacketSpawnMob);
    }
    
    public void handleTimeUpdate(final S03PacketTimeUpdate s03PacketTimeUpdate) {
        this.parent.handleTimeUpdate(s03PacketTimeUpdate);
    }
    
    public void handleSpawnPosition(final S05PacketSpawnPosition s05PacketSpawnPosition) {
        this.parent.handleSpawnPosition(s05PacketSpawnPosition);
    }
    
    public void handleEntityAttach(final S1BPacketEntityAttach s1BPacketEntityAttach) {
        this.parent.handleEntityAttach(s1BPacketEntityAttach);
    }
    
    public void handleEntityStatus(final S19PacketEntityStatus s19PacketEntityStatus) {
        this.parent.handleEntityStatus(s19PacketEntityStatus);
    }
    
    public void handleUpdateHealth(final S06PacketUpdateHealth s06PacketUpdateHealth) {
        this.parent.handleUpdateHealth(s06PacketUpdateHealth);
    }
    
    public void handleSetExperience(final S1FPacketSetExperience s1FPacketSetExperience) {
        this.parent.handleSetExperience(s1FPacketSetExperience);
    }
    
    public void handleRespawn(final S07PacketRespawn s07PacketRespawn) {
        this.parent.handleRespawn(s07PacketRespawn);
    }
    
    public void handleExplosion(final S27PacketExplosion s27PacketExplosion) {
        this.parent.handleExplosion(s27PacketExplosion);
    }
    
    public void handleOpenWindow(final S2DPacketOpenWindow s2DPacketOpenWindow) {
        this.parent.handleOpenWindow(s2DPacketOpenWindow);
    }
    
    public void handleSetSlot(final S2FPacketSetSlot s2FPacketSetSlot) {
        this.parent.handleSetSlot(s2FPacketSetSlot);
    }
    
    public void handleConfirmTransaction(final S32PacketConfirmTransaction s32PacketConfirmTransaction) {
        this.parent.handleConfirmTransaction(s32PacketConfirmTransaction);
    }
    
    public void handleWindowItems(final S30PacketWindowItems s30PacketWindowItems) {
        this.parent.handleWindowItems(s30PacketWindowItems);
    }
    
    public void handleSignEditorOpen(final S36PacketSignEditorOpen s36PacketSignEditorOpen) {
        this.parent.handleSignEditorOpen(s36PacketSignEditorOpen);
    }
    
    public void handleUpdateSign(final S33PacketUpdateSign s33PacketUpdateSign) {
        this.parent.handleUpdateSign(s33PacketUpdateSign);
    }
    
    public void handleUpdateTileEntity(final S35PacketUpdateTileEntity s35PacketUpdateTileEntity) {
        this.parent.handleUpdateTileEntity(s35PacketUpdateTileEntity);
    }
    
    public void handleWindowProperty(final S31PacketWindowProperty s31PacketWindowProperty) {
        this.parent.handleWindowProperty(s31PacketWindowProperty);
    }
    
    public void handleEntityEquipment(final S04PacketEntityEquipment s04PacketEntityEquipment) {
        this.parent.handleEntityEquipment(s04PacketEntityEquipment);
    }
    
    public void handleCloseWindow(final S2EPacketCloseWindow s2EPacketCloseWindow) {
        this.parent.handleCloseWindow(s2EPacketCloseWindow);
    }
    
    public void handleBlockAction(final S24PacketBlockAction s24PacketBlockAction) {
        this.parent.handleBlockAction(s24PacketBlockAction);
    }
    
    public void handleBlockBreakAnim(final S25PacketBlockBreakAnim s25PacketBlockBreakAnim) {
        this.parent.handleBlockBreakAnim(s25PacketBlockBreakAnim);
    }
    
    public void handleMapChunkBulk(final S26PacketMapChunkBulk s26PacketMapChunkBulk) {
        this.parent.handleMapChunkBulk(s26PacketMapChunkBulk);
    }
    
    public void handleChangeGameState(final S2BPacketChangeGameState s2BPacketChangeGameState) {
        this.parent.handleChangeGameState(s2BPacketChangeGameState);
    }
    
    public void handleMaps(final S34PacketMaps s34PacketMaps) {
        this.parent.handleMaps(s34PacketMaps);
    }
    
    public void handleEffect(final S28PacketEffect s28PacketEffect) {
        this.parent.handleEffect(s28PacketEffect);
    }
    
    public void handleStatistics(final S37PacketStatistics s37PacketStatistics) {
        this.parent.handleStatistics(s37PacketStatistics);
    }
    
    public void handleEntityEffect(final S1DPacketEntityEffect s1DPacketEntityEffect) {
        this.parent.handleEntityEffect(s1DPacketEntityEffect);
    }
    
    public void handleRemoveEntityEffect(final S1EPacketRemoveEntityEffect s1EPacketRemoveEntityEffect) {
        this.parent.handleRemoveEntityEffect(s1EPacketRemoveEntityEffect);
    }
    
    public void handlePlayerListItem(final S38PacketPlayerListItem s38PacketPlayerListItem) {
        this.parent.handlePlayerListItem(s38PacketPlayerListItem);
    }
    
    public void handleKeepAlive(final S00PacketKeepAlive s00PacketKeepAlive) {
        this.parent.handleKeepAlive(s00PacketKeepAlive);
    }
    
    public void handlePlayerAbilities(final S39PacketPlayerAbilities s39PacketPlayerAbilities) {
        this.parent.handlePlayerAbilities(s39PacketPlayerAbilities);
    }
    
    public void handleTabComplete(final S3APacketTabComplete s3APacketTabComplete) {
        this.parent.handleTabComplete(s3APacketTabComplete);
    }
    
    public void handleSoundEffect(final S29PacketSoundEffect s29PacketSoundEffect) {
        this.parent.handleSoundEffect(s29PacketSoundEffect);
    }
    
    public void handleCustomPayload(final S3FPacketCustomPayload s3FPacketCustomPayload) {
        this.parent.handleCustomPayload(s3FPacketCustomPayload);
    }
    
    public void handleScoreboardObjective(final S3BPacketScoreboardObjective s3BPacketScoreboardObjective) {
        this.parent.handleScoreboardObjective(s3BPacketScoreboardObjective);
    }
    
    public void handleUpdateScore(final S3CPacketUpdateScore s3CPacketUpdateScore) {
        this.parent.handleUpdateScore(s3CPacketUpdateScore);
    }
    
    public void handleDisplayScoreboard(final S3DPacketDisplayScoreboard s3DPacketDisplayScoreboard) {
        this.parent.handleDisplayScoreboard(s3DPacketDisplayScoreboard);
    }
    
    public void handleWorldBorder(final S44PacketWorldBorder s44PacketWorldBorder) {
        this.parent.handleWorldBorder(s44PacketWorldBorder);
    }
    
    public void handleTeams(final S3EPacketTeams s3EPacketTeams) {
        this.parent.handleTeams(s3EPacketTeams);
    }
    
    public void handleParticles(final S2APacketParticles s2APacketParticles) {
        this.parent.handleParticles(s2APacketParticles);
    }
    
    public void handleEntityProperties(final S20PacketEntityProperties s20PacketEntityProperties) {
        this.parent.handleEntityProperties(s20PacketEntityProperties);
    }
}
