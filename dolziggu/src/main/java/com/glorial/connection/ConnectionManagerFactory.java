package com.glorial.connection;

import java.util.HashMap;

public class ConnectionManagerFactory {
	static HashMap managerMap = new HashMap();
	static{
		managerMap.put("jdbc", 		new JDBCConnectionManager());
		managerMap.put("mybatis", 	new MyBatisConnectionManager());
	}
	
	public static IConnectionManager getConnectionManager(String dataSourceType){
		return (IConnectionManager)managerMap.get(dataSourceType);
	}
}
