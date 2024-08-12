package com.example.demo.repository;

import com.example.demo.entity.NhanVien;
import com.example.demo.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SanPhamRepo extends JpaRepository<SanPham, Integer> {
    @Query("select sp from SanPham sp where sp.hang.id =:idHang")
    Page<SanPham> findAllSanPhamByIdHang(@Param("idHang") Integer idHang, Pageable pageable);
}
