package com.torpill.timothosaurus.renderers;

import com.torpill.timothosaurus.TimothosaurusWorld;
import com.torpill.timothosaurus.entities.ModEntities;
import com.torpill.timothosaurus.models.TimothosaurusEntityModel;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModRenderers {
    public static final EntityModelLayer MODEL_TIMOTHOSAURUS_LAYER = register("timothosaurus");

    public static EntityModelLayer register(String id) {
        Identifier modelID = Identifier.of(TimothosaurusWorld.MOD_ID, id);
        return new EntityModelLayer(modelID, "main");
    }

    public static void initialize() {
        TimothosaurusWorld.LOGGER.info("Initializing Mod Renderers");

        EntityRendererRegistry.register(ModEntities.TIMOTHOSAURUS, TimothosaurusEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(MODEL_TIMOTHOSAURUS_LAYER, TimothosaurusEntityModel::getTexturedModelData);
    }
}
