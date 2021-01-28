package br.com.rafaelfaustini.minecraftrpg.model;

public class SkillEntity {
    private Long id;
    private String name;
    private Integer type;
    private Long itemId;
    private ItemEntity item;

    public SkillEntity() {
    }

    public SkillEntity(String name, Integer type, Long itemId) {
        this.name = name;
        this.type = type;
        this.itemId = itemId;
    }

    public SkillEntity(Long id, String name, Integer type, Long itemId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.itemId = itemId;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }
}
