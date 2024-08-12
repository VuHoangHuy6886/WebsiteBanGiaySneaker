package com.example.demo.service;

import com.example.demo.dto.request.create_request.CreateRequestChiTietSanPham;
import com.example.demo.dto.request.update_request.UpdateRequestChiTietSanPham;
import com.example.demo.entity.ChiTietSanPham;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChiTietSanPhamService {

    Page<ChiTietSanPham> findAll(Integer page, Integer size);

    Page<ChiTietSanPham> findAllByID(Integer idSanPham, Integer page, Integer size);

    List<ChiTietSanPham> findAll();

    ChiTietSanPham findById(Integer id);

    String save(CreateRequestChiTietSanPham request);

    String update(UpdateRequestChiTietSanPham request);

    void deleteById(Integer id);

    List<ChiTietSanPham> findAllProductDetailByProductId(Integer idProduct);

    ChiTietSanPham findProductDetailByIdMauSacAndProduct(Integer idMauSac, Integer idProduct, Integer idKichThuoc);
}
