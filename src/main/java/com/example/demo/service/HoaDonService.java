package com.example.demo.service;


import com.example.demo.entity.Hang;
import com.example.demo.entity.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface HoaDonService {
    Page<HoaDon> findAll(Integer page, Integer size);

    String genMaHD();

    HoaDon findById(Integer id);

    void save(HoaDon hoaDon);

    Page<HoaDon>findAllSordDate(Integer page, Integer size);
}
