package com.torpill.timothosaurus.models;

import com.torpill.timothosaurus.animations.TimothosaurusAnimations;
import com.torpill.timothosaurus.entities.TimothosaurusEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class TimothosaurusEntityModel extends SinglePartEntityModel<TimothosaurusEntity> {
    private final ModelPart root;
    private final ModelPart right_leg;
    private final ModelPart left_leg;
    private final ModelPart right_arm;
    private final ModelPart left_arm;
    private final ModelPart head;
    private final ModelPart body;

    public TimothosaurusEntityModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.right_leg = body.getChild("right_leg");
        this.left_leg = body.getChild("left_leg");
        this.right_arm = body.getChild("right_arm");
        this.left_arm = body.getChild("left_arm");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData head = modelPartData.addChild("head",
                ModelPartBuilder.create()
                        .uv(0, 20)
                        .cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F))
                        .uv(0, 53)
                        .cuboid(-3.5F, -6.5F, -4.25F, 7.0F, 4.0F, 1.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, -3.0F, 0.0F)
        );
        ModelPartData body = modelPartData.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(-5.0F, -13.0F, -3.0F, 10.0F, 14.0F, 6.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 10.0F, 0.0F)
        );
        ModelPartData right_leg = body.addChild("right_leg",
                ModelPartBuilder.create()
                        .uv(32, 18)
                        .cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, new Dilation(0.0F)),
                ModelTransform.pivot(-2.0F, 0.0F, 0.0F)
        );
        ModelPartData left_leg = body.addChild("left_leg",
                ModelPartBuilder.create()
                        .uv(32, 0)
                        .cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, new Dilation(0.0F)),
                ModelTransform.pivot(2.0F, 0.0F, 0.0F)
        );
        ModelPartData right_arm = body.addChild("right_arm",
                ModelPartBuilder.create()
                        .uv(14, 36)
                        .cuboid(-3.0F, -1.0F, -2.0F, 3.0F, 13.0F, 4.0F, new Dilation(0.0F)),
                ModelTransform.pivot(-5.0F, -12.0F, 0.0F));
        ModelPartData left_arm = body.addChild("left_arm",
                ModelPartBuilder.create()
                        .uv(0, 36)
                        .cuboid(0.0F, -1.0F, -2.0F, 3.0F, 13.0F, 4.0F, new Dilation(0.0F)),
                ModelTransform.pivot(5.0F, -12.0F, 0.0F)
        );
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(TimothosaurusEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        getPart().traverse().forEach(ModelPart::resetTransform);
        setHeadAngles(netHeadYaw, headPitch);

        animateMovement(TimothosaurusAnimations.WALKING, limbSwing, limbSwingAmount, 2.0f, 2.5f);
        updateAnimation(entity.attackingAnimationState, TimothosaurusAnimations.ATTACKING, ageInTicks, 1.0f);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        float trueHeadYaw = MathHelper.clamp(headYaw, -30.0f, 30.0f);
        float trueHeadPitch = MathHelper.clamp(headPitch, -25.0f, 45.0f);
        float remainingHeadYaw = headYaw - trueHeadYaw;

        head.yaw = trueHeadYaw * 0.017453292f;
        head.pitch = trueHeadPitch * 0.017453292f;
        root.yaw += remainingHeadYaw * 0.017453292f;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        getPart().render(matrices, vertices, light, overlay, color);
    }

    public ModelPart getPart() {
        return root;
    }
}
