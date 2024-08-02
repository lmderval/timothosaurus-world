package com.torpill.timothosaurus.entities;

import com.torpill.timothosaurus.TimothosaurusWorld;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<TimothosaurusEntity> TIMOTHOSAURUS = register(
            EntityType.Builder.create(TimothosaurusEntity::new, SpawnGroup.CREATURE)
                    .dimensions(1.0f, 2.2f)
                    .build(), "timothosaurus"
    );

    public static <E extends Entity> EntityType<E> register(EntityType<E> entityType, String id) {
        Identifier entityID = Identifier.of(TimothosaurusWorld.MOD_ID, id);
        return Registry.register(Registries.ENTITY_TYPE, entityID, entityType);
    }
    public static void initialize() {
        TimothosaurusWorld.LOGGER.info("Initializing Mod Entities");

        FabricDefaultAttributeRegistry.register(TIMOTHOSAURUS, TimothosaurusEntity.createTimothosaurusAttributes());
    }
}
