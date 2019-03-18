<#import "common/common.ftl" as common />
<#import "common/menu.ftl" as menu />
<!DOCTYPE html>
<html lang="en" xmlns="http://java.sun.com/jsf/facelets">
<head>
	<@common.top />
	<script type="text/javascript" src="/static/js/showdown.min.js"></script>
	<script type="text/javascript">
		$(function () {
			compile();
		})
		function compile(){
			var text = document.getElementById("content").innerHTML;
			var converter = new showdown.Converter();
			var html = converter.makeHtml(text);
			console.log(html);
			document.getElementById("result").innerHTML = html;
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

					<!-- /section:basics/content.searchbox -->
				</div>

				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
				<@common.setting />
					<#--<#import "doc/README.md" as doc />-->
					<#--<@doc.text />-->
                    <div>
                        <div id="content" style="display: none">
							<#include "doc/README.md" />
                        </div>
						<div id="result"></div>
                    </div>
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
