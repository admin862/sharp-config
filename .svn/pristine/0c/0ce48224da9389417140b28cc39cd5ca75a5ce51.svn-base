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
								<a href="/admin/environment/list">环境管理</a>
							</li>
							<li class="active">添加环境</li>
						</ul><!-- /.breadcrumb -->

					</div>

					<!-- /section:basics/content.breadcrumbs -->
					<div class="page-content">
						<@common.setting />

						<!-- /section:settings.box -->
						<div class="page-header">
							<h1>
									环境管理
								<small>
									<i class="ace-icon fa fa-angle-double-right"></i>
										新增环境
								</small>
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<form class="form-horizontal" role="form" >
									<!-- #section:elements.form -->
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 环境名称 </label>
										<div class="col-sm-9">
											<input type="text" id="name" placeholder="名称" class="col-xs-10 col-sm-5" />
										</div>
									</div>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 环境根路径 </label>
                                        <div class="col-sm-9">
                                            <input type="text" id="path" placeholder="路径" class="col-xs-10 col-sm-5" />
                                        </div>
                                    </div>

									<div class="space-4"></div>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 备注 </label>
                                        <div class="col-sm-9">
                                            <input type="text" id="remark" placeholder="备注" class="col-xs-10 col-sm-5" />
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
                    var environment	 = {};
                    environment.name = $("#name").val();
                    environment.path = $("#path").val();
                    environment.remark = $("#remark").val();
				    $.post("add",environment,function (data) {
						alert(data);
                    });
				})
			});
		</script>
	</body>
</html>
