package br.com.rafaelfaustini.minecraftrpg;

import java.sql.Connection;

import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.rafaelfaustini.minecraftrpg.commands.ClassCommand;
import br.com.rafaelfaustini.minecraftrpg.config.ConfigurationProvider;
import br.com.rafaelfaustini.minecraftrpg.config.CustomConfig;
import br.com.rafaelfaustini.minecraftrpg.dao.ClassDAO;
import br.com.rafaelfaustini.minecraftrpg.dao.SqliteConnection;
import br.com.rafaelfaustini.minecraftrpg.dao.UserClassDAO;
import br.com.rafaelfaustini.minecraftrpg.dao.UserDAO;
import br.com.rafaelfaustini.minecraftrpg.events.ClassEvent;
import br.com.rafaelfaustini.minecraftrpg.events.JoinEvent;
import br.com.rafaelfaustini.minecraftrpg.utils.LoggingUtil;

public class MinecraftRpg extends JavaPlugin {

    @Override
    public void onEnable() {
        loadConfigurations();
        registerEvents();
        registerCommands();
        initDatabase();
    }

    private void loadConfigurations() {
        ConfigurationProvider.loadMessageConfig(new CustomConfig("messages.yml"));
        ConfigurationProvider.loadGuiConfig(new CustomConfig("gui.yml"));
    }

    private void registerEvents() {
        Server server = getServer();
        PluginManager pluginManager = server.getPluginManager();

        pluginManager.registerEvents(new JoinEvent(), this);
        pluginManager.registerEvents(new ClassEvent(), this);
    }

    private void registerCommands() {
        getCommand("class").setExecutor(new ClassCommand());
    }

    private void initDatabase() {
        SqliteConnection sql = new SqliteConnection();

        try {
            Connection con = sql.openConnection();
            UserDAO userDAO = new UserDAO(con);
            ClassDAO classDAO = new ClassDAO(con);
            UserClassDAO userClassDAO = new UserClassDAO(con);

            userDAO.createTable();
            classDAO.createTable();
            userClassDAO.createTable();

            classDAO.fillTable();
        } catch (Exception e) {
            LoggingUtil.error("Error initializing database", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }
    }
}
