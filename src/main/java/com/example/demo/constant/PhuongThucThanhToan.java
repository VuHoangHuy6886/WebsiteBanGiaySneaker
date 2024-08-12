package com.example.demo.constant;

public enum PhuongThucThanhToan {
    TIEN_MAT("TIỀN MẶT"),
    CHUYEN_KHOAN("CHUYỂN KHOẢN"),
    THANH_TOAN_KHI_NHAN_HANG("THANH TOÁN KHI NHẬN HÀNG");

    public final String value;

    PhuongThucThanhToan(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
