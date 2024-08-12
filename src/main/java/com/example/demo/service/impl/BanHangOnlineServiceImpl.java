package com.example.demo.service.impl;

import com.example.demo.constant.LoaiHoaDon;
import com.example.demo.constant.PhuongThucThanhToan;
import com.example.demo.constant.TrangThaiHoaDon;
import com.example.demo.dto.response.KhachHangResponse;
import com.example.demo.entity.*;
import com.example.demo.repository.ChiTietSanPhamRepo;
import com.example.demo.repository.HoaDonChiTietRepo;
import com.example.demo.repository.HoaDonRepo;
import com.example.demo.repository.KhacHangRepo;
import com.example.demo.service.*;
import com.example.demo.util.CauHinhNgay;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BanHangOnlineServiceImpl implements BanHangOnlineService {
    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    private KhacHangRepo khacHangRepo;
    @Autowired
    private HoaDonRepo hoaDonRepo;
    @Autowired
    private ChiTietSanPhamRepo chiTietSanPhamRepo;
    @Autowired
    private HoaDonChiTietRepo hoaDonChiTietRepo;
    @Autowired
    private CauHinhNgay cauHinhNgay;
    @Autowired
    private HoaDonService hoaDonService;
    @Autowired
    private KhachHangService khachHangService;
    @Autowired
    private LichSuTrangThaiHoaDonService lichSuTrangThaiHoaDonService;

    private Map<Integer, ChiTietSanPham> cart = new HashMap<>();


    @Override
    public Boolean addToCart(Integer idProductDetail, Integer soLuongMua) {
        ChiTietSanPham chiTietSanPhamFindByID = chiTietSanPhamService.findById(idProductDetail);
        if (chiTietSanPhamFindByID != null) {
            ChiTietSanPham chiTietSanPhamAddToCart = new ChiTietSanPham();
            chiTietSanPhamAddToCart.setId(chiTietSanPhamFindByID.getId());
            chiTietSanPhamAddToCart.setSanPham(chiTietSanPhamFindByID.getSanPham());
            chiTietSanPhamAddToCart.setDonGia(chiTietSanPhamFindByID.getDonGia());
            chiTietSanPhamAddToCart.setSoLuong(chiTietSanPhamFindByID.getSoLuong());
            chiTietSanPhamAddToCart.setMa(chiTietSanPhamFindByID.getMa());
            chiTietSanPhamAddToCart.setMauSac(chiTietSanPhamFindByID.getMauSac());
            chiTietSanPhamAddToCart.setKichThuoc(chiTietSanPhamFindByID.getKichThuoc());
            chiTietSanPhamAddToCart.setChatLieu(chiTietSanPhamFindByID.getChatLieu());
            chiTietSanPhamAddToCart.setLoaiDe(chiTietSanPhamFindByID.getLoaiDe());
            if (cart.containsKey(chiTietSanPhamFindByID.getId())) {
                System.out.println("Sản phẩm đã có trong giỏ hàng");
                ChiTietSanPham ctSpInCart = cart.get(chiTietSanPhamFindByID.getId());

                // Tăng số lượng
                int newSoLuong = ctSpInCart.getSoLuong() + soLuongMua;
                if (newSoLuong < chiTietSanPhamFindByID.getSoLuong()) {
                    ctSpInCart.setSoLuong(newSoLuong);
                    cart.put(ctSpInCart.getId(), ctSpInCart);
                    return true;
                } else {
                    return false;
                }
            } else {
                chiTietSanPhamAddToCart.setSoLuong(soLuongMua);
                cart.put(chiTietSanPhamAddToCart.getId(), chiTietSanPhamAddToCart);
            }
        }
        return true;
    }

    @Transactional
    @Override
    public Boolean cartToBill(KhachHangResponse khachHang) {
        // 1. Trước tiên cần check lại số lượng sản phẩm trong cart đã hợp lệ chưa
        boolean checkCartToBill = true;
        for (ChiTietSanPham spInCart : cart.values()) {
            ChiTietSanPham spInDatabase = chiTietSanPhamService.findById(spInCart.getId());
            if (spInCart.getSoLuong() > spInDatabase.getSoLuong()) {
                System.out.println("Số lượng trong cart: " + spInCart.getSoLuong());
                System.out.println("Số lượng trong database: " + spInDatabase.getSoLuong());
                checkCartToBill = false;
                break; // Dừng vòng lặp sớm khi phát hiện không hợp lệ
            }
        }

        if (checkCartToBill) {
            //2.1. create the customer entity to customer_response
            KhachHang customer = new KhachHang();
            customer.setMa(khachHangService.genMaKH());
            customer.setTen(khachHang.getTen());
            customer.setEmail(khachHang.getEmail());
            customer.setDiaChi(khachHang.getDiaChi());
            customer.setSdt(khachHang.getSoDienThoai());
            customer.setMatKhau("12345");
            khacHangRepo.saveAndFlush(customer);

            // 2.2.create bill
            HoaDon hoaDon = new HoaDon();
            hoaDon.setMa(hoaDonService.genMaHD()); // tôi muốn mã từ gen như sau : "HD" + 1 số từ : 1 - N
            hoaDon.setKhachHang(customer);
            hoaDon.setPhuongThucThanhToan(PhuongThucThanhToan.THANH_TOAN_KHI_NHAN_HANG.value);
            hoaDon.setLoaiHoaDon(LoaiHoaDon.ONLINE.value);
            hoaDon.setNgayTao(cauHinhNgay.layNgayGioHienTai());
            hoaDon.setNgaySua(cauHinhNgay.layNgayGioHienTai());
            hoaDon.setTrangThai(TrangThaiHoaDon.CHO_XAC_NHAN.getValue());
            hoaDon.setTongTien(this.getTongTien());
            hoaDonRepo.saveAndFlush(hoaDon);
            // udpate tao lich su trang thai hoa don
            LichSuDonHang lichSuDonHang = new LichSuDonHang();
            lichSuDonHang.setHoaDon(hoaDon);
            lichSuDonHang.setNgayTao(cauHinhNgay.layNgayGioHienTai());
            lichSuDonHang.setMoTa("Hoa Don Co Trang Thai Cho Xac Nhan !");
            lichSuDonHang.setTrangThai(TrangThaiHoaDon.CHO_XAC_NHAN.getValue());
            lichSuTrangThaiHoaDonService.save(lichSuDonHang);

            // 2.3. update lại sản phẩm trong giỏ hàng vao hoa don
            for (ChiTietSanPham sp : cart.values()) {
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setHoaDon(hoaDon);
                hoaDonChiTiet.setChiTietSanPham(sp);
                hoaDonChiTiet.setSoLuong(sp.getSoLuong());
                hoaDonChiTiet.setGiaBan(sp.getDonGia());
                hoaDonChiTietRepo.save(hoaDonChiTiet);

                // hàm update lại sản phẩm trong database
                List<ChiTietSanPham> sanPhamList = chiTietSanPhamRepo.findAll();
                for (ChiTietSanPham spDB : sanPhamList) {
                    if (sp.getId().equals(spDB.getId())) {
                        int slUpdate = spDB.getSoLuong() - sp.getSoLuong();
                        spDB.setSoLuong(slUpdate);
                        chiTietSanPhamRepo.save(spDB);
                    }
                }
            }
            cart.clear();
            System.out.println("Thanh Toán Thành Công");
        } else {
            System.out.println("Thanh Toán Thất Bại do số lượng không hợp lệ");
        }

        return checkCartToBill;
    }

    @Override
    public Collection<ChiTietSanPham> showCart() {
        return this.cart.values();
    }

    @Override
    public void remove(int id) {
        this.cart.remove(id);
    }

    @Override
    public void update(int id, int soLuong) {
        // Tìm sản phẩm trong cơ sở dữ liệu
        ChiTietSanPham sanPhamInDatabase = chiTietSanPhamRepo.findById(id).orElse(null);

        if (sanPhamInDatabase != null) {
            // Cập nhật số lượng cho sản phẩm
            sanPhamInDatabase.setSoLuong(soLuong);

            // Đưa sản phẩm cập nhật vào bản đồ cart
            cart.put(id, sanPhamInDatabase);

            // Log thông tin sản phẩm đã cập nhật
            System.out.println("Sản phẩm cập nhật có số  Lượng là: " + sanPhamInDatabase.getSoLuong());
        } else {
            // Nếu không tìm thấy sản phẩm, log lỗi
            System.out.println("Sản phẩm có id: " + id + " không tìm thấy.");
        }
    }


    @Override
    public void clear() {
        this.cart.clear();
    }

    @Override
    public int getSoLuong() {
        return this.cart.values()
                .stream()
                .mapToInt(item -> item.getSoLuong())
                .sum();
    }

    @Override
    public Double getTongTien() {
        return this.cart.values().stream().mapToDouble(item -> item.getDonGia() * item.getSoLuong()).sum();
    }

    @Override
    public ChiTietSanPham getValueIfKeyExists(int key) {
        System.out.println("Checking key: " + key);
        if (cart.containsKey(key)) {
            ChiTietSanPham value = cart.get(key);
            System.out.println("Value found: " + value.getSoLuong());
            return value;
        } else {
            System.out.println("Key not found in cart");
            return null;
        }
    }


}
