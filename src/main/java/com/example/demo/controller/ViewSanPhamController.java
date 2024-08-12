package com.example.demo.controller;

import com.example.demo.dto.response.ChiTietSanPhamResponse;
import com.example.demo.dto.response.KhachHangResponse;
import com.example.demo.dto.response.SanPhamResponse;
import com.example.demo.entity.*;
import com.example.demo.repository.KichThuocRepo;
import com.example.demo.repository.MauSacRepo;
import com.example.demo.service.ChiTietSanPhamService;
import com.example.demo.service.HangService;
import com.example.demo.service.SanPhamService;
import com.example.demo.service.impl.BanHangOnlineServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/trang-chu/products")
public class ViewSanPhamController {
    @Autowired
    private SanPhamService sanPhamService;
    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    private KichThuocRepo kichThuocRepo;
    @Autowired
    private MauSacRepo mauSacRepo;
    @Autowired
    private BanHangOnlineServiceImpl banHangOnlineService;
    @Autowired
    private HangService hangService;
    // đối tượng lưu thông tin khi find
    ChiTietSanPhamResponse chiTietSanPhamResponse = new ChiTietSanPhamResponse();
    // check trang thai size khi chua click vào color
    public Boolean trangThaiColor = false;
    public Boolean trangThaiChiTiet = false;
    Integer idSanPham = 0;

    @GetMapping("/all")
    public String showProducts(@RequestParam(value = "page", defaultValue = "0", required = false) String pageStr,
                               @RequestParam(value = "size", defaultValue = "5", required = false) String sizeStr,
                               Model model) {
        Integer page, size;
        try {
            page = Integer.parseInt(pageStr);
            size = Integer.parseInt(sizeStr);
        } catch (NumberFormatException e) {
            page = 0;
            size = 5;
        }
        Page<SanPhamResponse> pages = sanPhamService.getAllSanPhamShowView(page, size);
        model.addAttribute("hangs", hangService.findAll());
        model.addAttribute("pages", pages);
        return "client/Products";
    }

    @GetMapping("/loc-theo-hang")
    public String locTheoHang(
            @RequestParam(value = "idHang") Integer idHang,
            @RequestParam(value = "page", defaultValue = "0", required = false) String pageStr,
            @RequestParam(value = "size", defaultValue = "5", required = false) String sizeStr,
            Model model) {
        Integer page, size;
        try {
            page = Integer.parseInt(pageStr);
            size = Integer.parseInt(sizeStr);
        } catch (NumberFormatException e) {
            page = 0;
            size = 5;
        }
        model.addAttribute("hangs", hangService.findAll());
        Page<SanPhamResponse> pages = sanPhamService.getAllSanPhamShowViewByIdHang(idHang,page,size);
        model.addAttribute("pages", pages);
        return "client/Products";
    }

