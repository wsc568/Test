package com.model;

import java.io.Serializable;

public class Appraise implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String UName;
	private String OAppraise;

	public Appraise(String uName, String oAppraise) {
		super();
		UName = uName;
		OAppraise = oAppraise;
	}

	public Appraise() {
	}

	public String getUName() {
		return UName;
	}

	public void setUName(String uName) {
		UName = uName;
	}

	public String getOAppraise() {
		return OAppraise;
	}

	public void setOAppraise(String oAppraise) {
		OAppraise = oAppraise;
	}

}
