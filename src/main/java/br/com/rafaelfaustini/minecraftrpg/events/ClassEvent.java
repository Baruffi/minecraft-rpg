package br.com.rafaelfaustini.minecraftrpg.events;

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
import br.com.rafaelfaustini.minecraftrpg.model.UserClassEntity;
import br.com.rafaelfaustini.minecraftrpg.service.UserClassService;
import br.com.rafaelfaustini.minecraftrpg.utils.TextUtil;

public class ClassEvent implements Listener {

    private final MessageConfig messageConfig;
    private final GuiConfig guiClassConfig;
    private final UserClassService userClassService;

    public ClassEvent() {
        messageConfig = ConfigurationProvider.getMessageConfig();
        guiClassConfig = ConfigurationProvider.getClassGuiConfig();
        userClassService = new UserClassService();
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        InventoryView view = event.getView();

        if (view.getTitle().equals(guiClassConfig.getGuiTitle())) {
            for (GuiItemConfig itemConfig : guiClassConfig.getGuiItems()) {
                ItemStack eventItem = event.getCurrentItem();

                if (eventItem != null && eventItem.getType().equals(Material.getMaterial(itemConfig.getMaterial()))) {
                    Player player = (Player) event.getWhoClicked();
                    ClassEnum selectedClass = ClassEnum.fromString(itemConfig.getKey());

                    if (registerNewUserClass(player, selectedClass)) {
                        awardClassItems(player, selectedClass);
                        sendConfirmationMessage(player, itemConfig);
                        closeView(view);
                    } else {
                        sendAlreadyChosenMessage(player, itemConfig);
                    }

                    break;
                }
            }

            cancelEvent(event);
        }
    }

    private Boolean registerNewUserClass(Player player, ClassEnum selectedClass) {
        String playerUUID = player.getUniqueId().toString();
        Long classId = Long.valueOf(selectedClass.ordinal() + 1); // Could be better

        UserClassEntity userClassEntity = userClassService.get(playerUUID, classId);

        if (userClassEntity == null) {
            userClassEntity = new UserClassEntity(playerUUID, classId);

            userClassService.insert(userClassEntity);

            return true;
        } else {
            return false;
        }
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
