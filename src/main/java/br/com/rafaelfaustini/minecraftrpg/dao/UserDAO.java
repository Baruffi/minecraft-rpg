package br.com.rafaelfaustini.minecraftrpg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import br.com.rafaelfaustini.minecraftrpg.interfaces.IDao;
import br.com.rafaelfaustini.minecraftrpg.model.UserEntity;
import br.com.rafaelfaustini.minecraftrpg.utils.LoggingUtil;

public class UserDAO implements IDao<UserEntity> {

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
    public UserEntity get(long id) {
        return null;
    }

    @Override
    public List<UserEntity> getAll() {
        return null;
    }

    @Override
    public void insert(UserEntity userentity) {
        try {
            String sql = "INSERT INTO users VALUES ( uuid text PRIMARY KEY, money real )";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.execute();
        } catch (Exception e) {
            LoggingUtil.error("Database Creating UserEntity", e);
        }
    }

    @Override
    public void update(long id, UserEntity p) {
        try {
            String sql = "INSERT INTO users VALUES ( uuid text PRIMARY KEY, money real )";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.execute();
        } catch (Exception e) {
            LoggingUtil.error("Database Creating UserEntity", e);
        }
    }

    @Override
    public void delete(long id) {

    }
}
