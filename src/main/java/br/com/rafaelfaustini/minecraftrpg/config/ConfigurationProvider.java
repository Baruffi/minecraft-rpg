package br.com.rafaelfaustini.minecraftrpg.config;

import java.util.ArrayList;
import java.util.List;

import br.com.rafaelfaustini.minecraftrpg.service.InitService;

public class ConfigurationProvider {
    private static MessageConfig messageConfig;
    private static GuiConfig classGuiConfig;
    private static GuiConfig activeSkillGuiConfig;

    public static void loadMessageConfig(CustomConfig messageConfig) {
        ConfigurationProvider.messageConfig = new MessageConfig(messageConfig);
    }

    public static void loadGuiConfig(CustomConfig guiConfig) {
        ConfigurationProvider.classGuiConfig = new GuiConfig(guiConfig, "Class");
        ConfigurationProvider.activeSkillGuiConfig = new GuiConfig(guiConfig, "ActiveSkill");
    }

    public static void initDatabase() {
        InitService initService = new InitService();

        initService.createTables();
        initService.fillTables(classGuiConfig, activeSkillGuiConfig);
    }

    public static MessageConfig getMessageConfig() {
        return messageConfig;
    }

    public static GuiConfig getClassGuiConfig() {
        return classGuiConfig;
    }

    public static GuiConfig getActiveSkillGuiConfig() {
        return activeSkillGuiConfig;
    }
}
