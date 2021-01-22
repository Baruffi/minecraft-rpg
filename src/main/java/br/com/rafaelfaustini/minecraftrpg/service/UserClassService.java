package br.com.rafaelfaustini.minecraftrpg.service;

import java.sql.Connection;
import java.util.List;

import br.com.rafaelfaustini.minecraftrpg.dao.SqliteConnection;
import br.com.rafaelfaustini.minecraftrpg.dao.UserClassDAO;
import br.com.rafaelfaustini.minecraftrpg.model.UserClassEntity;
import br.com.rafaelfaustini.minecraftrpg.utils.LoggingUtil;

public class UserClassService {

    public UserClassEntity get(Long id) {
        SqliteConnection sql = new SqliteConnection();
        UserClassEntity userClass = null;

        try {
            Connection con = sql.openConnection();
            UserClassDAO userClassDAO = new UserClassDAO(con);

            userClass = userClassDAO.get(id);
        } catch (Exception e) {
            LoggingUtil.error("Database Get UserClassEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }

        return userClass;
    }

    public UserClassEntity get(String userUUID, Long classId) {
        SqliteConnection sql = new SqliteConnection();
        UserClassEntity userClass = null;

        try {
            Connection con = sql.openConnection();
            UserClassDAO userClassDAO = new UserClassDAO(con);

            userClass = userClassDAO.get(userUUID, classId);
        } catch (Exception e) {
            LoggingUtil.error("Database Get UserClassEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }

        return userClass;
    }

    public List<UserClassEntity> getAll() {
        SqliteConnection sql = new SqliteConnection();
        List<UserClassEntity> userClasses = null;

        try {
            Connection con = sql.openConnection();
            UserClassDAO userClassDAO = new UserClassDAO(con);

            userClasses = userClassDAO.getAll();
        } catch (Exception e) {
            LoggingUtil.error("Database GetAll UserClassEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }

        return userClasses;
    }

    public List<UserClassEntity> getAllByUser(String userUUID) {
        SqliteConnection sql = new SqliteConnection();
        List<UserClassEntity> userClasses = null;

        try {
            Connection con = sql.openConnection();
            UserClassDAO userClassDAO = new UserClassDAO(con);

            userClasses = userClassDAO.getAllByUser(userUUID);
        } catch (Exception e) {
            LoggingUtil.error("Database GetAllByUser UserClassEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }

        return userClasses;
    }

    public List<UserClassEntity> getAllByClass(Long classId) {
        SqliteConnection sql = new SqliteConnection();
        List<UserClassEntity> userClasses = null;

        try {
            Connection con = sql.openConnection();
            UserClassDAO userClassDAO = new UserClassDAO(con);

            userClasses = userClassDAO.getAllByClass(classId);
        } catch (Exception e) {
            LoggingUtil.error("Database GetAllByClass UserClassEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }

        return userClasses;
    }

    public void insert(UserClassEntity userClassEntity) {
        SqliteConnection sql = new SqliteConnection();

        try {
            Connection con = sql.openConnection();
            UserClassDAO userClassDAO = new UserClassDAO(con);

            userClassDAO.insert(userClassEntity);
        } catch (Exception e) {
            LoggingUtil.error("Database Insert UserClassEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }
    }

    public void update(UserClassEntity userClassEntity) {
        SqliteConnection sql = new SqliteConnection();

        try {
            Connection con = sql.openConnection();
            UserClassDAO userClassDAO = new UserClassDAO(con);

            userClassDAO.update(userClassEntity);
            userClassDAO = null;
        } catch (Exception e) {
            LoggingUtil.error("Database Update UserClassEntity", e);
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
            UserClassDAO userClassDAO = new UserClassDAO(con);

            userClassDAO.delete(id);
        } catch (Exception e) {
            LoggingUtil.error("Database Delete UserClassEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }
    }
}
