package br.com.rafaelfaustini.minecraftrpg.enums;

public enum ClassEnum {
    WARRIOR("warrior"), MAGE("mage"), ROGUE("rogue"), DRUID("druid"), ALCHEMIST("alchemist"), BARD("bard");

    private String className;

    ClassEnum(String className) {
        this.className = className;
    }

    public String getClassName() {
        return this.className;
    }

    public static ClassEnum fromString(String className) {
        for (ClassEnum classEnum : ClassEnum.values()) {
            if (classEnum.className.equals(className)) {
                return classEnum;
            }
        }

        return null;
    }
}
