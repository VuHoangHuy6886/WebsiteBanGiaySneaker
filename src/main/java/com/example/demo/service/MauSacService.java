package com.example.demo.service;

import com.example.demo.dto.request.create_request.CreateRequestHang;
import com.example.demo.dto.request.create_request.CreateRequestMauSac;
import com.example.demo.dto.request.update_request.UpdateRequestHang;
import com.example.demo.dto.request.update_request.UpdateRequestMauSac;
import com.example.demo.entity.Hang;
import com.example.demo.entity.MauSac;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MauSacService {
  List<MauSac> getAll();

  Page<MauSac> findAll(Integer page, Integer size);

  MauSac findById(Integer id);

  void deleteById(Integer id);

  String save(CreateRequestMauSac request);
  String update(UpdateRequestMauSac request);
}
