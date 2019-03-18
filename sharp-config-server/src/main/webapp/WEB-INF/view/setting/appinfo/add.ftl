<#import "common/common.ftl" as common />
<#import "common/menu.ftl" as menu />
<!DOCTYPE html>
<html lang="en">
	<head>
		<@common.top />
		<script type="text/javascript">
			function onEnvironmentChange(){
				var envId = $("#environmentId").val();
				if(envId == -1){
					return ;
				}
				$.getJSON("/admin/groupInfo/getGroupInfoByEnvId",{"envId":envId},function (data) {
					$("#groupId").empty();
					//$("#appId").empty();
					$.each(data,function (i,item) {
						$("#groupId").append("<option value='" + item.id + "'>" + item.groupName + " </option>");
					});
				});
			}
		</script>
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
								<a href="/admin/appInfo/list">App列表</a>
							</li>
							<li class="active">添加App</li>
						</ul><!-- /.breadcrumb -->

					</div>

					<!-- /section:basics/content.breadcrumbs -->
					<div class="page-content">
						<@common.setting />

						<!-- /section:settings.box -->
						<div class="page-header">
							<h1>
									App管理
								<small>
									<i class="ace-icon fa fa-angle-double-right"></i>
										添加App
								</small>
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<form class="form-horizontal" role="form" >
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="groupId"> 所属环境 </label>
                                        <div class="col-sm-9">
                                            <select id="environmentId" onchange="onEnvironmentChange()">
                                                <option value="-1"></option>
											<#list menuEnvironmentList as environment>
                                                <option value="${environment.id}">${environment.name}</option>
											</#list>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="groupId"> 所属组 </label>
                                        <div class="col-sm-9">
                                            <select id="groupId">
                                                <option value="-1"></option>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="space-4"></div>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> App名称 </label>
                                        <div class="col-sm-9">
                                            <input type="text" id="appName" placeholder="App名称" class="col-xs-10 col-sm-5" />
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

									<hr />
								</form>

								<div class="hr hr-18 dotted hr-double"></div>

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
					var appInfo = {};
                    appInfo.groupId = $("#groupId").val();
                    appInfo.appName = $("#appName").val();
                    appInfo.remark = $("#remark").val();
                    appInfo.groupId = $("#groupId").val();
                    if(appInfo.groupId == -1){
                        alert("请选择组");
                        return;
                    }

                    $.post("add",appInfo,function (data) {
						alert(data);
                    });
				})
			});
		</script>
	</body>
</html>
