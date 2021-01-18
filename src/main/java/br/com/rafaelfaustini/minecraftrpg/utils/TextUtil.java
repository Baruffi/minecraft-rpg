package br.com.rafaelfaustini.minecraftrpg.utils;

import org.bukkit.ChatColor;

public class TextUtil {
    public static String coloredText(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}