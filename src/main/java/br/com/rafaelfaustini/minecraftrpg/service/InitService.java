package br.com.rafaelfaustini.minecraftrpg.service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.rafaelfaustini.minecraftrpg.config.GuiConfig;
import br.com.rafaelfaustini.minecraftrpg.config.GuiItemConfig;
import br.com.rafaelfaustini.minecraftrpg.dao.ClassDAO;
import br.com.rafaelfaustini.minecraftrpg.dao.ClassSkillDAO;
import br.com.rafaelfaustini.minecraftrpg.dao.ItemDAO;
import br.com.rafaelfaustini.minecraftrpg.dao.LoreDAO;
import br.com.rafaelfaustini.minecraftrpg.dao.SkillDAO;
import br.com.rafaelfaustini.minecraftrpg.dao.SqliteConnection;
import br.com.rafaelfaustini.minecraftrpg.dao.UserClassDAO;
import br.com.rafaelfaustini.minecraftrpg.dao.UserDAO;
import br.com.rafaelfaustini.minecraftrpg.dao.UserSkillDAO;
import br.com.rafaelfaustini.minecraftrpg.enums.ActiveSkillEnum;
import br.com.rafaelfaustini.minecraftrpg.enums.ClassEnum;
import br.com.rafaelfaustini.minecraftrpg.enums.PassiveSkillEnum;
import br.com.rafaelfaustini.minecraftrpg.enums.SkillTypeEnum;
import br.com.rafaelfaustini.minecraftrpg.model.ClassEntity;
import br.com.rafaelfaustini.minecraftrpg.model.ClassSkillEntity;
import br.com.rafaelfaustini.minecraftrpg.model.ItemEntity;
import br.com.rafaelfaustini.minecraftrpg.model.LoreEntity;
import br.com.rafaelfaustini.minecraftrpg.model.SkillEntity;
import br.com.rafaelfaustini.minecraftrpg.utils.LoggingUtil;

public class InitService {

    public void createTables() {
        SqliteConnection sql = new SqliteConnection();

        try {
            Connection con = sql.openConnection();

            UserDAO userDAO = new UserDAO(con);
            ClassDAO classDAO = new ClassDAO(con);
            SkillDAO skillDAO = new SkillDAO(con);
            UserClassDAO userClassDAO = new UserClassDAO(con);
            UserSkillDAO userSkillDAO = new UserSkillDAO(con);
            ClassSkillDAO classSkillDAO = new ClassSkillDAO(con);
            ItemDAO itemDAO = new ItemDAO(con);
            LoreDAO loreDAO = new LoreDAO(con);

            userDAO.createTable();
            classDAO.createTable();
            skillDAO.createTable();
            userClassDAO.createTable();
            userSkillDAO.createTable();
            classSkillDAO.createTable();
            itemDAO.createTable();
            loreDAO.createTable();
        } catch (Exception e) {
            LoggingUtil.error("Error initializing database", e);
        } finally {
            try {
                sql.close();
            } catch (Exception e) {
            }
        }
    }

    public void fillTables(GuiConfig classGuiConfig, GuiConfig activeSkillGuiConfig, GuiConfig passiveSkillGuiConfig) {
        SqliteConnection sql = new SqliteConnection();

        try {
            Connection con = sql.openConnection();

            ClassDAO classDAO = new ClassDAO(con);
            SkillDAO skillDAO = new SkillDAO(con);
            ClassSkillDAO classSkillDAO = new ClassSkillDAO(con);
            ItemDAO itemDAO = new ItemDAO(con);
            LoreDAO loreDAO = new LoreDAO(con);

            if (databaseIsEmpty(classDAO, skillDAO, classSkillDAO, itemDAO, loreDAO)) {
                Map<String, ClassEntity> classes = fillClassesAndItems(classGuiConfig, classDAO, itemDAO, loreDAO);

                Map<String, SkillEntity> skills = fillSkillsAndItems(activeSkillGuiConfig, passiveSkillGuiConfig,
                        skillDAO, itemDAO, loreDAO);

                relateClassesToSkills(classSkillDAO, classes, skills);
            }
        } catch (Exception e) {
            LoggingUtil.error("Error initializing database", e);
        } finally {
            try {
                sql.close();
            } catch (Exception e) {
            }
        }
    }

