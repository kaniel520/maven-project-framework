package com.ynk.dao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.ynk.db.MongoDB;
import com.ynk.db.MySQLDB;
import com.ynk.tool.MySQLTool;

public class UserDao {

	public static String getMysqltest(){
		
		String info = "";
		
		try {
			Connection cnn = MySQLDB.shareInstance().getConn();
			String sql = "SELECT * FROM " + "test" + " WHERE id=? ;";
			info = MySQLTool.queryForString(cnn, sql, 1);
			
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}
	
public static String getMongodbTest(){
		
		String info = "";
		
		MongoCollection<Document> col = MongoDB.shareInstance().getDatabase("solo_db_test").getCollection("kk");
	
		
		FindIterable<Document> doc = col.find().limit(1);
		
		info = doc.first().get("img").toString();
		return info;
	}
}
