package com.model;

import java.io.Serializable;

public class OrderGood implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int OId;
	private int BId;
	private int UIdd;
	private int ONumber;
	private String ODate;
	private String OAppraise;
	private String BName;
	private double BPrice;
	private int BClassify;

	
	public OrderGood(int oId, int bId, int uIdd, int oNumber, String oDate,
			String oAppraise, String bName, double bPrice, int bClassify) {
		super();
		OId = oId;
		BId = bId;
		UIdd = uIdd;
		ONumber = oNumber;
		ODate = oDate;
		OAppraise = oAppraise;
		BName = bName;
		BPrice = bPrice;
		BClassify = bClassify;
	}

	public int getBClassify() {
		return BClassify;
	}

	public void setBClassify(int bClassify) {
		BClassify = bClassify;
	}

	public String getBName() {
		return BName;
	}

	public void setBName(String bName) {
		BName = bName;
	}

	public int getUIdd() {
		return UIdd;
	}

	public void setUIdd(int uIdd) {
		UIdd = uIdd;
	}

	public OrderGood() {
		// TODO Auto-generated constructor stub
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

	public int getONumber() {
		return ONumber;
	}

	public void setONumber(int oNumber) {
		ONumber = oNumber;
	}

	public String getODate() {
		return ODate;
	}

	public void setODate(String oDate) {
		ODate = oDate;
	}

	public String getOAppraise() {
		return OAppraise;
	}

	public void setOAppraise(String oAppraise) {
		OAppraise = oAppraise;
	}

	public double getBPrice() {
		return BPrice;
	}

	public void setBPrice(double bPrice) {
		BPrice = bPrice;
	}
}
