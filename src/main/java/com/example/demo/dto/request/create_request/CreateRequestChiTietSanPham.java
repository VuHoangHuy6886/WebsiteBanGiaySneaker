package com.example.demo.dto.request.create_request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CreateRequestChiTietSanPham {

    @NotBlank(message = "Khong duoc de trong ma")
    private String ma;
    @NotBlank(message = "khong duoc de trong")
    private String soLuong;
    @NotBlank(message = "khong duoc de trong")
    private String donGia;
    @NotBlank(message = "khong duoc de trong")
    private String trangThai;

//    bang cha

    @NotBlank(message = "khong duoc de trong")
    private String idMauSac;
    @NotBlank(message = "khong duoc de trong")
    private String idKichThuoc;
    @NotBlank(message = "khong duoc de trong")
    private String idChatLieu;
    @NotBlank(message = "khong duoc de trong")
    private String idLoaiDe;
    @NotBlank(message = "khong duoc de trong")
    private String idSanPham;
}
