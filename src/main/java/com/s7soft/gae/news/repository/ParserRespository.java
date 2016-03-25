package com.s7soft.gae.news.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.s7soft.gae.news.domain.ParserClass;


public interface ParserRespository extends JpaRepository<ParserClass, Long> {
	List<ParserClass> findByStatus(int status);

}
