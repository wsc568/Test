<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>易书网在线商城</title>
<jsp:include page="easyui.jsp"></jsp:include>
<style type="text/css">
body{
	background-image: url(images/login_bg.png);
}

center{
	margin: auto;
	margin-top: 200px;
}
</style>
</head>
<body>
	<!--    登录界面 -->
	<center>
		<h1>
			<font color="red">欢迎登录易书网在线商城</font>
		</h1>
		<form id="ff" action="login?action=login" method="post">
			<table>
				<tr>
					<td>用 户 名</td>
					<td><input class="easyui-textbox" type="text" name="UName"
						data-options="required:true" value="${UName}"></td>
				</tr>
				<tr>
					<td>密 码</td>
					<td><input class="easyui-textbox" type="password"
						name="UPassword" data-options="required:true" value="${UPassword}"></td>

				</tr>
				
				<tr>
					<td>验证码</td>
					<td><input type="text" name="checkcode"></td>
					<td><img alt="图片加载失败" src="/Buy_Goods_Online/CreateCode"></td>
				</tr>
				
				<tr>
					<td>		</td>
					<td><input type="checkbox" value="keep" name="iskeepinfo" />再此电脑上保存用户名</td>

				</tr>
				<tr>
					<td><font style="color: red">${error}</font></td>
				</tr>
			</table>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="登录">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <button><a href="register.jsp">注册</a></button>
		</form>
	</center>
</body>
</html>