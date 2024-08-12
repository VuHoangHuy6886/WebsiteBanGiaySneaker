package com.example.demo.dto.request.update_request;

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
public class UpdateRequestSanPham {
    private Integer id;
    @NotBlank(message = "khong duoc de trong")
    private String ma;
    @NotBlank(message = "khong duoc de trong")
    private String ten;
    @NotBlank(message = "khong duoc de trong")
    private String trangThai;
    @NotBlank(message = "khong duoc de trong")
    private String idHang;
}
