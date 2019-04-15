package com.wlk.util;

/**
 * 返回json数据
 */

import com.wlk.domain.Accounts;

import java.io.Serializable;

public class JsonUtil <T>  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1626793180717240861L;
	private Integer state;
	private String message;
	private T data;
	
	public JsonUtil() {
		super();
	}
	
	public JsonUtil(Integer state) {
		super();
		setState(state);
	}

	public JsonUtil(Integer state, String message) {
		this(state);
		setMessage(message);
	}
	public JsonUtil(Integer state, Exception e) {
		this(state,e.getMessage());
	}
	public JsonUtil(Integer state, T data) {
		this(state);
		setData(data);
	}

	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "ResponseResult [state=" + state + ", message=" + message + ", data=" + data + "]";
	}
	
}
