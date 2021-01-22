package br.com.rafaelfaustini.minecraftrpg.model;

public class ClassEntity {
    private Long id;
    private String name;
    private Long castItemId;

    public ClassEntity() {
    }

    public ClassEntity(String name) {
        this.name = name;
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

    public Long getCastItemId() {
        return castItemId;
    }

    public void setCastItemId(Long castItemId) {
        this.castItemId = castItemId;
    }
}
