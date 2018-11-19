package com.model;

import java.sql.Date;

public class Order {
	private int OId;
	private int BId;
	private int UIdd;
	private int ONumber;
	private Date ODate;
	private String OAppraise;
	private int OType;
	public Order(int oId, int bId, int uIdd, int oNumber, Date oDate,
			String oAppraise, int oType) {
		super();
		OId = oId;
		BId = bId;
		UIdd = uIdd;
		ONumber = oNumber;
		ODate = oDate;
		OAppraise = oAppraise;
		OType = oType;
	}
	
	public int getOId() {
		return OId;
	}
	public void setOId(int oId) {
		OId = oId;
	}
	public int getBId() {
		return BId;
	}
	public void setBId(int bId) {
		BId = bId;
	}
	public int getUIdd() {
		return UIdd;
	}
	public void setUIdd(int uIdd) {
		UIdd = uIdd;
	}
	public int getONumber() {
		return ONumber;
	}
	public void setONumber(int oNumber) {
		ONumber = oNumber;
	}
	public Date getODate() {
		return ODate;
	}
	public void setODate(Date oDate) {
		ODate = oDate;
	}
	public String getOAppraise() {
		return OAppraise;
	}
	public void setOAppraise(String oAppraise) {
		OAppraise = oAppraise;
	}
	public int getOType() {
		return OType;
	}
	public void setOType(int oType) {
		OType = oType;
	}
	
	

}
