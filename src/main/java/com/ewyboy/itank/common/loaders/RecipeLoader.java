package com.ewyboy.itank.common.loaders;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by EwyBoy
 **/
public class RecipeLoader {

    //TODO Add Oredictionary support for glass
    public static void registerRecipes() {
        GameRegistry.addRecipe(new ItemStack(BlockLoader.tank),
                "III", "GCG", "III",
                'I', Items.IRON_INGOT,
                'G', Blocks.GLASS,
                'C', Items.CAULDRON
        );
    }
}
