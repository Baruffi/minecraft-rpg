package br.com.rafaelfaustini.minecraftrpg.enums;

public enum PassiveSkillEnum {
    SNEAK("sneak");

    private String passiveSkillName;

    PassiveSkillEnum(String passiveSkillName) {
        this.passiveSkillName = passiveSkillName;
    }

    public String getPassiveSkillName() {
        return this.passiveSkillName;
    }

    public static PassiveSkillEnum fromString(String passiveSkillName) {
        for (PassiveSkillEnum passiveSkillEnum : PassiveSkillEnum.values()) {
            if (passiveSkillEnum.passiveSkillName.equals(passiveSkillName)) {
                return passiveSkillEnum;
            }
        }

        return null;
    }
}
