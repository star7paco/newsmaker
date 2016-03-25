package com.s7soft.gae.news.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.s7soft.gae.news.domain.CategoryClass;
import com.s7soft.gae.news.domain.ParserClass;
import com.s7soft.gae.news.domain.PostClass;
import com.s7soft.gae.news.domain.TargetClass;
import com.s7soft.gae.news.parser.Parser;
import com.s7soft.gae.news.repository.CategoryRespository;
import com.s7soft.gae.news.repository.ParserRespository;
import com.s7soft.gae.news.repository.PostRespository;
import com.s7soft.gae.news.repository.TargetRespository;
import com.s7soft.gae.news.rss.RssReader;

@Controller
public class NewsController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(NewsController.class);

	@Autowired
	ParserRespository parserRepo;

	@Autowired
	CategoryRespository categoryRepo;

	@Autowired
	PostRespository postRepo;

	@Autowired
	TargetRespository targetRepo;

	@RequestMapping("/spring")
	String index() {
		LOGGER.info("log test");
		return "index";
	}

	@RequestMapping("/admin")
	String admin() {

		long categoryCount = categoryRepo.count();
		if(categoryCount < 1){
			System.out.println("***************  Setup Default  ***************");
			categoryRepo.save(CategoryClass.getDefault());
			for(ParserClass parser : ParserClass.getDefault()){
				parserRepo.save(parser);
			}
		}



		return "admin";
	}

	/** category */
	@RequestMapping("admin/category-list")
	String categoryList(Model model) {
		long categoryCount = categoryRepo.count();
		List<CategoryClass> categoryList = categoryRepo.findAll();
		LOGGER.info("test {}", categoryList);
		model.addAttribute("categorylist", categoryList);
		model.addAttribute("categorycount", categoryCount);
		return "category-list";
	}

	@RequestMapping("admin/category-list/{Id}")
	String categoryList(@PathVariable("Id") Long categoryId, Model model) {
		CategoryClass category = categoryRepo.findOne(categoryId);
		model.addAttribute("onecategory", category);
		return "category-list";
	}

	@RequestMapping("admin/category-update")
	String updateCategory(CategoryClass category) {
		category.setDate(new Date());
		categoryRepo.save(category);
		return "redirect:/admin/category-list";
	}

	@RequestMapping("admin/category-add")
	String add(CategoryClass category) {
		category.setDate(new Date());
		categoryRepo.save(category);
		return "redirect:/admin/category-list";
	}

	/** parser */
	@RequestMapping("admin/parser-list")
	String parserList(Model model) {
		System.out.println("admin/parser-list");
		long parserCount = parserRepo.count();
		List<ParserClass> parserList = parserRepo.findAll();
		System.out.println("test : " + parserList);
		model.addAttribute("parserlist", parserList);
		model.addAttribute("parsercount", parserCount);
		return "parser-list";
	}

	@RequestMapping("admin/parser-list/{Id}")
	String parserList(@PathVariable("Id") Long parserId, Model model) {
		System.out.println("admin/parser-list/{Id}=" + parserId);
		long parserCount = parserRepo.count();
		ParserClass parser = parserRepo.findOne(parserId);
		model.addAttribute("oneparser", parser);
		model.addAttribute("parsercount", parserCount);
		return "parser-list";
	}

	@RequestMapping("admin/parser-update")
	String updateparser(ParserClass parser) {
		parser.setDate(new Date());
		parserRepo.save(parser);
		return "redirect:/admin/parser-list";
	}

	@RequestMapping("admin/parser-add")
	String add(ParserClass parser) {
		parser.setDate(new Date());
		parserRepo.save(parser);
		return "redirect:/admin/parser-list";
	}

	@RequestMapping("post-list")
	String postList(Model model) {
		List<PostClass> postList = postRepo.findAll();
		model.addAttribute("postlist", postList);
		return "post-list";
	}

	@RequestMapping("post-add")
	String add(PostClass post) {
		postRepo.save(post);
		return "redirect:/post-list";
	}

	@RequestMapping("admin/target-list")
	String targetList(Model model) {
		List<TargetClass> targetList = targetRepo.findAll();
		model.addAttribute("targetlist", targetList);
		return "target-list";
	}

	/** cron job */
	@RequestMapping("rss-read")
	String rssRead() {
		LOGGER.info("STAR RssRead");
		List<CategoryClass> categoryList = categoryRepo.findAll();
		for (CategoryClass category : categoryList) {
			List<TargetClass> targetList = RssReader.readRss(category);
			for (TargetClass target : targetList) {
				TargetClass ret = targetRepo.findByUrl(target.getUrl());
				if (ret != null) {
					LOGGER.info("continue " + ret.getTitle());
					continue;
				}
				target.setCategoryId(category.getId());
				target.setStatus(1);
				target.setDate(new Date());
				targetRepo.save(target);
			}
		}
		LOGGER.info("END RssRead");
		return "index";
	}

	/** cron job */
	@RequestMapping("post-maker")
	String postMaker() {
		LOGGER.info("STAR postMaker");
//		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
//		syncCache.setErrorHandler(ErrorHandlers
//				.getConsistentLogAndContinue(Level.INFO));
//		@SuppressWarnings("unchecked")
//		List<ParserClass> parsers = (List<ParserClass>) syncCache.get(Parser.MemcacheServiceKey);
//		Date cacheTime = (Date) syncCache.get(Parser.MemcacheServiceTimeKey);
//		System.out.println("cacheTime : "+cacheTime);
//		if (parsers == null || cacheTime == null || cacheTime.after(new Date())) {
//			List<ParserClass> parserList = parserRepo.findAll();
//			Calendar  cal = Calendar.getInstance();
//			cal.add(Calendar.MINUTE, 90);
//
//			System.out.println("new cacheTime : "+ cal.getTime());
//			syncCache.put(Parser.MemcacheServiceKey, parserList); // Populate
//			syncCache.put(Parser.MemcacheServiceTimeKey, cal.getTime());
//		}

		List<ParserClass> parserList = parserRepo.findByStatus(1);

		System.out.println("parserList size : " +parserList.size());

		int count = 0;
		List<TargetClass> targetList = targetRepo.findByStatus(1);
		for (TargetClass target : targetList) {


			System.out.println("targetList size : " +targetList.size());

			ParserClass parser = null;
			if(target.getUrl() == null){
				targetRepo.delete(target.getId());
				continue;
			}
			for (ParserClass dbParser : parserList) {
				if(target.getUrl().contains(dbParser.getKey())){
					parser = dbParser;
				}
			}


			// 一致するparserが無い
			if(parser == null){
				targetRepo.delete(target.getId());
				continue;
			}
			System.out.println("parser : " +parser.getKey());
			System.out.println("target : " +target.getUrl());

			count++;
			TargetClass ret = Parser.parsing(target, parser);

			System.out.println("ret Target: " +ret.getUrl());
			targetRepo.save(ret);

			// TODO 後処理。。。悩み中
//			switch (parser.getClosing()) {
//			case "delete":
//				targetRepo.delete(target.getId());
//				break;
//			case "update_status":
//				target.setStatus(2);
//				targetRepo.save(target);
//				break;
//			default:
//				break;
//			}

			if(count > 5){
				break;
			}

		}
		LOGGER.info("END postMaker");
		return "index";
	}
}
