package com.example.demo.service;

import com.example.demo.dto.request.create_request.CreateRequestKhachHang;
import com.example.demo.dto.request.update_request.UpdateRequestKhachHang;
import com.example.demo.entity.KhachHang;
import org.springframework.data.domain.Page;

import java.util.List;

public interface KhachHangService {
    Page<KhachHang> findAll(Integer page, Integer size);

    List<KhachHang> findAll();

    KhachHang findById(Integer id);

    void deleteById(Integer id);

    String save(CreateRequestKhachHang request);

    String update(UpdateRequestKhachHang request);

    String genMaKH();
}
