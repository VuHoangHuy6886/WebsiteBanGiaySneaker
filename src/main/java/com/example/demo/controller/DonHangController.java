package com.example.demo.controller;

import com.example.demo.constant.TrangThaiHoaDon;
import com.example.demo.dto.response.ChiTietDonHangReponse;
import com.example.demo.dto.response.ChiTietSanPhamResponse;
import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.HoaDonChiTiet;
import com.example.demo.entity.LichSuDonHang;
import com.example.demo.service.*;
import com.example.demo.util.CauHinhNgay;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/Don-Hang")
public class DonHangController {
    @Autowired
    private HoaDonService hoaDonService;
    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;
    @Autowired
    private SanPhamService sanPhamService;
    @Autowired
    private MauSacService mauSacService;
    @Autowired
    private KichThuocService kichThuocService;
    @Autowired
    private ChatLieuService chatLieuService;
    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    private DonHangService donHangService;
    @Autowired
    private LichSuTrangThaiHoaDonService lichSuTrangThaiHoaDonService;

    @GetMapping("/all")
    public String showHoaDon(
            @RequestParam(value = "page", defaultValue = "0", required = false) String pageStr,
            @RequestParam(value = "size", defaultValue = "5", required = false) String sizeStr,
            Model model) {
        Integer page, size;
        try {
            page = Integer.parseInt(pageStr);
            size = Integer.parseInt(sizeStr);
        } catch (NumberFormatException e) {
            page = 0;
            size = 5;
        }
        Page<HoaDon> pages = hoaDonService.findAllSordDate(page, size);
        model.addAttribute("pages", pages);
        return "admin/donhang/index";
    }

    @GetMapping("/Detail")
    public String showHoaDonChiTiet(@RequestParam("id") Integer id, Model model) {
        List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietService.findAllByIdHoaDon(id);
        HoaDon hoaDon = hoaDonService.findById(id);
        List<ChiTietDonHangReponse> reponses = new ArrayList<>();
        for (HoaDonChiTiet hd : hoaDonChiTietList) {
            ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findById(hd.getChiTietSanPham().getId());
            String ten = sanPhamService.findById(chiTietSanPham.getSanPham().getId()).getTen();
            String mauSac = mauSacService.findById(chiTietSanPham.getMauSac().getId()).getTen();
            String kichThuoc = kichThuocService.findById(chiTietSanPham.getKichThuoc().getId()).getTen();
            String chatLieu = chatLieuService.findById(chiTietSanPham.getChatLieu().getId()).getTen();
            ChiTietDonHangReponse dh = ChiTietDonHangReponse
                    .builder().ten(ten).chatLieu(chatLieu).donGia(Double.valueOf(hd.getGiaBan()))
                    .kichThuoc(kichThuoc).mauSac(mauSac).soLuong(hd.getSoLuong())
                    .build();
            reponses.add(dh);
        }
        model.addAttribute("History", lichSuTrangThaiHoaDonService.findHistoryIdHoaDon(id));
        model.addAttribute("pages", reponses);
        model.addAttribute("HoaDon", hoaDon);
        return "/admin/donhang/DonHangChiTiet";
    }

