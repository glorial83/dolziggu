package com.glorial.connection;

public interface IConnectionManager {
	public ConnectionKey beginTx() throws Exception;
	public void endTx(ConnectionKey ckey) throws Exception;
	public void commitTx(ConnectionKey ckey) throws Exception;
	public void rollbackTx(ConnectionKey ckey) throws Exception;
	public void closeConnection(Object connection) throws Exception;
	
	public Object getConnection();
	public Object getConnection(ConnectionKey ckey);
}
