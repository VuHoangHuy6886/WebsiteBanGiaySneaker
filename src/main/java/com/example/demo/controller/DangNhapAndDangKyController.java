package com.example.demo.controller;

import com.example.demo.dto.request.create_request.UserDangNhap;
import com.example.demo.entity.NhanVien;
import com.example.demo.service.NhanVienService;
import com.example.demo.util.NhanVienDangNhap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class DangNhapAndDangKyController {
    @Autowired
    private NhanVienService nhanVienService;
    @Autowired
    private NhanVienDangNhap nhanVienDangNhap;

    @GetMapping("/Dang-Nhap")
    public String dangNhap(Model model) {
        model.addAttribute("User", new UserDangNhap());
        return "DangNhap";
    }

    @GetMapping("/Dang-Ky")
    public String dangKy() {
        return "DangKy";
    }

    @PostMapping("/Dang-Nhap")
    public String dangNhapResult(@ModelAttribute("User") UserDangNhap userDangNhap, Model model) {
        List<NhanVien> nhanVienList = nhanVienService.findAll();
        boolean checkLogin = false;
        for (NhanVien nhanVien : nhanVienList) {
            if (nhanVien.getEmail().equalsIgnoreCase(userDangNhap.getEmail()) && nhanVien.getMatKhau().equalsIgnoreCase(userDangNhap.getPassword())) {
                checkLogin = true;
                nhanVienDangNhap.setNhanVien(nhanVien);
                System.out.println("Đăng Nhập Thành Công");
                return "redirect:/admin";
            }
        }
        if (!checkLogin) {
            System.out.println("Đăng Nhập Thất Bại");
            model.addAttribute("User", new UserDangNhap());
            model.addAttribute("showError","Đăng Nhập Thất Bại");
            return "DangNhap";
        }
        return "redirect:/admin";
    }
}
