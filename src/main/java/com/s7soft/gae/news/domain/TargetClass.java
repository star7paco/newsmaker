package com.s7soft.gae.news.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

import com.google.appengine.api.datastore.Text;

@Entity
@Component
public class TargetClass{

	// TODO ステータスの定義作成。
	@Id
	@GeneratedValue
	private Long id;

	private Long categoryId;

	private String url;
	private String title;

	private String imgurl;
	private Text body;

	private Date date;
	private int status;

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


	public TargetClass() {

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
			return null;
		}
	}

	public void setBody(Text body) {
		this.body = body;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}


}