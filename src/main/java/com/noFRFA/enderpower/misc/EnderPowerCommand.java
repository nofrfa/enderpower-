package com.noFRFA.enderpower.misc;

import com.noFRFA.enderpower.misc.registr.ItemsRegistry;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class EnderPowerCommand extends CommandBase {
    public static final String
            NAME = "enderpower_give",//Имя команды, используется при вызове
            USAGE = "/enderpower_give <detter_shulker>",//Шаблон вызова, выводится при выбрасывании WrongUsageException
            ALIAS = "ep_give";//Допустимая вариация команды, таких может быть несколько

    @Override
    public String getName() {
        return this.NAME;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return this.USAGE;
    }

    @Override
    public List<String> getAliases() {

        List<String> aliases = new ArrayList<String>();//Так как допустимых вариаций может быть много, передаются они массивом строк

        aliases.add(this.ALIAS);

        return aliases;
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if(args.length == 1)
            return getListOfStringsMatchingLastWord(args, "detter_shulker");
        else return null;
    }

    //Выполнение команды происходит здесь, этот метод вызывается только на сервере (физическом или логическом)
    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender instanceof EntityPlayer) {
            if (args.length != 1) {
                throw new WrongUsageException(this.getUsage(sender));
            }

            EntityPlayer player = this.getCommandSenderAsPlayer(sender);//Получение экземрляра игрока, вызвавшего команду
            ItemStack mainHandItem = player.getHeldItemMainhand();

            if(args[0].equals("detter_shulker")) {
                if(mainHandItem.getUnlocalizedName().equals("item.projectile_holder_filled")) {
                    if(!mainHandItem.hasTagCompound()) {
                        ItemStack finalItem = new ItemStack(ItemsRegistry.ITEM_projectile_holder_filled);

                        NBTTagCompound inside = new NBTTagCompound();
                        inside.setString("inside", "shulker_projectile");
                        finalItem.setTagCompound(inside);

                        mainHandItem.setCount(-1);

                        player.inventory.addItemStackToInventory(finalItem);
                        player.sendMessage(new TextComponentString(I18n.format("chatinfo.tag") + I18n.format("command.enderpower_give.success")));
                    } else {
                        player.sendMessage(new TextComponentString(I18n.format("chatinfo.tag") + I18n.format("command.enderpower_give.nbt.error")));
                    }
                } else {
                    player.sendMessage(new TextComponentString(I18n.format("chatinfo.tag") + I18n.format("command.enderpower_give.detter.error")));
                }
            }
            if(ConfigHalf.LevelAccess) {
                if (args[0].equals("detter_enderfish")) {
                    player.sendMessage(new TextComponentString(I18n.format("chatinfo.tag") + "Coming soon..."));
                }
            }
        }
    }
}
