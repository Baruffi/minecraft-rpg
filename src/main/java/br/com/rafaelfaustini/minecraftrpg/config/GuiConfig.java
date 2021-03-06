package br.com.rafaelfaustini.minecraftrpg.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GuiConfig {
    private String guiTitle;
    private List<GuiItemConfig> guiItems = new ArrayList<>();

    public GuiConfig(CustomConfig customGui, String name) {
        Set<String> itemKeys = customGui.getSectionKeys(name + ".items");

        for (String itemKey : itemKeys) {
            GuiItemConfig guiItemConfig = new GuiItemConfig(itemKey);
            String itemPath = name + ".items." + itemKey;

            guiItemConfig.setDisplayName(customGui.getString(itemPath + ".displayName"));
            guiItemConfig.setMaterial(customGui.getString(itemPath + ".material"));
            guiItemConfig.setLore(customGui.getStringList(itemPath + ".lore"));

            this.guiItems.add(guiItemConfig);
        }

        this.guiTitle = customGui.getString(name + ".title");
    }

    public String getGuiTitle() {
        return guiTitle;
    }

    public void setGuiTitle(String guiTitle) {
        this.guiTitle = guiTitle;
    }

    public List<GuiItemConfig> getGuiItems() {
        return guiItems;
    }

    public void setGuiItems(List<GuiItemConfig> guiItems) {
        this.guiItems = guiItems;
    }
}
