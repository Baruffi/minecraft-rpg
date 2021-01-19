package br.com.rafaelfaustini.minecraftrpg.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.rafaelfaustini.minecraftrpg.MinecraftRpg;
import br.com.rafaelfaustini.minecraftrpg.config.ConfigurationProvider;

public class LoggingUtil {
    private static final Logger LOGGER = MinecraftRpg.getPlugin(MinecraftRpg.class).getLogger();
    private static final String LOADING_EXCEPTION_CONFIG_STRING = "Utils.loadingException";
    private static final String COMMAND_EXCEPTION_CONFIG_STRING = "Utils.commandException";
    private static final String EVENT_EXCEPTION_CONFIG_STRING = "Utils.eventException";

    public static void loadException(String message, Exception e) {
        String exceptionMessage = ConfigurationProvider.getMessagesConfig().get(LOADING_EXCEPTION_CONFIG_STRING);

        LOGGER.log(Level.SEVERE, String.format("%s: %s", exceptionMessage, message), e);
    }

    public static void commandException(String message, Exception e) {
        String exceptionMessage = ConfigurationProvider.getMessagesConfig().get(COMMAND_EXCEPTION_CONFIG_STRING);

        LOGGER.log(Level.WARNING, String.format("%s: %s", exceptionMessage, message), e);
    }

    public static void eventException(String message, Exception e) {
        String exceptionMessage = ConfigurationProvider.getMessagesConfig().get(EVENT_EXCEPTION_CONFIG_STRING);

        LOGGER.log(Level.WARNING, String.format("%s: %s", exceptionMessage, message), e);
    }
}