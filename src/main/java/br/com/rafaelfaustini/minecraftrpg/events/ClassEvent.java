package br.com.rafaelfaustini.minecraftrpg.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import br.com.rafaelfaustini.minecraftrpg.config.ConfigurationProvider;
import br.com.rafaelfaustini.minecraftrpg.config.model.GuiConfig;
import br.com.rafaelfaustini.minecraftrpg.config.model.GuiItemConfig;
import br.com.rafaelfaustini.minecraftrpg.config.model.MessageConfig;
import br.com.rafaelfaustini.minecraftrpg.enums.ClassEnum;
import br.com.rafaelfaustini.minecraftrpg.model.UserEntity;
import br.com.rafaelfaustini.minecraftrpg.service.UserService;
import br.com.rafaelfaustini.minecraftrpg.utils.TextUtil;

public class ClassEvent implements Listener {

    private final MessageConfig messageConfig;
    private final GuiConfig guiClassConfig;
    private final UserService userService;

    public ClassEvent() {
        messageConfig = ConfigurationProvider.getMessageConfig();
        guiClassConfig = ConfigurationProvider.getClassGuiConfig();
        userService = new UserService();
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        InventoryView view = event.getView();

        if (view.getTitle().equalsIgnoreCase(guiClassConfig.getGuiTitle())) {
            for (GuiItemConfig itemConfig : guiClassConfig.getGuiItems()) {
                if (event.getCurrentItem().getType().equals(Material.getMaterial(itemConfig.getMaterial()))) {
                    Player player = (Player) event.getWhoClicked();
                    ClassEnum selectedClass = ClassEnum.fromString(itemConfig.getKey());

                    registerOnUserDatabase(player, selectedClass);
                    awardClassItems(player, selectedClass);
                    sendConfirmationMessage(player, itemConfig);

                    closeView(view);
                }
            }

            cancelEvent(event);
        }
    }

    private void registerOnUserDatabase(Player player, ClassEnum selectedClass) {
        String playerUUID = player.getUniqueId().toString();
        UserEntity userEntity = userService.get(playerUUID);
        Long classId = Long.valueOf(selectedClass.ordinal() + 1);

        userEntity.setClassId(classId); // Will need to change for multi-class support
        userService.update(userEntity);
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

    private void cancelEvent(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    private void closeView(InventoryView view) {
        view.close();
    }
}
