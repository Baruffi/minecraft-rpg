package br.com.rafaelfaustini.minecraftrpg.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import br.com.rafaelfaustini.minecraftrpg.MinecraftRpg;
import br.com.rafaelfaustini.minecraftrpg.config.ConfigurationProvider;
import br.com.rafaelfaustini.minecraftrpg.config.model.MessageConfig;
import br.com.rafaelfaustini.minecraftrpg.utils.TextUtil;

public class JoinEvent implements Listener {
    private final MessageConfig messageConfig;

    public JoinEvent() {
        messageConfig = ConfigurationProvider.getMessageConfig();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.sendMessage(ChatColor.LIGHT_PURPLE + "-------------------------------");
        player.sendMessage(ChatColor.LIGHT_PURPLE + MinecraftRpg.getPlugin(MinecraftRpg.class).getName().toUpperCase()
                + " VERSION 1.0");
        player.sendMessage(TextUtil.coloredText(messageConfig.getWelcome()));
        player.sendMessage(ChatColor.LIGHT_PURPLE + "-------------------------------");
    }
}
