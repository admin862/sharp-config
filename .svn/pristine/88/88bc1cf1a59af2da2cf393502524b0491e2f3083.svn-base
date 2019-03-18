<#macro menu>
    <div id="sidebar" class="sidebar responsive">
        <script type="text/javascript">
            try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
        </script>

        <div class="sidebar-shortcuts" id="sidebar-shortcuts">
            <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">

                <button class="btn btn-info" onclick="changeMenus();" title="切换菜单">
                    <i class="ace-icon fa fa-pencil"></i>
                </button>

                <button class="btn btn-success" title="UI实例">
                    <i class="ace-icon fa fa-signal"></i>
                </button>

                <!-- #section:basics/sidebar.layout.shortcuts -->
                <button class="btn btn-warning" title="" id="adminzidian">
                    <i class="ace-icon fa fa-book"></i>
                </button>

                <button class="btn btn-danger">
                    <i class="ace-icon fa fa-cogs"></i>
                </button>

                <!-- /section:basics/sidebar.layout.shortcuts -->
            </div>

            <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
                <span class="btn btn-success"></span>

                <span class="btn btn-info"></span>

                <span class="btn btn-warning"></span>

                <span class="btn btn-danger"></span>
            </div>
        </div><!-- /.sidebar-shortcuts -->

        <ul class="nav nav-list">
            <li class="">
                <a href="/admin">
                    <i class="menu-icon fa fa-tachometer"></i>
                    <span class="menu-text">后台首页</span>
                </a>
                <b class="arrow"></b>
            </li>

            <li class="">
                <a href="#" class="dropdown-toggle">
                    <i class="menu-icon fa fa-pencil-square-o"></i>
                    <span class="menu-text"> 设置 </span>

                    <b class="arrow fa fa-angle-down"></b>
                </a>

                <b class="arrow"></b>

                <ul class="submenu">
                    <li class="">
                        <a href="/admin/environment/list">
                            <i class="menu-icon fa fa-caret-right"></i>
                                环境设置
                        </a>

                        <b class="arrow"></b>
                    </li>

                    <li class="">
                        <a href="/admin/groupInfo/list">
                            <i class="menu-icon fa fa-caret-right"></i>
                                组设置
                        </a>

                        <b class="arrow"></b>
                    </li>

                    <li class="">
                        <a href="/admin/appInfo/list">
                            <i class="menu-icon fa fa-caret-right"></i>
                                APP设置
                        </a>

                        <b class="arrow"></b>
                    </li>

                    <li class="">
                        <a href="/admin/user/changePwdView">
                            <i class="menu-icon fa fa-caret-right"></i>
                                修改密码
                        </a>

                        <b class="arrow"></b>
                    </li>

                </ul>
            </li>

            <li class="">
                <a href="#" class="dropdown-toggle">
                    <i class="menu-icon fa fa-pencil-square-o"></i>
                    <span class="menu-text"> 业务系统配置 </span>

                    <b class="arrow fa fa-angle-down"></b>
                </a>

                <b class="arrow"></b>

                <ul class="submenu">

                    <#list menuEnvironmentList as environment>
                        <li class="">
                            <a href="/admin/config/${environment.path}">
                                <i class="menu-icon fa fa-caret-right"></i>
                                    ${environment.name}
                            </a>

                            <b class="arrow"></b>
                        </li>
                    </#list>

                </ul>
            </li>

            <li class="">
                <a href="#" class="dropdown-toggle">
                    <i class="menu-icon fa fa-pencil-square-o"></i>
                    <span class="menu-text"> 审批 </span>

                    <b class="arrow fa fa-angle-down"></b>
                </a>

                <b class="arrow"></b>

                <ul class="submenu">

                    <li class="">
                        <a href="/admin/audit/list">
                            <i class="menu-icon fa fa-caret-right"></i>
                                审批管理
                        </a>

                        <b class="arrow"></b>
                    </li>

                </ul>
            </li>

            <li class="">
                <a href="#" class="dropdown-toggle">
                    <i class="menu-icon fa fa-pencil-square-o"></i>
                    <span class="menu-text"> ZK </span>

                    <b class="arrow fa fa-angle-down"></b>
                </a>

                <b class="arrow"></b>

                <ul class="submenu">

                    <li class="">
                        <a href="/admin/zookeeper/show">
                            <i class="menu-icon fa fa-caret-right"></i>
                                ZK树形监控
                        </a>

                        <a href="/admin/zookeeper/getDataView">
                            <i class="menu-icon fa fa-caret-right"></i>
                                数据管理
                        </a>

                        <b class="arrow"></b>
                    </li>

                </ul>
            </li>

            <li class="">
                <a href="#" class="dropdown-toggle">
                    <i class="menu-icon fa fa-pencil-square-o"></i>
                    <span class="menu-text"> 开发运维 </span>

                    <b class="arrow fa fa-angle-down"></b>
                </a>

                <b class="arrow"></b>

                <ul class="submenu">

                    <li class="">
                        <a href="/admin/data/dataSynchronizeUI">
                            <i class="menu-icon fa fa-caret-right"></i>
                                数据同步
                        </a>

                        <b class="arrow"></b>
                    </li>

                </ul>
            </li>

            <script>
                var pathname = window.location.pathname;
                $(".nav-list li a").each(function() {
                    var href = $(this).attr("href");
                    if(pathname == href){
                        $(this).parents("ul").parent("li").addClass("active");
                        $(this).parent("li").addClass("active");
                    }
                });
            </script>

            <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
                <i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
            </div>

            <script type="text/javascript">
                try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
            </script>
    </div>
</#macro>