    @PostMapping("/hien-thi-san-pham-chi-tiet")
    public String showSanPhamChiTiet(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "ten", required = false) String ten,
            @RequestParam(value = "gia", required = false) String gia, Model model
    ) {
        System.out.println("id sp : " + id);
        System.out.println("ten sp : " + ten);
        System.out.println("gia sp : " + gia);
        this.idSanPham = id;
        // Kiểm tra giá và chuyển đổi
        double giaDouble = 0.0;
        if (gia != null && !gia.isEmpty()) {
            try {
                giaDouble = Double.parseDouble(gia);
            } catch (NumberFormatException e) {
                // Xử lý lỗi nếu giá không hợp lệ
                e.printStackTrace();
                // Thực hiện hành động nếu cần, ví dụ: thông báo lỗi hoặc giá mặc định
            }
        }

        // Định dạng giá
        NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedGia = vndFormat.format(giaDouble);

        // Xử lý đối tượng và dữ liệu
        ChiTietSanPhamResponse chiTietSanPhamResponse = new ChiTietSanPhamResponse();
        SanPham sanPham = sanPhamService.findById(idSanPham);
        if (sanPham == null) {
            // Xử lý khi sản phẩm không tìm thấy
            return "errorPage"; // Ví dụ
        }

        chiTietSanPhamResponse.setTen(ten);
        chiTietSanPhamResponse.setGia(giaDouble);
        chiTietSanPhamResponse.setSanPhamID(sanPham);
        trangThaiColor = false;

        // Lấy danh sách màu sắc và kích thước
        List<MauSac> listMauSac = mauSacRepo.findAll();
        List<KichThuoc> kichThuocList = kichThuocRepo.findAll();

        // Thêm dữ liệu vào model
        model.addAttribute("listMauSac", listMauSac);
        model.addAttribute("listKichThuoc", kichThuocList);
        model.addAttribute("ChiTietResponse", chiTietSanPhamResponse);
        model.addAttribute("trangThaiColor", trangThaiColor);

        return "client/ProductDetail";
    }


    // lọc sản phẩm theo mau sac
    @PostMapping("/loc-bien-the")
    public String findSanPhamByColor(@ModelAttribute("ChiTietResponse") ChiTietSanPhamResponse chiTietSanPhamResponse,
                                     Model model) {
        System.out.println("chiTietSanPhamResponse : " + chiTietSanPhamResponse.toString());
        Integer idSP = chiTietSanPhamResponse.getSanPhamID().getId();
        System.out.println("id sản phẩm : " + idSP);
        Integer idMauSac = chiTietSanPhamResponse.getMauSacID().getId();
        Integer idKichThuoc = chiTietSanPhamResponse.getKichThuocID().getId();
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findProductDetailByIdMauSacAndProduct(idSP, idMauSac, idKichThuoc);
        if (chiTietSanPham != null) {
            System.out.println("Có 1 biến thể");
            trangThaiChiTiet = true;
            // set lai dữ liệu cho chitiet san pham response
            chiTietSanPhamResponse.setGia(Double.valueOf(chiTietSanPham.getDonGia()));
            chiTietSanPhamResponse.setTen(chiTietSanPham.getSanPham().getTen());
            model.addAttribute("BienThe", chiTietSanPham);

        } else {
            trangThaiChiTiet = false;
            SanPham sanPham = sanPhamService.findById(idSP);
            List<ChiTietSanPham> chiTietSanPhamList = chiTietSanPhamService.findAllProductDetailByProductId(idSP);

            double averagePrice = chiTietSanPhamList.stream()
                    .mapToDouble(ChiTietSanPham::getDonGia)     // Lấy giá trị tiền từ từng ChiTietSanPham
                    .average()                                  // Tính trung bình
                    .orElse(0.0);                         // Nếu danh sách rỗng, trả về 0.0

            chiTietSanPhamResponse.setTen(sanPham.getTen());
            chiTietSanPhamResponse.setGia(averagePrice);
            model.addAttribute("tb", "Sản Phẩm đã Hết !");
            System.out.println("Trung bình giá của sản phẩm: " + averagePrice);

        }
        // Lấy danh sách màu sắc và kích thước
        List<MauSac> listMauSac = mauSacRepo.findAll();
        List<KichThuoc> kichThuocList = kichThuocRepo.findAll();
        // Thêm dữ liệu vào model
        model.addAttribute("listMauSac", listMauSac);
        model.addAttribute("listKichThuoc", kichThuocList);
        model.addAttribute("ChiTietResponse", chiTietSanPhamResponse);
        model.addAttribute("trangThaiColor", trangThaiColor);
        return "client/ProductDetail";
    }


    @PostMapping("/them-vao-gio-hang")
    public String showCart(@RequestParam("id") Integer idProductDetail, @RequestParam("soLuong") String soLuongMua, Model model) {
        System.out.println("id product detail : " + idProductDetail);
        System.out.println("soLuongMua : " + soLuongMua);

        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findById(idProductDetail);
        // 1. Kiểm tra nếu số lượng mua bị trống
        if (soLuongMua.isBlank() || soLuongMua.isEmpty()) {
            // hiển thị lại
            if (chiTietSanPham != null) {
                System.out.println("Có 1 biến thể");
                trangThaiChiTiet = true;
                model.addAttribute("BienThe", chiTietSanPham);
            } else {
                System.out.println("Sản Phẩm không có biến thể nào ");
            }
            model.addAttribute("soLuong", soLuongMua);
            model.addAttribute("error", "Số lượng mua không được để trống");
            // Lấy danh sách màu sắc và kích thước
            List<MauSac> listMauSac = mauSacRepo.findAll();
            List<KichThuoc> kichThuocList = kichThuocRepo.findAll();
            chiTietSanPhamResponse.setTen(chiTietSanPham.getSanPham().getTen());
            Double gia = Double.valueOf(chiTietSanPham.getDonGia());
            chiTietSanPhamResponse.setGia(gia);
            SanPham sanPham = sanPhamService.findById(idSanPham);
            chiTietSanPhamResponse.setSanPhamID(sanPham);
            // Thêm dữ liệu vào model
            model.addAttribute("listMauSac", listMauSac);
            model.addAttribute("listKichThuoc", kichThuocList);
            model.addAttribute("ChiTietResponse", chiTietSanPhamResponse);
            model.addAttribute("trangThaiColor", trangThaiColor);
            model.addAttribute("message", "Vui Lòng Nhập Số Lượng Sản phẩm");
            return "client/ProductDetail";
            // Hoặc trả về trang lỗi
        }

        // 2. Kiểm tra nếu số lượng mua không phải là chuỗi số
        if (!soLuongMua.matches("\\d+")) {
            if (chiTietSanPham != null) {
                System.out.println("Có 1 biến thể");
                trangThaiChiTiet = true;
                model.addAttribute("BienThe", chiTietSanPham);
            } else {
                System.out.println("Sản Phẩm không có biến thể nào ");
            }
            model.addAttribute("soLuong", soLuongMua);
            model.addAttribute("error", "Số lượng mua không được để trống");
            // Lấy danh sách màu sắc và kích thước
            List<MauSac> listMauSac = mauSacRepo.findAll();
            List<KichThuoc> kichThuocList = kichThuocRepo.findAll();
            chiTietSanPhamResponse.setTen(chiTietSanPham.getSanPham().getTen());
            Double gia = Double.valueOf(chiTietSanPham.getDonGia());
            chiTietSanPhamResponse.setGia(gia);
            SanPham sanPham = sanPhamService.findById(idSanPham);
            chiTietSanPhamResponse.setSanPhamID(sanPham);
            // Thêm dữ liệu vào model
            model.addAttribute("listMauSac", listMauSac);
            model.addAttribute("listKichThuoc", kichThuocList);
            model.addAttribute("ChiTietResponse", chiTietSanPhamResponse);
            model.addAttribute("trangThaiColor", trangThaiColor);
            model.addAttribute("soLuong", soLuongMua);
            model.addAttribute("error", "Số lượng mua phải là chuỗi số và không được chứa ký tự đặc biệt");
            return "client/ProductDetail"; // Hoặc trả về trang lỗi
        }
        // 3. Kiểm tra số lượng mua phải là số dương và ít nhất là 1
        int soLuong = Integer.parseInt(soLuongMua);
        Integer soLuongKho = chiTietSanPham.getSoLuong();
        if (soLuong < 1 || soLuong == 0 || soLuong > soLuongKho || soLuong == soLuongKho) {
            if (chiTietSanPham != null) {
                System.out.println("Có 1 biến thể");
                trangThaiChiTiet = true;
                model.addAttribute("BienThe", chiTietSanPham);
            } else {
                System.out.println("Sản Phẩm không có biến thể nào ");
            }
            model.addAttribute("soLuong", soLuongMua);
            model.addAttribute("error", "Số lượng mua không được để trống");
            // Lấy danh sách màu sắc và kích thước
            List<MauSac> listMauSac = mauSacRepo.findAll();
            List<KichThuoc> kichThuocList = kichThuocRepo.findAll();
            chiTietSanPhamResponse.setTen(chiTietSanPham.getSanPham().getTen());
            Double gia = Double.valueOf(chiTietSanPham.getDonGia());
            chiTietSanPhamResponse.setGia(gia);
            SanPham sanPham = sanPhamService.findById(idSanPham);
            chiTietSanPhamResponse.setSanPhamID(sanPham);

            // Thêm dữ liệu vào model
            model.addAttribute("listMauSac", listMauSac);
            model.addAttribute("listKichThuoc", kichThuocList);
            model.addAttribute("ChiTietResponse", chiTietSanPhamResponse);
            model.addAttribute("trangThaiColor", trangThaiColor);
            model.addAttribute("soLuong", soLuongMua);
            model.addAttribute("error", "Lơn Hơn 1 và nhỏ Hơn số lượng trong kho và không được bằng");
            return "client/ProductDetail"; // Hoặc trả về trang lỗi
        }

        Boolean tt = banHangOnlineService.addToCart(idProductDetail, soLuong);
        if (!tt) {
            trangThaiChiTiet = true;
            model.addAttribute("BienThe", chiTietSanPham);
            // Lấy danh sách màu sắc và kích thước
            List<MauSac> listMauSac = mauSacRepo.findAll();
            List<KichThuoc> kichThuocList = kichThuocRepo.findAll();
            chiTietSanPhamResponse.setTen(chiTietSanPham.getSanPham().getTen());
            Double gia = Double.valueOf(chiTietSanPham.getDonGia());
            chiTietSanPhamResponse.setGia(gia);
            // Thêm dữ liệu vào model
            model.addAttribute("listMauSac", listMauSac);
            model.addAttribute("listKichThuoc", kichThuocList);
            model.addAttribute("ChiTietResponse", chiTietSanPhamResponse);
            model.addAttribute("trangThaiColor", trangThaiColor);
            model.addAttribute("soLuong", soLuongMua);
            model.addAttribute("error", "Số lượng không hợp lệ Vì số Lượng Bạn Thêm Đã có trong giỏ hàng > số Lượng trong kho!");
            return "ViewChiTietSanPham"; // Hoặc trả về trang lỗi
        }
        model.addAttribute("QuantityProductInCart", banHangOnlineService.getSoLuong());
        model.addAttribute("totalMoney", banHangOnlineService.getTongTien());
        model.addAttribute("listCart", banHangOnlineService.showCart());
        model.addAttribute("KhachHang", new KhachHangResponse());
        return "client/cart";
    }
}
