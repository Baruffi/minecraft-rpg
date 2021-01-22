package br.com.rafaelfaustini.minecraftrpg.model;

public class UserClassEntity {
    private Long id;
    private String userUUID;
    private Long classId;

    public UserClassEntity() {
    }

    public UserClassEntity(String userUUID, Long classId) {
        this.userUUID = userUUID;
        this.classId = classId;
    }

    public UserClassEntity(Long id, String userUUID, Long classId) {
        this.id = id;
        this.userUUID = userUUID;
        this.classId = classId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }
}