    private boolean databaseIsEmpty(ClassDAO classDAO, SkillDAO skillDAO, ClassSkillDAO classSkillDAO, ItemDAO itemDAO,
            LoreDAO loreDAO) throws Exception {
        return classDAO.getAll().isEmpty() && skillDAO.getAll().isEmpty() && classSkillDAO.getAll().isEmpty()
                && itemDAO.getAll().isEmpty() && loreDAO.getAll().isEmpty();
    }

    private void relateClassesToSkills(ClassSkillDAO classSkillDAO, Map<String, ClassEntity> classes,
            Map<String, SkillEntity> skills) throws Exception {
        ClassSkillEntity classSkillEntity = new ClassSkillEntity(classes.get(ClassEnum.MAGE.getClassName()).getId(),
                skills.get(ActiveSkillEnum.FIREBALL.getActiveSkillName()).getId()); // Gives mages the fireball active

        classSkillDAO.insert(classSkillEntity);

        classSkillEntity = new ClassSkillEntity(classes.get(ClassEnum.ROGUE.getClassName()).getId(),
                skills.get(PassiveSkillEnum.SNEAK.getPassiveSkillName()).getId()); // Gives rogues the sneak passive

        classSkillDAO.insert(classSkillEntity);
    }

    private Map<String, SkillEntity> fillSkillsAndItems(GuiConfig activeSkillGuiConfig, GuiConfig passiveSkillGuiConfig,
            SkillDAO skillDAO, ItemDAO itemDAO, LoreDAO loreDAO) throws Exception {
        List<GuiItemConfig> guiActiveSkillItemConfigs = activeSkillGuiConfig.getGuiItems();
        List<GuiItemConfig> guiPassiveSkillItemConfigs = passiveSkillGuiConfig.getGuiItems();

        Map<String, SkillEntity> skills = new HashMap<>();

        for (GuiItemConfig guiItemConfig : guiActiveSkillItemConfigs) {
            ItemEntity item = new ItemEntity(guiItemConfig.getKey(), guiItemConfig.getDisplayName(),
                    guiItemConfig.getMaterial());

            itemDAO.insert(item);

            item = itemDAO.getByName(item.getName());

            for (String loreConfig : guiItemConfig.getLore()) {
                LoreEntity lore = new LoreEntity(loreConfig, item.getId());

                loreDAO.insert(lore);
            }

            SkillEntity skill = new SkillEntity(guiItemConfig.getKey(), SkillTypeEnum.ACTIVE.getTypeValue(),
                    item.getId());

            skillDAO.insert(skill);

            skill = skillDAO.getByName(skill.getName());

            skills.put(skill.getName(), skill);
        }

        for (GuiItemConfig guiItemConfig : guiPassiveSkillItemConfigs) {
            ItemEntity item = new ItemEntity(guiItemConfig.getKey(), guiItemConfig.getDisplayName(),
                    guiItemConfig.getMaterial());

            itemDAO.insert(item);

            item = itemDAO.getByName(item.getName());

            for (String loreConfig : guiItemConfig.getLore()) {
                LoreEntity lore = new LoreEntity(loreConfig, item.getId());

                loreDAO.insert(lore);
            }

            SkillEntity skill = new SkillEntity(guiItemConfig.getKey(), SkillTypeEnum.PASSIVE.getTypeValue(),
                    item.getId());

            skillDAO.insert(skill);

            skill = skillDAO.getByName(skill.getName());

            skills.put(skill.getName(), skill);
        }

        return skills;
    }

    private Map<String, ClassEntity> fillClassesAndItems(GuiConfig classGuiConfig, ClassDAO classDAO, ItemDAO itemDAO,
            LoreDAO loreDAO) throws Exception {
        List<GuiItemConfig> guiClassItemConfigs = classGuiConfig.getGuiItems();
        Map<String, ClassEntity> classes = new HashMap<>();

        for (GuiItemConfig guiItemConfig : guiClassItemConfigs) {
            ItemEntity item = new ItemEntity(guiItemConfig.getKey(), guiItemConfig.getDisplayName(),
                    guiItemConfig.getMaterial());

            itemDAO.insert(item);

            item = itemDAO.getByName(item.getName());

            for (String loreConfig : guiItemConfig.getLore()) {
                LoreEntity lore = new LoreEntity(loreConfig, item.getId());

                loreDAO.insert(lore);
            }

            ClassEntity classEntity = new ClassEntity(guiItemConfig.getKey(), item.getId());

            classDAO.insert(classEntity);

            classEntity = classDAO.getByName(classEntity.getName());

            classes.put(classEntity.getName(), classEntity);
        }
        return classes;
    }
}
