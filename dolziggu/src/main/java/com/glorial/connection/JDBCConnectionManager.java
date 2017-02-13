package com.glorial.connection;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCConnectionManager extends ConnectionManagerImpl{
	public ConnectionKey beginTx() throws Exception{
		Connection conn=null;
		try{
			conn=getConnection();
			conn.setAutoCommit(false);
		}catch(SQLException ex){
			if(conn!=null)try{conn.close();}catch(Exception e){}
			throw ex;
		}
		
		ConnectionKey ckey=new ConnectionKey();
		connections.put(ckey,conn);
		return ckey;
	}
	
	public void endTx(ConnectionKey ckey) throws Exception{
		
		Connection conn=(Connection)this.connections.remove(ckey);
		
		if(conn!=null){
			try{
				conn.close();
			}catch(SQLException ex){
				throw ex;
			}
		}
	}
	
	public void commitTx(ConnectionKey ckey) throws Exception{
		Connection conn=(Connection)connections.get(ckey);
		
		if(conn!=null){
			try{
				conn.commit();
			}catch(SQLException ex){
				throw ex;
			}
		}
	}
	
	public void rollbackTx(ConnectionKey ckey) throws Exception{
		Connection conn=(Connection)connections.get(ckey);
		
		if(conn!=null){
			try{
				conn.rollback();
			}catch(SQLException ex){
				throw ex;
			}
		}
		
	}
	
	public void closeConnection(Object connection) throws Exception{
		Connection conn=(Connection)connection;
		
		if(conn!=null){
			try{
				conn.close();
			}catch(SQLException ex){
				throw ex;
			}
		}
	}
	
	public Connection getConnection(){
		return null;
	}
	
	public Connection getConnection(ConnectionKey ckey){
		return (Connection)connections.get(ckey);
	}
}
