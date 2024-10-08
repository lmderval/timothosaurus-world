package com.torpill.timothosaurus.world;

import com.mojang.datafixers.util.Pair;
import com.torpill.timothosaurus.TimothosaurusWorld;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
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
    public static final RegistryKey<StructurePool> ZAZA_DECORATIONS = registerKey("zaza_house/decorations");

    public static final RegistryKey<StructurePool> SEX_TEMPLE = registerKey("sex_temple/temple");
    public static final RegistryKey<StructurePool> SEX_UNDERGROUNDS = registerKey("sex_temple/undergrounds");
    public static final RegistryKey<StructurePool> SEX_ROOMS = registerKey("sex_temple/rooms");
    public static final RegistryKey<StructurePool> SEX_CORRIDORS = registerKey("sex_temple/corridors");
    public static final RegistryKey<StructurePool> SEX_STRAIGHT_CORRIDORS = registerKey("sex_temple/straight_corridors");

    public static void bootstrap(Registerable<StructurePool> context) {
        RegistryEntryLookup<StructurePool> templatePoolEntryLookup = context.getRegistryLookup(RegistryKeys.TEMPLATE_POOL);
        RegistryEntryLookup<StructureProcessorList> processorListEntryLookup = context.getRegistryLookup(RegistryKeys.PROCESSOR_LIST);
        RegistryEntry<StructurePool> empty = templatePoolEntryLookup.getOrThrow(StructurePools.EMPTY);
        StructurePoolElement emptyElement = StructurePoolElement.ofEmpty().apply(StructurePool.Projection.RIGID);
        register(context, ZAZA_HOUSE, new StructurePool(
                empty,
                List.of(Pair.of(StructurePoolElement.ofSingle(Identifier.of(TimothosaurusWorld.MOD_ID, "zaza_house/house").toString()).apply(StructurePool.Projection.RIGID), 1))
        ));
        register(context, ZAZA_ROADS, new StructurePool(
                empty,
                Stream.of(Pair.of("extension1", 10), Pair.of("extension2", 5), Pair.of("extension3", 5), Pair.of("extension4", 1), Pair.of("road1", 10), Pair.of("road2", 5), Pair.of("road4", 3), Pair.of("road5", 4))
                        .map(weightedPath -> Pair.of(weightedPath.getFirst() == null ? emptyElement : StructurePoolElement.ofProcessedSingle(Identifier.of(TimothosaurusWorld.MOD_ID, "zaza_house/" + weightedPath.getFirst()).toString(), processorListEntryLookup.getOrThrow(StructureProcessorLists.STREET_PLAINS)).apply(StructurePool.Projection.TERRAIN_MATCHING), weightedPath.getSecond()))
                        .collect(Collectors.toList())
        ));
        register(context, ZAZA_GARDENS, new StructurePool(
                empty,
                Stream.of(Pair.of("garden1", 9), Pair.of("garden2", 1))
                        .map(weightedPath -> Pair.of(weightedPath.getFirst() == null ? emptyElement : StructurePoolElement.ofSingle(Identifier.of(TimothosaurusWorld.MOD_ID, "zaza_house/" + weightedPath.getFirst()).toString()).apply(StructurePool.Projection.RIGID), weightedPath.getSecond()))
                        .collect(Collectors.toList())
        ));
        register(context, ZAZA_DECORATIONS, new StructurePool(
                empty,
                Stream.of(Pair.of(null, 9), Pair.of("decoration1", 1))
                        .map(weightedPath -> Pair.of(weightedPath.getFirst() == null ? emptyElement : StructurePoolElement.ofProcessedSingle(Identifier.of(TimothosaurusWorld.MOD_ID, "zaza_house/" + weightedPath.getFirst()).toString(), processorListEntryLookup.getOrThrow(StructureProcessorLists.STREET_PLAINS)).apply(StructurePool.Projection.RIGID), weightedPath.getSecond()))
                        .collect(Collectors.toList())
        ));

        register(context, SEX_TEMPLE, new StructurePool(
                empty,
                List.of(Pair.of(StructurePoolElement.ofSingle(Identifier.of(TimothosaurusWorld.MOD_ID, "sex_temple/temple").toString()).apply(StructurePool.Projection.RIGID), 1))
        ));
        register(context, SEX_UNDERGROUNDS, new StructurePool(
                empty,
                Stream.of(Pair.of("underground1", 1))
                        .map(weightedPath -> Pair.of(weightedPath.getFirst() == null ? emptyElement : StructurePoolElement.ofSingle(Identifier.of(TimothosaurusWorld.MOD_ID, "sex_temple/" + weightedPath.getFirst()).toString()).apply(StructurePool.Projection.RIGID), weightedPath.getSecond()))
                        .collect(Collectors.toList())
        ));
        register(context, SEX_ROOMS, new StructurePool(
                empty,
                Stream.of(Pair.of("room1", 3), Pair.of("room2", 1), Pair.of("room3", 1))
                        .map(weightedPath -> Pair.of(weightedPath.getFirst() == null ? emptyElement : StructurePoolElement.ofSingle(Identifier.of(TimothosaurusWorld.MOD_ID, "sex_temple/" + weightedPath.getFirst()).toString()).apply(StructurePool.Projection.RIGID), weightedPath.getSecond()))
                        .collect(Collectors.toList())
        ));
        RegistryEntry<StructurePool> straightCorridors = register(context, SEX_STRAIGHT_CORRIDORS, new StructurePool(
                empty,
                Stream.of(Pair.of("corridor1", 1), Pair.of("corridor2", 3))
                        .map(weightedPath -> Pair.of(weightedPath.getFirst() == null ? emptyElement : StructurePoolElement.ofSingle(Identifier.of(TimothosaurusWorld.MOD_ID, "sex_temple/" + weightedPath.getFirst()).toString()).apply(StructurePool.Projection.RIGID), weightedPath.getSecond()))
                        .collect(Collectors.toList())
        ));
        register(context, SEX_CORRIDORS, new StructurePool(
                straightCorridors,
                Stream.of(Pair.of("corridor1", 2), Pair.of("corridor2", 4), Pair.of("corridor3", 3), Pair.of("corridor4", 4), Pair.of("corridor5", 4))
                        .map(weightedPath -> Pair.of(weightedPath.getFirst() == null ? emptyElement : StructurePoolElement.ofSingle(Identifier.of(TimothosaurusWorld.MOD_ID, "sex_temple/" + weightedPath.getFirst()).toString()).apply(StructurePool.Projection.RIGID), weightedPath.getSecond()))
                        .collect(Collectors.toList())
        ));
    }

    private static RegistryKey<StructurePool> registerKey(String id) {
        Identifier keyID = Identifier.of(TimothosaurusWorld.MOD_ID, id);
        return RegistryKey.of(RegistryKeys.TEMPLATE_POOL, keyID);
    }

    private static RegistryEntry.Reference<StructurePool> register(Registerable<StructurePool> context, RegistryKey<StructurePool> key, StructurePool templatePool) {
        return context.register(key, templatePool);
    }
}
