package br.com.rafaelfaustini.minecraftrpg.model;

public class ClassEntity {
    private Long id;
    private String name;

    public ClassEntity() {
    }

    public ClassEntity(Long id, String name) {
        this.id = id;
        this.name = name;
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
}
