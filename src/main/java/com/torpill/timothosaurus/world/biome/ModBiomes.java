package com.torpill.timothosaurus.world.biome;

import com.torpill.timothosaurus.TimothosaurusWorld;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class ModBiomes {
    public static final RegistryKey<Biome> MAPLE_FOREST = registerKey("maple_forest");

    public static void bootstrap(Registerable<Biome> context) {
        register(context, MAPLE_FOREST, MapleForestBiome.create(context));
    }

    private static RegistryKey<Biome> registerKey(String id) {
        Identifier keyID = Identifier.of(TimothosaurusWorld.MOD_ID, id);
        return RegistryKey.of(RegistryKeys.BIOME, keyID);
    }

    private static void register(Registerable<Biome> context, RegistryKey<Biome> key, Biome biome) {
        context.register(key, biome);
    }
}
