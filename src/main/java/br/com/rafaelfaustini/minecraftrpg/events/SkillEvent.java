package br.com.rafaelfaustini.minecraftrpg.events;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import br.com.rafaelfaustini.minecraftrpg.enums.SkillTypeEnum;
import br.com.rafaelfaustini.minecraftrpg.model.ItemEntity;
import br.com.rafaelfaustini.minecraftrpg.model.SkillEntity;
import br.com.rafaelfaustini.minecraftrpg.model.UserEntity;
import br.com.rafaelfaustini.minecraftrpg.model.UserSkillEntity;
import br.com.rafaelfaustini.minecraftrpg.service.SkillService;
import br.com.rafaelfaustini.minecraftrpg.service.UserService;
import br.com.rafaelfaustini.minecraftrpg.utils.GuiUtil;
import br.com.rafaelfaustini.minecraftrpg.utils.TextUtil;
import br.com.rafaelfaustini.minecraftrpg.utils.TimeUtil;

public class SkillEvent implements Listener {

    private final MessageConfig messageConfig;
    private final GuiConfig guiSkillConfig;
    private final GuiConfig guiActiveSkillConfig;
    private final GuiConfig guiPassiveSkillConfig;

    private final UserService userService;
    private final SkillService skillService;

    private final List<Material> castItems = new ArrayList<>();

