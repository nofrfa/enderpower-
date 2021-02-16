package com.noFRFA.enderpower.misc.jei;

import com.noFRFA.enderpower.misc.registr.AllMachinesTE;
import com.noFRFA.enderpower.tile.machines.GasConverterTE;
import com.noFRFA.enderpower.tile.machines.guigasconvert.GuiGas;
import ic2.core.block.TileEntityBlock;
import ic2.jeiIntegration.recipe.machine.IORecipeCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JEIMain implements IModPlugin {
    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) { // Регистрация категории, которая будет показываться в MC.
        registry.addRecipeCategories(new CrafterRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void register(IModRegistry registry) {

        registry.addRecipeClickArea(GuiGas.class, 58, 34, 21, 5, CrafterRecipeCategory.UID);

        registry.addRecipes(CrafterRecipe.getRecipes(), CrafterRecipeCategory.UID); // Регистрация рецептов для Вашего UID.
        registry.handleRecipes(CrafterRecipe.class, CrafterRecipeWrapper::new, CrafterRecipeCategory.UID); // Регистрация рецептов из листа рецептов.
        registry.addRecipeCatalyst(AllMachinesTE.gas_converter, CrafterRecipeCategory.UID);

        IIngredientBlacklist blackList = registry.getJeiHelpers().getIngredientBlacklist(); // Блек лист для JEI, используемый для скрытия предметов.
        blackList.addIngredientToBlacklist(new ItemStack(Blocks.DIRT)); // Скрытие блока земли из JEI.
    }
}
