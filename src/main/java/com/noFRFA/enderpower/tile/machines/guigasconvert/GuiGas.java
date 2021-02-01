package com.noFRFA.enderpower.tile.machines.guigasconvert;

import ic2.core.GuiIC2;
import ic2.core.gui.TankGauge;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiGas extends GuiIC2<ContainerGas> {

	private final int x1 = this.guiLeft + 6;
	private final int y1 = this.guiLeft + 30;

	public GuiGas(ContainerGas container) {
		super(container);
		addElement(TankGauge.createNormal(this, 60, 15, container.base.fluidTank));
	}

	@SideOnly(Side.CLIENT)
	protected void drawForegroundLayer(int mouseX, int mouseY) {
		super.drawForegroundLayer(mouseX, mouseY);

		//drawTexturedModalRect(26, 33, 196, 0, 50, 50);
        /*
        int var1, var2;
        var1 = (int) this.container.base.getMore(1);
        var2 = (int) this.container.base.getMore(2);

        if(this.isMouseOver(mouseX, mouseY)) {
            List<String> infoText = new ArrayList<>();
            infoText.add(I18n.format("gas_converter.gui.progress") + " " + this.container.base.getPercent());

            GuiUtils.drawHoveringText(infoText, mouseX, mouseY, mc.displayWidth, mc.displayHeight, -1, mc.fontRenderer);
        }
        int textureX;
        textureX = Math.toIntExact(Math.round(0 + container.base.getMore(1)));

        this.mc.getTextureManager().bindTexture(getTexture());
        this.drawTexturedModalRect(7, 30, 196, 0, textureX, 2);
        this.drawTexturedModalRect(7, 35, 196, 0, 5, 2);
        */
		this.fontRenderer.drawString("Progress", 7, 30, 4210752);
		this.fontRenderer.drawString("" + this.container.base.getPercent(), 18, 20, 4210752);
	}

	public ResourceLocation getTexture() {
		return new ResourceLocation("enderpower", "textures/gui/gui_gas.png");
	}

	private boolean isMouseOver(int mouseX, int mouseY) {
		return mouseX >= this.x1 && mouseY >= this.y1 && mouseX < this.x1 + 20 && mouseY < this.y1 + 4;
	}
}
