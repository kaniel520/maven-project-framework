package com.ynk.db;

import com.ynk.tool.DLog;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisDB {

	private static RedisDB instance;
	private JedisPool jpool;
	private int dbIndex = 0;
	
	public static RedisDB shareInstance() {
		synchronized (RedisDB.class) {
			if (instance == null)
				instance = new RedisDB();
		}
		return instance;
	}
	
	/**
	 * 初始化连接
	 * @param host
	 * @param password
	 * @param port
	 * @param dbIndex
	 */
	public void init(String host, String password, int port, int dbIndex){
		if (null == host || null == password || port == 0){
			DLog.LOG.error("xxxxxxxx init RedisDB error,param is empty");
			return;
		}
		
		this.dbIndex = dbIndex;
		jpool = getJRedisPool(host, password, port, dbIndex);
	}
	
	/**
	 * 初始化连接池
	 * 
	 * @author Kaniel
	 * @param host
	 * @param port
	 * @param dbIndex
	 * @return 
	 */
	public static JedisPool getJRedisPool(String host, String password, int port, int dbIndex) {

		//DLog.LOG.info("====== log knaiel");
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(50);
		//最大连接数, 应用自己评估，不要超过ApsaraDB for Redis每个实例最大的连接数
		config.setMaxTotal(100);
		config.setTestOnBorrow(false);
		config.setTestOnReturn(false);
		DLog.LOG.info("======== redisDB host="+host +" PORT="+port +" dbIndex=="+dbIndex);
		JedisPool pool = new JedisPool(config, host, port, 3000, password);
		Jedis jedis = null;
		boolean broken = false;
		try {
		    jedis = pool.getResource();
		    /// ... do stuff here ... for example
		    jedis.set("foo", "bar kaniel ok");
		    String foobar = jedis.get("foo");
		    System.out.println("k=================== redis get info :" + foobar);
		} catch(Exception e) {
			System.out.println("k=================== redis exception:" + e.toString());
		    broken = true;
		} finally {
			
			if (null != jedis){
				jedis.close();
			}
		}
		
		return pool;
	}
	
	
	/// ***************
	// Key-operation
	/// ***************
	
	/**
	 * get one key
	 * @param key
	 * @return
	 */
	public String get(String key) {
		Jedis jedis = null;
		String val = null;
		try {
			jedis = jpool.getResource();
			jedis.select(dbIndex);
			val = jedis.get(key);
		} catch (Exception e) {
			DLog.LOG.error("RedisDB get error" + e.getMessage(), e);
		} finally {
			if (null != jedis){
				jedis.close();
			}
		}
		return val;
	}
	
	/**
	 * set one key
	 * @param key
	 * @return
	 */
	public String set(String key, String value) {
		Jedis jedis = null;
		String val = null;
		try {
			jedis = jpool.getResource();
			jedis.select(dbIndex);
			val = jedis.set(key,value);
		} catch (Exception e) {
			DLog.LOG.error("RedisDB get error" + e.getMessage(), e);
		} finally {
			if (null != jedis){
				jedis.close();
			}
		}
		return val;
	}
	
	/// ***************
	// Set-operation
	/// ***************
	
	
	/// ***************
	// Hash-operation
	/// ***************
	
	/// ***************
	// List-operation
	/// ***************
	
	/// ***************
	// Sorted Set-operation
	/// ***************
	/**
	 * 新增sorted_set
	 * 
	 * @param key
	 * @param k
	 * @param member
	 * @param seconds
	 */
	public void addZSet(byte[] key, double k, byte[] member) {
		Jedis jedis = null;
		try {
			jedis = jpool.getResource();
			jedis.select(this.dbIndex);
			jedis.zadd(key, k, member);
		} catch (Exception e) {
			DLog.LOG.error("RedisDB add error" + e.getMessage(), e);
		} finally {
			if (jedis != null)
				jedis.close();
		}
	}

	/**
	 * 自增sorted_set
	 * 
	 * @param key
	 *            键值
	 * @param k
	 *            score
	 * @param member
	 *            成员
	 */
	public void addIncr(String key, double k, String member) {
		Jedis jedis = null;
		try {
			jedis = jpool.getResource();
			jedis.select(this.dbIndex);
			jedis.zincrby(key, k, member);
		} catch (Exception e) {
			DLog.LOG.error("RedisDB add error" + e.getMessage(), e);
		} finally {
			if (jedis != null){
				jedis.close();
			}
				
			
		}
	}
}












