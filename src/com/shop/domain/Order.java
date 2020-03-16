package com.shop.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
	//封装订单的实体
           /*
            * `oid` varchar(32) NOT NULL,
  `ordertime` datetime DEFAULT NULL,
  `total` double DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `address` varchar(30) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `uid` varchar(32) DEFAULT NULL*/
	
	private String oid;//订单的编号
	private Date ordertime;
	private double total;//该订单的总金额
	private int state;//表示支付状态1表示字符 0 表示没有支付
	private String address;//地址
	private String name;
	private String telephone;
	private User user;//该订单属于哪一个用户的
	private List<Orderitem>orderitem=new ArrayList<Orderitem>();//订单中有哪些订单项
	
	public List<Orderitem> getOrderitem() {
		return orderitem;
	}
	public void setOrderitem(List<Orderitem> orderitem) {
		this.orderitem = orderitem;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Date getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Order [oid=" + oid + ", ordertime=" + ordertime + ", total=" + total + ", state=" + state + ", address="
				+ address + ", name=" + name + ", telephone=" + telephone + ", user=" + user + ", orderitem="
				+ orderitem + "]";
	}
	
}
