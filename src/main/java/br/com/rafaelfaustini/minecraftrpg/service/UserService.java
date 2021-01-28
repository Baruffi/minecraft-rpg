package br.com.rafaelfaustini.minecraftrpg.service;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.rafaelfaustini.minecraftrpg.dao.ClassDAO;
import br.com.rafaelfaustini.minecraftrpg.dao.ItemDAO;
import br.com.rafaelfaustini.minecraftrpg.dao.LoreDAO;
import br.com.rafaelfaustini.minecraftrpg.dao.SkillDAO;
import br.com.rafaelfaustini.minecraftrpg.dao.SqliteConnection;
import br.com.rafaelfaustini.minecraftrpg.dao.UserClassDAO;
import br.com.rafaelfaustini.minecraftrpg.dao.UserDAO;
import br.com.rafaelfaustini.minecraftrpg.dao.UserSkillDAO;
import br.com.rafaelfaustini.minecraftrpg.enums.SkillStatusEnum;
import br.com.rafaelfaustini.minecraftrpg.model.ClassEntity;
import br.com.rafaelfaustini.minecraftrpg.model.ItemEntity;
import br.com.rafaelfaustini.minecraftrpg.model.LoreEntity;
import br.com.rafaelfaustini.minecraftrpg.model.SkillEntity;
import br.com.rafaelfaustini.minecraftrpg.model.UserClassEntity;
import br.com.rafaelfaustini.minecraftrpg.model.UserEntity;
import br.com.rafaelfaustini.minecraftrpg.model.UserSkillEntity;
import br.com.rafaelfaustini.minecraftrpg.utils.LoggingUtil;
import br.com.rafaelfaustini.minecraftrpg.utils.TimeUtil;

public class UserService {

    public UserEntity get(String uuid) {
        SqliteConnection sql = new SqliteConnection();
        UserEntity user = null;

        try {
            Connection con = sql.openConnection();

            UserDAO userDAO = new UserDAO(con);

            user = userDAO.get(uuid);

            if (user != null) {
                List<ClassEntity> classes = getUserClasses(user, con);

                user.setClasses(classes);

                List<SkillEntity> skills = getUserSkills(user, con);

                user.setSkills(skills);
            }
        } catch (Exception e) {
            LoggingUtil.error("Database Get UserEntity", e);
        } finally {
            try {
                sql.close();
            } catch (Exception e) {
            }
        }

        return user;
    }

    public List<UserEntity> getAll() {
        SqliteConnection sql = new SqliteConnection();
        List<UserEntity> users = null;

        try {
            Connection con = sql.openConnection();
            UserDAO userDAO = new UserDAO(con);

            users = userDAO.getAll();

            for (UserEntity user : users) {
                List<ClassEntity> classes = getUserClasses(user, con);

                user.setClasses(classes);

                List<SkillEntity> skills = getUserSkills(user, con);

                user.setSkills(skills);
            }
        } catch (Exception e) {
            LoggingUtil.error("Database GetAll UserEntity", e);
        } finally {
            try {
                sql.close();
            } catch (Exception e) {
            }
        }

        return users;
    }

    public void insert(UserEntity userEntity) {
        SqliteConnection sql = new SqliteConnection();

        try {
            Connection con = sql.openConnection();
            UserDAO userDAO = new UserDAO(con);

            userDAO.insert(userEntity);
        } catch (Exception e) {
            LoggingUtil.error("Database Insert UserEntity", e);
        } finally {
            try {
                sql.close();
            } catch (Exception e) {
            }
        }
    }

    public void update(UserEntity userEntity) {
        SqliteConnection sql = new SqliteConnection();

        try {
            Connection con = sql.openConnection();
            UserDAO userDAO = new UserDAO(con);

            userDAO.update(userEntity);
        } catch (Exception e) {
            LoggingUtil.error("Database Update UserEntity", e);
        } finally {
            try {
                sql.close();
            } catch (Exception e) {
            }
        }
    }

    public void updateSkillStatus(String uuid, Long skillId, Integer status) {
        SqliteConnection sql = new SqliteConnection();

        try {
            Connection con = sql.openConnection();
            UserSkillDAO userSkillDAO = new UserSkillDAO(con);

            UserSkillEntity userSkillEntity = userSkillDAO.get(uuid, skillId);

            userSkillEntity.setStatus(status);

            userSkillDAO.update(userSkillEntity);
        } catch (Exception e) {
            LoggingUtil.error("Database Update UserEntity", e);
        } finally {
            try {
                sql.close();
            } catch (Exception e) {
            }
        }
    }

