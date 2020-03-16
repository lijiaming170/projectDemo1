package com.shop.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;
import com.shop.domain.Cart;
import com.shop.domain.Cartitem;
import com.shop.domain.Category;
import com.shop.domain.Order;
import com.shop.domain.Orderitem;
import com.shop.domain.PageBean;
import com.shop.domain.Product;
import com.shop.domain.User;
import com.shop.service.CategoryService;
import com.shop.service.ProductService;
import com.shop.utils.CommentUtils;
import com.shop.utils.PaymentUtil;
import com.shop.utils.RidesUtils;

import redis.clients.jedis.Jedis;

public class ProductServlet extends BasicServlet {
           /*
            * 这是抽取出来的方法*/
	/*public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String methodname=request.getParameter("method");
		if("index".equals(methodname)){
			index(request,response);
		}
		else if("productinfo".equals(methodname)){
			productinfo(request,response);
		}
		else if("productlistbycid".equals(methodname)){
			productlistbycid(request,response);
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}*/
	public  void lookMyOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   HttpSession session = request.getSession();
	        User user=(User) session.getAttribute("user");
	        if(user==null){
	        	//如果用户是空的，那么让用户先登陆在提价订单。
	        	//跳转到登陆页面
	        	response.sendRedirect(request.getContextPath()+"/login.jsp");
	        	//下面的操作就不需要执行了
	        	return;
	        }
	        //通过数据库查询到orderlist byuid
	        String uid=user.getUid();
	        ProductService service=new ProductService();
	        List<Order>orderlist=service.findAllOrdersByUid(uid);
	        //遍历orderlist往里面添加缺少的orderitem list
	        for(Order order:orderlist){
	        	String oid=order.getOid();
	        	List<Map<String,Object>>productAndOrderitemsList=service.findProductAndOrderitemsListByOid(oid);
	        	for(Map<String,Object> map:productAndOrderitemsList){
	        		//开始封装数据
	        		try {
	        			Product product =new Product();
	        			Orderitem orderitem=new Orderitem();
	        			BeanUtils.populate(product, map);//map向product中填充数据
						BeanUtils.populate(orderitem, map);//map向orderitem中填充数据
						orderitem.setProduct(product);//把product填充到ordreitem中
//	        		List<Orderitem>orderitemlist=new ArrayList<Orderitem>();
						order.getOrderitem().add(orderitem);
					} catch (IllegalAccessException | InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		
	        	}
	        }
	        session.setAttribute("orderlist", orderlist);
	        response.sendRedirect(request.getContextPath()+"/order_list.jsp");
	        		
	        
	}
	//提交订单的表单lookMyOrders
	public  void orderForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   HttpSession session = request.getSession();
		String address=request.getParameter("address");
	     String name=request.getParameter("name");
	     String telephone=request.getParameter("telephone");
	     ProductService service=new ProductService();
	     Order order=(Order) session.getAttribute("order");
	     //提交收货人的信息
	     String oid=order.getOid();
	     //需要加入检验数据库当中是否已经提交了这个订单，（在字符失败的情况下 再次确认提交的情况下 不会二次提交
	     /*
	      * 
	      * 
	      * 
	      * 
	     */
	     service.addorderAddress(address,name,telephone,oid);
	     
	     
	     
	  // 获得 支付必须基本数据
			String orderid =oid;
			//String money = order.getTotal()+"";
		     String money="0.01";
			// 银行
			String pd_FrpId = request.getParameter("pd_FrpId");