    @PostMapping("/Xac-Nhan")
    public String xacNhan(@ModelAttribute("idHD") Integer id, Model model) {
        // vẫn return về trang detail
        List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietService.findAllByIdHoaDon(id);
        HoaDon hoaDon = hoaDonService.findById(id);
        List<ChiTietDonHangReponse> reponses = new ArrayList<>();
        for (HoaDonChiTiet hd : hoaDonChiTietList) {
            ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findById(hd.getChiTietSanPham().getId());
            String ten = sanPhamService.findById(chiTietSanPham.getSanPham().getId()).getTen();
            String mauSac = mauSacService.findById(chiTietSanPham.getMauSac().getId()).getTen();
            String kichThuoc = kichThuocService.findById(chiTietSanPham.getKichThuoc().getId()).getTen();
            String chatLieu = chatLieuService.findById(chiTietSanPham.getChatLieu().getId()).getTen();
            ChiTietDonHangReponse dh = ChiTietDonHangReponse
                    .builder().ten(ten).chatLieu(chatLieu).donGia(Double.valueOf(hd.getGiaBan()))
                    .kichThuoc(kichThuoc).mauSac(mauSac).soLuong(hd.getSoLuong())
                    .build();
            reponses.add(dh);
        }
        donHangService.xacNhanDonHang(id);
        model.addAttribute("History", lichSuTrangThaiHoaDonService.findHistoryIdHoaDon(id));
        model.addAttribute("pages", reponses);
        model.addAttribute("HoaDon", hoaDon);
        return "/admin/donhang/DonHangChiTiet";
    }

    @PostMapping("/Hoan-Thanh")
    public String hoanThanh(@ModelAttribute("idHD") Integer id, Model model) {
        // vẫn return về trang detail
        List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietService.findAllByIdHoaDon(id);
        HoaDon hoaDon = hoaDonService.findById(id);
        List<ChiTietDonHangReponse> reponses = new ArrayList<>();
        for (HoaDonChiTiet hd : hoaDonChiTietList) {
            ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findById(hd.getChiTietSanPham().getId());
            String ten = sanPhamService.findById(chiTietSanPham.getSanPham().getId()).getTen();
            String mauSac = mauSacService.findById(chiTietSanPham.getMauSac().getId()).getTen();
            String kichThuoc = kichThuocService.findById(chiTietSanPham.getKichThuoc().getId()).getTen();
            String chatLieu = chatLieuService.findById(chiTietSanPham.getChatLieu().getId()).getTen();
            ChiTietDonHangReponse dh = ChiTietDonHangReponse
                    .builder().ten(ten).chatLieu(chatLieu).donGia(Double.valueOf(hd.getGiaBan()))
                    .kichThuoc(kichThuoc).mauSac(mauSac).soLuong(hd.getSoLuong())
                    .build();
            reponses.add(dh);
        }
        donHangService.hoanThanh(id);
        model.addAttribute("History", lichSuTrangThaiHoaDonService.findHistoryIdHoaDon(id));
        model.addAttribute("pages", reponses);
        model.addAttribute("HoaDon", hoaDon);
        return "/admin/donhang/DonHangChiTiet";
    }

    @PostMapping("/Huy")
    public String huy(@ModelAttribute("idHD") Integer id, Model model) {
        // vẫn return về trang detail
        List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietService.findAllByIdHoaDon(id);
        HoaDon hoaDon = hoaDonService.findById(id);
        List<ChiTietDonHangReponse> reponses = new ArrayList<>();
        for (HoaDonChiTiet hd : hoaDonChiTietList) {
            ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findById(hd.getChiTietSanPham().getId());
            String ten = sanPhamService.findById(chiTietSanPham.getSanPham().getId()).getTen();
            String mauSac = mauSacService.findById(chiTietSanPham.getMauSac().getId()).getTen();
            String kichThuoc = kichThuocService.findById(chiTietSanPham.getKichThuoc().getId()).getTen();
            String chatLieu = chatLieuService.findById(chiTietSanPham.getChatLieu().getId()).getTen();
            ChiTietDonHangReponse dh = ChiTietDonHangReponse
                    .builder().ten(ten).chatLieu(chatLieu).donGia(Double.valueOf(hd.getGiaBan()))
                    .kichThuoc(kichThuoc).mauSac(mauSac).soLuong(hd.getSoLuong())
                    .build();
            reponses.add(dh);
        }
        donHangService.huyDon(id);
        model.addAttribute("History", lichSuTrangThaiHoaDonService.findHistoryIdHoaDon(id));
        model.addAttribute("pages", reponses);
        model.addAttribute("HoaDon", hoaDon);
        return "/admin/donhang/DonHangChiTiet";
    }
}
