package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    // Chemin ABSOLU : sinon le fichier est créé dans le dossier de lancement de Tomcat
    private static final String URL = "jdbc:sqlite:/home/goldman/sprint-base-test.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            initSchema();
        } catch (Exception e) {
            throw new RuntimeException("Initialisation SQLite impossible", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection(URL);
        try (Statement st = con.createStatement()) {
            st.execute("PRAGMA foreign_keys = ON"); // désactivé par défaut dans SQLite
        }
        return con;
    }

    // Crée les tables et les données de base au premier lancement
    private static void initSchema() throws SQLException {
        try (Connection con = getConnection(); Statement st = con.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS sexe ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "libelle TEXT NOT NULL UNIQUE)");
            st.execute("CREATE TABLE IF NOT EXISTS personne ("
                    + "id INTEGER PRIMARY KEY, "
                    + "nom TEXT NOT NULL, "
                    + "prenom TEXT, "
                    + "classe TEXT, "
                    + "id_sexe INTEGER NOT NULL REFERENCES sexe(id))");
            st.execute("INSERT OR IGNORE INTO sexe (libelle) VALUES ('Masculin'), ('Feminin')");
        }
    }
}
