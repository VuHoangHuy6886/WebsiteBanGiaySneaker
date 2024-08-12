package com.example.demo.service.impl;

import com.example.demo.constant.TrangThaiHoaDon;
import com.example.demo.entity.*;
import com.example.demo.service.*;
import com.example.demo.util.CauHinhNgay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DonHangServiceImpl implements DonHangService {
    @Autowired
    private HoaDonService hoaDonService;
    @Autowired
    private LichSuTrangThaiHoaDonService lichSuTrangThaiHoaDonService;
    @Autowired
    private CauHinhNgay cauHinhNgay;
    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;
    @Autowired
    private NhanVienService nhanVienService;

    @Override
    public void xacNhanDonHang(Integer idHoaDon) {
        // update lai trang thai cho hoa don
        HoaDon hoaDon = hoaDonService.findById(idHoaDon);
        hoaDon.setTrangThai(TrangThaiHoaDon.XAC_NHAN.getValue());
        hoaDon.setNgayTao(cauHinhNgay.layNgayGioHienTai());
        hoaDon.setNhanViens(nhanVienService.nhanVienDangNhap());
        hoaDonService.save(hoaDon);
        LichSuDonHang lichSuDonHang = new LichSuDonHang();
        lichSuDonHang.setHoaDon(hoaDon);
        lichSuDonHang.setMoTa("Xác Nhận Đơn Hàng");
        lichSuDonHang.setNgayTao(cauHinhNgay.layNgayGioHienTai());
        lichSuDonHang.setTrangThai(hoaDon.getTrangThai());
        lichSuDonHang.setNhanViens(nhanVienService.nhanVienDangNhap());
        lichSuTrangThaiHoaDonService.save(lichSuDonHang);
    }

    @Override
    public void hoanThanh(Integer idHoaDon) {
        // update lai trang thai cho hoa don
        HoaDon hoaDon = hoaDonService.findById(idHoaDon);
        hoaDon.setTrangThai(TrangThaiHoaDon.HOAN_THANH.getValue());
        hoaDon.setNgayTao(cauHinhNgay.layNgayGioHienTai());
        hoaDon.setNhanViens(nhanVienService.nhanVienDangNhap());
        hoaDonService.save(hoaDon);
        LichSuDonHang lichSuDonHang = new LichSuDonHang();
        lichSuDonHang.setHoaDon(hoaDon);
        lichSuDonHang.setMoTa("Hoàn Thành Đơn Hàng");
        lichSuDonHang.setNgayTao(cauHinhNgay.layNgayGioHienTai());
        lichSuDonHang.setTrangThai(hoaDon.getTrangThai());
        lichSuDonHang.setNhanViens(nhanVienService.nhanVienDangNhap());
        lichSuTrangThaiHoaDonService.save(lichSuDonHang);
    }

    @Override
    public void huyDon(Integer idHoaDon) {
        List<ChiTietSanPham> chiTietSanPhamList = chiTietSanPhamService.findAll();
        List<HoaDonChiTiet> hoaDonChiTiet = hoaDonChiTietService.findAllByIdHoaDon(idHoaDon);

        for (HoaDonChiTiet hd : hoaDonChiTiet) {
            for (ChiTietSanPham ct : chiTietSanPhamList) {
                if (hd.getChiTietSanPham().getId().equals(ct.getId())) {
                    Integer soLuongUpdate = ct.getSoLuong() + hd.getSoLuong();
                    ct.setSoLuong(soLuongUpdate);
                }
            }
        }

        // cập nhập trạng thái hóa đơn
        HoaDon hoaDon = hoaDonService.findById(idHoaDon);
        hoaDon.setTrangThai(TrangThaiHoaDon.HUY_DON.getValue());
        hoaDon.setNgayTao(cauHinhNgay.layNgayGioHienTai());
        hoaDon.setNhanViens(nhanVienService.nhanVienDangNhap());
        hoaDonService.save(hoaDon);
        LichSuDonHang lichSuDonHang = new LichSuDonHang();
        lichSuDonHang.setHoaDon(hoaDon);
        lichSuDonHang.setMoTa("Hủy Đơn Hàng");
        lichSuDonHang.setNgayTao(cauHinhNgay.layNgayGioHienTai());
        lichSuDonHang.setTrangThai(hoaDon.getTrangThai());
        lichSuDonHang.setNhanViens(nhanVienService.nhanVienDangNhap());
        lichSuTrangThaiHoaDonService.save(lichSuDonHang);
    }
}
