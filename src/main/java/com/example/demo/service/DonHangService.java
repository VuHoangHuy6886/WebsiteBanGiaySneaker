package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public interface DonHangService {
    void xacNhanDonHang(Integer idHoaDon);

    void hoanThanh(Integer idHoaDon);

    void huyDon(Integer idHoaDon);
}
