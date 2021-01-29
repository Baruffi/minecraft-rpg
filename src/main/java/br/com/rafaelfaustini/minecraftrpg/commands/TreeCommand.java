package br.com.rafaelfaustini.minecraftrpg.commands;

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
import br.com.rafaelfaustini.minecraftrpg.enums.SkillStatusEnum;
import br.com.rafaelfaustini.minecraftrpg.model.ItemEntity;
import br.com.rafaelfaustini.minecraftrpg.model.SkillEntity;
import br.com.rafaelfaustini.minecraftrpg.model.UserEntity;
import br.com.rafaelfaustini.minecraftrpg.model.UserSkillEntity;
import br.com.rafaelfaustini.minecraftrpg.service.SkillService;
import br.com.rafaelfaustini.minecraftrpg.service.UserService;
import br.com.rafaelfaustini.minecraftrpg.utils.GuiUtil;

public class TreeCommand implements CommandExecutor {

    private final GuiConfig guiSkillTreeConfig;

    private final UserService userService;
    private final SkillService skillService;

    public TreeCommand() {
        guiSkillTreeConfig = ConfigurationProvider.getSkillTreeGuiConfig();

        userService = new UserService();
        skillService = new SkillService();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String playerUUID = player.getUniqueId().toString();

            if (!userService.get(playerUUID).getUserSkillList().isEmpty()) {
                openSkillTreeInventory(player);
            }
        }

        return true;
    }

    private void openSkillTreeInventory(Player player) { // TODO: rework this
        Inventory gui = Bukkit.createInventory(player, 45, guiSkillTreeConfig.getGuiTitle());

        List<GuiItemConfig> guiItems = guiSkillTreeConfig.getGuiItems();

        for (GuiItemConfig guiItem : guiItems) {
            ItemStack item = GuiUtil.getSimpleItem(guiItem.getDisplayName(), guiItem.getMaterial(), guiItem.getLore());

            if (guiItem.getKey().equals("scrollLeft")) {
                gui.setItem(18, item);
            } else {
                gui.setItem(26, item);
            }
        }

        String playerUUID = player.getUniqueId().toString();
        UserEntity user = userService.get(playerUUID);

        for (UserSkillEntity userSkill : user.getUserSkillList()) {
            SkillEntity skill = skillService.get(userSkill.getSkillId());

            ItemEntity itemEntity = skill.getItem();

            String displayName = String.format("%s &d(%s)", itemEntity.getDisplayName(),
                    SkillStatusEnum.fromInteger(userSkill.getStatus()).toString());

            ItemStack item = GuiUtil.getSimpleItem(displayName, itemEntity.getMaterial(), itemEntity.getLore());

            gui.addItem(item);
        }

        player.openInventory(gui);
    }
}
