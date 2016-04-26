package com.s7soft.gae.news.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

import com.google.appengine.api.datastore.Text;
import com.s7soft.gae.news.util.TimeUtil;

@Entity
@Component
public class PostClass implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	private Long categoryId;
	private String categoryName;
	private String url;
	private String title;
	private Text body;
	private String keywords;
	private String originalKeywords;
	private String originalTitle;
	private Text originalBody;
	private String imgurl;
	private String videourl;
	private Date date;
	private int status;
	private int clickCount;

	private String lang;

	public String getDateTime() {
		return TimeUtil.format(date);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getClickCount() {
		return clickCount;
	}

	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}

	public PostClass() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public String getStringTitle() {

		int stringlan = 30;

		if(title != null && title.trim().length() > 0){
			return title.replaceAll("<.+?>", "");
		}else{

			String body = getStringBody();
			if(body.length() > stringlan){
				return " - " + body.substring(0 , stringlan)+"...";
			}else{
				return " - " + body+"...";
			}


		}
	}

	public String getImgTag() {
		if(imgurl != null && imgurl.trim().length() > 0){
			String ret = "";
			String urls[] = imgurl.split("@");

			for(String url : urls){
				if( url==null || url.isEmpty() ){
					continue;
				}
				ret = ret + "<img class=\"img-responsive\" src=\""+url+"\">";
			}
			return ret;
		}else{
			return "";
		}
	}

	/**
	 * post-listに表示する
	 * */
	public String getListImgTag() {
		if(imgurl != null && imgurl.trim().length() > 0){
			String urls[] = imgurl.split("@");

			for(String url : urls){
				if( url==null || url.isEmpty() ){
					continue;
				}
				// Imageを１個だけ使用する
				return "<img class=\"media-object\" src=\""+url+"\">";
			}
//			return ret;
		}
		return "<img class=\"media-object\" src=\"http://www.gpn.jp/econet/images/img_noimage.jpg\">";
	}

	public String getImgTag(String className) {
		if(imgurl != null && imgurl.trim().length() > 0){
			String ret = "";
			String urls[] = imgurl.split("@");

			for(String url : urls){
				if( url==null || url.isEmpty() ){
					continue;
				}
				ret = ret + "<img class=\""+className+"\" src=\""+url+"\">";
			}
			return ret;
		}else{
			return "";
		}
	}

	public String getImgTagNoClass() {
		if(imgurl != null && imgurl.trim().length() > 0){
			String ret = "";
			String urls[] = imgurl.split("@");

			for(String url : urls){
				if( url==null || url.isEmpty() ){
					continue;
				}
				ret = ret + "<img src=\""+url+"\">";
			}
			return ret;
		}else{
			return "";
		}
	}


	public String getVideoTag() {
		if(videourl != null && videourl.trim().length() > 0){
			String ret = "";
			String urls[] = videourl.split("@");

			for(String url : urls){
				if( url==null || url.isEmpty() ){
					continue;
				}
				ret = ret + "<iframe width=\"560\" height=\"315\" src=\""+url+"\" frameborder=\"0\" allowfullscreen></iframe>";
			}
			return ret;
		}else{
			return "";
		}
	}


	public void setTitle(String title) {
		this.title = title;
	}

	public Text getBody() {
		return body;
	}

	public void setBody(Text body) {
		this.body = body;
	}

	public String getStringBody() {
		if(body!=null){
			return getImgTag() + body.getValue() + getVideoTag();
		}else{
			return "";
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public Text getOriginalBody() {
		return originalBody;
	}


	public String getStringOriginalBody() {
		if(originalBody!=null){
			return originalBody.getValue();
		}else{
			return null;
		}
	}


	public void setOriginalBody(Text originalBody) {
		this.originalBody = originalBody;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		if(imgurl == null){
			imgurl = "";
		}
		this.imgurl = imgurl;
	}

	public String getVideourl() {
		return videourl;
	}

	public void setVideourl(String videourl) {
		this.videourl = videourl;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getOriginalKeywords() {
		return originalKeywords;
	}

	public void setOriginalKeywords(String originalKeywords) {
		this.originalKeywords = originalKeywords;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}





}
