package br.com.rafaelfaustini.minecraftrpg;

import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.rafaelfaustini.minecraftrpg.config.CustomConfig;
import br.com.rafaelfaustini.minecraftrpg.events.JoinEvent;

public class MinecraftRpg extends JavaPlugin implements Listener {
    public CustomConfig messagesConfig; // Trocar para Static ?

    @Override
    public void onEnable() {
        Server server = getServer();
        PluginManager pluginManager = server.getPluginManager();
        this.messagesConfig = new CustomConfig("messages.yml");
        pluginManager.registerEvents(new JoinEvent(), this);
    }

}
