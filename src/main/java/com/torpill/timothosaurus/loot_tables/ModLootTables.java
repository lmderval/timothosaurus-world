package com.torpill.timothosaurus.loot_tables;

import com.torpill.timothosaurus.TimothosaurusWorld;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModLootTables {
    public static final RegistryKey<LootTable> ZAZA_HOUSE_ATTIC = registerKey("zaza_house/attic");
    public static final RegistryKey<LootTable> ZAZA_HOUSE_ROOM = registerKey("zaza_house/room");

    private static RegistryKey<LootTable> registerKey(String id) {
        Identifier keyID = Identifier.of(TimothosaurusWorld.MOD_ID, id);
        return RegistryKey.of(RegistryKeys.LOOT_TABLE, keyID);
    }

    public static void initialize() {
        TimothosaurusWorld.LOGGER.info("Initializing Mod Loot Tables");
    }
}
