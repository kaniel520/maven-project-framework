package com.ynk.action;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

public class BaseAction implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Context
	protected HttpServletRequest request;
	@Context
	protected HttpServletResponse response;

}
