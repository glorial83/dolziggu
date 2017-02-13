package com.dolziggu.monitor.vo;

import java.math.BigDecimal;

public class ItemMonitoringVO {
	private String siteId;
	private String siteSubId;
	private String itemId;
	private String itemNm;
	
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
}
