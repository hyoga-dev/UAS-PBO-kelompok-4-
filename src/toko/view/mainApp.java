
package toko.view;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Dimension;
import java.awt.Window;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import toko.controller.ProdukController;
import javax.swing.UIManager;
import toko.model.Produk;
import toko.model.ProdukModel;
import toko.model.Transaksi;
import toko.model.TransaksiInfo;
import java.text.SimpleDateFormat;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import static toko.utils.ExportCSV.exportTableToCSV;
import toko.utils.Format;

public class mainApp extends javax.swing.JFrame {
    private final ProdukController controller;
    public static int subTotal;

    public mainApp() {
        initComponents();
        controller = new ProdukController(tabelProduk,(new ProdukModel()));
        initApp();
    }

    private void initApp() {
        
        DefaultComboBoxModel<String> combo = new DefaultComboBoxModel<>();
        combo.addElement("Menaik");
        combo.addElement("Menurun");
        
        comboUrut.setModel(combo);
        
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                jalankanAksi();
            }

            public void removeUpdate(DocumentEvent e) {
                jalankanAksi();
            }

            public void changedUpdate(DocumentEvent e) {
                // Biasanya nggak perlu buat plain text field
            }

            private void jalankanAksi() {
                filter();
            }
        });
        
        spinnerNilai.addChangeListener(e -> {
            filter();
        });
        
        controller.tampilkanData(tabelProduk);
        
        loadComboBoxData();
        setupTableSelectionListener();
        txtSearch.setEnabled(false);
        spinnerNilai.setEnabled(false);
        jScrollPane10.getVerticalScrollBar().setUnitIncrement(20);
        jScrollPane12.getVerticalScrollBar().setUnitIncrement(20);

        updateHargaProduk();
        loadTransaksi();
    }

    private void updateHargaProduk() {
        String selectedNama = (String) comboTransaksi.getSelectedItem();
        int harga = controller.tampilkanHargaByNama(selectedNama);
        labelHargaProduk.setText(String.valueOf(harga));
    }

    private void loadTransaksi() {
        List<TransaksiInfo> semuaTransaksi = controller.getSemuaTransaksi();

        panelHistori.setLayout(new BoxLayout(panelHistori, BoxLayout.Y_AXIS));
        
        panelKeranjang.removeAll();
        panelKeranjang.revalidate();
        panelKeranjang.repaint();

        for (TransaksiInfo t : semuaTransaksi) {
            String waktu = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t.getCreatedAt());
            CardKeranjang card = new CardKeranjang(waktu, "Rp." + t.getTotal(), t.getId());
            panelHistori.add(card);
        }
        
        panelHistori.revalidate();
        panelHistori.repaint();
    }
    
    private void loadComboBoxData() {
        ProdukModel.isiKategori(txtKategoriInsert, this);
        ProdukModel.isiKategori(txtKategoriUpdate, this);
        ProdukModel.isiKategori(comboUbahKategori, this);
        ProdukModel.isiKategori(comboHapusKategori, this);
        ProdukModel.isiKategori(comboUpdateKategoriNULL, this);
        ProdukModel.isiNama(txtKategoriDelete, this);
        ProdukModel.isiNama(comboTransaksi, this);
        ProdukModel.isiKolom(comboBerdasarkan, this);
        ProdukModel.isiOperator(comboOperator, this);
    }

    private void setupTableSelectionListener() {
        tabelProduk.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tabelProduk.getSelectedRow();
                if (row != -1) {
                    String nama = tabelProduk.getValueAt(row, 1).toString();
                    int harga = (int) tabelProduk.getValueAt(row, 2);
                    int stock = (int) tabelProduk.getValueAt(row, 3);
                    String kategori = tabelProduk.getValueAt(row, 4).toString();

                    txtNamaUpdate.setText(nama);
                    txtHargaUpdate.setValue(harga);
                    txtStockUpdate.setValue(stock);
                    txtKategoriUpdate.setSelectedItem(kategori);
                    txtKategoriDelete.setSelectedItem(nama);
                }
            }
        });
    }

    public void kosongkanField() {
        txtTambahKategori.setText("");
        txtUbahKategori.setText("");
        
        txtNamaInsert.setText("");
        txtHargaInsert.setValue(1000);
        txtStockInsert.setValue(10);
        txtKategoriInsert.setSelectedIndex(0);

        txtNamaUpdate.setText("");
        txtHargaUpdate.setValue(0);
        txtStockUpdate.setValue(0);
        txtKategoriUpdate.setSelectedIndex(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane4 = new javax.swing.JScrollPane();
        jLabel3 = new javax.swing.JLabel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane7 = new javax.swing.JScrollPane();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jPanel12 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelProduk = new javax.swing.JTable();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtStockUpdate = new javax.swing.JSpinner();
        txtKategoriDelete = new javax.swing.JComboBox<>();
        txtHargaUpdate = new javax.swing.JSpinner();
        btnDelete = new javax.swing.JButton();
        txtKategoriUpdate = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtStockInsert = new javax.swing.JSpinner();
        jLabel17 = new javax.swing.JLabel();
        txtHargaInsert = new javax.swing.JSpinner();
        btnUpdate = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        txtKategoriInsert = new javax.swing.JComboBox<>();
        txtNamaUpdate = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        btnInsert = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtNamaInsert = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        txtTambahKategori = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnTambahKategori = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        btnUbahKategori = new javax.swing.JButton();
        comboUbahKategori = new javax.swing.JComboBox<>();
        comboHapusKategori = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        btnHapusKategori = new javax.swing.JButton();
        txtUbahKategori = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        hapusNull = new javax.swing.JButton();
        comboUpdateKategoriNULL = new javax.swing.JComboBox<>();
        btnTambahKategoriNULL = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jPanel19 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        comboBerdasarkan = new javax.swing.JComboBox<>();
        jSeparator3 = new javax.swing.JSeparator();
        comboOperator = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        spinnerNilai = new javax.swing.JSpinner();
        jLabel34 = new javax.swing.JLabel();
        btnCari = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        comboUrut = new javax.swing.JComboBox<>();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        scrollTransaksi = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        comboTransaksi = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        spinnerTransaksi = new javax.swing.JSpinner();
        jPanel10 = new javax.swing.JPanel();
        labelHargaProduk = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btnTambahKeranjang = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        panelHistori = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtStock = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        panelKeranjang = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        btnTambahTransaksi = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        labelSubtotal = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        btnTambahTransaksi1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        eksporCsv = new javax.swing.JMenuItem();
        exportExcel = new javax.swing.JMenuItem();
        btnReload = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        lightMode = new javax.swing.JRadioButtonMenuItem();
        darkMode = new javax.swing.JRadioButtonMenuItem();

        jLabel3.setText("jLabel3");
        jScrollPane4.setViewportView(jLabel3);

        jTabbedPane3.setTabPlacement(javax.swing.JTabbedPane.RIGHT);
        jTabbedPane3.addTab("Buat transaksi", jScrollPane5);

        jPanel1.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 862, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 779, Short.MAX_VALUE)
        );

        jScrollPane6.setViewportView(jPanel4);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 862, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 779, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel3);

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 288, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jScrollPane7.setViewportView(jPanel11);

        jSplitPane1.setTopComponent(jScrollPane7);

        jPanel13.setPreferredSize(new java.awt.Dimension(298, 294));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 298, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 410, Short.MAX_VALUE)
        );

        jScrollPane9.setViewportView(jPanel13);

        jPanel12.setPreferredSize(new java.awt.Dimension(298, 298));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 298, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 298, Short.MAX_VALUE)
        );

        jScrollPane8.setViewportView(jPanel12);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("UAS_PBO (kelompok 4)");
        setMinimumSize(new java.awt.Dimension(516, 500));
        setPreferredSize(new java.awt.Dimension(1200, 850));

        jPanel6.setLayout(new java.awt.BorderLayout());

        tabelProduk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelProduk.setMinimumSize(new java.awt.Dimension(400, 80));
        jScrollPane2.setViewportView(tabelProduk);

        jPanel6.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel7.setPreferredSize(new java.awt.Dimension(185, 780));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setText("Hapus Produk");

        txtStockUpdate.setModel(new javax.swing.SpinnerNumberModel());

        txtKategoriDelete.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtHargaUpdate.setModel(new javax.swing.SpinnerNumberModel(500, 500, null, 100));

        btnDelete.setBackground(new java.awt.Color(197, 67, 67));
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        txtKategoriUpdate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel14.setText("Nama");

        jLabel15.setText("Stock");

        jLabel16.setText("Kategori");

        txtStockInsert.setModel(new javax.swing.SpinnerNumberModel(10, 1, 100000, 5));

        jLabel17.setText("Nama");

        txtHargaInsert.setModel(new javax.swing.SpinnerNumberModel(1000, 500, 100000, 100));

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        jLabel18.setText("Harga");

        txtKategoriInsert.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtNamaUpdate.setMinimumSize(new java.awt.Dimension(100, 22));
        txtNamaUpdate.setPreferredSize(new java.awt.Dimension(100, 22));

        jLabel19.setText("Kategori");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel20.setText("Masukkan Produk");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel21.setText("Ubah Produk");

        jLabel22.setText("Nama");

        btnInsert.setText("Insert");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        jLabel23.setText("Stock");

        jLabel24.setText("Harga");

        txtNamaInsert.setMinimumSize(new java.awt.Dimension(100, 22));
        txtNamaInsert.setPreferredSize(new java.awt.Dimension(100, 22));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel18)
                                    .addComponent(txtHargaUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel23)
                                    .addComponent(txtStockUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtKategoriUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16)))
                            .addComponent(txtNamaUpdate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNamaInsert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel24)
                                    .addComponent(txtHargaInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtStockInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel19)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txtKategoriInsert, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                                        .addGap(335, 335, 335)
                                        .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(3, 3, 3))
                    .addComponent(jLabel14)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(txtKategoriDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNamaInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel15)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHargaInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStockInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKategoriInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNamaUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel23)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHargaUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStockUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKategoriUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtKategoriDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(219, Short.MAX_VALUE))
        );

        jScrollPane3.setViewportView(jPanel7);

        jTabbedPane2.addTab("Manajemen", jScrollPane3);

        jLabel7.setText("Tambah kategori");

        btnTambahKategori.setText("Tambah");
        btnTambahKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahKategoriActionPerformed(evt);
            }
        });

        jLabel8.setText("Ubah kategori dari:");

        btnUbahKategori.setText("Ubah");
        btnUbahKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahKategoriActionPerformed(evt);
            }
        });

        comboUbahKategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        comboHapusKategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setText("Hapus kategori");

        btnHapusKategori.setText("Hapus");
        btnHapusKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusKategoriActionPerformed(evt);
            }
        });

        jLabel1.setText("ke");

        hapusNull.setText("Hapus produk dengan kategori NULL");
        hapusNull.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusNullActionPerformed(evt);
            }
        });

        comboUpdateKategoriNULL.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnTambahKategoriNULL.setText("Tambah");
        btnTambahKategoriNULL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahKategoriNULLActionPerformed(evt);
            }
        });

        jLabel2.setText("Tambah kategori pada produk dengan kategori NULL:");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(hapusNull, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(comboUpdateKategoriNULL, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTambahKategoriNULL))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtTambahKategori)
                                            .addGroup(jPanel8Layout.createSequentialGroup()
                                                .addComponent(comboUbahKategori, 0, 161, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtUbahKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnUbahKategori, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(btnTambahKategori, javax.swing.GroupLayout.Alignment.TRAILING)))
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(comboHapusKategori, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnHapusKategori)))
                                .addGap(1, 1, 1))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(28, 28, 28))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTambahKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTambahKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUbahKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboUbahKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUbahKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHapusKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboHapusKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboUpdateKategoriNULL, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTambahKategoriNULL, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(hapusNull, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(304, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Kategori", jPanel8);

        jLabel31.setText("Filter berdasarkan:");

        comboBerdasarkan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboBerdasarkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBerdasarkanActionPerformed(evt);
            }
        });

        comboOperator.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboOperator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboOperatorActionPerformed(evt);
            }
        });

        jLabel33.setText("Operator:");

        jLabel34.setText("Nilai:");

        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        jLabel32.setText("Cari:");

        comboUrut.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboUrut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboUrutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnCari)
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel32)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator3)
                            .addComponent(txtSearch)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel31)
                                    .addComponent(comboBerdasarkan, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel33)
                                    .addComponent(comboOperator, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(comboUrut, javax.swing.GroupLayout.Alignment.TRAILING, 0, 105, Short.MAX_VALUE)
                                    .addComponent(jLabel34)
                                    .addComponent(spinnerNilai, javax.swing.GroupLayout.Alignment.TRAILING))))))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboBerdasarkan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel33)
                            .addComponent(jLabel34))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboOperator, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                            .addComponent(spinnerNilai))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboUrut, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jLabel32)
                .addGap(3, 3, 3)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(392, Short.MAX_VALUE))
        );

        jScrollPane11.setViewportView(jPanel19);

        jTabbedPane2.addTab("Filter", jScrollPane11);

        jPanel6.add(jTabbedPane2, java.awt.BorderLayout.LINE_END);

        jPanel17.setName(""); // NOI18N
        jPanel17.setPreferredSize(new java.awt.Dimension(20, 217));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 705, Short.MAX_VALUE)
        );

        jPanel6.add(jPanel17, java.awt.BorderLayout.LINE_START);

        jPanel18.setPreferredSize(new java.awt.Dimension(1138, 20));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1145, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jPanel6.add(jPanel18, java.awt.BorderLayout.PAGE_END);

        jTabbedPane1.addTab("Produk", jPanel6);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel2.setPreferredSize(new java.awt.Dimension(200, 215));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Histori transaksi:");

        jLabel5.setText("Nama produk:");

        comboTransaksi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTransaksiActionPerformed(evt);
            }
        });

        jLabel6.setText("Jumlah produk:");

        spinnerTransaksi.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jPanel10.setBackground(new java.awt.Color(73, 73, 73));

        labelHargaProduk.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        labelHargaProduk.setText("0");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Rp.");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelHargaProduk, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(labelHargaProduk, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addGap(0, 5, Short.MAX_VALUE))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel11.setText("Harga per produk:");

        btnTambahKeranjang.setBackground(new java.awt.Color(56, 115, 174));
        btnTambahKeranjang.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTambahKeranjang.setText("Masukkan keranjang");
        btnTambahKeranjang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahKeranjangActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel12.setText("Buat Transaksi");

        jScrollPane12.setBackground(new java.awt.Color(50, 54, 55));
        jScrollPane12.setPreferredSize(new java.awt.Dimension(10, 100));

        panelHistori.setBackground(new java.awt.Color(53, 56, 58));
        panelHistori.setMinimumSize(new java.awt.Dimension(10, 100));
        panelHistori.setPreferredSize(new java.awt.Dimension(154, 3018));

        javax.swing.GroupLayout panelHistoriLayout = new javax.swing.GroupLayout(panelHistori);
        panelHistori.setLayout(panelHistoriLayout);
        panelHistoriLayout.setHorizontalGroup(
            panelHistoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 751, Short.MAX_VALUE)
        );
        panelHistoriLayout.setVerticalGroup(
            panelHistoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3018, Short.MAX_VALUE)
        );

        jScrollPane12.setViewportView(panelHistori);

        jPanel14.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));

        jLabel28.setText("Tanggal");

        jLabel29.setText("Subtotal");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel29)
                .addGap(41, 41, 41))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        txtStock.setEditable(false);
        txtStock.setColumns(2);
        txtStock.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel30.setText("Stock:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 793, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addContainerGap(20, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(comboTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(spinnerTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel30)
                                    .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnTambahKeranjang, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(50, 50, 50))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel11)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(comboTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(spinnerTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15)
                .addComponent(btnTambahKeranjang, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(22, 22, 22)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        scrollTransaksi.setViewportView(jPanel2);

        jPanel5.add(scrollTransaksi, java.awt.BorderLayout.CENTER);

        jPanel9.setPreferredSize(new java.awt.Dimension(300, 817));
        jPanel9.setLayout(new javax.swing.BoxLayout(jPanel9, javax.swing.BoxLayout.LINE_AXIS));

        jPanel15.setBackground(new java.awt.Color(51, 51, 51));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel25.setText("Keranjang");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(118, 118, 118))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jLabel25)
                .addGap(15, 15, 15))
        );

        jScrollPane10.setPreferredSize(new java.awt.Dimension(100, 1200));

        panelKeranjang.setBackground(new java.awt.Color(54, 55, 55));
        panelKeranjang.setMaximumSize(new java.awt.Dimension(12000, 12000));
        panelKeranjang.setPreferredSize(new java.awt.Dimension(108, 4000));
        panelKeranjang.setLayout(new javax.swing.BoxLayout(panelKeranjang, javax.swing.BoxLayout.LINE_AXIS));
        jScrollPane10.setViewportView(panelKeranjang);

        jPanel20.setMaximumSize(new java.awt.Dimension(32767, 10));

        btnTambahTransaksi.setBackground(new java.awt.Color(56, 115, 174));
        btnTambahTransaksi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTambahTransaksi.setText("Buat transaksi");
        btnTambahTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahTransaksiActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel26.setText("Subtotal:");

        labelSubtotal.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        labelSubtotal.setText("0");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel27.setText("Rp.");

        btnTambahTransaksi1.setBackground(new java.awt.Color(197, 67, 67));
        btnTambahTransaksi1.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        btnTambahTransaksi1.setText("Kosongkan keranjang");
        btnTambahTransaksi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahTransaksi1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnTambahTransaksi1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTambahTransaksi)
                        .addGap(12, 12, 12))))
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel27)
                .addGap(3, 3, 3)
                .addComponent(labelSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelSubtotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addGap(13, 13, 13)))
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(btnTambahTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(btnTambahTransaksi1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel9.add(jPanel15);

        jPanel5.add(jPanel9, java.awt.BorderLayout.LINE_END);

        jTabbedPane1.addTab("Transaksi", jPanel5);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");

        jMenu3.setText("Ekspor");
        jMenu3.setIconTextGap(0);
        jMenu3.setMargin(new java.awt.Insets(3, 0, 3, 6));

        eksporCsv.setText("CSV");
        eksporCsv.setIconTextGap(0);
        eksporCsv.setMargin(new java.awt.Insets(3, 0, 3, 6));
        eksporCsv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eksporCsvActionPerformed(evt);
            }
        });
        jMenu3.add(eksporCsv);

        exportExcel.setText("Excel");
        exportExcel.setIconTextGap(0);
        exportExcel.setMargin(new java.awt.Insets(3, 0, 3, 6));
        jMenu3.add(exportExcel);

        jMenu1.add(jMenu3);

        btnReload.setText("Reload");
        btnReload.setIconTextGap(0);
        btnReload.setMargin(new java.awt.Insets(3, 0, 3, 6));
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });
        jMenu1.add(btnReload);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        jMenu4.setText("Tema");
        jMenu4.setIconTextGap(0);
        jMenu4.setMargin(new java.awt.Insets(3, 1, 3, 6));

        buttonGroup1.add(lightMode);
        lightMode.setSelected(true);
        lightMode.setText("FlatDarkLaf");
        lightMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lightModeActionPerformed(evt);
            }
        });
        jMenu4.add(lightMode);

        buttonGroup1.add(darkMode);
        darkMode.setText("FlatLightLaf");
        darkMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                darkModeActionPerformed(evt);
            }
        });
        jMenu4.add(darkMode);

        jMenu2.add(jMenu4);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private boolean showConfirmation(String message) {
        int result = JOptionPane.showConfirmDialog(this, message, "Konfirmasi", JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int row = tabelProduk.getSelectedRow();
        if (row != -1) {
            int id = (int) tabelProduk.getValueAt(row, 0);
            if (showConfirmation("Yakin ingin menghapus data ini?")) {
                controller.hapusProduk(id);
            }
        } else {
            showMessage("Pilih baris yang ingin dihapus.");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        int row = tabelProduk.getSelectedRow();
        if (row != -1 && showConfirmation("Yakin ingin mengubah data ini?")) {
                int id = (int) tabelProduk.getValueAt(row, 0);
                String nama = txtNamaUpdate.getText();
                int harga = (int) txtHargaUpdate.getValue();
                int stock = (int) txtStockUpdate.getValue();
                int kategori = txtKategoriUpdate.getSelectedIndex();

                Produk p = new Produk(nama, harga, stock, kategori, id);
                controller.updateProduk(tabelProduk, p);
                loadComboBoxData();
                kosongkanField();
        } else {
            showMessage("Pilih baris yang ingin diubah.");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        String nama = txtNamaInsert.getText();
        int harga = (int) txtHargaInsert.getValue();
        int stock = (int) txtStockInsert.getValue();
        int kategori = txtKategoriInsert.getSelectedIndex();

        Produk produkBaru = new Produk(nama, harga, stock, kategori);
        controller.simpanProduk(produkBaru);
        controller.tampilkanData(tabelProduk);
        loadComboBoxData();
        kosongkanField();
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnTambahKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahKategoriActionPerformed
        String kategori = txtTambahKategori.getText();
        Produk produkBaru = new Produk(kategori);
        controller.simpanKategori(produkBaru);
        
        loadComboBoxData();
        kosongkanField();
    }//GEN-LAST:event_btnTambahKategoriActionPerformed

    private void btnUbahKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahKategoriActionPerformed
        String kategori = txtUbahKategori.getText();
        int kategoriIndex = comboUbahKategori.getSelectedIndex();
        Produk p = new Produk(kategori, kategoriIndex);
        controller.updateKategori(p);
        loadComboBoxData();
        kosongkanField();
    }//GEN-LAST:event_btnUbahKategoriActionPerformed

    private void btnHapusKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusKategoriActionPerformed
        int id = comboHapusKategori.getSelectedIndex() + 1;
        String namaKategori = comboHapusKategori.getSelectedItem().toString();
        int result = JOptionPane.showConfirmDialog(
            this,
            "Apakah Anda juga ingin menghapus semua produk dengan kategori '" + namaKategori + "' ?",
            "Konfirmasi Simpan",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            controller.hapusKategori(id);
            controller.hapusProdukKategoriNull();
            controller.tampilkanData(tabelProduk);
            loadComboBoxData();
        } else if (result == JOptionPane.NO_OPTION) {
            controller.hapusKategori(id);
            loadComboBoxData();
            controller.tampilkanData(tabelProduk);
        }
    }//GEN-LAST:event_btnHapusKategoriActionPerformed

    private void btnTambahKategoriNULLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahKategoriNULLActionPerformed
        int idKategoriBaru = comboUpdateKategoriNULL.getSelectedIndex() + 1;
        
        int result = JOptionPane.showConfirmDialog(
            this,
            "Apakah Anda mengubah semua produk dengan kategori NULL ke kategori baru?",
            "Konfirmasi Simpan",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            controller.updateKategoriNull(idKategoriBaru);
        }
    }//GEN-LAST:event_btnTambahKategoriNULLActionPerformed

    private void hapusNullActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusNullActionPerformed
        int result = JOptionPane.showConfirmDialog(
            this,
            "Apakah Anda yakin untuk menghapus semua produk dengan kategori NULL?",
            "Konfirmasi Simpan",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (result == JOptionPane.YES_OPTION) {
            controller.hapusProdukKategoriNull();
        }
    }//GEN-LAST:event_hapusNullActionPerformed

    private void eksporCsvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eksporCsvActionPerformed
        exportTableToCSV(tabelProduk, "D:/produk.csv");
    }//GEN-LAST:event_eksporCsvActionPerformed

    private void switchTheme(LookAndFeel laf) {
        try {
            UIManager.setLookAndFeel(laf);
            for (Window window : Window.getWindows()) {
                SwingUtilities.updateComponentTreeUI(window);
                window.pack();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void lightModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lightModeActionPerformed
         switchTheme(new FlatDarkLaf());
    }//GEN-LAST:event_lightModeActionPerformed

    private void darkModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_darkModeActionPerformed
        switchTheme(new FlatLightLaf());
    }//GEN-LAST:event_darkModeActionPerformed

    List<Transaksi> daftarProduk = new ArrayList<>();;
    private void btnTambahKeranjangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahKeranjangActionPerformed
        panelKeranjang.setLayout(new BoxLayout(panelKeranjang, BoxLayout.Y_AXIS));
        
        String barang = comboTransaksi.getSelectedItem().toString();
        int jumlah = (int) spinnerTransaksi.getValue();
        int harga = Integer.parseInt(labelHargaProduk.getText());
        int idProduk = controller.getIdByNama(barang);
        int stock = Integer.parseInt(txtStock.getText());
        
        CardKeranjang card = new CardKeranjang(barang, jumlah, 400);
        panelKeranjang.add(card);
        panelKeranjang.add(Box.createRigidArea(new Dimension(0, 5)));
        panelKeranjang.revalidate();
        panelKeranjang.repaint();
        
        daftarProduk.add(new Transaksi(idProduk, jumlah, harga * jumlah));
        
        subTotal += harga * jumlah;
        String total = String.valueOf(subTotal);
        if (stock >= jumlah) {
            txtStock.setText(String.valueOf(stock - jumlah));
        } else {
            showMessage("Stock tidak cukup");
        }
        labelSubtotal.setText(total);
    }//GEN-LAST:event_btnTambahKeranjangActionPerformed
    
    private int comboIndex = 0;
    private void comboTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTransaksiActionPerformed
        SpinnerNumberModel spinner = (SpinnerNumberModel) spinnerTransaksi.getModel();
        String namaBarang = comboTransaksi.getSelectedItem().toString();
        int stock = controller.tampilkanStockByNama(namaBarang);
        int currentSpinnerValue  = (int) spinnerTransaksi.getValue();
        
        System.out.println(stock);
        labelHargaProduk.setText(Integer.toString(controller.tampilkanHargaByNama(namaBarang))); // konversi ke String
        if (stock > 0 ) {
//            spinnerTransaksi.setValue(1);
            spinner.setMaximum(stock);
            txtStock.setText(String.valueOf(stock));
            
            if (currentSpinnerValue  > stock) {
                spinnerTransaksi.setValue(stock);
            }
        } else {
            showMessage("Stock kosong.");
            comboTransaksi.setSelectedIndex(comboIndex);
        }
        
        comboIndex = comboTransaksi.getSelectedIndex();
    }//GEN-LAST:event_comboTransaksiActionPerformed
    
    private void btnTambahTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahTransaksiActionPerformed
        if (daftarProduk.isEmpty()) {
            showMessage("Keranjang masih kosong.");
            return;
        }
        
        String waktu = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        boolean sukses = controller.simpanTransaksi("Bambang", daftarProduk);

        if (sukses) {
            tampilkanTransaksiBaru(waktu, controller.getTransaksiTerkini());
            resetKeranjang();
        }
    }//GEN-LAST:event_btnTambahTransaksiActionPerformed

    private void tampilkanTransaksiBaru(String waktu, int idTransaksi) {
        panelHistori.setLayout(new BoxLayout(panelHistori, BoxLayout.Y_AXIS));

        String subtotalFormatted = Format.formatRibuan(subTotal);
        CardKeranjang card = new CardKeranjang(waktu, "Rp." + subtotalFormatted, idTransaksi);
        panelHistori.add(card, 0);
        panelHistori.revalidate();
        panelHistori.repaint();
    }
    
    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed
        controller.tampilkanData(tabelProduk);
    }//GEN-LAST:event_btnReloadActionPerformed

    private void btnTambahTransaksi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahTransaksi1ActionPerformed
        resetKeranjang();
    }//GEN-LAST:event_btnTambahTransaksi1ActionPerformed
    
    private void comboBerdasarkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBerdasarkanActionPerformed
        filter();
    }//GEN-LAST:event_comboBerdasarkanActionPerformed
    
    private void turnOnSpinner() {
        spinnerNilai.setEnabled(true);
        txtSearch.setEnabled(false);
        comboOperator.setEnabled(true);
    }
    
    private void turnOnSearch() {
        spinnerNilai.setEnabled(false);
        txtSearch.setEnabled(true);
        comboOperator.setEnabled(false);
    }
    
    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        filter();
    }//GEN-LAST:event_btnCariActionPerformed

    private void comboOperatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboOperatorActionPerformed
        String berdasarkan = (String) comboOperator.getSelectedItem();

        switch (berdasarkan){
            case "":
                spinnerNilai.setEnabled(false);
                txtSearch.setEnabled(false);
                break;
            default:
                spinnerNilai.setEnabled(true);
        }
        
        filter();
    }//GEN-LAST:event_comboOperatorActionPerformed

    private void comboUrutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboUrutActionPerformed
        filter();
    }//GEN-LAST:event_comboUrutActionPerformed
    
    private void filter() {
        String berdasarkan = comboBerdasarkan.getSelectedItem().toString();
        String operator = comboOperator.getSelectedItem().toString();
        String spinner = spinnerNilai.getValue().toString();
        String search = txtSearch.getText();
        String urutan = comboUrut.getSelectedItem().toString();
        boolean pengurut = "Menaik".equals( urutan);
        
        switch (berdasarkan){
            case "kode":
                turnOnSpinner();
                if (comboOperator.getSelectedItem() == "") spinnerNilai.setEnabled(false);
                
                controller.setFilter("kode", operator, spinner, pengurut);
                break;
            case "nama":
                turnOnSearch();
                
                controller.setFilter("nama", "LIKE", search, pengurut);
                break;
            case "harga":
                turnOnSpinner();
                if (comboOperator.getSelectedItem() == "") spinnerNilai.setEnabled(false);
                
                controller.setFilter("harga", operator, spinner, pengurut);
                break;
            case "stock":
                turnOnSpinner();
                if (comboOperator.getSelectedItem() == "") spinnerNilai.setEnabled(false);
                
                controller.setFilter("stock", operator, spinner, pengurut);
                break;
            case "kategori":
                turnOnSearch();
                
                controller.setFilter("kategori", "LIKE", search, pengurut);
                break;
        }
        controller.tampilkanData(tabelProduk);
    }
    
    private void resetKeranjang() {
        panelKeranjang.removeAll();
        panelKeranjang.revalidate();
        panelKeranjang.repaint();

        daftarProduk.clear();
        labelSubtotal.setText("0");
        subTotal = 0;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(mainApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mainApp().setVisible(true);
            }
        });
    }
    // <editor-fold defaultstate="collapsed">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnHapusKategori;
    private javax.swing.JButton btnInsert;
    private javax.swing.JMenuItem btnReload;
    private javax.swing.JButton btnTambahKategori;
    private javax.swing.JButton btnTambahKategoriNULL;
    private javax.swing.JButton btnTambahKeranjang;
    private javax.swing.JButton btnTambahTransaksi;
    private javax.swing.JButton btnTambahTransaksi1;
    private javax.swing.JButton btnUbahKategori;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> comboBerdasarkan;
    private javax.swing.JComboBox<String> comboHapusKategori;
    private javax.swing.JComboBox<String> comboOperator;
    private javax.swing.JComboBox<String> comboTransaksi;
    private javax.swing.JComboBox<String> comboUbahKategori;
    private javax.swing.JComboBox<String> comboUpdateKategoriNULL;
    private javax.swing.JComboBox<String> comboUrut;
    private javax.swing.JRadioButtonMenuItem darkMode;
    private javax.swing.JMenuItem eksporCsv;
    private javax.swing.JMenuItem exportExcel;
    private javax.swing.JButton hapusNull;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JLabel labelHargaProduk;
    private javax.swing.JLabel labelSubtotal;
    private javax.swing.JRadioButtonMenuItem lightMode;
    private javax.swing.JPanel panelHistori;
    private javax.swing.JPanel panelKeranjang;
    private javax.swing.JScrollPane scrollTransaksi;
    private javax.swing.JSpinner spinnerNilai;
    private javax.swing.JSpinner spinnerTransaksi;
    private javax.swing.JTable tabelProduk;
    private javax.swing.JSpinner txtHargaInsert;
    private javax.swing.JSpinner txtHargaUpdate;
    private javax.swing.JComboBox<String> txtKategoriDelete;
    private javax.swing.JComboBox<String> txtKategoriInsert;
    private javax.swing.JComboBox<String> txtKategoriUpdate;
    private javax.swing.JTextField txtNamaInsert;
    private javax.swing.JTextField txtNamaUpdate;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtStock;
    private javax.swing.JSpinner txtStockInsert;
    private javax.swing.JSpinner txtStockUpdate;
    private javax.swing.JTextField txtTambahKategori;
    private javax.swing.JTextField txtUbahKategori;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>
}

