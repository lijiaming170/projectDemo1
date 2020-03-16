package com.shop.domain;

public class Cartitem {
	private Product product;
	private int buyNum;
	private double tatalsub;
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}
	public double getTatalsub() {
		return tatalsub;
	}
	public void setTatalsub(double tatalsub) {
		this.tatalsub = tatalsub;
	}

}
