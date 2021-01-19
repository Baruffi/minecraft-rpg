package br.com.rafaelfaustini.minecraftrpg.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import br.com.rafaelfaustini.minecraftrpg.config.ConfigurationProvider;
import br.com.rafaelfaustini.minecraftrpg.config.model.GuiConfig;

public class ClickEvent implements Listener {

    private final GuiConfig guiClassConfig;

    public ClickEvent() {
        guiClassConfig = ConfigurationProvider.getClassGuiConfig();
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase(guiClassConfig.getGuiTitle())) {
            event.setCancelled(true);
        }
    }
}
