package com.shop.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RidesUtils {
	private static JedisPool pool=null;
	
	//静态方法执行一遍
	static{
		InputStream in = RidesUtils.class.getClassLoader().getResourceAsStream("redis.propreties");
		Properties pro=new Properties();
		try {
			JedisPoolConfig  poolConfig=new JedisPoolConfig();
			pro.load(in);
		poolConfig.setMaxIdle(Integer.parseInt(pro.getProperty("MaxIdle")));
		poolConfig.setMinIdle(Integer.parseInt(pro.getProperty("MinIdle")));
		poolConfig.setMinIdle(Integer.parseInt(pro.getProperty("MaxTotal")));
		pool=new JedisPool(poolConfig, pro.getProperty("url"), Integer.parseInt(pro.getProperty("prot")));
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//从连接池当中获得到 返回jedies
  public static Jedis getjedis(){
	return pool.getResource();
	  
  }
}
