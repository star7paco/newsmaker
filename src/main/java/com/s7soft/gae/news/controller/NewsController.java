package com.s7soft.gae.news.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
import com.s7soft.gae.news.translation.TranslationUtil;
import com.s7soft.gae.news.util.HtmlUtil;

@Controller
public class NewsController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(NewsController.class);
	private static final int TWENTY_SECOND = 20 * 1000;

	@Autowired
	ParserRespository parserRepo;

	@Autowired
	CategoryRespository categoryRepo;

	@Autowired
	PostRespository postRepo;

	@Autowired
	TargetRespository targetRepo;

	@RequestMapping("/")
	String index(Model model) {
		return postList(0, model);
	}

	@RequestMapping("/admin")
	String admin(Model model) {

		long categoryCount = 0;
		long parserCount = 0;
		long postCount = 0;
		long targetCount = 0;

		try {

			categoryCount = categoryRepo.count();
			parserCount = parserRepo.count();
			postCount = postRepo.count();
			targetCount = targetRepo.count();

		} catch (Exception e) {
		}


		if(categoryCount < 1){
			System.out.println("***************  Setup Default  ***************");
			for(CategoryClass category : CategoryClass.getDefault()){
				categoryRepo.save(category);
			}

			for(ParserClass parser : ParserClass.getDefault()){
				parserRepo.save(parser);
			}
		}

		model.addAttribute("categoryCount", categoryCount);
		model.addAttribute("parserCount", parserCount);
		model.addAttribute("postCount", postCount);
		model.addAttribute("targetCount", targetCount);


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
	String postList( Model model ) {
		return postList(0, model);
	}

	@RequestMapping("post-list/{page}")
	String postList(@PathVariable("page") Integer page, Model model) {

		if(page  < 0){
			page = 0;
		}

		Pageable pageable = new PageRequest(page, 12 , Direction.DESC , "date");
		Page<PostClass> postList = postRepo.findByStatus(1, pageable);
		model.addAttribute("postList", postList);
		model.addAttribute("page", page);
		model.addAttribute("title", "news" + page);
		return "post-list";
	}

	@RequestMapping("post/{Id}")
	String post(@PathVariable("Id") Long postId, Model model , @RequestParam(required = false) String page) {
		int intPage = 0;
		try {
			intPage = Integer.parseInt(page);
		} catch (Exception e) {
		}

		PostClass post = new PostClass();
		PostClass dsPost = postRepo.findOne(postId);

		if(dsPost != null){
			BeanUtils.copyProperties(dsPost, post);
			// data 後修正 titleの空白をなくす。
			if(post.getTitle().trim().length() == 0 ){
				try {
					String title = TranslationUtil.getChangeHtml(post.getOriginalTitle());
					post.setTitle(title);
				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}else if(post.getTitle().startsWith("<")){
				post.setTitle(HtmlUtil.delTag(post.getTitle()));
				post.setOriginalTitle(HtmlUtil.delTag(post.getOriginalTitle()));
			}

			post.setClickCount(post.getClickCount()+1);
			postRepo.save(post);
			model.addAttribute("title", post.getStringTitle());

			// カテゴリ情報追加 TODO DBではなくキャッシュしたい
			if(dsPost.getCategoryId() != null){
				CategoryClass category = categoryRepo.findOne(dsPost.getCategoryId());
				model.addAttribute("category", category);
			}else{
				CategoryClass category = new CategoryClass();
				category.setName("News");
				model.addAttribute("category", category);
			}
		}


		model.addAttribute("page", intPage);
		model.addAttribute("post", post);
		return "post";
	}

	@RequestMapping("post-add")
	String add(PostClass post) {
		postRepo.save(post);
		return "redirect:/post-list";
	}

	/**  target */
	@RequestMapping("admin/target-list")
	String targetList(Model model) {
		return targetList(0, 0, model);
	}

	/**  target */
	@RequestMapping("admin/target-list/{Id}")
	String targetList(@PathVariable("Id") Integer page, @Param("status") Integer status, Model model) {
		if(page  < 0){
			page = 0;
		}
		if(status == null){
			status = 0;
		}
		Pageable pageable = new PageRequest(page, 12 , Direction.DESC , "date");
		Page<TargetClass> targetList = targetRepo.findByStatus(status, pageable);
		model.addAttribute("targetList", targetList);
		model.addAttribute("page", page);
		model.addAttribute("status", status);
		return "target-list";
	}

	/** cron job */
	@RequestMapping("cron/rss-read")
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
	@RequestMapping("cron/post-maker")
	String postMaker() {
<<<<<<< HEAD
		long start = System.currentTimeMillis();
=======
//		long start = System.currentTimeMillis();
>>>>>>> branch 'feature/#1' of https://github.com/star7paco/newsmaker.git
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
			count++;
//			if(count > 3){
			if( start + TWENTY_SECOND < System.currentTimeMillis()  ){
				break;
			}

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
				TargetClass newTarget = new TargetClass();
				BeanUtils.copyProperties(target, newTarget);
				newTarget.setStatus(0);
				targetRepo.save(newTarget);
				continue;
			}
			System.out.println("parser : " +parser.getKey());
			System.out.println("target : " +target.getUrl());


			TargetClass saveObj = Parser.parsing(target, parser);
			System.out.println("ret Target: " +saveObj.getUrl());
			targetRepo.save(saveObj);

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
<<<<<<< HEAD

=======
			if(count > 5){
//			if( start + TWENTY_SECOND < System.currentTimeMillis()  ){
				break;
			}
>>>>>>> branch 'feature/#1' of https://github.com/star7paco/newsmaker.git

		}
		LOGGER.info("END postMaker : " + count);
		return "index";
	}


	/** cron job */
	@RequestMapping("cron/trans")
	String trans() {
<<<<<<< HEAD
		long start = System.currentTimeMillis();
=======
//		long start = System.currentTimeMillis();
>>>>>>> branch 'feature/#1' of https://github.com/star7paco/newsmaker.git
		LOGGER.info("STAR trans ");

		int count = 0;
		List<TargetClass> targetList = targetRepo.findByStatus(2);

		for (TargetClass target : targetList) {
			count++;
//			if(count > 3){
			if( start + TWENTY_SECOND < System.currentTimeMillis()  ){
				break;
			}
			System.out.println("targetList size : " +targetList.size());

			// 重複除去
			List<PostClass> list = postRepo.findByUrl(target.getUrl());
			if(list.size() < 1){
				try {
					PostClass post = TranslationUtil.trans(target);

					postRepo.save(post);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}


			// 一致するparserが無い
			TargetClass saveObj = new TargetClass();
			BeanUtils.copyProperties(target, saveObj);
			saveObj.setStatus(3);
			targetRepo.save(saveObj);

<<<<<<< HEAD
=======

			if(count > 5){
//			if( start + TWENTY_SECOND < System.currentTimeMillis()  ){
				break;
			}
>>>>>>> branch 'feature/#1' of https://github.com/star7paco/newsmaker.git
		}

		LOGGER.info("END trans : "+count);
		return "index";
	}
}
