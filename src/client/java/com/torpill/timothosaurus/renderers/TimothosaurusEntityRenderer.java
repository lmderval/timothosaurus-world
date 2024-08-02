package com.torpill.timothosaurus.renderers;

import com.torpill.timothosaurus.TimothosaurusWorld;
import com.torpill.timothosaurus.entities.TimothosaurusEntity;
import com.torpill.timothosaurus.models.TimothosaurusEntityModel;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class TimothosaurusEntityRenderer extends MobEntityRenderer<TimothosaurusEntity, TimothosaurusEntityModel> {
    private static final Identifier TEXTURE = Identifier.of(TimothosaurusWorld.MOD_ID, "textures/entity/timothosaurus/timothosaurus.png");

    public TimothosaurusEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new TimothosaurusEntityModel(context.getPart(ModRenderers.MODEL_TIMOTHOSAURUS_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(TimothosaurusEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(TimothosaurusEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if (livingEntity.isBaby()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
