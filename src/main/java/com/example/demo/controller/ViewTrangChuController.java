package com.example.demo.controller;

import com.example.demo.dto.response.SanPhamResponse;
import com.example.demo.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewTrangChuController {
    @Autowired
    private SanPhamService sanPhamService;

    @GetMapping("/trang-chu")
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
        model.addAttribute("pages", pages);
        return "/client/index";
    }

    public String giaoDienKhachHang() {
        return "/client/index";
    }
}
