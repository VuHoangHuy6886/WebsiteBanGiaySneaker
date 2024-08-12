package com.example.demo.service;


import com.example.demo.dto.request.create_request.CreateRequestLoaiDe;
import com.example.demo.dto.request.update_request.UpdateRequestLoaiDe;
import com.example.demo.entity.Hang;
import com.example.demo.entity.LoaiDe;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LoaiDeService {

    List<LoaiDe> getAll();


    Page<LoaiDe> findAll(Integer page, Integer size);

    LoaiDe findById(Integer id);

    void deleteById(Integer id);

    String save(CreateRequestLoaiDe request);
    String update(UpdateRequestLoaiDe request);
}
