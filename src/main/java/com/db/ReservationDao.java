package com.db;

import com.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDao {

    /** all reservations */
    public List<Reservation> findAll() {
        String sql = "SELECT id, customer_name, workspace_id, res_date, start_time, end_time FROM reservation ORDER BY id";
        List<Reservation> list = new ArrayList<>();
        try (Connection c = Db.get();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            System.out.println("DB error (findAll reservations): " + e.getMessage());
        }
        return list;
    }

    /** for a given workspace */
    public List<Reservation> findByWorkspace(int workspaceId) {
        String sql = "SELECT id, customer_name, workspace_id, res_date, start_time, end_time FROM reservation WHERE workspace_id = ? ORDER BY id";
        List<Reservation> list = new ArrayList<>();
        try (Connection c = Db.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, workspaceId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        } catch (SQLException e) {
            System.out.println("DB error (find reservations by workspace): " + e.getMessage());
        }
        return list;
    }

    /** insert; returns generated reservation id or -1 */
    public int insert(Reservation r) {
        String sql = """
                INSERT INTO reservation(customer_name, workspace_id, res_date, start_time, end_time)
                VALUES(?,?,?,?,?)
                """;
        try (Connection c = Db.get();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, r.getCustomerName());
            ps.setInt(2, r.getWorkspaceId());
            ps.setDate(3, Date.valueOf(r.getDate()));       // r.getDate() is String "yyyy-mm-dd"
            ps.setTime(4, Time.valueOf(r.getStartTime()));  // "HH:mm"
            ps.setTime(5, Time.valueOf(r.getEndTime()));    // "HH:mm"
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("DB error (insert reservation): " + e.getMessage());
        }
        return -1;
    }

    /** cancel by id */
    public boolean delete(int id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        try (Connection c = Db.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("DB error (delete reservation): " + e.getMessage());
            return false;
        }
    }

    private Reservation map(ResultSet rs) throws SQLException {
        // convert DB row to your existing Reservation class (String fields)
        return new Reservation(
                rs.getString("customer_name"),
                rs.getInt("workspace_id"),
                rs.getDate("res_date").toString(),
                rs.getTime("start_time").toString(),
                rs.getTime("end_time").toString()
        ) {
            // We lose DB id in your constructor. If you want, add a setter or
            // create an overloaded Reservation constructor with explicit id.
        };
    }

    public Integer findWorkspaceIdForReservation(int resId) {
        String sql = "SELECT workspace_id FROM reservation WHERE id = ?";
        try (var c = Db.get();
             var ps = c.prepareStatement(sql)) {
            ps.setInt(1, resId);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            System.out.println("DB error (findWorkspaceIdForReservation): " + e.getMessage());
        }
        return null;
    }

}
