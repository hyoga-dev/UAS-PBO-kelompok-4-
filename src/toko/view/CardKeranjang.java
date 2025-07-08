/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package toko.view;
import toko.model.ProdukModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CardKeranjang extends javax.swing.JPanel  {
    private JLabel lblNamaBarang;
    private JLabel lblX;
    private JLabel lblJumlah;

    public CardKeranjang(String namaBarang, int jumlah, int width) {
        setBackground(new Color(60, 63, 65)); // warna mirip abu gelap
        
        Dimension fixedSize = new Dimension(width, 40); // misalnya 400x40 px
        setPreferredSize(fixedSize);
        setMinimumSize(fixedSize);
        setMaximumSize(fixedSize);

        setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20)); // padding
        setLayout(new BorderLayout());

        // Label nama barang (kiri)
        lblNamaBarang = new JLabel(namaBarang);
        lblNamaBarang.setForeground(Color.WHITE);
        lblNamaBarang.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Label 'x' (tengah)
        lblX = new JLabel("x", SwingConstants.CENTER);
        lblX.setForeground(Color.WHITE);
        lblX.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Label jumlah (kanan)
        lblJumlah = new JLabel(String.valueOf(jumlah), SwingConstants.RIGHT);
        lblJumlah.setForeground(Color.WHITE);
        lblJumlah.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Tambahkan ke panel utama
        add(lblNamaBarang, BorderLayout.WEST);
        add(lblX, BorderLayout.CENTER);
        add(lblJumlah, BorderLayout.EAST);
    }
    
    public CardKeranjang(String namaBarang, String jumlah,int id) {
        setBackground(new Color(60, 63, 65)); // default background
        Color normal = getBackground();
        Color hover = new Color(80, 83, 85); // abu sedikit lebih terang saat hover
        
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem editItem = new JMenuItem("Buka");
        JMenuItem deleteItem = new JMenuItem("Delete");

        popupMenu.add(editItem);
        popupMenu.add(deleteItem);

        // Tambahkan action listener
        editItem.addActionListener(e -> {
            BukaDetailTransaksi form = new BukaDetailTransaksi(id);
            form.setVisible(true);
        });
        
        deleteItem.addActionListener(e -> {
            Container parent = CardKeranjang.this.getParent(); // ambil panel induk
            ProdukModel model = new ProdukModel();
            
            if (parent != null) {
                model.hapusTransaksi(id);
                parent.remove(CardKeranjang.this); // hapus diri sendiri
                parent.revalidate();               // update layout
                parent.repaint();                  // redraw
            }
        });
        
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        
        
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                new DetailTransaksi("udin", 90);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hover);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(normal);
                repaint();
            }
        });
        
        Dimension fixedSize = new Dimension(2000, 40); // misalnya 400x40 px
        setPreferredSize(fixedSize);
        setMinimumSize(fixedSize);
        setMaximumSize(fixedSize);

        setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20)); // padding
        setLayout(new BorderLayout());

        // Label nama barang (kiri)
        lblNamaBarang = new JLabel(namaBarang);
        lblNamaBarang.setForeground(Color.WHITE);
        lblNamaBarang.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Label 'x' (tengah)
        lblX = new JLabel("-", SwingConstants.CENTER);
        lblX.setForeground(Color.WHITE);
        lblX.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Label jumlah (kanan)
        lblJumlah = new JLabel(String.valueOf(jumlah), SwingConstants.RIGHT);
        lblJumlah.setForeground(Color.WHITE);
        lblJumlah.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Tambahkan ke panel utama
        add(lblNamaBarang, BorderLayout.WEST);
        add(lblX, BorderLayout.CENTER);
        add(lblJumlah, BorderLayout.EAST);
    }
}
