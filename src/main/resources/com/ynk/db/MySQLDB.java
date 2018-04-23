package com.ynk.db;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.ynk.tool.DLog;

public class MySQLDB {

	private ComboPooledDataSource cpds;
	private boolean bClosed = false;
	private static MySQLDB instance  = null;
	private Timer timer;
	public class SqlCnnTimer extends TimerTask{
		/**
		 * 线程方法
		 */
		public void run() {
			try {

				if (null != cpds){
					System.out.println("=========== 定时器检测 Mysql 连接最大总数:" + cpds.getMaxPoolSize() + 
							" 最小连接数：" + cpds.getMinPoolSize() + 
							" 正在使用连接数:" + cpds.getNumBusyConnections() + 
							" 空闲连接数" + cpds.getNumIdleConnections() + 
							" 总连接数" + cpds.getNumConnections());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private MySQLDB(){

		timer = new Timer();
		startSqlConnCount();

	}
	
	public void startSqlConnCount() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE,  0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		timer.scheduleAtFixedRate(new SqlCnnTimer(), cal.getTime(), 1000L * 60);
		
	}
	
	public static MySQLDB shareInstance() {
		synchronized (RedisDB.class) {
			if (instance == null)
				instance = new MySQLDB();
		}
		return instance;
	}
	
	public void init(String url, String name, String password){
		if (null == url || null == name || null == password){
			DLog.LOG.error("xxxxxxxx init MySQL error,param is empty");
			return;
		}
		
		try {
			initCpds(url, name, password);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 初始化连接池
	 * 
	 * @author Kaniel
	 * @throws PropertyVetoException
	 * @since 2.94
	 */
	private void initCpds(String url, String name, String password) throws PropertyVetoException {
		synchronized (this) {

			DLog.LOG.info("============== init mysql Cpds【开始】.... url:" + url + " name:" + name + " pwd:" + password);

			cpds = new ComboPooledDataSource();
		    cpds.setDriverClass("com.mysql.cj.jdbc.Driver"); // 加载驱动
			cpds.setJdbcUrl(url); // 设置连接URL
			cpds.setUser(name); // 设置用户名
			cpds.setPassword(password); // 密码
			cpds.setAutoCommitOnClose(false); // 是否自动提交

			cpds.setMaxPoolSize(300);
			cpds.setMinPoolSize(25);

			cpds.setTestConnectionOnCheckin(true);
			cpds.setIdleConnectionTestPeriod(120);
			cpds.setPreferredTestQuery("SELECT 1;");

			bClosed = false;
			DLog.LOG.info("========= end init mysql.");
		}
	}

	/**
	 * 获取连接
	 * 
	 * @return
	 * @throws PropertyVetoException
	 * @throws SQLException
	 */
	public synchronized Connection getConn() throws PropertyVetoException, SQLException {
		if (cpds == null) {
			DLog.LOG.error("xxxxxxxxxxxxxx mysql connection not init");
		}

		if (bClosed) {
			return null;
		}
		return cpds.getConnection();
	}
	
	public void close(){
		if (cpds != null) {
			bClosed = true;
			cpds.close();
		}
	}
}
