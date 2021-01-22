package br.com.rafaelfaustini.minecraftrpg.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GuiUtil {

    public static ItemStack getSimpleItem(String displayName, String material, List<String> lore) {
        ItemStack item = new ItemStack(Material.getMaterial(material));

        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(TextUtil.coloredText(displayName));

        List<String> itemLore = new ArrayList<>();

        for (String lorePart : lore) {
            itemLore.add(TextUtil.coloredText(lorePart));
        }

        itemMeta.setLore(itemLore);

        item.setItemMeta(itemMeta);

        return item;
    }
}
