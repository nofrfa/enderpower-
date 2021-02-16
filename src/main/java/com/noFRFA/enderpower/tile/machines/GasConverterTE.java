package com.noFRFA.enderpower.tile.machines;

import com.noFRFA.enderpower.misc.ConfigHalf;
import com.noFRFA.enderpower.misc.registr.FluidsRegister;
import com.noFRFA.enderpower.misc.registr.ItemsRegistry;
import com.noFRFA.enderpower.tile.machines.guigasconvert.ContainerGas;
import com.noFRFA.enderpower.tile.machines.guigasconvert.GuiGas;
import ic2.api.tile.IWrenchable;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.comp.Fluids;
import ic2.core.block.comp.Redstone;
import ic2.core.block.comp.TileEntityComponent;
import ic2.core.block.invslot.*;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.profile.NotClassic;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import java.util.*;

//TODO: сделать показ крафта в JEI
//TODO: нарисовать текстуру блоку
@NotClassic
public class GasConverterTE extends TileEntityElectricMachine implements IHasGui, IWrenchable {

    protected Redstone redstone;
    public final InvSlotConsumableItemStack inputContainer;
    public final InvSlotOutput outputFluidSlot;
    public final InvSlotConsumableLiquid inputFluidSlot;
    public final InvSlotConsumableItemStack customUpgradeSlot1;
    public final InvSlotConsumableItemStack customUpgradeSlot2;
    public final InvSlotOutput outputContainer;
    public final FluidTank fluidTank;
    protected final Fluids fluids;
    public int MAX_PROGRESS;
    private float progress;
    protected int timer;
    private int gasOut_increase;
    private int energy_increase;
    private int time_decrease;

    public GasConverterTE() {
        super(8000, 4);
        this.MAX_PROGRESS = 2400;
        this.redstone = this.addComponent(new Redstone(this));
        this.redstone.subscribe(i -> GasConverterTE.this.setActive(i <= 0));

        NBTTagCompound nbttt = new NBTTagCompound();
        nbttt.setString("inside", "shulker_projectile");

        ItemStack AcceptItem = new ItemStack(ItemsRegistry.ITEM_projectile_holder_filled);
        AcceptItem.setTagCompound(nbttt);

        this.inputContainer = new InvSlotConsumableItemStack(this, "container", 1, AcceptItem);
        this.outputContainer = new InvSlotOutput(this, "output", 1);
        this.outputFluidSlot = new InvSlotOutput(this, "output_fluid", 1);
        this.inputFluidSlot = new InvSlotConsumableLiquidByList(this, "container_fluid", InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Fill, FluidsRegister.GAS_ERBI);
        this.fluids = (Fluids) addComponent((TileEntityComponent) new Fluids(this));
        this.fluidTank = this.fluids.addTank("fluidTank", 10000, Fluids.fluidPredicate(FluidsRegister.GAS_ERBI));
        this.customUpgradeSlot1 = new InvSlotConsumableItemStack(this, "cupslot1", 1, new ItemStack(ItemsRegistry.UPGRADE_speed));
        this.customUpgradeSlot2 = new InvSlotConsumableItemStack(this, "cupslot2", 1, new ItemStack(ItemsRegistry.UPGRADE_Volecy));
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.progress = nbt.getInteger("progress");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("progress", getProgress());
        return nbt;
    }

    public void onPlaced(ItemStack stack, EntityLivingBase placer, EnumFacing facing) {
        super.onPlaced(stack, placer, facing);
        if (!this.world.isRemote) {
            this.setActive(true);
        }
    }

    protected void updateEntityServer() {
        super.updateEntityServer();
        boolean isActive = this.getActive();

        if(this.timer++ % 100 == 0) {
            haveUpgrades();
        }

        if(canWork(this.gasOut_increase) && isEnergy(energy_increase) && isActive) {
            this.progress += 1 + this.time_decrease;
            this.energy.useEnergy(256 + energy_increase);
        }

        if(this.progress >= this.MAX_PROGRESS) {
            this.progress = 0.0f;
            this.performAction(this.gasOut_increase);
        }

        if(!this.inputFluidSlot.isEmpty())
            this.inputFluidSlot.processFromTank(this.fluidTank, this.outputFluidSlot);

        if(this.inputContainer.isEmpty())
            this.progress = 0.0f;
    }

    private boolean canWork(int count_gas) {
        return !this.inputContainer.isEmpty()
                && this.outputContainer.isEmpty()
                && this.fluidTank.getFluidAmount() + 250 + count_gas < this.fluidTank.getCapacity();
    }

    private boolean isEnergy (int energy) {
        return 256 + energy <= this.energy.getEnergy();
    }

    private void haveUpgrades() {
        this.energy_increase =
                this.customUpgradeSlot1.get().getCount() * ConfigHalf.deligUpdatesInfo_speed_energy
                + this.customUpgradeSlot2.get().getCount() * ConfigHalf.deligUpdatesInfo_volecy_energy;
        this.time_decrease =
                this.customUpgradeSlot1.get().getCount() * ConfigHalf.deligUpdatesInfo_speed_time / 20;
        this.gasOut_increase =
                this.customUpgradeSlot2.get().getCount() * ConfigHalf.deligUpdatesInfo_volecy_mb;
    }

    private void performAction(int count_gas) {
        this.inputContainer.consume(1);
        this.outputContainer.add(new ItemStack(ItemsRegistry.ITEM_projectile_holder));
        this.fluidTank.fillInternal(new FluidStack(FluidsRegister.GAS_ERBI, 250 + count_gas), true);
    }

    @Override
    public ContainerBase<?> getGuiContainer(EntityPlayer entityPlayer) {
        return new ContainerGas(entityPlayer, this);
    }

    @Override
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean b) {
        return new GuiGas(new ContainerGas(entityPlayer, this));
    }
    public int getProgress() {
        return Math.round(this.progress / 2400 * 100F);
    }

    @Override
    public void onGuiClosed(EntityPlayer entityPlayer) {
    }

    @Override
    public EnumFacing getFacing(World world, BlockPos blockPos) {
        return this.getFacing();
    }

    @Override
    public boolean setFacing(World world, BlockPos blockPos, EnumFacing enumFacing, EntityPlayer entityPlayer) {
        if (!this.canSetFacingWrench(enumFacing, entityPlayer)) {
            return false;
        } else {
            this.setFacing(enumFacing);
            return true;
        }
    }

    @Override
    public boolean wrenchCanRemove(World world, BlockPos blockPos, EntityPlayer entityPlayer) {
        return true;
    }

    @Override
    public List<ItemStack> getWrenchDrops(World world, BlockPos blockPos, IBlockState iBlockState, TileEntity tileEntity, EntityPlayer entityPlayer, int i) {
        List<ItemStack> list = new ArrayList<>();
        inputContainer.forEach(list::add);
        outputContainer.forEach(list::add);
        inputFluidSlot.forEach(list::add);
        outputFluidSlot.forEach(list::add);
        customUpgradeSlot1.forEach(list::add);
        customUpgradeSlot2.forEach(list::add);

        return list;
    }
}