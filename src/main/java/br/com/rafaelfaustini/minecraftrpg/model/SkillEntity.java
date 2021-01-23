package br.com.rafaelfaustini.minecraftrpg.model;

public class SkillEntity {
    private Long id;
    private String name;
    private Integer type;

    public SkillEntity() {
    }

    public SkillEntity(String name, Integer type) {
        this.name = name;
        this.type = type;
    }

    public SkillEntity(Long id, String name, Integer type) {
        this.id = id;
        this.name = name;
        this.type = type;
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
}
