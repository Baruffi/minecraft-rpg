package br.com.rafaelfaustini.minecraftrpg.service;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.rafaelfaustini.minecraftrpg.dao.ClassDAO;
import br.com.rafaelfaustini.minecraftrpg.dao.ClassSkillDAO;
import br.com.rafaelfaustini.minecraftrpg.dao.ItemDAO;
import br.com.rafaelfaustini.minecraftrpg.dao.LoreDAO;
import br.com.rafaelfaustini.minecraftrpg.dao.SkillDAO;
import br.com.rafaelfaustini.minecraftrpg.dao.SqliteConnection;
import br.com.rafaelfaustini.minecraftrpg.model.ClassEntity;
import br.com.rafaelfaustini.minecraftrpg.model.ClassSkillEntity;
import br.com.rafaelfaustini.minecraftrpg.model.ItemEntity;
import br.com.rafaelfaustini.minecraftrpg.model.LoreEntity;
import br.com.rafaelfaustini.minecraftrpg.model.SkillEntity;
import br.com.rafaelfaustini.minecraftrpg.utils.LoggingUtil;

public class ClassService {

    public ClassEntity get(Long id) {
        SqliteConnection sql = new SqliteConnection();
        ClassEntity classEntity = null;

        try {
            Connection con = sql.openConnection();
            ClassDAO classDAO = new ClassDAO(con);

            classEntity = classDAO.get(id);

            if (classEntity != null) {
                ItemEntity item = getClassItem(classEntity, con);

                classEntity.setItem(item);

                List<SkillEntity> skills = getClassSkills(classEntity, con);

                classEntity.setSkills(skills);
            }
        } catch (Exception e) {
            LoggingUtil.error("Database Get ClassEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }

        return classEntity;
    }

    public ClassEntity getByName(String name) {
        SqliteConnection sql = new SqliteConnection();
        ClassEntity classEntity = null;

        try {
            Connection con = sql.openConnection();
            ClassDAO classDAO = new ClassDAO(con);

            classEntity = classDAO.getByName(name);

            if (classEntity != null) {
                ItemEntity item = getClassItem(classEntity, con);

                classEntity.setItem(item);

                List<SkillEntity> skills = getClassSkills(classEntity, con);

                classEntity.setSkills(skills);
            }
        } catch (Exception e) {
            LoggingUtil.error("Database Get ClassEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }

        return classEntity;
    }

    public List<ClassEntity> getAll() {
        SqliteConnection sql = new SqliteConnection();
        List<ClassEntity> classes = null;

        try {
            Connection con = sql.openConnection();
            ClassDAO classDAO = new ClassDAO(con);

            classes = classDAO.getAll();

            for (ClassEntity classEntity : classes) {
                ItemEntity item = getClassItem(classEntity, con);

                classEntity.setItem(item);

                List<SkillEntity> skills = getClassSkills(classEntity, con);

                classEntity.setSkills(skills);
            }
        } catch (Exception e) {
            LoggingUtil.error("Database GetAll ClassEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }

        return classes;
    }

    public void insert(ClassEntity classEntity) {
        SqliteConnection sql = new SqliteConnection();

        try {
            Connection con = sql.openConnection();
            ClassDAO classDAO = new ClassDAO(con);

            classDAO.insert(classEntity);
        } catch (Exception e) {
            LoggingUtil.error("Database Insert ClassEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }
    }

    public void update(ClassEntity classEntity) {
        SqliteConnection sql = new SqliteConnection();

        try {
            Connection con = sql.openConnection();
            ClassDAO classDAO = new ClassDAO(con);

            classDAO.update(classEntity);
            classDAO = null;
        } catch (Exception e) {
            LoggingUtil.error("Database Update ClassEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }
    }

    public void delete(Long id) {
        SqliteConnection sql = new SqliteConnection();

        try {
            Connection con = sql.openConnection();
            ClassDAO classDAO = new ClassDAO(con);

            classDAO.delete(id);
        } catch (Exception e) {
            LoggingUtil.error("Database Delete ClassEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }
    }

    private ItemEntity getClassItem(ClassEntity classEntity, Connection con) throws Exception {
        ItemDAO itemDAO = new ItemDAO(con);
        LoreDAO loreDAO = new LoreDAO(con);

        ItemEntity item = itemDAO.get(classEntity.getItemId());
        List<LoreEntity> lores = loreDAO.getAllByItem(item.getId());

        item.setLore(lores.stream().map(lore -> lore.getLore()).collect(Collectors.toList())); // hmmmm...

        return item;
    }

    private List<SkillEntity> getClassSkills(ClassEntity classEntity, Connection con) throws Exception {
        ClassSkillDAO classEntitySkillDAO = new ClassSkillDAO(con);
        SkillDAO skillDAO = new SkillDAO(con);
        ItemDAO itemDAO = new ItemDAO(con);
        LoreDAO loreDAO = new LoreDAO(con);

        List<SkillEntity> skills = classEntity.getSkills();
        List<ClassSkillEntity> classEntitySkills = classEntitySkillDAO.getAllByClass(classEntity.getId());

        for (ClassSkillEntity classEntitySkill : classEntitySkills) {
            SkillEntity skill = skillDAO.get(classEntitySkill.getSkillId());
            ItemEntity item = itemDAO.get(classEntity.getItemId());
            List<LoreEntity> lores = loreDAO.getAllByItem(item.getId());

            item.setLore(lores.stream().map(lore -> lore.getLore()).collect(Collectors.toList())); // hmmmm...
            skill.setItem(item);
            skills.add(skill);
        }

        return skills;
    }
}
