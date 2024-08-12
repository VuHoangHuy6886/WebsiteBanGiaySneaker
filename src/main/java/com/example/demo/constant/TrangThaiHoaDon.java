package com.example.demo.constant;

public enum TrangThaiHoaDon {
    CHO_XAC_NHAN("CHỜ XÁC NHẬN"),
    TAO_HOA_DON("TẠO HÓA ĐƠN"),
    XAC_NHAN("XÁC NHẬN"),
    HUY_DON("ĐÃ HỦY"),
    HOAN_THANH("HOÀN THÀNH");

    private final String value;

    TrangThaiHoaDon(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
