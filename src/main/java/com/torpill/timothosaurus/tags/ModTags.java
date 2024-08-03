package com.torpill.timothosaurus.tags;

import com.torpill.timothosaurus.TimothosaurusWorld;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class ModTags {
    public static final TagKey<Biome> HAS_ZAZA_HOUSE = create(RegistryKeys.BIOME, "has_zaza_house");

    public static void initialize() {
        TimothosaurusWorld.LOGGER.info("Initializing Mod Tags");
    }

    private static <T> TagKey<T> create(RegistryKey<? extends Registry<T>> registryKey, String id) {
        Identifier tagID = Identifier.of(TimothosaurusWorld.MOD_ID, id);
        return TagKey.of(registryKey, tagID);
    }
}
