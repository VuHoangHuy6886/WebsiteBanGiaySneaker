package com.example.demo.dto.response;

import com.example.demo.entity.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ChiTietSanPhamResponse {
    private SanPham sanPhamID;
    private String ten;
    private Double gia;
    private Integer soLuong;
    private MauSac mauSacID;
    private KichThuoc kichThuocID;
}
