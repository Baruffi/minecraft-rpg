package br.com.rafaelfaustini.minecraftrpg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.rafaelfaustini.minecraftrpg.interfaces.IDao;
import br.com.rafaelfaustini.minecraftrpg.model.SkillEntity;

public class SkillDAO implements IDao<Long, SkillEntity> { // <Type of id, entity>

    private final Connection connection;

    public SkillDAO(Connection con) {
        connection = con;
    }

    @Override
    public void createTable() throws Exception {
        String sql = "CREATE TABLE IF NOT EXISTS SKILLS ( ID INTEGER PRIMARY KEY, NAME TEXT, TYPE INTEGER, ITEM_ID INTEGER, FOREIGN KEY(ITEM_ID) REFERENCES ITEMS(ID), UNIQUE (NAME) )";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.execute();
    }

    @Override
    public SkillEntity get(Long id) throws Exception {
        ResultSet rs = null;
        SkillEntity skillEntity = null;
        String sql = "SELECT NAME, TYPE, ITEM_ID FROM SKILLS WHERE ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setLong(1, id);
        rs = ps.executeQuery();

        if (rs.next()) {
            String name = rs.getString(1);
            Integer type = rs.getInt(2);
            Long itemId = rs.getLong(3);

            skillEntity = new SkillEntity(id, name, type, itemId);
        }

        return skillEntity;
    }

    public SkillEntity getByName(String name) throws Exception {
        ResultSet rs = null;
        SkillEntity skillEntity = null;
        String sql = "SELECT ID, TYPE, ITEM_ID FROM SKILLS WHERE NAME=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, name);
        rs = ps.executeQuery();

        if (rs.next()) {
            Long id = rs.getLong(1);
            Integer type = rs.getInt(2);
            Long itemId = rs.getLong(3);

            skillEntity = new SkillEntity(id, name, type, itemId);
        }

        return skillEntity;
    }

    @Override
    public List<SkillEntity> getAll() throws Exception {
        ResultSet rs = null;
        List<SkillEntity> skills = new ArrayList<>();
        String sql = "SELECT ID, NAME, TYPE, ITEM_ID FROM SKILLS";
        PreparedStatement ps = connection.prepareStatement(sql);

        rs = ps.executeQuery();

        while (rs.next()) {
            Long id = rs.getLong(1);
            String name = rs.getString(2);
            Integer type = rs.getInt(3);
            Long itemId = rs.getLong(4);

            SkillEntity skillEntity = new SkillEntity(id, name, type, itemId);

            skills.add(skillEntity);
        }

        return skills;
    }

    @Override
    public void insert(SkillEntity skillEntity) throws Exception {
        String sql = "INSERT INTO SKILLS (NAME, TYPE, ITEM_ID) VALUES ( ?, ?, ? )";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, skillEntity.getName());
        ps.setInt(2, skillEntity.getType());
        ps.setLong(3, skillEntity.getItemId());
        ps.execute();
    }

    @Override
    public void update(SkillEntity skillEntity) throws Exception {
        String sql = "UPDATE SKILLS SET NAME=?, TYPE=?, ITEM_ID WHERE ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, skillEntity.getName());
        ps.setInt(2, skillEntity.getType());
        ps.setLong(3, skillEntity.getItemId());
        ps.setLong(4, skillEntity.getId());
        ps.execute();
    }

    @Override
    public void delete(Long id) throws Exception {
        String sql = "DELETE SKILLS WHERE ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setLong(1, id);
        ps.execute();
    }
}
