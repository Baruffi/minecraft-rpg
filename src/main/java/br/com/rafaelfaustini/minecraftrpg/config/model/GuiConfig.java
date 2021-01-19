package br.com.rafaelfaustini.minecraftrpg.config.model;

import org.bukkit.inventory.ItemStack;

import br.com.rafaelfaustini.minecraftrpg.config.CustomConfig;

public class GuiConfig {
    private String guiTitle;
    private ItemStack[] guiItems;

    public GuiConfig(CustomConfig customGui, String name) {
        this.guiTitle = customGui.get(name + ".title", String.class);
        this.guiItems = customGui.getAll(name + ".items", ItemStack.class).toArray(new ItemStack[0]);
    }

    public String getGuiTitle() {
        return guiTitle;
    }

    public void setGuiTitle(String guiTitle) {
        this.guiTitle = guiTitle;
    }

    public ItemStack[] getGuiItems() {
        return guiItems;
    }

    public void setGuiItems(ItemStack[] guiItems) {
        this.guiItems = guiItems;
    }
}
