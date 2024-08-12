package com.example.demo.service;

import com.example.demo.dto.request.create_request.CreateRequestHang;
import com.example.demo.dto.request.update_request.UpdateRequestHang;
import com.example.demo.entity.Hang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HangService {
    List<Hang> getAll();

    Page<Hang> findAll(Integer page, Integer size);
    List<Hang> findAll();

    Hang findById(Integer id);

    String save(CreateRequestHang request);

    String update(UpdateRequestHang request);

    void deleteById(Integer id);
}
