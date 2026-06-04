package com.pao.proiect.PlatformaLicitatii.repository;

import com.pao.proiect.PlatformaLicitatii.model.Licitatie;
import com.pao.proiect.PlatformaLicitatii.model.StatusLicitatie;
import com.pao.proiect.PlatformaLicitatii.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LicitatieRepository {

    private final Connection connection =
            DatabaseConnection.getInstance().getConnection();

    public void save(Licitatie l) {

        try {

            String sqlProdus = """
                INSERT INTO produs (id, nume, pret_start, categorie)
                VALUES (?, ?, ?, ?)
            """;

            try (PreparedStatement ps =
                         connection.prepareStatement(sqlProdus)) {

                ps.setInt(1, l.getProdus().getId());
                ps.setString(2, l.getProdus().getNume());
                ps.setDouble(3, l.getProdus().getPretStart());

                ps.setString(
                        4,
                        l.getProdus().getCategorie().nume()
                );

                ps.executeUpdate();
            }

            String sqlLic = """
                INSERT INTO licitatie (id, produs_id, status)
                VALUES (?, ?, ?)
            """;

            try (PreparedStatement ps =
                         connection.prepareStatement(sqlLic)) {

                ps.setInt(1, l.getId());
                ps.setInt(2, l.getProdus().getId());
                ps.setString(3, l.getStatus().name());

                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Licitatie> findById(int id) {

        String sql = "SELECT * FROM licitatie WHERE id = ?";

        try (PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Licitatie l = new Licitatie(
                        rs.getInt("id"),
                        null,
                        StatusLicitatie.valueOf(
                                rs.getString("status")
                        )
                );

                return Optional.of(l);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public List<Licitatie> findAll() {

        List<Licitatie> list = new ArrayList<>();

        String sql = "SELECT * FROM licitatie";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Licitatie l = new Licitatie(
                        rs.getInt("id"),
                        null,
                        StatusLicitatie.valueOf(
                                rs.getString("status")
                        )
                );

                list.add(l);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void updateStatus(int id, String status) {

        String sql =
                "UPDATE licitatie SET status = ? WHERE id = ?";

        try (PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateOfertaStatus(int ofertaId,
                                   String status) {

        String sql =
                "UPDATE oferta SET status = ? WHERE id = ?";

        try (PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, ofertaId);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void raportLicitatiiCuNumarOferte() {

        String sql = """
            SELECT 
                l.id AS licitatie_id,
                p.nume AS produs,
                l.status,
                COUNT(o.id) AS total_oferte
            FROM licitatie l
            JOIN produs p ON l.produs_id = p.id
            LEFT JOIN oferta o ON o.licitatie_id = l.id
            GROUP BY l.id, p.nume, l.status
            ORDER BY total_oferte DESC
        """;

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            boolean ok = false;

            while (rs.next()) {

                ok = true;

                System.out.println(
                        "Licitatie " +
                        rs.getInt("licitatie_id") +
                        " | produs=" +
                        rs.getString("produs") +
                        " | oferte=" +
                        rs.getInt("total_oferte") +
                        " | status=" +
                        rs.getString("status")
                );
            }

            if (!ok)
                System.out.println("Nu exista date!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {

        String sql =
                "DELETE FROM licitatie WHERE id = ?";

        try (PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void finalizeazaLicitatie(int licitatieId, int clientId, int ofertaId, double sumaCastigata) {

    Connection conn = DatabaseConnection.getInstance().getConnection();

    try {
        conn.setAutoCommit(false);

        //update licitatie
        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE licitatie SET status = 'INCHISA' WHERE id = ?")) {
            ps.setInt(1, licitatieId);
            ps.executeUpdate();
        }

        //update oferta castigatoare
        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE oferta SET status = 'ACCEPTATA' WHERE id = ?")) {
            ps.setInt(1, ofertaId);
            ps.executeUpdate();
        }

        //update buget client
        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE client SET buget = buget - ? WHERE id = ?")) {
            ps.setDouble(1, sumaCastigata);
            ps.setInt(2, clientId);
            ps.executeUpdate();
        }

        conn.commit();

    } catch (Exception e) {
        try {
            conn.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        throw new RuntimeException("Eroare tranzactie licitatie!", e);

    } finally {
        try {
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
}