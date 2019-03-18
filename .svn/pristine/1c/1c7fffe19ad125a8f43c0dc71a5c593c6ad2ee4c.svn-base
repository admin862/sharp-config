<!DOCTYPE html>
<html lang="en">
<head>
	<title>sharp-config</title>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<link rel="stylesheet" href="static/login/bootstrap.min.css" />
	<link rel="stylesheet" href="static/login/css/camera.css" />
	<link rel="stylesheet" href="static/login/bootstrap-responsive.min.css" />
	<link rel="stylesheet" href="static/login/matrix-login.css" />
	<link href="static/login/font-awesome.css" rel="stylesheet" />
	<script type="text/javascript" src="static/login/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript">
			//window.setTimeout(showfh,3000);
			var timer;
			function showfh(){
				fhi = 1;
				//关闭提示晃动屏幕，注释掉这句话即可
				timer = setInterval(xzfh2, 10);
			};
			var current = 0;
			function xzfh(){
				current = (current)%360;
				document.body.style.transform = 'rotate('+current+'deg)';
				current ++;
				if(current>360){current = 0;}
			};
			var fhi = 1;
			var current2 = 1;
			function xzfh2(){
				if(fhi>50){
					document.body.style.transform = 'rotate(0deg)';
					clearInterval(timer);
					return;
				}
				current = (current2)%360;
				document.body.style.transform = 'rotate('+current+'deg)';
				current ++;
				if(current2 == 1){current2 = -1;}else{current2 = 1;}
				fhi++;
            };
	</script>
</head>
<body>
	<div style="width:100%;text-align: center;margin: 0 auto;position: absolute;">
		<div id="loginbox">
			<form action="/login" method="post" name="loginForm"
				id="loginForm">
				<div class="control-group normal_text">

				</div>
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_lg">
							<i><img height="37" src="static/login/user.png" /></i>
							</span><input type="text" name="username" id="loginname" value="" placeholder="请输入用户名" />
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_ly">
							<i><img height="37" src="static/login/suo.png" /></i>
							</span><input type="password" name="password" id="password" placeholder="请输入密码" value=""/>
						</div>
					</div>
				</div>
				<div class="form-actions">
					<div style="width:86%;padding-left:8%;">
						<span class="pull-right" style="padding-right:3%;"><a href="javascript:quxiao();" class="btn btn-success">取消</a></span>
						<#--<span class="pull-right"><a onclick="severCheck();" class="flip-link btn btn-info" id="to-recover">登录</a></span>-->
						<button class="flip-link btn btn-info" type="submit">登录</button>
					</div>
				</div>
			</form>
			<div class="controls">
				<div class="main_input_box">
					<font color="white"><span id="nameerr">Copyright © sharp-config</span></font>
				</div>
			</div>
		</div>
	</div>
	<div id="templatemo_banner_slide" class="container_wapper">
		<div class="camera_wrap camera_emboss" id="camera_slide">
			<div data-src="static/login/images/banner_slide_01.jpg"></div>
			<div data-src="static/login/images/banner_slide_02.jpg"></div>
			<div data-src="static/login/images/banner_slide_03.jpg"></div>
		</div>
	</div>


	<!--
	<script type="text/javascript">
		//服务器校验
		function severCheck(){
			if(check()){
				var username = $("#loginname").val();
				var password = $("#password").val();
				$.ajax({
					type: "POST",
					url: 'login',
			    	data: {"username":username,"password":password},
					dataType:'json',
					cache: false,
					success: function(data){
						if(0 == data.code){
//							saveCookie();
							window.location.href="/admin";
						} else {
							$("#loginname").tips({
								side : 1,
								msg : data.msg ,
								bg : '#FF5080',
								time : 15
							});
							showfh();
							$("#loginname").focus();
						}
					}
				});
			}
		}
	
		$(document).keyup(function(event) {
			if (event.keyCode == 13) {
				$("#to-recover").trigger("click");
			}
		});

		function genTimestamp() {
			var time = new Date();
			return time.getTime();
		}

		//客户端校验
		function check() {
			if ($("#loginname").val() == "") {

				$("#loginname").tips({
					side : 2,
					msg : '用户名不得为空',
					bg : '#AE81FF',
					time : 3
				});
				showfh();
				$("#loginname").focus();
				return false;
			} else {
				$("#loginname").val(jQuery.trim($('#loginname').val()));
			}

			if ($("#password").val() == "") {

				$("#password").tips({
					side : 2,
					msg : '密码不得为空',
					bg : '#AE81FF',
					time : 3
				});
				showfh();
				$("#password").focus();
				return false;
			}

			$("#loginbox").tips({
				side : 1,
				msg : '正在登录 , 请稍后 ...',
				bg : '#68B500',
				time : 3
			});
			return true;
		}

		function saveCookie() {
            $.cookie('username', $("#loginname").val());
            $.cookie('password', $("#password").val());
		}
		function quxiao() {
			$("#loginname").val('');
			$("#password").val('');
		}
		
		jQuery(function() {
			var loginname = $.cookie('loginname');
			var password = $.cookie('password');
			if (typeof(loginname) != "undefined"
					&& typeof(password) != "undefined") {
				$("#loginname").val(loginname);
				$("#password").val(password);
				$("#saveid").attr("checked", true);
				$("#code").focus();
			}
		});
	</script>
	-->
	<script>
		//TOCMAT重启之后 点击左侧列表跳转登录首页 
		if (window != top) {
			top.location.href = location.href;
		}
	</script>
	<script src="static/login/js/bootstrap.min.js"></script>
	<script src="static/js/jquery-1.7.2.js"></script>
	<script src="static/login/js/jquery.easing.1.3.js"></script>
	<script src="static/login/js/jquery.mobile.customized.min.js"></script>
	<script src="static/login/js/camera.min.js"></script>
	<script src="static/login/js/templatemo_script.js"></script>
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript" src="static/js/jquery.cookie.js"></script>
</body>
</html>