package br.com.rafaelfaustini.minecraftrpg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import br.com.rafaelfaustini.minecraftrpg.interfaces.IDao;
import br.com.rafaelfaustini.minecraftrpg.model.UserEntity;
import br.com.rafaelfaustini.minecraftrpg.utils.LoggingUtil;

public class UserDAO implements IDao<String, UserEntity> { // <Type of id, entity>

    private Connection conexao;

    private void createTable() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS users ( uuid text PRIMARY KEY, last_account_name text )";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.execute();
        } catch (Exception e) {
            LoggingUtil.error("Database Creating UserEntity", e);
        }
    }

    public UserDAO(Connection con) {
        conexao = con;
        createTable();
    }

    @Override
    public UserEntity get(String uuid) {
        return null;
    }

    @Override
    public List<UserEntity> getAll() {
        return null;
    }

    @Override
    public void insert(UserEntity userEntity) {
        try {
            String sql = "INSERT INTO users (uuid, last_account_name) VALUES ( ?, ? )";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, userEntity.getUUID());
            ps.setString(2, userEntity.getLastAccountName());
            ps.execute();
        } catch (Exception e) {
            LoggingUtil.error("Database Inserting UserEntity", e);
        }
    }

    @Override
    public void update(UserEntity userEntity) {
        try {
            String sql = "UPDATE users SET last_account_name=? where uuid=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, userEntity.getLastAccountName());
            ps.setString(2, userEntity.getUUID());
            ps.execute();
        } catch (Exception e) {
            LoggingUtil.error("Database Updating UserEntity", e);
        }
    }

    @Override
    public void delete(String uuid) {
        try {
            String sql = "DELETE users where uuid=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, uuid);
            ps.execute();
        } catch (Exception e) {
            LoggingUtil.error("Database Updating UserEntity", e);
        }
    }
}
