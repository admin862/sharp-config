<#import "common/common.ftl" as common />
<#import "common/menu.ftl" as menu />
<!DOCTYPE html>
<html lang="en">
	<head>
		<@common.top />
	</head>

	<body class="no-skin">
		<!-- 页面顶部¨ -->
		<@common.header />

		<!-- /section:basics/navbar.layout -->
		<div class="main-container" id="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

			<!-- #section:basics/sidebar -->
			<!-- 左侧菜单 -->
			<@menu.menu />

			<!-- /section:basics/sidebar -->
			<div class="main-content">
				<div class="main-content-inner">
					<!-- #section:basics/content.breadcrumbs -->
					<div class="breadcrumbs" id="breadcrumbs">
						<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>

						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								<a href="/admin">Home</a>
							</li>

							<li>
								<a href="/admin/groupInfo/list">用户</a>
							</li>
							<li class="active">修改密码</li>
						</ul><!-- /.breadcrumb -->
					</div>

					<!-- /section:basics/content.breadcrumbs -->
					<div class="page-content">
						<@common.setting />

						<!-- /section:settings.box -->
						<div class="page-header">
							<h1>
                                	用户
								<small>
									<i class="ace-icon fa fa-angle-double-right"></i>
                                    	修改密码
								</small>
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<form class="form-horizontal" role="form" >
									<!-- #section:elements.form -->

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="groupId"> 原密码 </label>
                                        <div class="col-sm-9">
                                            <input type="password" id="originPwd" class="col-xs-10 col-sm-5" />
                                        </div>
                                    </div>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 新密码 </label>
										<div class="col-sm-9">
											<input type="password" id="newPwd" class="col-xs-10 col-sm-5" />
										</div>
									</div>

									<div class="space-4"></div>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 确认密码 </label>
                                        <div class="col-sm-9">
                                            <input type="password" id="confirmPwd" class="col-xs-10 col-sm-5" />
                                        </div>
                                    </div>

                                    <div class="space-4"></div>

									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											<button id="btnAjaxForm" class="btn btn-info" type="button">
												<i class="ace-icon fa fa-check bigger-110"></i>
													Submit
											</button>
										</div>
									</div>

									<div class="hr hr-24"></div>

									<div class="space-24"></div>

									<hr />
								</form>

								<div class="hr hr-18 dotted hr-double"></div>

								<h4 class="pink">
									<i class="ace-icon fa fa-hand-o-right green"></i>
									<a href="#modal-form" role="button" class="blue" data-toggle="modal"> Form Inside a Modal Box </a>
								</h4>

							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div>
			</div><!-- /.main-content -->

			<@common.foot />

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->

		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			jQuery(function($) {
				$("#btnAjaxForm").click(function(){
				    var originPwd = $("#originPwd").val();
                    var newPwd = $("#newPwd").val();
                    var confirmPwd = $("#confirmPwd").val();
                    if(originPwd == null || originPwd.length <= 0){
                        alert("请填写原密码");
                        return;
					}
                    if(newPwd == null || newPwd.length <= 0){
                        alert("请填写新密码");
                        return;
                    }
                    if(confirmPwd == null || confirmPwd.length <= 0){
                        alert("请再次填写确认密码");
                        return;
                    }
                    if(newPwd != confirmPwd){
                        alert("新密码-两次填写的密码不一致!")
                        return;
					}
					if(newPwd.length < 6){
                        alert("密码至少为6位数!");
                        return;
					}

					$.post("changePwd",{"originPwd" : originPwd,"newPwd" : newPwd},function (data) {
						alert(data);
                    });
				})
			});
		</script>
	</body>
</html>
