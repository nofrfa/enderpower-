package com.noFRFA.enderpower.item;

import com.noFRFA.enderpower.EnderPower;
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

public class MachinesUpgradeVolecy extends Item {
	public MachinesUpgradeVolecy(String name, int setMaxStack, CreativeTabs setTab) {
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(setTab);
		this.setMaxStackSize(setMaxStack);
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		int deBoostTime = stack.getCount() * EnderPower.Upgrader_volecy;
		int boostMilibackets = stack.getCount() * EnderPower.Upgrader_volecy_boost;

		tooltip.add(I18n.format("upgrade.volecy") + " " + deBoostTime + " " + I18n.format("more.sec"));
		tooltip.add(I18n.format("upgrade.volecy2") + " " + boostMilibackets + " " + I18n.format("more.mb_out"));
	}
}