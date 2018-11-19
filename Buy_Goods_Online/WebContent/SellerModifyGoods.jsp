<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.model.Goods"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="easyui.jsp"></jsp:include>
<title>Insert title here</title>
</head>
<body>
	<div id="SecondPagediv">
		<%
			int BID = 0;
			if (request.getParameter("goosid") != null) {
				BID = Integer.parseInt(request.getParameter("goosid"));
			}
			ArrayList<Goods> Goodslist = new ArrayList<Goods>();
			if (session.getAttribute("sellergoodslist") != null) {//如果有这个商品的话
				Goodslist = (ArrayList<Goods>) session
						.getAttribute("sellergoodslist");// 窄化转换，没办法去掉警告 
			}
			for (int i = 0; i < Goodslist.size(); i++) {
				if (Goodslist.get(i).getBID() == BID)//遍历商品的链表，找打对应的商品
				{
		%>
		<center>
			<form action="goods?action=sellermodifygoods" method="post">
				<div>
					<div style="width: 400px">
						<div style="float: left">
							<!-- 显示商品的图片 -->
							<img src="booksimg/<%=Goodslist.get(i).getBID() + ".jpg"%>">
						</div>
						<div style="float: left">
							<input type="hidden" name="GoodsId"
								value="<%=Goodslist.get(i).getBID()%>"> <input
								type="hidden" name="GoodsClassify"
								value="<%=Goodslist.get(i).getBClassify()%>"> 名称：<input
								name="GoodsName" value="<%=Goodslist.get(i).getBName()%>">
							<br> <br>价格：<input name="GoodsPrice"
								value="<%=Goodslist.get(i).getBPrice()%>"> <br> <br>
							库存： <input name="GoodsStock"
								value="<%=Goodslist.get(i).getBStock()%>"> <br> <br>
							相信信息：<input name="GoodsDescripe"
								value="<%=Goodslist.get(i).getBDescripe()%>"> <br><br>
							<input type="submit" value="修改信息">
							<!-- 显示商品的其他信息 -->
						</div>
					</div>
				</div>
			</form>
		</center>
	</div>
	<%
		}
		}
	%>

</body>
</html>