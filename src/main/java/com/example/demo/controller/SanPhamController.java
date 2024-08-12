package com.example.demo.controller;

import com.example.demo.dto.request.create_request.CreateRequestKichThuoc;
import com.example.demo.dto.request.create_request.CreateRequestSanPham;
import com.example.demo.dto.request.update_request.UpdateRequestKichThuoc;
import com.example.demo.dto.request.update_request.UpdateRequestSanPham;
import com.example.demo.entity.Hang;
import com.example.demo.entity.KhachHang;
import com.example.demo.entity.KichThuoc;
import com.example.demo.entity.SanPham;
import com.example.demo.service.HangService;
import com.example.demo.service.KhachHangService;
import com.example.demo.service.SanPhamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/San-Pham")
public class SanPhamController {

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private HangService hangService;

    @GetMapping("/all")
    public String showData(@RequestParam(value = "page", defaultValue = "0", required = false) String pageStr,
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
        Page<SanPham> pages = sanPhamService.findAll(page, size);
        System.out.println(pages.getContent().size());
        model.addAttribute("pages", pages);
        return "admin/sanpham/index";
    }

    @GetMapping("/formAdd")
    public String formAdd(Model model) {
        model.addAttribute("SanPham", new CreateRequestSanPham());
        List<Hang> hangs = hangService.getAll();
        model.addAttribute("hangs", hangs);
        return "admin/sanpham/form-add";
    }


    @GetMapping("/formUpdate")
    public String formUpdate(@ModelAttribute(name = "id") Integer id, Model model) {
        SanPham sanPhamFinById = sanPhamService.findById(id);
        UpdateRequestSanPham updateRequestSanPham = UpdateRequestSanPham.builder()
                .ten(sanPhamFinById.getTen())
                .ma(sanPhamFinById.getMa())
                .id(sanPhamFinById.getId())
                .trangThai(sanPhamFinById.getTrangThai())
                .idHang(String.valueOf(sanPhamFinById.getHang().getId()))
                .build();
        model.addAttribute("SanPham", updateRequestSanPham);

        List<Hang> hangs = hangService.getAll();
        model.addAttribute("hangs", hangs);
        return "admin/sanpham/form-update";
    }


    @PostMapping("/save")
    public String add(@Valid @ModelAttribute("SanPham") CreateRequestSanPham requestSanPham,
                      BindingResult bindingResult,
                      Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("Lỗi: Có lỗi xảy ra trong dữ liệu");
            model.addAttribute("SanPham", requestSanPham);
            return "admin/San-Pham/form-add"; // Đảm bảo rằng view này đúng
        } else {
            String result = sanPhamService.save(requestSanPham);
            return "redirect:/admin/San-Pham/all";
        }
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("SanPham") UpdateRequestSanPham requestSanPham,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("SanPham", requestSanPham);
            return "admin/sanpham/form-update";
        }
        System.out.println("id of hang : " + requestSanPham.getIdHang());
        System.out.println("id Sản phẩm  :" + requestSanPham.getId());
        String result = sanPhamService.update(requestSanPham);
//        System.out.println("Kết quả : " + result);
        return "redirect:/admin/San-Pham/all";
    }


}
