<#macro top>
	<title>sharp-config</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta name="description" content="" />
	<link rel="stylesheet" href="/static/ace/css/bootstrap.css" />
	<link rel="stylesheet" href="/static/ace/css/font-awesome.css" />

	<link rel="stylesheet" href="/static/ace/css/jquery-ui.css" />
	<link rel="stylesheet" href="/static/ace/css/datepicker.css" />
	<link rel="stylesheet" href="/static/ace/css/ui.jqgrid.css" />
	<link rel="stylesheet" href="/static/ace/css/chosen.css" />
	<link rel="stylesheet" href="/static/ace/css/bootstrap-timepicker.css" />
	<link rel="stylesheet" href="/static/ace/css/daterangepicker.css" />
	<link rel="stylesheet" href="/static/ace/css/bootstrap-datetimepicker.css" />
	<link rel="stylesheet" href="/static/ace/css/colorpicker.css" />

	<link rel="stylesheet" href="/static/ace/css/ace-fonts.css" />
	<link rel="stylesheet" href="/static/ace/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />
	<script src="/static/ace/js/ace-extra.js"></script>
	<script src="/static/assets/js/jquery.js"></script>
	<script src="/static/assets/js/bootstrap.js"></script>
	<script src="/static/assets/js/ace/ace.js"></script>
	<script src="/static/assets/js/ace/ace.sidebar.js"></script>
</#macro>

<#macro header>
	<div id="navbar" class="navbar navbar-default">
		<script type="text/javascript">
			try{ace.settings.check('navbar' , 'fixed');}catch(e){}
		</script>

		<div class="navbar-container" id="navbar-container">
			<!-- #section:basics/sidebar.mobile.toggle -->
			<button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
				<span class="sr-only">Toggle sidebar</span>

				<span class="icon-bar"></span>

				<span class="icon-bar"></span>

				<span class="icon-bar"></span>
			</button>

			<!-- /section:basics/sidebar.mobile.toggle -->
			<div class="navbar-header pull-left">
				<!-- #section:basics/navbar.layout.brand -->
				<a class="navbar-brand">
					<small> <i class="fa fa-leaf"></i> sharp-config </small>
				</a>

				<!-- /section:basics/navbar.layout.brand -->

				<!-- #section:basics/navbar.toggle -->

				<!-- /section:basics/navbar.toggle -->
			</div>

			<!-- #section:basics/navbar.dropdown -->
			<div class="navbar-buttons navbar-header pull-right" role="navigation">
				<ul class="nav ace-nav">
					<li class="grey">
						<a data-toggle="dropdown" class="dropdown-toggle" href="#">
							<i class="ace-icon fa fa-tasks"></i>
							<span class="badge badge-grey">2</span>
						</a>

						<ul class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
							<li class="dropdown-header">
								<i class="ace-icon fa fa-check"></i>
								预留功能,待开发
							</li>
							<li class="dropdown-footer">
								<a href="javascript:">
									预留功能,待开发
									<i class="ace-icon fa fa-arrow-right"></i>
								</a>
							</li>
						</ul>
					</li>

					<li title="即时聊天" class="purple"  onclick="creatw();"><!-- creatw()在 WebRoot\plugins\websocketInstantMsg\websocket.js中 -->
						<a data-toggle="dropdown" class="dropdown-toggle" href="#">
							<i class="ace-icon fa fa-bell icon-animated-bell"></i>
							<span class="badge badge-important"></span>
						</a>

						<ul class="dropdown-menu-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-close">
							<li class="dropdown-header">
								<i class="ace-icon fa fa-bell-o"></i>
								FH Aadmin 即时通讯
							</li>
						</ul>
					</li>

					<li title="站内信" class="green" onclick="fhsms();" id="fhsmstss"><!-- fhsms()在 WebRoot\static\js\myjs\head.js中 -->
						<a data-toggle="dropdown" class="dropdown-toggle" href="#">
							<i class="ace-icon fa fa-envelope icon-animated-vertical"></i>
							<span class="badge badge-success" id="fhsmsCount"></span>
						</a>
					</li>

					<!-- #section:basics/navbar.user_menu -->
					<li class="light-blue">
						<a data-toggle="dropdown"  class="dropdown-toggle" href="#">
							<img class="nav-user-photo" src="/static/ace/avatars/user.jpg" alt="Jason's Photo" />
							<span class="user-info" id="user_info">
							</span>
							<i class="ace-icon fa fa-caret-down"></i>
						</a>

						<ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
							<li>
								<a onclick="editUserH();" style="cursor:pointer;"><i class="ace-icon fa fa-cog"></i>修改资料</a><!-- editUserH()在 WebRoot\static\js\myjs\head.js中 -->
							</li>
							<li id="systemset">
								<a onclick="editSys();" style="cursor:pointer;"><i class="ace-icon fa fa-user"></i>系统设置</a><!-- editSys()在 WebRoot\static\js\myjs\head.js中 -->
							</li>
							<li class="divider"></li>
							<li>
								<a href="/logout"><i class="ace-icon fa fa-power-off"></i>退出登录</a>
							</li>
						</ul>
					</li>

					<!-- /section:basics/navbar.user_menu -->
				</ul>
			</div>
			<!-- /section:basics/navbar.dropdown -->
		</div><!-- /.navbar-container -->
	</div>
	<!-- 站内信声音消息提示 -->
	<div id="fhsmsobj"></div>
