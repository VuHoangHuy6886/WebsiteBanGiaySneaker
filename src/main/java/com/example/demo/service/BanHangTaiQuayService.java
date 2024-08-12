package com.example.demo.service;

import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.KhachHang;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BanHangTaiQuayService {

    // 2.thêm 1 hóa đơn mới
    void themMoiHoaDon(HoaDon hoaDon);

    // 3.find list chi tiết sản phẩm bằng id hóa đơn
    List<ChiTietSanPham> layListChiTietSanPhamByIdHoaDon(Integer idHoaDon);

    // 4.find hoa don co trang thai la tao hoa don
    List<HoaDon> layHoaDonMoiTao();

    // 5.thêm sản phẩm vào hóa đơn
    void themSanPhamChiTietVaoHoaDon(Integer idProductDetail, Integer soLuong);

    // 6. xóa product detail
    void xoa(Integer idProductDetail);

    // 7. xóa all
    void xoaAll(Integer idHoaDon);

    // 8. tổng số lượng sản phẩm in cart by key
    Integer totalQuantity(Integer idHoaDon);

    // 9. get tổng tiền
    Double totalPrice(Integer idHoaDon);

    // 10. thanh toan
    Boolean thanhToan(String phuongThucThanhToan, KhachHang khachHang);

    // xoa id hoa don da click
    void clentId();
}
