package com.shop.web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.shop.domain.Category;
import com.shop.domain.Product;
import com.shop.service.AdminService;
import com.shop.utils.CommentUtils;

public class AddProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    //接受文件上传的多表单
		//创建磁盘文件项工厂
		request.setCharacterEncoding("UTF-8");
		DiskFileItemFactory factory=new DiskFileItemFactory();
		//创建磁盘核心类
		ServletFileUpload upload=new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		try {
			//解析request
			List<FileItem>fileitem=upload.parseRequest(request);
			Product product=new Product();
			Map<String,Object>map=new HashMap<String,Object>();
			for(FileItem item:fileitem){
				boolean formField = item.isFormField();//判断是否是普通文件
				if(formField){
					//是普通文件
					String filename=item.getFieldName();
					String filenamevalue=item.getString("UTF-8");
					map.put(filename, filenamevalue);
				}else{
					//不是普通文件
		        	String filedname=item.getName();
					InputStream in=item.getInputStream();
					String path=this.getServletContext().getRealPath("load");
					OutputStream out=new FileOutputStream(path+"/"+filedname);
					//文件名字
					
					map.put("pimage", "load/"+filedname);
					IOUtils.copyLarge(in, out);
					out.close();
					in.close();
					item.delete();
				}
			}
			try {
				//一个product中只有一个产品对象，一个category中有多个product 
				Category category=new Category();
				category.setCid(map.get("cid").toString());
				BeanUtils.populate(product, map);
				product.setPid(CommentUtils.getUuid());
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");//格式化日期
				Date date=new Date();
				String time=format.format(date);
				product.setCategory(category);
				product.setPdate(time);
				product.setPflag(1);
				AdminService service=new AdminService();
				
				service.addproduct(product);
			} catch (IllegalAccessException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}