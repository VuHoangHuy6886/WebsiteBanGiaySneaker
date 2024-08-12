package com.example.demo.service.impl;

import com.example.demo.dto.request.create_request.CreateRequestSanPham;
import com.example.demo.dto.request.update_request.UpdateRequestSanPham;
import com.example.demo.dto.response.SanPhamResponse;
import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.Hang;
import com.example.demo.entity.KhachHang;
import com.example.demo.entity.KichThuoc;
import com.example.demo.entity.SanPham;
import com.example.demo.repository.HangRepo;
import com.example.demo.repository.SanPhamRepo;
import com.example.demo.service.ChiTietSanPhamService;
import com.example.demo.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SanPhamServiceImpl implements SanPhamService {

    @Autowired
    private SanPhamRepo sanPhamRepo;
    @Autowired
    private HangRepo hangRepo;
    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;

    @Override
    public List<SanPham> getAll() {
        return sanPhamRepo.findAll();
    }

    @Override
    public Page<SanPham> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SanPham> pages = sanPhamRepo.findAll(pageable);
        return pages;
    }

    @Override
    public Page<SanPhamResponse> getAllSanPhamShowView(Integer page, Integer size) {
        List<SanPham> listProducts = sanPhamRepo.findAll();
        List<SanPhamResponse> responses = listProducts.stream().map(sp -> {
            // Hiển thị sản phẩm chi tiết theo id sản phẩm
            List<ChiTietSanPham> findPrice = chiTietSanPhamService.findAllProductDetailByProductId(sp.getId());

            // Tính trung bình đơn giá của tất cả sản phẩm chi tiết theo id sản phẩm
            double averageDonGia = findPrice.stream()
                    .mapToDouble(ct -> ct.getDonGia().doubleValue())
                    .average()
                    .orElse(0.0);

            // Tạo đối tượng SanPhamResponse và thiết lập các giá trị
            SanPhamResponse sanPhamResponse = new SanPhamResponse();
            sanPhamResponse.setTen(sp.getTen());
            sanPhamResponse.setId(sp.getId());
            sanPhamResponse.setDonGia(averageDonGia);

            return sanPhamResponse;
        }).collect(Collectors.toList());

        // Tạo trang (page) response
        int start = Math.min((int) PageRequest.of(page, size).getOffset(), responses.size());
        int end = Math.min((start + size), responses.size());
        Page<SanPhamResponse> pageResponse = new PageImpl<>(responses.subList(start, end), PageRequest.of(page, size), responses.size());
        return pageResponse;
    }

    @Override
    public Page<SanPhamResponse> getAllSanPhamShowViewByIdHang(Integer idHang, Integer page, Integer size) {
        // Truy vấn các sản phẩm theo idHang và phân trang
        Page<SanPham> sanPhamPage = sanPhamRepo.findAllSanPhamByIdHang(idHang, PageRequest.of(page, size));

        // Chuyển đổi từng sản phẩm thành SanPhamResponse
        List<SanPhamResponse> responses = sanPhamPage.stream().map(sp -> {
            // Hiển thị sản phẩm chi tiết theo id sản phẩm
            List<ChiTietSanPham> findPrice = chiTietSanPhamService.findAllProductDetailByProductId(sp.getId());

            // Tính trung bình đơn giá của tất cả sản phẩm chi tiết theo id sản phẩm
            double averageDonGia = findPrice.stream()
                    .mapToDouble(ct -> ct.getDonGia().doubleValue())
                    .average()
                    .orElse(0.0);

            // Tạo đối tượng SanPhamResponse và thiết lập các giá trị
            SanPhamResponse sanPhamResponse = new SanPhamResponse();
            sanPhamResponse.setTen(sp.getTen());
            sanPhamResponse.setId(sp.getId());
            sanPhamResponse.setDonGia(averageDonGia);

            return sanPhamResponse;
        }).collect(Collectors.toList());

        // Tạo trang (page) response dựa trên các phản hồi đã tạo
        Page<SanPhamResponse> pageResponse = new PageImpl<>(responses, PageRequest.of(page, size), sanPhamPage.getTotalElements());
        return pageResponse;
    }

    @Override
    public SanPham findById(Integer id) {
        SanPham sanPham = sanPhamRepo.findById(id).orElseThrow();
        return sanPham;
    }

    @Override
    public String save(CreateRequestSanPham request) {
        Hang hangRequest = hangRepo.findById(Integer.parseInt(request.getIdHang())).orElseThrow();
        SanPham sanPhamAdd = SanPham.builder().ma(request.getMa()).ten(request.getTen()).trangThai(request.getTrangThai()).hang(hangRequest).build();
        sanPhamRepo.save(sanPhamAdd);
        return "Add successfully";
    }

    @Override
    public String update(UpdateRequestSanPham request) {
        Hang hangRequest = hangRepo.findById(Integer.parseInt(request.getIdHang())).orElseThrow();
        SanPham sanPhamAdd = SanPham.builder()
                .id(request.getId())
                .ma(request.getMa())
                .ten(request.getTen())
                .trangThai(request.getTrangThai())
                .hang(hangRequest).build();
        sanPhamRepo.save(sanPhamAdd);
        return "Add successfully";
    }


    @Override
    public void deleteById(Integer id) {
        sanPhamRepo.deleteById(id);
    }
}
