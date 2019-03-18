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
								<a href="#">ZK</a>
							</li>
							<li class="active">数据管理</li>
						</ul><!-- /.breadcrumb -->

					</div>

					<!-- /section:basics/content.breadcrumbs -->
					<div class="page-content">
						<@common.setting />

						<!-- /section:settings.box -->
						<div class="page-header">
							<h1>
									ZK数据管理
								<small>
									<i class="ace-icon fa fa-angle-double-right"></i>
										数据管理
								</small>
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<form class="form-horizontal" role="form" >

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> path </label>

                                        <div class="col-sm-9">
                                            <input type="text" id="path" placeholder="path" class="col-xs-10 col-sm-5" />
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> data </label>

                                        <div class="col-sm-9">
                                            <input type="text" id="zookeeperData" placeholder="data" class="col-xs-10 col-sm-5" />
                                        </div>
                                    </div>

                                    <div class="space-4"></div>

                                    <div class="clearfix form-actions">
                                        <div class="col-md-offset-3 col-md-9">
                                            <button id="btnViewData" class="btn btn-info" type="button">
                                                <i class="ace-icon fa fa-check bigger-110"></i>
                                                查看
                                            </button>
                                        </div>
                                    </div>

									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											<button id="btnSubmitData" class="btn btn-info" type="button">
												<i class="ace-icon fa fa-check bigger-110"></i>
													修改
											</button>
										</div>
									</div>

                                    <div class="clearfix form-actions">
                                        <div class="col-md-offset-3 col-md-9">
                                            <button id="btnDeleteData" class="btn btn-info" type="button">
                                                <i class="ace-icon fa fa-check bigger-110"></i>
													删除
                                            </button>
                                        </div>
                                    </div>

									<div class="hr hr-24"></div>

									<hr />
									<div id="showTxt"></div>
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

                $("#btnViewData").click(function(){
                    var path = $("#path").val();
                    if(path == null || path.length <= 0){
                        alert("请填入正确的地址");
                        return ;
					}
                    $.get("getChildDataByPath",{"parentPath":path},function (data) {
                        $("#showTxt").text("");
                        $("#showTxt").text(data);
                    });
                });

				$("#btnSubmitData").click(function(){
				    var path = $("#path").val();
                    var zookeeperData = $("#zookeeperData").val();
                    if(path == null || path.length <= 0){
                        alert("请填入正确的地址");
                        return ;
                    }
                    if(zookeeperData == null || zookeeperData.length <= 0){
                        alert("请填入正确的内容");
                        return ;
                    }
                    $.post("updateZKData",{"path":path,"zookeeperData":zookeeperData},function (data) {
						alert(data);
                    });
				});

                $("#btnDeleteData").click(function(){
                    var path = $("#path").val();
                    if(path == null || path.length <= 0){
                        alert("请填入正确的地址");
                        return ;
                    }
                    $.post("deleteZKData",{"path":path},function (data) {
                        alert(data);
                    });
                });

			});
		</script>
	</body>
</html>
