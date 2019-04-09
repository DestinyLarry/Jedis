package com.redis;
import java.util.Iterator;
import java.util.Set;
import redis.clients.jedis.Jedis;

public class RedisSocket {
	//redisIP
	private static String ip = "192.168.18.3";
	//redis端口
	private static int port = 6379;
	
	public static void main(String[] args)  {
		//连接本地的 Redis 服务
		Jedis  jedis = null;
		try {
			jedis = new Jedis(ip,port);
			String pingResult  = jedis.ping();
			//检查Redis服务是否正常
	        System.out.println("pingResult = 【" + pingResult + "】");
	        //获取redis中所有的key
	        Set<String> keys = jedis.keys("*"); 
	        Iterator<String> it=keys.iterator();
	        //遍历Redis中的key
	        while(it.hasNext()){   
	            String key = it.next();   
	            //System.out.println(key);   
	        }
	        if(jedis.get("number") != null) {
	        	get(jedis.get("number"));
	        }else {
	        	jedis.set("number",":1000\\r\\n");
	        }
	        if(jedis.get("test") != null ){
	        	  get(jedis.get("test"));
	        }else {
	        	jedis.set("test","+OK\\r\\n");
	        	
	        }
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			//结束时关闭
			if(jedis != null) {
				jedis.close();
			}
		}
	}
	 private static void get(String cmd) {
		 System.err.println("send command : " + cmd);
		 int index = cmd.indexOf("\\r\\n");
		 if(index == -1) {
			 System.err.println("other message : " + cmd);
			 return;
		 }
         String currentStr = cmd.substring(0, index);
         if (currentStr.startsWith("+")) {
             System.err.println("+ status reply message：" + currentStr.substring(1));
         } else if (currentStr.startsWith("-")) {
             System.err.println("- error reply message：" + currentStr.substring(1));
         } else if (currentStr.startsWith(":")) {
             System.err.println(": integer reply message：" + currentStr.substring(1));
         } else if (currentStr.startsWith("$")) {
             System.err.println("$ bulk reply message：" + currentStr.substring(1));
         } else if (currentStr.startsWith("*")) {
             System.err.println("* multi bulk reply message：" + currentStr.substring(1));
         }else {
             System.err.println("other message : " + currentStr);
         }
	 }
}
