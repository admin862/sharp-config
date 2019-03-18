<#import "common/common.ftl" as common />
<#import "common/menu.ftl" as menu />
<!DOCTYPE html>
<html>
	<head>
		<@common.top />
		<link rel="stylesheet" type="text/css" href="/static/EasyUI/themes/default/easyui.css" />
		<link rel="stylesheet" type="text/css" href="/static/EasyUI/themes/icon.css" />
		<script type="text/javascript" src="/static/EasyUI/jquery.easyui.min.js" ></script>
		<script type="text/javascript">

            var hasAuth = "${hasAuth}";

            function btnAudit(currentConfigId,nextVersionId) {
                $.messager.prompt("提交审核", "您确定要执行操作吗？", function (data) {
                    if (data) {
                        $.post("audit",{"currentConfigId":currentConfigId,"prepareVersionId":nextVersionId,"data" : data},function (callbackData) {
							alert(callbackData);
                            window.location.reload();
                        });
                    }
                });
            }

            function btnCancelAudit(id,currentConfigId) {
                $.messager.confirm('Confirm','确定要取消审核吗?',function(r){
                    if (r){
                        $.post("cancelAudit",{"id":id,"currentConfigId":currentConfigId},function (data) {
                            window.location.reload();
                        });
                    }
                });
            }

			function btnPrepare(currentConfigId,nextVersionId) {
				$.post("prepare",{"currentConfigId":currentConfigId,"prepareVersionId":nextVersionId},function (data) {
                    refreshZooKeeper();
					alert(data);
				});
			}

			function btnPublish(currentConfigId,nextVersionId) {
                $.post("publish",{"currentConfigId":currentConfigId,"prepareVersionId":nextVersionId},function (data) {
                    alert(data);
                    window.location.reload();
                });
			}

			$(function () {
				var hasAuth = "${hasAuth}";
				if(hasAuth == 0){
				    alert("没有改配置文件的操作权限!");
				} else {
                    refreshZooKeeper();
                    setInterval('refreshZooKeeper()',3000); //指定6秒刷新一次
                }
            })

			var number=0;
			
			function refreshZooKeeper() {
                var environmentPath = "${currentConfig.environmentPath}";
                var groupName = "${currentConfig.groupName}";
                var appName = "${currentConfig.appName}";
                var path = "/config/" + environmentPath + "/" + groupName + "/" + appName;

                $.getJSON("/admin/zookeeper/getDataByPath",{"path":path},function (data) {
					$("#appZKData").html(data);
                    var appZKData = JSON.parse(data);
                    var currentConfigName = $("#currentConfigName").text();
                    $("#currentVersion").text(getVersion(appZKData.current,currentConfigName));
                });

                $.getJSON("/admin/zookeeper/getTreeList",{"path":path },function (data) {
			        $("#zkTree").empty();
			        $.each(data,function (i,item) {
                        var path = item.path;

                        var normState = "<span class='sticker'><span class='label label-success arrowed-in'>" +
                                "<span class='label label-success arrowed-in'><i class='ace-icon fa fa-check bigger-110'></i>"+
                                "</span></span>";

                        var warmState ="<span class='sticker'><i class='ace-icon fa fa-exclamation-triangle red bigger-130'></i></span>";


                        var state = normState;
						var appZKDataJSONString = $("#appZKData").html();

						var appZKData = JSON.parse(appZKDataJSONString);

						var jsonDataZK = JSON.parse(item.data);


                        var nextInMachine = jsonDataZK.next;

                        if(appZKData.next != null && appZKData.next.length > 0 ){
						    if(nextInMachine == null || nextInMachine == undefined || nextInMachine.length <= 0){
                                state = warmState;
							} else {
                                var configNameInApp = appZKData.next[0].configName;
                                var versionInApp = appZKData.next[0].version;
                                var timestampInApp = appZKData.next[0].timestamp;


                                var configNameInMachine = nextInMachine[0].configName;
                                var versionInMachine = nextInMachine[0].version;
                                var timestampInMachine = nextInMachine[0].timestamp;

                                if(configNameInApp != configNameInMachine || versionInApp != versionInMachine || timestampInApp != timestampInMachine){
                                    state = warmState;
                                }
							}
						}

                        var child = $("<li class='dd-item'><div class='dd-handle'><span class='orange'>"
								+ path +"</span><span class='lighter grey'>" + item.data + "</span>" + state + "</div></li>");

//                        var child = $("<li class='dd-item'><div class='dd-handle'><span class='orange'>"
//								+ path +"</span>" + state + " </div></li>");

                        $("#zkTree").append(child);
                        number = number + 1;
                    });
                });

            }

			function getVersion(currentObj,configName) {
			    var version ;
				$.each(currentObj,function (index,value) {
					if(value.configName == configName){
					    version = value.version;
					}
                });
				return version;
            }

            function btnDelete(currentConfigId,configVersionId) {
                $.messager.confirm('Confirm','确定要删除该版本吗?',function(r){
                    if (r){
                        $.post("deleteVersionById",{"currentConfigId":currentConfigId,"configVersionId":configVersionId},function (data) {
                            if(data.code == 0){
                                window.location.reload();
                            } else {
                                alert(data.msg);
                            }
                        });
                    }
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
								<a href="/admin/config/${currentConfig.environmentPath}">配置管理</a>
							</li>
							<li class="active">发布配置</li>
						</ul><!-- /.breadcrumb -->

						<!-- /section:basics/content.searchbox -->
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
										${currentConfig.environmentName}
								</small>
							</h1>
						</div><!-- /.page-header -->

						<h4 class="pink">
							<i class="ace-icon fa fa-hand-o-right green"></i>
							<a href="#modal-form" role="button" class="blue" data-toggle="modal"> 当前app下的机器节点 </a>
						</h4>

						<div class="row">
							<div class="col-sm-6">
								<div class="dd" id="nestable">
									<ol class="dd-list">
										<li class="dd-item" data-id="2">
											<div class="dd-handle">${currentConfig.appName}
                                                <span id="appZKData" class="lighter grey"></span>
											</div>

											<ol id="zkTree" class="dd-list">


											</ol>
										</li>
									</ol>
								</div>
							</div>
							<div class="vspace-16-sm"></div>
						</div>

						<div class="row">
							<div class="col-xs-12">
								<h3>
                                    当前配置文件  <span id="currentConfigName">${currentConfig.configName}</span>     所启用的版本: <span id="currentVersion"></span>
								</h3>
							</div>
						</div>

                        <div class="row">
                            <div class="col-xs-12">
                                <h3>
                                    待审核记录
                                </h3>
                            </div>
                            <div class="col-xs-12">
                                <table id="simple-table" class="table table-striped table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th>id</th>
                                        <th>原切换版本</th>
                                        <th>切换后版本</th>
                                        <th>状态</th>
                                        <th>提交用户</th>
                                        <th class="hidden-480">创建时间</th>
                                        <th> </th>
									</tr>
                                    </thead>

                                    <tbody>
									<#list auditList as auditObject>
										<tr>
											<td>${auditObject.id}</td>
											<td>${auditObject.fromVersion}</td>
											<td>${auditObject.toVersion}</td>
											<td>
												<#if auditObject.state == 0>
																	待审核
													<#elseif auditObject.state == 1>
																		审核通过
													<#elseif auditObject.state == 2>
																		审核失败
													<#elseif auditObject.state == 3>
																		已完成
												</#if>
                                            </td>
                                            <td>${auditObject.submitUsername}</td>
                                            <td>${auditObject.createTime?string('yyyy-MM-dd HH:mm:ss')}</td>
                                            <td>
                                                <div class="hidden-sm hidden-xs btn-group">
                                                    <button class="btn btn-xs btn-success" onclick="btnCancelAudit('${auditObject.id}','${currentConfig.id}');">
                                                        <i class="ace-icon fa fa-pencil bigger-120">取消</i>
                                                    </button>
                                                </div>
                                                <button class="btn btn-xs btn-success" onclick="window.open( '/admin/audit/diff?fromConfigId=${auditObject.fromConfigId}&toConfigId=${auditObject.toConfigId}')" >
                                                    <i class="ace-icon fa fa-pencil bigger-120">差异</i>
                                                </button>
                                            </td>
										</tr>
									</#list>
                                    </tbody>
                                </table>
                            </div><!-- /.span -->
                        </div><!-- /.row -->

						<div class="row">
							<div class="col-xs-12">
								<table id="simple-table" class="table table-striped table-bordered table-hover">
									<thead>
									<tr>
										<th>id</th>
										<th>组名称</th>
										<th>App名称</th>
										<th>版本号</th>
										<th class="hidden-480">创建时间</th>
                                        <th> </th>
									</tr>
									</thead>

									<tbody>
									<#list configVersionList as configVersionBean>
                                    	<tr>
											<td>${configVersionBean.id}</td>
											<td>${configVersionBean.groupName}</td>
											<td>${configVersionBean.appName}</td>
											<td>${configVersionBean.version}</td>
											<td>${configVersionBean.createTime?string('yyyy-MM-dd HH:mm:ss')}</td>
											<td>
												<div class="hidden-sm hidden-xs btn-group">
													<button class="btn btn-xs btn-success" onclick="window.location.href='/admin/config/addNewVersionUI?configVersionId=${configVersionBean.id}'">
														<i class="ace-icon fa fa-pencil bigger-120">view</i>
													</button>
                                                    <button class="btn btn-xs btn-lighter" onclick="btnAudit('${currentConfig.id}','${configVersionBean.id}');">
                                                        <i class="ace-icon fa fa-check bigger-120">提交审核</i>
                                                    </button>
													<button class="btn btn-xs btn-lighter" onclick="btnPrepare('${currentConfig.id}','${configVersionBean.id}');">
														<i class="ace-icon fa fa-check bigger-120">prepare</i>
													</button>
													<button class="btn btn-xs btn-warning" onclick="btnPublish('${currentConfig.id}','${configVersionBean.id}');">
														<i class="ace-icon fa fa-flag bigger-120">confirm</i>
													</button>
                                                    <button class="btn btn-xs btn-danger" onclick="btnDelete('${currentConfig.id}','${configVersionBean.id}');">
                                                        <i class="ace-icon fa fa-trash-o bigger-120">Delete</i>
                                                    </button>
												</div>
											</td>
										</tr>
									</#list>
									</tbody>
								</table>
							</div><!-- /.span -->
						</div><!-- /.row -->

					</div><!-- /.page-content -->
				</div>
			</div><!-- /.main-content -->

			<@common.foot />

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->

        <!-- page specific plugin scripts -->
        <script src="/static/assets/js/jquery.nestable.js"></script>

        <!-- inline scripts related to this page -->

        <script type="text/javascript">
            jQuery(function($){

                $('.dd').nestable();

                $('.dd-handle a').on('mousedown', function(e){
                    e.stopPropagation();
                });

                $('[data-rel="tooltip"]').tooltip();

            });

        </script>

	</body>
</html>
