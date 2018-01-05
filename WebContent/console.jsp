<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="database.SelectDB"%>
<%@page import="java.util.*"%>
<%@page import="object.*" %>
<%@page import="util.*" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>我的控制台</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap_css/bootstrap.min.css" rel="stylesheet">

    <!-- 我的css文件-->
    <link href="css/console_css/console_css.css" rel="stylesheet">
    
    <!-- sweet alert的css文件 -->
	<link rel="stylesheet" type="text/css" href="css/sweetalert_css/sweetalert.css">
	
</head>
<body class="container">

    <!-- 导航栏 -->
    <ul class="navbar navbar-tabs navbar-inverse navbar-fixed-top">
        <!--根据内部边框计算元素大小-->
        <div class="container-fluid">
            <div class="navbar-header">
                <a href="<%="./console.jsp" %>" class="navbar-brand">
                    <span class="glyphicon glyphicon-home"></span>
                </a>
                <!--按钮用于触发导航条的展开-->
                <button class="navbar-toggle" data-toggle="collapse" data-target="#divNav">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>

            <!--用于展开的导航栏-->
            <div id="divNav" class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li><a href="<%="./console_userInfo.jsp" %>">用户信息</a></li>
                    <li><a href="<%="./console_presentInfo.jsp" %>">填表信息</a></li>
                    <li><a href="<%="./console_photo.jsp" %>">用户照片</a></li>
                    <li><a href="<%="./frontPage.jsp" %>">退出</a></li>
                </ul>
            </div>

        </div>
    </ul>

    <!--巨幕显示总的提示信息-->
    <div class="row">
        <div class="jumbotron">
            <h1>我的控制台</h1>
            <p>下面提供一些表格 可以根据您需要进行下载</p>
            <p>
                <a class="btn btn-success btn-lg" href="#" role="button">
                    查看注册信息
                    <span class="glyphicon glyphicon-search"></span>
                </a>
            </p>
        </div>
    </div>

    <!--显示全部的数据库的表格-->
    <div class="row">
        <!--显示标题-->
        <h2><strong>数据库中的原生表</strong></h2>
        <hr>
        <!--显示第一组表-->
        <div class="row">
            <!---显示用户注册表格-->
            <div class="col-sm-6">
                <div class="panel panel-success">
                    <!--显示面板的头部-->
                    <div class="panel panel-heading">
                        <div class="row">
                            <div class="col-sm-6">
                                <h5><strong>用户注册表格(user_info)</strong></h5>
                            </div>
                            <div class="col-sm-6 text-right">
                                <a href="<%="./console_userInfo.jsp" %>" class="btn btn-primary">查看<span class="glyphicon glyphicon-search"></span></a>
                                <button type="button" class="btn btn-danger" onclick="exportExcel('<%=Source.userInfoTable %>', '<%=Source.queryUserInfo%>')">
                                	下载
                                	<span class="glyphicon glyphicon-download"></span>
                                </button>
                            </div>
                        </div>
                    </div>
                    <!--显示表格的样式-->
                    <div class="table-responsive">
	                    <table class="table table-responsive">
	                        <thead>
	                            <tr>
	                                <th>用户ID</th>
	                                <th>名字</th>
	                                <th>电话</th>
	                                <th>性别</th>
	                                <th>年龄</th>
	                                <th>地址</th>
	                                <th>皮肤属性</th>
	                                <th>目前问题</th>
	                                <th>目前使用产品</th>
	                                <th>备注</th>
	                            </tr>
	                        </thead>
	                        <tbody>
	                            <tr>
	                                <th>...</th>
	                                <th>...</th>
	                                <th>...</th>
	                                <th>...</th>
	                                <th>...</th>
	                                <th>...</th>
	                                <th>...</th>
	                                <th>...</th>
	                                <th>...</th>
	                                <th>...</th>
	                            </tr>
	                        </tbody>
	                    </table>
                    </div>
                </div>
            </div>
            <!--显示注册图片的表格-->
            <div class="col-sm-6">
                <div class="panel panel-warning">
                    <!--显示面板的头部-->
                    <div class="panel panel-heading">
                        <div class="row">
                            <div class="col-sm-6">
                                <h5><strong>注册图片表格(previous_photo)</strong></h5>
                            </div>
                            <div class="col-sm-6 text-right">
                                <button type="button" class="btn btn-danger" onclick="exportExcel('<%=Source.previousPhotoTable %>', '<%=Source.queryPreviousPhoto%>')">
                                	下载
                                	<span class="glyphicon glyphicon-download"></span>
                                </button>
                            </div>
                        </div>
                    </div>
                    <!--显示表格的样式-->
                    <div class="table-responsive">
	                    <table class="table table-responsive">
	                        <thead>
	                        <tr>
	                            <th>用户ID</th>
	                            <th>图片ID</th>
	                            <th>注册的日期</th>
	                            <th>图片名</th>
	                            <th>图片路径</th>
	                        </tr>
	                        </thead>
	                        <tbody>
	                        <tr>
	                            <th>...</th>
	                            <th>...</th>
	                            <th>...</th>
	                            <th>...</th>
	                            <th>...</th>
	                        </tr>
	                        </tbody>
	                    </table>
                    </div>
                </div>
            </div>
        </div>

        <!--显示第二组表-->
        <div class="row">
            <!---显示用户填写的表格-->
            <div class="col-sm-6">
                <div class="panel panel-info">
                    <!--显示面板的头部-->
                    <div class="panel panel-heading">
                        <div class="row">
                            <div class="col-sm-6">
                                <h5><strong>用户反馈表格(present_info)</strong></h5>
                            </div>
                            <div class="col-sm-6 text-right">
                                <a href="<%="./console_presentInfo.jsp" %>" class="btn btn-primary">查看<span class="glyphicon glyphicon-search"></span></a>
                                <button type="button" class="btn btn-danger" onclick="exportExcel('<%=Source.presentInfoTable %>', '<%=Source.queryPresentInfo%>')">
                                	下载
                                	<span class="glyphicon glyphicon-download"></span>
                                </button>
                            </div>
                        </div>
                    </div>
                    <!--显示表格的样式-->
                    <div class="table-responsive">
	                    <table class="table table-responsive">
	                        <thead>
	                        <tr>
	                            <th>用户ID</th>
	                            <th>反馈ID</th>
	                            <th>使用日期</th>
	                            <th>使用产品</th>
	                            <th>使用感觉</th>
	                            <th>出现的问题</th>
	                            <th>备注</th>
	                        </tr>
	                        </thead>
	                        <tbody>
	                        <tr>
	                            <th>...</th>
	                            <th>...</th>
	                            <th>...</th>
	                            <th>...</th>
	                            <th>...</th>
	                            <th>...</th>
	                            <th>...</th>
	                        </tr>
	                        </tbody>
	                    </table>
                    </div>
                </div>
            </div>
            <!--显示用户填写反馈的图片信息-->
            <div class="col-sm-6">
                <div class="panel panel-danger">
                    <!--显示面板的头部-->
                    <div class="panel panel-heading">
                        <div class="row">
                            <div class="col-sm-6">
                                <h5><strong>反馈图片表格(present_photo)</strong></h5>
                            </div>
                            <div class="col-sm-6 text-right">
                                <button type="button" class="btn btn-danger" onclick="exportExcel('<%=Source.presentPhotoTable %>', '<%=Source.queryPresentPhoto%>')">
                                	下载
                                	<span class="glyphicon glyphicon-download"></span>
                                </button>
                            </div>
                        </div>
                    </div>
                    
	                <!--显示表格的样式-->
	                <div class="table-responsive">
	                    <table class="table table-responsive">
	                        <thead>
	                        <tr>
	                            <th>用户ID</th>
	                            <th>图片ID</th>
	                            <th>上传日期</th>
	                            <th>图片名</th>
	                            <th>图片路径</th>
	                        </tr>
	                        </thead>
	                        <tbody>
	                        <tr>
	                            <th>...</th>
	                            <th>...</th>
	                            <th>...</th>
	                            <th>...</th>
	                            <th>...</th>
	                        </tr>
	                        </tbody>
	                    </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!--显示其他表-->
    <div class="row">
        <!--显示标题-->
        <h2><strong>合成的表格</strong></h2>
        <hr>

        <div class="panel-group" id="other_form_panel" role="tablist" aria-multiselectable="true">
            <div class="panel panel-success">
                <div class="panel-heading" role="tab" id="register_form_head">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#other_form_panel" href="#register_body" aria-expanded="true" aria-controls="register_body">
                            全部注册信息(register_info)
                        </a>
                    </h4>
                </div>
                <div id="register_body" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="register_form_head">
                    <div class="panel-body">
                    	<div class="table-responsive">
                        	<!--显示注册表格的样式-->
	                        <table class="table">
	                            <thead>
		                            <tr>
		                                <th>用户ID</th>
		                                <th>名字</th>
		                                <th>电话</th>
		                                <th>性别</th>
		                                <th>年龄</th>
		                                <th>地址</th>
		                                <th>皮肤属性</th>
		                                <th>目前问题</th>
		                                <th>目前使用产品</th>
		                                <th>备注</th>
		                                <th>注册图片ID</th>
		                                <th>注册的日期</th>
		                                <th>图片名</th>
		                                <th>图片路径</th>
		                            </tr>
	                            </thead>
	                            <tbody>
		                            <tr>
		                                <th>...</th>
		                                <th>...</th>
		                                <th>...</th>
		                                <th>...</th>
		                                <th>...</th>
		                                <th>...</th>
		                                <th>...</th>
		                                <th>...</th>
		                                <th>...</th>
		                                <th>...</th>
		                                <th>...</th>
		                                <th>...</th>
		                                <th>...</th>
		                                <th>...</th>
		                            </tr>
	                            </tbody>
	                        </table>
                        </div>
                        <!--显示下载的按钮-->
                        <button type="button" class="btn btn-success" onclick="exportExcel('<%=Source.registerInfoTable %>', '<%=Source.queryRegisterInfo%>')">
                            下载该表格
                            <span class="glyphicon glyphicon-download"></span>
                        </button>
                    </div>
                </div>
            </div>
            <div class="panel panel-warning">
                <div class="panel-heading" role="tab" id="feedback_form_head">
                    <h4 class="panel-title">
                        <a class="collapsed" data-toggle="collapse" data-parent="#other_form_panel" href="#feed_back_body" aria-expanded="false" aria-controls="feed_back_body">
                            全部反馈信息(feedback_info)
                        </a>
                    </h4>
                </div>
                <div id="feed_back_body" class="panel-collapse collapse" role="tabpanel" aria-labelledby="feedback_form_head">
                    <div class="panel-body">
                        <!--显示反馈表格的样式-->
                        <div class="table-responsive">
	                        <table class="table table-responsive">
	                            <thead>
	                            <tr>
	                                <th>用户ID</th>
	                                <th>使用日期</th>
	                                <th>使用产品</th>
	                                <th>使用感觉</th>
	                                <th>出现的问题</th>
	                                <th>备注</th>
	                                <th>上传日期</th>
	                                <th>图片名</th>
	                                <th>图片路径</th>
	                            </tr>
	                            </thead>
	                            <tbody>
	                            <tr>
	                                <th>...</th>
	                                <th>...</th>
	                                <th>...</th>
	                                <th>...</th>
	                                <th>...</th>
	                                <th>...</th>
	                                <th>...</th>
	                                <th>...</th>
	                                <th>...</th>
	                            </tr>
	                            </tbody>
	                        </table>
                        </div>
                        <!--显示下载的按钮-->
                        <button type="button" class="btn btn-success" onclick="exportExcel('<%=Source.feedBackInfoTable %>', '<%=Source.queryFeedBackInfo%>')">
                            下载该表格
                            <span class="glyphicon glyphicon-download"></span>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!--尾部-->
    <div class="row">
        <footer class="text-center">
            <hr>
            <P>深圳前海广利生物科技有限公司@2016</P>
        </footer>
    </div>


<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="js/scripts/jquery.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap_js/bootstrap.min.js"></script>

<!-- sweet alert的js文件 -->
<script src="js/sweetalert_js/sweetalert.min.js"></script>

<!-- 我的js文件 -->
<script src="js/console_js/console.js"></script>

<!-- 判断是否需要登录 -->
<script>
	jQuery(document).ready(function() {
		
		console.log('pp');
		
		var user_name = '<%=session.getAttribute("admin_id")%>';
		var user_phone = '<%=session.getAttribute("admin_password")%>';
		//判断是否存在
		if(user_name == "" || user_name == null || user_name == "null") {
			swal({
				  title: "没有登录管理员",
				  text: "请先登录后填写",
				  type: "error",
				  confirmButtonText: "好的",
				  closeOnConfirm: false,
				  closeOnCancel: false
				},
				function(isConfirm){
					//跳转到首页
					window.location.href="<%="./frontPage.jsp"%>";
				});
		}
		else {
		}
		
	});
</script>

</body>
</html>
