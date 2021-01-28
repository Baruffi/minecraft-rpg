package br.com.rafaelfaustini.minecraftrpg.model;

import java.util.ArrayList;
import java.util.List;

public class UserEntity {
    private String UUID;
    private String lastAccountName;
    private List<ClassEntity> classes = new ArrayList<>();
    private List<SkillEntity> skills = new ArrayList<>();
    private List<UserSkillEntity> userSkillList = new ArrayList<>();

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

    public List<ClassEntity> getClasses() {
        return classes;
    }

    public void setClasses(List<ClassEntity> classes) {
        this.classes = classes;
    }

    public List<SkillEntity> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillEntity> skills) {
        this.skills = skills;
    }

    public List<UserSkillEntity> getUserSkillList() {
        return userSkillList;
    }

    public void setUserSkillList(List<UserSkillEntity> userSkillList) {
        this.userSkillList = userSkillList;
    }
}
