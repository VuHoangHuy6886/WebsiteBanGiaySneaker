package com.example.demo.controller;

import com.example.demo.service.HoaDonChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/Hoa-Don-Chi-Tiet")
public class HoaDonChiTietController {
    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;

    @GetMapping("/all")
    public String showHoaDonChiTiet(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("pages", hoaDonChiTietService.findAllByIdHoaDon(id));
        return "/admin/hoadonchitiet/index";
    }
}
