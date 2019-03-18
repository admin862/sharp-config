<#import "common/common.ftl" as common />
<#import "common/menu.ftl" as menu />
<!DOCTYPE html>
<html lang="en">
<head>
	<@common.top />
    <link rel="stylesheet" type="text/css" href="/static/EasyUI/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="/static/EasyUI/themes/icon.css" />
    <link rel="stylesheet" href="/static/layui/css/layui.css"  media="all">

    <script type="text/javascript" src="/static/EasyUI/jquery.easyui.min.js" ></script>
    <!-- page specific plugin scripts -->
    <script src="/static/assets/js/dataTables/jquery.dataTables.js"></script>
    <script src="/static/assets/js/dataTables/jquery.dataTables.bootstrap.js"></script>
    <script type="text/javascript">

        $(document).ready(function() {
            $('#simple-table').DataTable();
        });

        function btnAudit(id,state) {
            $.messager.confirm('Confirm','确定该操作?',function(r){
                if (r){
                    $.post("operate",{"id":id,"state":state},function (callbackData) {
                        window.location.reload();
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
                            审核
                            <small>
                                <i class="ace-icon fa fa-angle-double-right"></i>
                                    列表
                            </small>
                        </h1>
                    </div><!-- /.page-header -->

                    <div class="row">
                        <table class="layui-hide" id="test" lay-filter="tableFilter"></table>

                        <script type="text/html" id="bar">
                            <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="diff">差异</a>
                            <a class="layui-btn layui-btn-xs" lay-event="go">通过</a>
                            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="cancel">取消</a>
                        </script>

                        <script src="/static/layui/layui.js" charset="utf-8"></script>

                        <script>
                            layui.use('table', function(){
                                var table = layui.table;

                                table.render({
                                    elem: '#test'
                                    ,url:'/admin/audit/dataList'
                                    ,title: '用户数据表'
                                    ,cols: [[
                                        {field:'id', title:'ID', width:60, fixed: true}
                                        ,{field:'groupName', title:'组名称', width:120}
                                        ,{field:'appName', title:'项目名称', width:120}
                                        ,{field:'configName', title:'配置名称', width:150}
                                        ,{field:'remark', title:'备注信息', width:180}
                                        ,{field:'fromVersion', title:'原切换版本', width:120}
                                        ,{field:'toVersion', title:'切换后版本', width:120}
                                        ,{field:'showStateText', title:'状态', sort: true,width:100}
                                        ,{field:'state', title:'状态'}
                                        ,{field:'submitUsername', title:'提交用户', width:90}
                                        ,{field:'createTime', title:'创建时间', width:90}
                                        ,{fixed: 'right',width:180, align:'center', toolbar: '#bar'}
                                    ]]
                                    ,page: true
                                    ,done: function () {
                                        $("[data-field='state']").css('display','none');
                                    }
                                });

                                //监听工具条
                                table.on('tool(tableFilter)', function(obj){
                                    var data = obj.data;
                                    if(obj.event === 'diff'){
                                        window.open('/admin/audit/diff?fromConfigId=' + data.fromConfigId + '&toConfigId=' + data.toConfigId)
                                    } else if(obj.event === 'go'){
                                        if(data.state != 0){
                                            layer.msg('只有待审核的任务才能执行该操作');
                                            return;
                                        }
                                        btnAudit(data.id,1);
                                    } else if(obj.event === 'cancel'){
                                        if(data.state != 0){
                                            layer.msg('只有待审核的任务才能执行该操作');
                                            return ;
                                        }
                                        btnAudit(data.id,2);
                                    }
                                });

                            });
                        </script>
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
