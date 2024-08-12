package com.example.demo.service.impl;

import com.example.demo.entity.LichSuDonHang;
import com.example.demo.repository.LichSuTrangThaiHoaDonRepo;
import com.example.demo.service.LichSuTrangThaiHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LichSuTrangThaiDonHangServiceImpl implements LichSuTrangThaiHoaDonService {
    @Autowired
    private LichSuTrangThaiHoaDonRepo lichSuTrangThaiHoaDonRepo;

    @Override
    public List<LichSuDonHang> findAll() {
        return lichSuTrangThaiHoaDonRepo.findAll();
    }

    @Override
    public LichSuDonHang findById(int id) {
        return lichSuTrangThaiHoaDonRepo.findById(id).get();
    }

    @Override
    public void save(LichSuDonHang lichSuDonHang) {
        lichSuTrangThaiHoaDonRepo.save(lichSuDonHang);
    }

    @Override
    public List<LichSuDonHang> findHistoryIdHoaDon(int id) {
        return lichSuTrangThaiHoaDonRepo.findHistoryByIdHoaDon(id);

    }
}
