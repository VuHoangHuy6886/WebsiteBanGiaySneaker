package com.example.demo.controller;


import com.example.demo.dto.request.create_request.CreateRequestKichThuoc;
import com.example.demo.dto.request.update_request.UpdateRequestKichThuoc;
import com.example.demo.entity.KichThuoc;
import com.example.demo.service.KichThuocService;
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
@RequestMapping("/admin/Kich-Thuoc")
public class KichThuocController {

    @Autowired
    private KichThuocService kichThuocService;

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
        Page<KichThuoc> pages = kichThuocService.findAll(page,size);
        model.addAttribute("pages", pages);

        return "admin/kichthuoc/index";
    }

    @GetMapping("/formAdd")
    public String formAdd(Model model) {
        model.addAttribute("KichThuoc", new KichThuoc());
        return "admin/kichthuoc/form-add";
    }

    @GetMapping("/formUpdate")
    public String formUpdate(@ModelAttribute(name = "id") Integer id, Model model) {
        KichThuoc kichThuocFinById = kichThuocService.findById(id);
        model.addAttribute("KichThuoc", kichThuocFinById);
        return "admin/kichthuoc/form-update";
    }


    @PostMapping("/save")
    public String add(@Valid @ModelAttribute("KichThuoc") CreateRequestKichThuoc requestKichThuoc,
                      BindingResult bindingResult,
                      Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("Lỗi: Có lỗi xảy ra trong dữ liệu");
            model.addAttribute("KichThuoc", requestKichThuoc);
            return "admin/kichthuoc/form-add"; // Đảm bảo rằng view này đúng
        } else {
            String result = kichThuocService.save(requestKichThuoc);
            System.out.println("Kết quả: " + result);
            return "redirect:/admin/Kich-Thuoc/all";
        }
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("KichThuoc") UpdateRequestKichThuoc requestKichThuoc,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("KichThuoc", requestKichThuoc);
            return "admin/kichthuoc/form-update";
        }
        System.out.println(requestKichThuoc.getId());
        String result = kichThuocService.update(requestKichThuoc);
//        System.out.println("Kết quả : " + result);
        return "redirect:/admin/Kich-Thuoc/all";
    }

    @GetMapping("/delete")
    public String delete(@ModelAttribute(name = "id") Integer id) {
        kichThuocService.deleteById(id);
        return "redirect:/admin/Kich-Thuoc/all";
    }




//    END
}

