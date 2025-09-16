package com.dika.produk_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dika.produk_service.model.Produk;

@Repository
public interface ProdukRepository extends JpaRepository<Produk, Long> {

}