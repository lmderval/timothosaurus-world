package com.torpill.timothosaurus.datagen;

import com.torpill.timothosaurus.blocks.ModBlocks;
import com.torpill.timothosaurus.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.mixin.recipe.ingredient.IngredientMixin;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.book.CookingRecipeCategory;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import javax.sound.midi.MidiChannel;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    private static final List<ItemConvertible> CONVERTIBLE_TO_MAPLE_PLANKS = List.of(
            ModBlocks.MAPLE_LOG,
            ModBlocks.MAPLE_WOOD,
            ModBlocks.STRIPPED_MAPLE_LOG,
            ModBlocks.STRIPPED_MAPLE_WOOD
    );
    private static final List<ItemConvertible> CONVERTIBLE_TO_MAPLE_WOOD = List.of(
            ModBlocks.MAPLE_LOG,
            ModBlocks.MAPLE_WOOD
    );
    private static final List<ItemConvertible> CONVERTIBLE_TO_STRIPPED_MAPLE_WOOD = List.of(
            ModBlocks.STRIPPED_MAPLE_LOG,
            ModBlocks.STRIPPED_MAPLE_WOOD
    );

    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.SCHOKO_BONS, 16)
                .input(Items.COCOA_BEANS, 4)
                .input(Items.MILK_BUCKET)
                .criterion(hasItem(Items.COCOA_BEANS), conditionsFromItem(Items.COCOA_BEANS))
                .criterion(hasItem(Items.MILK_BUCKET), conditionsFromItem(Items.MILK_BUCKET))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.SCHOKO_BONS)));

        CONVERTIBLE_TO_MAPLE_PLANKS.forEach(maple -> ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.MAPLE_PLANKS, 4)
                .input(maple)
                .group("planks")
                .criterion(hasItem(maple), conditionsFromItem(maple))
                .offerTo(exporter, Identifier.of(convertBetween(ModBlocks.MAPLE_PLANKS, maple)))
        );

        CONVERTIBLE_TO_MAPLE_WOOD.forEach(maple -> ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.MAPLE_WOOD, 3)
                .input(maple, 4)
                .group("wood")
                .criterion(hasItem(maple), conditionsFromItem(maple))
                .offerTo(exporter, Identifier.of(convertBetween(ModBlocks.MAPLE_WOOD, maple)))
        );

        CONVERTIBLE_TO_STRIPPED_MAPLE_WOOD.forEach(maple -> ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.STRIPPED_MAPLE_WOOD, 3)
                .input(maple, 4)
                .group("wood")
                .criterion(hasItem(maple), conditionsFromItem(maple))
                .offerTo(exporter, Identifier.of(convertBetween(ModBlocks.STRIPPED_MAPLE_WOOD, maple)))
        );

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.TREETAP)
                .pattern(" X ")
                .pattern("XXX")
                .pattern("  X")
                .input('X', ItemTags.PLANKS)
                .criterion("has_planks", conditionsFromTag(ItemTags.PLANKS))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.TREETAP)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.ZAZA_POWDER)
                .input(ModItems.ZAZA_LEAF)
                .input(Items.SUGAR)
                .criterion(hasItem(ModItems.ZAZA_LEAF), conditionsFromItem(ModItems.ZAZA_LEAF))
                .criterion(hasItem(Items.SUGAR), conditionsFromItem(Items.SUGAR))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.ZAZA_POWDER)));

        offerSmelting(exporter, List.of(ModItems.MAPLE_SAP), RecipeCategory.MISC, ModItems.MAPLE_SYRUP, 1.0f, 200, "maple_syrup");
    }
}
