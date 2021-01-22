package br.com.rafaelfaustini.minecraftrpg.model;

public class UserEntity {
    private String UUID;
    private String lastAccountName;
    private Long classId;

    public UserEntity() {
    }

    public UserEntity(String UUID, String lastAccountName, Long classId) {
        this.UUID = UUID;
        this.lastAccountName = lastAccountName;
        this.classId = classId;
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

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }
}
