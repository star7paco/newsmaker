package com.s7soft.gae.news.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.s7soft.gae.news.domain.PostClass;


public interface PostRespository extends JpaRepository<PostClass, Long> {
	List<PostClass> findByTitle(String title);

}
