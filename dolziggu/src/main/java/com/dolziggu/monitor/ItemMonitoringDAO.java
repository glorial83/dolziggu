package com.dolziggu.monitor;

import java.util.List;

import com.dolziggu.monitor.vo.ItemMonitoringResultVO;
import com.dolziggu.monitor.vo.ItemMonitoringUserVO;
import com.dolziggu.monitor.vo.ItemMonitoringVO;
import com.glorial.connection.ConnectionKey;
import com.glorial.dao.BaseDaoImpl;

public class ItemMonitoringDAO extends BaseDaoImpl{

	/**
	 * 모니터링 대상 조회
	 * @return
	 */
	public List<ItemMonitoringVO> getItemMonitoringList(){
		return (List<ItemMonitoringVO>)this.doSelectList("monitor.getItemMonitoringList");
	}
	
	/**
	 * 모니터링 요청 사용자 조회
	 * @param itemNm
	 * @return
	 */
	public List<ItemMonitoringUserVO> getItemMonitoringUserList(String itemNm){
		return (List<ItemMonitoringUserVO>)this.doSelectList("monitor.getItemMonitoringUserList", itemNm);
	}
	
	/**
	 * 모니터링 대상 추가
	 * @param ckey
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public int createItemMonitoring(ConnectionKey ckey, ItemMonitoringVO record) throws Exception{
		return this.doInsert(ckey, "monitor.createItemMonitoring", record);
	}
	
	/**
	 * 모니터링 대상 추가 사용자 등록
	 * @param ckey
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public int createItemMonitoringUser(ConnectionKey ckey, ItemMonitoringUserVO record) throws Exception{
		return this.doInsert(ckey, "monitor.createItemMonitoringUser", record);
	}
	
	/**
	 * 모니터링 결과 등록
	 * @param ckey
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public int createItemMonitoringResult(ConnectionKey ckey, ItemMonitoringResultVO record) throws Exception{
		return this.doInsert(ckey, "monitor.createItemMonitoringResult", record);
	}
}
