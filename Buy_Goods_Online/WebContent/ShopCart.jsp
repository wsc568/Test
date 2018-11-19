<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.model.Goods"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>购物车</title>
<jsp:include page="easyui.jsp"></jsp:include>
<%
	if (session.getAttribute("currentuser") == null) {//防止未登录用户直接进入界面
		response.sendRedirect("login.jsp");
		return;
	}
%>
<script type="text/javascript">
	window.onload = function() {
		if (!sessionStorage.getItem("shopcartflag")) {
			sessionStorage.setItem("shopcartflag", 1);
			$.ajax({
				type : "post",
				async : false,
				url : "goods?action=GetShopCart",
				datatype : "json",
				success : function(data) {
					window.location.reload();
				},
			})
		}
	}
	function divclick(temp) {
		var a = temp.getElementsByTagName("input")[0].value;
		var b = temp.getElementsByTagName("input")[1].value;
		var fm = document.getElementById("fm");
		fm.action = "goods?action=GetAppraise&classify=" + a + "&bid=" + b + "";
		fm.submit();
	}
	function delectgoods(temp) {
        var goodsid = temp.getElementsByTagName("input")[0].value;
        $.ajax({
			type : "post",
			async : false,
			url : "goods?action=DeleteGoods&goodsid="+goodsid+"",
			datatype : "json",
			success : function(data) {
				alert(data);
				window.location.reload();
			},
		})
	}
</script>

<style type="text/css">
body{
	background-image: url(images/2.jpg);
}
</style>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<center>
		<div class="FirstPagediv">
			<form id="fm" method="post">
				<!-- 负责这个界面的内容 -->

				<%
					final int PAGESIZE = 6;//每页放8个商品；
					ArrayList<Goods> list = new ArrayList<Goods>();
					if (session.getAttribute("shopcartlist") != null) {//如果有这个商品的话
						list = (ArrayList<Goods>) session.getAttribute("shopcartlist");// 窄化转换，没办法去掉警告 
					}
				%>
				<%
					int PageCount = 0;
					int CurrPage = 1;//当前页，首先设置为1；
					if (list.size() > 0) {//如果商品的数量不为0
						int Size = list.size();
						PageCount = (Size % PAGESIZE == 0) ? (Size / PAGESIZE) : (Size
								/ PAGESIZE + 1);
						String tmp = request.getParameter("CurrPage");//获取带过来的页面数
						if (tmp == null) {
							tmp = "1";
						}
						CurrPage = Integer.parseInt(tmp);
						CurrPage = CurrPage == 0 ? 1 : CurrPage;
						CurrPage = CurrPage > PageCount ? PageCount : CurrPage;
						int count = (CurrPage - 1) * PAGESIZE;
						for (int i = count; i < count + PAGESIZE && i < Size; i++)//
						{
				%>
				<div style="float: left; width: 20%; margin-left: 1%">
					 <img
						src="booksimg/<%=list.get(i).getBID() + ".jpg"%>"><br>
				</div>
				<div style="float: left; width: 10%; margin-left: 2%">
					<h2>
						<%=list.get(i).getBName()%>
					</h2>
					<br>
					<h2>
						<font color="red"> ￥：<%=list.get(i).getBPrice()%></font>
					</h2>
					<br>
					<div onclick="divclick(this)">
						<input type="hidden" value="<%=list.get(i).getBClassify()%>">
						<input type="hidden" value="<%=list.get(i).getBID()%>"> <input
							type="button" value="查看商品详情" onclick="imputclick()">
					</div>
					<div onclick="delectgoods(this)">
						<input type="hidden" value="<%=list.get(i).getBID()%>"> <input
							type="button" value="删除商品">
					</div>
				</div>
				<%
					}

					}else{
						%> 
					    <font color="red" style="font-size:22px">购物车空空如也</font>
					<% 
					}
				%>
				<div style="position: absolute; bottom: 50px; margin-left: 650px;">
					<div style="text-align: center">
						<a href="ShopCart.jsp?CurrPage=1">首页</a> <a
							href="ShopCart.jsp?CurrPage=<%=CurrPage - 1%>">上一页</a> <a
							href="ShopCart.jsp?CurrPage=<%=CurrPage + 1%>">下一页</a> <a
							href="ShopCart.jsp?CurrPage=<%=CurrPage%>">尾页</a> 第<%=CurrPage%>页/共<%=PageCount%>页
					</div>
				</div>
			</form>
		</div>

	</center>
</body>
</html>