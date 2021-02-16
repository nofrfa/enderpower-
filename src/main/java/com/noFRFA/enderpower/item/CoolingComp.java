package com.noFRFA.enderpower.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CoolingComp extends Item {
    /**
     *
     * @param name name item
     * @param setMaxStack max count in slot
     * @param maxDamage max damage
     * @param setTab creative tab
     */
    public CoolingComp(String name, int setMaxStack, int maxDamage, CreativeTabs setTab) {
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setMaxDamage(maxDamage);
        this.setCreativeTab(setTab);
        this.setMaxStackSize(setMaxStack);
        this.setNoRepair();
    }

    public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return false;
    }
}