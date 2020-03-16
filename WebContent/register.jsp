<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员注册</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/jquery.validate.min.js"></script>
<script type="text/javascript" src="js/messages_zh.js"></script> 
<script src="js/bootstrap.min.js" type="text/javascript"></script>

<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="css/style.css" type="text/css" />

<style>
body {
	margin-top: 20px;
	margin: 0 auto;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}

font {
	color: #3164af;
	font-size: 18px;
	font-weight: normal;
	padding: 0 10px;
}
.error{
color:red;
}
</style>
<script type="text/javascript">
/* 自定义校验规则*/
  $.validator.addMethod("checkusername",
		function(value,element,params){
	      var fog=false;
	        $.ajax({
	        	async:false,
	        	type:"POST",
	        	data:{"username":value},
	        	dataType:"json",
	        	success:function(data){
	        		fog=data.folg;
	        	},
	        	url:"${pageContext.request.contextPath}/user?method=checkusername"
	        });
	        //返回false表示校验器不能通过
	      
	        return !fog; 
});
 $(function(){
	 $("#myform").validate({
		 rules:{
			 "username":{
				 "required":true,
		     "checkusername":true
			 },
		  "password":{
				 "required":true,
				 "rangelength":[5,9]
			 },
			  "reqpassword":{
				 "equalTo":"#password"
			 } ,
			 "email":{
				 "required":true,
				 "email":true
			 },
			 "name":{
				 "required":true
			 },
			 "birthday":{
				 "dateISO":true
			 },
			 "sex":{
				 "required":true
			 }
			 
		 },
		 messages:{
			 "username":{
				 "required":"用户名不能为空",
				 "checkusername":"该用户名已经存在"
			 },
			 "password":{
				 "required":"密码不能为空",
				 "rangelength":"密码长度设置5-9位"
			 },
			 "reqpassword":{
				 "equalTo":"两次密码不相同"
			 }  ,
			 "email":{
				 "required":"邮箱不能为空",
				 "email":"邮箱格式不正确"
			 },
			 "name":{
				 "required":"名字不能为空",
			 },
			 "birthday":{
				 "dateISO":"格式不正确"
			 }
			 
			  
		 }
		 
		 
	 });
	 
 });

</script>
</head>
<body>

	<!-- 引入header.jsp -->
	<jsp:include page="/header.jsp"></jsp:include>

	<div class="container"
		style="width: 100%; background: url('image/regist_bg.jpg');">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8"
				style="background: #fff; padding: 40px 80px; margin: 30px; border: 7px solid #ccc;">
				<font>会员注册</font>USER REGISTER
				<form  id="myform" class="form-horizontal" style="margin-top: 5px;" method="post" action="${pageContext.request.contextPath}/user?method=register">
					<div class="form-group">
						<label for="username" class="col-sm-2 control-label">用户名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="username"
								placeholder="请输入用户名" name="username">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label">密码</label>
						<div class="col-sm-6">
							<input type="password" class="form-control" id="password"
								placeholder="请输入密码" name="password">
						</div>
					</div>
					<div class="form-group">
						<label for="confirmpwd" class="col-sm-2 control-label">确认密码</label>
						<div class="col-sm-6">
							<input type="password" class="form-control" id="confirmpwd"
								placeholder="请输入确认密码" name="reqpassword">
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">Email</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="inputEmail3"
								placeholder="Email" name="email">
						</div>
					</div>
					<div class="form-group">
						<label for="usercaption" class="col-sm-2 control-label">姓名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="usercaption"
								placeholder="请输入姓名" name="name">
						</div>
					</div>
					<div class="form-group opt">
						<label for="inlineRadio1" class="col-sm-2 control-label">性别</label>
						<div class="col-sm-6">
							<label class="radio-inline"> <input type="radio"
								name="sex" id="sex1" value="male">
								男
							</label> <label class="radio-inline"> <input type="radio"
								name="sex" id="sex2" value="female">
								女
							</label>
							<label class="error" for="sex" style="display:none">必须选择一项</label>
						</div>
					</div>
					<div class="form-group">
						<label for="date" class="col-sm-2 control-label">出生日期</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="birthday" placeholder="××××-××-××">
						</div>
					</div>

					<div class="form-group">
						<label for="date" class="col-sm-2 control-label">验证码</label>
						<div class="col-sm-3">
							<input type="text" class="form-control">

						</div>
						<div class="col-sm-2">
							<img src="./image/captcha.jhtml" />
						</div>

					</div>

					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<input type="submit" width="100" value="注册" name="submit"
								style="background: url('./images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0); height: 35px; width: 100px; color: white;">
						</div>
					</div>
				</form>
			</div>

			<div class="col-md-2"></div>

		</div>
	</div>

	<!-- 引入footer.jsp -->
	<jsp:include page="/footer.jsp"></jsp:include>

</body>
</html>




