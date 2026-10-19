package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonneDAO {

    // Retourne l'id du sexe, ou -1 s'il n'existe pas
    private int findSexeId(Connection con, String libelle) throws SQLException {
        if (libelle == null) return -1;
        String sql = "SELECT id FROM sexe WHERE LOWER(libelle) = LOWER(?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, libelle.trim());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt("id") : -1;
            }
        }
    }

    public void save(Artiste p) {
        String sql = "INSERT INTO personne (id, nom, prenom, classe, id_sexe) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection()) {
            String libelle = (p.getGenre() == null) ? null : p.getGenre().getLibelle();
            int idSexe = findSexeId(con, libelle);
            if (idSexe == -1) {
                throw new IllegalArgumentException(
                    "Sexe invalide : '" + libelle + "' (absent de la table sexe)");
            }
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, p.getId());
                ps.setString(2, p.getNom());
                ps.setString(3, p.getAge());
                ps.setString(4, "classe");
                ps.setInt(5, idSexe);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement : " + e.getMessage(), e);
        }
    }
}
