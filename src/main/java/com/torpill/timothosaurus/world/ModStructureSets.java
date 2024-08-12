package com.torpill.timothosaurus.world;

import com.mojang.datafixers.util.Pair;
import com.torpill.timothosaurus.TimothosaurusWorld;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.StructureSet;
import net.minecraft.structure.StructureSetKeys;
import net.minecraft.structure.StructureSets;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.gen.chunk.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.gen.chunk.placement.SpreadType;
import net.minecraft.world.gen.chunk.placement.StructurePlacement;
import net.minecraft.world.gen.structure.Structure;

import java.util.List;
import java.util.Optional;

public class ModStructureSets {
    public static final RegistryKey<StructureSet> ZAZA_HOUSE = registerKey("zaza_house");
    public static final RegistryKey<StructureSet> SEX_TEMPLE = registerKey("sex_temple");

    public static void bootstrap(Registerable<StructureSet> context) {
        RegistryEntryLookup<Structure> structureEntryLookup = context.getRegistryLookup(RegistryKeys.STRUCTURE);
        RegistryEntryLookup<StructureSet> structureSetEntryLookup = context.getRegistryLookup(RegistryKeys.STRUCTURE_SET);
        register(context, ZAZA_HOUSE,
                List.of(StructureSet.createEntry(structureEntryLookup.getOrThrow(ModStructures.ZAZA_HOUSE))),
                new RandomSpreadStructurePlacement(Vec3i.ZERO, StructurePlacement.FrequencyReductionMethod.DEFAULT, 1.0f, 30875816,
                        Optional.of(new StructurePlacement.ExclusionZone(structureSetEntryLookup.getOrThrow(StructureSetKeys.VILLAGES), 8)), 20, 2, SpreadType.LINEAR)
        );
        register(context, SEX_TEMPLE,
                List.of(StructureSet.createEntry(structureEntryLookup.getOrThrow(ModStructures.SEX_TEMPLE))),
                new RandomSpreadStructurePlacement(Vec3i.ZERO, StructurePlacement.FrequencyReductionMethod.DEFAULT, 0.2f, 30875817,
                        Optional.of(new StructurePlacement.ExclusionZone(structureSetEntryLookup.getOrThrow(StructureSetKeys.VILLAGES), 8)), 20, 2, SpreadType.LINEAR)
        );
    }

    private static RegistryKey<StructureSet> registerKey(String id) {
        Identifier keyID = Identifier.of(TimothosaurusWorld.MOD_ID, id);
        return RegistryKey.of(RegistryKeys.STRUCTURE_SET, keyID);
    }

    private static void register(Registerable<StructureSet> context, RegistryKey<StructureSet> key, List<StructureSet.WeightedEntry> weightedEntries, StructurePlacement structurePlacement) {
        context.register(key, new StructureSet(weightedEntries, structurePlacement));
    }
}
