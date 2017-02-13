package com.dolziggu.monitor.vo;

import java.math.BigDecimal;

public class ItemMonitoringUserVO {
	private String siteId;
	private String siteSubId;
	private String itemId;
	private String itemNm;
	private BigDecimal itemAmt;
	private String userId;
	
	private BigDecimal itemInterval;
	
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getSiteSubId() {
		return siteSubId;
	}
	public void setSiteSubId(String siteSubId) {
		this.siteSubId = siteSubId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemNm() {
		return itemNm;
	}
	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
	}
	public BigDecimal getItemAmt() {
		return itemAmt;
	}
	public void setItemAmt(BigDecimal itemAmt) {
		this.itemAmt = itemAmt;
	}
	public BigDecimal getItemInterval() {
		return itemInterval;
	}
	public void setItemInterval(BigDecimal itemInterval) {
		this.itemInterval = itemInterval;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
