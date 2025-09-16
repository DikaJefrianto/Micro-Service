package com.dika.order.vo;

import com.dika.order.model.Order;

public class ResponseTemplate {
    Order order;
    Produk produk;
    Pelanggan pelanggan;

    public ResponseTemplate(Order order, Produk produk, Pelanggan pelanggan) {
        this.order = order;
        this.produk = produk;
        this.pelanggan = pelanggan;
    }

    public ResponseTemplate() {
    }

    public ResponseTemplate(Order order, Pelanggan pelanggan, Produk produk) {
        this.order = order;
        this.pelanggan = pelanggan;
        this.produk = produk;
    }

    public Order getOrder() {
        return order;
    }

    public Produk getProduk() {
        return produk;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setProduk(Produk produk) {
        this.produk = produk;
    }

    public void setPelanggan(Pelanggan pelanggan) {
        this.pelanggan = pelanggan;
    }


}
