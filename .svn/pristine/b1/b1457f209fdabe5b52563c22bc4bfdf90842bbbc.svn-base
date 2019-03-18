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
							<a href="/admin/config/updateUI?appId=${configVersion.appId}&configName=${configVersion.configName}">${configVersion.configName}</a>
						</li>
						<li class="active">增加版本</li>
					</ul><!-- /.breadcrumb -->

				</div>

				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
				<@common.setting />
					<!-- /section:settings.box -->
					<div class="page-header">
						<h1>
							配置管理
							<small>
								<i class="ace-icon fa fa-angle-double-right"></i>
									版本${configVersion.version}
							</small>
						</h1>
					</div><!-- /.page-header -->

					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<form class="form-horizontal" role="form" >
								<!-- #section:elements.form -->

								<input type="hidden" id="currentConfigId" value="${currentConfig.id}" />

                                <div class="space-4"></div>

                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 此版本app配置信息 </label>
                                    <div class="col-sm-9">
										<textarea id="dataInfo" rows="10" cols="130"  contenteditable="false">${configVersion.dataInfo}</textarea>
                                    </div>
                                </div>

								<div class="space-4"></div>

                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 备注信息 </label>
                                    <div class="col-sm-9">
                                        <textarea id="remark" rows="6" cols="130">${configVersion.remark}</textarea>
                                    </div>
                                </div>


                                <div class="clearfix form-actions">
									<div class="col-md-offset-3 col-md-9">
										<button id="btnAjaxForm" class="btn btn-info" type="button">
											<i class="ace-icon fa fa-check bigger-110"></i>
												增加新版本
										</button>
									</div>
								</div>

								<div class="hr hr-24"></div>

								<div class="space-24"></div>

								<hr />
							</form>

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
			    var currentConfigId = $("#currentConfigId").val();
			    var remark = $("#remark").val();
			    var dataInfo = $("#dataInfo").val();
                $.post("addversion",{"currentConfigId":currentConfigId,"dataInfo":dataInfo,"remark":remark},function (data) {
                    window.location.href = "/admin/config/updateUI?appId=${configVersion.appId}&configName=${configVersion.configName}";
                });
			})
		});
	</script>
</body>
</html>
