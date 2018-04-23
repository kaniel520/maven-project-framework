package com.ynk.tool;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 加载配置文件
 * @author kaniel
 * 2017-6-26
 */
public class PropertyConfig {
	protected static final Logger log = LoggerFactory.getLogger(PropertyConfig.class);
	private static Properties prop = new Properties();
	
	/**
	 * 加载配置文件    
	 * linux下的路径不同 
	 * @throws IOException 
	 */
	public static void loadProper(String configPath) throws Exception{
		if (null == configPath || configPath.length() == 0){
			log.error("xxxxxxxx config property file path empty");
			return;
		}
		
		log.info("begin load property config");
		String system_name = System.getProperty("os.name");
		log.info("current System name:" + system_name);
		InputStream is =null;
		try {
			if (system_name != null && system_name.toLowerCase().indexOf("linux") != -1) {
				//linux系统
				is = new FileInputStream(configPath + "config.properties");
				prop.load(is);
			} else {
				is = PropertyConfig.class.getClassLoader().getResourceAsStream("config.properties");
				prop.load(is);
			}
			log.debug("PropertyConfig load finished!");
		} catch (Exception e) {
			throw e;
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}
	
	/**
	 * 获取配置信息
	 * @param key
	 * @return
	 */
	public static String getProper(String key){
		return prop.getProperty(key);
	}
	

	public static int getIntProper(String key){
		String temp = prop.getProperty(key);
		int intValue = 0;
		if (null == temp){
			return intValue;
		}
		
		try{
			intValue = Integer.parseInt(temp);
		}catch(Exception e){
			log.error("xxxxxxxxxxx convert error" + e.toString());
		}
		
		return intValue;
		
	}
	
	/**
	 * 判断是否包含主键
	 * @param key
	 * @return
	 */
	public static boolean hasProper(String key){
		return prop.containsKey(key);
	}

}
