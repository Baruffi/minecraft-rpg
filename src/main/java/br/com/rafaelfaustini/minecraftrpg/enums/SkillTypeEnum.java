package br.com.rafaelfaustini.minecraftrpg.enums;

public enum SkillTypeEnum {
    PASSIVE("passive", 0), ACTIVE("active", 1);

    private String typeName;
    private Integer typeValue;

    SkillTypeEnum(String typeName, Integer typeValue) {
        this.typeName = typeName;
        this.typeValue = typeValue;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public Integer getTypeValue() {
        return this.typeValue;
    }

    public static SkillTypeEnum fromString(String typeName) {
        for (SkillTypeEnum skillTypeEnum : SkillTypeEnum.values()) {
            if (skillTypeEnum.typeName.equals(typeName)) {
                return skillTypeEnum;
            }
        }

        return null;
    }

    public static SkillTypeEnum fromInteger(Integer typeValue) {
        for (SkillTypeEnum skillTypeEnum : SkillTypeEnum.values()) {
            if (skillTypeEnum.typeValue.equals(typeValue)) {
                return skillTypeEnum;
            }
        }

        return null;
    }
}
