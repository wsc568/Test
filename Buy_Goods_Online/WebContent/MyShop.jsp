<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.model.OrderGood"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的订单</title>
<jsp:include page="easyui.jsp"></jsp:include>
<%
	if (session.getAttribute("currentuser") == null) {//防止未登录用户直接进入界面
		response.sendRedirect("login.jsp");
		return;
	}
%>
<script type="text/javascript">
	window.onload = function() {
		if (!sessionStorage.getItem("myshopflag")) {
			sessionStorage.setItem("myshopflag", 1);
			$.ajax({
				type : "post",
				async : false,
				url : "goods?action=GetMyShop",
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
		var fm2 = document.getElementById("fm");
		fm.action = "goods?action=GetAppraise&classify="+a+"&bid="+b+"";
		fm.submit();
	} 
	function addappraise(temp) {
		var oid = temp.getElementsByTagName("input")[0].value;
		$("#dlg").dialog("open").dialog("setTitle", "添加评价");
		url = "goods?action=AddAppraise&OId=" + oid + "";
	}
	function saveappraise() {
		var app = document.getElementById("UAppraise").value;
		url += "&app=" + encodeURI(app) + "";
		$("#fm").form(
				"submit",
				{
					url : url,
					success : function(result) {
						//alert("success");
						var result = eval('(' + result + ')');
						//alert("success2");
						if (result.errorMsg) {
							//alert("success3");
							$.messager.alert('系统提示', "<font color=red>"
									+ result.errorMsg + "</font>");
							return;
						} else {
							//alert("nsjdcnjsd");
							$.messager.alert('系统提示', '添加成功');
							closesaveappraise();
							window.location.reload();
						}
					}

				});
	}
	function closesaveappraise() {
		$("#dlg").dialog("close");
		$("#fm2").form('clear');
	}
	function delectorder(temp) {
        var orderid = temp.getElementsByTagName("input")[0].value;
        $.ajax({
			type : "post",
			async : false,
			url : "goods?action=DeleteOrder&orderid="+orderid+"",
			datatype : "json",
			success : function(data) {
				alert(data);
				window.location.reload();
			},
		})
	}
</script>
<style type="text/css">
.h {
	display: inline
}
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
					ArrayList<OrderGood> list = new ArrayList<OrderGood>();
					if (session.getAttribute("myshoplist") != null) {//如果有这个商品的话
						list = (ArrayList<OrderGood>) session
								.getAttribute("myshoplist");// 窄化转换，没办法去掉警告 
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
				<div style="float: left; width: 20%; margin-left: 1%"
					onclick="divclick(this)">
					<input type="hidden" value="<%=list.get(i).getBClassify()%>">
					<input type="hidden" value="<%=list.get(i).getBId()%>"> <img
						src="booksimg/<%=list.get(i).getBId() + ".jpg"%>" title="点击查看商品详情"><br>
				</div>
				<div style="float: left; width: 10%; margin-left: 2%">
					<h2>
						<%=list.get(i).getBName()%>
					</h2>
					<br>
					<h2>
						<font color="red"> 单价：<%=list.get(i).getBPrice()%></font>
					</h2>
					<h2 class="h">
						<font color="red"> 总价：<%=list.get(i).getBPrice()
							* list.get(i).getONumber()%></font>
					</h2>
					<h5 class="h">
						购买<%=list.get(i).getONumber()%>件
					</h5>
					<br> <br>
					<%
						if (list.get(i).getOAppraise() == null) {
					%>
					<div onclick="addappraise(this)">
						<input type="hidden" value="<%=list.get(i).getOId()%>"> <input
							type="button" value="点击评价">
					</div>
					<%
						} else {
					%>
					<div>
						<textarea rows="2" cols="20" readonly="readonly">您已评价：<%=list.get(i).getOAppraise()%></textarea>
					</div>
					<%
						}
					%>
					<div onclick="delectorder(this)">
						<input type="hidden" value="<%=list.get(i).getOId()%>"> <input
							type="button" value="删除商品" >
					</div>
				</div>
				<%
					}

					}else{
						%> 
						    <font color="red" style="font-size:22px">你还没买任何宝贝，赶紧去购物吧</font>
						<% 
					}
				%>
				<div style="position: absolute; bottom: 50px; margin-left: 650px;">
					<div style="text-align: center">
						<a href="MyShop.jsp?CurrPage=1">首页</a> <a
							href="MyShop.jsp?CurrPage=<%=CurrPage - 1%>">上一页</a> <a
							href="MyShop.jsp?CurrPage=<%=CurrPage + 1%>">下一页</a> <a
							href="MyShop.jsp?CurrPage=<%=CurrPage%>">尾页</a> 第<%=CurrPage%>页/共<%=PageCount%>页
					</div>
				</div>
			</form>
		</div>

	</center>
	<div id="dlg" class="easyui-dialog"
		style="width: 570px; height: 270px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="fm" action="post">
			<table cellspacing="5px;">
				<tr>
					<td>您的评价：</td>
					<td width="80%"><textarea id="UAppraise" rows="4" cols="40"
							name="UAppraise"></textarea></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:saveappraise()" class="easyui-linkbutton">保存</a> <a
			href="javascript:closesaveappraise()" class="easyui-linkbutton">关闭</a>
	</div>
</body>
</html>