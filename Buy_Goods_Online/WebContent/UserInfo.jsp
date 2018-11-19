<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<jsp:include page="easyui.jsp"></jsp:include>
<%
	if (session.getAttribute("currentuser") == null) {//防止未登录用户直接进入界面
		response.sendRedirect("login.jsp");
		return;
	}
%>
<script type="text/javascript">
	function modifypassword() {
		url = "login?action=modifypassword";
		$("#dlg").dialog("open").dialog("setTitle", "修改密码");
	}
	function modifyPassword() {
		$("#fm").form("submit", {
			url : url,
			onSubmit : function() {
				var oldPassword = $("#oldPassword").val();
				var newPassword = $("#newPassword").val();
				var newPassword2 = $("#newPassword2").val();
				if (!$(this).form("validate")) {
					return false;
				}
				if (oldPassword != '${currentuser.UPassword}') {
					$.messager.alert('系统提示', '用户名密码输入错误！');
					return false;
				}
				if (newPassword != newPassword2) {
					$.messager.alert('系统提示', '确认密码输入错误！');
					return false;
				}
				return true;
			},
			success : function(result) {
				var result = eval('(' + result + ')');
				if (result.errorMsg) {
					$.messager.alert('系统提示', result.errorMsg);
					return;
				} else {
					$.messager.alert('系统提示', '密码修改成功，下一次登录生效！');
					closePasswordModifyDialog();
				}
			}
		});
	}
	function closePasswordModifyDialog() {
		$("#dlg").dialog("close");
		$("#oldPassword").val("");
		$("#newPassword").val("");
		$("#newPassword2").val("");
	}
</script>
<style type="text/css">
.p {
	font-size: 18px;
}
</style>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<center>
		<div style="width: 50%; /* weight: 100px; */ border: 1px solid #000">
			<br> <br> <br> <br> <br> <br>
			<table>
				<tr>
					<td><p class="p">用 户 名:</td>
					<td><p class="p">${currentuser.UName}</td>
				</tr>
				<tr>
					<td><p class="p">账户余额：</td>
					<td><p class="p">${currentuser.UBalance}</td>
				</tr>
				<tr>
					<td><p class="p">电 话：</td>
					<td><p class="p">${currentuser.UTel}</td>
				</tr>
				<tr>
					<td><p class="p">身份证号：</td>
					<td><p class="p">${currentuser.UIdentify}</td>
				</tr>
				<tr>
					<td><p class="p">收货地址：</td>
					<td><p class="p">${currentuser.UAddress}</td>
				</tr>
				<tr>
					<td><input type="button" value="修改密码"
						onclick="modifypassword()"></td>
				</tr>
			</table>
			<br> <br> <br> <br> <br> <br>
		</div>
	</center>

	<div id="dlg" class="easyui-dialog"
		style="width: 400px; height: 280px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons" data-options="iconCls:'icon-modifyPassword'">
		<form id="fm" method="post">
			<table cellspacing="4px;">
				<tr>
					<td>用户名：</td>
					<td><input type="hidden" name="userId" id="userId"
						value="${currentuser.UID }"><input type="text"
						name="userName" id="userName" readonly="readonly"
						value="${currentuser.UName}" style="width: 200px;" /></td>
				</tr>
				<tr>
					<td>原密码：</td>
					<td><input type="password" class="easyui-validatebox"
						name="oldPassword" id="oldPassword" style="width: 200px;"
						required="true" /></td>
				</tr>
				<tr>
					<td>新密码：</td>
					<td><input type="password" class="easyui-validatebox"
						name="newPassword" id="newPassword" style="width: 200px;"
						required="true" /></td>
				</tr>
				<tr>
					<td>确认新密码：</td>
					<td><input type="password" class="easyui-validatebox"
						name="newPassword2" id="newPassword2" style="width: 200px;"
						required="true" /></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:modifyPassword()" class="easyui-linkbutton">保存</a>
		<a href="javascript:closePasswordModifyDialog()"
			class="easyui-linkbutton">关闭</a>
	</div>

</body>
</html>