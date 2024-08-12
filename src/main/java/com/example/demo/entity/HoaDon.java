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
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "hoa_don")
public class HoaDon {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    @Column(name = "ma")
    private String ma;
    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;
    @Column(name = "ngay_sua")
    private LocalDateTime ngaySua;
    @Column(name = "loai_hoa_don")
    private String loaiHoaDon;
    @Column(name = "phuong_thuc_thanh_toan")
    private String phuongThucThanhToan;
    @Column(name = "tong_tien")
    private Double tongTien;
    @Column(name = "trang_thai")
    private String trangThai;

//  khoa phu
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_khach_hang")
    private KhachHang khachHang;

    @ManyToOne(fetch =FetchType.LAZY )
    @JoinColumn(name = "id_nhan_vien")
    private NhanVien nhanViens;

//  khoa chinh
    @OneToMany(mappedBy = "hoaDon")
    List<HoaDonChiTiet> hoaDonChiTiets;

    @OneToMany(mappedBy = "hoaDon")
    private List<LichSuDonHang> lichSuDonHangs;


}
