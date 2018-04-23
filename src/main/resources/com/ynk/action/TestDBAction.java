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

@Path("/testdb")
public class TestDBAction extends BaseAction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8166065134096337905L;

	@GET
	@Path("/redis")
	@Produces(MediaType.APPLICATION_JSON)
	public String redis(@QueryParam("name") String s){
		DLog.LOG.info("======= redis:");
		long beginTime = System.currentTimeMillis();
		JSONObject result = new JSONObject();
		if (null != s){
			RedisDB.shareInstance().set("testRedis", s);
		}
		String name = RedisDB.shareInstance().get("testRedis");
		result.put("name", name);
		result.put("age", 100);
		
		long endTime = System.currentTimeMillis();
		DLog.LOG.info("==== cost time:" + (endTime - beginTime));
		return result.toString();
	}
	
	@GET
	@Path("/mysql")
	@Produces(MediaType.APPLICATION_JSON)
	public String mysql(){
		DLog.LOG.info("======= mysql:");
		long beginTime = System.currentTimeMillis();
		JSONObject result = new JSONObject();
		String name = UserDao.getMysqltest();
		result.put("hello", name);
		result.put("age", 100); 
		long endTime = System.currentTimeMillis();
		DLog.LOG.info("==== cost time:" + (endTime - beginTime));
		return result.toString();
	}
	
	@GET
	@Path("/mongodb")
	@Produces(MediaType.APPLICATION_JSON)
	public String mongodb(){
		DLog.LOG.info("======= mongodb:");
		long beginTime = System.currentTimeMillis();
		JSONObject result = new JSONObject();
		String name = UserDao.getMongodbTest();
		result.put("hello", name);
		result.put("age", 100); 
		long endTime = System.currentTimeMillis();
		DLog.LOG.info("==== cost time:" + (endTime - beginTime));
		return result.toString();
	}
}
