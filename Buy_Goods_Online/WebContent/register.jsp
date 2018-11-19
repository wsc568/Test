<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎注册</title>
<script type="text/javascript">
	function check() {
		var password = document.getElementById("password").value;
		var Repassword = document.getElementById("Repassword").value;
		if (password != Repassword) {
			alert("两次密码不一致!");
			return false;
		}
		var tel = document.getElementById("tel").value;
		var re = /^1[0-9]{10}$/;
		if (!re.test(tel)) {
			alert("请输入正确的手机号码");
			return false;
		}
		var iden = /^[0-9]{18}$/;
		var identify = document.getElementById("identify").value;
		if (!iden.test(identify)) {
			alert("请输入正确的身份证号");
			return false;
		}
		return true;
	}
</script>
<jsp:include page="easyui.jsp"></jsp:include>
</head>
<body>
	<center>
		<h1>
			<font color="red">欢迎注册在线商城</font>
		</h1>
		<form action="login?action=register" method="post">
			<table>
				<tr>
					<td>用 户 名</td>
					<td><input class="easyui-textbox" type="text" name="UName"
						data-options="required:true"></td>
				</tr>
				<tr>
					<td>用户类型:</td><!-- 买家为1，买家为2，在数据库里面好表示 -->
					<td><select class="easyui-combobox" name="UClassify"><option
								value="1">买家</option>
							<option value="2">卖家</option></select></td>
				</tr>
				<tr>
					<td>密 码</td>
					<td><input id="password" class="easyui-textbox"
						type="password" name="UPassword" data-options="required:true"></td>
				</tr>
				<tr>
					<td>确认密码</td>
					<td><input type="password" class="easyui-textbox"
						id="Repassword" name="ReUPassword"></td>
					<!-- class="easyui-textbox"  -->
				</tr>
				<tr>
					<td>电 话</td>
					<td><input id="tel" class="easyui-textbox" type="text"
						name="UTel" data-options="required:true"></td>
				</tr>
				<tr>
					<td>身份证号</td>
					<td><input id="identify" class="easyui-textbox" type="text"
						name="UIdentify" data-options="required:true"></td>
				</tr>
				<tr>
					<td>收货地址</td>
					<td><input class="easyui-textbox" type="text" name="UAdress"
						data-options="required:true"></td>
				</tr>
			</table>
			<input onclick="return check()" type="submit" value="提交">
		</form>
	</center>
</body>
</html>