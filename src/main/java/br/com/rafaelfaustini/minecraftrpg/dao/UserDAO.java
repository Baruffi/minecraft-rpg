package br.com.rafaelfaustini.minecraftrpg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.rafaelfaustini.minecraftrpg.interfaces.IDao;
import br.com.rafaelfaustini.minecraftrpg.model.UserEntity;
import br.com.rafaelfaustini.minecraftrpg.utils.LoggingUtil;

public class UserDAO implements IDao<String, UserEntity> { // <Type of id, entity>

    private final Connection connection;

    private void createTable() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS USERS ( UUID TEXT PRIMARY KEY, LAST_ACCOUNT_NAME TEXT, CLASS_ID INTEGER, FOREIGN KEY(CLASS_ID) REFERENCES CLASSES(ID) )";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.execute();
        } catch (Exception e) {
            LoggingUtil.error("Database Creating UserEntity", e);
        }
    }

    public UserDAO(Connection con) {
        connection = con;
        createTable();
    }

    @Override
    public UserEntity get(String uuid) throws Exception {
        ResultSet rs = null;
        UserEntity user = null;
        String sql = "SELECT LAST_ACCOUNT_NAME, CLASS_ID FROM USERS WHERE UUID=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, uuid);
        rs = ps.executeQuery();

        if (rs.next()) {
            String lastAccountName = rs.getString(1);
            Long classId = rs.getLong(2);
            user = new UserEntity(uuid, lastAccountName, classId);
        }

        return user;
    }

    @Override
    public List<UserEntity> getAll() throws Exception {
        ResultSet rs = null;
        List<UserEntity> users = new ArrayList<>();
        String sql = "SELECT UUID, LAST_ACCOUNT_NAME, CLASS_ID FROM USERS";
        PreparedStatement ps = connection.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            String uuid = rs.getString(1);
            String lastAccountName = rs.getString(2);
            Long classId = rs.getLong(3);
            UserEntity user = new UserEntity(uuid, lastAccountName, classId);
            users.add(user);
        }

        return users;
    }

    @Override
    public void insert(UserEntity userEntity) throws Exception {
        String sql = "INSERT INTO USERS (UUID, LAST_ACCOUNT_NAME, CLASS_ID) VALUES ( ?, ?, ? )";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, userEntity.getUUID());
        ps.setString(2, userEntity.getLastAccountName());

        if (userEntity.getClassId() != null) {
            ps.setLong(3, userEntity.getClassId());
        }

        ps.execute();
    }

    @Override
    public void update(UserEntity userEntity) throws Exception {
        String sql = "UPDATE USERS SET LAST_ACCOUNT_NAME=?, CLASS_ID=? WHERE UUID=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, userEntity.getLastAccountName());
        ps.setLong(2, userEntity.getClassId());
        ps.setString(3, userEntity.getUUID());
        ps.execute();
    }

    @Override
    public void delete(String uuid) throws Exception {
        String sql = "DELETE USERS WHERE UUID=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, uuid);
        ps.execute();
    }
}
