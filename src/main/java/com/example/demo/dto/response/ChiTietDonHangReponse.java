package com.example.demo.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChiTietDonHangReponse {
    private String ten;
    private String mauSac;
    private String kichThuoc;
    private String chatLieu;
    private Integer soLuong;
    private Double donGia;
}
