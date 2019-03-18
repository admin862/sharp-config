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
								<a href="#">数据管理</a>
							</li>

							<li>
								<a href="#">数据同步</a>
							</li>
							<li class="active">环境同步</li>
						</ul><!-- /.breadcrumb -->

					</div>

					<!-- /section:basics/content.breadcrumbs -->
					<div class="page-content">
						<@common.setting />

						<!-- /section:settings.box -->
						<div class="page-header">
							<h1>
									数据同步
								<small>
									<i class="ace-icon fa fa-angle-double-right"></i>
										环境同步
								</small>
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<form class="form-horizontal" role="form" >

									<div class="form-group" style="width:80%;margin:0 auto;">
										<label class="col-sm-1 col-sm-offset-1 control-label" > 环境A </label>
										<div class="col-sm-2">
											<select id="environmentIdA">
												<option value="-1"></option>
												<#list menuEnvironmentList as environment>
													<option value="${environment.id}">${environment.name}</option>
												</#list>
											</select>
										</div>
										<label class="col-sm-2 " > 同步到 </label>
										<label class="col-sm-1 control-label" > 环境B </label>
										<div class="col-sm-2">
											<select id="environmentIdB">
												<option value="-1"></option>
												<#list menuEnvironmentList as environment>
													<option value="${environment.id}">${environment.name}</option>
												</#list>
											</select>
										</div>
									</div>

                                    <div class="space-4"></div>

									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											<button id="btnEnvSyn" class="btn btn-info" type="button">
												<i class="ace-icon fa fa-check bigger-110"></i>
												Submit
											</button>
										</div>
									</div>

									<div class="hr hr-24"></div>

									<hr />
								</form>
							</div><!-- /.col -->
						</div><!-- /.row -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<form class="form-horizontal" role="form" >

									<div class="form-group" style="width:80%;margin:0 auto;">
										<label class="col-sm-1 col-sm-offset-1 control-label" > ZooKeeper路径同步 </label>
										<div class="col-sm-2">
											<select id="singleEnvironment">
												<option value="-1"></option>
												<#list menuEnvironmentList as environment>
													<option value="${environment.id}">${environment.name}</option>
												</#list>
											</select>
										</div>
									</div>

									<div class="space-4"></div>

									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											<button id="btnSingleSyn" class="btn btn-info" type="button">
												<i class="ace-icon fa fa-check bigger-110"></i>
												Submit
											</button>
										</div>
									</div>

									<div class="hr hr-24"></div>

									<hr />
								</form>
							</div><!-- /.col -->
						</div><!-- /.row -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<form class="form-horizontal" role="form" >

									<div class="form-group" style="width:80%;margin:0 auto;">
										<label class="col-sm-1 col-sm-offset-1 control-label" > 所有ZooKeeper环境同步 </label>
									</div>

									<div class="space-4"></div>

									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											<button id="btnAllSyn" class="btn btn-info" type="button">
												<i class="ace-icon fa fa-check bigger-110"></i>
												Submit
											</button>
										</div>
									</div>

									<div class="hr hr-24"></div>

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

				$("#btnEnvSyn").click(function(){
                    var environmentIdA = $("#environmentIdA").val();
                    var environmentIdB = $("#environmentIdB").val();
                    if(environmentIdA == -1 || environmentIdB == -1){
                        alert("请选择组");
                        return;
                    }

                    $.post("environmentSynchronize",{"envFromId":environmentIdA,"envToId":environmentIdB},function (data) {
						alert(data);
                    });
				});

                $("#btnSingleSyn").click(function(){
                    var singleEnvironment = $("#singleEnvironment").val();
                    if(singleEnvironment == -1 ){
                        alert("请选择组");
                        return;
                    }

                    $.post("synchronizeSingleZooKeeperEnvironment",{"envId":singleEnvironment},function (data) {
                        alert(data);
                    });
                })

                $("#btnAllSyn").click(function(){
                    $.post("synchronizeAllZooKeeperEnvironment",function (data) {
                        alert(data);
                    });
                })
			});
		</script>
	</body>
</html>
