package com.example.demo.constant;

public enum LoaiHoaDon {
    ONLINE("ONLINE"),
    TAI_QUAY("TẠI QUẦY");
    public final String value;

    LoaiHoaDon(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
