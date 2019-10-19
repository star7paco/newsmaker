package com.s7soft.gae.news.admin;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class AdminUtil {
	public static final String ADMIN_ID = "xxxxxx@gmail.com";


	public static boolean isAdminUser(){
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		if( user != null && ADMIN_ID.equals(user.getEmail()) ){
			return true;
		}

		return false;
	}

}
