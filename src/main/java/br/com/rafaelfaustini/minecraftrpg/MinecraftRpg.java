package br.com.rafaelfaustini.minecraftrpg;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.rafaelfaustini.minecraftrpg.commands.ClassCommand;
import br.com.rafaelfaustini.minecraftrpg.commands.TreeCommand;
import br.com.rafaelfaustini.minecraftrpg.config.ConfigurationProvider;
import br.com.rafaelfaustini.minecraftrpg.config.CustomConfig;
import br.com.rafaelfaustini.minecraftrpg.events.ClassEvent;
import br.com.rafaelfaustini.minecraftrpg.events.JoinEvent;
import br.com.rafaelfaustini.minecraftrpg.events.SkillEvent;
import br.com.rafaelfaustini.minecraftrpg.events.TreeEvent;
import br.com.rafaelfaustini.minecraftrpg.model.UserEntity;
import br.com.rafaelfaustini.minecraftrpg.service.UserService;

public class MinecraftRpg extends JavaPlugin {

    @Override
    public void onEnable() {
        configure();
        registerEvents();
        registerCommands();
        registerPlayers();
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
        pluginManager.registerEvents(new TreeEvent(), this);
        pluginManager.registerEvents(new SkillEvent(), this);
    }

    private void registerCommands() {
        getCommand("class").setExecutor(new ClassCommand());
        getCommand("tree").setExecutor(new TreeCommand());
    }

    private void registerPlayers() {
        for (Player player : MinecraftRpg.getPlugin(MinecraftRpg.class).getServer().getOnlinePlayers()) {
            insertPlayer(player);
        }
    }

    private void insertPlayer(Player player) {
        String playerUUID = player.getUniqueId().toString();
        String playerName = player.getName();

        UserService userService = new UserService();
        UserEntity userEntity = userService.get(playerUUID);

        if (userEntity == null) {
            userEntity = new UserEntity(playerUUID, playerName);

            userService.insert(userEntity);
        } else if (!userEntity.getLastAccountName().equals(playerName)) { // IF DISPLAYNAME CHANGED UPDATE
            userEntity = new UserEntity(playerUUID, playerName);

            userService.update(userEntity);
        }
    }
}
