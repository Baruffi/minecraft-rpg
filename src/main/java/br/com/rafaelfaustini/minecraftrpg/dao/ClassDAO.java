package br.com.rafaelfaustini.minecraftrpg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.rafaelfaustini.minecraftrpg.interfaces.IDao;
import br.com.rafaelfaustini.minecraftrpg.model.ClassEntity;

public class ClassDAO implements IDao<Long, ClassEntity> { // <Type of id, entity>

    private final Connection connection;

    public ClassDAO(Connection con) {
        connection = con;
    }

    @Override
    public void createTable() throws Exception {
        String sql = "CREATE TABLE IF NOT EXISTS CLASSES ( ID INTEGER PRIMARY KEY, NAME TEXT, ITEM_ID INTEGER, FOREIGN KEY(ITEM_ID) REFERENCES ITEMS(ID), UNIQUE (NAME) )";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.execute();
    }

    @Override
    public ClassEntity get(Long id) throws Exception {
        ResultSet rs = null;
        ClassEntity classEntity = null;
        String sql = "SELECT NAME, ITEM_ID FROM CLASSES WHERE ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setLong(1, id);
        rs = ps.executeQuery();

        if (rs.next()) {
            String name = rs.getString(1);
            Long itemId = rs.getLong(2);

            classEntity = new ClassEntity(id, name, itemId);
        }

        return classEntity;
    }

    public ClassEntity getByName(String name) throws Exception {
        ResultSet rs = null;
        ClassEntity classEntity = null;
        String sql = "SELECT ID, ITEM_ID FROM CLASSES WHERE NAME=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, name);
        rs = ps.executeQuery();

        if (rs.next()) {
            Long id = rs.getLong(1);
            Long itemId = rs.getLong(2);

            classEntity = new ClassEntity(id, name, itemId);
        }

        return classEntity;
    }

    @Override
    public List<ClassEntity> getAll() throws Exception {
        ResultSet rs = null;
        List<ClassEntity> classes = new ArrayList<>();
        String sql = "SELECT ID, NAME, ITEM_ID FROM CLASSES";
        PreparedStatement ps = connection.prepareStatement(sql);

        rs = ps.executeQuery();

        while (rs.next()) {
            Long id = rs.getLong(1);
            String name = rs.getString(2);
            Long itemId = rs.getLong(3);

            ClassEntity classEntity = new ClassEntity(id, name, itemId);

            classes.add(classEntity);
        }

        return classes;
    }

    @Override
    public void insert(ClassEntity classEntity) throws Exception {
        String sql = "INSERT INTO CLASSES (NAME, ITEM_ID) VALUES ( ?, ? )";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, classEntity.getName());
        ps.setLong(2, classEntity.getItemId());
        ps.execute();
    }

    @Override
    public void update(ClassEntity classEntity) throws Exception {
        String sql = "UPDATE CLASSES SET NAME=?, ITEM_ID=? WHERE ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, classEntity.getName());
        ps.setLong(2, classEntity.getItemId());
        ps.setLong(3, classEntity.getId());
        ps.execute();
    }

    @Override
    public void delete(Long id) throws Exception {
        String sql = "DELETE CLASSES WHERE ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setLong(1, id);
        ps.execute();
    }
}
