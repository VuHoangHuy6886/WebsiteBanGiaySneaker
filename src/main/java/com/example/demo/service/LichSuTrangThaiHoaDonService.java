package com.example.demo.service;

import com.example.demo.entity.LichSuDonHang;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LichSuTrangThaiHoaDonService {
    List<LichSuDonHang> findAll();

    LichSuDonHang findById(int id);

    void save(LichSuDonHang lichSuDonHang);

    List<LichSuDonHang> findHistoryIdHoaDon(int id);
}
