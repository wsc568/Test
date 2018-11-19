package com.model;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private int UID;
	private String UName;
	private String UPassword;
	private double UBalance;
	private long UTel;
	private long UIdentify;
	private String UAddress;
	private int UClassify;
	public User(int uID, String uName, String uPassword, double uBalance,
			long uTel, long uIdentify, String uAddress, int uClassify) {
		super();
		UID = uID;
		UName = uName;
		UPassword = uPassword;
		UBalance = uBalance;
		UTel = uTel;
		UIdentify = uIdentify;
		UAddress = uAddress;
		UClassify = uClassify;
	}

	

	public int getUClassify() {
		return UClassify;
	}

	public void setUClassify(int uClassify) {
		UClassify = uClassify;
	}

	public long getUTel() {
		return UTel;
	}

	public void setUTel(long uTel) {
		UTel = uTel;
	}

	public String getUAddress() {
		return UAddress;
	}

	public void setUAddress(String uAddress) {
		UAddress = uAddress;
	}



	public long getUIdentify() {
		return UIdentify;
	}

	public void setUIdentify(long uIdentify) {
		UIdentify = uIdentify;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public int getUID() {
		return UID;
	}

	public void setUID(int uID) {
		UID = uID;
	}

	public String getUName() {
		return UName;
	}

	public void setUName(String uName) {
		UName = uName;
	}

	public String getUPassword() {
		return UPassword;
	}

	public void setUPassword(String uPassword) {
		UPassword = uPassword;
	}

	public double getUBalance() {
		return UBalance;
	}

	public void setUBalance(double uBalance) {
		UBalance = uBalance;
	}
}
