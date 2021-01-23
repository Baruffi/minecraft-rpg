package br.com.rafaelfaustini.minecraftrpg.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
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
import br.com.rafaelfaustini.minecraftrpg.enums.SkillStatusEnum;
import br.com.rafaelfaustini.minecraftrpg.model.SkillEntity;
import br.com.rafaelfaustini.minecraftrpg.model.UserSkillEntity;
import br.com.rafaelfaustini.minecraftrpg.service.SkillService;
import br.com.rafaelfaustini.minecraftrpg.service.UserSkillService;
import br.com.rafaelfaustini.minecraftrpg.utils.GuiUtil;
import br.com.rafaelfaustini.minecraftrpg.utils.TextUtil;

public class SkillEvent implements Listener {

    private final MessageConfig messageConfig;
    private final GuiConfig guiActiveSkillConfig;

    private final UserSkillService userSkillService;
    private final SkillService skillService;

    private final List<Material> castItems = new ArrayList<>();

    public SkillEvent() {
        messageConfig = ConfigurationProvider.getMessageConfig();
        guiActiveSkillConfig = ConfigurationProvider.getActiveSkillGuiConfig();

        userSkillService = new UserSkillService();
        skillService = new SkillService();

        castItems.add(Material.WOODEN_AXE);
        castItems.add(Material.WOODEN_SWORD);
        castItems.add(Material.MUSIC_DISC_CAT);
        castItems.add(Material.BONE);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        InventoryView view = event.getView();

        if (view.getTitle().equals(guiActiveSkillConfig.getGuiTitle())) {
            List<GuiItemConfig> guiItems = guiActiveSkillConfig.getGuiItems();

            for (GuiItemConfig guiItem : guiItems) {
                ItemStack eventItem = event.getCurrentItem();

                if (eventItem != null && eventItem.getType().equals(Material.getMaterial(guiItem.getMaterial()))) {
                    Player player = (Player) event.getWhoClicked();
                    ActiveSkillEnum activeSkill = ActiveSkillEnum.fromString(guiItem.getKey());

                    if (registerUserActiveSkill(player, activeSkill)) {
                        sendConfirmationMessage(player, guiItem);
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

        if (isValidSkillInteraction(action, itemStack, player)) {
            if (wasARightClick(action)) {
                openSkillSelectionGui(player);
            } else {
                castCurrentActiveSkill(player);
            }

            cancelEvent(event);
        }
    }

    private boolean isValidSkillInteraction(Action action, ItemStack itemStack, Player player) {
        String playerUUID = player.getUniqueId().toString();

        return holdsAnItem(itemStack) && castItems.contains(itemStack.getType()) && !hitABlock(action)
                && !userSkillService.getAllByUser(playerUUID).isEmpty();
    }

    private boolean registerUserActiveSkill(Player player, ActiveSkillEnum activeSkill) {
        String playerUUID = player.getUniqueId().toString();
        Long skillId = Long.valueOf(activeSkill.ordinal() + 1);
        UserSkillEntity userSkillEntity = userSkillService.get(playerUUID, skillId);

        if (userSkillEntity == null) {
            return false;
        } else {
            if (hasActiveStatus(userSkillEntity)) {
                userSkillEntity.setStatus(0);
            } else {
                userSkillEntity.setStatus(1);
            }

            // Flips skill from active to inactive
            // and vice-versa; actually allows for
            // multiple skills to be active at
            // once; will probably be changed.

            userSkillService.update(userSkillEntity);

            return true;
        }
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

        String playerUUID = player.getUniqueId().toString();
        List<UserSkillEntity> userSkillEntities = userSkillService.getAllByUser(playerUUID);

        for (GuiItemConfig guiItem : guiItems) {
            for (UserSkillEntity userSkillEntity : userSkillEntities) {
                SkillEntity skillEntity = skillService.get(userSkillEntity.getSkillId());

                if (guiItem.getKey().equals(skillEntity.getName())) { // Matching key to name here, not using id! Might
                                                                      // honestly be better than going through the
                                                                      // ordinal shenanigans
                    String displayName = String.format("%s &d(%s)", guiItem.getDisplayName(),
                            SkillStatusEnum.fromInteger(userSkillEntity.getStatus()).toString());
                    ItemStack item = GuiUtil.getSimpleItem(displayName, guiItem.getMaterial(), guiItem.getLore());

                    items.add(item);

                    break;
                }
            }
        }

        Inventory gui = Bukkit.createInventory(player, ((items.size() / 9) + 1) * 9,
                guiActiveSkillConfig.getGuiTitle());

        gui.setContents(items.toArray(new ItemStack[0]));

        player.openInventory(gui);
    }

    private void castCurrentActiveSkill(Player player) {
        String playerUUID = player.getUniqueId().toString();
        List<UserSkillEntity> userSkillEntities = userSkillService.getAllByUser(playerUUID);

        for (UserSkillEntity userSkillEntity : userSkillEntities) {
            if (hasActiveStatus(userSkillEntity)) {
                SkillEntity skillEntity = skillService.get(userSkillEntity.getSkillId());
                List<GuiItemConfig> guiItems = guiActiveSkillConfig.getGuiItems();

                for (GuiItemConfig guiItem : guiItems) {
                    if (guiItem.getKey().equals(skillEntity.getName())) {
                        castSkill(player, skillEntity);
                        sendCastMessage(player, guiItem);

                        break;
                    }
                }
            }
        }
    }

    private void castSkill(Player player, SkillEntity skillEntity) {
        ActiveSkillEnum activeSkill = ActiveSkillEnum.fromString(skillEntity.getName());

        switch (activeSkill) {
            case FIREBALL:
                player.launchProjectile(Fireball.class);

                break;

            default:
                break;
        }
    }

    private void sendCastMessage(Player player, GuiItemConfig guiItem) {
        String message = String.format(messageConfig.getSkillCast(), guiItem.getDisplayName());

        player.sendMessage(TextUtil.coloredText(message));
    }

    private boolean hasActiveStatus(UserSkillEntity userSkillEntity) {
        Integer status = userSkillEntity.getStatus();

        return status != null && status != 0;
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
