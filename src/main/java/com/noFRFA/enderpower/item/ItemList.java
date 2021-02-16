package com.noFRFA.enderpower.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemList extends Item {
    /**
     * Create more items
     * @param name setItemName
     * @param setMaxStack setMaxStackInSlot
     * @param setTab setCreativeTab
     */
    public ItemList(String name, int setMaxStack, CreativeTabs setTab) {
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(setTab);
        this.setMaxStackSize(setMaxStack);
    }
}