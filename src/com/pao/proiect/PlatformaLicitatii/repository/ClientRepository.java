package com.pao.proiect.PlatformaLicitatii.repository;

import com.pao.proiect.PlatformaLicitatii.model.Client;
import com.pao.proiect.PlatformaLicitatii.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepository {

    private final Connection connection =
            DatabaseConnection.getInstance().getConnection();

    public void save(Client c) {

        String sql = """
            INSERT INTO client (nume, prenume, email, username, buget)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, c.getNume());
            ps.setString(2, c.getPrenume());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getUsername());
            ps.setDouble(5, c.getBuget());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Client> findById(int id) {

        String sql = "SELECT * FROM client WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Client c = new Client(
                        rs.getInt("id"),
                        rs.getString("nume"),
                        rs.getString("prenume"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getDouble("buget")
                );

                return Optional.of(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public List<Client> findAll() {

        List<Client> list = new ArrayList<>();

        String sql = "SELECT * FROM client";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Client c = new Client(
                        rs.getInt("id"),
                        rs.getString("nume"),
                        rs.getString("prenume"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getDouble("buget")
                );

                list.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void update(Client c) {

        String sql = """
            UPDATE client
            SET nume=?, prenume=?, email=?, username=?, buget=?
            WHERE id=?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, c.getNume());
            ps.setString(2, c.getPrenume());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getUsername());
            ps.setDouble(5, c.getBuget());
            ps.setInt(6, c.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {

        String sql = "DELETE FROM client WHERE id=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}