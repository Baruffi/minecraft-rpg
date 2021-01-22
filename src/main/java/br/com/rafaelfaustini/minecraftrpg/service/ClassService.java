package br.com.rafaelfaustini.minecraftrpg.service;

import java.sql.Connection;
import java.util.List;

import br.com.rafaelfaustini.minecraftrpg.dao.ClassDAO;
import br.com.rafaelfaustini.minecraftrpg.dao.SqliteConnection;
import br.com.rafaelfaustini.minecraftrpg.model.ClassEntity;
import br.com.rafaelfaustini.minecraftrpg.utils.LoggingUtil;

public class ClassService {

    public ClassEntity get(Long id) {
        SqliteConnection sql = new SqliteConnection();
        ClassEntity user = null;

        try {
            Connection con = sql.openConnection();
            ClassDAO classDAO = new ClassDAO(con);

            user = classDAO.get(id);
        } catch (Exception e) {
            LoggingUtil.error("Database Get ClassEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }

        return user;
    }

    public List<ClassEntity> getAll() {
        SqliteConnection sql = new SqliteConnection();
        List<ClassEntity> users = null;

        try {
            Connection con = sql.openConnection();
            ClassDAO classDAO = new ClassDAO(con);

            users = classDAO.getAll();
        } catch (Exception e) {
            LoggingUtil.error("Database GetAll ClassEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }

        return users;
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
}
