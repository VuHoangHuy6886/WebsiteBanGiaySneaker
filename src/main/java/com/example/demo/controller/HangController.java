package com.example.demo.controller;

import com.example.demo.dto.request.create_request.CreateRequestHang;
import com.example.demo.dto.request.update_request.UpdateRequestHang;
import com.example.demo.entity.Hang;
import com.example.demo.repository.HangRepo;
import com.example.demo.service.HangService;
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

@RequestMapping("/admin/Hang")
@Controller
public class HangController {
    @Autowired
    private HangService hangService;

    @GetMapping("/all")
    public String showData(
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
        Page<Hang> pages = hangService.findAll(page, size);
        model.addAttribute("pages", pages);
        return "admin/hang/index";
    }

    @GetMapping("/formAdd")
    public String formAdd(Model model) {
        model.addAttribute("Hang", new Hang());
        return "admin/hang/form-add";
    }

    @GetMapping("/formUpdate")
    public String formUpdate(@ModelAttribute(name = "id") Integer id, Model model) {
        Hang hangFinById = hangService.findById(id);
        model.addAttribute("Hang", hangFinById);
        return "admin/hang/form-update";
    }

    @PostMapping("/save")
    public String add(@Valid @ModelAttribute("Hang") CreateRequestHang requestHang,
                                                     BindingResult bindingResult,
                                                     Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("Lỗi: Có lỗi xảy ra trong dữ liệu");
            model.addAttribute("Hang", requestHang);
            return "admin/hang/form-add"; // Đảm bảo rằng view này đúng
        } else {
            String result = hangService.save(requestHang);
            System.out.println("Kết quả: " + result);
            return "redirect:/admin/Hang/all";
        }
    }


    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("Hang") UpdateRequestHang requestHang, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("Hang", requestHang);
            return "admin/hang/form-update";
        }
        String result = hangService.update(requestHang);
        System.out.println("Kết quả : " + result);
        return "redirect:/admin/Hang/all";
    }

    @GetMapping("/delete")
    public String delete(@ModelAttribute(name = "id") Integer id) {
        hangService.deleteById(id);
        return "redirect:/admin/Hang/all";
    }

}
