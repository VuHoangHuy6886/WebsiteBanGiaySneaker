package com.example.demo.repository;

import com.example.demo.entity.HoaDon;
import com.example.demo.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KhacHangRepo extends JpaRepository<KhachHang, Integer> {
    @Query(value = "SELECT * FROM khachhang ORDER BY ma DESC LIMIT 1", nativeQuery = true)
    KhachHang findLastKhachHang();

    @Query("select  kh from  KhachHang kh where kh.ten =:tenKH")
    KhachHang findByKhachLe(@Param("tenKH") String tenKH);

}
