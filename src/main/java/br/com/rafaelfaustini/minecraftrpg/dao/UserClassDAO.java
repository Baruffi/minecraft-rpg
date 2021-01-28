package br.com.rafaelfaustini.minecraftrpg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.rafaelfaustini.minecraftrpg.interfaces.IDao;
import br.com.rafaelfaustini.minecraftrpg.model.UserClassEntity;

public class UserClassDAO implements IDao<Long, UserClassEntity> { // <Type of id, entity>

    private final Connection connection;

    public UserClassDAO(Connection con) {
        connection = con;
    }

    @Override
    public void createTable() throws Exception {
        String sql = "CREATE TABLE IF NOT EXISTS USERS_CLASSES ( ID INTEGER PRIMARY KEY, USER_UUID TEXT, CLASS_ID INTEGER, FOREIGN KEY(USER_UUID) REFERENCES USERS(UUID), FOREIGN KEY(CLASS_ID) REFERENCES CLASSES(ID), UNIQUE (USER_UUID, CLASS_ID) )";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.execute();
    }

    @Override
    public UserClassEntity get(Long id) throws Exception {
        ResultSet rs = null;
        UserClassEntity userClass = null;
        String sql = "SELECT USER_UUID, CLASS_ID FROM USERS_CLASSES WHERE ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setLong(1, id);
        rs = ps.executeQuery();

        if (rs.next()) {
            String userUUID = rs.getString(1);
            Long classId = rs.getLong(2);

            userClass = new UserClassEntity(id, userUUID, classId);
        }

        return userClass;
    }

    public UserClassEntity get(String userUUID, Long classId) throws Exception {
        ResultSet rs = null;
        UserClassEntity userClass = null;
        String sql = "SELECT ID FROM USERS_CLASSES WHERE USER_UUID=? AND CLASS_ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, userUUID);
        ps.setLong(2, classId);
        rs = ps.executeQuery();

        if (rs.next()) {
            Long id = rs.getLong(1);

            userClass = new UserClassEntity(id, userUUID, classId);
        }

        return userClass;
    }

    @Override
    public List<UserClassEntity> getAll() throws Exception {
        ResultSet rs = null;
        List<UserClassEntity> userClasses = new ArrayList<>();
        String sql = "SELECT ID, USER_UUID, CLASS_ID FROM USERS_CLASSES";
        PreparedStatement ps = connection.prepareStatement(sql);

        rs = ps.executeQuery();

        while (rs.next()) {
            Long id = rs.getLong(1);
            String userUUID = rs.getString(2);
            Long classId = rs.getLong(3);
            UserClassEntity userClass = new UserClassEntity(id, userUUID, classId);

            userClasses.add(userClass);
        }

        return userClasses;
    }

    public List<UserClassEntity> getAllByUser(String userUUID) throws Exception {
        ResultSet rs = null;
        List<UserClassEntity> userClasses = new ArrayList<>();
        String sql = "SELECT ID, CLASS_ID FROM USERS_CLASSES WHERE USER_UUID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, userUUID);
        rs = ps.executeQuery();

        while (rs.next()) {
            Long id = rs.getLong(1);
            Long classId = rs.getLong(2);

            UserClassEntity userClass = new UserClassEntity(id, userUUID, classId);

            userClasses.add(userClass);
        }

        return userClasses;
    }

    public List<UserClassEntity> getAllByClass(Long classId) throws Exception {
        ResultSet rs = null;
        List<UserClassEntity> userClasses = new ArrayList<>();
        String sql = "SELECT ID, USER_UUID FROM USERS_CLASSES WHERE CLASS_ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setLong(1, classId);
        rs = ps.executeQuery();

        while (rs.next()) {
            Long id = rs.getLong(1);
            String userUUID = rs.getString(2);
            UserClassEntity userClass = new UserClassEntity(id, userUUID, classId);

            userClasses.add(userClass);
        }

        return userClasses;
    }

    @Override
    public void insert(UserClassEntity userClassEntity) throws Exception {
        String sql = "INSERT INTO USERS_CLASSES (USER_UUID, CLASS_ID) VALUES ( ?, ? )";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, userClassEntity.getUserUUID());
        ps.setLong(2, userClassEntity.getClassId());
        ps.execute();
    }

    @Override
    public void update(UserClassEntity userClassEntity) throws Exception {
        String sql = "UPDATE USERS_CLASSES SET USER_UUID=?, CLASS_ID=? WHERE ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, userClassEntity.getUserUUID());
        ps.setLong(2, userClassEntity.getClassId());
        ps.setLong(3, userClassEntity.getId());
        ps.execute();
    }

    @Override
    public void delete(Long id) throws Exception {
        String sql = "DELETE USERS_CLASSES WHERE ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setLong(1, id);
        ps.execute();
    }
}
