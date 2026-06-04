package com.pao.proiect.PlatformaLicitatii.repository;

import com.pao.proiect.PlatformaLicitatii.model.Administrator;
import com.pao.proiect.PlatformaLicitatii.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdministratorRepository {

    private final Connection connection =
            DatabaseConnection.getInstance().getConnection();

    public void save(Administrator a) {

        String sql = """
            INSERT INTO administrator (nume, prenume, email, username, departament)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, a.getNume());
            ps.setString(2, a.getPrenume());
            ps.setString(3, a.getEmail());
            ps.setString(4, a.getUsername());
            ps.setString(5, a.getDepartament());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Administrator> findById(int id) {

        String sql = "SELECT * FROM administrator WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Administrator a = new Administrator(
                        rs.getInt("id"),
                        rs.getString("nume"),
                        rs.getString("prenume"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("departament")
                );

                return Optional.of(a);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public List<Administrator> findAll() {

        List<Administrator> list = new ArrayList<>();

        String sql = "SELECT * FROM administrator";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Administrator a = new Administrator(
                        rs.getInt("id"),
                        rs.getString("nume"),
                        rs.getString("prenume"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("departament")
                );

                list.add(a);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void update(Administrator a) {

        String sql = """
            UPDATE administrator
            SET nume=?, prenume=?, email=?, username=?, departament=?
            WHERE id=?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, a.getNume());
            ps.setString(2, a.getPrenume());
            ps.setString(3, a.getEmail());
            ps.setString(4, a.getUsername());
            ps.setString(5, a.getDepartament());
            ps.setInt(6, a.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {

        String sql = "DELETE FROM administrator WHERE id=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}