<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=medium-dpi" />
<title>Insert title here</title>
<style>
.ia{position:relative; margin:0 0 19px 0;}
.ip{padding:0 85px 0 19px;}
.ip li{width:100%;margin-bottom:5px;}
.ip input{width:100%;}
.ip label{float:left;padding-top:1px;font-weight:bold;color:#000;font-size:12px;}
.it{display:block;margin-left:58px;border:1px solid #c9c6c6;background:#fff;}
.it input{width:100%;height:20px;border:0 none;}
.ac{top:0;bottom:auto;}
</style>
</head>
<body>
	<form action="/dolziggu/list.jsp" method="post">
	<div class="ia">
		<div style="background:#E2E2E2;">
			<br>
			<ul class="ip">
				<span class="it" id="idb"><input type="text" id="productName" name="productName" value='' /></span>	
			</ul>
			<p class="ac">
				<input type="submit" value="검색">
			</p>
			<br>
		</div>
	</div>
	</form>
</body>
</html>