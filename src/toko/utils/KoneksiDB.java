package toko.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KoneksiDB {
    public static Connection getKoneksi() {
        Connection koneksi = null;
        try {
            String url = "jdbc:mysql://localhost:3306/db_toko";
            String user = "root";
            String password = ""; // ganti sesuai password MySQL kamu
            koneksi = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
        }
        return koneksi;
    }
}
