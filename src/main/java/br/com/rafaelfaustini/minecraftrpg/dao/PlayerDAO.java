package br.com.rafaelfaustini.minecraftrpg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import br.com.rafaelfaustini.minecraftrpg.interfaces.IDao;
import br.com.rafaelfaustini.minecraftrpg.utils.LoggingUtil;

import org.bukkit.entity.Player;

public class PlayerDAO implements IDao<Player> {

    private Connection conexao;

    private void createTable() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS player ( uuid text PRIMARY KEY, money real )";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.execute();
        } catch (Exception e) {
            LoggingUtil.error("Database Creating Player", e);
        }
    }

    public PlayerDAO(Connection con) {
        conexao = con;
        createTable();
    }

    @Override
    public Player get(long id) {
        return null;
    }

    @Override
    public List<Player> getAll() {
        return null;
    }

    @Override
    public void insert(Player player) {

    }

    @Override
    public void update(long id, Player p) {

    }

    @Override
    public void delete(long id) {

    }
}
