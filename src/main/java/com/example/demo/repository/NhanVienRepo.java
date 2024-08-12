package com.example.demo.repository;

import com.example.demo.entity.KhachHang;
import com.example.demo.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NhanVienRepo extends JpaRepository<NhanVien, Integer> {
    Boolean existsByEmail(String email);

    NhanVien findByEmail(String email);

    @Query(value = "SELECT * FROM nhan_vien ORDER BY ma DESC LIMIT 1", nativeQuery = true)
    NhanVien findLastNhanVien();
}
