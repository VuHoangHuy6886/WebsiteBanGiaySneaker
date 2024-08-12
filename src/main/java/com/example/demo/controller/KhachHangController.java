package com.example.demo.controller;

import com.example.demo.dto.request.create_request.CreateRequestKhachHang;
import com.example.demo.dto.request.update_request.UpdateRequestKhachHang;
import com.example.demo.entity.KhachHang;
import com.example.demo.service.KhachHangService;
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

@Controller
@RequestMapping("/admin/Khach-Hang")
public class KhachHangController {

    @Autowired
    private KhachHangService khachHangService;

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
        Page<KhachHang> pages = khachHangService.findAll(page, size);
        model.addAttribute("pages", pages);
        System.out.println("abc" + pages.getContent().size());
        return "admin/khachhang/index";
    }

    @GetMapping("/formAdd")
    public String formAdd(Model model) {
        model.addAttribute("KhachHang", new KhachHang());
        return "admin/khachhang/form-add";
    }

    @GetMapping("/formUpdate")
    public String formUpdate(@ModelAttribute(name = "id") Integer id, Model model) {
        KhachHang khachHangFinById = khachHangService.findById(id);
        model.addAttribute("KhachHang", khachHangFinById);
        return "admin/khachhang/form-update";
    }

    @PostMapping("/save")
    public String add(@Valid @ModelAttribute("KhachHang") CreateRequestKhachHang requestKhachHang,
                      BindingResult bindingResult,
                      Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("Lỗi: Có lỗi xảy ra trong dữ liệu");
            model.addAttribute("KhachHang", requestKhachHang);
            return "admin/khachhang/form-add"; // Đảm bảo rằng view này đúng
        } else {
            String result = khachHangService.save(requestKhachHang);
            System.out.println("Kết quả: " + result);
            return "redirect:/admin/Khach-Hang/all";
        }
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("KhachHang") UpdateRequestKhachHang requestKhachHang,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("KhachHang", requestKhachHang);
            return "admin/khachhang/form-update";
        }
        String result = khachHangService.update(requestKhachHang);
//        System.out.println("Kết quả : " + result);
        return "redirect:/admin/Khach-Hang/all";
    }

    @GetMapping("/delete")
    public String delete(@ModelAttribute(name = "id") Integer id) {
        khachHangService.deleteById(id);
        return "redirect:/admin/Khach-Hang/all";
    }


//    end
}
