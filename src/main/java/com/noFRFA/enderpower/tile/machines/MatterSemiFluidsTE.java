package com.noFRFA.enderpower.tile.machines;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IMultiEnergySource;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.recipe.ISemiFluidFuelManager;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.comp.Energy;
import ic2.core.block.comp.Fluids;
import ic2.core.block.comp.Redstone;
import ic2.core.block.comp.TileEntityComponent;
import ic2.core.block.invslot.*;
import ic2.core.network.GuiSynced;
import ic2.core.ref.FluidName;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public class MatterSemiFluidsTE extends TileEntityInventory implements IHasGui, IUpgradableBlock, IMultiEnergySource, INetworkClientTileEntityEventListener, ISemiFluidFuelManager {

	public final InvSlotUpgrade upgradeSlot;
	public final InvSlotOutput outputSlot;
	public final InvSlotConsumableLiquid containerslot;
	public final FluidTank fluidTank;
	protected final Fluids fluids;
	@GuiSynced
	public int tier;
	public int generation;
	public int scrap;
	protected Redstone redstone;
	private boolean addedToEnet;
	private Energy energy;

	public MatterSemiFluidsTE() {
		this.tier = 4;
		this.generation = 75000;
		this.redstone = (Redstone) this.addComponent(new Redstone(this));
		this.redstone.subscribe(new Redstone.IRedstoneChangeHandler() {
			public void onRedstoneChange(int i) {
				MatterSemiFluidsTE.this.setActive(i <= 0);
			}
		});
		this.outputSlot = new InvSlotOutput(this, "output", 1);
		this.containerslot = new InvSlotConsumableLiquidByList(this, "container", InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Drain, FluidName.uu_matter.getInstance());
		this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 4);
		this.fluids = (Fluids) addComponent((TileEntityComponent) new Fluids(this));
		this.fluidTank = this.fluids.addTank("fluidTank", 10000, Fluids.fluidPredicate(FluidName.uu_matter.getInstance()));
	}

	protected void onLoaded() {
		super.onLoaded();
		if (!this.world.isRemote) {
			this.addedToEnet = !MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
		}
	}

	protected void onUnloaded() {
		super.onUnloaded();
		if (this.addedToEnet) {
			this.addedToEnet = MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
		}
	}

	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.generation = nbt.getInteger("production");
		this.tier = nbt.getInteger("tier");
	}

	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("production", this.generation);
		nbt.setInteger("tier", this.tier);
		return nbt;
	}

	public void onPlaced(ItemStack stack, EntityLivingBase placer, EnumFacing facing) {
		super.onPlaced(stack, placer, facing);
		if (!this.world.isRemote) {
			this.setActive(true);
		}
	}

	@Override
	public boolean sendMultipleEnergyPackets() {
		return (double) this.generation - EnergyNet.instance.getPowerFromTier(this.tier) > 0.0D;
	}

	@Override
	public int getMultipleEnergyPacketAmount() {
		return (int) Math.round((double) this.generation);
	}

	public void changeProduction(int value) {
		this.generation += value;
		if (this.generation <= 0)
			this.generation = 0;
		if (this.generation >= 999999999)
			this.generation = 999999999;
	}

	@Override
	public double getOfferedEnergy() {
		if (!this.getActive())
			return 0;
		if (this.fluidTank.getFluidAmount() > 0) {
			this.fluidTank.drain(1, true);
			return this.generation;
		} else return 0;
	}

	@Override
	public void drawEnergy(double v) {
	}

	@Override
	public boolean emitsEnergyTo(IEnergyAcceptor iEnergyAcceptor, EnumFacing enumFacing) {
		return true;
	}

	@Override
	public void onNetworkEvent(EntityPlayer entityPlayer, int i) {
	}

	@Override
	public double getEnergy() {
		return this.energy.getEnergy();
	}

	@Override
	public boolean useEnergy(double v) {
		return false;
	}

	@Override
	public Set<UpgradableProperty> getUpgradableProperties() {
		return EnumSet.of(
				UpgradableProperty.RedstoneSensitive
		);
	}

	@Override
	public ContainerBase<MatterSemiFluidsTE> getGuiContainer(EntityPlayer player) {
		return null;
	}

	@Override
	public GuiScreen getGui(EntityPlayer player, boolean b) {
		return null;
	}

	@Override
	public void onGuiClosed(EntityPlayer entityPlayer) {

	}

	@Override
	public void markDirty() {
	}

	@Override
	public void addFluid(String s, long l, long l1) {
		FluidName.uu_matter.getInstance();
	}

	@Override
	public void removeFluid(String s) {
	}

	@Override
	public FuelProperty getFuelProperty(Fluid fluid) {
		return null;
	}

	@Override
	public Map<String, FuelProperty> getFuelProperties() {
		return null;
	}

	@Override
	public boolean acceptsFluid(Fluid fluid) {
		return false;
	}

	@Override
	public Set<Fluid> getAcceptedFluids() {
		return null;
	}

	@Override
	public int getSourceTier() {
		return this.tier;
	}

	@Override
	public int getSizeInventory() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return null;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
	}

	@Override
	public int getInventoryStackLimit() {
		return 0;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return false;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return this.getDisplayName();
	}
}