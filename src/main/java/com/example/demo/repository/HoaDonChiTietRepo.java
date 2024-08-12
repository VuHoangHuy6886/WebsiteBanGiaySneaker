package com.example.demo.repository;

import com.example.demo.entity.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HoaDonChiTietRepo extends JpaRepository<HoaDonChiTiet, Integer> {
    @Query("select hd from HoaDonChiTiet  hd where hd.hoaDon.id =:idHoaDon ")
    List<HoaDonChiTiet> findAllByIdHoaDon(@Param("idHoaDon") Integer idHoaDon);
}
