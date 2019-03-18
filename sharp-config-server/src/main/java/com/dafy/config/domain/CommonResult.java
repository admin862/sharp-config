package com.dafy.config.domain;

import com.alibaba.fastjson.JSON;

import javax.swing.plaf.PanelUI;

/**
 * 操作的通用结果
 * @author 言枫
 * @time 2016年12月29日
 */
public class CommonResult {

	public static final String PARAMETER_ERROR = "参数错误";

	public static final String OPERATE_SUCCESS = "操作成功";

	public static final String OPERATE_FAIL = "操作失败";

	/** 返回的操作码    0-成功  **/
	private int code;
	/** 成功或者是不返回的信息**/
	private String msg;
	/** 封装的数据信息 **/
	private Object data="";
	
	public CommonResult(){
		
	}

	public CommonResult(int code,String msg){
		this.code=code;
		this.msg=msg;
	}

	public static CommonResult newResult(int code,String msg){
		CommonResult result = new CommonResult(code,msg);
		return result;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}


	public String toJSONString(){
		return JSON.toJSONString(this);
	}

	@Override
	public String toString() {
		return "CommonResult{" +
				"code=" + code +
				", msg='" + msg + '\'' +
				", data=" + data +
				'}';
	}
}
