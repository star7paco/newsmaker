package com.s7soft.gae.news.controller;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.s7soft.gae.news.admin.AdminUtil;
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

	private static final Logger log = Logger.getLogger(NewsController.class.getName());
	private static final int RUN_TIME = 29 * 1000;

	@Autowired
	ParserRespository parserRepo;

	@Autowired
	CategoryRespository categoryRepo;

	@Autowired
	PostRespository postRepo;

	@Autowired
	TargetRespository targetRepo;

	@RequestMapping("/")
	String index(HttpSession session, Model model) {
		return postList(session, model , 0 , 0L);
	}

	@RequestMapping("/admin")
	String admin(Model model) {

		long categoryCount = 0;
		try {
			categoryCount = categoryRepo.count();
			model.addAttribute("categoryCount", categoryCount);
		} catch (Exception e) {
			model.addAttribute("categoryCount", "-");
		}


		model.addAttribute("parserCount", "-");
		model.addAttribute("postCount", "-");
		model.addAttribute("targetCount", "-");

		if(categoryCount < 1){
//			System.out.println("***************  Setup Default  ***************");
			for(CategoryClass category : CategoryClass.getDefault()){
				categoryRepo.save(category);
			}

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
		log.info("test :"+ categoryList);
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

		long parserCount = parserRepo.count();
		List<ParserClass> parserList = parserRepo.findAll();

		model.addAttribute("parserlist", parserList);
		model.addAttribute("parsercount", parserCount);
		return "parser-list";
	}

	@RequestMapping("admin/parser-list/{Id}")
	String parserList(@PathVariable("Id") Long parserId, Model model) {
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
	String postList(HttpSession session, Model model, @RequestParam(required = false) Integer p,  @RequestParam(required = false) Long c) {

		if(p == null || p  < 0){
			p = 0;
		}

		if(c == null || c  < 0){
			c = 0l;
		}

		Page<PostClass> postList = null;
		CategoryClass category =  getCategory(c);
		String keywords = "일본,뉴스";

		Pageable pageable = new PageRequest(p, 12 , Direction.DESC , "date");
		if(category == null){
			postList = postRepo.findByStatus(1, pageable);
		}else{
//			Pageable pageable = new PageRequest(0, 6 , Direction.DESC , "clickCount", "date");
			postList = postRepo.findByCategoryIdAndStatus(c, 1, pageable);
			keywords = keywords + "," + category.getName();
		}

		for (PostClass post : postList) {
			if(StringUtils.isEmpty(post.getCategoryName())){
				System.out.println("Update post Category Name");
				CategoryClass postCategory = getCategory(post.getCategoryId());
				PostClass saveObj = new PostClass();
				BeanUtils.copyProperties(post, saveObj);
				saveObj.setCategoryName(postCategory.getName());
				postRepo.save(saveObj);
			}

		}


		model.addAttribute("postList", postList);
		model.addAttribute("page", p);
		model.addAttribute("title", "news:" + p);
		model.addAttribute("keywords", keywords);
		model.addAttribute("category", category);

		return "post-list";
	}

	@RequestMapping("post/{Id}")
	String post(@PathVariable("Id") Long postId, Model model , @RequestParam(required = false) String page) {
		int intPage = 0;
		try {
			intPage = Integer.parseInt(page);
		} catch (Exception e) {
		}

		PostClass dsPost = getPost(postId);
		PostClass post = new PostClass();

		CategoryClass category = null;
		String keywords = "";

		if(dsPost != null){
			BeanUtils.copyProperties(dsPost, post);
			// data 後修正 titleの空白をなくす。
			if(post.getTitle().trim().length() == 0 ){
				try {
					String title = TranslationUtil.getChangeHtml(post.getOriginalTitle());
					post.setTitle(title);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// data 後修正 タグも削除
			post.setTitle(HtmlUtil.delTag(post.getTitle()));
			post.setKeywords(HtmlUtil.delTag(post.getKeywords()));
			post.setOriginalTitle(HtmlUtil.delTag(post.getOriginalTitle()));

			if( post.getVideourl() != null && post.getVideourl().trim().length() > 0 &&  post.getVideourl().contains("www.facebook.com") ){
				post.setVideourl("");
			}

			if(!AdminUtil.isAdminUser()){
				post.setClickCount(post.getClickCount()+1);
			}
			postRepo.save(post);
			updateCachePost( post);

			model.addAttribute("title", post.getStringTitle());

			// カテゴリ情報追加 TODO DBではなくキャッシュしたい
			if(dsPost.getCategoryId() != null){
				category = categoryRepo.findOne(dsPost.getCategoryId());
			}
		}

		if(category == null){
			category = new CategoryClass();
			category.setName("News");
		}

		keywords = category.getName() + ","  + post.getKeywords();

		model.addAttribute("category", category);
		model.addAttribute("keywords", keywords);
		model.addAttribute("page", intPage);
		model.addAttribute("post", post);
		return "post";
	}

	@RequestMapping("admin/post-update")
	String postUpdate(@Param("id") Long id, @Param("status") Integer status) {

		PostClass dsPost = postRepo.findOne(id);
		if(dsPost == null){
			return "redirect:admin/post-list";
		}
		PostClass post = new PostClass();
		BeanUtils.copyProperties(dsPost, post);
		post.setStatus(status);
		postRepo.save(post);
		return "redirect:admin/post-list";
	}

	/**  target */
	@RequestMapping("admin/target-add")
	String addtarget(@Param("id") Long id, @Param("status") Integer status) {

		if( status == null || status < 0){
			return "target-list";
		}

		TargetClass target = targetRepo.findOne(id);

		TargetClass saveObj = new TargetClass();
		BeanUtils.copyProperties(target, saveObj);
		saveObj.setStatus(status);
		targetRepo.save(saveObj);
		return "target-list";
	}

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
		model.addAttribute("count", targetList.getTotalElements());
		return "target-list";
	}

	@RequestMapping("admin/post-list")
	String adminPostList( Model model ) {
		return adminPostList(0, model);
	}

	@RequestMapping("admin/post-list/{page}")
	String adminPostList(@PathVariable("page") Integer page, Model model) {

		if(page  < 0){
			page = 0;
		}

		Pageable pageable = new PageRequest(page, 20 , Direction.DESC , "date");
		Page<PostClass> postList = postRepo.findAll(pageable);
		model.addAttribute("isAdmin", "true");
		model.addAttribute("postList", postList);
		model.addAttribute("page", page);
		model.addAttribute("title", "news" + page);
		return "post-list";
	}

	/** cron job */
	@RequestMapping("cron/rss-read")
	String rssRead() {
		log.info("STAR RssRead");
		List<CategoryClass> categoryList = categoryRepo.findAll();
		for (CategoryClass category : categoryList) {
			List<TargetClass> targetList = RssReader.readRss(category);
			for (TargetClass target : targetList) {
				TargetClass ret = targetRepo.findByUrl(target.getUrl());
				if (ret != null) {
					log.info("continue " + ret.getTitle());
					continue;
				}
				target.setCategoryId(category.getId());
				target.setStatus(1);
				target.setDate(new Date());
				targetRepo.save(target);
			}
		}
		log.info("END RssRead");
		return "index";
	}

	/** cron job */
	@RequestMapping("cron/post-maker")
	String postMaker() {
		long start = System.currentTimeMillis();
		log.info("STAR postMaker");
		List<ParserClass> parsers = parserRepo.findByStatus(1);

		int count = 0;
		List<TargetClass> targetList = targetRepo.findByStatus(1);

		System.out.println("targetList size : "+ targetList.size());

		for (TargetClass target : targetList) {
			count++;
//			if(count > 3){
			if( start + RUN_TIME < System.currentTimeMillis()  ){
				break;
			}


			ParserClass parser = null;
			if(target.getUrl() == null){
				targetRepo.delete(target.getId());
				continue;
			}
			for (ParserClass dbParser : parsers) {
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

			if (parser.getNewsLinkTag() != null && !parser.getNewsLinkTag().isEmpty()) {
				TargetClass saveObj = Parser.parsing(target, parser);
				targetRepo.save(saveObj);

				TargetClass newTarget = new TargetClass();
				BeanUtils.copyProperties(target, newTarget);
				newTarget.setId(null); // urlチェック用に旧URLを作成
				newTarget.setStatus(4);
				targetRepo.save(newTarget);


			}else{
				TargetClass saveObj = Parser.parsing(target, parser);
				targetRepo.save(saveObj);
			}


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
			if( start + RUN_TIME < System.currentTimeMillis()  ){
				break;
			}
		}
		log.info("END postMaker : " + count);
		return "index";
	}


	/** cron job */
	@RequestMapping("cron/trans")
	String trans() {
		long start = System.currentTimeMillis();
		System.out.println("STAR trans ");

		int count = 0;
		int error = 0;
		List<TargetClass> targetList = targetRepo.findByStatus(2);

		System.out.println("targetList size : "+ targetList.size());

		for (TargetClass target : targetList) {
			count++;
			if( start + RUN_TIME < System.currentTimeMillis()  ){
				break;
			}

			if(target.getTitle() == null || target.getTitle().trim().length() < 1 || target.getBody() == null || target.getStringBody().trim().length() < 1){
				// データ不足
				error++;
				TargetClass saveObj = new TargetClass();
				BeanUtils.copyProperties(target, saveObj);
				saveObj.setStatus(4);
				targetRepo.save(saveObj);

			}else{

				// 重複除去
				List<PostClass> list = postRepo.findByUrl(target.getUrl());
				if(list.size() < 1){
					try {
						PostClass post = TranslationUtil.trans(target);

//						CategoryClass category = getCategory(post.getCategoryId());
//						post.setCategoryName(category.getName());

						postRepo.save(post);
					} catch (Exception e) {
						System.out.println(e.getMessage());
						if(e.getMessage().contains("www.excite.co.jp")){
							System.out.println("time out skip");
							continue;
						}
						// 通訳失敗
						error++;
						TargetClass saveObj = new TargetClass();
						BeanUtils.copyProperties(target, saveObj);
						saveObj.setStatus(4);
						targetRepo.save(saveObj);

						continue;
					}
				}
			}


			// postに登録完了。
			TargetClass saveObj = new TargetClass();
			BeanUtils.copyProperties(target, saveObj);
			saveObj.setStatus(3);
			targetRepo.save(saveObj);

			if( start + RUN_TIME < System.currentTimeMillis()  ){
				break;
			}
		}
		System.out.println("trans error: "+error);
		System.out.println("END trans : "+count);
		return "index";
	}


	private CategoryClass getCategory(long id) {
		// メモリキャッシュでデータを取得
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));

		CategoryClass category = null;
		if(id > 0){
			category =(CategoryClass)syncCache.get("c"+id);

			if(category == null){
				category = categoryRepo.findOne(id);
				if(category != null){
					syncCache.put("c"+id, category);
				}

			}
		}
		return category;
	}

	private PostClass getPost(long id) {
		// メモリキャッシュでデータを取得
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));

		PostClass post = null;
		if(id > 0){
			post =(PostClass)syncCache.get("p"+id);
			if(post == null){
				post = postRepo.findOne(id);
				if(post != null){
					syncCache.put("p"+id, post);
				}
			}
		}
		return post;
	}

	private void updateCachePost(PostClass p) {
		// メモリキャッシュでデータを取得
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
		if(p != null){
			syncCache.put("p"+p.getId(), p);
		}
	}
}
