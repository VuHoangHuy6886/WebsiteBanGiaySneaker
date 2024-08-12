package com.example.demo.service;

import com.example.demo.dto.request.create_request.CreateRequestChatLieu;
import com.example.demo.dto.request.create_request.CreateRequestMauSac;
import com.example.demo.dto.request.update_request.UpdateRequestChatLieu;
import com.example.demo.dto.request.update_request.UpdateRequestMauSac;
import com.example.demo.entity.ChatLieu;
import com.example.demo.entity.Hang;
import com.example.demo.entity.MauSac;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ChatLieuService {

    List<ChatLieu> getAll();


    Page<ChatLieu> findAll(Integer page, Integer size);

    ChatLieu findById(Integer id);

    void deleteById(Integer id);

    String save(CreateRequestChatLieu request);
    String update(UpdateRequestChatLieu request);
}
