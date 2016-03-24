package com.s7soft.gae.news.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.s7soft.gae.news.domain.CategoryClass;
import com.s7soft.gae.news.domain.PostClass;
import com.s7soft.gae.news.repository.CategoryRespository;
import com.s7soft.gae.news.repository.PostRespository;

@Controller
public class NewsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(NewsController.class);

	@Autowired
	CategoryRespository categoryRepo;

	@Autowired
	PostRespository postRepo;

	@RequestMapping("/spring")
	String index(){
		LOGGER.info("log test");
		return "index";
	}

	@RequestMapping("admin/category-list")
	String categoryList(Model model){
		long categoryCount = categoryRepo.count();
		List<CategoryClass> categoryList = categoryRepo.findAll();
		LOGGER.info("test {}", categoryList);
		model.addAttribute("categorylist", categoryList);
		model.addAttribute("categorycount", categoryCount);
		return "category-list";
	}

	@RequestMapping("post-list")
	String postList(Model model){
		List<PostClass> postList = postRepo.findAll();
		model.addAttribute("postlist", postList);
		return "post-list";
	}

	@RequestMapping("admin/category-add")
	String add(CategoryClass member){
		member.setDate(new Date());
		categoryRepo.save(member);
		return "redirect:/admin/category-list";
	}

	@RequestMapping("post-add")
	String add(PostClass post){
		postRepo.save(post);
		return "redirect:/post-list";
	}

	String update(){
		return "redirect:/admin/category-list";
	}
}

