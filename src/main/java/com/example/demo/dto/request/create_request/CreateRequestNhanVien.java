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
public class CreateRequestNhanVien {


    @NotBlank(message = "Ma khong duoc de trong ten")
    private String ma;
    @NotBlank(message = "Ten khong duoc de trong ten")
    private String ten;
    @NotBlank(message = "Sdt khong duoc de trong ten")
    private String sdt;
    @NotBlank(message = "Dia chi khong duoc de trong ten")
    private String diaChi;
    @NotBlank(message = "Email khong duoc de trong ten")
    private String email;
    @NotBlank(message = "Mat Khau khong duoc de trong ten")
    private String matKhau;

}
