package br.com.rafaelfaustini.minecraftrpg.model;

import java.util.ArrayList;
import java.util.List;

public class ClassEntity {
    private Long id;
    private String name;
    private Long itemId;
    private ItemEntity item;
    private List<SkillEntity> skills = new ArrayList<>();

    public ClassEntity() {
    }

    public ClassEntity(String name, Long itemId) {
        this.name = name;
        this.itemId = itemId;
    }

    public ClassEntity(Long id, String name, Long itemId) {
        this.id = id;
        this.name = name;
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

    public List<SkillEntity> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillEntity> skills) {
        this.skills = skills;
    }
}
