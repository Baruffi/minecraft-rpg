package br.com.rafaelfaustini.minecraftrpg.config;

import br.com.rafaelfaustini.minecraftrpg.service.InitService;

public class ConfigurationProvider {
    private static MessageConfig messageConfig;
    private static GuiConfig classGuiConfig;
    private static GuiConfig skillTreeGuiConfig;
    private static GuiConfig skillGuiConfig;
    private static GuiConfig activeSkillGuiConfig;
    private static GuiConfig passiveSkillGuiConfig;

    public static void loadMessageConfig(CustomConfig messageConfig) {
        ConfigurationProvider.messageConfig = new MessageConfig(messageConfig);
    }

    public static void loadGuiConfig(CustomConfig guiConfig) {
        ConfigurationProvider.classGuiConfig = new GuiConfig(guiConfig, "Class");
        ConfigurationProvider.skillTreeGuiConfig = new GuiConfig(guiConfig, "SkillTree");
        ConfigurationProvider.skillGuiConfig = new GuiConfig(guiConfig, "Skill");
        ConfigurationProvider.activeSkillGuiConfig = new GuiConfig(guiConfig, "ActiveSkill");
        ConfigurationProvider.passiveSkillGuiConfig = new GuiConfig(guiConfig, "PassiveSkill");
    }

    public static void initDatabase() {
        InitService initService = new InitService();

        initService.createTables();
        initService.fillTables(classGuiConfig, activeSkillGuiConfig, passiveSkillGuiConfig);
    }

    public static MessageConfig getMessageConfig() {
        return messageConfig;
    }

    public static GuiConfig getClassGuiConfig() {
        return classGuiConfig;
    }

    public static GuiConfig getSkillTreeGuiConfig() {
        return skillTreeGuiConfig;
    }

    public static GuiConfig getSkillGuiConfig() {
        return skillGuiConfig;
    }

    public static GuiConfig getActiveSkillGuiConfig() {
        return activeSkillGuiConfig;
    }

    public static GuiConfig getPassiveSkillGuiConfig() {
        return passiveSkillGuiConfig;
    }
}
