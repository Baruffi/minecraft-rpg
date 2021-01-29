package br.com.rafaelfaustini.minecraftrpg.events;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import br.com.rafaelfaustini.minecraftrpg.config.ConfigurationProvider;
import br.com.rafaelfaustini.minecraftrpg.config.GuiConfig;
import br.com.rafaelfaustini.minecraftrpg.config.GuiItemConfig;
import br.com.rafaelfaustini.minecraftrpg.config.MessageConfig;
import br.com.rafaelfaustini.minecraftrpg.enums.SkillStatusEnum;
import br.com.rafaelfaustini.minecraftrpg.model.SkillEntity;
import br.com.rafaelfaustini.minecraftrpg.model.UserEntity;
import br.com.rafaelfaustini.minecraftrpg.service.UserService;
import br.com.rafaelfaustini.minecraftrpg.utils.TextUtil;

public class TreeEvent implements Listener {

    private final MessageConfig messageConfig;
    private final GuiConfig guiSkillTreeConfig;

    private final UserService userService;

    public TreeEvent() {
        messageConfig = ConfigurationProvider.getMessageConfig();
        guiSkillTreeConfig = ConfigurationProvider.getSkillTreeGuiConfig();

        userService = new UserService();
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        InventoryView view = event.getView();
        List<GuiItemConfig> guiItems = null;

        if (view.getTitle().equals(guiSkillTreeConfig.getGuiTitle())) {
            guiItems = guiSkillTreeConfig.getGuiItems();

            handleSkillTreeInteraction(event, view, guiItems);
        }
    }

    private void handleSkillTreeInteraction(InventoryClickEvent event, InventoryView view,
            List<GuiItemConfig> guiItems) {
        Player player = (Player) event.getWhoClicked();
        String playerUUID = player.getUniqueId().toString();
        UserEntity user = userService.get(playerUUID);

        for (GuiItemConfig guiItem : guiItems) {
            ItemStack eventItem = event.getCurrentItem();

            if (eventItemWasClicked(guiItem, eventItem)) {
                break;
            }
        }

        for (SkillEntity skill : user.getSkills()) {
            ItemStack eventItem = event.getCurrentItem();

            if (skillItemWasClicked(skill, eventItem)) {
                if (registerUserSkillStatus(user, skill)) {
                    sendConfirmationMessage(player, skill);
                    closeView(view);
                }

                break;
            }
        }

        cancelEvent(event);
    }

    private boolean skillItemWasClicked(SkillEntity skill, ItemStack eventItem) {
        return eventItem != null && eventItem.getType().equals(Material.getMaterial(skill.getItem().getMaterial()));
    }

    private boolean eventItemWasClicked(GuiItemConfig guiItem, ItemStack eventItem) {
        return eventItem != null && eventItem.getType().equals(Material.getMaterial(guiItem.getMaterial()));
    }

    private boolean registerUserSkillStatus(UserEntity user, SkillEntity skill) {
        if (userHasSkill(user, skill)) {
            Integer status = getUserSkillStatus(user, skill);

            if (status == SkillStatusEnum.UNOBTAINED.getStatusValue()) {
                status = SkillStatusEnum.INACTIVE.getStatusValue();

                userService.updateSkillStatus(user.getUUID(), skill.getId(), status);

                return true;
            }
        }

        return false;
    }

    private Integer getUserSkillStatus(UserEntity user, SkillEntity skill) {
        return user.getUserSkillList().stream().filter(userSkill -> userSkill.getSkillId().equals(skill.getId()))
                .findFirst().get().getStatus(); // TODO: fix this
    }

    private boolean userHasSkill(UserEntity user, SkillEntity skill) {
        return user != null && skill != null
                && user.getSkills().stream().anyMatch(userSkill -> userSkill.getName().equals(skill.getName()));
    }

    private void sendConfirmationMessage(Player player, SkillEntity skill) {
        String message = String.format(messageConfig.getSkillObtained(), skill.getItem().getDisplayName());

        player.sendMessage(TextUtil.coloredText(message));
    }

    private void cancelEvent(Cancellable event) {
        event.setCancelled(true);
    }

    private void closeView(InventoryView view) {
        view.close();
    }
}
