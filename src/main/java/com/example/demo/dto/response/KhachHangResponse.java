package com.example.demo.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Validated
public class KhachHangResponse {
    @NotBlank(message = "Tên Khách Hàng Không Được Để Trống !")
    private String ten;

    @NotBlank(message = "email Khách Hàng Không Được Để Trống !")
    @Email(message = "Vui lòng nhập đúng định dạng email!")
    private String email;

    @NotBlank(message = "Số điện thoại không được để trống!")
    @Pattern(regexp = "0[0-9]{9,10}", message = "Vui lòng nhập đúng định dạng số điện thoại!")
    private String soDienThoai;

    @NotBlank(message = "Địa chỉ không được để trống!")
    private String diaChi;
}
