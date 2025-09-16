package com.dika.order.vo;

public class Produk {
    private Long id;
    private String nama;
    private String satuan;
    private double harga;

    public Produk(double harga, Long id, String nama, String satuan) {
        this.harga = harga;
        this.id = id;
        this.nama = nama;
        this.satuan = satuan;
    }

    @Override
    public String toString() {
        
        return super.toString();
    }
    public void setId(Long id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public Long getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getSatuan() {
        return satuan;
    }

    public double getHarga() {
        return harga;
    }
}
