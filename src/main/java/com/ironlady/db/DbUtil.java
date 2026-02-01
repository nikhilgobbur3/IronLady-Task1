package com.ironlady.db;

import com.ironlady.model.ChatMessage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbUtil {
    // TODO: Update these constants to match your local MySQL setup
    private static final String DB_NAME = "ironlady";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME + "?useSSL=false&serverTimezone=UTC";
    private static final String DB_ADMIN_URL = "jdbc:mysql://localhost:3306/?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "22556688Ay@";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            ensureDatabaseExists();
            initDb();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    private static void ensureDatabaseExists() {
        String sql = "CREATE DATABASE IF NOT EXISTS " + DB_NAME + " CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci";
        try (Connection conn = DriverManager.getConnection(DB_ADMIN_URL, DB_USER, DB_PASS);
             Statement st = conn.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            // Log but continue: if we can't create DB, later getConnection() will fail and show error
            e.printStackTrace();
        }
    }

    private static void initDb() {
        String sql = "CREATE TABLE IF NOT EXISTS chat_history (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "sender VARCHAR(20) NOT NULL," +
                "message TEXT NOT NULL," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ") ENGINE=InnoDB;";
        try (Connection conn = getConnection(); Statement st = conn.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveMessage(String sender, String message) {
        String sql = "INSERT INTO chat_history (sender, message) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sender);
            ps.setString(2, message);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<ChatMessage> getHistory(int limit) {
        List<ChatMessage> out = new ArrayList<>();
        String sql = "SELECT id, sender, message, created_at FROM chat_history ORDER BY id DESC LIMIT ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(new ChatMessage(
                            rs.getInt("id"),
                            rs.getString("sender"),
                            rs.getString("message"),
                            rs.getTimestamp("created_at")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Return in chronological order
        List<ChatMessage> chronological = new ArrayList<>();
        for (int i = out.size() - 1; i >= 0; i--) chronological.add(out.get(i));
        return chronological;
    }
}
