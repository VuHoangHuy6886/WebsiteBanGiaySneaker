package com.example.demo.service.impl;


import com.example.demo.dto.request.create_request.CreateRequestChatLieu;
import com.example.demo.dto.request.update_request.UpdateRequestChatLieu;
import com.example.demo.entity.ChatLieu;
import com.example.demo.entity.Hang;
import com.example.demo.entity.MauSac;
import com.example.demo.repository.ChatLieuRepo;
import com.example.demo.service.ChatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatLieuServiceImpl implements ChatLieuService {

    @Autowired
    private ChatLieuRepo chatLieuRepo;


    @Override
    public List<ChatLieu> getAll() {
        return chatLieuRepo.findAll();
    }

    @Override
    public Page<ChatLieu> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ChatLieu> pages = chatLieuRepo.findAll(pageable);
        return pages;
    }

    @Override
    public ChatLieu findById(Integer id) {
        ChatLieu chatLieu = chatLieuRepo.findById(id).orElseThrow();
        return chatLieu;
    }

    @Override
    public void deleteById(Integer id) {

        chatLieuRepo.deleteById(id);
    }

    @Override
    public String save(CreateRequestChatLieu request) {
        ChatLieu chatLieuadd = ChatLieu.builder().ten(request.getTen()).trangThai(request.getTrangThai()).build();
        chatLieuRepo.save(chatLieuadd);
        return "Add successfully";
    }

    @Override
    public String update(UpdateRequestChatLieu request) {
        ChatLieu chatLieuUpdate = ChatLieu.builder().id(request.getId()).ten(request.getTen()).trangThai(request.getTrangThai()).build();
        chatLieuRepo.save(chatLieuUpdate);
        return "Add successfully";
    }


//    end
}
