package com.example.demo.service;

import com.example.demo.entity.NhanVien;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NhanVienService {
    NhanVien save(NhanVien nhanVien);

    Boolean checkEmail(String email);

    NhanVien findByEmail(String email);

    NhanVien nhanVienDangNhap();

    List<NhanVien> findAll();

    String resetPassword(String email);

    String genMaNV();
}
