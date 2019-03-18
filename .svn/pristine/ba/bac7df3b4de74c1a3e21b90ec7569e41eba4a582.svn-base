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
							<a href="/admin">Home</a>
						</li>

						<li class="active">差异</li>
					</ul><!-- /.breadcrumb -->

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
									版本${currentVersion.version} -> ${nextVersion.version}
							</small>
						</h1>
					</div><!-- /.page-header -->

					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<form class="form-horizontal" role="form" >
								<!-- #section:elements.form -->

                                <div class="space-4"></div>

                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 版本 : ${currentVersion.version} </label>
                                    <div class="col-sm-9">
                                        <pre>${currentVersion.dataInfo}</pre>
                                    </div>
                                </div>

								<hr />
							</form>

						</div><!-- /.col -->
					</div><!-- /.row -->
                    <div class="row">
                        <div class="col-xs-12">
                            <!-- PAGE CONTENT BEGINS -->
                            <form class="form-horizontal" role="form" >
                                <!-- #section:elements.form -->

                                <div class="space-4"></div>

                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 版本 : ${nextVersion.version} </label>
                                    <div class="col-sm-9">
                                        <pre>${nextVersion.dataInfo}</pre>
                                    </div>
                                </div>

                                <hr />
                            </form>

                        </div><!-- /.col -->
                    </div><!-- /.row -->
                    <div class="row">
                        <div class="col-xs-12">
                            <!-- PAGE CONTENT BEGINS -->
                            <form class="form-horizontal" role="form" >
                                <!-- #section:elements.form -->

                                <div class="space-4"></div>

                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 差异 </label>
                                    <div class="col-sm-9">
                                        <pre>${diffData}</pre>
                                    </div>
                                </div>

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

</body>
</html>
