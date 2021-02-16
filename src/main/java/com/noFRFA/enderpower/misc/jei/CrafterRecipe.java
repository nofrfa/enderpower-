package com.noFRFA.enderpower.misc.jei;

import com.noFRFA.enderpower.misc.registr.FluidsRegister;
import com.noFRFA.enderpower.misc.registr.ItemsRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class CrafterRecipe {
    private static List<CrafterRecipe> recipes = new ArrayList<CrafterRecipe>(); // Лист всех рецептов.

    public static List<CrafterRecipe> getRecipes() { // Получатель всех рецептов.
        return recipes;
    }

    private final ItemStack input, output; // Компоненты крафта.
    private final FluidStack outputFluid;

    public CrafterRecipe(ItemStack input, ItemStack output, FluidStack fluid) { // Конструктор рецепта.
        this.input = input;
        this.output = output;
        this.outputFluid = fluid;
    }

    public ItemStack getInput() { // Получатель входного предмета рецепта.
        return input;
    }

    public ItemStack getOutput() { // Получатель выходного предмета рецепта.
        return output.copy();
    }

    public FluidStack getOutputFluid() {return outputFluid; }

    public static CrafterRecipe addRecipe(ItemStack input, ItemStack output, FluidStack fluidOutput) { // Метод добавления рецепта.
        CrafterRecipe recipe = new CrafterRecipe(input, output, fluidOutput); // Создаем рецепт.
        if (recipes.contains(recipe)) // Если он есть уже в рецептах - игнорим.
            return null;
        recipes.add(recipe); // Если же нет - добавляем.
        return recipe;
    }

    public static CrafterRecipe getRecipe(ItemStack is) { // Получатель рецепта через входной предмет.
        if (is == null || is.isEmpty())
            return null;
        for (CrafterRecipe recipe : recipes) // Проходим по списку всех рецептов.
            if (recipe.matchesInput(is)) // Сравниваем входные элементы.
                return recipe; // Возвращаем рецепт, если входные элементы одинаковые.
        return null;
    }

    public boolean matchesInput(ItemStack is) {
        return is.getItem() == input.getItem();
    }

    public static void initRecipes() { // Метод регистрации рецептов.
        addRecipe(is(ItemsRegistry.ITEM_projectile_holder_filled), is(ItemsRegistry.ITEM_projectile_holder), new FluidStack(FluidsRegister.GAS_ERBI, 250));
    }

    private static ItemStack is(Item item) { // Побочный метод.
        return new ItemStack(item);
    }

    private static ItemStack is(Block block) { // Побочный метод.
        return new ItemStack(block);
    }
}
