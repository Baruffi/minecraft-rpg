package br.com.rafaelfaustini.minecraftrpg.model;

public class UserEntity {
    private String UUID;
    private String lastAccountName;

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
	public UserEntity(String uUID, String lastAccountName) {
		UUID = uUID;
		this.lastAccountName = lastAccountName;
    }
    public UserEntity() {

	}
}
