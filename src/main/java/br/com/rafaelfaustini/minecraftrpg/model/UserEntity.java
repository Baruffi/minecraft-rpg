package br.com.rafaelfaustini.minecraftrpg.model;

public class UserEntity {
    private String UUID;
    private String last_account_name;

    public String getUUID() {
        return UUID;
    }
    public void setUUID(String UUID) {
        this.UUID = UUID;
    }
    public String getLast_account_name() {
        return last_account_name;
    }
    public void setLast_account_name(String last_account_name) {
        this.last_account_name = last_account_name;
    }
}
