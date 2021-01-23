package br.com.rafaelfaustini.minecraftrpg.model;

public class LoreEntity {
    private Long id;
    private String lore;
    private Long itemId;

    public LoreEntity() {
    }

    public LoreEntity(String lore, Long itemId) {
        this.lore = lore;
        this.itemId = itemId;
    }

    public LoreEntity(Long id, String lore, Long itemId) {
        this.id = id;
        this.lore = lore;
        this.itemId = itemId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
}
