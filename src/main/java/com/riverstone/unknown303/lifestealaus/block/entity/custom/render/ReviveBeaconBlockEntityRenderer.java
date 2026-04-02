package com.riverstone.unknown303.lifestealaus.block.entity.custom.render;

import net.minecraft.block.entity.BeamEmitter;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.state.BeaconBlockEntityRenderState;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

public class ReviveBeaconBlockEntityRenderer<T extends BlockEntity & BeamEmitter> implements BlockEntityRenderer<T, BeaconBlockEntityRenderState> {
    public static final Identifier BEAM_TEXTURE = Identifier.ofVanilla("textures/entity/beacon_beam.png");
    public static final int MAX_BEAM_HEIGHT = 2048;
    private static final float field_56505 = 96.0F;
    public static final float field_56503 = 0.2F;
    public static final float field_56504 = 0.25F;

    public BeaconBlockEntityRenderState createRenderState() {
        return new BeaconBlockEntityRenderState();
    }

    public void updateRenderState(
            T blockEntity,
            BeaconBlockEntityRenderState beaconBlockEntityRenderState,
            float f,
            Vec3d vec3d,
            ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlayCommand
    ) {
        BlockEntityRenderer.super.updateRenderState(blockEntity, beaconBlockEntityRenderState, f, vec3d, crumblingOverlayCommand);
        updateBeaconRenderState(blockEntity, beaconBlockEntityRenderState, f, vec3d);
    }

    public static <T extends BlockEntity & BeamEmitter> void updateBeaconRenderState(
            T blockEntity, BeaconBlockEntityRenderState state, float tickProgress, Vec3d cameraPos
    ) {
        state.beamRotationDegrees = blockEntity.getWorld() != null ? Math.floorMod(blockEntity.getWorld().getTime(), 40) + tickProgress : 0.0F;
        state.beamSegments = blockEntity.getBeamSegments()
                .stream()
                .map(beamSegment -> new BeaconBlockEntityRenderState.BeamSegment(beamSegment.getColor(), beamSegment.getHeight()))
                .toList();
        float f = (float)cameraPos.subtract(state.pos.toCenterPos()).horizontalLength();
        ClientPlayerEntity clientPlayerEntity = MinecraftClient.getInstance().player;
        state.beamScale = clientPlayerEntity != null && clientPlayerEntity.isUsingSpyglass() ? 1.0F : Math.max(1.0F, f / 96.0F);
    }

    public void render(
            BeaconBlockEntityRenderState beaconBlockEntityRenderState,
            MatrixStack matrixStack,
            OrderedRenderCommandQueue orderedRenderCommandQueue,
            CameraRenderState cameraRenderState
    ) {
        float i = 0.9375F;

        for (int j = 0; j < beaconBlockEntityRenderState.beamSegments.size(); j++) {
            BeaconBlockEntityRenderState.BeamSegment beamSegment = beaconBlockEntityRenderState.beamSegments.get(j);
            renderBeam(
                    matrixStack,
                    orderedRenderCommandQueue,
                    beaconBlockEntityRenderState.beamScale,
                    beaconBlockEntityRenderState.beamRotationDegrees,
                    i,
                    j == beaconBlockEntityRenderState.beamSegments.size() - 1 ? 2048 : beamSegment.height(),
                    beamSegment.color()
            );
            i += beamSegment.height();
        }
    }

    private static void renderBeam(
            MatrixStack matrices, OrderedRenderCommandQueue queue, float scale, float rotationDegrees, float minHeight, int maxHeight, int color
    ) {
        renderBeam(matrices, queue, BEAM_TEXTURE, 1.0F, rotationDegrees, minHeight, maxHeight, color, 0.2F * scale, 0.25F * scale);
    }

