package br.com.rafaelfaustini.minecraftrpg.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.rafaelfaustini.minecraftrpg.MinecraftRpg;
import br.com.rafaelfaustini.minecraftrpg.config.ConfigurationProvider;

public class LoggingUtil {
    private static final Logger LOGGER = MinecraftRpg.getPlugin(MinecraftRpg.class).getLogger();

    public static void info(String message) {
        LOGGER.log(Level.INFO, message);
    }

    public static void warn(String message, Exception e) {
        LOGGER.log(Level.WARNING, message, e);
    }

    public static void error(String message, Exception e) {
        LOGGER.log(Level.SEVERE, message, e);
    }

    public static void loadException(String message, Exception e) {
        String exceptionMessage = ConfigurationProvider.getMessageConfig().getLoadingException();

        error(prefix(exceptionMessage, message), e);
    }

    public static void commandException(String message, Exception e) {
        String exceptionMessage = ConfigurationProvider.getMessageConfig().getCommandException();

        warn(prefix(exceptionMessage, message), e);
    }

    public static void eventException(String message, Exception e) {
        String exceptionMessage = ConfigurationProvider.getMessageConfig().getEventException();

        warn(prefix(exceptionMessage, message), e);
    }

    private static String prefix(String prefix, String message) {
        return String.format("%s: %s", prefix, message);
    }
}