    public SkillEvent() {
        messageConfig = ConfigurationProvider.getMessageConfig();
        guiSkillConfig = ConfigurationProvider.getSkillGuiConfig();
        guiActiveSkillConfig = ConfigurationProvider.getActiveSkillGuiConfig();
        guiPassiveSkillConfig = ConfigurationProvider.getPassiveSkillGuiConfig();

        userService = new UserService();
        skillService = new SkillService();

        castItems.add(Material.WOODEN_AXE);
        castItems.add(Material.WOODEN_HOE);
        castItems.add(Material.WOODEN_PICKAXE);
        castItems.add(Material.WOODEN_SHOVEL);
        castItems.add(Material.WOODEN_SWORD);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack itemStack = event.getItem();

        if (isValidSkillInteraction(action, itemStack, player)) {
            if (wasARightClick(action)) {
                openSkillTypeSelectionGui(player);
            } else {
                castCurrentActiveSkills(player);
            }

            cancelEvent(event);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        InventoryView view = event.getView();
        List<GuiItemConfig> guiItems = null;

        if (view.getTitle().equals(guiSkillConfig.getGuiTitle())) {
            guiItems = guiSkillConfig.getGuiItems();

            handleSkillTypeSelection(event, view, guiItems);
        } else {
            if (view.getTitle().equals(guiActiveSkillConfig.getGuiTitle())) {
                guiItems = guiActiveSkillConfig.getGuiItems();
            } else if (view.getTitle().equals(guiPassiveSkillConfig.getGuiTitle())) {
                guiItems = guiPassiveSkillConfig.getGuiItems();
            }

            if (guiItems != null) {
                handleSkillSelection(event, view, guiItems);
            }
        }
    }

    private void handleSkillTypeSelection(InventoryClickEvent event, InventoryView view, List<GuiItemConfig> guiItems) {
        for (GuiItemConfig guiItem : guiItems) {
            ItemStack eventItem = event.getCurrentItem();

            if (eventItemWasClicked(guiItem, eventItem)) {
                Player player = (Player) event.getWhoClicked();

                cancelEvent(event);
                openSkillSelectionGui(player, SkillTypeEnum.fromString(guiItem.getKey()));

                break;
            }
        }
    }

    private void handleSkillSelection(InventoryClickEvent event, InventoryView view, List<GuiItemConfig> guiItems) {
        for (GuiItemConfig guiItem : guiItems) {
            ItemStack eventItem = event.getCurrentItem();

            if (eventItemWasClicked(guiItem, eventItem)) {
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

    private boolean eventItemWasClicked(GuiItemConfig guiItem, ItemStack eventItem) {
        return eventItem != null && eventItem.getType().equals(Material.getMaterial(guiItem.getMaterial()));
    }

    private boolean isValidSkillInteraction(Action action, ItemStack itemStack, Player player) {
        String playerUUID = player.getUniqueId().toString();
        UserEntity user = userService.get(playerUUID);

        return holdsAnItem(itemStack) && castItems.contains(itemStack.getType()) && didNotHitABlock(action)
                && isNotAnInventoryClick(player) && userHasObtainedSkill(user);
    }

    private boolean isNotAnInventoryClick(Player player) {
        return player.getOpenInventory().getTitle().equals("Crafting"); // TODO: improve this
    }

    private boolean userHasObtainedSkill(UserEntity user) {
        return !user.getUserSkillList().stream()
                .filter(userSkill -> userSkill.getStatus() != SkillStatusEnum.UNOBTAINED.getStatusValue())
                .collect(Collectors.toList()).isEmpty(); // TODO: improve this
    }

    private boolean registerUserActiveSkill(Player player, String skillName) {
        String playerUUID = player.getUniqueId().toString();

        UserEntity user = userService.get(playerUUID);
        SkillEntity skill = skillService.getByName(skillName);

        if (userHasSkill(user, skill)) {
            Integer status = getUserSkillStatus(user, skill);

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

    private Integer getUserSkillStatus(UserEntity user, SkillEntity skill) {
        return user.getUserSkillList().stream().filter(userSkill -> userSkill.getSkillId().equals(skill.getId()))
                .findFirst().get().getStatus(); // TODO: improve this
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

    private void openSkillTypeSelectionGui(Player player) {
        List<ItemStack> items = new ArrayList<>();

        for (GuiItemConfig guiItem : guiSkillConfig.getGuiItems()) {
            ItemStack item = GuiUtil.getSimpleItem(guiItem.getDisplayName(), guiItem.getMaterial(), guiItem.getLore());

            items.add(item);
        }

        Inventory gui = Bukkit.createInventory(player, ((items.size() / 9) + 1) * 9, guiSkillConfig.getGuiTitle());

        gui.setContents(items.toArray(new ItemStack[0]));

        player.openInventory(gui);
    }

    private void openSkillSelectionGui(Player player, SkillTypeEnum type) {
        List<ItemStack> items = new ArrayList<>();

        String playerUUID = player.getUniqueId().toString();
        UserEntity user = userService.get(playerUUID);

        for (UserSkillEntity userSkill : user.getUserSkillList()) {
            if (userSkill.getStatus() != SkillStatusEnum.UNOBTAINED.getStatusValue()) {
                SkillEntity skill = skillService.get(userSkill.getSkillId());
                if (skill.getType().equals(type.getTypeValue())) {
                    ItemEntity guiItem = skill.getItem();
                    String displayName = String.format("%s &d(%s)", guiItem.getDisplayName(),
                            SkillStatusEnum.fromInteger(userSkill.getStatus()).toString());
                    ItemStack item = GuiUtil.getSimpleItem(displayName, guiItem.getMaterial(), guiItem.getLore());

                    items.add(item);
                }
            }
        }

        GuiConfig skillSelectionGui = null;

        if (type == SkillTypeEnum.ACTIVE) {
            skillSelectionGui = guiActiveSkillConfig;
        } else {
            skillSelectionGui = guiPassiveSkillConfig;
        }

        Inventory gui = Bukkit.createInventory(player, ((items.size() / 9) + 1) * 9, skillSelectionGui.getGuiTitle());

        gui.setContents(items.toArray(new ItemStack[0]));

        player.openInventory(gui);
    }

    private void castCurrentActiveSkills(Player player) {
        String playerUUID = player.getUniqueId().toString();
        UserEntity user = userService.get(playerUUID);

        for (UserSkillEntity userSkill : user.getUserSkillList()) {
            SkillEntity skill = skillService.get(userSkill.getSkillId());

            Integer skillType = skill.getType();
            Integer skillStatus = userSkill.getStatus();

            if (skillType == SkillTypeEnum.ACTIVE.getTypeValue()
                    && skillStatus == SkillStatusEnum.ACTIVE.getStatusValue()) {
                if (registerSkillCooldown(userSkill)) {
                    castSkill(player, skill);
                    sendCastMessage(player, skill);
                } else {
                    sendCooldownMessage(player, skill, userSkill);
                }
            }
        }
    }

    private boolean registerSkillCooldown(UserSkillEntity userSkill) {
        Long cooldownTimestamp = userSkill.getCooldownUntil();

        if (cooldownTimestamp < TimeUtil.getCurrentTime()) {
            userService.updateSkillCooldown(userSkill.getUserUUID(), userSkill.getSkillId(), 10);

            return true;
        } else {
            return false;
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

    private void sendCooldownMessage(Player player, SkillEntity skill, UserSkillEntity userSkill) {
        String message = String.format(messageConfig.getSkillCooldown(), skill.getItem().getDisplayName(),
                TimeUtil.toSeconds(userSkill.getCooldownUntil() - TimeUtil.getCurrentTime()));

        player.sendMessage(TextUtil.coloredText(message));
    }

    private boolean holdsAnItem(ItemStack itemStack) {
        return itemStack != null;
    }

    private boolean didNotHitABlock(Action action) {
        return !(action == Action.PHYSICAL || action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK);
    }

    private boolean wasARightClick(Action action) {
        return action == Action.RIGHT_CLICK_AIR;
    }
}
