package com.song.utils;

import java.util.UUID;

import com.song.constants.Constants;

public class TokenUtils {

	 public static String getMemberToken(){
		 return Constants.TOKEN_MEMBER+"-"+UUID.randomUUID();
	 }
	
}
