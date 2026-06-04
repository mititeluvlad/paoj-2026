package com.pao.proiect.PlatformaLicitatii.repository;

import com.pao.proiect.PlatformaLicitatii.model.Produs;
import com.pao.proiect.PlatformaLicitatii.model.CategorieProdus;
import com.pao.proiect.PlatformaLicitatii.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdusRepository {

    private final Connection connection =
            DatabaseConnection.getInstance().getConnection();

    public void save(Produs p) {

        String sql = "INSERT INTO produs (nume, pret_start, categorie) VALUES (?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, p.getNume());
            ps.setDouble(2, p.getPretStart());
            ps.setString(3, p.getCategorie().nume());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Produs> findById(int id) {

        String sql = "SELECT * FROM produs WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Produs p = new Produs(
                        rs.getInt("id"),
                        rs.getString("nume"),
                        rs.getDouble("pret_start"),
                        new CategorieProdus(rs.getString("categorie"))
                );

                return Optional.of(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public List<Produs> findAll() {

        List<Produs> list = new ArrayList<>();

        String sql = "SELECT * FROM produs";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Produs p = new Produs(
                        rs.getInt("id"),
                        rs.getString("nume"),
                        rs.getDouble("pret_start"),
                        new CategorieProdus(rs.getString("categorie"))
                );

                list.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void update(Produs p) {

        String sql = "UPDATE produs SET nume=?, pret_start=?, categorie=? WHERE id=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, p.getNume());
            ps.setDouble(2, p.getPretStart());
            ps.setString(3, p.getCategorie().nume());
            ps.setInt(4, p.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(int id) {

        String sql = "DELETE FROM produs WHERE id=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void raportProduseCuLicitatiiSiOferte() {

    String sql = """
        SELECT 
            p.id AS produs_id,
            p.nume,
            p.pret_start,
            p.categorie,
            COUNT(DISTINCT l.id) AS nr_licitatii,
            COUNT(o.id) AS nr_oferte,
            MAX(o.suma) AS oferta_maxima
        FROM produs p
        LEFT JOIN licitatie l ON l.produs_id = p.id
        LEFT JOIN oferta o ON o.licitatie_id = l.id
        GROUP BY p.id, p.nume, p.pret_start, p.categorie
        ORDER BY nr_oferte DESC
    """;

    try (Statement st = connection.createStatement();
         ResultSet rs = st.executeQuery(sql)) {

        boolean ok = false;

        while (rs.next()) {
            ok = true;
            System.out.println(
                    "Produs " + rs.getInt("produs_id") +
                    " | nume=" + rs.getString("nume") +
                    " | pret=" + rs.getDouble("pret_start") +
                    " | categorie=" + rs.getString("categorie") +
                    " | licitatii=" + rs.getInt("nr_licitatii") +
                    " | oferte=" + rs.getInt("nr_oferte") +
                    " | max_oferta=" + rs.getDouble("oferta_maxima")
            );
        }

        if (!ok) {
            System.out.println("Nu exista date!");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}