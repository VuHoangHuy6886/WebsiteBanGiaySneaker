package com.example.demo.service.impl;

import com.example.demo.dto.request.create_request.CreateRequestKhachHang;
import com.example.demo.dto.request.update_request.UpdateRequestKhachHang;
import com.example.demo.entity.ChatLieu;
import com.example.demo.entity.KhachHang;
import com.example.demo.repository.ChatLieuRepo;
import com.example.demo.repository.KhacHangRepo;
import com.example.demo.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class KhachHangServiceImpl implements KhachHangService {

    @Autowired
    private KhacHangRepo khacHangRepo;

    @Override
    public Page<KhachHang> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<KhachHang> pages = khacHangRepo.findAll(pageable);
        return pages;
    }

    @Override
    public List<KhachHang> findAll() {
        KhachHang khachLe = khacHangRepo.findByKhachLe("KHÁCH LẺ");

        return khacHangRepo.findAll().stream()
                .filter(khachHang -> !khachHang.equals(khachLe))
                .collect(Collectors.toList());
    }

    @Override
    public KhachHang findById(Integer id) {
        KhachHang khachHang = khacHangRepo.findById(id).orElseThrow();
        return khachHang;
    }

    @Override
    public void deleteById(Integer id) {
        khacHangRepo.deleteById(id);
    }

    @Override
    public String save(CreateRequestKhachHang request) {
        KhachHang khachHangadd = KhachHang.builder().ten(request.getTen()).ma(request.getMa()).sdt(request.getSdt()).diaChi(request.getDiaChi()).email(request.getEmail()).matKhau(request.getMatKhau()).build();
        khacHangRepo.save(khachHangadd);
        return "Add successfully";
    }

    @Override
    public String update(UpdateRequestKhachHang request) {
        KhachHang khachHangUpdate = KhachHang.builder().id(request.getId()).ten(request.getTen()).ma(request.getMa()).sdt(request.getSdt()).diaChi(request.getDiaChi()).email(request.getEmail()).matKhau(request.getMatKhau()).build();
        khacHangRepo.save(khachHangUpdate);
        return "Add successfully";
    }

    @Override
    public String genMaKH() {
        // Lấy mã hóa đơn cuối cùng trong cơ sở dữ liệu (nếu có)
        KhachHang lastHoaDon = khacHangRepo.findLastKhachHang();
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
        return "MKH" + String.format("%03d", nextMa);
    }
}
