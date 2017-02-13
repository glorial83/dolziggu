package com.glorial.dao;

import com.glorial.connection.ConnectionKey;

public interface IBaseDAO {
	public Object getConnection();
	public Object getConnection(ConnectionKey ckey);
	public int doInsert(ConnectionKey ckey, String query, Object record);
	public int doUpdate(ConnectionKey ckey, String query, Object record);
	public int doDelete(ConnectionKey ckey, String query, Object record);
	public Object doSelect(ConnectionKey ckey, String query);
	public Object doSelect(String query);
	public Object doSelect(String query, Object record);
	public java.util.List doSelectList(String query);
	public java.util.List doSelectList(String query, Object record);
}