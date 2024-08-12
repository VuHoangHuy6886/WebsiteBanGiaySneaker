package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "ctsp")
public class ChiTietSanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "ma")
    private String ma;
    @Column(name = "so_luong")
    private Integer soLuong;
    @Column(name = "don_gia")
    private Float donGia;
    @Column(name = "trang_thai")
    private String trangThai;

//    Khoa Chinh
    @OneToMany(mappedBy = "chiTietSanPham")
    List<ChiTietGioHang> listChiTietGioHangs;

    @OneToMany(mappedBy = "chiTietSanPham")
    List<HoaDonChiTiet> hoaDonChiTiets;


//    khoa Phu
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mau_sac")
    private MauSac mauSac;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_kich_thuoc")
    private KichThuoc kichThuoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chat_lieu")
    private ChatLieu chatLieu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_loai_de")
    private LoaiDe loaiDe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn ( name = "id_san_pham")
    private SanPham sanPham;



}
