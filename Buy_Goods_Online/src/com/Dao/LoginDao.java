package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.model.User;

public class LoginDao {
    
	//ע��ʱ����ע����Ϣ�������ݿ�
	public int addUser(HttpServletRequest request, Connection con, User user) throws SQLException {
		String sql = "insert into SCOTT.USE(UIDD,UNAME,UPASSWORD,UBALANCE,UTEL,UIDENTIFY,UADDRESS,UCLASSIFY) values(?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, user.getUID());
		pstmt.setString(2, user.getUName());
		pstmt.setString(3, user.getUPassword());
		pstmt.setDouble(4, user.getUBalance());
		pstmt.setLong(5 ,user.getUTel());
		pstmt.setLong(6, user.getUIdentify());
		pstmt.setString(7, user.getUAddress());
		pstmt.setInt(8, user.getUClassify());
		return pstmt.executeUpdate();
	}

	//��ȡ���ݿ�����UIDD����ʵ��������
	public int getmaxid(HttpServletRequest request, Connection con) throws SQLException {
		String sql = "select max(UIDD) from SCOTT.USE";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()){
			return rs.getInt(1);
		}
		return 0;
	}

	//��¼�����û��������빲ͬ��ѯ���ݿ⣬�н������¼�ɹ�
	public User login(HttpServletRequest request, Connection con, String uName,
			String uPassword) throws SQLException {
		User user  = null;
		String sql = "select * from USE where UNAME = '"+uName+"' and UPASSWORD='"+uPassword+"'";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs= pstmt.executeQuery();
		while(rs.next()){
			user = new User();
			user.setUID(rs.getInt("UIDD"));
			user.setUName(rs.getString("UNAME"));
			user.setUPassword(rs.getString("UPASSWORD"));
			user.setUBalance(rs.getDouble("UBALANCE"));
			user.setUTel(rs.getLong("UTEL"));
			user.setUIdentify(rs.getLong("UIDENTIFY"));
			user.setUAddress(rs.getString("UADDRESS"));
			user.setUClassify(rs.getInt("UCLASSIFY"));
		}
		return user;	
	}

	//�޸ĵ�ǰ�û�����
	public int modifypassword(HttpServletRequest request, Connection con,
			User currentuser, String newpassword) throws SQLException {
		String sql="update SCOTT.USE SET UPASSWORD='"+newpassword+"' WHERE UIDD='"+currentuser.getUID()+"'";
		PreparedStatement pstmt = con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
}
