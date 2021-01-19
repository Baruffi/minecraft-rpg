package br.com.rafaelfaustini.minecraftrpg.config;

public class ConfigurationProvider {
    private static CustomConfig messagesConfig;

    public static CustomConfig getMessagesConfig() {
        return messagesConfig;
    }

    public static void setMessagesConfig(CustomConfig messagesConfig) {
        ConfigurationProvider.messagesConfig = messagesConfig;
    }
}
