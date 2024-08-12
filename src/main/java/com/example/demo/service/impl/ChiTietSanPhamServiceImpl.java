package com.example.demo.service.impl;

import com.example.demo.dto.request.create_request.CreateRequestChiTietSanPham;
import com.example.demo.dto.request.update_request.UpdateRequestChiTietSanPham;
import com.example.demo.entity.ChatLieu;
import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.KichThuoc;
import com.example.demo.entity.LoaiDe;
import com.example.demo.entity.MauSac;
import com.example.demo.entity.SanPham;
import com.example.demo.repository.ChatLieuRepo;
import com.example.demo.repository.ChiTietSanPhamRepo;
import com.example.demo.repository.KichThuocRepo;
import com.example.demo.repository.LoaiDeRepo;
import com.example.demo.repository.MauSacRepo;
import com.example.demo.repository.SanPhamRepo;
import com.example.demo.service.ChiTietSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChiTietSanPhamServiceImpl implements ChiTietSanPhamService {

    @Autowired
    private ChiTietSanPhamRepo chiTietSanPhamRepo;
    @Autowired
    private MauSacRepo mauSacRepo;
    @Autowired
    private KichThuocRepo kichThuocRepo;
    @Autowired
    private ChatLieuRepo chatLieuRepo;
    @Autowired
    private LoaiDeRepo loaiDeRepo;
    @Autowired
    private SanPhamRepo sanPhamRepo;

    @Override
    public Page<ChiTietSanPham> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ChiTietSanPham> pages = chiTietSanPhamRepo.findAll(pageable);
        System.out.println("ds serviceImpl : " + pages.getContent().size());
        return pages;
    }

    @Override
    public Page<ChiTietSanPham> findAllByID(Integer idSanPham, Integer page, Integer size) {
        return chiTietSanPhamRepo.findByHoaDonId(idSanPham, PageRequest.of(page, size));
    }

    @Override
    public List<ChiTietSanPham> findAll() {
        return chiTietSanPhamRepo.findAll();
    }

    @Override
    public ChiTietSanPham findById(Integer id) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepo.findById(id).orElseThrow();
        return chiTietSanPham;
    }

    @Override
    public String save(CreateRequestChiTietSanPham request) {
        MauSac hangRequest = mauSacRepo.findById(Integer.parseInt(request.getIdMauSac())).orElseThrow();
        KichThuoc kichThuocRequest = kichThuocRepo.findById(Integer.parseInt(request.getIdKichThuoc())).orElseThrow();
        ChatLieu chatLieugRequest = chatLieuRepo.findById(Integer.parseInt(request.getIdChatLieu())).orElseThrow();
        LoaiDe loaiDeRepoRequest = loaiDeRepo.findById(Integer.parseInt(request.getIdLoaiDe())).orElseThrow();
        SanPham sanPhamRequest = sanPhamRepo.findById(Integer.parseInt(request.getIdSanPham())).orElseThrow();

        ChiTietSanPham chiTietSanPhammAdd = ChiTietSanPham.builder().ma(request.getMa()).soLuong(Integer.parseInt(request.getSoLuong())).donGia(Float.parseFloat(request.getDonGia())).trangThai(request.getTrangThai())
                .mauSac(hangRequest).kichThuoc(kichThuocRequest).chatLieu(chatLieugRequest).loaiDe(loaiDeRepoRequest).sanPham(sanPhamRequest).build();
        chiTietSanPhamRepo.save(chiTietSanPhammAdd);
        return "Add successfully";
    }

    @Override
    public String update(UpdateRequestChiTietSanPham request) {
        MauSac hangRequest = mauSacRepo.findById(Integer.parseInt(request.getIdMauSac())).orElseThrow();
        KichThuoc kichThuocRequest = kichThuocRepo.findById(Integer.parseInt(request.getIdKichThuoc())).orElseThrow();
        ChatLieu chatLieugRequest = chatLieuRepo.findById(Integer.parseInt(request.getIdChatLieu())).orElseThrow();
        LoaiDe loaiDeRepoRequest = loaiDeRepo.findById(Integer.parseInt(request.getIdLoaiDe())).orElseThrow();
        SanPham sanPhamRequest = sanPhamRepo.findById(Integer.parseInt(request.getIdSanPham())).orElseThrow();

        ChiTietSanPham chiTietSanPhammAdd = ChiTietSanPham.builder()
                .id(request.getId())
                .ma(request.getMa())
                .soLuong(Integer.parseInt(request.getSoLuong()))
                .donGia(Float.parseFloat(request.getDonGia()))
                .trangThai(request.getTrangThai())
                .mauSac(hangRequest)
                .kichThuoc(kichThuocRequest)
                .chatLieu(chatLieugRequest)
                .loaiDe(loaiDeRepoRequest)
                .sanPham(sanPhamRequest).build();

        System.out.println(chiTietSanPhammAdd.getId());
        chiTietSanPhamRepo.save(chiTietSanPhammAdd);
        return "Add successfully";
    }

    @Override
    public void deleteById(Integer id) {
        chiTietSanPhamRepo.deleteById(id);
    }

    @Override
    public List<ChiTietSanPham> findAllProductDetailByProductId(Integer idProduct) {
        return chiTietSanPhamRepo.findAllProductDetailByProductId(idProduct);
    }

    @Override
    public ChiTietSanPham findProductDetailByIdMauSacAndProduct(Integer idMauSac, Integer idProduct, Integer idKichThuoc) {
        ChiTietSanPham chiTietSanPhams = chiTietSanPhamRepo.findProductDetailByIdMauSacAndProduct(idMauSac, idProduct, idKichThuoc);
        return chiTietSanPhams;
    }
}
