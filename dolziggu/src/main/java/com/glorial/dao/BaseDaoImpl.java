package com.glorial.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.glorial.connection.ConnectionKey;
import com.glorial.connection.ConnectionManagerFactory;
import com.glorial.connection.IConnectionManager;
import com.glorial.dao.IBaseDAO;

public abstract class BaseDaoImpl implements IBaseDAO {
	private IConnectionManager iConnectionManager = ConnectionManagerFactory.getConnectionManager("mybatis");
	
	public Object getConnection(){
		return iConnectionManager.getConnection();
	}
	
	public Object getConnection(ConnectionKey ckey){
		return iConnectionManager.getConnection(ckey);
	}
	
	public int doDelete(ConnectionKey ckey, String query, Object record) {
		return ((SqlSession)getConnection(ckey)).delete(query, record);
	}

	public int doInsert(ConnectionKey ckey, String query, Object record) {
		return ((SqlSession)getConnection(ckey)).insert(query, record);
	}
	
	public int doUpdate(ConnectionKey ckey, String query, Object record) {
		return ((SqlSession)getConnection()).update(query, record);
	}
	
	public Object doSelect(ConnectionKey ckey, String query){
		return ((SqlSession)getConnection()).selectOne(query);
	}
	
	public Object doSelect(String query){
		SqlSession sqlSession = (SqlSession)getConnection();
		Object obj = sqlSession.selectOne(query);
		sqlSession.close();
		
		return obj;
	}
	
	public Object doSelect(String query, Object record){
		SqlSession sqlSession = (SqlSession)getConnection();
		Object obj = sqlSession.selectOne(query);
		sqlSession.close();
		
		return obj;
	}
	
	public List doSelectList(String query) {
		SqlSession sqlSession = (SqlSession)getConnection();
		List list = sqlSession.selectList(query);
		sqlSession.close();
		
		return list;
	}

	public List doSelectList(String query, Object record) {
		SqlSession sqlSession = (SqlSession)getConnection();
		List list = sqlSession.selectList(query, record);
		sqlSession.close();
		
		return list;
	}
}
