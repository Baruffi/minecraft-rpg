package br.com.rafaelfaustini.minecraftrpg.enums;

public enum SkillTypeEnum {
    PASSIVE(0), ACTIVE(1);

    private Integer typeValue;

    SkillTypeEnum(Integer typeValue) {
        this.typeValue = typeValue;
    }

    public Integer getTypeValue() {
        return this.typeValue;
    }

    public static SkillTypeEnum fromInteger(Integer typeValue) {
        if (typeValue == null) {
            typeValue = 0;
        }

        for (SkillTypeEnum skillTypeEnum : SkillTypeEnum.values()) {
            if (skillTypeEnum.typeValue.equals(typeValue)) {
                return skillTypeEnum;
            }
        }

        return null;
    }
}
