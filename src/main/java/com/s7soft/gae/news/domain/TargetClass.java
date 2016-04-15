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
public class TargetClass implements Serializable{

	private static final long serialVersionUID = 1L;

	// TODO ステータスの定義作成。
	@Id
	@GeneratedValue
	private Long id;

	private Long categoryId;

	private String url;
	private String keywords;
	private String title;

	private String imgurl;
	private String videourl;
	private Text body;

	private Date date;
	private int status;

	public TargetClass() {

	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDateTime() {
		return TimeUtil.format(date);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Text getBody() {
		return body;
	}

	public String getStringBody() {
		if(body!=null){
			return body.getValue();
		}else{
			return "";
		}
	}

	public void setBody(Text body) {
		this.body = body;
	}

	public void addBody(String body) {
		this.body = new Text(getStringBody() + body);
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public void addImgurl(String imgurl) {

		if(this.imgurl != null && !this.imgurl.isEmpty() ){
			this.imgurl = this.imgurl + " @ " + imgurl;
		}else{
			setImgurl(imgurl);
		}


	}

	public String getVideourl() {
		return videourl;
	}

	public void setVideourl(String videourl) {
		this.videourl = videourl;
	}

	public void addVideourl(String videourl) {

		if(this.videourl != null && !this.videourl.isEmpty() ){
			this.videourl = this.videourl + " @ " + videourl;
		}else{
			setVideourl(videourl);
		}
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}


}