package br.com.rafaelfaustini.minecraftrpg;

import java.sql.Connection;

import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.rafaelfaustini.minecraftrpg.commands.ClassCommand;
import br.com.rafaelfaustini.minecraftrpg.config.ConfigurationProvider;
import br.com.rafaelfaustini.minecraftrpg.config.CustomConfig;
import br.com.rafaelfaustini.minecraftrpg.dao.PlayerDAO;
import br.com.rafaelfaustini.minecraftrpg.dao.SqliteConnection;
import br.com.rafaelfaustini.minecraftrpg.events.ClickEvent;
import br.com.rafaelfaustini.minecraftrpg.events.JoinEvent;
import br.com.rafaelfaustini.minecraftrpg.utils.LoggingUtil;

public class MinecraftRpg extends JavaPlugin {

    @Override
    public void onEnable() {
        loadConfigurations();
        registerEvents();
        registerCommands();
        testDatabase();
    }


    private void loadConfigurations() {
        ConfigurationProvider.loadMessageConfig(new CustomConfig("messages.yml"));
        ConfigurationProvider.loadGuiConfig(new CustomConfig("gui.yml"));
    }

    private void registerEvents() {
        Server server = getServer();
        PluginManager pluginManager = server.getPluginManager();

        pluginManager.registerEvents(new JoinEvent(), this);
        pluginManager.registerEvents(new ClickEvent(), this);
    }

    private void registerCommands() {
        getCommand("class").setExecutor(new ClassCommand());
    }

    private void testDatabase(){
        SqliteConnection sqliteConnection = new SqliteConnection();
        try {
            Connection con = sqliteConnection.openConnection();
            PlayerDAO playerDao = new PlayerDAO(con);
            sqliteConnection.close();

        } catch (Exception e) {
            LoggingUtil.error("Database Open Error", e);
            //TODO: handle exception
        }
    }
}
