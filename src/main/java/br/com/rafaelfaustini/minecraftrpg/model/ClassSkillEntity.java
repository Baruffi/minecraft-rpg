package br.com.rafaelfaustini.minecraftrpg.model;

public class ClassSkillEntity {
    private Long id;
    private Long classId;
    private Long skillId;

    public ClassSkillEntity() {
    }

    public ClassSkillEntity(Long classId, Long skillId) {
        this.classId = classId;
        this.skillId = skillId;
    }

    public ClassSkillEntity(Long id, Long classId, Long skillId) {
        this.id = id;
        this.classId = classId;
        this.skillId = skillId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }
}
