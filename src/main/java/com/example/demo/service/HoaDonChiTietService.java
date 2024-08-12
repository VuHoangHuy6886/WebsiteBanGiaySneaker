package com.example.demo.service;

import com.example.demo.entity.HoaDonChiTiet;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface HoaDonChiTietService {
    List<HoaDonChiTiet> findAllByIdHoaDon(int id);
}
