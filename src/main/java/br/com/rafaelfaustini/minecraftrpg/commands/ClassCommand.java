package br.com.rafaelfaustini.minecraftrpg.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import br.com.rafaelfaustini.minecraftrpg.config.ConfigurationProvider;
import br.com.rafaelfaustini.minecraftrpg.config.GuiConfig;
import br.com.rafaelfaustini.minecraftrpg.config.GuiItemConfig;
import br.com.rafaelfaustini.minecraftrpg.config.MessageConfig;
import br.com.rafaelfaustini.minecraftrpg.model.ClassEntity;
import br.com.rafaelfaustini.minecraftrpg.model.UserClassEntity;
import br.com.rafaelfaustini.minecraftrpg.service.ClassService;
import br.com.rafaelfaustini.minecraftrpg.service.UserClassService;
import br.com.rafaelfaustini.minecraftrpg.utils.GuiUtil;
import br.com.rafaelfaustini.minecraftrpg.utils.TextUtil;

public class ClassCommand implements CommandExecutor {

    private static final int MULTI_CLASS_LEVEL = 100;

    private final MessageConfig messageConfig;
    private final GuiConfig guiClassConfig;
    private final ClassService classService;
    private final UserClassService userClassService;

    public ClassCommand() {
        messageConfig = ConfigurationProvider.getMessageConfig();
        guiClassConfig = ConfigurationProvider.getClassGuiConfig();
        classService = new ClassService();
        userClassService = new UserClassService();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("class")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                String playerUUID = player.getUniqueId().toString();
                Integer playerLevel = player.getLevel();

                List<UserClassEntity> userClassEntities = userClassService.getAllByUser(playerUUID);

                if (userClassEntities.isEmpty() || playerLevel > MULTI_CLASS_LEVEL) {
                    openClassChoiceInventory(player);
                } else {
                    for (UserClassEntity userClassEntity : userClassEntities) {
                        Long classId = userClassEntity.getClassId();

                        sendClassIdMessage(player, classId);
                    }
                }
            }
        }

        return true;
    }

    private void openClassChoiceInventory(Player player) {
        Inventory gui = Bukkit.createInventory(player, 9, guiClassConfig.getGuiTitle());

        List<GuiItemConfig> guiItems = guiClassConfig.getGuiItems();
        List<ItemStack> items = new ArrayList<>();

        for (GuiItemConfig guiItem : guiItems) {
            ItemStack item = GuiUtil.getSimpleItem(guiItem.getDisplayName(), guiItem.getMaterial(), guiItem.getLore());

            items.add(item);
        }

        gui.setContents(items.toArray(new ItemStack[0]));

        player.openInventory(gui);
    }

    private void sendClassIdMessage(Player player, Long classId) {
        ClassEntity classEntity = classService.get(classId);

        List<GuiItemConfig> guiItems = guiClassConfig.getGuiItems();
        for (GuiItemConfig guiItem : guiItems) {
            if (guiItem.getKey().equals(classEntity.getName())) {
                String message = String.format(messageConfig.getClassAlreadyChosen(), guiItem.getDisplayName());

                player.sendMessage(TextUtil.coloredText(message));

                break;
            }
        }
    }
}
