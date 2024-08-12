package com.example.demo.service.impl;

import com.example.demo.entity.HoaDon;
import com.example.demo.repository.HoaDonRepo;
import com.example.demo.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class HoaDonServiceImpl implements HoaDonService {
    @Autowired
    private HoaDonRepo hoaDonRepo;

    @Override
    public Page<HoaDon> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<HoaDon> pages = hoaDonRepo.findAll(pageable);
        return pages;
    }

    @Override
    public String genMaHD() {
        // Lấy mã hóa đơn cuối cùng trong cơ sở dữ liệu (nếu có)
        HoaDon lastHoaDon = hoaDonRepo.findLastHoaDon();
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
        return "MHD" + String.format("%03d", nextMa);
    }

    @Override
    public HoaDon findById(Integer id) {
        return hoaDonRepo.findById(id).orElseThrow();
    }

    @Override
    public void save(HoaDon hoaDon) {
        hoaDonRepo.save(hoaDon);
    }

    @Override
    public Page<HoaDon> findAllSordDate(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<HoaDon> pages = hoaDonRepo.findAllSordDate(pageable);
        return pages;
    }
}
