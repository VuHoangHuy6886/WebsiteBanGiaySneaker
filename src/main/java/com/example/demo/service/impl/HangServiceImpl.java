package com.example.demo.service.impl;

import com.example.demo.dto.request.create_request.CreateRequestHang;
import com.example.demo.dto.request.update_request.UpdateRequestHang;
import com.example.demo.entity.Hang;
import com.example.demo.repository.HangRepo;
import com.example.demo.service.HangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.sound.midi.MidiFileFormat;
import java.util.List;

@Component
public class HangServiceImpl implements HangService {
    @Autowired
    private HangRepo hangRepo;

    @Override
    public List<Hang> getAll() {
        return hangRepo.findAll();
    }

    @Override
    public Page<Hang> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Hang> pages = hangRepo.findAll(pageable);
        return pages;
    }

    @Override
    public List<Hang> findAll() {
        return hangRepo.findAll();
    }

    @Override
    public Hang findById(Integer id) {
        Hang hang = hangRepo.findById(id).orElseThrow();
        return hang;
    }

    @Override
    public String save(CreateRequestHang request) {
        Hang hangAdd = Hang.builder().ten(request.getTen()).trangThai(request.getTrangThai()).build();
        hangRepo.save(hangAdd);
        return "Add successfully";
    }

    @Override
    public String update(UpdateRequestHang request) {
        // cach 1 : su dung builder set doi tuong
        Hang hangUpdate = Hang.builder().id(request.getId()).ten(request.getTen()).trangThai(request.getTrangThai()).build();
        // cach 2 : set truc du lieu vao doi tuong
//        Hang hangUpdate = new Hang();
//        hangUpdate.setId(request.getId());
//        hangUpdate.setTen(request.getTen());
//        hangUpdate.setTrangThai(request.getTrangThai());
        hangRepo.save(hangUpdate);
        return "Add successfully";
    }

    @Override
    public void deleteById(Integer id) {
        hangRepo.deleteById(id);
    }
}
