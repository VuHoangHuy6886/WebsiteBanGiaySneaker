package com.example.demo.service;

import com.example.demo.dto.response.KhachHangResponse;
import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.KhachHang;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface BanHangOnlineService {

    Boolean addToCart(Integer idProductDetail, Integer soLuongMua);

    Boolean cartToBill(KhachHangResponse khachHang);

    Collection<ChiTietSanPham> showCart();

    void remove(int id);

    void update(int id, int soLuong);

    void clear();

    int getSoLuong();

    Double getTongTien();

    ChiTietSanPham getValueIfKeyExists(int key);

}
