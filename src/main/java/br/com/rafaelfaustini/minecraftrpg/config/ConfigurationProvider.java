package br.com.rafaelfaustini.minecraftrpg.config;

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
