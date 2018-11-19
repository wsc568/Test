<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>在线商城</title>
<jsp:include page="easyui.jsp"></jsp:include>
<script type="text/javascript" src="jquery-3.2.1/jquery-3.2.1.js"></script>
<script type="text/javascript" src="jquery-3.2.1/jquery-3.2.1.min.js"></script>
<%
	if (session.getAttribute("currentuser") == null) {//防止未登录用户直接进入界面
		response.sendRedirect("login.jsp");
		return;
	}
%>
<style type="text/css">
body{
	margin: 0;
	padding: 0;

}
.over {
	background: red;
}
.out {
	background: honeydew;
}
#head{
margin-bottom: 20px;
}
a {font-size:18px} 
a:link {color: blue; text-decoration:none;} //未访问：蓝色、无下划线 
a:active:{color: red; } //激活：红色 
a:visited {color:purple;text-decoration:none;} //已访问：紫色、无下划线 

</style>
</head>
<body>
	<center>
		<div id="head" style="width: 100%; height: 40px; background: honeydew;">
			<div >
			
				<div style="float: left; width: 20%;"
					onmouseover="this.className='over';"
					onmouseout="this.className='out';">
					<h2>
						<a class="headera" href="FirstPage.jsp">首 页</a>
					</h2>
				</div>
				
				<div style="float: left; width: 20%;"
					onmouseover="this.className='over';"
					onmouseout="this.className='out';">
					<h2>
						<a class="headera" href="ShopCart.jsp">购物车</a>
					</h2>
				</div>
				
				<div style="float: left; width: 20%;"
					onmouseover="this.className='over';"
					onmouseout="this.className='out';">
					<h2>
						<a class="headera" href="MyShop.jsp">我的购物</a>
					</h2>
				</div>
				
				<div style="float: left; width: 20%;"
					onmouseover="this.className='over';"
					onmouseout="this.className='out';">
					<h2>
						<a class="headera" href="UserInfo.jsp">欢迎：${currentuser.UName}</a>
					</h2>
				</div>
				
				<div style="float: left; width: 20%;"
					onmouseover="this.className='over';"
					onmouseout="this.className='out';">
					<h2>
						<a class="headera" href="login.jsp">安全退出</a>
					</h2>
				</div>
			</div>
		</div>
	</center>
</body>
</html>