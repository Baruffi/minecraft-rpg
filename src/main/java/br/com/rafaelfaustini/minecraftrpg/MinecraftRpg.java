package br.com.rafaelfaustini.minecraftrpg;

import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.rafaelfaustini.minecraftrpg.commands.ClassCommand;
import br.com.rafaelfaustini.minecraftrpg.config.ConfigurationProvider;
import br.com.rafaelfaustini.minecraftrpg.config.CustomConfig;
import br.com.rafaelfaustini.minecraftrpg.events.ClassEvent;
import br.com.rafaelfaustini.minecraftrpg.events.JoinEvent;
import br.com.rafaelfaustini.minecraftrpg.events.SkillEvent;

public class MinecraftRpg extends JavaPlugin {

    @Override
    public void onEnable() {
        configure();
        registerEvents();
        registerCommands();
    }

    private void configure() {
        ConfigurationProvider.loadMessageConfig(new CustomConfig("messages.yml"));
        ConfigurationProvider.loadGuiConfig(new CustomConfig("gui.yml"));
        ConfigurationProvider.initDatabase();
    }

    private void registerEvents() {
        Server server = getServer();
        PluginManager pluginManager = server.getPluginManager();

        pluginManager.registerEvents(new JoinEvent(), this);
        pluginManager.registerEvents(new ClassEvent(), this);
        pluginManager.registerEvents(new SkillEvent(), this);
    }

    private void registerCommands() {
        getCommand("class").setExecutor(new ClassCommand());
    }
}
