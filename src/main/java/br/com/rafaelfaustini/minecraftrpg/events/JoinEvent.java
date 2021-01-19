package br.com.rafaelfaustini.minecraftrpg.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import br.com.rafaelfaustini.minecraftrpg.MinecraftRpg;
import br.com.rafaelfaustini.minecraftrpg.config.ConfigurationProvider;
import br.com.rafaelfaustini.minecraftrpg.utils.TextUtil;

public class JoinEvent implements Listener {
    private static final String WELCOME_CONFIG_STRING = "Events.welcome";

    private final String welcomeMessage;

    public JoinEvent() {
        welcomeMessage = ConfigurationProvider.getMessagesConfig().get(WELCOME_CONFIG_STRING);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.sendMessage(ChatColor.LIGHT_PURPLE + "-------------------------------");
        player.sendMessage(ChatColor.LIGHT_PURPLE + MinecraftRpg.getPlugin(MinecraftRpg.class).getName().toUpperCase()
                + " VERSION 1.0");
        player.sendMessage(TextUtil.coloredText(welcomeMessage));
        player.sendMessage(ChatColor.LIGHT_PURPLE + "-------------------------------");
    }
}
