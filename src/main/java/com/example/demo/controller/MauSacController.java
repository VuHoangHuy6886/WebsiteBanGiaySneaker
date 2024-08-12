package com.example.demo.controller;


import com.example.demo.dto.request.create_request.CreateRequestHang;
import com.example.demo.dto.request.create_request.CreateRequestMauSac;
import com.example.demo.dto.request.update_request.UpdateRequestHang;
import com.example.demo.dto.request.update_request.UpdateRequestMauSac;
import com.example.demo.entity.Hang;
import com.example.demo.entity.MauSac;
import com.example.demo.service.MauSacService;
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
@RequestMapping("/admin/Mau-Sac")
public class MauSacController {

    @Autowired
    private MauSacService mauSacService;

    @GetMapping("/all")
    public String showData(@RequestParam(value = "page",defaultValue = "0",required = false)String pageStr,
                           @RequestParam(value = "size",defaultValue = "5",required = false)String sizeStr,
                           Model model){
        Integer page,size;
        try{
            page = Integer.parseInt(pageStr);
            size = Integer.parseInt(sizeStr);
        }catch (NumberFormatException e){
            page = 0;
            size = 5;
        }
        Page<MauSac> pages = mauSacService.findAll(page,size);
        model.addAttribute("pages",pages);
        System.out.println( "abc"+pages.getContent().size());
        return "admin/mausac/index";
    }

    @GetMapping("/viewAdd")
    private String viewAdd(Model model){
        model.addAttribute("MauSac", new MauSac());
        return "admin/mausac/form-add";
    }

    @PostMapping("/save")
    public String add(@Valid @ModelAttribute("MauSac")CreateRequestMauSac requestMauSac,
                      BindingResult bindingResult,
                      Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("Lỗi: Có lỗi xảy ra trong dữ liệu");
            model.addAttribute("MauSac", requestMauSac);
            return "admin/hang/form-add"; // Đảm bảo rằng view này đúng
        } else {
            String result = mauSacService.save(requestMauSac);
            System.out.println("Kết quả: " + result);
            return "redirect:/admin/Mau-Sac/all";
        }
    }




    @GetMapping("/formUpdate")
    public String formUpdate(@ModelAttribute(name = "id") Integer id, Model model) {
        MauSac hangFinById = mauSacService.findById(id);
        model.addAttribute("MauSac", hangFinById);
        return "admin/mausac/form-update";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("MauSac") UpdateRequestMauSac requestMauSac, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("MauSac", requestMauSac);
            return "admin/mausac/form-update";
        }
        String result = mauSacService.update(requestMauSac);
        System.out.println("Kết quả : " + result);
        return "redirect:/admin/Mau-Sac/all";
    }

    @GetMapping("/delete")
    public String delete(@ModelAttribute(name = "id") Integer id) {
        mauSacService.deleteById(id);
        return "redirect:/admin/Mau-Sac/all";
    }



}
