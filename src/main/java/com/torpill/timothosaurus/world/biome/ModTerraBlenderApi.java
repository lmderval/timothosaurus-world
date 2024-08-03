package com.torpill.timothosaurus.world.biome;

import com.torpill.timothosaurus.TimothosaurusWorld;
import com.torpill.timothosaurus.world.biome.surface.ModMaterialRules;
import net.minecraft.util.Identifier;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class ModTerraBlenderApi implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        Identifier overworldID = Identifier.of(TimothosaurusWorld.MOD_ID, "overworld");
        Regions.register(new ModOverworldRegion(overworldID, 4));
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, TimothosaurusWorld.MOD_ID, ModMaterialRules.makeRules());
    }
}
