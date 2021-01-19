package br.com.rafaelfaustini.minecraftrpg.config;

import br.com.rafaelfaustini.minecraftrpg.config.model.GuiConfig;
import br.com.rafaelfaustini.minecraftrpg.config.model.MessageConfig;

public class ConfigurationProvider {
    private static MessageConfig messageConfig;
    private static GuiConfig classGuiConfig;

    public static void loadMessageConfig(CustomConfig messageConfig) {
        ConfigurationProvider.messageConfig = new MessageConfig(messageConfig);
    }

    public static void loadGuiConfig(CustomConfig guiConfig) {
        ConfigurationProvider.classGuiConfig = new GuiConfig(guiConfig, "Class");
    }

    public static MessageConfig getMessageConfig() {
        return messageConfig;
    }

    public static GuiConfig getClassGuiConfig() {
        return classGuiConfig;
    }
}
