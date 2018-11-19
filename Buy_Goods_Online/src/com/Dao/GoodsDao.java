package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.model.Appraise;
import com.model.Goods;
import com.model.Order;
import com.model.OrderGood;
import com.model.User;

public class GoodsDao {

	public ArrayList<Goods> getgoods(HttpServletRequest request,Connection con, int i) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<Goods> list = new ArrayList<Goods>();
		Goods goods = null;
		String sql = "select * from GOODS where BClASSIFY = '" + i + "'";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			goods = new Goods();
			goods.setBID(rs.getInt("BID"));
			goods.setBName(rs.getString("BName"));
			goods.setBPrice(rs.getDouble("BPrice"));
			goods.setBStock(rs.getInt("BStock"));
			goods.setBDescripe(rs.getString("BDescripe"));
			goods.setBClassify(rs.getInt("BClassify"));
			list.add(goods);
		}
		return list;
	}

	public ArrayList<Appraise> getappraise(HttpServletRequest request,
			Connection con, int bID) throws SQLException {
		String sql = "select UNAME,OAPPRAISE from USE,ORDE WHERE ORDE.UIDD=USE.UIDD AND ORDE.BID='"
				+ bID + "'";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		ArrayList<Appraise> list = new ArrayList<Appraise>();
		Appraise appraise = null;
		while (rs.next()) {
			appraise = new Appraise();
			appraise.setOAppraise(rs.getString("OAPPRAISE"));
			appraise.setUName(rs.getString("UNAME"));
			list.add(appraise);
		}
		return list;
	}

	public int getOId(HttpServletRequest request, Connection con)
			throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select max(OID) from SCOTT.ORDE";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			return rs.getInt(1);
		}
		return 0;
	}

	public int addorder(HttpServletRequest request, Connection con, Order order)
			throws SQLException {
		// TODO Auto-generated method stub
		java.util.Date a = new java.util.Date();
		java.sql.Timestamp d = new java.sql.Timestamp(a.getTime());
		String sql = "insert into ORDE(OID,BID,UIDD,ONUMBER,ODATE,OTYPE) values(?,?,?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, order.getOId());
		pstmt.setInt(2, order.getBId());
		pstmt.setInt(3, order.getUIdd());
		pstmt.setInt(4, order.getONumber());
		pstmt.setTimestamp(5, d);
		pstmt.setInt(6, order.getOType());
		return pstmt.executeUpdate();
	}

	// 在个人信息里减去
	public void minbalance(HttpServletRequest request, Connection con,
			User currentuser, double sum) throws SQLException {
		String sql = "update SCOTT.USE set  UBALANCE = UBALANCE-'" + sum
				+ "' WHERE UIDD='" + currentuser.getUID() + "'";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.executeUpdate();
	}

	public double getbalance(HttpServletRequest request, Connection con,
			User currentuser) throws SQLException {
		String sql = "select UBALANCE FROM SCOTT.USE WHERE UIDD='"
				+ currentuser.getUID() + "'";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			return rs.getDouble(1);
		}
		return 0;
		// TODO Auto-generated method stub

	}

	public int addtoshopcart(HttpServletRequest request, Connection con,
			User currentuser, int goodsId, int sid) throws SQLException {
		String sql = "insert into SCOTT.SHOPCART(SID,UIDD,BID) values(?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, sid);
		pstmt.setInt(2, currentuser.getUID());
		pstmt.setInt(3, goodsId);
		return pstmt.executeUpdate();
	}

	public int getsid(HttpServletRequest request, Connection con)
			throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select max(SID) from SCOTT.SHOPCART";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			return rs.getInt(1);
		}
		return 0;
	}

	public ArrayList<Goods> getshopcartlist(HttpServletRequest request,
			Connection con, User currentuser) throws SQLException {
		ArrayList<Goods> list = new ArrayList<Goods>();
		Goods goods = null;
		String sql = "select * from GOODS,SHOPCART where GOODS.BID=SHOPCART.BID AND SHOPCART.UIDD='"
				+ currentuser.getUID() + "'";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			goods = new Goods();
			goods.setBID(rs.getInt("BID"));
			goods.setBName(rs.getString("BName"));
			goods.setBPrice(rs.getDouble("BPrice"));
			goods.setBStock(rs.getInt("BStock"));
			goods.setBDescripe(rs.getString("BDescripe"));
			goods.setBClassify(rs.getInt("BClassify"));
			list.add(goods);
		}
		return list;
	}

	public int deletegoods(HttpServletRequest request, Connection con,
			User currentuser, int goodsId) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "delete from SCOTT.SHOPCART  WHERE UIDD='"
				+ currentuser.getUID() + "' and BID='" + goodsId + "'";
		PreparedStatement pstmt = con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}

	public int queryshopcart(HttpServletRequest request, Connection con,
			User currentuser, int goodsId) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select * from SCOTT.SHOPCART WHERE UIDD='"
				+ currentuser.getUID() + "' and BID='" + goodsId + "'";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			return 0;
		}
		return 1;
	}

	public ArrayList<OrderGood> getmyshop(HttpServletRequest request,
			Connection con, User currentuser) throws SQLException {
		ArrayList<OrderGood> list = new ArrayList<OrderGood>();
		OrderGood ordergoods = null;
		String sql = "select OID,SCOTT.ORDE.BID,UIDD,ONUMBER,ODATE,OAPPRAISE,BNAME,BPRICE,BCLASSIFY from SCOTT.ORDE,SCOTT.GOODS WHERE GOODS.BID=ORDE.BID AND ORDE.UIDD='"
				+ currentuser.getUID() + "'";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			ordergoods = new OrderGood();
			ordergoods.setOId(rs.getInt("OID"));
			ordergoods.setBId(rs.getInt("bid"));
			ordergoods.setUIdd(rs.getInt("UIDD"));
			ordergoods.setONumber(rs.getInt("ONUMBER"));
			ordergoods.setODate(rs.getDate("ODATE").toString());
			ordergoods.setOAppraise(rs.getString("OAPPRAISE"));
			ordergoods.setBName(rs.getString("BNAME"));
			ordergoods.setBPrice(rs.getDouble("BPRICE"));
			ordergoods.setBClassify(rs.getInt("BCLASSIFY"));
			list.add(ordergoods);
		}
		return list;
	}

	public int addappraise(HttpServletRequest request, Connection con, int oId,
			String app2) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "UPDATE SCOTT.ORDE SET OAPPRAISE='" + app2
				+ "' where OID='" + oId + "' ";
		PreparedStatement pstmt = con.prepareStatement(sql);
		return pstmt.executeUpdate();

	}

	public int deleteorder(HttpServletRequest request, Connection con,
			int orderid) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "delete from SCOTT.ORDE WHERE OID='" + orderid + "'";
		PreparedStatement pstmt = con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}

	public ArrayList<Goods> sellergetgoods(HttpServletRequest request,
			Connection con, User currentuser) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<Goods> list = new ArrayList<Goods>();
		Goods goods = null;
		String sql = "select * from SCOTT.GOODS WHERE BBELONGUSE='"
				+ currentuser.getUID() + "' ORDER BY BID";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			goods = new Goods();
			goods.setBID(rs.getInt("BID"));
			goods.setBName(rs.getString("BNAME"));
			goods.setBPrice(rs.getDouble("BPRICE"));
			goods.setBStock(rs.getInt("BSTOCK"));
			goods.setBDescripe(rs.getString("BDESCRIPE"));
			goods.setBClassify(rs.getInt("BCLASSIFY"));
			goods.setBBelonguse(rs.getInt("BBELONGUSE"));
			list.add(goods);
		}
		return list;
	}

	public int sellermodifygoods(HttpServletRequest request, Connection con,
			Goods goods) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "update SCOTT.GOODS SET BNAME='" + goods.getBName()
				+ "',BPRICE='" + goods.getBPrice() + "',BSTOCK='"
				+ goods.getBStock() + "',BDESCRIPE='" + goods.getBDescripe()
				+ "',BCLASSIFY='" + goods.getBClassify() + "',BBELONGUSE='"
				+ goods.getBBelonguse() + "' where BID='" + goods.getBID()
				+ "'";
		PreparedStatement pstmt = con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}

	public int sellerdeletegoods(HttpServletRequest request, Connection con,
			int goodsid) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "delete from SCOTT.GOODS WHERE BID='" + goodsid + "'";
		PreparedStatement pstmt = con.prepareStatement(sql);
		return pstmt.executeUpdate();

	}

	public int sellerdeleteshopcart(HttpServletRequest request, Connection con,
			int goodsid) throws SQLException {
		String sql = "delete from SCOTT.SHOPCART WHERE BID='" + goodsid + "'";
		PreparedStatement pstmt = con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}

	public int sellerdeletemyshop(HttpServletRequest request, Connection con,
			int goodsid) throws SQLException {
		String sql = "delete from SCOTT.ORDE WHERE BID='" + goodsid + "'";
		PreparedStatement pstmt = con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}

	public int sellergetbid(HttpServletRequest request, Connection con)
			throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select max(BID) from SCOTT.GOODS";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			return rs.getInt(1);
		}
		return 0;
	}

	public int selleraddgoods(HttpServletRequest request, Connection con,
			Goods goods) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "insert into SCOTT.GOODS(BID,BNAME,BPRICE,BSTOCK,BDESCRIPE,BCLASSIFY,BBELONGUSE) VALUES(?,?,?,?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, goods.getBID());
		pstmt.setString(2, goods.getBName());
		pstmt.setDouble(3, goods.getBPrice());
		pstmt.setInt(4, goods.getBStock());
		pstmt.setString(5, goods.getBDescripe());
		pstmt.setInt(6, goods.getBClassify());
		pstmt.setInt(7, goods.getBBelonguse());
		return pstmt.executeUpdate();
	}

	public int minstock(HttpServletRequest request, Connection con, Order order)
			throws SQLException {
		// TODO Auto-generated method stub
		String sql = "update SCOTT.GOODS SET BSTOCK =BSTOCK- '"
				+ order.getONumber() + "' where BID='" + order.getBId() + "'";
		PreparedStatement pstmt = con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}

	public int addbalance(HttpServletRequest request, Connection con,
			Order order, double sum) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "update SCOTT.USE SET UBALANCE=UBALANCE+'"
				+ sum
				+ "' WHERE USE.UIDD=(SELECT BBELONGUSE FROM GOODS WHERE GOODS.BID='"
				+ order.getBId() + "')";
		PreparedStatement pstmt = con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
}