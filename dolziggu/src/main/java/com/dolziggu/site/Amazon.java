package com.dolziggu.site;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dolziggu.monitor.ItemMonitoringDAO;
import com.dolziggu.monitor.vo.ItemMonitoringVO;
import com.dolziggu.site.vo.ItemInfo;
import com.glorial.config.PropertiesFactory;
import com.glorial.util.StringUtil;

public class Amazon {
	private static final Logger logger = LoggerFactory.getLogger(Amazon.class);
	/**
		https://aws.amazon.com/ko/ tjdjfak@naver.com
		https://affiliate-program.amazon.com -> naragu-20
		https://affiliate-program.amazon.com/gp/advertising/api/registration/welcome.html
		
		api http://docs.aws.amazon.com/AWSECommerceService/latest/GSG/SubmittingYourFirstRequest.html
		
		db : dolziggu.c7c6qixn8rno.us-west-2.rds.amazonaws.com
		 	 1521
			 glorial/orgodnjs83
			 
		http://hyeonstorage.tistory.com/145 참고사이트
	 */
	
	public List<ItemInfo> searchItem(String productName) throws Exception{
		if(productName == null){
			return null;
		}
		
		String endPoint 	= PropertiesFactory.getInstance().getProperty("aws.endPoint");
		String accessKeyId 	= PropertiesFactory.getInstance().getProperty("aws.awsAccessKeyId");
		String secretKey 	= PropertiesFactory.getInstance().getProperty("aws.awsSecretKey");
		String associateTag = PropertiesFactory.getInstance().getProperty("aws.associateTag");
		
		SignedRequestsHelper helper;
		try {
			helper = SignedRequestsHelper.getInstance(endPoint,accessKeyId, secretKey);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("Service"		, "AWSECommerceService");
		params.put("Operation"		, "ItemSearch");
		params.put("SearchIndex"	, "All");
		params.put("Keywords"		, productName);
		params.put("ResponseGroup"	, "ItemAttributes,Offers,Images");
		params.put("AssociateTag"	, associateTag);
		
		logger.debug("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		
		//'Request','ItemIds','Small','Medium','Large','Offers','OfferFull','OfferSummary','OfferListings','PromotionSummary','PromotionDetails',
		//'VariationMinimum','VariationSummary','VariationMatrix','VariationOffers','Variations','TagsSummary','Tags','ItemAttributes','MerchantItemAttributes',
		//'Tracks','Accessories','EditorialReview','SalesRank','BrowseNodes','Images','Similarities','Subjects','Reviews','ListmaniaLists','SearchInside',
		//'PromotionalTag','SearchBins','AlternateVersions','Collections','RelatedItems','ShippingCharges','ShippingOptions'
		String requestUrl = helper.sign(params);
		
		Document doc = Jsoup.connect(requestUrl).get();
		
		//가격, 할인가격(세이브), 현재최저가, 역대최저가   
		
		//가격 		ItemAttributes 	--> 	itemattributes 	// listprice 		// formattedprice
		//할인가격  	Offers 			-->  	offer			// offerlisting		//	price			// formattedprice
		//할인가격(%)	Offers 			-->  	offer			// offerlisting		// percentagesaved
		
		//신품최저가  	Offers 			-->  	offersummary	// lowestnewprice		// formattedprice
		//중고최저가  	Offers 			-->  	offersummary	// lowestusedprice		// formattedprice
		
		//item 가져오기
		Elements items = doc.getElementsByTag("item");
		List<ItemInfo> itemList = new ArrayList<ItemInfo>();
		
		for(int i = 0 ; i < items.size() ; i++){
			
			ItemInfo itemInfo = new ItemInfo();
			itemInfo.setSiteId("AMAZON");
			itemInfo.setSiteSubId("US");
			
			Element item = items.get(i);
			
			Element asin = item.getElementsByTag("asin").get(0);
			itemInfo.setItemId(StringUtil.nvl(asin.html()));
			
			Element detailpageurl = item.getElementsByTag("detailpageurl").get(0);
			itemInfo.setPageUrl(StringUtil.nvl(detailpageurl.html()));
			
			Element title = item.getElementsByTag("title").get(0);
			itemInfo.setItemNm(StringUtil.nvl(title.html()));
			
			if(item.getElementsByTag("smallimage").size() > 0){
				Element image = item.getElementsByTag("smallimage").get(0).getElementsByTag("url").get(0);
				itemInfo.setItemImage(StringUtil.nvl(image.html()));
			}
			
			if(item.getElementsByTag("listprice").size() > 0){
				Element amt = item.getElementsByTag("listprice").get(0).getElementsByTag("formattedprice").get(0);
				Element amtformat = item.getElementsByTag("listprice").get(0).getElementsByTag("amount").get(0);
				itemInfo.setItemAmtFormatted(StringUtil.nvl(amtformat.html()));
				itemInfo.setItemAmt( StringUtil.parseBigDecimal(StringUtil.nvl(amt.html(),"0")) );
			}
			
			if(item.getElementsByTag("offer").size() > 0){
				if(item.getElementsByTag("offer").get(0).getElementsByTag("offerlisting").get(0).getElementsByTag("price").size() > 0){
					Element saleamtformat = item.getElementsByTag("offer").get(0).getElementsByTag("offerlisting").get(0).getElementsByTag("price").get(0).getElementsByTag("formattedprice").get(0);
					Element saleamt = item.getElementsByTag("offer").get(0).getElementsByTag("offerlisting").get(0).getElementsByTag("price").get(0).getElementsByTag("amount").get(0);
					itemInfo.setSaleAmtFormatted(StringUtil.nvl(saleamtformat.html()));
					itemInfo.setSaleAmt( StringUtil.parseBigDecimal(StringUtil.nvl(saleamt.html(),"0")) );
				}
				
				if(item.getElementsByTag("offer").get(0).getElementsByTag("offerlisting").size() > 0){
					if(item.getElementsByTag("offer").get(0).getElementsByTag("offerlisting").get(0).getElementsByTag("percentagesaved").size() > 0){
						Element salepercent = item.getElementsByTag("offer").get(0).getElementsByTag("offerlisting").get(0).getElementsByTag("percentagesaved").get(0);
						itemInfo.setSalePercent( StringUtil.parseBigDecimal(StringUtil.nvl(salepercent.html(),"0")) );
					}
				}
			}
			
			if(item.getElementsByTag("offersummary").size() > 0){
				if(item.getElementsByTag("offersummary").get(0).getElementsByTag("lowestnewprice").size() > 0){
					Element lowestnew = item.getElementsByTag("offersummary").get(0).getElementsByTag("lowestnewprice").get(0).getElementsByTag("formattedprice").get(0);
					itemInfo.setLowestNewAmt(lowestnew.html());
				}
				
				if(item.getElementsByTag("offersummary").get(0).getElementsByTag("lowestusedprice").size() > 0){
					Element lowestused = item.getElementsByTag("offersummary").get(0).getElementsByTag("lowestusedprice").get(0).getElementsByTag("formattedprice").get(0);
					itemInfo.setLowestUsedAmt(lowestused.html());
				}
			}
			
			itemList.add(itemInfo);
		}
		
		ItemMonitoringDAO dao = new ItemMonitoringDAO();
		List<ItemMonitoringVO> list = dao.getItemMonitoringList();
		
		return itemList;
	}
	
	public void searchItemByJDK(String productName) throws Exception{
		//http://docs.aws.amazon.com/AWSECommerceService/latest/GSG/GettingStarted.html#Java
		//wsdl을 java class로 변환해서 작업하면 됨
	}
	
	public static void main(String[] args) throws Exception{
		new Amazon().searchItem("LG gram 14Z950 i7 14\" Laptop");
	}
}
