package com.example.demo.service;

import com.example.demo.dto.request.create_request.CreateRequestKichThuoc;
import com.example.demo.dto.request.create_request.CreateRequestSanPham;
import com.example.demo.dto.request.update_request.UpdateRequestKichThuoc;
import com.example.demo.dto.request.update_request.UpdateRequestSanPham;
import com.example.demo.dto.response.SanPhamResponse;
import com.example.demo.entity.Hang;
import com.example.demo.entity.KichThuoc;
import com.example.demo.entity.SanPham;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SanPhamService {

    List<SanPham> getAll();

    Page<SanPham> findAll(Integer page, Integer size);

    Page<SanPhamResponse> getAllSanPhamShowView(Integer page, Integer size);

    Page<SanPhamResponse> getAllSanPhamShowViewByIdHang(Integer idHang, Integer page, Integer size);

    SanPham findById(Integer id);

    String save(CreateRequestSanPham request);

    String update(UpdateRequestSanPham request);

    void deleteById(Integer id);

}
