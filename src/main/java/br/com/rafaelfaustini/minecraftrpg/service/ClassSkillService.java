package br.com.rafaelfaustini.minecraftrpg.service;

import java.sql.Connection;
import java.util.List;

import br.com.rafaelfaustini.minecraftrpg.dao.ClassSkillDAO;
import br.com.rafaelfaustini.minecraftrpg.dao.SqliteConnection;
import br.com.rafaelfaustini.minecraftrpg.model.ClassSkillEntity;
import br.com.rafaelfaustini.minecraftrpg.utils.LoggingUtil;

public class ClassSkillService {

    public ClassSkillEntity get(Long id) {
        SqliteConnection sql = new SqliteConnection();
        ClassSkillEntity classSkill = null;

        try {
            Connection con = sql.openConnection();
            ClassSkillDAO classSkillDAO = new ClassSkillDAO(con);

            classSkill = classSkillDAO.get(id);
        } catch (Exception e) {
            LoggingUtil.error("Database Get ClassSkillEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }

        return classSkill;
    }

    public ClassSkillEntity get(Long classId, Long skillId) {
        SqliteConnection sql = new SqliteConnection();
        ClassSkillEntity classSkill = null;

        try {
            Connection con = sql.openConnection();
            ClassSkillDAO classSkillDAO = new ClassSkillDAO(con);

            classSkill = classSkillDAO.get(classId, skillId);
        } catch (Exception e) {
            LoggingUtil.error("Database Get ClassSkillEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }

        return classSkill;
    }

    public List<ClassSkillEntity> getAll() {
        SqliteConnection sql = new SqliteConnection();
        List<ClassSkillEntity> classSkills = null;

        try {
            Connection con = sql.openConnection();
            ClassSkillDAO classSkillDAO = new ClassSkillDAO(con);

            classSkills = classSkillDAO.getAll();
        } catch (Exception e) {
            LoggingUtil.error("Database GetAll ClassSkillEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }

        return classSkills;
    }

    public List<ClassSkillEntity> getAllByClass(Long classId) {
        SqliteConnection sql = new SqliteConnection();
        List<ClassSkillEntity> classSkills = null;

        try {
            Connection con = sql.openConnection();
            ClassSkillDAO classSkillDAO = new ClassSkillDAO(con);

            classSkills = classSkillDAO.getAllByClass(classId);
        } catch (Exception e) {
            LoggingUtil.error("Database GetAllByClass ClassSkillEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }

        return classSkills;
    }

    public List<ClassSkillEntity> getAllBySkill(Long skillId) {
        SqliteConnection sql = new SqliteConnection();
        List<ClassSkillEntity> classSkills = null;

        try {
            Connection con = sql.openConnection();
            ClassSkillDAO classSkillDAO = new ClassSkillDAO(con);

            classSkills = classSkillDAO.getAllBySkill(skillId);
        } catch (Exception e) {
            LoggingUtil.error("Database GetAllBySkill ClassSkillEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }

        return classSkills;
    }

    public void insert(ClassSkillEntity classSkillEntity) {
        SqliteConnection sql = new SqliteConnection();

        try {
            Connection con = sql.openConnection();
            ClassSkillDAO classSkillDAO = new ClassSkillDAO(con);

            classSkillDAO.insert(classSkillEntity);
        } catch (Exception e) {
            LoggingUtil.error("Database Insert ClassSkillEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }
    }

    public void update(ClassSkillEntity classSkillEntity) {
        SqliteConnection sql = new SqliteConnection();

        try {
            Connection con = sql.openConnection();
            ClassSkillDAO classSkillDAO = new ClassSkillDAO(con);

            classSkillDAO.update(classSkillEntity);
            classSkillDAO = null;
        } catch (Exception e) {
            LoggingUtil.error("Database Update ClassSkillEntity", e);
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
            ClassSkillDAO classSkillDAO = new ClassSkillDAO(con);

            classSkillDAO.delete(id);
        } catch (Exception e) {
            LoggingUtil.error("Database Delete ClassSkillEntity", e);
        } finally {
            try {
                sql.close();
                sql = null;
            } catch (Exception e) {
            }
        }
    }
}
