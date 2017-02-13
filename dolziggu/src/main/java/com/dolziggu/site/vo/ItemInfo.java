package com.dolziggu.site.vo;

import java.math.BigDecimal;

public class ItemInfo {
	private String siteId			;
	private String siteSubId		;
	private String itemId			;
	private String pageUrl			;
	private String itemNm			;
	private String itemImage		;
	private String itemAmtFormatted	;
	private BigDecimal itemAmt		;
	private String saleAmtFormatted	;
	private BigDecimal saleAmt		;
	private BigDecimal salePercent	;
	private String lowestNewAmt		;
	private String lowestUsedAmt	;
	
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public String getItemNm() {
		return itemNm;
	}
	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
	}
	public String getItemImage() {
		return itemImage;
	}
	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}
	public String getItemAmtFormatted() {
		return itemAmtFormatted;
	}
	public void setItemAmtFormatted(String itemAmtFormatted) {
		this.itemAmtFormatted = itemAmtFormatted;
	}
	public BigDecimal getItemAmt() {
		return itemAmt;
	}
	public void setItemAmt(BigDecimal itemAmt) {
		this.itemAmt = itemAmt;
	}
	public String getSaleAmtFormatted() {
		return saleAmtFormatted;
	}
	public void setSaleAmtFormatted(String saleAmtFormatted) {
		this.saleAmtFormatted = saleAmtFormatted;
	}
	public BigDecimal getSaleAmt() {
		return saleAmt;
	}
	public void setSaleAmt(BigDecimal saleAmt) {
		this.saleAmt = saleAmt;
	}
	public BigDecimal getSalePercent() {
		return salePercent;
	}
	public void setSalePercent(BigDecimal salePercent) {
		this.salePercent = salePercent;
	}
	public String getLowestNewAmt() {
		return lowestNewAmt;
	}
	public void setLowestNewAmt(String lowestNewAmt) {
		this.lowestNewAmt = lowestNewAmt;
	}
	public String getLowestUsedAmt() {
		return lowestUsedAmt;
	}
	public void setLowestUsedAmt(String lowestUsedAmt) {
		this.lowestUsedAmt = lowestUsedAmt;
	}
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
}
