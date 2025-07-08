package toko.controller;

import toko.model.Produk;
import toko.model.ProdukModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import toko.model.Transaksi;
import toko.model.TransaksiInfo;

public class ProdukController extends ProdukModel {

    private final JTable tabelUser;
    private ProdukModel model;

    public ProdukController(JTable tabelUser, ProdukModel model) {
        this.tabelUser = tabelUser;
        this.model = model;
    }
    
    @Override
    public void tampilkanData(JTable tabelUser) {
        model.tampilkanData(tabelUser);
        aturTabel(tabelUser);
    }
    
    public void setFilter(String kolom, String operator, String nilai, boolean ascending) {
        model.setKolomFilter(kolom);
        model.setOperatorFilter(operator);
        model.setNilaiFilter(nilai);
        model.setAscending(ascending);
    }
    
    @Override
    public List<TransaksiInfo> getSemuaTransaksi() {
        return super.getSemuaTransaksi();
    }
    
    public void updateProduk(JTable tabelUser1, Produk p) {
        int row = tabelUser.getSelectedRow();
        if (row != -1) {
            String kode = tabelUser.getValueAt(row, 0).toString();
            boolean sukses = updateProduk(kode, p);
            showMessage(sukses ? "Data berhasil diperbarui." : "Gagal memperbarui data.");
            if (sukses) tampilkanData(tabelUser);
        } else {
            showMessage("Pilih baris yang ingin diupdate.");
        }
    }
    
    @Override
    public boolean simpanTransaksi(String namaPembeli, List<Transaksi> daftarProduk) {
        boolean sukses = super.simpanTransaksi(namaPembeli, daftarProduk);
        showMessage(sukses ? "Data berhasil diperbarui." : "Gagal memperbarui data.");
        if (sukses) tampilkanData(tabelUser); 
        return sukses;
    }
    
    @Override
    public boolean hapusTransaksi(int id) {
        boolean sukses = super.hapusTransaksi(id);
        showMessage(sukses ? "Data berhasil diperbarui." : "Gagal memperbarui data.");
        if (sukses) tampilkanData(tabelUser); 
        return sukses;
    }
    
    @Override
    public boolean updateKategori(Produk p) {
        boolean sukses =  super.updateKategori(p);
        showMessage(sukses ? "Data berhasil diperbarui." : "Gagal memperbarui data.");
        if (sukses) tampilkanData(tabelUser); 
        return sukses;
    }
    
    @Override
    public boolean updateKategoriNull(int idKategoriBaru) {
        boolean sukses = super.updateKategoriNull(idKategoriBaru);
        showMessage(sukses ? "Data berhasil diperbarui." : "Gagal memperbarui data.");
        if (sukses) tampilkanData(tabelUser);    
        return sukses;
    }
    
    public void simpanProduk(Produk p) {
        boolean sukses = insertProduk(p);
        showMessage(sukses ? "Data berhasil disimpan!" : "Gagal menyimpan data.");
        if (sukses) tampilkanData(tabelUser);
    }
    
    public void simpanKategori(Produk p) {
        boolean sukses = insertKategori(p);
        showMessage(sukses ? "Data berhasil disimpan!" : "Gagal menyimpan data.");
        if (sukses) tampilkanData(tabelUser);
    }
    
    @Override
    public boolean hapusProduk(int id) {
        boolean sukses = super.hapusProduk(id);
        showMessage(sukses ? "Data berhasil dihapus." : "Gagal menghapus data.");
        if (sukses) tampilkanData(tabelUser); return sukses;
    }
    
    @Override
    public boolean hapusProdukKategoriNull() {
        boolean sukses = super.hapusProdukKategoriNull();
        showMessage(sukses ? "Data berhasil dihapus." : "Gagal menghapus data.");
        if (sukses) tampilkanData(tabelUser); return sukses;
    }
    
    @Override
    public boolean hapusKategori(int id) {
        boolean sukses = super.hapusKategori(id);
        showMessage(sukses ? "Data berhasil dihapus." : "Gagal menghapus data.");
        if (sukses) tampilkanData(tabelUser); return sukses;
    }
    
    
//    public void tampilkanData() {
//        DefaultTableModel tableModel = new DefaultTableModel();
//        tableModel.addColumn("Kode");
//        tableModel.addColumn("Nama");
//        tableModel.addColumn("Harga");
//        tableModel.addColumn("Stock");
//        tableModel.addColumn("Kategori");
//
//        List<Produk> daftarProduk = getProdukFiltered();
//        for (Produk p : daftarProduk) {
//            tableModel.addRow(new Object[]{
//                p.getKode(), p.getNama(), p.getHarga(), p.getStock(), p.getKategori()
//            });
//        }
//
//        tabelUser.setModel(tableModel);
//        aturTabel();
//    }
    
    public void tampilkanDataTransaksi(int id) {
        tampilkanDetailTransaksi(id, tabelUser);
        aturTabelTransaksi();
    }

    private void aturTabel(JTable tabelUser) {
        tabelUser.setRowSelectionAllowed(true);
        tabelUser.setColumnSelectionAllowed(false);
        tabelUser.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabelUser.getColumnModel().getColumn(0).setMaxWidth(40);
        tabelUser.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabelUser.getColumnModel().getColumn(2).setMaxWidth(100);
        tabelUser.getColumnModel().getColumn(3).setMaxWidth(80);
        tabelUser.getColumnModel().getColumn(4).setMinWidth(110);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        tabelUser.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tabelUser.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tabelUser.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
    }
    
    private void aturTabelTransaksi() {
        tabelUser.setRowSelectionAllowed(true);
        tabelUser.setColumnSelectionAllowed(false);
        tabelUser.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabelUser.getColumnModel().getColumn(0).setMaxWidth(40);
        tabelUser.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabelUser.getColumnModel().getColumn(2).setMaxWidth(100);
        tabelUser.getColumnModel().getColumn(3).setPreferredWidth(100);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        tabelUser.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tabelUser.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
//        tabelUser.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
    }
    

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
