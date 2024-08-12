package com.example.demo.service;

import com.example.demo.dto.request.create_request.CreateRequestKichThuoc;
import com.example.demo.dto.request.update_request.UpdateRequestKichThuoc;
import com.example.demo.entity.Hang;
import com.example.demo.entity.KichThuoc;
import org.springframework.data.domain.Page;

import java.util.List;

public interface KichThuocService {

    List<KichThuoc> getAll();


    Page<KichThuoc> findAll(Integer page, Integer size);

    KichThuoc findById(Integer id);

    String save(CreateRequestKichThuoc request);
    String update(UpdateRequestKichThuoc request);

    void deleteById(Integer id);

}
