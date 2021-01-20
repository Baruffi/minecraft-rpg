package br.com.rafaelfaustini.minecraftrpg.service;

import java.util.List;

import br.com.rafaelfaustini.minecraftrpg.dao.SqliteConnection;
import br.com.rafaelfaustini.minecraftrpg.dao.UserDAO;
import br.com.rafaelfaustini.minecraftrpg.model.UserEntity;
import br.com.rafaelfaustini.minecraftrpg.utils.LoggingUtil;

public class UserService {

    public UserEntity get(String uuid) {
        SqliteConnection sql = new SqliteConnection();
        UserEntity user = null;
        try {
            sql.openConnection();
            UserDAO userDAO = new UserDAO(sql.getCon());
            user = userDAO.get(uuid);

        } catch (Exception e) {
            LoggingUtil.error("Database Get UserEntity", e);
        } finally {
            try { sql.close(); } catch (Exception e) {}
        }
        return user;
    }

    public List<UserEntity> getAll() {
        SqliteConnection sql = new SqliteConnection();
        List<UserEntity> users = null;
        try {
            sql.openConnection();
            UserDAO userDAO = new UserDAO(sql.getCon());
            users = userDAO.getAll();
        } catch (Exception e) {
            LoggingUtil.error("Database GetAll UserEntity", e);
        } finally {
            try { sql.close(); } catch (Exception e) {}
        }
        return users;
    }

    public void insert(UserEntity userEntity) {
        SqliteConnection sql = new SqliteConnection();
        try {
            sql.openConnection();
            UserDAO userDAO = new UserDAO(sql.getCon());
            userDAO.insert(userEntity);
        } catch (Exception e) {
            LoggingUtil.error("Database Insert UserEntity", e);
        } finally {
            try { sql.close(); } catch (Exception e) {}
        }
    }

    public void update(UserEntity userEntity) {
        SqliteConnection sql = new SqliteConnection();
        try {
            sql.openConnection();
            UserDAO userDAO = new UserDAO(sql.getCon());
            userDAO.update(userEntity);
        } catch (Exception e) {
            LoggingUtil.error("Database Update UserEntity", e);
        } finally {
            try { sql.close(); } catch (Exception e) {}
        }
    }

    public void delete(String uuid) {
        SqliteConnection sql = new SqliteConnection();
        try {
            sql.openConnection();
            UserDAO userDAO = new UserDAO(sql.getCon());
            userDAO.delete(uuid);
        } catch (Exception e) {
            LoggingUtil.error("Database Delete UserEntity", e);
        } finally {
            try { sql.close(); } catch (Exception e) {}
        }
    }
}
