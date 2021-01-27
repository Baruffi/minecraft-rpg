package br.com.rafaelfaustini.minecraftrpg.service;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.rafaelfaustini.minecraftrpg.dao.ItemDAO;
import br.com.rafaelfaustini.minecraftrpg.dao.LoreDAO;
import br.com.rafaelfaustini.minecraftrpg.dao.SkillDAO;
import br.com.rafaelfaustini.minecraftrpg.dao.SqliteConnection;
import br.com.rafaelfaustini.minecraftrpg.model.ItemEntity;
import br.com.rafaelfaustini.minecraftrpg.model.LoreEntity;
import br.com.rafaelfaustini.minecraftrpg.model.SkillEntity;
import br.com.rafaelfaustini.minecraftrpg.utils.LoggingUtil;

public class SkillService {

    public SkillEntity get(Long id) {
        SqliteConnection sql = new SqliteConnection();
        SkillEntity skill = null;

        try {
            Connection con = sql.openConnection();
            SkillDAO skillDAO = new SkillDAO(con);

            skill = skillDAO.get(id);

            if (skill != null) {
                ItemEntity item = getSkillItem(skill, con);

                skill.setItem(item);
            }
        } catch (Exception e) {
            LoggingUtil.error("Database Get SkillEntity", e);
        } finally {
            try {
                sql.close();
            } catch (Exception e) {
            }
        }

        return skill;
    }

    public SkillEntity getByName(String name) {
        SqliteConnection sql = new SqliteConnection();
        SkillEntity skill = null;

        try {
            Connection con = sql.openConnection();
            SkillDAO skillDAO = new SkillDAO(con);

            skill = skillDAO.getByName(name);

            if (skill != null) {
                ItemEntity item = getSkillItem(skill, con);

                skill.setItem(item);
            }
        } catch (Exception e) {
            LoggingUtil.error("Database Get SkillEntity", e);
        } finally {
            try {
                sql.close();
            } catch (Exception e) {
            }
        }

        return skill;
    }

    public List<SkillEntity> getAll() {
        SqliteConnection sql = new SqliteConnection();
        List<SkillEntity> skills = null;

        try {
            Connection con = sql.openConnection();
            SkillDAO skillDAO = new SkillDAO(con);

            skills = skillDAO.getAll();

            for (SkillEntity skill : skills) {
                ItemEntity item = getSkillItem(skill, con);

                skill.setItem(item);
            }
        } catch (Exception e) {
            LoggingUtil.error("Database GetAll SkillEntity", e);
        } finally {
            try {
                sql.close();
            } catch (Exception e) {
            }
        }

        return skills;
    }

    public void insert(SkillEntity skillEntity) {
        SqliteConnection sql = new SqliteConnection();

        try {
            Connection con = sql.openConnection();
            SkillDAO skillDAO = new SkillDAO(con);

            skillDAO.insert(skillEntity);
        } catch (Exception e) {
            LoggingUtil.error("Database Insert SkillEntity", e);
        } finally {
            try {
                sql.close();
            } catch (Exception e) {
            }
        }
    }

    public void update(SkillEntity skillEntity) {
        SqliteConnection sql = new SqliteConnection();

        try {
            Connection con = sql.openConnection();
            SkillDAO skillDAO = new SkillDAO(con);

            skillDAO.update(skillEntity);
        } catch (Exception e) {
            LoggingUtil.error("Database Update SkillEntity", e);
        } finally {
            try {
                sql.close();
            } catch (Exception e) {
            }
        }
    }

    public void delete(Long id) {
        SqliteConnection sql = new SqliteConnection();

        try {
            Connection con = sql.openConnection();
            SkillDAO skillDAO = new SkillDAO(con);

            skillDAO.delete(id);
        } catch (Exception e) {
            LoggingUtil.error("Database Delete SkillEntity", e);
        } finally {
            try {
                sql.close();
            } catch (Exception e) {
            }
        }
    }

    private ItemEntity getSkillItem(SkillEntity skill, Connection con) throws Exception {
        ItemDAO itemDAO = new ItemDAO(con);
        LoreDAO loreDAO = new LoreDAO(con);

        ItemEntity item = itemDAO.get(skill.getItemId());
        List<LoreEntity> lores = loreDAO.getAllByItem(item.getId());

        item.setLore(lores.stream().map(lore -> lore.getLore()).collect(Collectors.toList())); // hmmmm...

        return item;
    }
}
