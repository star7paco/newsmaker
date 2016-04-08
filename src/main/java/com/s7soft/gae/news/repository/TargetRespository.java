package com.s7soft.gae.news.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.s7soft.gae.news.domain.TargetClass;


public interface TargetRespository extends JpaRepository<TargetClass, Long> {
	TargetClass findByUrl(String Url);

	List<TargetClass> findByStatus(int status);

	Page<TargetClass> findByStatus(int status , Pageable paramPageable);

}
