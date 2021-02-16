package com.noFRFA.enderpower.misc.tabs;

import com.noFRFA.enderpower.misc.registr.ItemsRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class TabsList {
    /* [1]
    Добавляем
    public static final CreativeTabs НАЗВАНИЕ = new CreativeTabs("name") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ТЕКСТУРА_ИЛИ_МОДЕЛЬ_ИКОНКИ_ВКЛАДКИ);
        }
    };
    Пример:
    public static final CreativeTabs ExampleModTab1 = new CreativeTabs("EMT1") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ТЕКСТУРА_ИЛИ_МОДЕЛЬ_ИКОНКИ_ВКЛАДКИ);
        }
    };
    */ //TODO: изменить label
    public static final CreativeTabs EXtabs = new CreativeTabs("examplemod") {

        public boolean items = true;
        public boolean blocks = true;
        public boolean tools = false;

        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ItemsRegistry.ITEM_Ultimate_Circuit);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void displayAllRelevantItems(@Nonnull NonNullList<ItemStack> itemStacks)
        {
            if (blocks) for (Item item : Item.REGISTRY) if (item instanceof ItemBlock)                            item.getSubItems(this, itemStacks);
            if (tools)  for (Item item : Item.REGISTRY) if (item.isDamageable())                                  item.getSubItems(this, itemStacks);
            if (items)  for (Item item : Item.REGISTRY) if (!(item instanceof ItemBlock))                         item.getSubItems(this, itemStacks);
        }
    };
}
