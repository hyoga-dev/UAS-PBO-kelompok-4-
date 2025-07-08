package toko.model;

import toko.utils.KoneksiDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import toko.utils.Format;

public class ProdukModel {
    private List<Produk> listProduk = new ArrayList<>();

    private Connection getConnection() throws SQLException {
        return KoneksiDB.getKoneksi();
    }
    
    public List<TransaksiInfo> getSemuaTransaksi() {
        List<TransaksiInfo> list = new ArrayList<>();
        

        String sql = "SELECT transaksi.id_transaksi, transaksi.created_at, COALESCE(SUM(transaksi_detail.subtotal), 0) AS total " +
             "FROM transaksi " +
             "LEFT JOIN transaksi_detail ON transaksi.id_transaksi = transaksi_detail.id_transaksi " +
             "GROUP BY transaksi.id_transaksi, transaksi.created_at " +
             "ORDER BY transaksi.created_at DESC";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_transaksi");
                Timestamp createdAt = rs.getTimestamp("created_at");
                int total = rs.getInt("total");
                
                String subtotalFormatted = Format.formatRibuan(total);                
                TransaksiInfo info = new TransaksiInfo(id, createdAt, subtotalFormatted);
                list.add(info);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void tampilkanDetailTransaksi(int idTransaksi, JTable table) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nama Produk");
        model.addColumn("Jumlah");
        model.addColumn("Subtotal");

        try {
            Connection conn = KoneksiDB.getKoneksi();
            String sql = "SELECT td.id, p.nama AS nama_produk, td.jumlah, td.subtotal " +
                         "FROM transaksi_detail td " +
                         "JOIN produk p ON td.kode_produk = p.kode " +
                         "WHERE td.id_transaksi = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idTransaksi);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idDetail = rs.getInt("id");
                String namaProduk = rs.getString("nama_produk");
                int jumlah = rs.getInt("jumlah");
                int subtotal = rs.getInt("subtotal");
                
                String subtotalFormatted = Format.formatRibuan(subtotal);
                model.addRow(new Object[]{idDetail, namaProduk, jumlah,"Rp." + subtotalFormatted});
            }

            table.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal menampilkan detail transaksi: " + e.getMessage());
        }
    }

    public boolean hapusTransaksi(int idTransaksi) {
        String sql = "DELETE FROM transaksi WHERE id_transaksi = ?";
        int result = JOptionPane.showConfirmDialog(
            null,
            "Apakah Anda yakin ingin menghapus Transaksi? Tindakan in tidak dapat diurungkan!!",
            "Konfirmasi Simpan",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            try {
                Connection conn = getConnection(); // ambil koneksi dari class KoneksiDB
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, idTransaksi);

                int affectedRows = ps.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Transaksi dengan ID " + idTransaksi + " berhasil dihapus.");
                    return true;
                } else {
                    System.out.println("Transaksi dengan ID " + idTransaksi + " tidak ditemukan.");
                    return false;
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } 
        } else if (result == JOptionPane.NO_OPTION) {
            System.out.println("Pengguna pilih NO");
        } else {
            System.out.println("Dialog ditutup");
        }
        
        return false;
    }

    
    private int transaksiIdTerkini;
    public int getTransaksiTerkini() {
        return transaksiIdTerkini;
    }
    
    public boolean simpanTransaksi(String namaPembeli, List<Transaksi> daftarProduk) {
        String sqlTransaksi = "INSERT INTO transaksi (created_at, pembuat_transaksi) VALUES (NOW(), ?)";
        String sqlDetail = "INSERT INTO transaksi_detail (id_transaksi, kode_produk, jumlah, subtotal) VALUES (?, ?, ?, ?)";
        String sqlUpdateStok = "UPDATE produk SET stock = stock - ? WHERE kode = ?";

        Connection conn = null;
        PreparedStatement psTransaksi = null;
        PreparedStatement psDetail = null;
        PreparedStatement psUpdateStok = null;
        ResultSet rs = null;

        try {
            conn = KoneksiDB.getKoneksi();
            conn.setAutoCommit(false); // Start transaction

            // 1. Simpan transaksi utama
            psTransaksi = conn.prepareStatement(sqlTransaksi, Statement.RETURN_GENERATED_KEYS);
            psTransaksi.setString(1, namaPembeli);
            psTransaksi.executeUpdate();

            rs = psTransaksi.getGeneratedKeys();
            if (rs.next()) {
                int idTransaksi = rs.getInt(1);
                transaksiIdTerkini = idTransaksi;

                // 2. Simpan detail transaksi
                psDetail = conn.prepareStatement(sqlDetail);
                psUpdateStok = conn.prepareStatement(sqlUpdateStok);

                for (Transaksi pt : daftarProduk) {
                    // Simpan ke transaksi_detail
                    psDetail.setInt(1, idTransaksi);
                    psDetail.setInt(2, pt.getIdProduk());
                    psDetail.setInt(3, pt.getJumlah());
                    psDetail.setInt(4, pt.getSubtotal());
                    psDetail.addBatch();

                    // Update stok
                    psUpdateStok.setInt(1, pt.getJumlah());
                    psUpdateStok.setInt(2, pt.getIdProduk());
                    psUpdateStok.addBatch();
                }

                psDetail.executeBatch();
                psUpdateStok.executeBatch();

                conn.commit(); // Semua berhasil
                return true;
            } else {
                conn.rollback();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (psTransaksi != null) psTransaksi.close();
                if (psDetail != null) psDetail.close();
                if (psUpdateStok != null) psUpdateStok.close();
                if (conn != null) conn.setAutoCommit(true);
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    
    public boolean updateProduk(String kode, Produk p) {
        String sql = "UPDATE produk SET nama = ?, harga = ?, stock = ?, id_kategori = ? WHERE kode = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNama());
            ps.setInt(2, p.getHarga());
            ps.setInt(3, p.getStock());
            ps.setInt(4, p.getKategoriIndex() + 1);
            ps.setString(5, kode);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error update produk: " + e.getMessage());
            return false;
        }
    }
    
    public boolean updateKategori(Produk p) {
        String sql = "UPDATE kategori SET nama_kategori = ? WHERE id_kategori = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getKategori());
            ps.setInt(2, p.getKategoriIndex() + 1);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error update produk: " + e.getMessage());
            return false;
        }
    }

    public List<Produk> getAllProduk() {
        listProduk.clear();

        String query = "SELECT produk.kode, produk.nama, produk.harga, produk.stock, kategori.nama_kategori AS kategori FROM produk LEFT JOIN kategori ON produk.id_kategori = kategori.id_kategori ORDER BY produk.kode ASC";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Produk p = new Produk(
                    rs.getInt("kode"),
                    rs.getString("nama"),
                    rs.getInt("harga"),
                    rs.getInt("stock"),
                    rs.getString("kategori")
                );
                listProduk.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error ambil produk: " + e.getMessage());
        }
        return listProduk;
    }

    private String kolomFilter = "kode";
    private String operatorFilter = ""; 
    private String nilaiFilter = "";
    private boolean ascending = true;

    public void setKolomFilter(String kolomFilter) {
        this.kolomFilter = kolomFilter;
    }
    
    public void setOperatorFilter(String operatorFilter) {
        this.operatorFilter = operatorFilter;
    }

    public void setNilaiFilter(String nilaiFilter) {
        this.nilaiFilter = nilaiFilter;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    public List<Produk> getProdukFiltered() {
        listProduk.clear();

        // Kolom valid untuk produk (tanpa id_kategori), kita pakai nama_kategori dari tabel kategori
        List<String> kolomValid = List.of("kode", "nama", "stock", "harga", "kategori");
        List<String> operatorValid = List.of("=", "<", ">", "<=", ">=", "LIKE");

        String kolomQuery = kolomFilter;

        // Ubah kolomFilter menjadi nama kolom sebenarnya di database
        if ("kategori".equalsIgnoreCase(kolomFilter)) {
            kolomQuery = "kategori.nama_kategori"; // Gunakan nama_kategori di tabel kategori
        } else if (!kolomValid.contains(kolomFilter)) {
            System.out.println("Kolom tidak valid.");
            return listProduk;
        } else {
            kolomQuery = "produk." + kolomFilter;
        }

        boolean adaFilter = nilaiFilter != null && !nilaiFilter.trim().isEmpty();
        boolean gunakanFilter = adaFilter && operatorFilter != null && !operatorFilter.trim().isEmpty();

        if (gunakanFilter && !operatorValid.contains(operatorFilter.toUpperCase())) {
            System.out.println("Operator tidak valid.");
            return listProduk;
        }

        StringBuilder query = new StringBuilder();
        query.append("SELECT produk.kode, produk.nama, produk.harga, produk.stock, kategori.nama_kategori AS kategori ");
        query.append("FROM produk LEFT JOIN kategori ON produk.id_kategori = kategori.id_kategori ");

        if (gunakanFilter) {
            query.append("WHERE ")
                 .append(kolomQuery)
                 .append(" ")
                 .append(operatorFilter)
                 .append(" ? ");
        }

        query.append("ORDER BY ")
             .append(kolomQuery)
             .append(ascending ? " ASC" : " DESC");

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            if (gunakanFilter) {
                if ("LIKE".equalsIgnoreCase(operatorFilter)) {
                    stmt.setString(1, "%" + nilaiFilter + "%");
                } else {
                    stmt.setString(1, nilaiFilter);
                }
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Produk p = new Produk(
                    rs.getInt("kode"),
                    rs.getString("nama"),
                    rs.getInt("harga"),
                    rs.getInt("stock"),
                    rs.getString("kategori")
                );
                listProduk.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error ambil produk dengan filter: " + e.getMessage());
        }

        return listProduk;
    }



    public void tampilkanData(JTable tabelUser) {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Kode");
        tableModel.addColumn("Nama");
        tableModel.addColumn("Harga");
        tableModel.addColumn("Stock");
        tableModel.addColumn("Kategori");

        List<Produk> daftarProduk = getProdukFiltered();
        for (Produk p : daftarProduk) {
            tableModel.addRow(new Object[]{
                p.getKode(), p.getNama(), p.getHarga(), p.getStock(), p.getKategori()
            });
        }

        tabelUser.setModel(tableModel);
//        aturTabel();
    }

    public List<Produk> getListProduk() {
        return listProduk;
    }
    private static void isiComboBox(JComboBox<String> itemBox, String query, String columnName, java.awt.Component parentComponent) {
        try (Connection conn = KoneksiDB.getKoneksi();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            while (rs.next()) {
                String nama = rs.getString(columnName);
                if (nama.equals("id_kategori")) nama = "kategori";
                model.addElement(nama);
            }
            itemBox.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(parentComponent, "Gagal mengisi data: " + e.getMessage());
        }
    }
    
    private static void isiCombo(JComboBox<String> itemBox, List<String> kolom, java.awt.Component parentComponent) {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (String k : kolom) {
            model.addElement(k);
        }
        itemBox.setModel(model);
    }
    
    public static void isiOperator(JComboBox<String> itemBox, java.awt.Component parentComponent) {
        List<String> namaKolom = new ArrayList<String>();
        namaKolom.add("");
        namaKolom.add("=");
        namaKolom.add("<");
        namaKolom.add("<=");
        namaKolom.add(">");
        namaKolom.add(">=");
        namaKolom.add("!=");
        isiCombo(itemBox, namaKolom, parentComponent);
    }
    
    public static void isiNama(JComboBox<String> itemBox, java.awt.Component parentComponent) {
        String query = "SELECT nama FROM produk ORDER BY nama ASC";
        isiComboBox(itemBox, query, "nama", parentComponent);
    }

    public static void isiKolom(JComboBox<String> itemBox, java.awt.Component parentComponent) {
        String query = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'db_toko' AND TABLE_NAME = 'produk'";
        isiComboBox(itemBox, query, "COLUMN_NAME", parentComponent);
    }

    public int tampilkanHargaByNama(String selectedNama) {
        if (selectedNama == null || selectedNama.isEmpty()) return 0;

        int harga = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConnection(); // pastikan KoneksiDB sudah ada
            String sql = "SELECT harga FROM produk WHERE LOWER(nama) = LOWER(?) LIMIT 1";
            ps = conn.prepareStatement(sql);
            ps.setString(1, selectedNama);
            rs = ps.executeQuery();

            if (rs.next()) {
                harga = rs.getInt("harga");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }

        return harga;
    }

    
    public int tampilkanStockByNama(String selectedNama) {
        if (selectedNama == null) return 0;

        String sql = "SELECT stock FROM produk WHERE nama = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, selectedNama);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("stock");
            }

        } catch (SQLException e) {
            System.out.println("Error ambil stok produk: " + e.getMessage());
        }

        return 0;
    }

    
    public int getIdByNama(String selectedNama) {
        if (selectedNama == null) return 0;

        String sql = "SELECT kode FROM produk WHERE nama = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, selectedNama);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("kode");
            }

        } catch (SQLException e) {
            System.out.println("Error ambil ID produk: " + e.getMessage());
        }

        return 0;
    }

    
    public static void isiKategori(JComboBox<String> itemBox, java.awt.Component parentComponent) {
        String query = "SELECT nama_kategori FROM kategori ORDER BY id_kategori ASC";
        isiComboBox(itemBox, query, "nama_kategori", parentComponent);
    }

    public boolean hapusProduk(int id) {
        String sql = "DELETE FROM produk WHERE kode = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            try (Connection conn = getConnection();) {
                if (e.getMessage().contains("foreign key constraint fails")) {
                System.out.println("Gagal hapus produk, karena sudah digunakan di transaksi.");

                // Update nama produk dan set stok = 0
                PreparedStatement updateStmt = conn.prepareStatement(
                    "UPDATE produk SET nama = CONCAT(nama, ' (produk sudah tidak dijual)'), stock = 0 WHERE kode = ?"
                );
                updateStmt.setString(1, String.valueOf(id));
                updateStmt.executeUpdate();

                JOptionPane.showMessageDialog(null,
                    "Produk tidak bisa dihapus karena sudah pernah digunakan.\nProduk ditandai sebagai 'tidak dijual' dan stok diset 0.");
                return true;
                } else {
                    // Error lain
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Terjadi error saat menghapus produk: " + e.getMessage());
                    return false;
                } 
            } catch (SQLException er) {
                JOptionPane.showMessageDialog(null, "Terjadi error saat menghapus produk: " + er.getMessage());
                return false;
            }
            
        }
    }
    
    public boolean hapusProdukKategoriNull() {
        String sql = "DELETE FROM produk WHERE id_kategori IS NULL";

        try (Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " produk dengan id_kategori NULL berhasil dihapus.");
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error hapus produk dengan id_kategori NULL: " + e.getMessage());
            return false;
        }
    }

    
    public boolean hapusKategori(int id) {
        String sqlClearProdukKategori = "UPDATE produk SET id_kategori = NULL WHERE id_kategori = ?";
        String sqlDeleteKategori = "DELETE FROM kategori WHERE id_kategori = ?";
        String sqlUpdateKategori = "UPDATE kategori SET id_kategori = id_kategori - 1 WHERE id_kategori > ?";
        String sqlGetMax = "SELECT MAX(id_kategori) FROM kategori";
        String sqlResetAI = "ALTER TABLE kategori AUTO_INCREMENT = ?";

        try (Connection conn = getConnection();
             PreparedStatement psClearProduk = conn.prepareStatement(sqlClearProdukKategori);
             PreparedStatement psDeleteKategori = conn.prepareStatement(sqlDeleteKategori);
             PreparedStatement psUpdateKategori = conn.prepareStatement(sqlUpdateKategori)) {

            conn.setAutoCommit(false); // mulai transaction

            // 1. Clear id_kategori di tabel produk (set ke NULL)
            psClearProduk.setInt(1, id);
            psClearProduk.executeUpdate();

            // 2. Hapus kategori
            psDeleteKategori.setInt(1, id);
            int rowsAffected = psDeleteKategori.executeUpdate();

            if (rowsAffected > 0) {
                // 3. Update id_kategori yang di atasnya
                psUpdateKategori.setInt(1, id);
                psUpdateKategori.executeUpdate();

                // 4. Ambil MAX id_kategori terbaru
                int nextAutoIncrement = 1;
                try (PreparedStatement psMax = conn.prepareStatement(sqlGetMax);
                     ResultSet rs = psMax.executeQuery()) {
                    if (rs.next()) {
                        nextAutoIncrement = rs.getInt(1) + 1;
                    }
                }

                // 5. Reset Auto Increment
                try (PreparedStatement psReset = conn.prepareStatement(sqlResetAI)) {
                    psReset.setInt(1, nextAutoIncrement);
                    psReset.executeUpdate();
                }

                conn.commit();
                return true;

            } else {
                conn.rollback();
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error hapus kategori: " + e.getMessage());
            return false;
        }
    }

    public boolean insertProduk(Produk p) {
        String sql = "INSERT INTO produk (nama, harga, stock, id_kategori) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNama());
            ps.setInt(2, p.getHarga());
            ps.setInt(3, p.getStock());
            ps.setInt(4, p.getKategoriIndex() + 1);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // 1062 = Duplicate entry
                JOptionPane.showMessageDialog(null, 
                    "Nama kategori sudah digunakan!", 
                    "Error Duplikat", 
                    JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Error database: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            System.out.println("Error insert produk: " + e.getMessage());
            return false;
        }
    }
    
    public boolean insertKategori(Produk p) {
        String sql = "INSERT INTO kategori (nama_kategori) VALUES (?)";
        try (Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getKategori());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error insert produk: " + e.getMessage());
            return false;
        }
    }
    
    public boolean updateKategoriNull(int idKategoriBaru) {
        String sql = "UPDATE produk SET id_kategori = ? WHERE id_kategori IS NULL";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idKategoriBaru);
            int affectedRows = ps.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal update produk null: " + e.getMessage());
            return false;
        }
    }
}
