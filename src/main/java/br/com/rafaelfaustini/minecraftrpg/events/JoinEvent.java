package br.com.rafaelfaustini.minecraftrpg.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import br.com.rafaelfaustini.minecraftrpg.MinecraftRpg;
import br.com.rafaelfaustini.minecraftrpg.utils.ExceptionUtil;
import br.com.rafaelfaustini.minecraftrpg.utils.TextUtil;

public class JoinEvent implements Listener {
    private static final MinecraftRpg PLUGIN = MinecraftRpg.getPlugin(MinecraftRpg.class);
    private String exceptionMessage;

    public JoinEvent() {
        try {
            exceptionMessage = TextUtil.coloredText(PLUGIN.getConfig().get("Utils.exception").toString()); // Talvez
                                                                                                           // virar um
                                                                                                           // método ?
        } catch (Exception e) {
            ExceptionUtil.tryException(e);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.sendMessage(ChatColor.LIGHT_PURPLE + "-------------------------------");
        player.sendMessage(ChatColor.LIGHT_PURPLE + PLUGIN.getName().toUpperCase() + " version 1.0");
        player.sendMessage(exceptionMessage);
        player.sendMessage(ChatColor.LIGHT_PURPLE + "-------------------------------");

    }

}
