package com.example.demo.controller;

import com.example.demo.entity.NhanVien;
import com.example.demo.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SecurityController {
    @Autowired
    private NhanVienService nhanVienService;

    // return form login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/save")
    public String register(@ModelAttribute NhanVien nhanVien) {
        System.out.println("employee :  " + nhanVien);

        boolean checkEmail = nhanVienService.checkEmail(nhanVien.getEmail());
        if (checkEmail) {
            System.out.println("Email đã tồn tại ");
        } else {
            NhanVien e = nhanVienService.save(nhanVien);
            if (e != null) {
                System.out.println("Register successful");
            } else {
                System.out.println("Register failed");

            }
        }
        return "redirect:/register";
    }

    @GetMapping("/Reset-password")
    public String reset() {
        return "ResetPassword";
    }

    @PostMapping("/Reset-password")
    public String processForgotPassword(@RequestParam("email") String email) {
        if (email != null && !email.isEmpty()) {
            nhanVienService.resetPassword(email);
        } else {
            return "login";
        }
        return "login";
    }
}
