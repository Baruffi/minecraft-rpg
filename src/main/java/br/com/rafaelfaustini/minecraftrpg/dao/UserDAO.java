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
    public UserEntity get(String uuid) throws Exception {
        ResultSet rs = null;
        UserEntity user = null;
        String sql = "SELECT LAST_ACCOUNT_NAME FROM users where uuid=?";
        PreparedStatement ps = conexao.prepareStatement(sql);
        ps.setString(1, uuid);
        rs = ps.executeQuery();

        if(rs.next()){
            String lastAccountName = rs.getString(1);
            user = new UserEntity(uuid, lastAccountName);
        }
        return user;
    }

    @Override
    public List<UserEntity> getAll() throws Exception {
        ResultSet rs = null;
        List<UserEntity> users = new ArrayList<>();
        String sql = "SELECT UUID, LAST_ACCOUNT_NAME FROM users";
        PreparedStatement ps = conexao.prepareStatement(sql);
        rs = ps.executeQuery();

        while(rs.next()){
            String uuid = rs.getString(1);
            String lastAccountName = rs.getString(2);
            UserEntity user = new UserEntity(uuid, lastAccountName);
            users.add(user);
        }
        return users;
    }

    @Override
    public void insert(UserEntity userEntity) throws Exception {
        String sql = "INSERT INTO users (uuid, last_account_name) VALUES ( ?, ? )";
        PreparedStatement ps = conexao.prepareStatement(sql);
        ps.setString(1, userEntity.getUUID());
        ps.setString(2, userEntity.getLastAccountName());
        ps.execute();
    }

    @Override
    public void update(UserEntity userEntity) throws Exception {
        String sql = "UPDATE users SET last_account_name=? where uuid=?";
        PreparedStatement ps = conexao.prepareStatement(sql);
        ps.setString(1, userEntity.getLastAccountName());
        ps.setString(2, userEntity.getUUID());
        ps.execute();
    }

    @Override
    public void delete(String uuid) throws Exception {
        String sql = "DELETE users where uuid=?";
        PreparedStatement ps = conexao.prepareStatement(sql);
        ps.setString(1, uuid);
        ps.execute();
    }
}
