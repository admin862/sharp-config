package com.dafy.config.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;

public class CommonUtils {

	private CommonUtils(){}

	public static String md5(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}

	public static String getDiffVersionHtmlData(String originData,String newData){
		DiffMatchPatch diff = new DiffMatchPatch();
		return diff.diff_prettyHtml(diff.diff_main(originData,newData));
	}
	
	public static void main(String[] args) {
		String[] arr = new String[]{"yanhai","huwenhu","lijinming","heweiming","zhanghui","caozhengsheng","jusongsong","yuanhengrun","liuxin","xuliangliang","liupeng","jinxin"};

		for(String name : arr){
			String sql = "INSERT INTO tbUser(strUsername,strPassword,strNickname,strMobile,strEmail,nEnable,dtCreateTime) VALUES ('%s','%s','%s','','%s',1,NOW()); ";
			System.out.println(String.format(sql,name,md5("123456{" + name + "}"),name,name + "@dafy.com"));
		}
	}

}