    public void updateSkillCooldown(String uuid, Long skillId, int cooldownSeconds) {
        SqliteConnection sql = new SqliteConnection();

        try {
            Connection con = sql.openConnection();
            UserSkillDAO userSkillDAO = new UserSkillDAO(con);

            UserSkillEntity userSkillEntity = userSkillDAO.get(uuid, skillId);

            userSkillEntity.setCooldownUntil(TimeUtil.getCooldownTime(cooldownSeconds));

            userSkillDAO.update(userSkillEntity);
        } catch (Exception e) {
            LoggingUtil.error("Database Update UserEntity", e);
        } finally {
            try {
                sql.close();
            } catch (Exception e) {
            }
        }
    }

    public void addUserClass(String uuid, Long classId) {
        SqliteConnection sql = new SqliteConnection();

        try {
            Connection con = sql.openConnection();
            UserClassDAO userClassDAO = new UserClassDAO(con);

            UserClassEntity userClassEntity = new UserClassEntity(uuid, classId);

            userClassDAO.insert(userClassEntity);
        } catch (Exception e) {
            LoggingUtil.error("Database Update UserEntity", e);
        } finally {
            try {
                sql.close();
            } catch (Exception e) {
            }
        }
    }

    public void addUserSkill(String uuid, Long skillId) {
        SqliteConnection sql = new SqliteConnection();

        try {
            Connection con = sql.openConnection();
            UserSkillDAO userSkillDAO = new UserSkillDAO(con);

            UserSkillEntity userSkillEntity = new UserSkillEntity(uuid, skillId,
                    SkillStatusEnum.INACTIVE.getStatusValue(), TimeUtil.getCurrentTime());

            userSkillDAO.insert(userSkillEntity);
        } catch (Exception e) {
            LoggingUtil.error("Database Update UserEntity", e);
        } finally {
            try {
                sql.close();
            } catch (Exception e) {
            }
        }
    }

    public void delete(String uuid) {
        SqliteConnection sql = new SqliteConnection();

        try {
            Connection con = sql.openConnection();
            UserDAO userDAO = new UserDAO(con);

            userDAO.delete(uuid);
        } catch (Exception e) {
            LoggingUtil.error("Database Delete UserEntity", e);
        } finally {
            try {
                sql.close();
            } catch (Exception e) {
            }
        }
    }

    private List<ClassEntity> getUserClasses(UserEntity user, Connection con) throws Exception {
        UserClassDAO userClassDAO = new UserClassDAO(con);
        ClassDAO classDAO = new ClassDAO(con);
        ItemDAO itemDAO = new ItemDAO(con);
        LoreDAO loreDAO = new LoreDAO(con);

        List<ClassEntity> classes = user.getClasses();
        List<UserClassEntity> userClasses = userClassDAO.getAllByUser(user.getUUID());

        for (UserClassEntity userClass : userClasses) {
            ClassEntity classEntity = classDAO.get(userClass.getClassId());
            ItemEntity item = itemDAO.get(classEntity.getItemId());
            List<LoreEntity> lores = loreDAO.getAllByItem(item.getId());

            item.setLore(lores.stream().map(lore -> lore.getLore()).collect(Collectors.toList())); // hmmmm...
            classEntity.setItem(item);
            classes.add(classEntity);
        }

        return classes;
    }

    private List<SkillEntity> getUserSkills(UserEntity user, Connection con) throws Exception {
        UserSkillDAO userSkillDAO = new UserSkillDAO(con);
        SkillDAO skillDAO = new SkillDAO(con);
        ItemDAO itemDAO = new ItemDAO(con);
        LoreDAO loreDAO = new LoreDAO(con);

        List<SkillEntity> skills = user.getSkills();
        List<UserSkillEntity> userSkillList = user.getUserSkillList();
        List<UserSkillEntity> userSkills = userSkillDAO.getAllByUser(user.getUUID());

        for (UserSkillEntity userSkill : userSkills) {
            SkillEntity skill = skillDAO.get(userSkill.getSkillId());
            ItemEntity item = itemDAO.get(skill.getItemId());
            List<LoreEntity> lores = loreDAO.getAllByItem(item.getId());

            item.setLore(lores.stream().map(lore -> lore.getLore()).collect(Collectors.toList())); // hmmmm...
            skill.setItem(item);
            skills.add(skill);

            userSkillList.add(userSkill);
        }

        user.setUserSkillList(userSkillList); // TODO: move this later

        return skills;
    }
}
