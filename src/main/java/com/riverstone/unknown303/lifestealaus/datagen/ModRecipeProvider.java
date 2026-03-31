package com.riverstone.unknown303.lifestealaus.datagen;

import com.riverstone.unknown303.lifestealaus.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                createShaped(RecipeCategory.MISC, ModItems.HEART)
                        .pattern("GNG")
                        .pattern("OSO")
                        .pattern("DND")
                        .input('G', Blocks.GOLD_BLOCK)
                        .input('N', Items.NETHERITE_INGOT)
                        .input('O', Blocks.OBSIDIAN)
                        .input('S', Items.NETHER_STAR)
                        .input('D', Blocks.DIAMOND_BLOCK)
                        .criterion(hasItem(Items.NETHER_STAR), conditionsFromItem(Items.NETHER_STAR))
                        .offerTo(exporter);
            }
        };
    }

    @Override
    public String getName() {
        return "LifestealAUS Recipes";
    }
}