			// 发给支付公司需要哪些数据
			String p0_Cmd = "Buy";
			String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
			String p2_Order = orderid;
			String p3_Amt = money;
			String p4_Cur = "CNY";
			String p5_Pid = "";
			String p6_Pcat = "";
			String p7_Pdesc = "";
			// 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
			// 第三方支付可以访问网址
			String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("callback");
			String p9_SAF = "";
			String pa_MP = "";
			String pr_NeedResponse = "1";
			// 加密hmac 需要密钥
			String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
					"keyValue");
			String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
					p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
					pd_FrpId, pr_NeedResponse, keyValue);
			
			
			String url = "https://www.yeepay.com/app-merchant-proxy/node?pd_FrpId="+pd_FrpId+
							"&p0_Cmd="+p0_Cmd+
							"&p1_MerId="+p1_MerId+
							"&p2_Order="+p2_Order+
							"&p3_Amt="+p3_Amt+
							"&p4_Cur="+p4_Cur+
							"&p5_Pid="+p5_Pid+
							"&p6_Pcat="+p6_Pcat+
							"&p7_Pdesc="+p7_Pdesc+
							"&p8_Url="+p8_Url+
							"&p9_SAF="+p9_SAF+
							"&pa_MP="+pa_MP+
							"&pr_NeedResponse="+pr_NeedResponse+
							"&hmac="+hmac;

			//重定向到第三方支付平台
			response.sendRedirect(url);
		
	}//加载菜单列表的方法 使用ajax动态传数据
	public  void categorylist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	    Jedis jdis=RidesUtils.getjedis();
	    String  categorylist1 = null;
	    categorylist1 = jdis.get("categorylist1");
	 //先从redies中获取，如果没有那么从数据库中获得然后放在redies缓冲当中
	    if(categorylist1==null){
	    	System.out.println("rides中缓存没有数据从数据库中获得");
	    	CategoryService categoryservice=new CategoryService();
			List<Category>categorylist=categoryservice.findcategorylist();
			Gson json=new Gson();
			 categorylist1 = json.toJson(categorylist);
	/*		System.out.println(categorylist1);*/
			 jdis.set("categorylist1", categorylist1);
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(categorylist1);
	    }
	    //如果存在直接从里面获取
	    else{
	    	System.out.println("这是从redies中获得的数据");
	        categorylist1 = jdis.get("categorylist1");
	    	response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(categorylist1);
	    }
		/*CategoryService categoryservice=new CategoryService();
		List<Category>categorylist=categoryservice.findcategorylist();
		Gson json=new Gson();
		String categorylist1 = json.toJson(categorylist	System.out.println(categorylist1);
			response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(categorylist1);*/
		//request.setAttribute("categorylist", categorylist);
	}
	//提交订单 数据到数据库
		public  void orderCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        HttpSession session = request.getSession();
	        User user=(User) session.getAttribute("user");
	        if(user==null){
	        	//如果用户是空的，那么让用户先登陆在提价订单。
	        	//跳转到登陆页面
	        	response.sendRedirect(request.getContextPath()+"/login.jsp");
	        	//下面的操作就不需要执行了
	        	return;
	        }
	        else{
	        	Order order=new Order();
	        	//准备Order数据
	        	// 1.private String oid;//订单的编号
	        	order.setOid(CommentUtils.getUuid());
	        	//private Date ordertime;
	        	order.setOrdertime(new Date());
	        	//private double total;该订单的总金额
	        	Cart cart=(Cart) session.getAttribute("cart");
	        	order.setTotal(cart.getTotal());
	        	//private int state;//表示支付状态1表示字符 0 表示没有支付
	        	order.setState(0);
	        	//private String address;//地址
	        	order.setAddress(null);
	        	//private String name;
	        	order.setName("null");
	        	//private String telephone;
	        	order.setTelephone(null);
	        	//private User user;//该订单属于哪一个用户的
	        	order.setUser(user);
	        	
/*	        orderitemz中的数据	private String itemid;//订单项的ID
	        	private int count;//商品的数量
	        	private double subtotal;//小计费用
	        	private Product product;//商品的id
	        	private Order order;//属于订单的ID
*/	        	
	        	
	        	/*private Product product;
	              private int buyNum;
	             private double tatalsub;*/
	        	Map<String,Cartitem>cartitem=cart.getCartmap();
	        	for(Map.Entry<String,Cartitem>entry:cartitem.entrySet()){
	        		Orderitem orderitem=new Orderitem();
	        		orderitem.setCount(entry.getValue().getBuyNum());//商品数量
	        		orderitem.setProduct(entry.getValue().getProduct());//商品的ID
	        		orderitem.setSubtotal(entry.getValue().getTatalsub());//总金额
	        		orderitem.setItemid(CommentUtils.getUuid());
	        		orderitem.setOrder(order);
	        		order.getOrderitem().add(orderitem);
	        		//数据全部封装好
	        	}
	       
	        ProductService service=new ProductService();
	              service.ordercart(order);
	           
	              session.setAttribute("order", order);
	              response.sendRedirect(request.getContextPath()+"/order_info.jsp");
	        }
	      
		}
	        	
	        	//private List<String>orderitem=new ArrayList<String>();//订单中有哪些订单项,遍历cart 将itemcart取出来，然后存放
	        
	 
			//开始准备数据
	

	//清空购物车
	public  void clearCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		//从域中拿到存储的cart对象
	    Cart cart= (Cart) session.getAttribute("cart");
	      Map<String,Cartitem>cartmap=cart.getCartmap();
	      cartmap.clear();
	      cart.setCartmap(cartmap);
	      cart.setTotal(0.0);
	      session.setAttribute("cart", cart);
	      response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}//删除购物项
	public  void delItemFromCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String pid=request.getParameter("pid");
		HttpSession session = request.getSession();
		//从域中拿到存储的cart对象
	    Cart cart= (Cart) session.getAttribute("cart");
	      Map<String,Cartitem>cartmap=cart.getCartmap();
	      cart.setTotal(cart.getTotal()-cartmap.get(pid).getTatalsub());
	      cartmap.remove(pid);
	      cart.setCartmap(cartmap);
	      session.setAttribute("cart", cart); 
	      response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}
	//添加商品数量去购物车
	public void addProductToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   String pid=request.getParameter("pid");
	   String buyNum=request.getParameter("buyNum");
	   ProductService service=new ProductService();
	   Product product = service.findProductInfo(pid);
	   double totalsub=(product.getShop_price())*(Integer.parseInt(buyNum));
	   Cartitem item=new Cartitem();
	   item.setProduct(product);
	   item.setBuyNum(Integer.parseInt(buyNum));
	   item.setTatalsub(totalsub);
	    HttpSession session = request.getSession();
	   Cart cart=(Cart) session.getAttribute("cart");
	   double newtotalsub=0.0;
	   if(cart==null){
		   cart=new Cart();         //第一次访问 没有cart对象 那么需要创建
	   } 
	   Map<String,Cartitem>cartmap=cart.getCartmap();
	   if(cartmap.containsKey(pid)){        //判断是否存在 ，如果存相同的商品 ，那么更改cartitem的buyNUm和totalsub的值
		    Cartitem cartitem=cartmap.get(pid);
		    //将原来的值取出来 加上现在的
		   int totalbuynum=cartitem.getBuyNum()+Integer.parseInt(buyNum);
		   cartitem.setBuyNum(totalbuynum);
		   //将小计加起来
		   double totalsamesub=cartitem.getTatalsub()+(product.getShop_price())*(Integer.parseInt(buyNum));
		   cartitem.setTatalsub(totalsamesub);
		    newtotalsub =(product.getShop_price())*(Integer.parseInt(buyNum));
		   cart.setCartmap(cartmap);
	   } else{
		   cartmap.put(pid, item);           //不存在那么久直接添加
		     cart.setCartmap(cartmap);
		     newtotalsub=(product.getShop_price())*(Integer.parseInt(buyNum));
	   }
	     double total=cart.getTotal()+newtotalsub;
	     cart.setTotal(total);
	     session.setAttribute("cart", cart);
		/*   Map<String,Cartitem>cartmap=cart.getCartmap();
		   if(cartmap.containsKey(pid)){
			   cartitem=cartmap.get(pid);
			   int n=cartitem.getBuyNum()+Integer.parseInt(buyNum);
			   cartitem.setBuyNum(n);
			   double j=cartitem.getTatalsub()+(product.getShop_price())*(Integer.parseInt(buyNum));
			   cartitem.setTatalsub(j);
			   double k=cart.getTotal()+(product.getShop_price())*(Integer.parseInt(buyNum));
			   cart.setTotal(k);
			  session.setAttribute("cart", cart);
		   }
		   else{
			   cartmap=cart.getCartmap();
			   cartitem.setProduct(product);
			   cartitem.setBuyNum(Integer.parseInt(buyNum));
			   cartitem.setTatalsub(totalsub);
			   double count=cart.getTotal()+cartitem.getTatalsub();
			   cart.setTotal(count);
			   session.setAttribute("cart", cart);
		   }
	   }
	*/
	      response.sendRedirect(request.getContextPath()+"/cart.jsp");
	     }

	//访问主页的方法
	public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 /*获得最新
	      * 最热的商品*/
			ProductService service=new ProductService();
			List<Product>hotproductlist=service.findhotproductlist();
			request.setAttribute("hotproductlist", hotproductlist);//将准备的数据存储在request域当中
		
			List<Product>newproductlist=service.findnewproductlist();
			request.setAttribute("newproductlist", newproductlist);
			/*CategoryService categoryservice=new CategoryService();
			List<Category>categorylist=categoryservice.findcategorylist();
			Gson json=new Gson();
			String categorylist1 = json.toJson(categorylist);
			System.out.println(categorylist1);
			response.setContentType("text/html;charset=UTF-8");
			System.out.println();
			response.getWriter().write(categorylist1);*/
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			/*
			 *查询商品分类list*/
	}    
	//商品详情的列表
	public void productinfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pid = request.getParameter("pid");
		String cid=request.getParameter("cid");
		String currentpage=request.getParameter("currentpage");
		request.setAttribute("cid", cid);
		request.setAttribute("currentpage", currentpage);
		ProductService productService=new ProductService();
		Product product=productService.findProductInfo(pid);
		request.setAttribute("product", product);
		HttpSession session = request.getSession();
		/*
		 * 
		 * 
		 * 1.利用session域来存储那个pid 存在一个list集合当中
		 * 2.利用集合里面的pid判断是否存在，来进行操作3.
		 * 3.注意那个peoductlist集合是每次都new一个，不需要把其存在session中
		 *  */
		LinkedList<String>pidlist=( LinkedList<String>)session.getAttribute("pidlist");
		if(pidlist==null){
			pidlist=new LinkedList<String>();
			pidlist.add(pid);
			List<Product>historyproductlsit=new ArrayList<Product>();
			
			historyproductlsit.add(productService.findProductInfo(pid));
			session.setAttribute("pidlist", pidlist);
			   session.setAttribute("historyproductlsit", historyproductlsit);
			}
		else{
			pidlist=(LinkedList<String>) session.getAttribute("pidlist");
			if(pidlist.contains(pid)){
				pidlist.remove(pid);
				pidlist.addFirst(pid);
				List<Product>historyproductlsit=new ArrayList<Product>();
				   for(int i=0;i<pidlist.size();i++){
					   historyproductlsit.add(productService.findProductInfo(pidlist.get(i)));
				   }
				   session.setAttribute("historyproductlsit", historyproductlsit);
			}
			else{
				pidlist.addFirst(pid);
				List<Product>historyproductlsit=new ArrayList<Product>();
				  for(int i=0;i<pidlist.size();i++){
					   historyproductlsit.add(productService.findProductInfo(pidlist.get(i)));
				   }
				   session.setAttribute("historyproductlsit", historyproductlsit);
			}
 	}
		   request.getRequestDispatcher("/product_info.jsp").forward(request, response);
		
	}
	
	public void productlistbycid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cid=request.getParameter("cid");
		request.setAttribute("cid", cid);
		ProductService  service=new ProductService();
		int currentcount=12;
		int currentpage=0;
		String current=request.getParameter("currentpage");
//		System.out.println(current+"-----current");
		if(current==null||current.equals("")){
			currentpage=1;
		}else{
			currentpage=Integer.parseInt(current);
		}
		  PageBean pageBean=service.findProductListByCid(cid,currentpage,currentcount);
		  List<Product>list=pageBean.getProductlistbycid();
		 /* for(Product pro:list){
			  System.out.println(pro);
			  
		  }*/
		 
		  request.setAttribute("pageBean", pageBean);
		  request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}
}