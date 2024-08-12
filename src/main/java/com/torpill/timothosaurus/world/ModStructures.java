package com.torpill.timothosaurus.world;

import com.torpill.timothosaurus.TimothosaurusWorld;
import com.torpill.timothosaurus.tags.ModTags;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.StructureTerrainAdaptation;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.heightprovider.ConstantHeightProvider;
import net.minecraft.world.gen.structure.JigsawStructure;
import net.minecraft.world.gen.structure.Structure;

public class ModStructures {
    public static final RegistryKey<Structure> ZAZA_HOUSE = registerKey("zaza_house");
    public static final RegistryKey<Structure> SEX_TEMPLE = registerKey("sex_temple");

    public static void bootstrap(Registerable<Structure> context) {
        RegistryEntryLookup<Biome> biomeRegistryLookup = context.getRegistryLookup(RegistryKeys.BIOME);
        RegistryEntryLookup<StructurePool> templatePoolRegistryLookup = context.getRegistryLookup(RegistryKeys.TEMPLATE_POOL);
        register(context, ZAZA_HOUSE, new JigsawStructure(
                new Structure.Config.Builder(biomeRegistryLookup.getOrThrow(ModTags.HAS_ZAZA_HOUSE))
                        .terrainAdaptation(StructureTerrainAdaptation.BEARD_BOX)
                        .build(),
                templatePoolRegistryLookup.getOrThrow(ModTemplatePools.ZAZA_HOUSE),
                20,
                ConstantHeightProvider.create(YOffset.fixed(0)),
                false,
                Heightmap.Type.WORLD_SURFACE_WG
        ));
        register(context, SEX_TEMPLE, new JigsawStructure(
                new Structure.Config.Builder(biomeRegistryLookup.getOrThrow(ModTags.HAS_SEX_TEMPLE))
                        .terrainAdaptation(StructureTerrainAdaptation.BEARD_BOX)
                        .build(),
                templatePoolRegistryLookup.getOrThrow(ModTemplatePools.SEX_TEMPLE),
                9,
                ConstantHeightProvider.create(YOffset.fixed(0)),
                true,
                Heightmap.Type.WORLD_SURFACE_WG
        ));
    }

    private static RegistryKey<Structure> registerKey(String id) {
        Identifier keyID = Identifier.of(TimothosaurusWorld.MOD_ID, id);
        return RegistryKey.of(RegistryKeys.STRUCTURE, keyID);
    }

    private static void register(Registerable<Structure> context, RegistryKey<Structure> key, Structure structure) {
        context.register(key, structure);
    }
}
