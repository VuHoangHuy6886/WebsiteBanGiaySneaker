package com.example.demo.service.impl;


import com.example.demo.dto.request.create_request.CreateRequestMauSac;
import com.example.demo.dto.request.update_request.UpdateRequestMauSac;
import com.example.demo.entity.MauSac;
import com.example.demo.repository.MauSacRepo;
import com.example.demo.service.HangService;
import com.example.demo.service.MauSacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MauSacServiceImpl implements MauSacService {

    @Autowired
    private MauSacRepo mauSacRepo;

    @Override
    public List<MauSac> getAll() {
        return mauSacRepo.findAll();
    }

    @Override
    public Page<MauSac> findAll(Integer page, Integer size) {

//        Phan trang
        Pageable pageable = PageRequest.of(page, size);
        Page<MauSac> pages = mauSacRepo.findAll(pageable);
        return pages;
    }

    @Override
    public MauSac findById(Integer id) {
        MauSac mauSac = mauSacRepo.findById(id).orElseThrow();
        return mauSac;
    }

    @Override
    public String save(CreateRequestMauSac request) {
        MauSac mauSacadd = MauSac.builder().ten(request.getTen()).trangThai(request.getTrangThai()).build();
        mauSacRepo.save(mauSacadd);
        return "Add successfully";
    }


    @Override
    public String update(UpdateRequestMauSac request) {
        MauSac mauSacUpdate = MauSac.builder().id(request.getId()).ten(request.getTen()).trangThai(request.getTrangThai()).build();
        mauSacRepo.save(mauSacUpdate);
        return "Add successfully";
    }

    @Override
    public void deleteById(Integer id) {
        mauSacRepo.deleteById(id);
    }
}
