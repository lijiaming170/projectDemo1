package com.shop.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.shop.domain.Category;
import com.shop.domain.Order;
import com.shop.domain.PageBean;
import com.shop.domain.Product;
import com.shop.service.AdminService;
import com.shop.service.CategoryService;
import com.shop.service.ProductService;

/**
 * Servlet implementation class AdminServlet
 */
public class AdminServlet extends BasicServlet  {
	/*后台控制模块*/
	//添加商品的时候动态回显商品的分类findAllorders
	public  void findAllcategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	AdminService service=new AdminService();
	List<Category>categorylist=service.findAllcategory();
	Gson gson=new Gson();
	String data=gson.toJson(categorylist);//获得商品信息的json数据格式
	response.getWriter().write(data);
	


}
	
	public  void findAllorders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
		AdminService service=new AdminService();
      List<Order>orderlist=service.findAllorders();
      /*for(Order order:orderlist){
    	  System.out.println(order);
      }*/
      request.setAttribute("orderlist", orderlist);

      request.getRequestDispatcher("/admin/order/list.jsp").forward(request, response);
	}//findOrderInfoByOid

	public  void findOrderInfoByOid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String oid=request.getParameter("oid");
	     request.setAttribute("oid",oid );//将数据传到后台
		AdminService service=new AdminService();
	
		List<Map<String, Object>>orderitemAndProductllist=service.orderitemAndProductllist(oid);
		Order order=new Order();
		/*for(Map<String,Object> map:orderitemAndProductllist){
    		//开始封装数据
    		try {
    			Product product =new Product();
    			Orderitem orderitem=new Orderitem();
    		BeanUtils.populate(product, map);//map向product中填充数据
			BeanUtils.populate(orderitem, map);//map向orderitem中填充数据
				orderitem.setProduct(product);//把product填充到ordreitem中
   		List<Orderitem>orderitemlist=new ArrayList<Orderitem>();
				order.getOrderitem().add(orderitem);
			} catch (IllegalAccessException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		
		response.setContentType("text/html;charset=UTF-8");
	    Gson gson=new Gson();
	     String orderitems= gson.toJson(orderitemAndProductllist);
	     response.getWriter().write(orderitems);
	    
		
      }
	//查询所有的productlist商品的详细信息
	public  void findAllproductlist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		/*AdminService service = new AdminService();
		List<Product>productlist=service.findAllproductlist();
		request.setAttribute("productlist", productlist);
		request.getRequestDispatcher("/admin/product/list.jsp").forward(request, response);*/
		String currentpage=request.getParameter("currentpage");
		int currentpage1=0;
		if(currentpage==null||"".equals(currentpage)){
			currentpage1=1;
		}
		else{
			currentpage1=Integer.parseInt(currentpage);
		}
		int currentcount=7;
		request.setAttribute("currentpage",currentpage1);
		AdminService service = new AdminService();
		PageBean pagebean=service.getPagebean( currentpage1, currentcount);
		//System.out.println(pagebean);
		request.setAttribute("pagebean", pagebean);
		request.getRequestDispatcher("/admin/product/list.jsp").forward(request, response);
	}
	
	//updateproduct添加商品的编辑的回显示,后台信息的显
	public  void updateproductui(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//产品分类的回显
		CategoryService categoryService=new CategoryService ();
		List<Category>categorylist=categoryService.findcategorylist();
		request.setAttribute("categorylist", categorylist);
		String pid=request.getParameter("pid");
		ProductService productService=new ProductService();
		Product product=productService.findproductBypid(pid);
		request.setAttribute("product", product);
		request.getRequestDispatcher("/admin/product/edit.jsp").forward(request, response);
	
	
	}
	}
	
