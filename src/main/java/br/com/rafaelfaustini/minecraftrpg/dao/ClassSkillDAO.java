package br.com.rafaelfaustini.minecraftrpg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.rafaelfaustini.minecraftrpg.interfaces.IDao;
import br.com.rafaelfaustini.minecraftrpg.model.ClassSkillEntity;

public class ClassSkillDAO implements IDao<Long, ClassSkillEntity> { // <Type of id, entity>

    private final Connection connection;

    public ClassSkillDAO(Connection con) {
        connection = con;
    }

    @Override
    public void createTable() throws Exception {
        String sql = "CREATE TABLE IF NOT EXISTS CLASSES_SKILLS ( ID INTEGER PRIMARY KEY, CLASS_ID TEXT, SKILL_ID INTEGER, FOREIGN KEY(CLASS_ID) REFERENCES CLASSES(ID), FOREIGN KEY(SKILL_ID) REFERENCES SKILLS(ID), UNIQUE (CLASS_ID, SKILL_ID) )";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.execute();
    }

    @Override
    public ClassSkillEntity get(Long id) throws Exception {
        ResultSet rs = null;
        ClassSkillEntity classSkill = null;
        String sql = "SELECT CLASS_ID, SKILL_ID FROM CLASSES_SKILLS WHERE ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setLong(1, id);
        rs = ps.executeQuery();

        if (rs.next()) {
            Long classId = rs.getLong(1);
            Long skillId = rs.getLong(2);

            classSkill = new ClassSkillEntity(id, classId, skillId);
        }

        return classSkill;
    }

    public ClassSkillEntity get(Long classId, Long skillId) throws Exception {
        ResultSet rs = null;
        ClassSkillEntity classSkill = null;
        String sql = "SELECT ID FROM CLASSES_SKILLS WHERE CLASS_ID=? AND SKILL_ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setLong(1, classId);
        ps.setLong(2, skillId);
        rs = ps.executeQuery();

        if (rs.next()) {
            Long id = rs.getLong(1);

            classSkill = new ClassSkillEntity(id, classId, skillId);
        }

        return classSkill;
    }

    @Override
    public List<ClassSkillEntity> getAll() throws Exception {
        ResultSet rs = null;
        List<ClassSkillEntity> classSkills = new ArrayList<>();
        String sql = "SELECT ID, CLASS_ID, SKILL_ID FROM CLASSES_SKILLS";
        PreparedStatement ps = connection.prepareStatement(sql);

        rs = ps.executeQuery();

        while (rs.next()) {
            Long id = rs.getLong(1);
            Long classId = rs.getLong(2);
            Long skillId = rs.getLong(3);
            ClassSkillEntity classSkill = new ClassSkillEntity(id, classId, skillId);

            classSkills.add(classSkill);
        }

        return classSkills;
    }

    public List<ClassSkillEntity> getAllByClass(Long classId) throws Exception {
        ResultSet rs = null;
        List<ClassSkillEntity> classSkills = new ArrayList<>();
        String sql = "SELECT ID, SKILL_ID FROM CLASSES_SKILLS WHERE CLASS_ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setLong(1, classId);
        rs = ps.executeQuery();

        if (rs.next()) {
            Long id = rs.getLong(1);
            Long skillId = rs.getLong(2);

            ClassSkillEntity classSkill = new ClassSkillEntity(id, classId, skillId);

            classSkills.add(classSkill);
        }

        return classSkills;
    }

    public List<ClassSkillEntity> getAllBySkill(Long skillId) throws Exception {
        ResultSet rs = null;
        List<ClassSkillEntity> classSkills = new ArrayList<>();
        String sql = "SELECT ID, CLASS_ID FROM CLASSES_SKILLS WHERE SKILL_ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setLong(1, skillId);
        rs = ps.executeQuery();

        while (rs.next()) {
            Long id = rs.getLong(1);
            Long classId = rs.getLong(2);
            ClassSkillEntity classSkill = new ClassSkillEntity(id, classId, skillId);

            classSkills.add(classSkill);
        }

        return classSkills;
    }

    @Override
    public void insert(ClassSkillEntity classSkillEntity) throws Exception {
        String sql = "INSERT INTO CLASSES_SKILLS (CLASS_ID, SKILL_ID) VALUES ( ?, ? )";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setLong(1, classSkillEntity.getClassId());
        ps.setLong(2, classSkillEntity.getSkillId());
        ps.execute();
    }

    @Override
    public void update(ClassSkillEntity classSkillEntity) throws Exception {
        String sql = "UPDATE CLASSES_SKILLS SET CLASS_ID=?, SKILL_ID=? WHERE ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setLong(1, classSkillEntity.getClassId());
        ps.setLong(2, classSkillEntity.getSkillId());
        ps.setLong(3, classSkillEntity.getId());
        ps.execute();
    }

    @Override
    public void delete(Long id) throws Exception {
        String sql = "DELETE CLASSES_SKILLS WHERE ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setLong(1, id);
        ps.execute();
    }
}
