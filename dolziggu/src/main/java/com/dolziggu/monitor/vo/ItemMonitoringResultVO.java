package com.dolziggu.monitor.vo;

import java.math.BigDecimal;
import java.util.Date;

public class ItemMonitoringResultVO {
	private String siteId;
	private String siteSubId;
	private String itemId;
	private String itemNm;
	private Date monViewpoint;
	private BigDecimal monAmt;
	private String monUrl;
	
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
	public Date getMonViewpoint() {
		return monViewpoint;
	}
	public void setMonViewpoint(Date monViewpoint) {
		this.monViewpoint = monViewpoint;
	}
	public BigDecimal getMonAmt() {
		return monAmt;
	}
	public void setMonAmt(BigDecimal monAmt) {
		this.monAmt = monAmt;
	}
	public String getMonUrl() {
		return monUrl;
	}
	public void setMonUrl(String monUrl) {
		this.monUrl = monUrl;
	}
}
