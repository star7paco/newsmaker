package com.s7soft.gae.news.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.s7soft.gae.news.domain.CategoryClass;

public class UI implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<CategoryClass> menus = new ArrayList<CategoryClass>();

	public static final String UI_CACHE_KEY = "UI_CACHE_KEY";

	public UI(List<CategoryClass> category){
		this.menus = category;
	}
	public List<CategoryClass> getMenus() {
		return menus;
	}
}
