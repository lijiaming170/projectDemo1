package com.shop.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shop.domain.Product;
import com.shop.service.ProductService;

public class ProductInfoServlet extends HttpServlet {

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		 * 4.历史记录就是存储pid 
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
		
		
		
		
		
	/*
	 * 利用session域来存储历史记录
	 * 因为list存储product集合，无法判断他是否存在*/

		/* LinkedList<Product>historyproductlsit=(LinkedList<Product>) session.getAttribute("historyproductlsit");
		   if(historyproductlsit==null){
			  historyproductlsit=new LinkedList<Product>();
			  historyproductlsit.addFirst(product);
			  session.setAttribute("historyproductlsit", historyproductlsit);
		      }
		   else{
			   historyproductlsit=(LinkedList<Product>) session.getAttribute("historyproductlsit");
			   if(historyproductlsit.contains(product)){//无法判断里面是否有这个元素，只是进行字符串比较
		    	   historyproductlsit.remove(product);
		    	   historyproductlsit.addFirst(product);
		    	   System.out.println("66666");
		       }else{
		    	   historyproductlsit.addFirst(product);
		    	   System.out.println("fodadad"+historyproductlsit.contains(product));
		            }
		   }
		 
		   session.setAttribute("historyproductlsit", historyproductlsit);
	
		   request.getRequestDispatcher("/product_info.jsp").forward(request, response);

	*/

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}