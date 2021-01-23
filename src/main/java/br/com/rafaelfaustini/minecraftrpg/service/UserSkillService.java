package br.com.rafaelfaustini.minecraftrpg.service;

import java.sql.Connection;
import java.util.List;

import br.com.rafaelfaustini.minecraftrpg.dao.SqliteConnection;
import br.com.rafaelfaustini.minecraftrpg.dao.UserSkillDAO;
import br.com.rafaelfaustini.minecraftrpg.model.UserSkillEntity;
import br.com.rafaelfaustini.minecraftrpg.utils.LoggingUtil;

public class UserSkillService {

    public UserSkillEntity get(Long id) {
        SqliteConnection sql = new SqliteConnection();
        UserSkillEntity userSkill = null;

        try {
            Connection con = sql.openConnection();
            UserSkillDAO userSkillDAO = new UserSkillDAO(con);

            userSkill = userSkillDAO.get(id);
        } catch (Exception e) {
            LoggingUtil.error("Database Get UserSkillEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }

        return userSkill;
    }

    public UserSkillEntity get(String userUUID, Long skillId) {
        SqliteConnection sql = new SqliteConnection();
        UserSkillEntity userSkill = null;

        try {
            Connection con = sql.openConnection();
            UserSkillDAO userSkillDAO = new UserSkillDAO(con);

            userSkill = userSkillDAO.get(userUUID, skillId);
        } catch (Exception e) {
            LoggingUtil.error("Database Get UserSkillEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }

        return userSkill;
    }

    public List<UserSkillEntity> getAll() {
        SqliteConnection sql = new SqliteConnection();
        List<UserSkillEntity> userSkills = null;

        try {
            Connection con = sql.openConnection();
            UserSkillDAO userSkillDAO = new UserSkillDAO(con);

            userSkills = userSkillDAO.getAll();
        } catch (Exception e) {
            LoggingUtil.error("Database GetAll UserSkillEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }

        return userSkills;
    }

    public List<UserSkillEntity> getAllByUser(String userUUID) {
        SqliteConnection sql = new SqliteConnection();
        List<UserSkillEntity> userSkills = null;

        try {
            Connection con = sql.openConnection();
            UserSkillDAO userSkillDAO = new UserSkillDAO(con);

            userSkills = userSkillDAO.getAllByUser(userUUID);
        } catch (Exception e) {
            LoggingUtil.error("Database GetAllByUser UserSkillEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }

        return userSkills;
    }

    public List<UserSkillEntity> getAllBySkill(Long skillId) {
        SqliteConnection sql = new SqliteConnection();
        List<UserSkillEntity> userSkills = null;

        try {
            Connection con = sql.openConnection();
            UserSkillDAO userSkillDAO = new UserSkillDAO(con);

            userSkills = userSkillDAO.getAllBySkill(skillId);
        } catch (Exception e) {
            LoggingUtil.error("Database GetAllBySkill UserSkillEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }

        return userSkills;
    }

    public void insert(UserSkillEntity userSkillEntity) {
        SqliteConnection sql = new SqliteConnection();

        try {
            Connection con = sql.openConnection();
            UserSkillDAO userSkillDAO = new UserSkillDAO(con);

            userSkillDAO.insert(userSkillEntity);
        } catch (Exception e) {
            LoggingUtil.error("Database Insert UserSkillEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }
    }

    public void update(UserSkillEntity userSkillEntity) {
        SqliteConnection sql = new SqliteConnection();

        try {
            Connection con = sql.openConnection();
            UserSkillDAO userSkillDAO = new UserSkillDAO(con);

            userSkillDAO.update(userSkillEntity);
            userSkillDAO = null;
        } catch (Exception e) {
            LoggingUtil.error("Database Update UserSkillEntity", e);
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
            UserSkillDAO userSkillDAO = new UserSkillDAO(con);

            userSkillDAO.delete(id);
        } catch (Exception e) {
            LoggingUtil.error("Database Delete UserSkillEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }
    }
}
