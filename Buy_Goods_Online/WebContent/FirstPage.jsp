<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.model.Goods"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主页</title>
<jsp:include page="easyui.jsp"></jsp:include>
<%
	if (session.getAttribute("currentuser") == null) {//防止未登录用户直接进入界面
		response.sendRedirect("login.jsp");
		return;
	}
%>

<script type="text/javascript">
	window.onload = function() {
		if (!sessionStorage.getItem("firstpageflag")) {
			sessionStorage.setItem("firstpageflag", 1);
			$.ajax({
				type : "post",
				async : false,
				url : "goods?action=GetGoods",
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
		fm.action="goods?action=GetAppraise&classify="+a+"&bid="+b+"";
		fm.submit();	
	}
</script>
<style type="text/css">
body{
	background-image: url(images/2.jpg);
}
html body #FirstPagediv {
	width: 80%;
	height: 80%;
	position: relative;
}
</style>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include><!-- 引入最上面的导航栏 -->
	<center>
	
		<div class="FirstPagediv">
			<form id="fm" method="post">
				<!-- 负责这个界面的内容 -->

				<%
					final int PAGESIZE = 8;//每页放8个商品；
					ArrayList<Goods> list = new ArrayList<Goods>();
					if (session.getAttribute("bookslist") != null) {//如果有这个商品的话
						list = (ArrayList<Goods>) session.getAttribute("bookslist");// 窄化转换，没办法去掉警告 
					}
				%>
				<%
					int PageCount = 0;//共有是多少页
					int CurrPage = 1;//当前页，首先设置为1；
					if (list.size() > 0) {//如果商品的数量不为0

						int Size = list.size();
						PageCount = (Size % PAGESIZE == 0) ? (Size / PAGESIZE) : (Size
								/ PAGESIZE + 1);//计算出共有多少页
						String tmp = request.getParameter("CurrPage");//获取带过来的页面数
						if (tmp == null) {
							tmp = "1";
						}
						CurrPage = Integer.parseInt(tmp);
						CurrPage = CurrPage == 0 ? 1 : CurrPage;
						CurrPage = CurrPage > PageCount ? PageCount : CurrPage;
						int count = (CurrPage - 1) * PAGESIZE;//从第几条数据开始显示
						
						for (int i = count; i < count + PAGESIZE && i < Size; i++)//
						{
				%>
				<div style="float: left; width: 20%; margin-left: 5%"
					onclick="divclick(this)">
					<input type="hidden" value="<%=list.get(i).getBClassify()%>">
					<input type="hidden" value="<%=list.get(i).getBID()%>">
					<img src="booksimg/<%=list.get(i).getBID()+ ".jpg"%>"><br>
					<%-- <a
					class="firstpagea" style="display: block"
					href="SecondPage.jsp?BClassify=<%=list.get(i).getBClassify()%>&BId=<%=list.get(i).getBID()%>"><%=list.get(i).getBName()%></a> --%>
					<p><%=list.get(i).getBName()%>
				</div>
				<%
					}

					}
				%>
				<div style="position: absolute; bottom: 50px; margin-left: 650px;">
					<div style="text-align: center">
						<a href="FirstPage.jsp?CurrPage=1">首页</a> 
						<a href="FirstPage.jsp?CurrPage=<%=CurrPage - 1%>">上一页</a> 
						
						<%//显示分页
						for(int i=1;i<=PageCount;i++){ %>
							<a href="FirstPage.jsp?CurrPage=<%=i%>">『<%=i%>』</a> 
						<% } %>
						
						
						<a href="FirstPage.jsp?CurrPage=<%=CurrPage + 1%>">下一页</a> 
						<a href="FirstPage.jsp?CurrPage=<%=CurrPage%>">尾页</a>
						 第<%=CurrPage%>页/共<%=PageCount%>页
					</div>
					<h4>上次登录:<%=session.getAttribute("cookie") %></h4>
				</div>
			</form>
		</div>

	</center>
	<!-- 商城首页 -->
</body>
</html>