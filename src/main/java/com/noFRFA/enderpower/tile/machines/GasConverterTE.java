package com.noFRFA.enderpower.tile.machines;

import com.noFRFA.enderpower.EnderPower;
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

import java.util.ArrayList;
import java.util.List;

@NotClassic
public class GasConverterTE extends TileEntityElectricMachine implements IHasGui, IWrenchable {

	public final InvSlotConsumableItemStack inputContainer;
	public final InvSlotOutput outputFluidSlot;
	public final InvSlotConsumableLiquid inputFluidSlot;
	public final InvSlotConsumableItemStack customUpgradeSlot1;
	public final InvSlotConsumableItemStack customUpgradeSlot2;
	public final InvSlotOutput outputContainer;
	public final FluidTank fluidTank;
	protected final Fluids fluids;
	public int fixGuiPart;
	public int guiProgress;
	public float MAX_PROGRESS;
	protected Redstone redstone;
	private float progress;

	public GasConverterTE() {
		super(4096, 4);
		this.guiProgress = 0;
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
		nbt.setInteger("progress", getPercent());
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

		System.out.println(getPercent());

		boolean active = this.getActive();
		float count_speed_boost = this.customUpgradeSlot1.get().getCount() * EnderPower.Upgrader_speed_boost;
		int count_speed_energy = this.customUpgradeSlot1.get().getCount() * EnderPower.Upgrader_speed_energy;
		int count_volecy_boost = this.customUpgradeSlot2.get().getCount() * EnderPower.Upgrader_volecy_boost;
		float count_volecy_time = this.customUpgradeSlot2.get().getCount() * EnderPower.Upgrader_volecy_time;
		int gas_output = 250 + count_volecy_boost;

		if (this.inputContainer.isEmpty()) {
			this.progress = 0;
		}

		this.MAX_PROGRESS = 1200 + count_volecy_time;

		if (this.progress >= this.MAX_PROGRESS) {
			this.hasOperate(gas_output);
			this.progress -= this.MAX_PROGRESS;
			active = false;
		}

		if (!this.inputFluidSlot.isEmpty()) this.inputFluidSlot.processFromTank(this.fluidTank, this.outputFluidSlot);

		if (active && canOperate() && 256 + count_speed_energy <= this.energy.getEnergy()) {
			this.progress += 1 + count_speed_boost;
			this.energy.useEnergy(256 + count_speed_energy);
			this.fixGuiPart = getPercent();
		}
	}

	private void hasOperate(int gas_output) {
		this.inputContainer.consume(1);
		this.outputContainer.add(new ItemStack(ItemsRegistry.ITEM_projectile_holder));
		this.fluidTank.fillInternal(new FluidStack(FluidsRegister.GAS_ERBI, gas_output), true);
	}

	private boolean canOperate() {
		return this.outputContainer.isEmpty() && !this.inputContainer.isEmpty() && this.fluidTank.getFluidAmount() + 1 < this.fluidTank.getCapacity();
	}

	@Override
	public ContainerBase<?> getGuiContainer(EntityPlayer entityPlayer) {
		return new ContainerGas(entityPlayer, this);
	}

	@Override
	public GuiScreen getGui(EntityPlayer entityPlayer, boolean b) {
		return new GuiGas(new ContainerGas(entityPlayer, this));
	}

	public int getPercent() {
		return Math.round(this.progress / Math.max(1200, this.MAX_PROGRESS) * 100F);
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

		return list;
	}
}