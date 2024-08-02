package com.torpill.timothosaurus.world;

import com.torpill.timothosaurus.TimothosaurusWorld;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.StructureTerrainAdaptation;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.heightprovider.ConstantHeightProvider;
import net.minecraft.world.gen.structure.JigsawStructure;
import net.minecraft.world.gen.structure.Structure;

public class ModStructures {
    public static final RegistryKey<Structure> ZAZA_HOUSE = registerKey("zaza_house");

    public static void bootstrap(Registerable<Structure> context) {
        RegistryEntryLookup<Biome> biomeRegistryLookup = context.getRegistryLookup(RegistryKeys.BIOME);
        RegistryEntryLookup<StructurePool> templatePoolRegistryLookup = context.getRegistryLookup(RegistryKeys.TEMPLATE_POOL);
        register(context, ZAZA_HOUSE, new JigsawStructure(
                new Structure.Config.Builder(biomeRegistryLookup.getOrThrow(BiomeTags.IS_TAIGA))
                        .terrainAdaptation(StructureTerrainAdaptation.BEARD_THIN)
                        .build(),
                templatePoolRegistryLookup.getOrThrow(ModTemplatePools.ZAZA_HOUSE),
                10,
                ConstantHeightProvider.create(YOffset.fixed(0)),
                false,
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
