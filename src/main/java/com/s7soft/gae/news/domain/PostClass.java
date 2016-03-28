package com.s7soft.gae.news.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

import com.google.appengine.api.datastore.Text;

@Entity
@Component
public class PostClass {

	@Id
	@GeneratedValue
	private Long id;

	private Long targetId;
	private String url;
	private String title;
	private Text body;

	private String originalTitle;
	private Text originalBody;

	private Date date;
	private int status;
	private int clickCount;



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
			return body.getValue();
		}else{
			return null;
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
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





}
