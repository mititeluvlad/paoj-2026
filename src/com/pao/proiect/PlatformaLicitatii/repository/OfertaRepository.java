package com.pao.proiect.PlatformaLicitatii.repository;

import com.pao.proiect.PlatformaLicitatii.model.Oferta;
import com.pao.proiect.PlatformaLicitatii.model.StatusOferta;
import com.pao.proiect.PlatformaLicitatii.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OfertaRepository {

    private final Connection connection =
            DatabaseConnection.getInstance().getConnection();

    public void save(Oferta o, int clientId, int licitatieId) {

        String sql = """
            INSERT INTO oferta (suma, data, status, client_id, licitatie_id)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setDouble(1, o.getSuma());
            ps.setString(2, o.getData().toString());
            ps.setString(3, o.getStatus().name());
            ps.setInt(4, clientId);
            ps.setInt(5, licitatieId);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Oferta> findById(int id) {

        String sql = "SELECT * FROM oferta WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Oferta o = new Oferta(
                        rs.getInt("id"),
                        null, // client îl iei în service
                        rs.getDouble("suma"),
                        StatusOferta.valueOf(rs.getString("status"))
                );

                return Optional.of(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public List<Oferta> findAll() {

        List<Oferta> list = new ArrayList<>();

        String sql = "SELECT * FROM oferta";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Oferta o = new Oferta(
                        rs.getInt("id"),
                        null,
                        rs.getDouble("suma"),
                        StatusOferta.valueOf(rs.getString("status"))
                );

                list.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public void updateStatus(int id, String status) {

        String sql = "UPDATE oferta SET status = ? WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {

        String sql = "DELETE FROM oferta WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertOferta(int clientId,
                         int licitatieId,
                         double suma) {

    String sql = """
        INSERT INTO oferta (suma, status, client_id, licitatie_id, data)
        VALUES (?, 'PENDING', ?, ?, datetime('now'))
    """;

    try (PreparedStatement ps =
                 connection.prepareStatement(sql)) {

        ps.setDouble(1, suma);
        ps.setInt(2, clientId);
        ps.setInt(3, licitatieId);

        ps.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void raportTopClientiDupaOferte() {

    String sql = """
        SELECT 
            c.username AS client,
            COUNT(o.id) AS nr_oferte,
            SUM(o.suma) AS total_suma,
            GROUP_CONCAT(DISTINCT l.id) AS licitatii
        FROM oferta o
        JOIN client c ON o.client_id = c.id
        JOIN licitatie l ON o.licitatie_id = l.id
        GROUP BY c.id, c.username
        ORDER BY total_suma DESC
    """;

    try (Statement st = connection.createStatement();
         ResultSet rs = st.executeQuery(sql)) {

        boolean ok = false;

        while (rs.next()) {

            ok = true;

            System.out.println(
                    "Client=" +
                    rs.getString("client") +
                    " | nr_oferte=" +
                    rs.getInt("nr_oferte") +
                    " | total=" +
                    rs.getDouble("total_suma") +
                    " | licitatii=" +
                    rs.getString("licitatii")
            );
        }

        if (!ok)
            System.out.println("Nu exista date!");

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void raportOferteComplete() {

    String sql = """
        SELECT 
            o.id AS oferta_id,
            o.suma,
            o.status,
            o.client_id,
            p.nume AS produs,
            o.licitatie_id
        FROM oferta o
        JOIN licitatie l ON o.licitatie_id = l.id
        JOIN produs p ON l.produs_id = p.id
        ORDER BY o.suma DESC
    """;

    try (Statement st = connection.createStatement();
         ResultSet rs = st.executeQuery(sql)) {

        boolean ok = false;

        while (rs.next()) {

            ok = true;

            System.out.println(
                    "Oferta " +
                    rs.getInt("oferta_id") +
                    " | suma=" +
                    rs.getDouble("suma") +
                    " | client=" +
                    rs.getInt("client_id") +
                    " | produs=" +
                    rs.getString("produs") +
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
}