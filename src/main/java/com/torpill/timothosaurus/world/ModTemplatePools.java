package com.torpill.timothosaurus.world;

import com.mojang.datafixers.util.Pair;
import com.torpill.timothosaurus.TimothosaurusWorld;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.pool.*;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.structure.processor.StructureProcessorLists;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModTemplatePools {
    public static final RegistryKey<StructurePool> ZAZA_HOUSE = registerKey("zaza_house/house");
    public static final RegistryKey<StructurePool> ZAZA_ROADS = registerKey("zaza_house/roads");
    public static final RegistryKey<StructurePool> ZAZA_GARDENS = registerKey("zaza_house/gardens");

    public static void bootstrap(Registerable<StructurePool> context) {
        RegistryEntryLookup<StructurePool> templatePoolEntryLookup = context.getRegistryLookup(RegistryKeys.TEMPLATE_POOL);
        RegistryEntryLookup<StructureProcessorList> processorListEntryLookup = context.getRegistryLookup(RegistryKeys.PROCESSOR_LIST);
        register(context, ZAZA_HOUSE, new StructurePool(
                templatePoolEntryLookup.getOrThrow(StructurePools.EMPTY),
                List.of(new Pair<>(StructurePoolElement.ofSingle(Identifier.of(TimothosaurusWorld.MOD_ID, "zaza_house/house").toString()).apply(StructurePool.Projection.RIGID), 1))
        ));
        register(context, ZAZA_ROADS, new StructurePool(
                templatePoolEntryLookup.getOrThrow(StructurePools.EMPTY),
                Stream.of(Pair.of("extension1", 1), Pair.of("extension2", 4), Pair.of("extension3", 2), Pair.of("extension4", 2), Pair.of("road1", 5), Pair.of("road2", 2), Pair.of("road3", 2), Pair.of("road4", 1))
                        .map(weightedPath -> new Pair<>((StructurePoolElement) StructurePoolElement.ofProcessedSingle(Identifier.of(TimothosaurusWorld.MOD_ID, "zaza_house/" + weightedPath.getFirst()).toString(), processorListEntryLookup.getOrThrow(StructureProcessorLists.STREET_PLAINS)).apply(StructurePool.Projection.TERRAIN_MATCHING), weightedPath.getSecond()))
                        .collect(Collectors.toList())
        ));
        register(context, ZAZA_GARDENS, new StructurePool(
                templatePoolEntryLookup.getOrThrow(StructurePools.EMPTY),
                List.of(new Pair<>(StructurePoolElement.ofSingle(Identifier.of(TimothosaurusWorld.MOD_ID, "zaza_house/garden1").toString()).apply(StructurePool.Projection.RIGID), 1))
        ));
    }

    private static RegistryKey<StructurePool> registerKey(String id) {
        Identifier keyID = Identifier.of(TimothosaurusWorld.MOD_ID, id);
        return RegistryKey.of(RegistryKeys.TEMPLATE_POOL, keyID);
    }

    private static void register(Registerable<StructurePool> context, RegistryKey<StructurePool> key, StructurePool templatePool) {
        context.register(key, templatePool);
    }
}
