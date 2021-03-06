package com.s7soft.gae.news.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.s7soft.gae.news.domain.CategoryClass;

public interface CategoryRespository extends JpaRepository<CategoryClass, Long> {
	List<CategoryClass> findByname(String name);



}
