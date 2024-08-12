package com.example.demo.dto.request.create_request;

import com.example.demo.entity.SanPham;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CreateRequestHang {
    @NotBlank(message = "Khong duoc de trong ten")
    private String ten;
    @NotBlank(message = "trang thai khong duoc trong")
    private String trangThai;
}
