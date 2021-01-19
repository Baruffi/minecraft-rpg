package br.com.rafaelfaustini.minecraftrpg;

import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.rafaelfaustini.minecraftrpg.commands.ClassCommand;
import br.com.rafaelfaustini.minecraftrpg.config.ConfigurationProvider;
import br.com.rafaelfaustini.minecraftrpg.config.CustomConfig;
import br.com.rafaelfaustini.minecraftrpg.events.JoinEvent;

public class MinecraftRpg extends JavaPlugin {

    @Override
    public void onEnable() {
        loadConfigurations();
        registerEvents();
        registerCommands();
    }

    private void loadConfigurations() {
        ConfigurationProvider.setMessagesConfig(new CustomConfig("messages.yml"));
    }

    private void registerEvents() {
        Server server = getServer();
        PluginManager pluginManager = server.getPluginManager();

        pluginManager.registerEvents(new JoinEvent(), this);
    }

    private void registerCommands() {
        getCommand("class").setExecutor(new ClassCommand());
    }
}
