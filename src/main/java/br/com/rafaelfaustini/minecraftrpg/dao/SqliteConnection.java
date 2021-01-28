package br.com.rafaelfaustini.minecraftrpg.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.com.rafaelfaustini.minecraftrpg.MinecraftRpg;

public class SqliteConnection {

    private Connection con = null;

    public Connection openConnection() throws SQLException {
        MinecraftRpg plugin = MinecraftRpg.getPlugin(MinecraftRpg.class);

        File file = new File(plugin.getDataFolder(), "database.db");

        String URL = "jdbc:sqlite:" + file;

        con = DriverManager.getConnection(URL);

        return con;
    }

    public void close() throws SQLException {
        if (con != null) {
            con.close();
        }
    }

    public Connection getCon() {
        return con;
    }
}
