package com.Util;

import com.model.User;

public class test {
	public static void main(String[] args) {
		User user = new User();
		if(user.equals(null)){
			System.out.println(true);
		}else if(user.getUName().equals(null)){
			System.out.println(false);
		}
	}

}
