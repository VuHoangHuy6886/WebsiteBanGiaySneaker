package com.example.demo.repository;

import com.example.demo.entity.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface HoaDonRepo extends JpaRepository<HoaDon, Integer> {
    @Query(value = "SELECT * FROM hoa_don ORDER BY ma DESC LIMIT 1", nativeQuery = true)
    HoaDon findLastHoaDon();

    @Query("SELECT d FROM HoaDon d WHERE "
            + "(:ma IS NULL OR d.ma LIKE %:ma%) "
            + "AND (:tongTien IS NULL OR d.tongTien = :tongTien) "
            + "AND (:loaiHoaDon IS NULL OR d.loaiHoaDon = :loaiHoaDon) "
            + "AND (:phuongThucThanhToan IS NULL OR d.phuongThucThanhToan = :phuongThucThanhToan) "
            + "AND (:trangThai IS NULL OR d.trangThai = :trangThai) "
            + "AND (:thoiGian IS NULL OR d.ngayTao >= :thoiGian) ")
    Page<HoaDon> findAllFilter(Pageable pageable,
                               @Param("ma") String ma,
                               @Param("tongTien") Double tongTien,
                               @Param("loaiHoaDon") String loaiHoaDon,
                               @Param("phuongThucThanhToan") String phuongThucThanhToan,
                               @Param("trangThai") Integer trangThai,
                               @Param("thoiGian") LocalDateTime thoiGian);


    @Query("SELECT h FROM HoaDon h ORDER BY h.ngayTao DESC")
    Page<HoaDon> findAllSordDate(Pageable pageable);
}
