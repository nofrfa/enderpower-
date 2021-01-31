package com.noFRFA.enderpower.item;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ProjectileHolderFilled extends Item {
	public ProjectileHolderFilled(String name, int setMaxStack, CreativeTabs setTab) {
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(setTab);
		this.setMaxStackSize(setMaxStack);
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (GuiScreen.isShiftKeyDown()) {
			if (!stack.hasTagCompound()) {
				tooltip.add(I18n.format("projectileHolder_filled.information.line1") + " " + I18n.format("projectileHolder_filled.null_info"));
			} else {
				String get_nbt = stack.getTagCompound().getString("inside");
				tooltip.add(I18n.format("projectileHolder_filled.information.line1") + " " + get_nbt);
			}

		} else {
			tooltip.add(I18n.format("projectileHolder.shift"));
		}
	}
}