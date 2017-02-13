<%@page import="com.dolziggu.site.Amazon"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String productName = request.getParameter("productName");
	Amazon amazon = new Amazon();
	List itemList = amazon.searchItem(productName);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 
가격, 할인가격(세이브), 현재최저가, 역대최저가   
 -->
 	<form action="/dolziggu/list.jsp" method="post">
		<input type="text" name="productName" value="<%=productName%>"> <input type="submit" value="검색">
	</form>
	<br>
	
	<table border="1" width="100%">
	<tr>
		<td>이미지</td>
		<td>상품명</td>
		<td>기본가격</td>
		<td>현재가격(할인율)</td>
		<td>신품최저가</td>
		<td>중고최저가</td>
	</tr>
	
	</table>
</body>
</html>