package br.com.rafaelfaustini.minecraftrpg.utils;

import br.com.rafaelfaustini.minecraftrpg.MinecraftRpg;

public class ExceptionUtil {
    private static final MinecraftRpg PLUGIN = MinecraftRpg.getPlugin(MinecraftRpg.class);

    public static void tryException(Exception e) {
        String exceptionMessage = PLUGIN.messagesConfig.getConfig().get("Utils.exception").toString(); // Leitura de arquivo, cabe um
                                                                                        // try catch
        String pluginName = PLUGIN.getName();
        System.out.println(String.format("[%s] %s", pluginName, exceptionMessage));
        System.out.println(String.format("[%s] %s", pluginName, e.getMessage()));
        e.printStackTrace();
    }
}