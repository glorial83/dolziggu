package com.glorial.service;

import com.glorial.connection.ConnectionKey;
import com.glorial.connection.ConnectionManagerFactory;
import com.glorial.connection.IConnectionManager;

public abstract class BaseService {
	IConnectionManager iConnectionManager = null; 
	
	public BaseService(){
		iConnectionManager = ConnectionManagerFactory.getConnectionManager("mybatis");
	}
	
	protected ConnectionKey beginTransaction() throws Exception{
		return iConnectionManager.beginTx();
	}
	
	protected void endTransaction(ConnectionKey ckey) throws Exception{
		iConnectionManager.endTx(ckey);
	}
	
	protected void commitTransaction(ConnectionKey ckey) throws Exception{
		iConnectionManager.commitTx(ckey);
	}
	
	protected void rollbackTransaction(ConnectionKey ckey) throws Exception{
		iConnectionManager.rollbackTx(ckey);
	}
}
