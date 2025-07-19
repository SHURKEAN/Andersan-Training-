package com.db;

import com.Workspace;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WorkspaceDao {

    public List<Workspace> findAll() {
        String sql = "SELECT id, type, is_available FROM workspace ORDER BY id";
        List<Workspace> list = new ArrayList<>();
        try (Connection c = Db.get();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String type = rs.getString("type");
                boolean avail = rs.getBoolean("is_available");
                list.add(new Workspace(id, type, avail));
            }
        } catch (SQLException e) {
            System.out.println("DB error (findAll workspaces): " + e.getMessage());
        }
        return list;
    }

    public Optional<Workspace> findById(int id) {
        String sql = "SELECT id, type, is_available FROM workspace WHERE id = ?";
        try (Connection c = Db.get();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Workspace(
                            rs.getInt("id"),
                            rs.getString("type"),
                            rs.getBoolean("is_available")));
                }
            }
        } catch (SQLException e) {
            System.out.println("DB error (find workspace): " + e.getMessage());
        }
        return Optional.empty();
    }

    /** insert; returns generated id, or -1 on failure */
    public int insert(String type, boolean available) {
        String sql = "INSERT INTO workspace(type, is_available) VALUES(?,?)";
        try (Connection c = Db.get();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, type);
            ps.setBoolean(2, available);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("DB error (insert workspace): " + e.getMessage());
        }
        return -1;
    }

    /** toggle availability */
    public void updateAvailability(int id, boolean available) {
        String sql = "UPDATE workspace SET is_available = ? WHERE id = ?";
        try (Connection c = Db.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setBoolean(1, available);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("DB error (update availability): " + e.getMessage());
        }
    }

    /** delete; returns true if row removed */
    public boolean delete(int id) {
        String sql = "DELETE FROM workspace WHERE id = ?";
        try (Connection c = Db.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("DB error (delete workspace): " + e.getMessage());
            return false;
        }
    }
}
