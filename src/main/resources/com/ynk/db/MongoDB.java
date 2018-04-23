package com.ynk.db;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.ynk.tool.DLog;

public class MongoDB {

	
	private static MongoDB instance;
	private MongoClient mongoClient;
	
	public static MongoDB shareInstance() {
		synchronized (RedisDB.class) {
			if (instance == null)
				instance = new MongoDB();
		}
		return instance;
	}
	
	public void initURI(String addr) {
		
		if (null == addr){
			DLog.LOG.error("xxxxxxxx init Mongodb error,param is empty");
			return;
		}
		
		mongoClient = createMongoDBClientWithURI(addr);
	}
	
	public static MongoClient createMongoDBClientWithURI(String addr) {
    	DLog.LOG.info("================== init mongodb:" + addr);
    	if (null == addr || addr.length() == 0){
    		System.out.println("xxxx createMongoDBClientWithURI mongo addr empty");
    		return null;
    	}
    	MongoClientURI connectionString = new MongoClientURI(addr);
    	return new MongoClient(connectionString);
    }
	
	/**
	 * 获取数据库
	 * @param databaseName
	 * @return
	 */
	public MongoDatabase getDatabase(String databaseName){
		return mongoClient.getDatabase(databaseName);
	}
}
