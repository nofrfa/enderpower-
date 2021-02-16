package com.noFRFA.enderpower.misc.events;

import com.noFRFA.enderpower.misc.registr.ItemsRegistry;
import ibxm.Player;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventsHandler {

    /*
    [Появление игрока в мире]
    [Когда игрок возрождается/заходит в мир, ему пишется в чат Hello, 'nick']
    @SubscribeEvent
    public void onJoin(EntityJoinWorldEvent e) {
        if (e.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) e.getEntity();
            player.sendMessage(new TextComponentString("Hello, %p!".replace("%p", player.getName())));
        }
    }

    [Смерть игрока]
    [При смерти игрока, из него выпадает наш новенький предмет 'key']
    @SubscribeEvent
    public void onDeath(LivingDeathEvent e) {
        if (e.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) e.getEntity();

                player.dropItem(new ItemStack(KEY, 1, 0), false);
        }
    }
     */

    @SubscribeEvent
    public void playerInteract(PlayerInteractEvent.EntityInteractSpecific event) {
        World world = event.getWorld();

        if (!world.isRemote) {
            Entity entity = event.getTarget();
            EnumHand handIs = event.getHand();
            ItemStack itemIs = event.getItemStack();
            if (
                    handIs == EnumHand.MAIN_HAND
                    && itemIs.getUnlocalizedName().equals("item.projectile_holder")
                    && entity.getName().equals("entity.ShulkerBullet.name")
            ) {
                EntityPlayer player = event.getEntityPlayer();
                if(player.dimension != 1) {
                    player.sendMessage(new TextComponentString(I18n.format("chatinfo.tag") + I18n.format("event.fail")));
                } else {
                
                    NBTTagCompound inside = new NBTTagCompound();
                    inside.setString("inside", "shulker_projectile");
                
                    entity.setDead();
                    itemIs.setCount(itemIs.getCount()-1);

                    ItemStack finalItem = new ItemStack(ItemsRegistry.ITEM_projectile_holder_filled);
                    finalItem.setTagCompound(inside);

                    player.inventory.addItemStackToInventory(finalItem);
                }
            }
        }
    }
}