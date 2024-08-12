package com.example.demo.controller;


import com.example.demo.dto.request.create_request.CreateRequestLoaiDe;
import com.example.demo.dto.request.update_request.UpdateRequestLoaiDe;
import com.example.demo.entity.LoaiDe;
import com.example.demo.service.LoaiDeService;
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
@RequestMapping("/admin/Loai-De")
public class LoaiDeController {
    @Autowired
    private LoaiDeService loaiDeService;

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
        Page<LoaiDe> pages = loaiDeService.findAll(page, size);
        model.addAttribute("pages", pages);
        System.out.println("abc" + pages.getContent().size());
        return "admin/loaide/index";
    }

    @GetMapping("/formAdd")
    public String formAdd(Model model) {
        model.addAttribute("LoaiDe", new LoaiDe());
        return "admin/loaide/form-add";
    }

    @GetMapping("/formUpdate")
    public String formUpdate(@ModelAttribute(name = "id") Integer id, Model model) {
        LoaiDe loaiDeFinById = loaiDeService.findById(id);
        model.addAttribute("LoaiDe", loaiDeFinById);
        return "admin/loaide/form-update";
    }


    @PostMapping("/save")
    public String add(@Valid @ModelAttribute("LoaiDe") CreateRequestLoaiDe requestLoaiDe,
                      BindingResult bindingResult,
                      Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("Lỗi: Có lỗi xảy ra trong dữ liệu");
            model.addAttribute("LoaiDe", requestLoaiDe);
            return "admin/loaide/form-add"; // Đảm bảo rằng view này đúng
        } else {
            String result = loaiDeService.save(requestLoaiDe);
            System.out.println("Kết quả: " + result);
            return "redirect:/admin/Loai-De/all";
        }


    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("LoaiDe") UpdateRequestLoaiDe requestLoaiDe, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("LoaiDe", requestLoaiDe);
            return "admin/loaide/form-update";
        }
        String result = loaiDeService.update(requestLoaiDe);
        System.out.println("Kết quả : " + result);
        return "redirect:/admin/Loai-De/all";
    }

    @GetMapping("/delete")
    public String delete(@ModelAttribute(name = "id") Integer id) {
        loaiDeService.deleteById(id);
        return "redirect:/admin/Loai-De/all";
    }



//    End
}
