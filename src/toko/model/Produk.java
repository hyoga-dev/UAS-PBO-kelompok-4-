package toko.model;

public class Produk {
    private int kode;
    private String nama;
    private int harga;
    private int stock;
    private int kategoriIndex;
    private String kategori;
    
    public Produk(String kategori, int kategoriIndex) {
        this.kategori = kategori;
        this.kategoriIndex = kategoriIndex;
    }
    
    public Produk(int kode, String nama, int harga, int stock, String kategori) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.stock = stock;
        this.kategori = kategori;
    }
    
    public Produk(String nama, int harga, int stock, int kategoriIndex, int kode) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.stock = stock;
        this.kategoriIndex = kategoriIndex;
    }
    
    public Produk(String nama, int harga, int stock, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.stock = stock;
        this.kategori = kategori;
    }
    public Produk(String kategori) {
        this.kategori = kategori;
    }
    
    public Produk(String nama, int harga, int stock, int kategori) {
        this.nama = nama;
        this.harga = harga;
        this.stock = stock;
        this.kategoriIndex = kategori;
    }

    // Getter
    public int getKode() { return kode; }
    public String getNama() { return nama; }
    public int getHarga() { return harga; }
    public int getStock() { return stock; }
    public String getKategori() { return kategori; }
    public int getKategoriIndex() { return kategoriIndex; }
}
