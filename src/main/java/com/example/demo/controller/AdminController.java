package com.example.demo.controller;

import com.example.demo.util.NhanVienDangNhap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private NhanVienDangNhap nhanVienDangNhap;

    @GetMapping("")
    public String index() {
        //System.out.println(" nhan viên đăng nhập là : " + nhanVienDangNhap.getNhanVien().getEmail());
        return "admin/index";
    }

    @GetMapping("/khach-hang")
    public String khachHang() {
        return "admin/khachhang/index";
    }

    @GetMapping("/Nhan-Vien")
    public String nhanVien() {
        return "admin/nhanvien/index";
    }

    @GetMapping("/Hang")
    public String hang(Model model) {
        return "admin/hang/index";
    }

    @GetMapping("/San-Pham")
    public String sanPham() {
        return "admin/sanpham/index";
    }

    @GetMapping("/San-Pham-Chi-Tiet")
    public String sanPhamChiTiet() {
        return "admin/sanphamchitiet/index";
    }

    @GetMapping("/Mau-Sac")
    public String mauSac() {
        return "admin/mausac/index";
    }

    @GetMapping("/Kich-Thuoc")
    public String kichThuoc() {
        return "admin/kichthuoc/index";
    }

    @GetMapping("/Chat-Lieu")
    public String chatLieu() {
        return "admin/chatlieu/index";
    }

    @GetMapping("/Loai-De")
    public String loaiDe() {
        return "admin/loaide/index";
    }

    @GetMapping("/Hoa-Don")
    public String hoaDon() {
        return "admin/hoadon/index";
    }

    @GetMapping("/Hoa-Don-Chi-Tiet")
    public String hoaDonChiTiet() {
        return "admin/hoadonchitiet/index";
    }

    @GetMapping("/Quan-Ly-Don-Hang")
    public String quanLyDonHang() {
        return "admin/donhang/index";
    }

    @GetMapping("/Ban-Hang-Tai-Quay")
    public String banHangTaiQuay() {
        return "admin/banhangtaiquay/index";
    }
}
