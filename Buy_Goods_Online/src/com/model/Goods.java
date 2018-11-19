package com.model;

import java.io.Serializable;

public class Goods implements Serializable {

	private static final long serialVersionUID = 1000000;
	private int BID;
	private String BName;
	private double BPrice;
	private int BStock;
	
	private String BDescripe;
	private int BClassify;
	private int BBelonguse;

	public Goods(int bID, String bName, double bPrice, int bStock,
			String bDescripe, int bClassify, int bBelonguse) {
		super();
		BID = bID;
		BName = bName;
		BPrice = bPrice;
		BStock = bStock;
		BDescripe = bDescripe;
		BClassify = bClassify;
		BBelonguse = bBelonguse;
	}

	public int getBStock() {
		return BStock;
	}

	public void setBStock(int bStock) {
		BStock = bStock;
	}

	
	public int getBBelonguse() {
		return BBelonguse;
	}

	public void setBBelonguse(int bBelonguse) {
		BBelonguse = bBelonguse;
	}

	public Goods() {
		// TODO Auto-generated constructor stub
	}

	public int getBID() {
		return BID;
	}

	public void setBID(int bID) {
		BID = bID;
	}

	public String getBName() {
		return BName;
	}

	public void setBName(String bName) {
		BName = bName;
	}

	public double getBPrice() {
		return BPrice;
	}

	public void setBPrice(double bPrice) {
		BPrice = bPrice;
	}

	public String getBDescripe() {
		return BDescripe;
	}

	public void setBDescripe(String bDescripe) {
		BDescripe = bDescripe;
	}

	public int getBClassify() {
		return BClassify;
	}

	public void setBClassify(int bClassify) {
		BClassify = bClassify;
	}
}
