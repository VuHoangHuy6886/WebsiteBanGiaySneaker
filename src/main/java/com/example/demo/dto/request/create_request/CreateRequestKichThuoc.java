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
public class CreateRequestKichThuoc {
    @NotBlank(message = "Khong duoc de trong ten")
    private String ten;
    @NotBlank(message = "trang thai khong duoc trong")
    private String trangThai;
}
