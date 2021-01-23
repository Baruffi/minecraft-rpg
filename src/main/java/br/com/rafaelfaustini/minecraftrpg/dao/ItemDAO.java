package br.com.rafaelfaustini.minecraftrpg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.rafaelfaustini.minecraftrpg.interfaces.IDao;
import br.com.rafaelfaustini.minecraftrpg.model.ItemEntity;

public class ItemDAO implements IDao<Long, ItemEntity> { // <Type of id, entity>

    private final Connection connection;

    public ItemDAO(Connection con) {
        connection = con;
    }

    @Override
    public void createTable() throws Exception {
        String sql = "CREATE TABLE IF NOT EXISTS ITEMS ( ID INTEGER PRIMARY KEY, NAME TEXT, DISPLAY_NAME TEXT, MATERIAL TEXT, UNIQUE (NAME) )";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.execute();
    }

    @Override
    public ItemEntity get(Long id) throws Exception {
        ResultSet rs = null;
        ItemEntity itemEntity = null;
        String sql = "SELECT NAME, DISPLAY_NAME, MATERIAL FROM ITEMS WHERE ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setLong(1, id);
        rs = ps.executeQuery();

        if (rs.next()) {
            String name = rs.getString(1);
            String displayName = rs.getString(2);
            String material = rs.getString(3);

            itemEntity = new ItemEntity(id, name, displayName, material);
        }

        return itemEntity;
    }

    public ItemEntity getByName(String name) throws Exception {
        ResultSet rs = null;
        ItemEntity itemEntity = null;
        String sql = "SELECT ID, DISPLAY_NAME, MATERIAL FROM ITEMS WHERE NAME=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, name);
        rs = ps.executeQuery();

        if (rs.next()) {
            Long id = rs.getLong(1);
            String displayName = rs.getString(2);
            String material = rs.getString(3);

            itemEntity = new ItemEntity(id, name, displayName, material);
        }

        return itemEntity;
    }

    @Override
    public List<ItemEntity> getAll() throws Exception {
        ResultSet rs = null;
        List<ItemEntity> items = new ArrayList<>();
        String sql = "SELECT ID, NAME, DISPLAY_NAME, MATERIAL FROM ITEMS";
        PreparedStatement ps = connection.prepareStatement(sql);

        rs = ps.executeQuery();

        while (rs.next()) {
            Long id = rs.getLong(1);
            String name = rs.getString(2);
            String displayName = rs.getString(3);
            String material = rs.getString(4);

            ItemEntity itemEntity = new ItemEntity(id, name, displayName, material);

            items.add(itemEntity);
        }

        return items;
    }

    @Override
    public void insert(ItemEntity itemEntity) throws Exception {
        String sql = "INSERT INTO ITEMS (NAME, DISPLAY_NAME, MATERIAL) VALUES ( ?, ?, ? )";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, itemEntity.getName());
        ps.setString(2, itemEntity.getDisplayName());
        ps.setString(3, itemEntity.getMaterial());
        ps.execute();
    }

    @Override
    public void update(ItemEntity itemEntity) throws Exception {
        String sql = "UPDATE ITEMS SET NAME=?, DISPLAY_NAME=?, MATERIAL=? WHERE ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, itemEntity.getName());
        ps.setString(2, itemEntity.getDisplayName());
        ps.setString(3, itemEntity.getMaterial());
        ps.setLong(4, itemEntity.getId());
        ps.execute();
    }

    @Override
    public void delete(Long id) throws Exception {
        String sql = "DELETE ITEMS WHERE ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setLong(1, id);
        ps.execute();
    }
}
