package br.com.rafaelfaustini.minecraftrpg.config.model;

import java.util.List;

public class GuiItemConfig {
    private String displayName;
    private String material;
    private List<String> lore;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }
}
