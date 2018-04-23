package com.ynk.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ynk.db.MongoDB;
import com.ynk.db.MySQLDB;
import com.ynk.db.RedisDB;
import com.ynk.tool.DLog;
import com.ynk.tool.PropertyConfig;


public class ContextListener implements ServletContextListener{

	public void contextInitialized(ServletContextEvent event) {
		
		System.out.println("========= 监听器(初始化项目)"); 
		DLog.LOG.debug("======= test logger");   
		
		try {
			PropertyConfig.loadProper("/kaniel/server/config/");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//init redis database
		String redisIP = PropertyConfig.getProper("db.redis.ip");
		String redisPassword = PropertyConfig.getProper("db.redis.password");
		int port = PropertyConfig.getIntProper("db.redis.port");
		int dbIndex = PropertyConfig.getIntProper("db.redis.index");
		RedisDB.shareInstance().init(redisIP, redisPassword, port, dbIndex);
		// init mongodb
		String mongoAddr = PropertyConfig.getProper("db.mongodb.addr");
		MongoDB.shareInstance().initURI(mongoAddr);
		// init mysql
		String url = PropertyConfig.getProper("db.mysql.url");
		String name = PropertyConfig.getProper("db.mysql.name");//"lkm_mysql";
		String password = PropertyConfig.getProper("db.mysql.password");//"Lkmmysql2017";
		MySQLDB.shareInstance().init(url, name, password);  
		
		
//		try { 
//			
//			initLogger();
//			
//			log.info("ContextListener Initializ Start ...");
//			System.out.println("========= 监听器(初始化项目)");
//			Config.loadProper(); // 属性文件加载
//			DFAFilter.init();
//			//FriendLogDB.startThread(); 		// 关注日志
//			DBExecuteThread.start(5); 		// 日志
//			AsyncClientSMS.startService(); 	// SMS短信
//			OnlineCountSchedule.getInstance().startOnlineNumTask(); // 统计
//			OnlineCountSchedule.getInstance().startRandOnlineNumTask(); // 随机聊当前人数统计
//			
//			SoloMongoDB.initByURI(SoloGlobalConfig.mongoAddr()); //初始化MongoDb
//			log.info("ContextListener Initializ End ...");
//		} catch (Exception ex) {
//			log.error(ex.getMessage(), ex);
//		}
	}

	public void contextDestroyed(ServletContextEvent event) {
		
		System.out.println("========= 监听器(项目销毁)");
//		log.error("ContextListener Destroyed ..." + new Date() + ".");
//
//		try {
//			GoodsCache.getInstance().stopThread();
//			ConfigCache.getInstance().stopThread();
//			FriendLogDB.stopThread();
//			MbmSchedule.stopSchedule();
//			OnlineCountSchedule.getInstance().stopOnlineNumTask();
//			OnlineCountSchedule.getInstance().stopRandOnlineNumTask();
//			
//			com.bkq.mkxlib.db.MySqlConn.getInstance().close();
//			MongoDB.close();
//			SoloMongoDB.close();//add by 0811
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
