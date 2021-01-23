package br.com.rafaelfaustini.minecraftrpg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.rafaelfaustini.minecraftrpg.interfaces.IDao;
import br.com.rafaelfaustini.minecraftrpg.model.UserSkillEntity;

public class UserSkillDAO implements IDao<Long, UserSkillEntity> { // <Type of id, entity>

    private final Connection connection;

    public UserSkillDAO(Connection con) {
        connection = con;
    }

    @Override
    public void createTable() throws Exception {
        String sql = "CREATE TABLE IF NOT EXISTS USERS_SKILLS ( ID INTEGER PRIMARY KEY, USER_UUID TEXT, SKILL_ID INTEGER, STATUS INTEGER, FOREIGN KEY(USER_UUID) REFERENCES USERS(UUID), FOREIGN KEY(SKILL_ID) REFERENCES SKILLS(ID), UNIQUE (USER_UUID, SKILL_ID) )";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.execute();
    }

    @Override
    public UserSkillEntity get(Long id) throws Exception {
        ResultSet rs = null;
        UserSkillEntity userSkill = null;
        String sql = "SELECT USER_UUID, SKILL_ID, STATUS FROM USERS_SKILLS WHERE ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setLong(1, id);
        rs = ps.executeQuery();

        if (rs.next()) {
            String userUUID = rs.getString(1);
            Long skillId = rs.getLong(2);
            Integer status = rs.getInt(3);

            userSkill = new UserSkillEntity(id, userUUID, skillId, status);
        }

        return userSkill;
    }

    public UserSkillEntity get(String userUUID, Long skillId) throws Exception {
        ResultSet rs = null;
        UserSkillEntity userSkill = null;
        String sql = "SELECT ID, STATUS FROM USERS_SKILLS WHERE USER_UUID=? AND SKILL_ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, userUUID);
        ps.setLong(2, skillId);
        rs = ps.executeQuery();

        if (rs.next()) {
            Long id = rs.getLong(1);
            Integer status = rs.getInt(2);

            userSkill = new UserSkillEntity(id, userUUID, skillId, status);
        }

        return userSkill;
    }

    @Override
    public List<UserSkillEntity> getAll() throws Exception {
        ResultSet rs = null;
        List<UserSkillEntity> userSkills = new ArrayList<>();
        String sql = "SELECT ID, USER_UUID, SKILL_ID, STATUS FROM USERS_SKILLS";
        PreparedStatement ps = connection.prepareStatement(sql);

        rs = ps.executeQuery();

        while (rs.next()) {
            Long id = rs.getLong(1);
            String userUUID = rs.getString(2);
            Long skillId = rs.getLong(3);
            Integer status = rs.getInt(4);

            UserSkillEntity userSkill = new UserSkillEntity(id, userUUID, skillId, status);

            userSkills.add(userSkill);
        }

        return userSkills;
    }

    public List<UserSkillEntity> getAllByUser(String userUUID) throws Exception {
        ResultSet rs = null;
        List<UserSkillEntity> userSkills = new ArrayList<>();
        String sql = "SELECT ID, SKILL_ID, STATUS FROM USERS_SKILLS WHERE USER_UUID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, userUUID);
        rs = ps.executeQuery();

        if (rs.next()) {
            Long id = rs.getLong(1);
            Long skillId = rs.getLong(2);
            Integer status = rs.getInt(3);

            UserSkillEntity userSkill = new UserSkillEntity(id, userUUID, skillId, status);

            userSkills.add(userSkill);
        }

        return userSkills;
    }

    public List<UserSkillEntity> getAllBySkill(Long skillId) throws Exception {
        ResultSet rs = null;
        List<UserSkillEntity> userSkills = new ArrayList<>();
        String sql = "SELECT ID, USER_UUID, STATUS FROM USERS_SKILLS WHERE SKILL_ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setLong(1, skillId);
        rs = ps.executeQuery();

        while (rs.next()) {
            Long id = rs.getLong(1);
            String userUUID = rs.getString(2);
            Integer status = rs.getInt(3);

            UserSkillEntity userSkill = new UserSkillEntity(id, userUUID, skillId, status);

            userSkills.add(userSkill);
        }

        return userSkills;
    }

    @Override
    public void insert(UserSkillEntity userSkillEntity) throws Exception {
        String sql = "INSERT INTO USERS_SKILLS (USER_UUID, SKILL_ID) VALUES ( ?, ? )";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, userSkillEntity.getUserUUID());
        ps.setLong(2, userSkillEntity.getSkillId());
        ps.execute();
    }

    @Override
    public void update(UserSkillEntity userSkillEntity) throws Exception {
        String sql = "UPDATE USERS_SKILLS SET USER_UUID=?, SKILL_ID=?, STATUS=? WHERE ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, userSkillEntity.getUserUUID());
        ps.setLong(2, userSkillEntity.getSkillId());
        ps.setInt(3, userSkillEntity.getStatus());
        ps.setLong(4, userSkillEntity.getId());

        ps.execute();
    }

    @Override
    public void delete(Long id) throws Exception {
        String sql = "DELETE USERS_SKILLS WHERE ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setLong(1, id);
        ps.execute();
    }
}
