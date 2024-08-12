package com.example.demo.service.impl;

import com.example.demo.dto.request.create_request.CreateRequestLoaiDe;
import com.example.demo.dto.request.update_request.UpdateRequestLoaiDe;
import com.example.demo.entity.ChatLieu;
import com.example.demo.entity.LoaiDe;
import com.example.demo.repository.ChatLieuRepo;
import com.example.demo.repository.LoaiDeRepo;
import com.example.demo.service.LoaiDeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoaiDeServiceImpl implements LoaiDeService {

    @Autowired
    private LoaiDeRepo loaiDeRepo;

    @Override
    public List<LoaiDe> getAll() {
        return loaiDeRepo.findAll();
    }

    @Override
    public Page<LoaiDe> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<LoaiDe> pages = loaiDeRepo.findAll(pageable);
        return pages;
    }

    @Override
    public LoaiDe findById(Integer id) {
        LoaiDe loaiDe = loaiDeRepo.findById(id).orElseThrow();
        return loaiDe;
    }

    @Override
    public void deleteById(Integer id) {
        loaiDeRepo.deleteById(id);
    }

    @Override
    public String save(CreateRequestLoaiDe request) {
        LoaiDe loaiDeadd = LoaiDe.builder().ten(request.getTen()).trangThai(request.getTrangThai()).build();
        loaiDeRepo.save(loaiDeadd);
        return "Add successfully";
    }

    @Override
    public String update(UpdateRequestLoaiDe request) {
        LoaiDe loaiDeUpdate = LoaiDe.builder().id(request.getId()).ten(request.getTen()).trangThai(request.getTrangThai()).build();
        loaiDeRepo.save(loaiDeUpdate);
        return "Add successfully";
    }
}
