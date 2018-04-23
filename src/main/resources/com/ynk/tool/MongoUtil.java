package com.ynk.tool;

import java.lang.reflect.Field;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * Mongo数据格式化工具类
 * 
 * @author zhou 2012-11-21
 */
public class MongoUtil {

	/**
	 * 将对象转换为 DBObject
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static DBObject convertDBObject(Object obj) throws Exception {
//		return (DBObject) JSON.parse(Utils.object2JSON(obj));
		return null;
	}

	/**
	 * 将对象属性转换为DBobject 作为查询
	 * 
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static DBObject convertFieldDBObject(Class<?> clazz) throws Exception {
		Field[] fields = clazz.getDeclaredFields();
		DBObject ref = new BasicDBObject();
		for (Field field : fields) {
			ref.put(field.getName(), "");
		}
		return ref;
	}
}
