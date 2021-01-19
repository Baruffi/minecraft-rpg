package br.com.rafaelfaustini.minecraftrpg.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import br.com.rafaelfaustini.minecraftrpg.config.ConfigurationProvider;
import br.com.rafaelfaustini.minecraftrpg.config.model.GuiConfig;
import br.com.rafaelfaustini.minecraftrpg.config.model.GuiItemConfig;
import br.com.rafaelfaustini.minecraftrpg.utils.TextUtil;

public class ClassCommand implements CommandExecutor {
    private final GuiConfig guiClassConfig;

    public ClassCommand() {
        guiClassConfig = ConfigurationProvider.getClassGuiConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("class")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                Inventory gui = Bukkit.createInventory(player, 9, guiClassConfig.getGuiTitle());

                List<GuiItemConfig> guiItems = guiClassConfig.getGuiItems();
                List<ItemStack> items = new ArrayList<>();

                for (GuiItemConfig guiItem : guiItems) {
                    ItemStack item = getSimpleItem(guiItem.getDisplayName(), guiItem.getMaterial(), guiItem.getLore());

                    items.add(item);
                }

                gui.setContents(items.toArray(new ItemStack[0]));

                player.openInventory(gui);
            }
        }

        return true;
    }

    private ItemStack getSimpleItem(String displayName, String material, List<String> lore) {
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
