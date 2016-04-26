package com.s7soft.gae.news.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.s7soft.gae.news.domain.PostClass;


public interface PostRespository extends JpaRepository<PostClass, Long> {
	List<PostClass> findByTitle(String title);

	Page<PostClass> findByLangAndStatus(String lang , int status , Pageable paramPageable);

//	Page<PostClass> findByLangNot(String lang , Pageable paramPageable);
	//Page<PostClass> findByStatus(int status , Pageable paramPageable);

	Page<PostClass> findByStatusAndDateBetween(int status ,Date departure, Date arrival, Pageable paramPageable);

	List<PostClass> findByUrl(String url);

	Page<PostClass> findByCategoryIdAndStatus(long categoryId, int status , Pageable paramPageable);

	Page<PostClass> findByCategoryIdAndStatusAndDateBetween(long categoryId, int status ,Date departure, Date arrival, Pageable paramPageable);

	List<PostClass> findByDateAfter(Date date);

	List<PostClass> findByCategoryIdAndDateAfter(long categoryId, Date date);

	Page<PostClass> findByDateAfter(Date date, Pageable paramPageable);

}
