package br.com.rafaelfaustini.minecraftrpg.enums;

public enum SkillStatusEnum {
    INACTIVE(0), ACTIVE(1);

    private Integer statusValue;

    SkillStatusEnum(Integer statusValue) {
        this.statusValue = statusValue;
    }

    public Integer getStatusValue() {
        return this.statusValue;
    }

    public static SkillStatusEnum fromInteger(Integer statusValue) {
        for (SkillStatusEnum skillStatusEnum : SkillStatusEnum.values()) {
            if (skillStatusEnum.statusValue.equals(statusValue)) {
                return skillStatusEnum;
            }
        }

        return null;
    }
}
