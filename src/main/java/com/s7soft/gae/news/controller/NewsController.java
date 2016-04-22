package com.s7soft.gae.news.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import com.s7soft.gae.news.ui.UI;
import com.s7soft.gae.news.util.HtmlUtil;
import com.s7soft.gae.news.util.TimeUtil;

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
	String index(Model model) {
		return postList(model , 0 , 0L, null);
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


	@RequestMapping("post-list/{p}")
	String postList(@PathVariable("p") Integer page, Model model) {
		return postList(model,page,0L,"");
	}


	@RequestMapping("post-list")
	String postList(Model model, @RequestParam(required = false) Integer p,  @RequestParam(required = false) Long c, @RequestParam(required = false) String hit) {

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


		model.addAttribute("hotpost", getHotPostByCache());
		model.addAttribute("postList", postList);
		model.addAttribute("page", p);
		model.addAttribute("title", "news:" + p);
		model.addAttribute("keywords", keywords);
		model.addAttribute("category", category);
		model.addAttribute("ui", getUI());
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

			if( !StringUtils.isEmpty(post.getVideourl()) &&  post.getVideourl().contains("www.facebook.com") ){
				post.setVideourl("");
			}

			// data 後修正 カテゴリ名追加
			if(StringUtils.isEmpty(post.getCategoryName())){
				System.out.println("Update post Category Name");
				CategoryClass postCategory = getCategory(post.getCategoryId());
				post.setCategoryName(postCategory.getName());
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



		model.addAttribute("hotpost", getHotPostByCache());
		model.addAttribute("category", category);
		model.addAttribute("keywords", keywords);
		model.addAttribute("page", intPage);
		model.addAttribute("post", post);
		model.addAttribute("ui", getUI());

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
//		List<CategoryClass> categoryList = categoryRepo.findAll();
		List<CategoryClass> categoryList = getUI().getMenus();
		for (CategoryClass category : categoryList) {
			List<TargetClass> targetList = RssReader.readRss(category);
			for (TargetClass target : targetList) {

				// URL確認でメモリキャッシュを使用する
				if ( isUrlOnTarget( target.getUrl() ) ) {
					log.info("continue " + target.getTitle());
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
				try {
					TargetClass saveObj = Parser.parsing(target, parser);
					targetRepo.save(saveObj);

					TargetClass newTarget = new TargetClass();
					BeanUtils.copyProperties(target, newTarget);
					newTarget.setId(null); // urlチェック用に旧URLを作成
					newTarget.setStatus(4);
					targetRepo.save(newTarget);
				} catch (Exception e) {
				}

			}else{
				try {
					TargetClass saveObj = Parser.parsing(target, parser);
					targetRepo.save(saveObj);
				} catch (Exception e) {
				}
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

						CategoryClass category = getCategory(post.getCategoryId());
						post.setCategoryName(category.getName());

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

	/** cron job */
	@RequestMapping("cron/delete-target")
	String deleteTarget() {
		long start = System.currentTimeMillis();
		log.info("STAR DeleteTarget");
		int count = 0;

		  // カレンダークラスのインスタンスを取得
        Calendar cal = Calendar.getInstance();
        // 10日前
        cal.add(Calendar.DATE, -10);
        Date date = cal.getTime();
        log.info("delete date : " + TimeUtil.format(date));

		List<TargetClass> targetList = targetRepo.findByDateBefore(date);
		log.info("targetList size : " + targetList.size());

		for (TargetClass target : targetList) {

			targetRepo.delete( target.getId() );
			 count ++;
			if( start + RUN_TIME < System.currentTimeMillis()  ){
				break;
			}
		}

		log.info("END DeleteTarget : " + count);
		return "index";
	}

	private MemcacheService getMemcache(String name){
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService(name);
		syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
		return syncCache;
	}

	private CategoryClass getCategory(long id) {
		// メモリキャッシュでデータを取得
		MemcacheService syncCache = getMemcache(CategoryClass.class.getName());

		CategoryClass category = null;
		if(id > 0){
			category =(CategoryClass)syncCache.get(id);

			if(category == null){
				category = categoryRepo.findOne(id);
				if(category != null){
					syncCache.put(id, category);
				}

			}
		}
		return category;
	}

	private PostClass getPost(long id) {
		// メモリキャッシュでデータを取得
		MemcacheService syncCache = getMemcache(PostClass.class.getName());

		PostClass post = null;
		if(id > 0){
			post =(PostClass)syncCache.get(id);
			if(post == null){
				post = postRepo.findOne(id);
				if(post != null){
					syncCache.put(id, post);
				}
			}
		}
		return post;
	}


	private List<PostClass> getHotPost() {
		log.info("start DB hot post");
		List<PostClass> list = null;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date date = cal.getTime();
		list =  postRepo.findByDateAfter(date);

		log.info("list.size : " + list.size());

		PostClass rank[] = new PostClass[3];

		if(list.size() > 4 ){
			for (PostClass post : list) {
				if(rank[0] == null ){
					rank[0] = post;
					rank[1] = post;
					rank[2] = post;
					continue;
				}


				for (int i = 0; i < rank.length; i++) {
					if(rank[i].getClickCount() < post.getClickCount() ){
						rank[i] = post;
						break;
					}
				}


				if(rank[0].getId() == rank[1].getId()){
					rank[1] = post;
				}else if(rank[1].getId() == rank[2].getId()){
					rank[2] = post;
				}else if(rank[0].getId() == rank[2].getId()){
					rank[2] = post;
				}

			}
		}

		list = Arrays.asList(rank);

		log.info("end DB hot post");
		return list;
	}


	private List<PostClass> getHotPostByCache() {
		log.info("start getHotPostByCache");
		List<PostClass> list = null;
		// メモリキャッシュでデータを取得
		MemcacheService syncCache = getMemcache("HOT_POST");


		final String timeKey = "LAST_UP_TIME";
		Long now = System.currentTimeMillis();
		Long lasttime = (Long)syncCache.get(timeKey);
		long cacheTime = 1000 * 60 * 60;
//		long cacheTime = 1000;

		PostClass rank1 = (PostClass)syncCache.get("1");
		PostClass rank2 = (PostClass)syncCache.get("2");
		PostClass rank3 = (PostClass)syncCache.get("3");

		if(rank1 == null || rank2 == null || rank3 == null || lasttime == null ||  lasttime < ( now - cacheTime) ){

			syncCache.clearAll();
			log.info("clearAll Hot Post MemcacheService");

			log.info("add Hot Post MemcacheService");
			list = getHotPost();

			if(list.size() >= 3){

				syncCache.put("1", list.get(0));
				syncCache.put("2", list.get(1));
				syncCache.put("3", list.get(2));

				syncCache.put(timeKey, now);
			}

		}
		else{
			list = new ArrayList<PostClass>();

			list.add(rank1);
			list.add(rank2);
			list.add(rank3);

		}

		log.info("end getHotPostByCache");
		return list;
	}




	private void updateCachePost(PostClass p) {
		// メモリキャッシュでデータを取得
		MemcacheService syncCache = getMemcache(PostClass.class.getName());
		if(p != null){
			syncCache.put(p.getId(), p);
		}
	}


	private UI getUI() {
		// メモリキャッシュでデータを取得
		MemcacheService syncCache = getMemcache(UI.class.getName());

		UI ui = (UI)syncCache.get(UI.UI_CACHE_KEY);
		if(ui != null){
			return ui;
		}else{
			List<CategoryClass> categoryList = categoryRepo.findAll();
			ui = new UI(categoryList);
			return ui;
		}
	}

	private boolean isUrlOnTarget(String url) {
		// メモリキャッシュでデータを取得
		MemcacheService syncCache = getMemcache(RssReader.class.getName());
		boolean ret = true;

		String cacheUrl =(String)syncCache.get(url);
		if( StringUtils.isEmpty(cacheUrl) && targetRepo.findByUrl(url) == null){
			ret = false;
		}
		syncCache.put(url, "0");
		return ret;

	}
}
