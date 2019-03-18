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

                        <li>
                            <a href="#">Tables</a>
                        </li>
                        <li class="active">Simple &amp; Dynamic</li>
                    </ul><!-- /.breadcrumb -->

                    <!-- /section:basics/content.searchbox -->
                </div>

                <!-- /section:basics/content.breadcrumbs -->
                <div class="page-content">
                    <@common.setting />

                    <!-- /section:settings.box -->
                    <div class="page-header">
                        <h1>
                            Tables
                            <small>
                                <i class="ace-icon fa fa-angle-double-right"></i>
                                    ZooKeeper监控树形列表
                            </small>
                        </h1>
                    </div><!-- /.page-header -->

                    <link rel="stylesheet" type="text/css" href="/static/EasyUI/themes/default/easyui.css">
                    <link rel="stylesheet" type="text/css" href="/static/EasyUI/themes/icon.css">
                    <script type="text/javascript" src="/static/EasyUI/jquery.min.js"></script>
                    <script type="text/javascript" src="/static/EasyUI/jquery.easyui.min.js"></script>
                    <div class="row">
                        <div class="col-xs-12">
                            <!-- PAGE CONTENT BEGINS -->
                            <div class="row">
                                <div class="col-sm-12">
                                    <table title="Folder Browser" class="easyui-treegrid" style="width:auto;height:auto"
                                           data-options="
                                                            url: 'getTreeList?path=/config/online',
                                                            method: 'get',
                                                            rownumbers: true,
                                                            idField: 'id',
                                                            treeField: 'path'
                                                        ">
                                        <thead>
                                            <tr>
                                                <th data-options="field:'path'" width="auto">path</th>
                                                <th data-options="field:'data'" width="auto" align="left">data</th>
                                            </tr>
                                        </thead>
                                    </table>
                                </div>

                                <div class="vspace-16-sm"></div>

                            </div><!-- PAGE CONTENT ENDS -->
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

    <script src="/static/assets/js/jquery.nestable.js"></script>
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
