package com.xn.bmpos.manager.bmposmanager.controller;


import javax.servlet.http.HttpSession;

public class BaskController {
	protected Integer getUidfromSession(HttpSession session) {
		return  Integer.valueOf(session.getAttribute("userId").toString());
	}
}
