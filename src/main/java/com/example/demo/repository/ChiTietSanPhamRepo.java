package com.example.demo.repository;

import com.example.demo.entity.ChiTietSanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChiTietSanPhamRepo extends JpaRepository<ChiTietSanPham, Integer> {
    // find chi tiết sản phẩm by id sản phẩm
    @Query("select ct from ChiTietSanPham ct where ct.sanPham.id = :idSanPham")
    List<ChiTietSanPham> findAllProductDetailByProductId(@Param("idSanPham") Integer idSanPham);

    // find chi tiết sản phẩm  by - idMauSac And idSanPham
    @Query(value = "select  ct from  ChiTietSanPham ct where ct.mauSac.id = :idMauSac And ct.sanPham.id = :idSanPham and ct.kichThuoc.id =:idKichThuoc")
    ChiTietSanPham findProductDetailByIdMauSacAndProduct(
            @Param("idSanPham") Integer idSanPham,
            @Param("idMauSac") Integer idMauSac,
            @Param("idKichThuoc") Integer idKichThuoc
    );

    //  find chi tiết sản phẩm by - idSanPham - idMauSac - Id Kich Thuoc
    // find chi tiết sản phẩm by id sản phẩm
    @Query("select ct from ChiTietSanPham ct where ct.sanPham.id = :idSanPham")
    Page<ChiTietSanPham> findByHoaDonId(@Param("idSanPham") Integer idSanPham, Pageable pageable);
}
