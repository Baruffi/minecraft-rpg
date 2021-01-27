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
import br.com.rafaelfaustini.minecraftrpg.model.ItemEntity;
import br.com.rafaelfaustini.minecraftrpg.model.SkillEntity;
import br.com.rafaelfaustini.minecraftrpg.model.UserEntity;
import br.com.rafaelfaustini.minecraftrpg.service.SkillService;
import br.com.rafaelfaustini.minecraftrpg.service.UserService;
import br.com.rafaelfaustini.minecraftrpg.utils.GuiUtil;
import br.com.rafaelfaustini.minecraftrpg.utils.TextUtil;

public class SkillEvent implements Listener {

    private final MessageConfig messageConfig;
    private final GuiConfig guiActiveSkillConfig;

    private final UserService userService;
    private final SkillService skillService;

    private final List<Material> castItems = new ArrayList<>();

    public SkillEvent() {
        messageConfig = ConfigurationProvider.getMessageConfig();
        guiActiveSkillConfig = ConfigurationProvider.getActiveSkillGuiConfig();

        userService = new UserService();
        skillService = new SkillService();

        castItems.add(Material.WOODEN_AXE);
        castItems.add(Material.WOODEN_HOE);
        castItems.add(Material.WOODEN_PICKAXE);
        castItems.add(Material.WOODEN_SHOVEL);
        castItems.add(Material.WOODEN_SWORD);
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

                    if (registerUserActiveSkill(player, guiItem.getKey())) {
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
                castCurrentActiveSkills(player);
            }

            cancelEvent(event);
        }
    }

    private boolean isValidSkillInteraction(Action action, ItemStack itemStack, Player player) {
        String playerUUID = player.getUniqueId().toString();

        return holdsAnItem(itemStack) && castItems.contains(itemStack.getType()) && !hitABlock(action)
                && !userService.get(playerUUID).getSkills().isEmpty();
    }

    private boolean registerUserActiveSkill(Player player, String skillName) {
        String playerUUID = player.getUniqueId().toString();

        UserEntity user = userService.get(playerUUID);
        SkillEntity skill = skillService.getByName(skillName);

        if (userHasSkill(user, skill)) {
            Integer status = user.getSkillStatusMap().get(skill.getName());

            if (status == SkillStatusEnum.ACTIVE.getStatusValue()) {
                status = SkillStatusEnum.INACTIVE.getStatusValue();
            } else {
                status = SkillStatusEnum.ACTIVE.getStatusValue();
            }

            // Flips skill from active to inactive
            // and vice-versa; actually allows for
            // multiple skills to be active at
            // once; will probably be changed.

            userService.updateSkillStatus(playerUUID, skill.getId(), status);

            return true;
        } else {

            return false;
        }
    }

    private boolean userHasSkill(UserEntity user, SkillEntity skill) {
        return user.getSkills().stream().anyMatch(userSkill -> userSkill.getName().equals(skill.getName()));
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
        List<ItemStack> items = new ArrayList<>();

        String playerUUID = player.getUniqueId().toString();
        UserEntity user = userService.get(playerUUID);

        for (SkillEntity skillEntity : user.getSkills()) {
            ItemEntity guiItem = skillEntity.getItem();
            String displayName = String.format("%s &d(%s)", guiItem.getDisplayName(),
                    SkillStatusEnum.fromInteger(user.getSkillStatusMap().get(skillEntity.getName())).toString());
            ItemStack item = GuiUtil.getSimpleItem(displayName, guiItem.getMaterial(), guiItem.getLore());

            items.add(item);
        }

        Inventory gui = Bukkit.createInventory(player, ((items.size() / 9) + 1) * 9,
                guiActiveSkillConfig.getGuiTitle());

        gui.setContents(items.toArray(new ItemStack[0]));

        player.openInventory(gui);
    }

    private void castCurrentActiveSkills(Player player) {
        String playerUUID = player.getUniqueId().toString();
        UserEntity userEntity = userService.get(playerUUID);

        for (String skillName : userEntity.getSkillStatusMap().keySet()) {
            Integer skillStatus = userEntity.getSkillStatusMap().get(skillName);

            if (skillStatus == SkillStatusEnum.ACTIVE.getStatusValue()) {
                SkillEntity skill = skillService.getByName(skillName);

                castSkill(player, skill);
                sendCastMessage(player, skill);
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

    private void sendCastMessage(Player player, SkillEntity skill) {
        String message = String.format(messageConfig.getSkillCast(), skill.getItem().getDisplayName());

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
