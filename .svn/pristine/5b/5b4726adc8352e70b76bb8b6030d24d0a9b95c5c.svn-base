<#import "common/common.ftl" as common />
<#import "common/menu.ftl" as menu />
<!DOCTYPE html>
<html lang="en">
<head>
	<@common.top />
	<script type="text/javascript">
		function onGroupChange() {
            var groupId = $("#groupId").val();
            if(groupId == -1){
                return ;
            }
            $.getJSON("/admin/appInfo/getGroupInfoByEnvId",{"groupId":groupId},function (data) {
                $("#appId").empty();
                $.each(data,function (i,item) {
                    $("#appId").append("<option value='" + item.id + "'>" + item.appName + " </option>");
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
							<a href="/admin/config/${environment.path}">${environment.name}</a>
						</li>
						<li class="active">新增配置</li>
					</ul><!-- /.breadcrumb -->

				</div>

				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<@common.setting />
					<!-- /section:settings.box -->
					<div class="page-header">
						<h1>
								${environment.name}
							<small>
								<i class="ace-icon fa fa-angle-double-right"></i>
									新增配置
							</small>
						</h1>
					</div><!-- /.page-header -->

					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<form class="form-horizontal" role="form" >
								<!-- #section:elements.form -->

                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="groupId"> 所属组 </label>
									<div class="col-sm-9">
                                        <select id="groupId" onchange="onGroupChange()" onclick="onGroupChange()" onselect="onGroupChange()">
                                            <option value="-1"></option>
											<#list groupInfoList as groupInfo>
											    <option value="${groupInfo.id}">${groupInfo.groupName}</option>
											</#list>
                                        </select>
									</div>
                                </div>

								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="appId"> 所属App </label>
                                    <div class="col-sm-9">
                                        <select id="appId">
                                            <option value="-1"></option>
                                        </select>
									</div>
								</div>

								<div class="space-4"></div>

								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> app配置名称 </label>
									<div class="col-sm-9">
										<input type="text" id="configName" placeholder="app配置名称" class="col-xs-10 col-sm-5" />
									</div>
								</div>

                                <div class="space-4"></div>

                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> app配置信息 </label>
                                    <div class="col-sm-9">
                                        <textarea id="dataInfo" rows="10" cols="130"></textarea>
                                    </div>
                                </div>

								<div class="space-4"></div>

                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 备注信息 </label>
                                    <div class="col-sm-9">
                                        <textarea id="remark" rows="6" cols="130"></textarea>
                                    </div>
                                </div>

								<div class="clearfix form-actions">
									<div class="col-md-offset-3 col-md-9">
										<button id="btnAjaxForm" class="btn btn-info" type="button">
											<i class="ace-icon fa fa-check bigger-110"></i>
												Submit
										</button>
									</div>
								</div>

								<div class="space-24"></div>

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
				var configBean = {};
                configBean.appId = $("#appId").val();
                configBean.configName = $("#configName").val();
                configBean.dataInfo = $("#dataInfo").val();
                configBean.remark = $("#remark").val();

                if(configBean.appId == -1){
                    alert("请选择App");
                    return;
				}
                $.post("add",configBean,function (data) {
					alert(data);
                });
			})
		});
	</script>
</body>
</html>
