package com.example.demo.controller;


import com.example.demo.dto.request.create_request.CreateRequestChiTietSanPham;
import com.example.demo.dto.request.create_request.CreateRequestSanPham;
import com.example.demo.dto.request.update_request.UpdateRequestChiTietSanPham;
import com.example.demo.dto.request.update_request.UpdateRequestSanPham;
import com.example.demo.entity.ChatLieu;
import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.Hang;
import com.example.demo.entity.KichThuoc;
import com.example.demo.entity.LoaiDe;
import com.example.demo.entity.MauSac;
import com.example.demo.entity.SanPham;
import com.example.demo.service.ChatLieuService;
import com.example.demo.service.ChiTietSanPhamService;
import com.example.demo.service.KichThuocService;
import com.example.demo.service.LoaiDeService;
import com.example.demo.service.MauSacService;
import com.example.demo.service.SanPhamService;
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
@RequestMapping("/admin/Chi-Tiet-San-Pham")
public class ChiTietSanPhamController {

    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    private MauSacService mauSacService;
    @Autowired
    private KichThuocService kichThuocService;
    @Autowired
    private ChatLieuService chatLieuService;
    @Autowired
    private LoaiDeService loaiDeService;
    @Autowired
    private SanPhamService sanPhamService;

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
        Page<ChiTietSanPham> pages = chiTietSanPhamService.findAll(page, size);
        model.addAttribute("pages", pages);
        return "admin/chitietsanpham/index";
    }


    @GetMapping("/formAdd")
    public String formAdd(@RequestParam("idSP") Integer idSP, Model model) {


        List<MauSac> mauSacs = mauSacService.getAll();
        model.addAttribute("mauSacs", mauSacs);
        List<KichThuoc> kichThuocs = kichThuocService.getAll();
        model.addAttribute("kichThuocs", kichThuocs);
        List<ChatLieu> chatLieus = chatLieuService.getAll();
        model.addAttribute("chatLieus", chatLieus);
        List<LoaiDe> loaiDes = loaiDeService.getAll();
        model.addAttribute("loaiDes", loaiDes);
        Integer sanPhams = sanPhamService.findById(idSP).getId();
        System.out.println("id sp form add : " + sanPhams);

        CreateRequestChiTietSanPham chiTietSanPham = new CreateRequestChiTietSanPham();
        chiTietSanPham.setIdSanPham(String.valueOf(sanPhams));
        model.addAttribute("ChiTietSanPham", chiTietSanPham);
        model.addAttribute("idSP", sanPhams);
        return "admin/chitietsanpham/form-add";
    }

    @PostMapping("/save")
    public String add(@Valid @ModelAttribute("ChiTietSanPham") CreateRequestChiTietSanPham requestChiTietSanPham,
                      BindingResult bindingResult,
                      Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("Lỗi: Có lỗi xảy ra trong dữ liệu");
            model.addAttribute("ChiTietSanPham", requestChiTietSanPham);
            return "admin/Chi-Tiet-San-Pham/form-add"; // Đảm bảo rằng view này đúng
        } else {
            String result = chiTietSanPhamService.save(requestChiTietSanPham);
            return "redirect:/admin/San-Pham/all";
        }
    }

    @GetMapping("/formUpdate")
    public String formUpdate(@ModelAttribute(name = "id") Integer id, Model model) {
        ChiTietSanPham chiTietSanPhamFinById = chiTietSanPhamService.findById(id);
        System.out.println("lay thanh cong id " + chiTietSanPhamFinById.getId());

        UpdateRequestChiTietSanPham updateRequestChiTietSanPham = new UpdateRequestChiTietSanPham();
        updateRequestChiTietSanPham.setId(chiTietSanPhamFinById.getId());
        updateRequestChiTietSanPham.setMa(chiTietSanPhamFinById.getMa());
        updateRequestChiTietSanPham.setSoLuong(String.valueOf(chiTietSanPhamFinById.getSoLuong()));
        updateRequestChiTietSanPham.setDonGia(String.valueOf(chiTietSanPhamFinById.getDonGia()));
        updateRequestChiTietSanPham.setTrangThai(String.valueOf(chiTietSanPhamFinById.getTrangThai()));
        updateRequestChiTietSanPham.setIdMauSac(String.valueOf(chiTietSanPhamFinById.getMauSac().getId()));
        updateRequestChiTietSanPham.setIdKichThuoc(String.valueOf(chiTietSanPhamFinById.getKichThuoc().getId()));
        updateRequestChiTietSanPham.setIdChatLieu(String.valueOf(chiTietSanPhamFinById.getChatLieu().getId()));
        updateRequestChiTietSanPham.setIdLoaiDe(String.valueOf(chiTietSanPhamFinById.getLoaiDe().getId()));
        updateRequestChiTietSanPham.setIdSanPham(String.valueOf(chiTietSanPhamFinById.getSanPham().getId()));
        System.out.println("id san p " + updateRequestChiTietSanPham.getIdSanPham());

        model.addAttribute("ChiTietSanPham", updateRequestChiTietSanPham);

        List<MauSac> mauSacs = mauSacService.getAll();
        List<KichThuoc> kichThuocs = kichThuocService.getAll();
        List<ChatLieu> chatLieus = chatLieuService.getAll();
        List<LoaiDe> loaiDes = loaiDeService.getAll();
        List<SanPham> sanPhams = sanPhamService.getAll();
        model.addAttribute("mauSacs", mauSacs);
        model.addAttribute("kichThuocs", kichThuocs);
        model.addAttribute("chatLieus", chatLieus);
        model.addAttribute("loaiDes", loaiDes);
        model.addAttribute("sanPhams", sanPhams);
        return "admin/chitietsanpham/form-update";
    }


    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("ChiTietSanPham") UpdateRequestChiTietSanPham requestChiTietSanPham,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ChiTietSanPham", requestChiTietSanPham);
            return "admin/chitietsanpham/form-update";
        }
        String result = chiTietSanPhamService.update(requestChiTietSanPham);
        return "redirect:/admin/San-Pham/all";
    }


    @GetMapping("/san-pham")
    public String showListSanPham(
            @RequestParam("idSP") Integer idSP,
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
        Page<ChiTietSanPham> pages = chiTietSanPhamService.findAllByID(idSP, page, size);
        model.addAttribute("pages", pages);
        model.addAttribute("idSP", idSP);
        return "admin/chitietsanpham/index";
    }
}
