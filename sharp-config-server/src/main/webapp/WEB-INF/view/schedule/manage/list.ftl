<!DOCTYPE html>
<html lang="en">
<head>
<#import "/common/top.ftl" as top />
		<@top.top />
</head>

<body class="no-skin">
<!-- 页面顶部¨ -->
<#import "/common/common.ftl" as common />
<@common.header />
<div id="websocket_button"></div><!-- 少了此处，聊天窗口就无法关闭 -->

<div class="main-container" id="main-container">
    <!-- #section:basics/sidebar -->
    <script type="text/javascript">
        try{ace.settings.check('main-container' , 'fixed')}catch(e){}
    </script>

    <!-- 左侧菜单 -->
<#import "/common/left.ftl" as left />
<@left.left />

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
                        <a href="#">Home</a>
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
            <#import "/common/setting.ftl" as common />
            <@common.setting />

                <!-- /section:settings.box -->
                <div class="page-header">
                    <h1>
                        Tables
                        <small>
                            <i class="ace-icon fa fa-angle-double-right"></i>
                                定时任务
                        </small>
                    </h1>
                </div><!-- /.page-header -->

                <form class="form-horizontal" role="form" action="list" method="post" >
                    <!-- #section:elements.form -->
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 意向id </label>
                        <div class="col-sm-9">
                            <input type="text"  name="borrowIntentId" placeholder="" class="col-xs-10 col-sm-5" />
                            <button id="btnAjaxForm" class="btn btn-info" type="submit">
                                <i class="ace-icon fa fa-check bigger-110"></i>
                                Submit
                            </button>

                        </div>
                    </div>

                    <div class="space-4"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 借据号 </label>
                        <div class="col-sm-9">
                            <input type="text"  name="thirdOrderSN" placeholder="" class="col-xs-10 col-sm-5" />
                            <button id="btnAjaxForm" class="btn btn-info" type="submit">
                                <i class="ace-icon fa fa-check bigger-110"></i>
                                Submit
                            </button>
                        </div>
                    </div>

                    <div class="space-4"></div>

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
                                        <th>用户id</th>
                                        <th class="hidden-480">状态</th>
                                        <th> 代偿状态 </th>
                                        <th> 借据号 </th>
                                        <th> 借款金额 </th>
                                        <th> 已还本金 </th>
                                        <th> 已还利息 </th>
                                        <th> 期数 </th>
                                        <th> 放款日 </th>
                                        <th class="hidden-480">创建时间</th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                    
                                    </tbody>
                                </table>
                            </div><!-- /.span -->
                        </div><!-- /.row -->
                    </div>
                </div>

            </div><!-- /.page-content -->
        </div>
    </div><!-- /.main-content -->

    <div class="footer">
        <div class="footer-inner">
            <!-- #section:basics/footer -->
            <div class="footer-content">
						<span class="bigger-120">
							<span class="blue bolder">Ace</span>
							Application &copy; 2013-2014
						</span>

                &nbsp; &nbsp;
                <span class="action-buttons">
							<a href="#">
								<i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
							</a>

							<a href="#">
								<i class="ace-icon fa fa-facebook-square text-primary bigger-150"></i>
							</a>

							<a href="#">
								<i class="ace-icon fa fa-rss-square orange bigger-150"></i>
							</a>
						</span>
            </div>

            <!-- /section:basics/footer -->
        </div>
    </div>

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
    </a>
</div><!-- /.main-container -->

<!-- basic scripts -->
<script type="text/javascript">
    if('ontouchstart' in document.documentElement) document.write("<script src='/static/assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");
</script>
<script src="/static/assets/js/jquery.js"></script>
<script src="/static/assets/js/bootstrap.js"></script>

<!-- page specific plugin scripts -->
<script src="/static/assets/js/dataTables/jquery.dataTables.js"></script>
<script src="/static/assets/js/dataTables/jquery.dataTables.bootstrap.js"></script>
<script src="/static/assets/js/dataTables/extensions/TableTools/js/dataTables.tableTools.js"></script>
<script src="/static/assets/js/dataTables/extensions/ColVis/js/dataTables.colVis.js"></script>

<!-- ace scripts -->
<script src="/static/assets/js/ace/elements.scroller.js"></script>
<script src="/static/assets/js/ace/elements.colorpicker.js"></script>
<script src="/static/assets/js/ace/elements.fileinput.js"></script>
<script src="/static/assets/js/ace/elements.typeahead.js"></script>
<script src="/static/assets/js/ace/elements.wysiwyg.js"></script>
<script src="/static/assets/js/ace/elements.spinner.js"></script>
<script src="/static/assets/js/ace/elements.treeview.js"></script>
<script src="/static/assets/js/ace/elements.wizard.js"></script>
<script src="/static/assets/js/ace/elements.aside.js"></script>
<script src="/static/assets/js/ace/ace.js"></script>
<script src="/static/assets/js/ace/ace.ajax-content.js"></script>
<script src="/static/assets/js/ace/ace.touch-drag.js"></script>
<script src="/static/assets/js/ace/ace.sidebar.js"></script>
<script src="/static/assets/js/ace/ace.sidebar-scroll-1.js"></script>
<script src="/static/assets/js/ace/ace.submenu-hover.js"></script>
<script src="/static/assets/js/ace/ace.widget-box.js"></script>
<script src="/static/assets/js/ace/ace.settings.js"></script>
<script src="/static/assets/js/ace/ace.settings-rtl.js"></script>
<script src="/static/assets/js/ace/ace.settings-skin.js"></script>
<script src="/static/assets/js/ace/ace.widget-on-reload.js"></script>
<script src="/static/assets/js/ace/ace.searchbox-autocomplete.js"></script>
<!-- inline scripts related to this page -->
<script type="text/javascript">

    function getSubListByParentId(parentId){
        window.location.href = "?parentId="+parentId;
    }

    function deleteById(id){
        $.ajax({
            type:"POST",
            url:"deleteById",
            data:"id="+id,
            dataType:"json",
            success:function(data){
                console.log("begin:"+data);
                if(data.code == 0){
                    location.reload(true);
                }
            }
        });
    }

    function updateById(id){
        window.location.href = "/brand/updateUI?id="+id;
    }

    function addChild(parentId){
        window.location.href = "addUI?parentId="+parentId;
    }
</script>
</body>
</html>
