package br.com.rafaelfaustini.minecraftrpg.model;

public class UserEntity {
    private String UUID;
    private String lastAccountName;

    public UserEntity() {
    }

    public UserEntity(String UUID, String lastAccountName) {
        this.UUID = UUID;
        this.lastAccountName = lastAccountName;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getLastAccountName() {
        return lastAccountName;
    }

    public void setLastAccountName(String lastAccountName) {
        this.lastAccountName = lastAccountName;
    }
}
