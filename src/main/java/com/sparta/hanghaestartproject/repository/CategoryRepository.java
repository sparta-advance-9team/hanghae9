package com.sparta.hanghaestartproject.repository;

import com.sparta.hanghaestartproject.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional saveAll();
}
