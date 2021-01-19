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
                gui.setContents(guiClassConfig.getGuiItems());

                player.openInventory(gui);
            }
        }

        return true;
    }

    // TODO: criar arquivo de config custom para displaynames e lore de items de GUI

    private ItemStack getWarrior() {
        return getSimpleItem(Material.CHAINMAIL_CHESTPLATE, "&aWarrior", "&bA powerful warrior",
                "&bthat pushes through with", "&cstrength and resilience!");
    }

    private ItemStack getMage() {
        return getSimpleItem(Material.ENCHANTED_BOOK, "&aMage", "&bA scholar mage", "&bthat will enchant you with",
                "&cwisdom and magic!");
    }

    private ItemStack getRogue() {
        return getSimpleItem(Material.LEATHER_BOOTS, "&aRogue", "&bA fast rogue", "&bmoving quickly with his",
                "&cwits and speed!");
    }

    private ItemStack getDruid() {
        return getSimpleItem(Material.BONE, "&aDruid", "&bA wild druid", "&bthat fights by the side of",
                "&canimals and nature!");
    }

    private ItemStack getAlchemist() {
        return getSimpleItem(Material.POTION, "&aAlchemist", "&bA genius alchemist", "&ba powerful foe who can use",
                "&cpotions and transmutation!");
    }

    private ItemStack getBard() {
        return getSimpleItem(Material.NOTE_BLOCK, "&aBard", "&bA charming bard", "&bcheering everyone with",
                "&cthe power of music!");
    }

    private ItemStack getSimpleItem(Material material, String displayName, String... lore) {
        ItemStack item = new ItemStack(material);

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
