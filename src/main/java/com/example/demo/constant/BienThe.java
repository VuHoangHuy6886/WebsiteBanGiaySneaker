package com.example.demo.constant;

public enum BienThe {
    HOAT_DONG("Hoạt Động"),
    KHONG_HOAT_DONG("Không Hoạt Động");
    private final String value;

    BienThe(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
