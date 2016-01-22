package com.s7soft.gae.newsmaker.dao;

import java.util.List;

import com.s7soft.gae.newsmaker.bean.RssMaster;

public interface NewsMakerDao {

	public List<RssMaster> getRssList() throws Exception;

	public void insertNews(String date) throws Exception;
}
