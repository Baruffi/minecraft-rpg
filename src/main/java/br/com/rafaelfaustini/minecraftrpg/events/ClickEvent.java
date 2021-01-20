package br.com.rafaelfaustini.minecraftrpg.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import br.com.rafaelfaustini.minecraftrpg.config.ConfigurationProvider;
import br.com.rafaelfaustini.minecraftrpg.config.model.GuiConfig;
import br.com.rafaelfaustini.minecraftrpg.config.model.GuiItemConfig;
import br.com.rafaelfaustini.minecraftrpg.enums.ClassEnum;

public class ClickEvent implements Listener {

    private final GuiConfig guiClassConfig;

    public ClickEvent() {
        guiClassConfig = ConfigurationProvider.getClassGuiConfig();
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase(guiClassConfig.getGuiTitle())) {
            Player player = (Player) event.getWhoClicked();

            for (GuiItemConfig itemConfig : guiClassConfig.getGuiItems()) {
                if (event.getCurrentItem().getType().equals(Material.getMaterial(itemConfig.getMaterial()))) {
                    ClassEnum selectedClass = ClassEnum.fromString(itemConfig.getKey());

                    switch (selectedClass) {
                        case WARRIOR:
                            player.sendMessage("Nice");
                            break;
                        default:
                            player.sendMessage("Not so nice");
                            break;
                    }
                }
            }

            event.setCancelled(true);
        }
    }
}
