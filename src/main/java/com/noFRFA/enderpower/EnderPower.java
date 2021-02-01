package com.noFRFA.enderpower;

import com.noFRFA.enderpower.misc.loader.AllMachinesTE;
import com.noFRFA.enderpower.proxy.CommonProxy;
import ic2.api.event.TeBlockFinalCallEvent;
import ic2.core.block.BlockTileEntity;
import ic2.core.block.TeBlockRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/* ОБНОВЛЕНИЕ ОТ 27.12.2020 (подробности в readme.txt)
> Добавление: Предметов | main/java/com/example/examplemod/misc/registr.ItemsRegistry      | +Инструкция
>             Еды | main/java/com/example/examplemod/misc/registr.FoodRegistry             | +Инструкция
>             Брони | main/java/com/example/examplemod/misc/registr.ArmorRegistry          | +Инструкция
>             Инструментов | main/java/com/example/examplemod/misc/registr.ToolsRegistry   | +Инструкция
>             Материалов | main/java/com/example/examplemod/misc.ItemCustomMaterial        | +Примеры
>             Блоков | main/java/com/example/examplemod/misc/registr.BlocksRegister        | +Инструкция
>             Вкладки в креативе | main/java/com/example/examplemod/misc/tabs              | +Примеры
>             Событий | main/java/com/example/examplemod/misc/events                       | Частично есть примеры
>             Крафтов | main/resources/assets/examplemod/recipes                           | Теперь достаточно добавить туда файл name_ВАШЕГО_ПРЕДМЕТА.json и рецепт в нём :). Если файл не будет добавлен - то и крафта у предмета не будет
>
> Прокси: main/java/com/example/examplemod/proxy                                           | Common+Client Proxy
> Локализация: main/resources/assets/examplemod/lang
> Информация об моде: main/java/assets/examplemod/mcmod.info
*/
@Mod(
		modid = EnderPower.MODID,
		name = EnderPower.NAME,
		version = EnderPower.VERSION,
		acceptedMinecraftVersions = EnderPower.STABLEVERSION, // Версия(и) майнкрафта на которых будет работать мод
		dependencies = "required-after:ic2;"
)
public class EnderPower {
	public static final String MODID = "enderpower";
	public static final String NAME = "Ender Power";
	public static final String VERSION = "1.0.0";
	public static final String STABLEVERSION = "1.12.2";

	public static final boolean LevelAccess = false; // false-user, true-root

	public static final float Upgrader_speed = 3.125F;
	public static final float Upgrader_speed_boost = 0.15625F;
	public static final int Upgrader_speed_energy = 125;
	public static final int Upgrader_volecy = 10;
	public static final int Upgrader_volecy_time = 200;
	public static final int Upgrader_volecy_boost = 50;

	@SidedProxy(clientSide = "com.noFRFA.enderpower.proxy.ClientProxy", serverSide = "com.noFRFA.enderpower.proxy.CommonProxy")
	public static CommonProxy proxy;

	@Mod.Instance
	public static EnderPower instance;

	public static BlockTileEntity machines;

	public static ResourceLocation getIdentifier(final String name) {
		return new ResourceLocation(EnderPower.MODID, name);
	}

	@EventHandler
	public void start(FMLConstructionEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void register(TeBlockFinalCallEvent event) {
		TeBlockRegistry.addAll(AllMachinesTE.class, AllMachinesTE.LOCATIONRES);
		TeBlockRegistry.setDefaultMaterial(AllMachinesTE.LOCATIONRES, Material.ROCK);
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		proxy.serverStarting(event);
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
		machines = TeBlockRegistry.get(AllMachinesTE.LOCATIONRES);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
}