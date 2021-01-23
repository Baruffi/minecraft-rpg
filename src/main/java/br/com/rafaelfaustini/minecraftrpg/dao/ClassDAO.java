package br.com.rafaelfaustini.minecraftrpg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.rafaelfaustini.minecraftrpg.enums.ClassEnum;
import br.com.rafaelfaustini.minecraftrpg.interfaces.IDao;
import br.com.rafaelfaustini.minecraftrpg.model.ClassEntity;

public class ClassDAO implements IDao<Long, ClassEntity> { // <Type of id, entity>

    private final Connection connection;

    public ClassDAO(Connection con) {
        connection = con;
    }

    @Override
    public void createTable() throws Exception {
        String sql = "CREATE TABLE IF NOT EXISTS CLASSES ( ID INTEGER PRIMARY KEY, NAME TEXT, UNIQUE (NAME) )";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.execute();
    }

    public void fillTable() throws Exception { // Could be better
        String sql = "INSERT OR REPLACE INTO CLASSES (ID, NAME) VALUES ( ?, ? )";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setLong(1, ClassEnum.WARRIOR.ordinal() + 1);
        ps.setString(2, ClassEnum.WARRIOR.getClassName());
        ps.execute();

        ps.setLong(1, ClassEnum.MAGE.ordinal() + 1);
        ps.setString(2, ClassEnum.MAGE.getClassName());
        ps.execute();

        ps.setLong(1, ClassEnum.ROGUE.ordinal() + 1);
        ps.setString(2, ClassEnum.ROGUE.getClassName());
        ps.execute();

        ps.setLong(1, ClassEnum.DRUID.ordinal() + 1);
        ps.setString(2, ClassEnum.DRUID.getClassName());
        ps.execute();

        ps.setLong(1, ClassEnum.ALCHEMIST.ordinal() + 1);
        ps.setString(2, ClassEnum.ALCHEMIST.getClassName());
        ps.execute();

        ps.setLong(1, ClassEnum.BARD.ordinal() + 1);
        ps.setString(2, ClassEnum.BARD.getClassName());
        ps.execute();
    }

    @Override
    public ClassEntity get(Long id) throws Exception {
        ResultSet rs = null;
        ClassEntity classEntity = null;
        String sql = "SELECT NAME FROM CLASSES WHERE ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setLong(1, id);
        rs = ps.executeQuery();

        if (rs.next()) {
            String name = rs.getString(1);

            classEntity = new ClassEntity(id, name);
        }

        return classEntity;
    }

    @Override
    public List<ClassEntity> getAll() throws Exception {
        ResultSet rs = null;
        List<ClassEntity> classes = new ArrayList<>();
        String sql = "SELECT ID, NAME FROM CLASSES";
        PreparedStatement ps = connection.prepareStatement(sql);

        rs = ps.executeQuery();

        while (rs.next()) {
            Long id = rs.getLong(1);
            String name = rs.getString(2);
            ClassEntity classEntity = new ClassEntity(id, name);

            classes.add(classEntity);
        }

        return classes;
    }

    @Override
    public void insert(ClassEntity ClassEntity) throws Exception {
        String sql = "INSERT INTO CLASSES (NAME) VALUES ( ? )";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, ClassEntity.getName());
        ps.execute();
    }

    @Override
    public void update(ClassEntity ClassEntity) throws Exception {
        String sql = "UPDATE CLASSES SET NAME=? WHERE ID=?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, ClassEntity.getName());
        ps.setLong(2, ClassEntity.getId());
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
