package com.example.demo.service.impl;

import com.example.demo.dto.request.create_request.CreateRequestKichThuoc;
import com.example.demo.dto.request.update_request.UpdateRequestKichThuoc;
import com.example.demo.entity.Hang;
import com.example.demo.entity.KichThuoc;
import com.example.demo.repository.KichThuocRepo;
import com.example.demo.service.KichThuocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KichThuocServiceImpl implements KichThuocService {

    @Autowired
    private KichThuocRepo kichThuocRepo;

    @Override
    public List<KichThuoc> getAll() {
        return kichThuocRepo.findAll();
    }

    @Override
    public Page<KichThuoc> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<KichThuoc> pages = kichThuocRepo.findAll(pageable);
        return pages;
    }

    @Override
    public KichThuoc findById(Integer id) {
        KichThuoc kichThuoc = kichThuocRepo.findById(id).orElseThrow();
        return kichThuoc;
    }

    @Override
    public String save(CreateRequestKichThuoc request) {
        KichThuoc kichThuocAdd = KichThuoc.builder().ten(request.getTen()).trangThai(request.getTrangThai()).build();
        kichThuocRepo.save(kichThuocAdd);
        return "Add successfully";
    }

    @Override
    public String update(UpdateRequestKichThuoc request) {
        KichThuoc kichThuocUpdate = KichThuoc.builder().id(request.getId()).ten(request.getTen()).trangThai(request.getTrangThai()).build();
        kichThuocRepo.save(kichThuocUpdate);
        return "Add successfully";
    }

    @Override
    public void deleteById(Integer id) {

        kichThuocRepo.deleteById(id);
    }
}
