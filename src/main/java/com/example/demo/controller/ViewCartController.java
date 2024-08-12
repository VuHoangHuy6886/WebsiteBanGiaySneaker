package com.example.demo.controller;

import com.example.demo.dto.response.KhachHangResponse;
import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.KhachHang;
import com.example.demo.service.BanHangOnlineService;
import com.example.demo.service.ChiTietSanPhamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;


@Controller
@RequestMapping("/trang-chu/cart")
public class ViewCartController {
    @Autowired
    private BanHangOnlineService banHangOnlineService;
    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;

    @GetMapping("/all")
    public String cart(Model model) {
        model.addAttribute("QuantityProductInCart", banHangOnlineService.getSoLuong());
        model.addAttribute("totalMoney", banHangOnlineService.getTongTien());
        model.addAttribute("listCart", banHangOnlineService.showCart());
        model.addAttribute("KhachHang", new KhachHangResponse());
        return "client/cart";
    }

    @GetMapping("/tang")
    public String tangSoLuong(@RequestParam("id") Integer id, Model model) {
        ChiTietSanPham ct = banHangOnlineService.getValueIfKeyExists(id);
        ChiTietSanPham ctInDatabase = chiTietSanPhamService.findById(id);
        if (ct != null) {
            System.out.println("Chi tiết sản phẩm có Số Lượng trong cart " + ct.getSoLuong());
        } else {
            System.out.println("Chi tiết sản phẩm không có trong cart");
        }
        int soLuongCheck = ct.getSoLuong() + 1;
        int soLuongIndatabase = ctInDatabase.getSoLuong();
        if (soLuongCheck >= soLuongIndatabase) {
            model.addAttribute("error", "Số Lượng Vượt Qua số Lượng Trong Database");
            model.addAttribute("listCart", banHangOnlineService.showCart());
            model.addAttribute("QuantityProductInCart", banHangOnlineService.getSoLuong());
            model.addAttribute("totalMoney", banHangOnlineService.getTongTien());
            model.addAttribute("KhachHang", new KhachHangResponse());
            return "client/cart";
        } else {
            banHangOnlineService.update(id, soLuongCheck);
            model.addAttribute("listCart", banHangOnlineService.showCart());
            model.addAttribute("QuantityProductInCart", banHangOnlineService.getSoLuong());
            model.addAttribute("totalMoney", banHangOnlineService.getTongTien());
            model.addAttribute("KhachHang", new KhachHangResponse());
            return "client/cart";
        }
    }

    @GetMapping("/giam")
    public String giamSoLuong(@RequestParam("id") Integer id, Model model) {
        ChiTietSanPham ct = banHangOnlineService.getValueIfKeyExists(id);
        ChiTietSanPham ctInDatabase = chiTietSanPhamService.findById(id);
        if (ct != null) {
            System.out.println("Chi tiết sản phẩm có trong cart " + ct.getSoLuong());
        } else {
            System.out.println("Chi tiết sản phẩm không có trong cart");
        }
        int soLuongCheck = ct.getSoLuong() - 1;
        int soLuongIndatabase = ctInDatabase.getSoLuong();
        if (soLuongCheck < 1) {
            model.addAttribute("QuantityProductInCart", banHangOnlineService.getSoLuong());
            model.addAttribute("totalMoney", banHangOnlineService.getTongTien());
            model.addAttribute("error", "Số Lượng Không Hợp Lệ ");
            model.addAttribute("listCart", banHangOnlineService.showCart());
            model.addAttribute("KhachHang", new KhachHangResponse());
            return "client/cart";
        } else {
            banHangOnlineService.update(id, soLuongCheck);
            model.addAttribute("QuantityProductInCart", banHangOnlineService.getSoLuong());
            model.addAttribute("totalMoney", banHangOnlineService.getTongTien());
            model.addAttribute("listCart", banHangOnlineService.showCart());
            model.addAttribute("KhachHang", new KhachHangResponse());
            return "client/cart";
        }
    }

    @GetMapping("/Xoa")
    public String xoa(@RequestParam("id") Integer id, Model model) {
        banHangOnlineService.remove(id);
        model.addAttribute("QuantityProductInCart", banHangOnlineService.getSoLuong());
        model.addAttribute("totalMoney", banHangOnlineService.getTongTien());
        model.addAttribute("listCart", banHangOnlineService.showCart());
        model.addAttribute("KhachHang", new KhachHangResponse());
        return "client/cart";
    }

    @GetMapping("/xoa-all")
    public String xoaAll(Model model) {
        banHangOnlineService.clear();
        model.addAttribute("QuantityProductInCart", banHangOnlineService.getSoLuong());
        model.addAttribute("totalMoney", banHangOnlineService.getTongTien());
        model.addAttribute("listCart", banHangOnlineService.showCart());
        model.addAttribute("KhachHang", new KhachHangResponse());
        return "client/cart";
    }

    @PostMapping("/thanh-toan")
    public String thanhToan(@Valid @ModelAttribute("KhachHang") KhachHangResponse khachHangResponse, BindingResult bindingResult, Model model) {
        System.out.println("controller thanh toán");
        if (bindingResult.hasErrors()) {
            model.addAttribute("QuantityProductInCart", banHangOnlineService.getSoLuong());
            model.addAttribute("totalMoney", banHangOnlineService.getTongTien());
            model.addAttribute("listCart", banHangOnlineService.showCart());
            model.addAttribute("KhachHang", khachHangResponse);
            return "client/cart";
        }
        Collection<ChiTietSanPham> listCart = banHangOnlineService.showCart();
        Integer soluongInCart = listCart.size();
        if (soluongInCart >= 1) {
            Boolean result = banHangOnlineService.cartToBill(khachHangResponse);
            if (result) {
                model.addAttribute("result", "Đặt HàngThành Công !");
                return "client/Message";
            } else {
                model.addAttribute("QuantityProductInCart", banHangOnlineService.getSoLuong());
                model.addAttribute("totalMoney", banHangOnlineService.getTongTien());
                model.addAttribute("listCart", banHangOnlineService.showCart());
                model.addAttribute("KhachHang", khachHangResponse);
                model.addAttribute("ketQuaThanhToan", "Thanh Toán Thất bại Số Lượng Sản phẩm Trong Giỏ Hàng > số Lượng trong Databse !");
                return "client/cart";
            }
        } else {
            model.addAttribute("QuantityProductInCart", banHangOnlineService.getSoLuong());
            model.addAttribute("totalMoney", banHangOnlineService.getTongTien());
            model.addAttribute("listCart", banHangOnlineService.showCart());
            model.addAttribute("KhachHang", khachHangResponse);
            model.addAttribute("ketQuaThanhToan", "Thanh Toán Thất bại Giỏ Hàng Trống !");
            return "client/cart";
        }

    }

}
