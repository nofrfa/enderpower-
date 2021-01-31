package com.noFRFA.enderpower.misc.loader;

import com.noFRFA.enderpower.EnderPower;
import com.noFRFA.enderpower.tile.machines.*;
import ic2.core.block.ITeBlock;
import ic2.core.block.TileEntityBlock;
import ic2.core.ref.TeBlock;
import ic2.core.util.Util;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Set;

import com.noFRFA.enderpower.misc.RarityList;

public enum AllMachinesTE implements ITeBlock {
    matter_fluids_semi(MatterSemiFluidsTE.class, 0, RarityList.MSFR),
    gas_converter(GasConverterTE.class, 1, RarityList.MSFR)
    ;

    private final Class AMTEClass;
    private final int itemMeta;
    private final EnumRarity rarity;
    private TileEntityBlock dummyTe;
    private static final AllMachinesTE[] VALUES = values();
    public static final ResourceLocation LOCATIONRES;

    static {
        LOCATIONRES = new ResourceLocation(EnderPower.MODID, "machines");
    }

    private AllMachinesTE(Class AMTEClass, int itemMeta, EnumRarity rarity) {
        this.AMTEClass = AMTEClass;
        this.itemMeta = itemMeta;
        this.rarity = rarity;
        GameRegistry.registerTileEntity(AMTEClass, "enderpower:" + this.getName());
    }

    public TileEntityBlock getDummyTe() {
        return this.dummyTe;
    }

    public ResourceLocation getIdentifier() {
        return LOCATIONRES;
    }

    @Override
    public boolean hasItem() {
        return true;
    }

    @Override
    public Class getTeClass() {
        return this.AMTEClass;
    }

    @Override
    public boolean hasActive() {
        return false;
    }

    @Override
    public Set getSupportedFacings() {
        return ic2.core.util.Util.horizontalFacings;
    }

    @Override
    public float getHardness() {
        return 3.0F;
    }

    @Override
    public float getExplosionResistance() {
        return 20.0F;
    }

    @Override
    public TeBlock.HarvestTool getHarvestTool() {
        return TeBlock.HarvestTool.Wrench;
    }

    @Override
    public TeBlock.DefaultDrop getDefaultDrop() {
        return TeBlock.DefaultDrop.Self;
    }

    @Override
    public EnumRarity getRarity() {
        return this.rarity;
    }

    @Override
    public boolean allowWrenchRotating() {
        return false;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public int getId() {
        return this.itemMeta;
    }

    public Material getMaterial() {
        return Material.ROCK;
    }

    public static void buildDummies() {
        ModContainer main = Loader.instance().activeModContainer();
        if(main != null && EnderPower.MODID.equals(main.getModId())) {
            AllMachinesTE[] var1 = VALUES;
            int var2 = var1.length;

            for(int var3 = 1; var3 < var2; ++var3) {
                AllMachinesTE block = var1[var3];
                if(block.AMTEClass != null) {
                    try {
                        block.dummyTe = (TileEntityBlock) block.AMTEClass.newInstance();
                    } catch (Exception var6) {
                        if(Util.inDev()) { var6.printStackTrace(); }
                    }
                }
            }
        } else { throw new IllegalAccessError("accessErrorLegacy ::::"); }
    }
}
