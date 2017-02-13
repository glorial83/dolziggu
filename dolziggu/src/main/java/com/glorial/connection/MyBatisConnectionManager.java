package com.glorial.connection;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisConnectionManager extends ConnectionManagerImpl{
	
	private static SqlSessionFactory sqlSessionFactory;
	
	public static synchronized SqlSessionFactory getSqlSessionFactory() {
		if(sqlSessionFactory == null) {
			String resource = "mybatis/myBatisConfig.xml";
			Reader reader;
			try {
				reader = Resources.getResourceAsReader( resource );
				sqlSessionFactory = new SqlSessionFactoryBuilder().build( reader );
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		return sqlSessionFactory;
	}
	
	public ConnectionKey beginTx() throws Exception{
		SqlSession sqlSession = this.getSqlSessionFactory().openSession(false);
		
		ConnectionKey ckey = new ConnectionKey();
		connections.put(ckey,	sqlSession);
		
		return ckey;
	}
	
	public void endTx(ConnectionKey ckey) throws Exception{
		
		SqlSession sqlSession = (SqlSession)this.connections.remove(ckey);
		
		if(sqlSession != null){
			sqlSession.close();
		}
	}
	
	public void commitTx(ConnectionKey ckey) throws Exception{
		SqlSession sqlSession = (SqlSession)connections.get(ckey);
		
		if(sqlSession != null){
			sqlSession.commit();
		}
	}
	
	public void rollbackTx(ConnectionKey ckey) throws Exception{
		SqlSession sqlSession = (SqlSession)connections.get(ckey);
		
		if(sqlSession != null){
			sqlSession.rollback();
		}
	}
	
	public void closeConnection(Object connection) throws Exception{
		SqlSession sqlSession = (SqlSession)connection;
		
		if(sqlSession != null){
			sqlSession.close();
		}
	}
	
	public SqlSession getConnection(){
		return this.getSqlSessionFactory().openSession();
	}
	
	public SqlSession getConnection(ConnectionKey ckey){
		return (SqlSession)connections.get(ckey);
	}
}
