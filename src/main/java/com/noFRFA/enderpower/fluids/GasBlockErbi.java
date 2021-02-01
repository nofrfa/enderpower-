package com.noFRFA.enderpower.fluids;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class GasBlockErbi extends BlockFluidClassic {
	public GasBlockErbi(Fluid fluid) {
		super(fluid, Material.WATER);
		setRegistryName("gas_erbi");
	}
}
