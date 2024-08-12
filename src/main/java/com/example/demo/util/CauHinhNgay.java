package com.example.demo.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CauHinhNgay {

    // Phương thức để lấy LocalDateTime hiện tại
    public LocalDateTime layNgayGioHienTai() {
        return LocalDateTime.now();
    }

    public static void main(String[] args) {
        CauHinhNgay cauHinhNgay = new CauHinhNgay();
        // In ra ngày giờ hiện tại dưới dạng LocalDateTime
        System.out.println("Ngày giờ hiện tại: " + cauHinhNgay.layNgayGioHienTai());
    }
}
