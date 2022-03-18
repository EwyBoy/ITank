package com.ewyboy.itank.util;

import net.minecraft.client.resources.language.I18n;

public class TextHelper {

    public static String lang(String modID, String key) {
        return I18n.get(modID + "." + key);
    }

    /*
     * Formats a string to displays capacity info
     * Eks: 3000 / 5000
     */
    public static String formatCapacityInfo(int currentValue, int maxValue) {
        return currentValue + " / " + maxValue;
    }

    /*
     * Formats a string to displays capacity info with a unit postfix
     * Eks: 3000 / 5000 RF
     */
    public static String formatCapacityInfo(int currentValue, int maxValue, String unit) {
        return currentValue + " / " + maxValue + " " + unit;
    }

}
