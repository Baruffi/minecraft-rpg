package br.com.rafaelfaustini.minecraftrpg.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CastEvent implements Listener {

    private final List<Material> castItems = new ArrayList<>();

    public CastEvent() {
        castItems.add(Material.WOODEN_AXE);
        castItems.add(Material.WOODEN_SWORD);
        castItems.add(Material.MUSIC_DISC_CAT);
        castItems.add(Material.BONE);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack itemStack = event.getItem();

        if (holdsAnItem(itemStack) && castItems.contains(itemStack.getType()) && wasARightClick(action)) {
            player.sendMessage(itemStack.getType().toString());

            event.setCancelled(true);
        }
    }

    private boolean holdsAnItem(ItemStack itemStack) {
        return itemStack != null;
    }

    private boolean wasARightClick(Action action) {
        return action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK;
    }
}
