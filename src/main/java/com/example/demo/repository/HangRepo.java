package com.example.demo.repository;

import com.example.demo.entity.Hang;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HangRepo extends JpaRepository<Hang,Integer> {
}
