<#import "common/common.ftl" as common />
<#import "common/menu.ftl" as menu />
<!DOCTYPE html>
<html lang="en">
<head>
	<@common.top />
    <link rel="stylesheet" type="text/css" href="/static/EasyUI/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="/static/EasyUI/themes/icon.css" />
    <script type="text/javascript" src="/static/EasyUI/jquery.easyui.min.js" ></script>
    <script type="text/javascript">
        function btnDelete(id) {
            $.messager.confirm('Confirm','确定要删除该app吗?',function(r){
                if (r){
                    $.post("deleteAppById",{"id":id},function (data) {
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
    <div id="websocket_button"></div><!-- 少了此处，聊天窗口就无法关闭 -->

    <div class="main-container" id="main-container">
        <!-- #section:basics/sidebar -->
        <script type="text/javascript">
            try{ace.settings.check('main-container' , 'fixed')}catch(e){}
        </script>

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
                    </ul><!-- /.breadcrumb -->
                    <!-- /section:basics/content.searchbox -->
                </div>

                <!-- /section:basics/content.breadcrumbs -->
                <div class="page-content">
                    <@common.setting />

                    <!-- /section:settings.box -->
                    <div class="page-header">
                        <h1>
                                APP管理
                            <small>
                                <i class="ace-icon fa fa-angle-double-right"></i>
                                    App列表
                            </small>
                        </h1>
                    </div><!-- /.page-header -->

                    <form class="form-horizontal" role="form" action="list" method="get" >
                        <!-- #section:elements.form -->
                        <#--<div class="form-group">-->
                            <#--<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 组名称 </label>-->
                            <#--<div class="col-sm-9">-->
                                <#--<input type="text"  name="groupName" placeholder="" class="col-xs-10 col-sm-5" />-->
                            <#--</div>-->
                        <#--</div>-->

                        <div class="space-4"></div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> App名称 </label>
                            <div class="col-sm-9">
                                <input type="text"  name="appName" placeholder="" class="col-xs-10 col-sm-5" />
                                <button id="btnAjaxForm" class="btn btn-info" type="submit">
                                    <i class="ace-icon fa fa-check bigger-110"></i>
                                        查询
                                </button>
                            </div>
                        </div>

                        <div class="space-4"></div>

                        <div class="col-md-9">
                            <button class="btn btn-info" type="button" onclick="window.location.href='/admin/appInfo/addUI'">
                                <i class="ace-icon fa fa-check bigger-110"></i>
                                    添加App
                            </button>
                        </div>
                    </form>

                    <div class="row">
                        <div class="col-xs-12">
                            <!-- PAGE CONTENT BEGINS -->
                            <div class="row">
                                <div class="col-xs-12">
                                    <table id="simple-table" class="table table-striped table-bordered table-hover">
                                        <thead>
                                            <tr>
                                                <th>id</th>
                                                <th>环境名称</th>
                                                <th>组名称</th>
                                                <th>App名称</th>
                                                <th> 备注 </th>
                                                <th class="hidden-480">创建时间</th>
                                                <th>  </th>
                                            </tr>
                                        </thead>

                                        <tbody>
                                            <#list appInfoList as appInfo>
                                                <tr>
                                                    <td>${appInfo.id}</td>
                                                    <td>${appInfo.environmentName}</td>
                                                    <td>${appInfo.groupName}</td>
                                                    <td>${appInfo.appName}</td>
                                                    <td>${appInfo.remark}</td>
                                                    <td>${appInfo.createTime?string('yyyy-MM-dd HH:mm:ss')}</td>
                                                    <td>
                                                        <div class="hidden-sm hidden-xs btn-group">
                                                            <button class="btn btn-xs btn-danger" onclick="btnDelete('${appInfo.id}');">
                                                                <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                                            </button>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </#list>
                                        </tbody>
                                    </table>
                                </div><!-- /.span -->
                            </div><!-- /.row -->
                        </div>
                    </div>

                </div><!-- /.page-content -->
            </div>
        </div><!-- /.main-content -->

        <@common.foot />

        <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
            <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
        </a>
    </div><!-- /.main-container -->
</body>
</html>
