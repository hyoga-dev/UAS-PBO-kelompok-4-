/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package toko.model;

public class Transaksi {
    private int idProduk;
    private int jumlah;
    private int subtotal;

    // Constructor
    public Transaksi(int idProduk, int jumlah, int subtotal) {
        this.idProduk = idProduk;
        this.jumlah = jumlah;
        this.subtotal = subtotal;
    }

    // Getter
    public int getIdProduk() {return idProduk;}
    public int getJumlah() {return jumlah;}
    public int getSubtotal() {return subtotal;}

}
