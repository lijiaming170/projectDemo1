<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<HTML>
<HEAD>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="css/style.css" type="text/css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
<script language="javascript"
	src="${pageContext.request.contextPath}/js/public.js"></script>
<script type="text/javascript">
	function addProduct() {
		window.location.href = "${pageContext.request.contextPath}/admin/product/add.jsp";
	}
</script>
</HEAD>
<body>
	<br>
	<form id="Form1" name="Form1"
		action="${pageContext.request.contextPath}/user/list.jsp"
		method="post">
		<table cellSpacing="1" cellPadding="0" width="100%" align="center"
			bgColor="#f5fafe" border="0">
			<TBODY>
				<tr>
					<td class="ta_01" align="center" bgColor="#afd1f3"><strong>商品列表</strong>
					</TD>
				</tr>
				<tr>
					<td class="ta_01" align="right">
						<button type="button" id="add" name="add" value="添加"
							class="button_add" onclick="addProduct()">
							&#28155;&#21152;</button>

					</td>
				</tr>
				<tr>
					<td class="ta_01" align="center" bgColor="#f5fafe">
						<table cellspacing="0" cellpadding="1" rules="all"
							bordercolor="gray" border="1" id="DataGrid1"
							style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
							<tr
								style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">

								<td align="center" width="18%">序号</td>
								<td align="center" width="17%">商品图片</td>
								<td align="center" width="17%">商品名称</td>
								<td align="center" width="17%">商品价格</td>
								<td align="center" width="17%">是否热门</td>
								<td width="7%" align="center">编辑</td>
								<td width="7%" align="center">删除</td>
							</tr>
							<c:forEach items="${pagebean.productlistbycid }" var="pro"
								varStatus="vs">
								<tr onmouseover="this.style.backgroundColor = 'white'"
									onmouseout="this.style.backgroundColor = '#F5FAFE';">
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="18%">${vs.count}</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="17%"><img
										src="${pageContext.request.contextPath }/${pro.pimage}"
										width="40" height="45" src=""></td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="17%">${pro.pname }</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="17%">${pro.shop_price}</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="17%">${pro.is_hot==1?"是":"否"}</td>
									<td align="center" style="HEIGHT: 22px"><a
										href="${ pageContext.request.contextPath }/admin?method=updateproductui&pid=${pro.pid}">
											<img
											src="${pageContext.request.contextPath}/images/i_edit.gif"
											border="0" style="CURSOR: hand">
									</a></td>

									<td align="center" style="HEIGHT: 22px"><a href="#"> <img
											src="${pageContext.request.contextPath}/images/i_del.gif"
											width="16" height="16" border="0" style="CURSOR: hand">
									</a></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</TBODY>
		</table>

		<!--分页 -->
		<div style="width: 380px; margin: 0 auto; margin-top: 20px;">
			<!-- <ul class="pagination" style="text-align: center; margin-top: 10px;">
						<li class="disabled"><a href="#" aria-label="Previous"><span
								aria-hidden="true">&laquo;</span></a></li>
						<li class="active"><a href="#">1</a></li>
						<li><a href="#">2</a></li>
						<li><a href="#">3</a></li>
						<li><a href="#">4</a></li>
						<li><a href="#">5</a></li>
						<li><a href="#">6</a></li>
						<li><a href="#">7</a></li>
						<li><a href="#">8</a></li>
						<li><a href="#">9</a></li>
						<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
						</a></li>
					</ul> -->
			<ul class="pagination" style="text-align: center; margin-top: 10px;">
			<!-- 上一页的设置 -->
				<c:if test="${currentpage==1}">
					<li class="disabled"><a href="javascript:void(0)"
						aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
				</c:if>
				<c:if test="${currentpage!=1}">
					<li><a
						href="${pageContext.request.contextPath}/admin?method=findAllproductlist&currentpage=${currentpage-1}"
						aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
				</c:if>

				<c:forEach begin="1" end="${pagebean.totalpage}" var="page">

					<c:if test="${pagebean.currentpage!=page}">
						<li><a
							href="${pageContext.request.contextPath}/admin?method=findAllproductlist&currentpage=${page}">${page}</a></li>
					</c:if>
					<c:if test="${pagebean.currentpage==page}">
						<li class="active"><a href="javascript:void(0)">${page}</a></li>
					</c:if>
				</c:forEach>
				<!-- 下一页的设置 -->
				<c:if test="${currentpage==pagebean.totalpage}">
					<li class="disabled"><a href="javascript:void(0)"
						aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
				</c:if>
				<c:if test="${currentpage!=pagebean.totalpage}">
					<li><a
						href="${pageContext.request.contextPath}/admin?method=findAllproductlist&currentpage=${currentpage+1}"
						aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
				</c:if>
			</ul>
		</div>
		<!-- 分页结束 -->
	</form>
</body>
</HTML>

