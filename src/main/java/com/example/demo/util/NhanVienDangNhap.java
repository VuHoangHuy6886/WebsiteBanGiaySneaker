package com.example.demo.util;

import com.example.demo.entity.NhanVien;
import org.springframework.stereotype.Component;

@Component
public class NhanVienDangNhap {
    private NhanVien nhanVien;

    public NhanVien setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
        return nhanVien;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }
}
