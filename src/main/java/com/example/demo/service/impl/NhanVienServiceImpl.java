package com.example.demo.service.impl;

import com.example.demo.entity.NhanVien;
import com.example.demo.repository.NhanVienRepo;
import com.example.demo.service.NhanVienService;
import com.example.demo.util.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NhanVienServiceImpl implements NhanVienService {
    @Autowired
    private NhanVienRepo nhanVienRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;

    @Override
    public NhanVien save(NhanVien nhanVien) {
        nhanVien.setMatKhau(passwordEncoder.encode(nhanVien.getMatKhau()));
        nhanVien.setRole("ROLE_ADMIN");
        nhanVien.setTen("Admin");
        nhanVien.setMa(this.genMaNV());
        return nhanVienRepo.save(nhanVien);
    }

    @Override
    public Boolean checkEmail(String email) {
        return nhanVienRepo.existsByEmail(email);
    }

    @Override
    public NhanVien findByEmail(String email) {
        return nhanVienRepo.findByEmail(email);
    }

    @Override
    public NhanVien nhanVienDangNhap() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Lấy tên đăng nhập, thông thường là email
        return nhanVienRepo.findByEmail(email);
    }

    @Override
    public List<NhanVien> findAll() {
        return nhanVienRepo.findAll();
    }

    @Override
    public String resetPassword(String email) {
        NhanVien nhanVien = nhanVienRepo.findByEmail(email);
        if (nhanVien != null) {
            // Tạo mật khẩu mới ngẫu nhiên
            String newPassword = PasswordGenerator.generateRandomPassword(10); // Tạo mật khẩu dài 10 ký tự

            // Mã hóa mật khẩu mới
            nhanVien.setMatKhau(passwordEncoder.encode(newPassword));

            // Cập nhật mật khẩu mới vào cơ sở dữ liệu
            nhanVienRepo.save(nhanVien);

            // Gửi mật khẩu mới qua email cho người dùng
            emailService.sendEmail(
                    email,
                    "Mật khẩu mới của bạn",
                    "Mật khẩu mới là: " + newPassword
            );
        } else {
            throw new RuntimeException("Email không tồn tại!");
        }
        return "Password had been send to email -> " + email;
    }

    @Override
    public String genMaNV() {
        // Lấy mã hóa đơn cuối cùng trong cơ sở dữ liệu (nếu có)
        NhanVien lastHoaDon = nhanVienRepo.findLastNhanVien();
        int nextMa = 1;

        // Kiểm tra xem lastHoaDon có null hay không
        if (lastHoaDon != null) {
            String lastMa = lastHoaDon.getMa();

            // Kiểm tra xem lastMa có null hay không
            if (lastMa != null) {
                try {
                    nextMa = Integer.parseInt(lastMa.substring(3)) + 1;
                } catch (NumberFormatException e) {
                    // Xử lý trường hợp lastMa không phải là số hợp lệ
                    e.printStackTrace();
                    // Nếu gặp lỗi, vẫn sử dụng nextMa = 1
                }
            }
        }

        // Tạo mã hóa đơn mới
        return "MNV" + String.format("%03d", nextMa);
    }
}
