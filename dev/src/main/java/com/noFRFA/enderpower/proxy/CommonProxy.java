package com.noFRFA.enderpower.proxy;

import com.noFRFA.enderpower.misc.EnderPowerCommand;
import com.noFRFA.enderpower.misc.events.EventsHandler;
import com.noFRFA.enderpower.misc.loader.AllMachinesTE;
import com.noFRFA.enderpower.misc.registr.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        FluidsRegister.register();
        BlocksRegister.register();
        MinecraftForge.EVENT_BUS.register(new EventsHandler());
    }

    public void init(FMLInitializationEvent event) {
        Ic2CraftMachinesRegister.addMachineRecipe();
        Ic2CraftRegister.addCraftingRecipes();
        AllMachinesTE.buildDummies();
    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new EnderPowerCommand());
    }

}