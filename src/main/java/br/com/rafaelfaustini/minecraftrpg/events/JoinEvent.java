package br.com.rafaelfaustini.minecraftrpg.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import br.com.rafaelfaustini.minecraftrpg.MinecraftRpg;
import br.com.rafaelfaustini.minecraftrpg.config.ConfigurationProvider;
import br.com.rafaelfaustini.minecraftrpg.config.model.MessageConfig;
import br.com.rafaelfaustini.minecraftrpg.model.UserEntity;
import br.com.rafaelfaustini.minecraftrpg.service.UserService;
import br.com.rafaelfaustini.minecraftrpg.utils.TextUtil;

public class JoinEvent implements Listener {
    private final MessageConfig messageConfig;
    private final UserService userService;

    public JoinEvent() {
        messageConfig = ConfigurationProvider.getMessageConfig();
        userService = new UserService();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.sendMessage(ChatColor.LIGHT_PURPLE + "-------------------------------");
        player.sendMessage(ChatColor.LIGHT_PURPLE + MinecraftRpg.getPlugin(MinecraftRpg.class).getName().toUpperCase()
                + " VERSION 1.0");
        player.sendMessage(TextUtil.coloredText(messageConfig.getWelcome()));
        player.sendMessage(ChatColor.LIGHT_PURPLE + "-------------------------------");

        insertPlayer(player);
    }

    private void insertPlayer(Player player) {
        String playerUUID = player.getUniqueId().toString();
        String playerName = player.getName();

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
