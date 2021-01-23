package br.com.rafaelfaustini.minecraftrpg.model;

public class UserSkillEntity {
    private Long id;
    private String userUUID;
    private Long skillId;
    private Integer status;

    public UserSkillEntity() {
    }

    public UserSkillEntity(String userUUID, Long skillId, Integer status) {
        this.userUUID = userUUID;
        this.skillId = skillId;
        this.status = status;
    }

    public UserSkillEntity(Long id, String userUUID, Long skillId, Integer status) {
        this.id = id;
        this.userUUID = userUUID;
        this.skillId = skillId;
        this.status = status;
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

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
