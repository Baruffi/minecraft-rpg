package br.com.rafaelfaustini.minecraftrpg.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import br.com.rafaelfaustini.minecraftrpg.config.ConfigurationProvider;
import br.com.rafaelfaustini.minecraftrpg.config.model.GuiConfig;
import br.com.rafaelfaustini.minecraftrpg.config.model.GuiItemConfig;
import br.com.rafaelfaustini.minecraftrpg.config.model.MessageConfig;
import br.com.rafaelfaustini.minecraftrpg.enums.ClassEnum;
import br.com.rafaelfaustini.minecraftrpg.utils.TextUtil;

public class ClickEvent implements Listener {

    private final MessageConfig messageConfig;
    private final GuiConfig guiClassConfig;

    public ClickEvent() {
        messageConfig = ConfigurationProvider.getMessageConfig();
        guiClassConfig = ConfigurationProvider.getClassGuiConfig();
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        InventoryView view = event.getView();
        Player player = (Player) event.getWhoClicked();

        if (view.getTitle().equalsIgnoreCase(guiClassConfig.getGuiTitle())) {
            for (GuiItemConfig itemConfig : guiClassConfig.getGuiItems()) {
                if (event.getCurrentItem().getType().equals(Material.getMaterial(itemConfig.getMaterial()))) {
                    ClassEnum selectedClass = ClassEnum.fromString(itemConfig.getKey());

                    // TODO: register the player class in the database

                    switch (selectedClass) {
                        case WARRIOR:
                            player.getInventory().addItem(new ItemStack(Material.WOODEN_AXE));
                            player.getInventory().addItem(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                            break;
                        case MAGE:
                            player.getInventory().addItem(new ItemStack(Material.WRITABLE_BOOK));
                            player.getInventory().addItem(new ItemStack(Material.ENCHANTING_TABLE));
                            break;
                        case ROGUE:
                            player.getInventory().addItem(new ItemStack(Material.WOODEN_SWORD));
                            player.getInventory().addItem(new ItemStack(Material.LEATHER_BOOTS));
                            break;
                        case DRUID:
                            player.getInventory().addItem(new ItemStack(Material.BONE, 10));
                            player.getInventory().addItem(new ItemStack(Material.SADDLE));
                            break;
                        case ALCHEMIST:
                            player.getInventory().addItem(new ItemStack(Material.GLASS_BOTTLE, 8));
                            player.getInventory().addItem(new ItemStack(Material.BREWING_STAND));
                            break;
                        case BARD:
                            player.getInventory().addItem(new ItemStack(Material.JUKEBOX));
                            player.getInventory().addItem(new ItemStack(Material.MUSIC_DISC_CAT));
                            break;
                        default:
                            break;
                    }

                    String message = String.format(messageConfig.getClassChoice(), itemConfig.getDisplayName());

                    player.sendMessage(TextUtil.coloredText(message));

                    view.close();
                }
            }

            event.setCancelled(true);
        }
    }
}
