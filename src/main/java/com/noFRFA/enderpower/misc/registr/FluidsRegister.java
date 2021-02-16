package com.noFRFA.enderpower.misc.registr;

import com.noFRFA.enderpower.EnderPower;
import com.noFRFA.enderpower.fluids.GasErbi;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidsRegister {
    public static final Fluid GAS_ERBI = (GasErbi) new GasErbi("gas_erbi",
            new ResourceLocation(EnderPower.MODID, "fluid/gas_erbi_still"),
            new ResourceLocation(EnderPower.MODID, "fluid/gas_erbi_flow"))
            .setDensity(0)
            .setGaseous(true)
            .setLuminosity(5)
            .setViscosity(1500)
            .setTemperature(100)
            .setUnlocalizedName("gas_erbi");

    public static void register()
    {
        registerFluid(GAS_ERBI);
    }

    public static void registerFluid(Fluid fluid)
    {
        FluidRegistry.registerFluid(fluid);
        FluidRegistry.addBucketForFluid(fluid);
    }
}
