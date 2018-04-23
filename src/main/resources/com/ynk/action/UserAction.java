package com.ynk.action;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSONObject;
import com.ynk.dao.UserDao;
import com.ynk.db.RedisDB;
import com.ynk.tool.DLog;


@Path("/user")
public class UserAction extends BaseAction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public String login(@QueryParam("param") String param) {
	
		System.out.println("======= hello:" + param);
		JSONObject result = new JSONObject();
		result.put("hello", "hello kaniel");
		result.put("age", 100);
		return result.toString(); 
	}
	
	
	
}