    public static void renderBeam(
            MatrixStack matrices,
            OrderedRenderCommandQueue queue,
            Identifier textureId,
            float beamHeight,
            float beamRotationDegrees,
            float minHeight,
            int maxHeight,
            int color,
            float innerScale,
            float outerScale
    ) {
        float i = minHeight + maxHeight;
        matrices.push();
        matrices.translate(0.5, 0.0, 0.5);
        float f = maxHeight < 0 ? beamRotationDegrees : -beamRotationDegrees;
        float g = MathHelper.fractionalPart(f * 0.2F - MathHelper.floor(f * 0.1F));
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(beamRotationDegrees * 2.25F - 45.0F));
        float h = 0.0F;
        float l = 0.0F;
        float m = -innerScale;
        float n = 0.0F;
        float o = 0.0F;
        float p = -innerScale;
        float q = 0.0F;
        float r = 1.0F;
        float s = -1.0F + g;
        float t = maxHeight * beamHeight * (0.5F / innerScale) + s;
        float finalM = m;
        float finalT = t;
        float finalS = s;
        queue.submitCustom(
                matrices,
                RenderLayers.beaconBeam(textureId, false),
                (matricesEntry, vertexConsumer) -> renderBeamLayer(
                        matricesEntry, vertexConsumer, color, minHeight, i, 0.0F, innerScale, innerScale, 0.0F, finalM, 0.0F, 0.0F, p, 0.0F, 1.0F, finalT, finalS
                )
        );
        matrices.pop();
        h = -outerScale;
        float j = -outerScale;
        l = -outerScale;
        m = -outerScale;
        q = 0.0F;
        r = 1.0F;
        s = -1.0F + g;
        t = maxHeight * beamHeight + s;
        float finalH = h;
        float finalT1 = t;
        float finalS1 = s;
        float finalL = l;
        float finalM1 = m;
        queue.submitCustom(
                matrices,
                RenderLayers.beaconBeam(textureId, true),
                (matricesEntry, vertexConsumer) -> renderBeamLayer(
                        matricesEntry, vertexConsumer, ColorHelper.withAlpha(32, color), minHeight, i, finalH, j, outerScale, finalL, finalM1, outerScale, outerScale, outerScale, 0.0F, 1.0F, finalT1, finalS1
                )
        );
        matrices.pop();
    }

    private static void renderBeamLayer(
            MatrixStack.Entry matricesEntry,
            VertexConsumer vertices,
            int color,
            float yOffset,
            float height,
            float x1,
            float z1,
            float x2,
            float z2,
            float x3,
            float z3,
            float x4,
            float z4,
            float u1,
            float u2,
            float v1,
            float v2
    ) {
        renderBeamFace(matricesEntry, vertices, color, yOffset, height, x1, z1, x2, z2, u1, u2, v1, v2);
        renderBeamFace(matricesEntry, vertices, color, yOffset, height, x4, z4, x3, z3, u1, u2, v1, v2);
        renderBeamFace(matricesEntry, vertices, color, yOffset, height, x2, z2, x4, z4, u1, u2, v1, v2);
        renderBeamFace(matricesEntry, vertices, color, yOffset, height, x3, z3, x1, z1, u1, u2, v1, v2);
    }

    private static void renderBeamFace(
            MatrixStack.Entry matrix,
            VertexConsumer vertices,
            int color,
            float yOffset,
            float height,
            float x1,
            float z1,
            float x2,
            float z2,
            float u1,
            float u2,
            float v1,
            float v2
    ) {
        renderBeamVertex(matrix, vertices, color, height, x1, z1, u2, v1);
        renderBeamVertex(matrix, vertices, color, yOffset, x1, z1, u2, v2);
        renderBeamVertex(matrix, vertices, color, yOffset, x2, z2, u1, v2);
        renderBeamVertex(matrix, vertices, color, height, x2, z2, u1, v1);
    }

    private static void renderBeamVertex(MatrixStack.Entry matrix, VertexConsumer vertices, int color, float y, float x, float z, float u, float v) {
        vertices.vertex(matrix, x, y, z)
                .color(color)
                .texture(u, v)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                .normal(matrix, 0.0F, 1.0F, 0.0F);
    }

    @Override
    public boolean rendersOutsideBoundingBox() {
        return true;
    }

    @Override
    public int getRenderDistance() {
        return MinecraftClient.getInstance().options.getClampedViewDistance() * 16;
    }

    @Override
    public boolean isInRenderDistance(T blockEntity, Vec3d pos) {
        return Vec3d.ofCenter(blockEntity.getPos()).multiply(1.0, 0.0, 1.0).isInRange(pos.multiply(1.0, 0.0, 1.0), this.getRenderDistance());
    }
}
