package br.com.rafaelfaustini.minecraftrpg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.rafaelfaustini.minecraftrpg.interfaces.IDao;
import br.com.rafaelfaustini.minecraftrpg.model.LoreEntity;

public class LoreDAO implements IDao<Long, LoreEntity> { // <Type of id, entity>

    private final Connection connection;

    public LoreDAO(Connection con) {
        connection = con;
    }

    @Override
    public void createTable() throws Exception {
        String sql = "CREATE TABLE IF NOT EXISTS LORES ( ID INTEGER PRIMARY KEY, LORE TEXT, ITEM_ID INTEGER, FOREIGN KEY(ITEM_ID) REFERENCES ITEMS(ID) )";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.execute();
    }

    @Override
    public LoreEntity get(Long id) throws Exception {
        ResultSet rs = null;
        LoreEntity loreEntity = null;
        String sql = "SELECT LORE, ITEM_ID FROM LORES WHERE ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setLong(1, id);
        rs = ps.executeQuery();

        if (rs.next()) {
            String lore = rs.getString(1);
            Long itemId = rs.getLong(2);

            loreEntity = new LoreEntity(id, lore, itemId);
        }

        return loreEntity;
    }

    @Override
    public List<LoreEntity> getAll() throws Exception {
        ResultSet rs = null;
        List<LoreEntity> lores = new ArrayList<>();
        String sql = "SELECT ID, LORE, ITEM_ID FROM LORES";
        PreparedStatement ps = connection.prepareStatement(sql);

        rs = ps.executeQuery();

        while (rs.next()) {
            Long id = rs.getLong(1);
            String lore = rs.getString(2);
            Long itemId = rs.getLong(3);

            LoreEntity loreEntity = new LoreEntity(id, lore, itemId);

            lores.add(loreEntity);
        }

        return lores;
    }

    public List<LoreEntity> getAllByItem(Long itemId) throws Exception {
        ResultSet rs = null;
        List<LoreEntity> lores = new ArrayList<>();
        String sql = "SELECT ID, LORE FROM LORES WHERE ITEM_ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setLong(1, itemId);
        rs = ps.executeQuery();

        while (rs.next()) {
            Long id = rs.getLong(1);
            String lore = rs.getString(2);

            LoreEntity loreEntity = new LoreEntity(id, lore, itemId);

            lores.add(loreEntity);
        }

        return lores;
    }

    @Override
    public void insert(LoreEntity loreEntity) throws Exception {
        String sql = "INSERT INTO LORES (LORE, ITEM_ID) VALUES ( ?, ? )";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, loreEntity.getLore());
        ps.setLong(2, loreEntity.getItemId());
        ps.execute();
    }

    @Override
    public void update(LoreEntity loreEntity) throws Exception {
        String sql = "UPDATE LORES SET LORE=?, ITEM_ID=? WHERE ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, loreEntity.getLore());
        ps.setLong(2, loreEntity.getItemId());
        ps.setLong(3, loreEntity.getId());
        ps.execute();
    }

    @Override
    public void delete(Long id) throws Exception {
        String sql = "DELETE LORES WHERE ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setLong(1, id);
        ps.execute();
    }
}
