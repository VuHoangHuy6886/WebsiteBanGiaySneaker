package com.example.demo.controller;


import com.example.demo.dto.request.create_request.CreateRequestChatLieu;
import com.example.demo.dto.request.update_request.UpdateRequestChatLieu;
import com.example.demo.entity.ChatLieu;
import com.example.demo.service.ChatLieuService;
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
@RequestMapping("/admin/Chat-Lieu")
public class ChatLieuController {

    @Autowired
    private ChatLieuService chatLieuService;

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
        Page<ChatLieu> pages = chatLieuService.findAll(page, size);
        model.addAttribute("pages", pages);
        System.out.println("abc" + pages.getContent().size());
        return "admin/chatlieu/index";
    }

    @GetMapping("/formAdd")
    public String formAdd(Model model) {
        model.addAttribute("ChatLieu", new ChatLieu());
        return "admin/chatlieu/form-add";
    }

    @GetMapping("/formUpdate")
    public String formUpdate(@ModelAttribute(name = "id") Integer id, Model model) {
        ChatLieu chatLieuFinById = chatLieuService.findById(id);
        model.addAttribute("ChatLieu", chatLieuFinById);
        return "admin/chatlieu/form-update";
    }


    @PostMapping("/save")
    public String add(@Valid @ModelAttribute("ChatLieu") CreateRequestChatLieu requestChatLieu,
                      BindingResult bindingResult,
                      Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("Lỗi: Có lỗi xảy ra trong dữ liệu");
            model.addAttribute("ChatLieu", requestChatLieu);
            return "admin/chatlieu/form-add"; // Đảm bảo rằng view này đúng
        } else {
            String result = chatLieuService.save(requestChatLieu);
            System.out.println("Kết quả: " + result);
            return "redirect:/admin/Chat-Lieu/all";
        }


    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("ChatLieu") UpdateRequestChatLieu requestChatLieu, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ChatLieu", requestChatLieu);
            return "admin/chatlieu/form-update";
        }
        String result = chatLieuService.update(requestChatLieu);
        System.out.println("Kết quả : " + result);
        return "redirect:/admin/Chat-Lieu/all";
    }

    @GetMapping("/delete")
    public String delete(@ModelAttribute(name = "id") Integer id) {
        chatLieuService.deleteById(id);
        return "redirect:/admin/Chat-Lieu/all";
    }


//    END
}