</#macro>

<#macro foot>
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
</#macro>

<#macro setting>
	<div class="ace-settings-container" id="ace-settings-container">
		<div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
			<i class="ace-icon fa fa-cog bigger-130"></i>
		</div>

		<div class="ace-settings-box clearfix" id="ace-settings-box">
			<div class="pull-left width-50">
				<!-- #section:settings.skins -->
				<div class="ace-settings-item">
					<div class="pull-left">
						<select id="skin-colorpicker" class="hide">
							<option data-skin="no-skin" value="#438EB9">#438EB9</option>
							<option data-skin="skin-1" value="#222A2D">#222A2D</option>
							<option data-skin="skin-2" value="#C6487E">#C6487E</option>
							<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
						</select>
					</div>
					<span>&nbsp; Choose Skin</span>
				</div>

				<!-- /section:settings.skins -->

				<!-- #section:settings.navbar -->
				<div class="ace-settings-item">
					<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-navbar" />
					<label class="lbl" for="ace-settings-navbar"> Fixed Navbar</label>
				</div>

				<!-- /section:settings.navbar -->

				<!-- #section:settings.sidebar -->
				<div class="ace-settings-item">
					<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-sidebar" />
					<label class="lbl" for="ace-settings-sidebar"> Fixed Sidebar</label>
				</div>

				<!-- /section:settings.sidebar -->

				<!-- #section:settings.breadcrumbs -->
				<div class="ace-settings-item">
					<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-breadcrumbs" />
					<label class="lbl" for="ace-settings-breadcrumbs"> Fixed Breadcrumbs</label>
				</div>

				<!-- /section:settings.breadcrumbs -->

				<!-- #section:settings.rtl -->
				<div class="ace-settings-item">
					<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-rtl" />
					<label class="lbl" for="ace-settings-rtl"> Right To Left (rtl)</label>
				</div>

				<!-- /section:settings.rtl -->

				<!-- #section:settings.container -->
				<div class="ace-settings-item">
					<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-add-container" />
					<label class="lbl" for="ace-settings-add-container">
						Inside
						<b>.container</b>
					</label>
				</div>

				<!-- /section:settings.container -->
			</div><!-- /.pull-left -->

			<div class="pull-left width-50">
				<!-- #section:basics/sidebar.options -->
				<div class="ace-settings-item">
					<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-hover" />
					<label class="lbl" for="ace-settings-hover"> Submenu on Hover</label>
				</div>

				<div class="ace-settings-item">
					<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-compact" />
					<label class="lbl" for="ace-settings-compact"> Compact Sidebar</label>
				</div>

				<div class="ace-settings-item">
					<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-highlight" />
					<label class="lbl" for="ace-settings-highlight"> Alt. Active Item</label>
				</div>

				<!-- /section:basics/sidebar.options -->
			</div><!-- /.pull-left -->
		</div><!-- /.ace-settings-box -->
	</div><!-- /.ace-settings-container -->
</#macro>