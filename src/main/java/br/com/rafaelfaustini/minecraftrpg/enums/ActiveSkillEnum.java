package br.com.rafaelfaustini.minecraftrpg.enums;

public enum ActiveSkillEnum {
    FIREBALL("fireball");

    private String activeSkillName;

    ActiveSkillEnum(String activeSkillName) {
        this.activeSkillName = activeSkillName;
    }

    public String getActiveSkillName() {
        return this.activeSkillName;
    }

    public static ActiveSkillEnum fromString(String activeSkillName) {
        for (ActiveSkillEnum activeSkillEnum : ActiveSkillEnum.values()) {
            if (activeSkillEnum.activeSkillName.equals(activeSkillName)) {
                return activeSkillEnum;
            }
        }

        return null;
    }
}
