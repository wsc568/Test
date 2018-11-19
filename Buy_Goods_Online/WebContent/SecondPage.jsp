<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.model.Goods"%>
<%@page import="com.model.Appraise"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品详情</title>
<%
	if (session.getAttribute("currentuser") == null) {//防止未登录用户直接进入界面
		response.sendRedirect("login.jsp");
		return;
	}
%>
<style type="text/css">
html body #SecondPagediv {
	width: 80%;
	height: 80%;
}
</style>
<script type="text/javascript">
	function ImBuy() {
		var goodsid = document.getElementById("GoodsId").value;
		var goodstype = document.getElementById("GoodsType").value;//获取两个参数的值带入后台ONUmber GoodsPrice
		var onumber = document.getElementById("ONUmber").value;
		var GoodsStock = document.getElementById("GoodsStock").value;
		var goodsprice = document.getElementById("GoodsPrice").value;
		if (onumber > GoodsStock || GoodsStock == 0) {
			alert("库存不足，购买失败");
		} else {
			$.ajax({
				type : "post",
				async : false,
				url : "goods?action=ImBuy&GoodsId=" + goodsid + "&GoodsType="
						+ goodstype + "&GoodsNumber=" + onumber
						+ "&GoodsPrice=" + goodsprice + "",
				datatype : "json",
				success : function(data) {
					alert(data);
					/* $.each(data.list,function(index,item){
					    alert(item.success); 
					//window.location.reload();
					});  */
				}
			})
		}
	}
	function AddToShopCart() {
		var goodsid = document.getElementById("GoodsId").value;
		$.ajax({
			type : "post",
			async : false,
			url : "goods?action=AddToShopCart&GoodsId=" + goodsid + "",
			datatype : "json",
			success : function(data) {
				alert(data);
				/* $.each(data.list,function(index,item){
				    alert(item.success); 
				//window.location.reload();
				});  */
			}
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
	<div id="SecondPagediv">
		<%
			int BClassify = (Integer) session.getAttribute("AppraiseBClassify");//获取商品的类型
			int BID = (Integer) session.getAttribute("AppraiseBId");//获取商品的id
			String img = "";
			ArrayList<Goods> Goodslist = new ArrayList<Goods>();
			switch (BClassify) {//获取对应的类型的商品
			case 1:
				Goodslist = (ArrayList<Goods>) session
						.getAttribute("bookslist");
				img = "booksimg";
				break;// 窄化转换，没办法去掉警告 
			case 2:
				Goodslist = (ArrayList<Goods>) session
						.getAttribute("clotheslist");
				img = "clothesimg";
				break;
			case 3:
				Goodslist = (ArrayList<Goods>) session
						.getAttribute("foodslist");
				img = "foodsimg";
				break;
			case 4:
				Goodslist = (ArrayList<Goods>) session.getAttribute("carslist");
				img = "carsimg";
				break;
			case 5:
				Goodslist = (ArrayList<Goods>) session
						.getAttribute("machineslist");
				img = "machinesimg";
				break;
			}

			for (int i = 0; i < Goodslist.size(); i++) {
				if (Goodslist.get(i).getBID() == BID)//遍历商品的链表，找打对应的商品
				{
		%>
		<center>
			<div>
				<div style="width: 400px">
					<div style="float: left">
						<!-- 显示商品的图片 -->
						<img src="<%=img%>/<%=Goodslist.get(i).getBID() + ".jpg"%>">
					</div>
					<div style="float: left">
						<h2><%=Goodslist.get(i).getBName()%></h2>
						<br>
						<h2>
							<font color="red">￥：<%=Goodslist.get(i).getBPrice()%></font>
						</h2>
						<br> <input type="hidden" id="GoodsId"
							value="<%=Goodslist.get(i).getBID()%>"> <input
							type="hidden" id="GoodsStock"
							value="<%=Goodslist.get(i).getBStock()%>"> <input
							type="hidden" id="GoodsPrice"
							value="<%=Goodslist.get(i).getBPrice()%>"> <input
							type="hidden" id="GoodsType"
							value="<%=Goodslist.get(i).getBClassify()%>"> 数量：<input
							class="easyui-numberspinner" value="1" id="ONUmber"
							data-options="min:1,max:<%=Goodslist.get(i).getBStock()%>,increment:1,required:true"
							style="width: 120px;">&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;<br>库存：<%=Goodslist.get(i).getBStock()%>
						<br> <br> <br> <input type="button" value="立即购买"
							onclick="ImBuy()"> <input type="button" value="加入购物车"
							onclick="AddToShopCart()">
						<!-- 显示商品的其他信息 -->
					</div>
				</div>
			</div>
		</center>



		<center>
			<div class="easyui-tabs"
				style="width: 700px; height: 350px; clear: both; margin-left: 250px">
				<div title="商品详情" style="padding: 10px">
					<%=Goodslist.get(i).getBDescripe()%>
				</div>
				<div title="买家评价" style="padding: 10px">
					<%
						if (session.getAttribute("userappraise") != null) {
									ArrayList<Appraise> appraiselist = (ArrayList<Appraise>) session
											.getAttribute("userappraise");
									if (appraiselist.size() == 0) {
					%>
					<font color="red">暂时还没有用户评价</font>
					<%
						}
									for (int j = 0; j < appraiselist.size(); j++) {
					%>
					<p><%=appraiselist.get(j).getUName()%>:
						<%
						if (appraiselist.get(j).getOAppraise() == null) {
					%>
						<font color="red">此用户还未评价</font>
						<%
							} else {
						%>
						<%=appraiselist.get(j).getOAppraise()%><br>
						<%
							}
										}
									}
						%>
					
				</div>
			</div>

		</center>
	</div>
	<%
		}
		}
	%>

</body>
</html>