package com.dolziggu.monitor.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dolziggu.monitor.ItemMonitoringDAO;
import com.dolziggu.monitor.vo.ItemMonitoringResultVO;
import com.dolziggu.monitor.vo.ItemMonitoringUserVO;
import com.dolziggu.monitor.vo.ItemMonitoringVO;
import com.dolziggu.site.Amazon;
import com.dolziggu.site.vo.ItemInfo;
import com.dolziggu.sms.EmailUtil;
import com.glorial.connection.ConnectionKey;
import com.glorial.service.BaseService;

public class ItemMonitoringAmazon extends BaseService implements Job {

	private static Logger _log = LoggerFactory.getLogger(ItemMonitoringAmazon.class);

	public ItemMonitoringAmazon() {
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
			ItemMonitoringDAO dao = new ItemMonitoringDAO();
			List<ItemMonitoringVO> list = dao.getItemMonitoringList();
			List<ItemMonitoringResultVO> resultList = new ArrayList<ItemMonitoringResultVO>();
			
			if(list == null){
				return;
			}
			
			//타겟 아이템 모니터링
			for(int i = 0 ; i < list.size() ; i++){
				ItemMonitoringVO itemMonitoringInfo = list.get(i);
				
				if(itemMonitoringInfo.getSiteId().equals("AMAZON")){
					//factory로 변경할 것
					Amazon amazon = new Amazon();
					
					List<ItemInfo> searchItemList = amazon.searchItem(itemMonitoringInfo.getItemNm());
					if(searchItemList != null && searchItemList.size() > 0){
						
						//검색한 아이템중 최초 1건만 지정
						ItemInfo searchItemInfo = searchItemList.get(0);
						BigDecimal saleAmt = searchItemInfo.getSaleAmt();
						if(saleAmt == null || saleAmt.compareTo(new BigDecimal("0")) == 0){
							saleAmt = searchItemInfo.getItemAmt();
						}
						
						//DB삽입용 생성
						ItemMonitoringResultVO resultInfo = new ItemMonitoringResultVO();
						resultInfo.setSiteId(searchItemInfo.getSiteId());
						resultInfo.setSiteSubId(searchItemInfo.getSiteSubId());
						resultInfo.setItemId(searchItemInfo.getItemId());
						resultInfo.setItemNm(searchItemInfo.getItemNm());
						resultInfo.setMonAmt(saleAmt);
						resultInfo.setMonUrl(searchItemInfo.getPageUrl());
						resultList.add(resultInfo);
					}
				}
			}
			
			//검색 결과 삽입
			ConnectionKey ckey = this.beginTransaction();
			try{
				if(resultList != null && resultList.size() > 0){
					int cnt = 0;
					for(int i = 0 ; i < resultList.size() ; i++){
						cnt += dao.createItemMonitoringResult(ckey, resultList.get(i));
					}
					
					this.commitTransaction(ckey);
				}
			}catch(Exception e){
				e.printStackTrace();
				this.rollbackTransaction(ckey);
			}finally{
				this.endTransaction(ckey);
			}
			
			//검색 결과를 원한 사용자들을 조회
			List emailList = new ArrayList();
			if(resultList != null && resultList.size() > 0){
				for(int i = 0 ; i < resultList.size() ; i++){
					List<ItemMonitoringUserVO> userList = dao.getItemMonitoringUserList(resultList.get(i).getItemNm());
					
					if(userList != null && userList.size() > 0){
						for(int j = 0 ; j < userList.size() ; j++){
							ItemMonitoringUserVO userInfo = userList.get(j);
							
							BigDecimal saleAmt = resultList.get(i).getMonAmt();
							BigDecimal itemAmt = userInfo.getItemAmt();
							if(saleAmt != itemAmt){
								if(saleAmt.compareTo(itemAmt) == -1){
									//더 작다면 이메일 발송
									Map emailInfo = new HashMap();
									emailInfo.put("emailId", userInfo.getUserId());
									emailInfo.put("itemNm", resultList.get(i).getItemNm());
									emailInfo.put("itemAmt", resultList.get(i).getMonAmt());
									emailInfo.put("itemUrl", resultList.get(i).getMonUrl());
									
									emailList.add(emailInfo);
								}
							}
						}
					}
				}
			}
		
		//이메일 발송
			Map defaultInfo = new HashMap();
			defaultInfo.put("title", "돌직구 아이템 모니터링 테스트");
			defaultInfo.put("sender", "wjdgodnjs@gmail.com");
			
			if(emailList != null && emailList.size() > 0){
				for(int i = 0 ; i < emailList.size() ; i++){
					Map emailInfo = (Map)emailList.get(i);
					EmailUtil.sendEmail(defaultInfo, emailInfo);
				}
			}
		}catch(JobExecutionException jb){
			throw jb;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
