package com.example.demo.controller;

import com.example.demo.constant.PhuongThucThanhToan;
import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.KhachHang;
import com.example.demo.service.BanHangTaiQuayService;
import com.example.demo.service.ChiTietSanPhamService;
import com.example.demo.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/Ban-Hang-Tai-Quay")
public class BanHangTaiQuayController {
    @Autowired
    private BanHangTaiQuayService banHangTaiQuayService;
    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    private KhachHangService khachHangService;
    private Integer IdHoaDonClick = null;

    // ham find hoa don có trạng thái là tạo hóa đon
    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("HoaDonNew", banHangTaiQuayService.layHoaDonMoiTao());
        model.addAttribute("HoaDonClick", IdHoaDonClick);
        return "admin/banhangtaiquay/index";
    }

    // tao hoa don moi
    @GetMapping("/Tao-Hoa-Don")
    public String TaoHoaDon(Model model) {
        banHangTaiQuayService.themMoiHoaDon(new HoaDon());
        model.addAttribute("HoaDonNew", banHangTaiQuayService.layHoaDonMoiTao());
        model.addAttribute("HoaDonClick", IdHoaDonClick);
        return "admin/banhangtaiquay/index";
    }

    // hien thi gio hang cua hoa don do
    @PostMapping("/Hien-Thi-Gio-Hang")
    public String HienThiGioHang(@ModelAttribute("idHD") Integer idHoaDon, Model model) {
        System.out.println(" id hd : " + idHoaDon);
        this.IdHoaDonClick = idHoaDon;

        // gọi hoa don Maps
        model.addAttribute("GioHang", banHangTaiQuayService.layListChiTietSanPhamByIdHoaDon(idHoaDon));
        model.addAttribute("HoaDonNew", banHangTaiQuayService.layHoaDonMoiTao());
        model.addAttribute("HoaDonClick", IdHoaDonClick);

        // phần hiển thị tổng số lượng sản phẩm and tổng tiền
        model.addAttribute("totalMoney", banHangTaiQuayService.totalPrice(IdHoaDonClick));
        model.addAttribute("totalQuantity", banHangTaiQuayService.totalQuantity(IdHoaDonClick));
        // phần hiển thị tổng số lượng sản phẩm and tổng tiền
        model.addAttribute("totalMoney", banHangTaiQuayService.totalPrice(IdHoaDonClick));
        model.addAttribute("totalQuantity", banHangTaiQuayService.totalQuantity(IdHoaDonClick));
        // add list khách hàng
        model.addAttribute("listCustomer", khachHangService.findAll());
        return "admin/banhangtaiquay/index";
    }

    // Hiển thị view sản phẩm
    @GetMapping("/Them-San-Pham")
    public String ThemSanPham(Model model) {
        System.out.println("Trang Thêm Sản Phẩm");
        model.addAttribute("ListProducts", chiTietSanPhamService.findAll());
        model.addAttribute("HoaDonClick", IdHoaDonClick);
        // phần hiển thị tổng số lượng sản phẩm and tổng tiền
        model.addAttribute("totalMoney", banHangTaiQuayService.totalPrice(IdHoaDonClick));
        model.addAttribute("totalQuantity", banHangTaiQuayService.totalQuantity(IdHoaDonClick));
        // phần hiển thị tổng số lượng sản phẩm and tổng tiền
        model.addAttribute("totalMoney", banHangTaiQuayService.totalPrice(IdHoaDonClick));
        model.addAttribute("totalQuantity", banHangTaiQuayService.totalQuantity(IdHoaDonClick));
        // add list khách hàng
        model.addAttribute("listCustomer", khachHangService.findAll());
        return "admin/banhangtaiquay/ListProducts";
    }

    @PostMapping("/Them-Vao-Gio-Hang")
    public String ThemVaoGioHang(@ModelAttribute("idSP") Integer idSp, @ModelAttribute("soluong") String soLuongStr, Model model) {

        System.out.println("id sản phẩm là : " + idSp);
        System.out.println("số Lượng là : " + soLuongStr);

        // Kiểm tra tính hợp lệ của soLuongStr
        if (!soLuongStr.matches("\\d+")) {
            model.addAttribute("ListProducts", chiTietSanPhamService.findAll());
            model.addAttribute("HoaDonClick", IdHoaDonClick);
            model.addAttribute("errorMessage", "Số lượng phải là ký tự số.");
            return "admin/banhangtaiquay/ListProducts";
        }

        // Chuyển đổi soLuongStr thành Integer
        Integer soLuong = Integer.parseInt(soLuongStr);
        // find so luong trong database
        Integer quantityProductDetailDB = chiTietSanPhamService.findById(idSp).getSoLuong();
        // check lai quantity in cart ròi mới cho thêm
        List<ChiTietSanPham> spInCart = banHangTaiQuayService.layListChiTietSanPhamByIdHoaDon(IdHoaDonClick);
        if (spInCart != null) {
            for (int i = 0; i < spInCart.size(); i++) {
                // tao bien đựng so luong sản phẩm in cart
                if (spInCart.get(i).getId().equals(idSp)) {
                    // quantity in cart
                    Integer slInCart = spInCart.get(i).getSoLuong() + soLuong;
                    // quantity in database
                    Integer slInDb = chiTietSanPhamService.findById(idSp).getSoLuong();
                    if (slInCart > slInDb) {
                        model.addAttribute("ListProducts", chiTietSanPhamService.findAll());
                        model.addAttribute("HoaDonClick", IdHoaDonClick);
                        model.addAttribute("errorMessage", "Số lượng Nhập + với lượng trong giỏ hàng đã có vượt Quá Số lượng Trong Kho " + slInDb);
                        return "admin/banhangtaiquay/ListProducts";
                    }

                }
            }
        }

        if (soLuong < 1 || soLuong >= quantityProductDetailDB) {
            model.addAttribute("ListProducts", chiTietSanPhamService.findAll());
            model.addAttribute("HoaDonClick", IdHoaDonClick);
            model.addAttribute("errorMessage", "Số lượng phải > 0 và Số Lượng < " + quantityProductDetailDB);
            return "admin/banhangtaiquay/ListProducts";
        }
        // Gọi đến hàm add
        banHangTaiQuayService.themSanPhamChiTietVaoHoaDon(idSp, soLuong);

        // Hiển thị lại đối tượng hóa đơn muốn add product
        model.addAttribute("GioHang", banHangTaiQuayService.layListChiTietSanPhamByIdHoaDon(IdHoaDonClick));
        model.addAttribute("HoaDonNew", banHangTaiQuayService.layHoaDonMoiTao());
        model.addAttribute("HoaDonClick", IdHoaDonClick);
        // phần hiển thị tổng số lượng sản phẩm and tổng tiền
        model.addAttribute("totalMoney", banHangTaiQuayService.totalPrice(IdHoaDonClick));
        model.addAttribute("totalQuantity", banHangTaiQuayService.totalQuantity(IdHoaDonClick));
        // add list khách hàng
        model.addAttribute("listCustomer", khachHangService.findAll());

        return "admin/banhangtaiquay/index";
    }

    @GetMapping("/Tang")
    public String tang(@RequestParam(value = "idSP", required = true) Integer idSP, Model model) {
        System.out.println("id Sản phẩm là: " + idSP);
        // check neu id san pham moi gui co sl > sl in database thi show error
        List<ChiTietSanPham> cart = banHangTaiQuayService.layListChiTietSanPhamByIdHoaDon(IdHoaDonClick);
        Integer slUpdateKhiTrung = 1;
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getId().equals(idSP)) {
                // lấy số lượng sản phẩm trong database ra check neu còn  nhiều hơn thì mới cho update
                Integer slSpByIdDatabase = chiTietSanPhamService.findById(idSP).getSoLuong();
                // lấy số lượng ở giỏ hàng ra để check
                Integer slSpInCart = cart.get(i).getSoLuong() + slUpdateKhiTrung;
                if (slSpInCart > slSpByIdDatabase) {
                    // Hiển thị lại quay lại trang add show error
                    model.addAttribute("GioHang", banHangTaiQuayService.layListChiTietSanPhamByIdHoaDon(IdHoaDonClick));
                    model.addAttribute("HoaDonNew", banHangTaiQuayService.layHoaDonMoiTao());
                    model.addAttribute("HoaDonClick", IdHoaDonClick);
                    model.addAttribute("errormesage", "số Lượng Vượt Quá Số Lượng Trong Kho !");
                } else {
                    ChiTietSanPham chiTietSanPhamAdd = ChiTietSanPham.builder()
                            .id(cart.get(i).getId()).ma(cart.get(i).getMa()).trangThai(cart.get(i).getTrangThai())
                            .donGia(cart.get(i).getDonGia()).soLuong(slSpInCart).sanPham(cart.get(i).getSanPham())
                            .mauSac(cart.get(i).getMauSac()).kichThuoc(cart.get(i).getKichThuoc())
                            .chatLieu(cart.get(i).getChatLieu()).loaiDe(cart.get(i).getLoaiDe())
                            .build();
                    cart.set(i, chiTietSanPhamAdd);
                }

            }

        }
        // Hiển thị lại đối tượng hóa đơn muốn add product
        model.addAttribute("GioHang", banHangTaiQuayService.layListChiTietSanPhamByIdHoaDon(IdHoaDonClick));
        model.addAttribute("HoaDonNew", banHangTaiQuayService.layHoaDonMoiTao());
        model.addAttribute("HoaDonClick", IdHoaDonClick);
        // phần hiển thị tổng số lượng sản phẩm and tổng tiền
        model.addAttribute("totalMoney", banHangTaiQuayService.totalPrice(IdHoaDonClick));
        model.addAttribute("totalQuantity", banHangTaiQuayService.totalQuantity(IdHoaDonClick));
        // add list khách hàng
        model.addAttribute("listCustomer", khachHangService.findAll());
        return "admin/banhangtaiquay/index";
    }

    @GetMapping("/Giam")
    public String giam(@RequestParam(value = "idSP", required = true) Integer idSP, Model model) {
        System.out.println("id Sản phẩm là: " + idSP);
        // check neu id san pham moi gui co sl > sl in database thi show error
        List<ChiTietSanPham> cart = banHangTaiQuayService.layListChiTietSanPhamByIdHoaDon(IdHoaDonClick);
        Integer slUpdateKhiTrung = 1;
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getId().equals(idSP)) {
                // lấy số lượng sản phẩm trong database ra check neu còn  nhiều hơn thì mới cho update
                // Integer slSpByIdDatabase = chiTietSanPhamService.findById(idSP).getSoLuong();
                // lấy số lượng ở giỏ hàng ra để check
                Integer slSpInCart = cart.get(i).getSoLuong() - slUpdateKhiTrung;
                if (slSpInCart < 1) {
                    // Hiển thị lại quay lại trang add show error
                    model.addAttribute("GioHang", banHangTaiQuayService.layListChiTietSanPhamByIdHoaDon(IdHoaDonClick));
                    model.addAttribute("HoaDonNew", banHangTaiQuayService.layHoaDonMoiTao());
                    model.addAttribute("HoaDonClick", IdHoaDonClick);
                    model.addAttribute("errormesage", "số Lượng Phải Lớn Hơn 1 !");
                } else {
                    ChiTietSanPham chiTietSanPhamAdd = ChiTietSanPham.builder()
                            .id(cart.get(i).getId()).ma(cart.get(i).getMa()).trangThai(cart.get(i).getTrangThai())
                            .donGia(cart.get(i).getDonGia()).soLuong(slSpInCart).sanPham(cart.get(i).getSanPham())
                            .mauSac(cart.get(i).getMauSac()).kichThuoc(cart.get(i).getKichThuoc())
                            .chatLieu(cart.get(i).getChatLieu()).loaiDe(cart.get(i).getLoaiDe())
                            .build();
                    cart.set(i, chiTietSanPhamAdd);
                }

            }
        }
        // Hiển thị lại đối tượng hóa đơn muốn add product
        model.addAttribute("GioHang", banHangTaiQuayService.layListChiTietSanPhamByIdHoaDon(IdHoaDonClick));
        model.addAttribute("HoaDonNew", banHangTaiQuayService.layHoaDonMoiTao());
        model.addAttribute("HoaDonClick", IdHoaDonClick);
        // phần hiển thị tổng số lượng sản phẩm and tổng tiền
        model.addAttribute("totalMoney", banHangTaiQuayService.totalPrice(IdHoaDonClick));
        model.addAttribute("totalQuantity", banHangTaiQuayService.totalQuantity(IdHoaDonClick));
        // add list khách hàng
        model.addAttribute("listCustomer", khachHangService.findAll());
        return "admin/banhangtaiquay/index";
    }

    @GetMapping("/Xoa")
    public String xoa(@RequestParam("id") Integer id, Model model) {
        System.out.println("id nead remove : " + id);
        banHangTaiQuayService.xoa(id);
        model.addAttribute("GioHang", banHangTaiQuayService.layListChiTietSanPhamByIdHoaDon(IdHoaDonClick));
        model.addAttribute("HoaDonNew", banHangTaiQuayService.layHoaDonMoiTao());
        model.addAttribute("HoaDonClick", IdHoaDonClick);
        // phần hiển thị tổng số lượng sản phẩm and tổng tiền
        model.addAttribute("totalMoney", banHangTaiQuayService.totalPrice(IdHoaDonClick));
        model.addAttribute("totalQuantity", banHangTaiQuayService.totalQuantity(IdHoaDonClick));
        // add list khách hàng
        model.addAttribute("listCustomer", khachHangService.findAll());
        return "admin/banhangtaiquay/index";
    }

    @GetMapping("/XoaAll")
    public String xoaAll(Model model) {
        banHangTaiQuayService.xoaAll(IdHoaDonClick);
        model.addAttribute("GioHang", banHangTaiQuayService.layListChiTietSanPhamByIdHoaDon(IdHoaDonClick));
        model.addAttribute("HoaDonNew", banHangTaiQuayService.layHoaDonMoiTao());
        model.addAttribute("HoaDonClick", IdHoaDonClick);
        // phần hiển thị tổng số lượng sản phẩm and tổng tiền
        model.addAttribute("totalMoney", banHangTaiQuayService.totalPrice(IdHoaDonClick));
        model.addAttribute("totalQuantity", banHangTaiQuayService.totalQuantity(IdHoaDonClick));
        // add list khách hàng
        model.addAttribute("listCustomer", khachHangService.findAll());
        return "admin/banhangtaiquay/index";
    }

    @PostMapping("/Thanh-Toan")
    public String thanhToan(
            @ModelAttribute("customerMethod") String kh,
            @ModelAttribute("paymentMethod") String pttt,
            @ModelAttribute("selectedCustomer") String idKHStr
            , Model model) {

        System.out.println("kh : " + kh);
        System.out.println("ptt : " + pttt);
        System.out.println("idKH : " + idKHStr);
        if(idKHStr.isEmpty()){
            // gọi hoa don Maps
            model.addAttribute("GioHang", banHangTaiQuayService.layListChiTietSanPhamByIdHoaDon(IdHoaDonClick));
            model.addAttribute("HoaDonNew", banHangTaiQuayService.layHoaDonMoiTao());
            model.addAttribute("HoaDonClick", IdHoaDonClick);

            // phần hiển thị tổng số lượng sản phẩm and tổng tiền
            model.addAttribute("totalMoney", banHangTaiQuayService.totalPrice(IdHoaDonClick));
            model.addAttribute("totalQuantity", banHangTaiQuayService.totalQuantity(IdHoaDonClick));
            // phần hiển thị tổng số lượng sản phẩm and tổng tiền
            model.addAttribute("totalMoney", banHangTaiQuayService.totalPrice(IdHoaDonClick));
            model.addAttribute("totalQuantity", banHangTaiQuayService.totalQuantity(IdHoaDonClick));
            // add list khách hàng
            model.addAttribute("listCustomer", khachHangService.findAll());
            model.addAttribute("errorMessage", "Vui lòng Chọn Lại Khách Hàng");
            return "admin/banhangtaiquay/index";
        }
        String phuongThucThanhToan;
        if (pttt.equals("TM")) {
            phuongThucThanhToan = PhuongThucThanhToan.TIEN_MAT.value;
        } else {
            phuongThucThanhToan = PhuongThucThanhToan.CHUYEN_KHOAN.value;
        }
        KhachHang khachHang;
        Integer idKH = 0;
        if (!idKHStr.isEmpty()) {
            idKH = Integer.valueOf(idKHStr);
        }
        if (kh.equals("KH")) {
            khachHang = khachHangService.findById(idKH);
        } else {
            khachHang = new KhachHang();
        }
        List<ChiTietSanPham> chiTietSanPhamList = banHangTaiQuayService.layListChiTietSanPhamByIdHoaDon(IdHoaDonClick);
        if (chiTietSanPhamList == null) {
            // gọi hoa don Maps
            model.addAttribute("GioHang", banHangTaiQuayService.layListChiTietSanPhamByIdHoaDon(IdHoaDonClick));
            model.addAttribute("HoaDonNew", banHangTaiQuayService.layHoaDonMoiTao());
            model.addAttribute("HoaDonClick", IdHoaDonClick);

            // phần hiển thị tổng số lượng sản phẩm and tổng tiền
            model.addAttribute("totalMoney", banHangTaiQuayService.totalPrice(IdHoaDonClick));
            model.addAttribute("totalQuantity", banHangTaiQuayService.totalQuantity(IdHoaDonClick));
            // phần hiển thị tổng số lượng sản phẩm and tổng tiền
            model.addAttribute("totalMoney", banHangTaiQuayService.totalPrice(IdHoaDonClick));
            model.addAttribute("totalQuantity", banHangTaiQuayService.totalQuantity(IdHoaDonClick));
            // add list khách hàng
            model.addAttribute("listCustomer", khachHangService.findAll());
            model.addAttribute("errorMessage", " Giỏ Hàng trống !");
            return "admin/banhangtaiquay/index";
        }

        Boolean kq = banHangTaiQuayService.thanhToan(phuongThucThanhToan, khachHang);
        if (kq) {
            this.IdHoaDonClick = null;
            banHangTaiQuayService.clentId();
            System.out.println("Thanh Toán Thành Công !");
        } else {
            // gọi hoa don Maps
            model.addAttribute("GioHang", banHangTaiQuayService.layListChiTietSanPhamByIdHoaDon(IdHoaDonClick));
            model.addAttribute("HoaDonNew", banHangTaiQuayService.layHoaDonMoiTao());
            model.addAttribute("HoaDonClick", IdHoaDonClick);

            // phần hiển thị tổng số lượng sản phẩm and tổng tiền
            model.addAttribute("totalMoney", banHangTaiQuayService.totalPrice(IdHoaDonClick));
            model.addAttribute("totalQuantity", banHangTaiQuayService.totalQuantity(IdHoaDonClick));
            // phần hiển thị tổng số lượng sản phẩm and tổng tiền
            model.addAttribute("totalMoney", banHangTaiQuayService.totalPrice(IdHoaDonClick));
            model.addAttribute("totalQuantity", banHangTaiQuayService.totalQuantity(IdHoaDonClick));
            // add list khách hàng
            model.addAttribute("listCustomer", khachHangService.findAll());
            model.addAttribute("errorMessage", "Thanh Toán Thất Bại số Lượng Không hơp lệ Hoặc Giỏ Hàng trống !");
            return "admin/banhangtaiquay/index";
        }
        model.addAttribute("GioHang", banHangTaiQuayService.layListChiTietSanPhamByIdHoaDon(IdHoaDonClick));
        model.addAttribute("HoaDonNew", banHangTaiQuayService.layHoaDonMoiTao());
        return "admin/banhangtaiquay/index";
    }
}
