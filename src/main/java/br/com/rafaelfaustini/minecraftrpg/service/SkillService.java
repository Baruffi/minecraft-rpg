package br.com.rafaelfaustini.minecraftrpg.service;

import java.sql.Connection;
import java.util.List;

import br.com.rafaelfaustini.minecraftrpg.dao.SkillDAO;
import br.com.rafaelfaustini.minecraftrpg.dao.SqliteConnection;
import br.com.rafaelfaustini.minecraftrpg.model.SkillEntity;
import br.com.rafaelfaustini.minecraftrpg.utils.LoggingUtil;

public class SkillService {

    public SkillEntity get(Long id) {
        SqliteConnection sql = new SqliteConnection();
        SkillEntity user = null;

        try {
            Connection con = sql.openConnection();
            SkillDAO skillDAO = new SkillDAO(con);

            user = skillDAO.get(id);
        } catch (Exception e) {
            LoggingUtil.error("Database Get SkillEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }

        return user;
    }

    public List<SkillEntity> getAll() {
        SqliteConnection sql = new SqliteConnection();
        List<SkillEntity> users = null;

        try {
            Connection con = sql.openConnection();
            SkillDAO skillDAO = new SkillDAO(con);

            users = skillDAO.getAll();
        } catch (Exception e) {
            LoggingUtil.error("Database GetAll SkillEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }

        return users;
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
                sql = null;
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
            skillDAO = null;
        } catch (Exception e) {
            LoggingUtil.error("Database Update SkillEntity", e);
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
            SkillDAO skillDAO = new SkillDAO(con);

            skillDAO.delete(id);
        } catch (Exception e) {
            LoggingUtil.error("Database Delete SkillEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }
    }
}
