package com.example.demo.repository;

import com.example.demo.entity.LichSuDonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LichSuTrangThaiHoaDonRepo extends JpaRepository<LichSuDonHang, Integer> {
    @Query("select ls from LichSuDonHang ls where ls.hoaDon.id =:idHoaDon")
    List<LichSuDonHang> findHistoryByIdHoaDon(@Param("idHoaDon") Integer idHoaDon);
}
