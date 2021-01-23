package br.com.rafaelfaustini.minecraftrpg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.rafaelfaustini.minecraftrpg.enums.ActiveSkillEnum;
import br.com.rafaelfaustini.minecraftrpg.enums.SkillTypeEnum;
import br.com.rafaelfaustini.minecraftrpg.interfaces.IDao;
import br.com.rafaelfaustini.minecraftrpg.model.SkillEntity;

public class SkillDAO implements IDao<Long, SkillEntity> { // <Type of id, entity>

    private final Connection connection;

    public SkillDAO(Connection con) {
        connection = con;
    }

    @Override
    public void createTable() throws Exception {
        String sql = "CREATE TABLE IF NOT EXISTS SKILLS ( ID INTEGER PRIMARY KEY, NAME TEXT, TYPE INTEGER, UNIQUE (NAME) )";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.execute();
    }

    public void fillTable() throws Exception { // Could be better
        String sql = "INSERT OR REPLACE INTO SKILLS (ID, NAME, TYPE) VALUES ( ?, ?, ? )";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setLong(1, ActiveSkillEnum.FIREBALL.ordinal() + 1);
        ps.setString(2, ActiveSkillEnum.FIREBALL.getActiveSkillName());
        ps.setInt(3, SkillTypeEnum.ACTIVE.ordinal());
        ps.execute();
    }

    @Override
    public SkillEntity get(Long id) throws Exception {
        ResultSet rs = null;
        SkillEntity skillEntity = null;
        String sql = "SELECT NAME, TYPE FROM SKILLS WHERE ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setLong(1, id);
        rs = ps.executeQuery();

        if (rs.next()) {
            String name = rs.getString(1);
            Integer type = rs.getInt(2);

            skillEntity = new SkillEntity(id, name, type);
        }

        return skillEntity;
    }

    @Override
    public List<SkillEntity> getAll() throws Exception {
        ResultSet rs = null;
        List<SkillEntity> skills = new ArrayList<>();
        String sql = "SELECT ID, NAME, TYPE FROM SKILLS";
        PreparedStatement ps = connection.prepareStatement(sql);

        rs = ps.executeQuery();

        while (rs.next()) {
            Long id = rs.getLong(1);
            String name = rs.getString(2);
            Integer type = rs.getInt(3);
            SkillEntity skillEntity = new SkillEntity(id, name, type);

            skills.add(skillEntity);
        }

        return skills;
    }

    @Override
    public void insert(SkillEntity SkillEntity) throws Exception {
        String sql = "INSERT INTO SKILLS (NAME, TYPE) VALUES ( ?, ? )";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, SkillEntity.getName());
        ps.setInt(2, SkillEntity.getType());
        ps.execute();
    }

    @Override
    public void update(SkillEntity SkillEntity) throws Exception {
        String sql = "UPDATE SKILLS SET NAME=?, TYPE=? WHERE ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, SkillEntity.getName());
        ps.setInt(2, SkillEntity.getType());
        ps.setLong(3, SkillEntity.getId());
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
