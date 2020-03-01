package com.song.constants;

public interface Constants {
	// 响应code
	String HTTP_RES_CODE_NAME = "code";
	// 响应msg
	String HTTP_RES_CODE_MSG = "msg";
	// 响应data
	String HTTP_RES_CODE_DATA = "data";
	// 响应请求成功
	String HTTP_RES_CODE_200_VALUE = "success";
	// 系统错误
	String HTTP_RES_CODE_500_VALUE = "error";
	// 响应请求成功code
	Integer HTTP_RES_CODE_200 = 200;
	// 系统错误
	Integer HTTP_RES_CODE_500 = 500;

	String SMS_MAIL = "email";
	// 会员token
	String TOKEN_MEMBER ="TOKEN_MEMBER";
	// 用户有效期 90天
	Long TOKEN_MEMBER_TIME =(long) (60*60*24*90);
}
