package com.torpill.timothosaurus.datagen;

import com.torpill.timothosaurus.TimothosaurusWorld;
import com.torpill.timothosaurus.blocks.MapleLogBlock;
import com.torpill.timothosaurus.blocks.ModBlocks;
import com.torpill.timothosaurus.blocks.ZazaCropBlock;
import com.torpill.timothosaurus.items.ModItems;
import com.torpill.timothosaurus.items.model.DisplayModel;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
    public static final TextureKey SAP = TextureKey.of("sap", TextureKey.ALL);

    public static final Model CUBE_COLUMN_SAP = block("cube_column", "_sap", TextureKey.END, TextureKey.SIDE);
    public static final Model CUBE_COLUMN_HORIZONTAL_SAP = block("cube_column_horizontal", "_horizontal_sap", TextureKey.END, TextureKey.SIDE);

    public static final Model SPAWN_EGG = item("template_spawn_egg");
    public static final Model TREETAP = item("generated", TextureKey.LAYER0)
            .getOrCreateTransformation(DisplayModel.ModelPosition.THIRD_PERSON_RIGHT_HAND)
            .rotation(0.0f, -90.0f, 0.0f)
            .translation(0.0f, 1.0f, 0.0f)
            .scale(0.55f, 0.55f, 0.55f)
            .submit()
            .getOrCreateTransformation(DisplayModel.ModelPosition.THIRD_PERSON_LEFT_HAND)
            .rotation(0.0f, 90.0f, 0.0f)
            .translation(0.0f, 1.0f, 0.0f)
            .scale(0.55f, 0.55f, 0.55f)
            .submit()
            .getOrCreateTransformation(DisplayModel.ModelPosition.FIRST_PERSON_LEFT_HAND)
            .rotation(0.0f, 90.0f, -25.0f)
            .translation(1.13f, 3.2f, 1.13f)
            .scale(0.68f, 0.68f, 0.68f)
            .submit();

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    private static Model block(String parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(Identifier.of("block/" + parent)), Optional.empty(), requiredTextureKeys);
    }

    private static Model block(String parent, String variant, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(Identifier.of("block/" + parent)), Optional.of(variant), requiredTextureKeys);
    }

    private static DisplayModel item(String parent, TextureKey... requiredTextureKeys) {
            return new DisplayModel(Optional.of(Identifier.of("item/" + parent)), Optional.empty(), requiredTextureKeys);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        registerMapleLog(blockStateModelGenerator)
                .log(ModBlocks.MAPLE_LOG)
                .wood(ModBlocks.MAPLE_WOOD);
        blockStateModelGenerator.registerLog(ModBlocks.STRIPPED_MAPLE_LOG)
                .log(ModBlocks.STRIPPED_MAPLE_LOG)
                .wood(ModBlocks.STRIPPED_MAPLE_WOOD);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.MAPLE_PLANKS);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.MAPLE_LEAVES);
        blockStateModelGenerator.registerTintableCross(ModBlocks.MAPLE_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerCrop(ModBlocks.ZAZA_CROP, ZazaCropBlock.AGE, 0, 1, 2, 3, 4, 5);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        // Generated
        itemModelGenerator.register(ModItems.SCHOKO_BONS, Models.GENERATED);
        itemModelGenerator.register(ModItems.ZAZA_POWDER, Models.GENERATED);
        itemModelGenerator.register(ModItems.MAPLE_SAP, Models.GENERATED);
        itemModelGenerator.register(ModItems.MAPLE_SYRUP, Models.GENERATED);

        // Handheld
        itemModelGenerator.register(ModItems.TREETAP, TREETAP);

        // Spawn eggs
        itemModelGenerator.register(ModItems.TIMOTHOSAURUS_SPAWN_EGG, SPAWN_EGG);
    }

    private static MapleLogTexturePool registerMapleLog(BlockStateModelGenerator blockStateModelGenerator) {
        return new MapleLogTexturePool(sideAndEndForTopAndSap(ModBlocks.MAPLE_LOG), blockStateModelGenerator);
    }

    public static TextureMap sideAndEndForTopAndSap(Block block) {
        return new TextureMap()
                .put(TextureKey.SIDE, TextureMap.getId(block))
                .put(TextureKey.END, TextureMap.getSubId(block, "_top"))
                .put(SAP, TextureMap.getSubId(block, "_sap"))
                .put(TextureKey.PARTICLE, TextureMap.getId(block));
    }

    public static BlockStateSupplier createMapleLogBlockState(Block block, Identifier verticalModelID, Identifier horizontalModelID, Identifier verticalSapModelID, Identifier horizontalSapModelID) {
        return VariantsBlockStateSupplier.create(block)
                .coordinate(BlockStateVariantMap.create(Properties.AXIS, MapleLogBlock.SAP)
                        .register(Direction.Axis.Y, false, BlockStateVariant.create()
                                .put(VariantSettings.MODEL, verticalModelID))
                        .register(Direction.Axis.Z, false, BlockStateVariant.create()
                                .put(VariantSettings.MODEL, horizontalModelID)
                                .put(VariantSettings.X, VariantSettings.Rotation.R90))
                        .register(Direction.Axis.X, false, BlockStateVariant.create()
                                .put(VariantSettings.MODEL, horizontalModelID)
                                .put(VariantSettings.X, VariantSettings.Rotation.R90)
                                .put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.Axis.Y, true, BlockStateVariant.create()
                                .put(VariantSettings.MODEL, verticalSapModelID))
                        .register(Direction.Axis.Z, true, BlockStateVariant.create()
                                .put(VariantSettings.MODEL, horizontalSapModelID)
                                .put(VariantSettings.X, VariantSettings.Rotation.R90))
                        .register(Direction.Axis.X, true, BlockStateVariant.create()
                                .put(VariantSettings.MODEL, horizontalSapModelID)
                                .put(VariantSettings.X, VariantSettings.Rotation.R90)
                                .put(VariantSettings.Y, VariantSettings.Rotation.R90))
                );
    }

    public static class MapleLogTexturePool {
        private final TextureMap textures;
        private final BlockStateModelGenerator blockStateModelGenerator;

        public MapleLogTexturePool(final TextureMap textures, final BlockStateModelGenerator blockStateModelGenerator) {
            this.textures = textures;
            this.blockStateModelGenerator = blockStateModelGenerator;
        }

        public MapleLogTexturePool wood(Block woodBlock) {
            TextureMap textureMap = textures.copyAndAdd(TextureKey.END, textures.getTexture(TextureKey.SIDE));
            Identifier identifier = Models.CUBE_COLUMN.upload(woodBlock, textureMap, blockStateModelGenerator.modelCollector);
            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(woodBlock, identifier));
            return this;
        }

        public MapleLogTexturePool stem(Block stemBlock) {
            Identifier identifier = Models.CUBE_COLUMN.upload(stemBlock, textures, blockStateModelGenerator.modelCollector);
            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(stemBlock, identifier));
            return this;
        }

        public MapleLogTexturePool log(Block logBlock) {
            TextureMap textureMap = textures.copyAndAdd(TextureKey.SIDE, textures.getTexture(SAP));
            Identifier verticalModelID = Models.CUBE_COLUMN.upload(logBlock, textures, blockStateModelGenerator.modelCollector);
            Identifier horizontalModelID = Models.CUBE_COLUMN_HORIZONTAL.upload(logBlock, textures, blockStateModelGenerator.modelCollector);
            Identifier verticalSapModelID = CUBE_COLUMN_SAP.upload(logBlock, textureMap, blockStateModelGenerator.modelCollector);
            Identifier horizontalSapModelID = CUBE_COLUMN_HORIZONTAL_SAP.upload(logBlock, textureMap, blockStateModelGenerator.modelCollector);
            blockStateModelGenerator.blockStateCollector.accept(createMapleLogBlockState(logBlock, verticalModelID, horizontalModelID, verticalSapModelID, horizontalSapModelID));
            return this;
        }

        public MapleLogTexturePool uvLockedLog(Block logBlock) {
            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createUvLockedColumnBlockState(logBlock, textures, blockStateModelGenerator.modelCollector));
            return this;
        }
    }
}
