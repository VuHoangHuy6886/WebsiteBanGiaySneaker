package com.example.demo.controller;

import com.example.demo.entity.HoaDon;
import com.example.demo.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/Hoa-Don")
public class HoaDonController {
    @Autowired
    private HoaDonService hoaDonService;

    @GetMapping("/all")
    public String showHoaDon(
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
        Page<HoaDon> pages = hoaDonService.findAll(page, size);
        model.addAttribute("pages", pages);
        return "admin/hoadon/index";
    }

}
