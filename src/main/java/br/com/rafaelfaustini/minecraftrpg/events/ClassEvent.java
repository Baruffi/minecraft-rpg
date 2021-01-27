package br.com.rafaelfaustini.minecraftrpg.events;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import br.com.rafaelfaustini.minecraftrpg.config.ConfigurationProvider;
import br.com.rafaelfaustini.minecraftrpg.config.GuiConfig;
import br.com.rafaelfaustini.minecraftrpg.config.GuiItemConfig;
import br.com.rafaelfaustini.minecraftrpg.config.MessageConfig;
import br.com.rafaelfaustini.minecraftrpg.enums.ClassEnum;
import br.com.rafaelfaustini.minecraftrpg.model.ClassEntity;
import br.com.rafaelfaustini.minecraftrpg.model.SkillEntity;
import br.com.rafaelfaustini.minecraftrpg.model.UserEntity;
import br.com.rafaelfaustini.minecraftrpg.service.ClassService;
import br.com.rafaelfaustini.minecraftrpg.service.UserService;
import br.com.rafaelfaustini.minecraftrpg.utils.TextUtil;

public class ClassEvent implements Listener {

    private final MessageConfig messageConfig;
    private final GuiConfig guiClassConfig;
    private final UserService userService;
    private final ClassService classService;

    public ClassEvent() {
        messageConfig = ConfigurationProvider.getMessageConfig();
        guiClassConfig = ConfigurationProvider.getClassGuiConfig();
        userService = new UserService();
        classService = new ClassService();
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        InventoryView view = event.getView();

        if (view.getTitle().equals(guiClassConfig.getGuiTitle())) {
            List<GuiItemConfig> guiItems = guiClassConfig.getGuiItems();

            for (GuiItemConfig guiItem : guiItems) {
                ItemStack eventItem = event.getCurrentItem();

                if (eventItem != null && eventItem.getType().equals(Material.getMaterial(guiItem.getMaterial()))) {
                    Player player = (Player) event.getWhoClicked();
                    ClassEnum selectedClass = ClassEnum.fromString(guiItem.getKey());

                    if (registerNewUserClass(player, guiItem.getKey())) {
                        awardClassItems(player, selectedClass);
                        sendConfirmationMessage(player, guiItem);
                        closeView(view);
                    } else {
                        sendAlreadyChosenMessage(player, guiItem);
                    }

                    break;
                }
            }

            cancelEvent(event);
        }
    }

    private Boolean registerNewUserClass(Player player, String className) {
        String playerUUID = player.getUniqueId().toString();

        UserEntity user = userService.get(playerUUID);
        ClassEntity classEntity = classService.getByName(className);

        if (userDoesntHaveClass(user, classEntity)) {
            userService.addUserClass(playerUUID, classEntity.getId());

            for (SkillEntity skill : classEntity.getSkills()) {
                userService.addUserSkill(playerUUID, skill.getId());
            }

            return true;
        } else {
            return false;
        }
    }

    private boolean userDoesntHaveClass(UserEntity user, ClassEntity classEntity) {
        return user.getClasses().stream().noneMatch(userClass -> userClass.getName().equals(classEntity.getName()));
    }

    private void awardClassItems(Player player, ClassEnum selectedClass) {
        Inventory playerInventory = player.getInventory();

        switch (selectedClass) {
            case WARRIOR:
                playerInventory.addItem(new ItemStack(Material.WOODEN_AXE),
                        new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                break;
            case MAGE:
                playerInventory.addItem(new ItemStack(Material.WRITABLE_BOOK),
                        new ItemStack(Material.ENCHANTING_TABLE));
                break;
            case ROGUE:
                playerInventory.addItem(new ItemStack(Material.WOODEN_SWORD), new ItemStack(Material.LEATHER_BOOTS));
                break;
            case DRUID:
                playerInventory.addItem(new ItemStack(Material.BONE, 10), new ItemStack(Material.SADDLE));
                break;
            case ALCHEMIST:
                playerInventory.addItem(new ItemStack(Material.GLASS_BOTTLE, 8), new ItemStack(Material.BREWING_STAND));
                break;
            case BARD:
                playerInventory.addItem(new ItemStack(Material.JUKEBOX), new ItemStack(Material.MUSIC_DISC_CAT));
                break;
            default:
                break;
        }
    }

    private void sendConfirmationMessage(Player player, GuiItemConfig itemConfig) {
        String message = String.format(messageConfig.getClassChoice(), itemConfig.getDisplayName());

        player.sendMessage(TextUtil.coloredText(message));
    }

    private void sendAlreadyChosenMessage(Player player, GuiItemConfig itemConfig) {
        String message = String.format(messageConfig.getClassAlreadyChosen(), itemConfig.getDisplayName());

        player.sendMessage(TextUtil.coloredText(message));
    }

    private void cancelEvent(Cancellable event) {
        event.setCancelled(true);
    }

    private void closeView(InventoryView view) {
        view.close();
    }
}
