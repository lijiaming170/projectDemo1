package com.shop.domain;

import java.util.HashMap;
import java.util.Map;

public class Cart {
	private Map<String,Cartitem> cartmap=new HashMap<String,Cartitem>();
	private double total;
	public Map<String, Cartitem> getCartmap() {
		return cartmap;
	}
	public void setCartmap(Map<String, Cartitem> cartmap) {
		this.cartmap = cartmap;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	

}
