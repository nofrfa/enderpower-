package com.noFRFA.enderpower.misc;

import net.minecraft.item.EnumRarity;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.EnumHelper;

public class RarityList {
    TextFormatting[] Preliminarily_DONT_USE = {
            TextFormatting.BLACK,//0
            TextFormatting.DARK_BLUE,//1
            TextFormatting.DARK_GREEN,//2
            TextFormatting.DARK_AQUA,//3
            TextFormatting.DARK_RED,//4
            TextFormatting.DARK_PURPLE,//5
            TextFormatting.GOLD,//6
            TextFormatting.GRAY,//7
            TextFormatting.DARK_GRAY,//8
            TextFormatting.BLUE,//9
            TextFormatting.GREEN,//10
            TextFormatting.AQUA,//11
            TextFormatting.RED,//12
            TextFormatting.LIGHT_PURPLE,//13
            TextFormatting.YELLOW,//14
            TextFormatting.WHITE,//15
            TextFormatting.OBFUSCATED,//16
            TextFormatting.BOLD,//17
            TextFormatting.STRIKETHROUGH,//18
            TextFormatting.UNDERLINE,//19
            TextFormatting.ITALIC,//20
    };
    public static final EnumRarity MSFR = EnumHelper.addRarity("matter_semiFluids_rarity", TextFormatting.DARK_PURPLE, "matter_semiFluids_rarity");
}
