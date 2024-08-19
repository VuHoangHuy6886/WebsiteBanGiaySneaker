package com.example.demo.service.impl;

import com.example.demo.constant.LoaiHoaDon;
import com.example.demo.constant.TrangThaiHoaDon;
import com.example.demo.entity.*;
import com.example.demo.repository.ChiTietSanPhamRepo;
import com.example.demo.repository.HoaDonChiTietRepo;
import com.example.demo.repository.HoaDonRepo;
import com.example.demo.repository.KhacHangRepo;
import com.example.demo.service.*;
import com.example.demo.util.CauHinhNgay;
import com.example.demo.util.NhanVienDangNhap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BanHangTaiQuayServiceImpl implements BanHangTaiQuayService {
    @Autowired
    private HoaDonService hoaDonService;
    @Autowired
    private HoaDonRepo hoaDonRepo;
    @Autowired
    private CauHinhNgay cauHinhNgay;
    @Autowired
    private KhacHangRepo khacHangRepo;
    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    private KhachHangService khachHangService;
    @Autowired
    private HoaDonChiTietRepo hoaDonChiTietRepo;
    @Autowired
    private ChiTietSanPhamRepo chiTietSanPhamRepo;
    @Autowired
    private LichSuTrangThaiHoaDonService lichSuTrangThaiHoaDonService;
    @Autowired
    private NhanVienService nhanVienService;
    @Autowired
    private NhanVienDangNhap nhanVienDangNhap;

    public BanHangTaiQuayServiceImpl(NhanVienDangNhap nhanVienDangNhap) {
        this.nhanVienDangNhap = nhanVienDangNhap;
    }

    // 1.khởi tạo map lưu value hoa đơn
    private Map<Integer, List<ChiTietSanPham>> hoaDonMaps = new HashMap<>();
    // 6. lấy idHoaDon
    private Integer idHoaDon = null;


    // 2.thêm 1 hóa đơn mới
    @Override
    public void themMoiHoaDon(HoaDon hoaDon) {
        hoaDon.setMa(hoaDonService.genMaHD());
        hoaDon.setNgayTao(cauHinhNgay.layNgayGioHienTai());
        hoaDon.setTrangThai(TrangThaiHoaDon.TAO_HOA_DON.getValue());
        hoaDon.setLoaiHoaDon(LoaiHoaDon.TAI_QUAY.value);
        hoaDon.setNgaySua(cauHinhNgay.layNgayGioHienTai());
        hoaDon.setNhanViens(nhanVienService.nhanVienDangNhap());
        hoaDonRepo.saveAndFlush(hoaDon);
        // udpate tao lich su trang thai hoa don
        LichSuDonHang lichSuDonHang = new LichSuDonHang();
        lichSuDonHang.setHoaDon(hoaDon);
        lichSuDonHang.setNgayTao(cauHinhNgay.layNgayGioHienTai());
        lichSuDonHang.setMoTa("Hoa Don Co Trang Thai Tao hoa Don !");
        lichSuDonHang.setTrangThai(TrangThaiHoaDon.TAO_HOA_DON.getValue());
        lichSuDonHang.setNhanViens(nhanVienService.nhanVienDangNhap());
        lichSuTrangThaiHoaDonService.save(lichSuDonHang);
        hoaDonMaps.put(hoaDon.getId(), new ArrayList<>());
    }

    // 3.find list chi tiết sản phẩm bằng id hóa đơn
    @Override
    public List<ChiTietSanPham> layListChiTietSanPhamByIdHoaDon(Integer idHoaDon) {
        this.idHoaDon = idHoaDon;
        return hoaDonMaps.get(idHoaDon);
    }

    // 4.find hoa don co trang thai la tao hoa don
    @Override
    public List<HoaDon> layHoaDonMoiTao() {
        List<HoaDon> hoaDons = hoaDonRepo.findAll();
        return hoaDons.stream()
                .filter(hd -> hd.getTrangThai().equals(TrangThaiHoaDon.TAO_HOA_DON.getValue()))
                .toList();  // Chuyển stream về List
    }

    // 5.thêm sản phẩm vào hóa đơn
    @Override
    public void themSanPhamChiTietVaoHoaDon(Integer idProductDetail, Integer soLuong) {
        System.out.println("id sản phẩm là : " + idProductDetail);
        System.out.println("Số Lượng ban đầu là : " + soLuong);
        System.out.println("id hoa don  = " + idHoaDon);

        // 5.1 check id hoa don trc
        if (idHoaDon != null) {
            //5.2 lấy danh sách sản phẩm thuộc id hoa don
            List<ChiTietSanPham> list = hoaDonMaps.get(idHoaDon);
            if (list == null) {
                list = new ArrayList<>();
                hoaDonMaps.put(idHoaDon, list);
            }

            //5.3 call product detail in database
            ChiTietSanPham detailDb = chiTietSanPhamService.findById(idProductDetail);
            System.out.println("Chi tiết sản phẩm từ DB: " + detailDb);

            // 5.4 tạo ra 1 product detail mới
            ChiTietSanPham chiTietSanPhamAdd = ChiTietSanPham.builder()
                    .id(detailDb.getId()).ma(detailDb.getMa()).trangThai(detailDb.getTrangThai())
                    .donGia(detailDb.getDonGia()).soLuong(soLuong).sanPham(detailDb.getSanPham())
                    .mauSac(detailDb.getMauSac()).kichThuoc(detailDb.getKichThuoc())
                    .chatLieu(detailDb.getChatLieu()).loaiDe(detailDb.getLoaiDe())
                    .build();

            //5.5 tạo biến check sản phẩm lúc add có trùng hay không
            Boolean checkProductDetail = false;

            //5.6 tạo vòng for để add sản phẩm vào list hoa don chi tiet by id hoa don
            for (int i = 0; i < list.size(); i++) {
                // 5.7 nếu trùng thì cộng số lượng
                if (list.get(i).getId().equals(chiTietSanPhamAdd.getId())) {
                    // 5.8 => vào đây là nó có vậy sẽ gọi nó ra để cập nhập
                    ChiTietSanPham productDetailGetI = list.get(i);
                    System.out.println("Sản phẩm trùng id: " + productDetailGetI.getId());

                    // 5.9 => tạo đối tượng mới để cập nhật lại list
                    Integer soLuongUpdate = productDetailGetI.getSoLuong() + soLuong;
                    System.out.println("Số lượng cập nhật: " + soLuongUpdate);

                    productDetailGetI.setSoLuong(soLuongUpdate);
                    list.set(i, productDetailGetI);
                    checkProductDetail = true;
                    break; // thêm break để tránh vòng lặp tiếp tục
                }
            }
            // Trường Hợp 2 : nếu checkProductDetail == false
            if (!checkProductDetail) {
                list.add(chiTietSanPhamAdd);
                System.out.println("Thêm sản phẩm mới vào list: " + chiTietSanPhamAdd.getId() + " - Số lượng: " + chiTietSanPhamAdd.getSoLuong());
            }

            List<ChiTietSanPham> cart = hoaDonMaps.get(idHoaDon);
            for (ChiTietSanPham car : cart) {
                System.out.println("Sản Phẩm Trong List " + car.getId() + " - Số lượng: " + car.getSoLuong());
            }
        } else {
            throw new IllegalArgumentException("idHoaDon is null");
        }
    }

    // 6. xóa product detail
    @Override
    public void xoa(Integer idProductDetail) {
        List<ChiTietSanPham> cart = hoaDonMaps.get(idHoaDon);
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getId().equals(idProductDetail)) {
                cart.remove(i);
            }
        }
    }

    // 7. xóa all
    @Override
    public void xoaAll(Integer idHoaDon) {
        this.hoaDonMaps.remove(idHoaDon);
    }

    // 8. tổng số lượng sản phẩm in cart by key
    @Override
    public Integer totalQuantity(Integer idHoaDon) {
        List<ChiTietSanPham> cart = hoaDonMaps.get(idHoaDon);
        if (cart == null) {
            return 0;
        }
        return cart.stream()
                .mapToInt(ChiTietSanPham::getSoLuong)
                .sum();
    }


    // 9. get tổng tiền
    @Override
    public Double totalPrice(Integer idHoaDon) {
        List<ChiTietSanPham> cart = hoaDonMaps.get(idHoaDon);
        if (cart == null) {
            return 0.0;
        }
        return cart.stream()
                .mapToDouble(c -> c.getSoLuong() * c.getDonGia()) // Giả sử `ChiTietSanPham` có phương thức `getDonGia()`
                .sum();
    }

    // 10. thanh toan
    @Override
    public Boolean thanhToan(String phuongThucThanhToan, KhachHang khachHang) {
        // 1. check lại sản phẩm trươc khi thanh toán
        boolean checkCartToBill = true;
        List<ChiTietSanPham> cart = hoaDonMaps.get(idHoaDon);
        for (int i = 0; i < cart.size(); i++) {
            // 1.2 : soLuong product detail in database
            Integer slInDB = chiTietSanPhamService.findById(cart.get(i).getId()).getSoLuong();
            // 1.3 : check với sô luong in cart
            Integer slInCart = cart.get(i).getSoLuong();
            if (slInCart > slInDB) {
                checkCartToBill = false;
                break;
            }
        }
        // check xem giỏ hang co bị trong hay ko
        Integer slInCart = cart.size();
        if (slInCart < 1) {
            checkCartToBill = false;
        }
        // 2. tiep tục check va update lai
        if (checkCartToBill) {
            // 2.1 : neu co have customer
            if (khachHang.getId() != null) {
                // tao khach hang dung du lieu
                KhachHang customer = new KhachHang();
                customer.setId(khachHang.getId());
                customer.setMa(khachHangService.genMaKH());
                customer.setTen(khachHang.getTen());
                customer.setEmail(khachHang.getEmail());
                customer.setDiaChi(khachHang.getDiaChi());
                customer.setSdt(khachHang.getSdt());
                customer.setMatKhau(khachHang.getMatKhau());
                khacHangRepo.saveAndFlush(customer);
                // update lai hoa don
                HoaDon hoaDon = hoaDonService.findById(idHoaDon);
                hoaDon.setPhuongThucThanhToan(phuongThucThanhToan);
                hoaDon.setTrangThai(TrangThaiHoaDon.HOAN_THANH.getValue());
                hoaDon.setTongTien(this.totalPrice(idHoaDon));
                hoaDon.setKhachHang(customer);
                hoaDon.setNhanViens(nhanVienService.nhanVienDangNhap());
                hoaDon.setNgayTao(cauHinhNgay.layNgayGioHienTai());
                hoaDonRepo.saveAndFlush(hoaDon);


                // udpate tao lich su trang thai hoa don
                LichSuDonHang lichSuDonHang = new LichSuDonHang();
                lichSuDonHang.setHoaDon(hoaDon);
                lichSuDonHang.setNhanViens(nhanVienDangNhap.getNhanVien());
                lichSuDonHang.setNgayTao(cauHinhNgay.layNgayGioHienTai());
                lichSuDonHang.setNhanViens(nhanVienService.nhanVienDangNhap());
                lichSuDonHang.setMoTa("Hoa Don Co Trang Thai Hoan Thanh !");
                lichSuDonHang.setTrangThai(TrangThaiHoaDon.HOAN_THANH.getValue());
                lichSuTrangThaiHoaDonService.save(lichSuDonHang);


                // update lai sản phẩm trong giỏ vao hoa don chi tiet
                List<ChiTietSanPham> list = this.layListChiTietSanPhamByIdHoaDon(idHoaDon);
                for (int i = 0; i < list.size(); i++) {
                    HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                    hoaDonChiTiet.setHoaDon(hoaDon);
                    hoaDonChiTiet.setChiTietSanPham(list.get(i));
                    hoaDonChiTiet.setSoLuong(list.get(i).getSoLuong());
                    hoaDonChiTiet.setGiaBan(list.get(i).getDonGia());
                    hoaDonChiTietRepo.save(hoaDonChiTiet);
                    // hàm update lại sản phẩm trong database
                    List<ChiTietSanPham> sanPhamList = chiTietSanPhamRepo.findAll();
                    for (ChiTietSanPham spDB : sanPhamList) {
                        if (list.get(i).getId().equals(spDB.getId())) {
                            int slUpdate = spDB.getSoLuong() - list.get(i).getSoLuong();
                            spDB.setSoLuong(slUpdate);
                            chiTietSanPhamRepo.save(spDB);
                        }
                    }
                }
                this.xoaAll(idHoaDon);
                this.idHoaDon = null;
            } else {
                // gọi đối tượng khách lẻ ra
                String tenKH = "KHÁCH LẺ";
                KhachHang khachLe = khacHangRepo.findByKhachLe(tenKH);
                HoaDon hoaDon = hoaDonService.findById(idHoaDon);
                if (khachLe != null) {
                    // update lai hoa don
                    hoaDon.setKhachHang(khachLe);
                    hoaDon.setPhuongThucThanhToan(phuongThucThanhToan);
                    hoaDon.setTrangThai(TrangThaiHoaDon.HOAN_THANH.getValue());
                    hoaDon.setTongTien(this.totalPrice(idHoaDon));
                    hoaDon.setNgayTao(cauHinhNgay.layNgayGioHienTai());
                    hoaDon.setNhanViens(nhanVienService.nhanVienDangNhap());
                    hoaDonRepo.saveAndFlush(hoaDon);
                } else {
                    // nếu khách lẻ không có thi tạo
                    KhachHang customer = new KhachHang();
                    customer.setMa(khachHangService.genMaKH());
                    customer.setTen(tenKH);
                    customer.setEmail("KhachLe@gmail.com");
                    customer.setDiaChi("Không Có");
                    customer.setSdt("Không Có");
                    customer.setMatKhau("12345");
                    khacHangRepo.saveAndFlush(customer);
                    hoaDon.setKhachHang(customer);
                    hoaDon.setPhuongThucThanhToan(phuongThucThanhToan);
                    hoaDon.setTrangThai(TrangThaiHoaDon.HOAN_THANH.getValue());
                    hoaDon.setTongTien(this.totalPrice(idHoaDon));
                    hoaDon.setNgayTao(cauHinhNgay.layNgayGioHienTai());
                    hoaDon.setNhanViens(nhanVienDangNhap.getNhanVien());
                    hoaDonRepo.saveAndFlush(hoaDon);
                }


                // udpate tao lich su trang thai hoa don
                LichSuDonHang lichSuDonHang = new LichSuDonHang();
                lichSuDonHang.setHoaDon(hoaDon);
                lichSuDonHang.setNgayTao(cauHinhNgay.layNgayGioHienTai());
                lichSuDonHang.setMoTa("Hoa Don Co Trang Thai Hoan Thanh !");
                lichSuDonHang.setTrangThai(TrangThaiHoaDon.HOAN_THANH.getValue());
                lichSuDonHang.setNhanViens(nhanVienService.nhanVienDangNhap());
                lichSuTrangThaiHoaDonService.save(lichSuDonHang);

                // update lai sản phẩm trong giỏ vao hoa don chi tiet
                List<ChiTietSanPham> list = this.layListChiTietSanPhamByIdHoaDon(idHoaDon);
                for (int i = 0; i < list.size(); i++) {
                    HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                    hoaDonChiTiet.setHoaDon(hoaDon);
                    hoaDonChiTiet.setChiTietSanPham(list.get(i));
                    hoaDonChiTiet.setSoLuong(list.get(i).getSoLuong());
                    hoaDonChiTiet.setGiaBan(list.get(i).getDonGia());
                    hoaDonChiTietRepo.save(hoaDonChiTiet);
                    // hàm update lại sản phẩm trong database
                    List<ChiTietSanPham> sanPhamList = chiTietSanPhamRepo.findAll();
                    for (ChiTietSanPham spDB : sanPhamList) {
                        if (list.get(i).getId().equals(spDB.getId())) {
                            int slUpdate = spDB.getSoLuong() - list.get(i).getSoLuong();
                            spDB.setSoLuong(slUpdate);
                            chiTietSanPhamRepo.save(spDB);
                        }
                    }
                }
                this.xoaAll(idHoaDon);
                this.idHoaDon = null;
            }
        }
        return checkCartToBill;
    }

    @Override
    public void clentId() {
        this.idHoaDon = null;
    }
}
