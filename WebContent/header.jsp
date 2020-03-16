<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="com.shop.service.*" %>
<%@ page import=" com.shop.domain.*" %>

<!DOCTYPE html>
<script type="text/javascript">
 $(function(){
	$.post("${pageContext.request.contextPath}/product?method=categorylist",
			function(data){
        //   [{"cid":"","pid":""},{},{},{}]
		 var content="";
		for(var i=0;i<data.length;i++){
          content+="<li><a href='${pageContext.request.contextPath}/product?cid="+data[i].cid+"&method=productlistbycid'>"+data[i].cname+"</a></li>"	
		}
		$("#categoryul").html(content);
	},"json") 
	}); 
	
	
	 /* $.ajax({
		async:true,
		type:"POST",
		dataType:"json",
		url:"${pageContext.request.contextPath}/categorylist",
		success:function(data){
             //   [{"cid":"","pid":""},{},{},{}]
			 var content="";
			for(var i=0;i<data.length;i++){
               content+="<li><a href="#">"+${data[i].cname}+"</a></li>"	
			}
			$("#categoryul").html(content);
		}
	});  */  
/* }); */
 function checkExit(){
	if(confirm("您是否要确认退出")){
		location.href="${pageContext.request.contextPath}/user?method=exituser"
	}
}
</script>

<!-- 登录 注册 购物车... -->
	<!-- 使用jquery方法无效果 -->

<div class="container-fluid">
	<div class="col-md-4">
		<img src="img/logo2.png" />
	</div>
	<div class="col-md-5">
		<img src="img/header.png" />
	</div>
	<div class="col-md-3" style="padding-top:20px">
	<c:if test="${empty user}">
		<ol class="list-inline">
			<li><a href="login.jsp">登录</a></li>
			<li><a href="register.jsp">注册</a></li>
			<li><a href="cart.jsp">购物车</a></li>
			<li><a href="order_list.jsp">我的订单</a></li>
		</ol>
		</c:if>
		<c:if test="${!empty user}">
			<ol class="list-inline">
		<li><a >欢迎您！</a></li>
		<li><a >${user.username }</a></li>
		<li><a href="cart.jsp">购物车</a></li>
			<li><a href="${pageContext.request.contextPath}/product?method=lookMyOrders">我的订单</a></li>
			<li><a href="javascript:void(0)" onclick="checkExit()">退出登陆</a></li>
		</ol>
		</c:if>
	</div>
</div>

<!-- 导航条 -->
<div class="container-fluid">
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">首页</a>
			</div>

			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
		<%-- 	<% CategoryService categoryservice=new CategoryService();
		List<Category>categorylist=categoryservice.findcategorylist();
		 request.setAttribute("categorylist", categorylist);%> --%>
		 	<ul class="nav navbar-nav" id="categoryul">
			<%-- 	<ul class="nav navbar-nav" id="categoryul">
			<c:forEach items="${categorylist}" var="catepro">
				<!--这种方式是post提交的方法 -->
					<li><a href="${pageContext.request.contextPath}/product?cid=${catepro.cid}&method=productlistbycid">${catepro.cname}</a></li>
					</c:forEach> --%>
					
					
				</ul>
				<form class="navbar-form navbar-right" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search">
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>
		</div>
	</nav>
</div>
