package br.com.rafaelfaustini.minecraftrpg.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import br.com.rafaelfaustini.minecraftrpg.config.ConfigurationProvider;
import br.com.rafaelfaustini.minecraftrpg.config.GuiConfig;
import br.com.rafaelfaustini.minecraftrpg.config.GuiItemConfig;
import br.com.rafaelfaustini.minecraftrpg.config.MessageConfig;
import br.com.rafaelfaustini.minecraftrpg.enums.ActiveSkillEnum;
import br.com.rafaelfaustini.minecraftrpg.utils.GuiUtil;
import br.com.rafaelfaustini.minecraftrpg.utils.TextUtil;

public class SkillEvent implements Listener {

    private final MessageConfig messageConfig;
    private final GuiConfig guiActiveSkillConfig;
    private final List<Material> castItems = new ArrayList<>();

    public SkillEvent() {
        messageConfig = ConfigurationProvider.getMessageConfig();
        guiActiveSkillConfig = ConfigurationProvider.getActiveSkillGuiConfig();

        castItems.add(Material.WOODEN_AXE);
        castItems.add(Material.WOODEN_SWORD);
        castItems.add(Material.MUSIC_DISC_CAT);
        castItems.add(Material.BONE);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        InventoryView view = event.getView();

        if (view.getTitle().equals(guiActiveSkillConfig.getGuiTitle())) {
            for (GuiItemConfig itemConfig : guiActiveSkillConfig.getGuiItems()) {
                ItemStack eventItem = event.getCurrentItem();

                if (eventItem != null && eventItem.getType().equals(Material.getMaterial(itemConfig.getMaterial()))) {
                    Player player = (Player) event.getWhoClicked();
                    ActiveSkillEnum activeSkill = ActiveSkillEnum.fromString(itemConfig.getKey());

                    if (registerUserActiveSKill(player, activeSkill)) {
                        sendConfirmationMessage(player, itemConfig);
                        closeView(view);
                    }

                    break;
                }
            }

            cancelEvent(event);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack itemStack = event.getItem();

        if (holdsAnItem(itemStack) && castItems.contains(itemStack.getType()) && !hitABlock(action)) {
            if (wasARightClick(action)) {
                openSkillSelectionGui(player);
            } else {
                castCurrentActiveSkill(player);
            }

            cancelEvent(event);
        }
    }

    private boolean registerUserActiveSKill(Player player, ActiveSkillEnum activeSkill) {
        return true;
    }

    private void sendConfirmationMessage(Player player, GuiItemConfig itemConfig) {
        String message = String.format(messageConfig.getSkillChoice(), itemConfig.getDisplayName());

        player.sendMessage(TextUtil.coloredText(message));
    }

    private void cancelEvent(Cancellable event) {
        event.setCancelled(true);
    }

    private void closeView(InventoryView view) {
        view.close();
    }

    private void openSkillSelectionGui(Player player) {
        List<GuiItemConfig> guiItems = guiActiveSkillConfig.getGuiItems();
        List<ItemStack> items = new ArrayList<>();

        for (GuiItemConfig guiItem : guiItems) {
            ItemStack item = GuiUtil.getSimpleItem(guiItem.getDisplayName(), guiItem.getMaterial(), guiItem.getLore());

            items.add(item);
        }

        Inventory gui = Bukkit.createInventory(player, ((guiItems.size() / 9) + 1) * 9,
                guiActiveSkillConfig.getGuiTitle());

        gui.setContents(items.toArray(new ItemStack[0]));

        player.openInventory(gui);
    }

    private void castCurrentActiveSkill(Player player) {
        String message = String.format(messageConfig.getSkillCast(), "&aFireball"); // fixed value for testing only

        player.sendMessage(TextUtil.coloredText(message));
    }

    private boolean holdsAnItem(ItemStack itemStack) {
        return itemStack != null;
    }

    private boolean hitABlock(Action action) {
        return action == Action.PHYSICAL || action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK;
    }

    private boolean wasARightClick(Action action) {
        return action == Action.RIGHT_CLICK_AIR;
    }
}
