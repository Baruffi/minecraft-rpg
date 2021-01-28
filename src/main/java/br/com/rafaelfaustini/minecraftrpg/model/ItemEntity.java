package br.com.rafaelfaustini.minecraftrpg.model;

import java.util.ArrayList;
import java.util.List;

public class ItemEntity {
    private Long id;
    private String name;
    private String displayName;
    private String material;
    private List<String> lore = new ArrayList<>();

    public ItemEntity() {
    }

    public ItemEntity(String name, String displayName, String material) {
        this.name = name;
        this.displayName = displayName;
        this.material = material;
    }

    public ItemEntity(Long id, String name, String displayName, String material) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
        this.material = material;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
