// package br.com.rafaelfaustini.minecraftrpg.dao;

// import java.io.File;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.SQLException;

// import br.com.rafaelfaustini.minecraftrpg.MinecraftRpg;

// public class SqliteConnection {

//     private Connection con = null;

//     public Connection openConnection() throws SQLException {
//         try {
//             MinecraftRpg plugin = MinecraftRpg.getPlugin(MinecraftRpg.class);

//             Class.forName("org.sqlite.JDBC"); // Unhandled Exception ClassNotFoundException

//             File file = new File(plugin.getDataFolder(), "database.db");

//             String URL = "jdbc:sqlite:" + file;

//             con = DriverManager.getConnection(URL); // Unhandled Exception SQLException

//             return con;
//         } catch (ClassNotFoundException e) {
//             // TODO: handle exception
//         }
//     }

//     public void close() throws SQLException {
//         if (con != null) {
//             con.close();
//             con = null;
//         }
//     }
// }
