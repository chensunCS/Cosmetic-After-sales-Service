<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="util.Source"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8"/>
<title>首页</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport"/>
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href='css/front_page_css/family.css' rel='stylesheet' type='text/css'>
<link href="css/front_page_css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="css/front_page_css/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
<link href="css/front_page_css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
<link href="css/front_page_css/owl.carousel.css" rel="stylesheet">
<link href="css/front_page_css/settings.css" rel="stylesheet">
<link href="css/front_page_css/cubeportfolio.min.css" rel="stylesheet">
<!-- END PAGE LEVEL PLUGIN STYLES -->
<!-- BEGIN THEME STYLES -->
<link href="css/front_page_css/layout.css" rel="stylesheet" type="text/css"/>
<link href="css/front_page_css/custom.css" rel="stylesheet" type="text/css"/>
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="favicon.ico"/>

<!-- sweet alert的css文件 -->
<link rel="stylesheet" type="text/css" href="css/sweetalert_css/sweetalert.css">

<!-- 我的css文件 -->
<link rel="stylesheet" type="text/css" href="css/front_page_css/frontPage.css">

</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<!-- DOC: Apply "page-on-scroll" class to the body element to set fixed header layout -->
<body class="page-header-fixed">

	<button id="adminLoginButton" type="button" class="btn btn-warning btn-block" data-toggle="modal" data-target="#login_admin_dialog">
		管理员入口
	</button>
    
    <!--显示模态框-->
    <div class="modal fade" id="login_admin_dialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <!--模态对话框-->
        <div class="modal-dialog">
            <!--对话框的内容-->
            <div class="modal-content">

                <div class="modal-header">
                    <!--添加关闭的按钮-->
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">&times;</span>
                        <span class="sr-only">Close</span>
                    </button>
                    <!--显示对话框的标题-->
                    <h4 class="modal-title">登录管理员</h4>
                </div>

                <!--这里是对话框的主体-->
                <div class="modal-body">
                    <!--登录的表单-->
                    <form id="admin_login_form" class="form-horizontal" role="form">
                        <!--管理员ID的输入框-->
                        <div class="form-group">
                            <label for="adminID" class="col-sm-3 control-label">管理员ID</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="adminID" name="admin_id" placeholder="请输入您的管理员ID">
                            </div>

                        </div>
                        <!--管理员密码的输入框-->
                        <div class="form-group">
                            <label for="adminPassword" class="col-sm-3 control-label">管理员密码</label>
                            <div class="col-sm-9">
                                <input type="password" class="form-control" id="adminPassword" name="admin_password" placeholder="请输入您的管理员密码">
                            </div>

                        </div>
                    </form>
                </div>

                <!--这里是对话框的脚注-->
                <div class="modal-footer">
                    <!--显示确定按钮和取消按钮-->
                    <button type="button" class="btn btn-primary" onclick="adminLogin()">登录</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                </div>

            </div>
        </div>
    </div>

    <!-- BEGIN MAIN LAYOUT -->
    <div class="page-content">
    	<section>
        <!-- SUBSCRIBE BEGIN -->
        <div id="head_div" class="subscribe" >
            <div class="container">
            <!-- 登录需要的div -->
                <div class="subscribe-wrap">
                    <div class="subscribe-body subscribe-desc md-margin-bottom-30">
                        <h1><strong id="user_info">登录您的账号</strong></h1>
                        <p><strong id="tips">请在右侧填写您的姓名和电话</strong></p>
                    </div>
                    <div id="login_div" class="subscribe-body">
                        <form id="login_form" class="form-wrap input-field" onSubmit="return isLoginValid();">
                        	<!-- 登录需要的div -->
	                        <div class="form-wrap-group">
	                            <input type="text" name="user_name" class="form-control" id="user_name" placeholder="姓名">
	                        </div>
	                        <div class="form-wrap-group border-left-transparent">
	                            <input type="text" name="user_phone" class="form-control" id="user_phone" placeholder="电话">
	                        </div>
	                        <div class="form-wrap-group">
	                            <button type="submit" class="btn-success btn-md btn-block" onclick="login(); return false;">登录</button>
	                        </div>
	                        <div class="form-wrap-group">
	                             <a type="button" class="btn-danger btn-md btn-block" href="<%="./register.jsp"%>">注册</a>
	                        </div>
                        </form>

                    </div>
                    
                    <div id="logined_div" class="form-wrap-group">
                    	<button id="logout_button" class="btn btn-primary btn-block" onclick="logout()">注销用户</button>
                    	<button id="retreat_button" class="btn btn-info btn-block" onclick="retreat()">撤回填写</button>
                    </div>
                                    
                </div>
                
            </div>
        </div>
        <!-- SUBSCRIBE END -->
        </section>
        <!-- END CONTACT SECTION -->
    </div>

    <!-- BEGIN INTRO SECTION -->
    <section id="intro">
        <!-- Slider BEGIN -->
        <div class="page-slider">
            <div class="fullwidthbanner-container revolution-slider">
                <div class="banner">
                    <ul id="revolutionul">
                        <!-- THE NEW SLIDE -->
                        <li data-transition="fade" data-slotamount="8" data-masterspeed="700" data-delay="9400" data-thumb="">
                            <!-- THE MAIN IMAGE IN THE FIRST SLIDE -->
                            <img src="img/bg_slider1.jpg" alt="">
                          
                            <div class="caption lft tp-resizeme"
                                data-x="center"
                                data-y="center"
                                data-hoffset="-340" 
                                data-voffset="-70"
                                data-speed="900"
                                data-start="1000"
                                data-easing="easeOutExpo">
                                <h3>填写产品的反馈</h3>
                            </div>
                            <div class="caption lft tp-resizeme"
                                data-x="center"
                                data-y="center"
                                data-hoffset="-400" 
                                data-voffset="45"
                                data-speed="900"
                                data-start="1500"
                                data-easing="easeOutExpo">
                                <p class="subtitle-v1">感谢您使用我们的产品 <br>
                                点击下面的按钮来填写产品的反馈</p>
                            </div>
                            <div class="caption lft tp-resizeme"
                                data-x="center"
                                data-y="center"
                                data-hoffset="-450" 
                                data-voffset="140"
                                data-speed="900" 
                                data-start="2000" 
                                data-easing="easeOutExpo">
                                <a href="<%="./record.jsp"%>" class="btn-brd-white">填写反馈</a>
                                <a id="user_photo_button" href="<%="./user_photo.jsp"%>" class="btn-brd-white">查看我的图片</a>
                            </div>
                  			<div class="caption lfb tp-resizeme"
                                data-x="right"
                                data-y="bottom" 
                                data-hoffset="50"
                                data-speed="900" 
                                data-start="2500"
                                data-easing="easeOutExpo">
                                <img src="img/men.png" alt="Image 3">
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <!-- Slider END -->
    </section>
    <!-- END INTRO SECTION -->

    <!-- Footer Coypright -->
    <div id="footer-copyright" class="footer-copyright">
        <div class="container">
           <P>深圳前海广利生物科技有限公司@2016</P>
        </div>
    </div>
    <!-- End Footer Coypright -->
    
<!-- sweet alert的js文件 -->
<script src="js/sweetalert_js/sweetalert.min.js"></script>
    
<!-- 我的js文件 -->
<script src="js/front_page_js/front_page.js" type="text/javascript"></script>

<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="js/respond.min.js"></script>
<script src="js/excanvas.min.js"></script>
<![endif]-->
<script src="js/front_page_js/jquery.min.js" type="text/javascript"></script>
<script src="js/front_page_js/jquery-migrate.min.js" type="text/javascript"></script>
<script src="js/front_page_js/bootstrap.min.js" type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="js/front_page_js/jquery.easing.js" type="text/javascript"></script>
<script src="js/front_page_js/jquery.parallax.js" type="text/javascript"></script>
<script src="js/front_page_js/smooth-scroll.js" type="text/javascript"></script>
<script src="js/front_page_js/owl.carousel.min.js" type="text/javascript"></script>
<!-- BEGIN Cubeportfolio -->
<script src="js/front_page_js/jquery.cubeportfolio.min.js" type="text/javascript"></script>
<script src="js/front_page_js/portfolio.js" type="text/javascript"></script>
<!-- END Cubeportfolio -->
<!-- BEGIN RevolutionSlider -->  
<script src="js/front_page_js/jquery.themepunch.revolution.min.js" type="text/javascript"></script>
<script src="js/front_page_js/jquery.themepunch.tools.min.js" type="text/javascript"></script>
<script src="js/front_page_js/revo-ini.js" type="text/javascript"></script>
<!-- END RevolutionSlider -->
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="js/front_page_js/layout.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<script>
jQuery(document).ready(function() {    
	
	//获取Session中的用户名，和用户Id
	var userName = '<%=request.getSession().getAttribute("user_name") %>';
 	var userPhone = '<%=request.getSession().getAttribute("user_phone")%>';
 	
 	//如果存在，则去掉注册登录的按钮，显示用户的信息
 	if(userName != null && userName != "null" && userName != "") {
		//隐藏登录、注册的元素
		$("#login_div").hide();
		//设置欢迎用户的标题
		document.getElementById("user_info").innerHTML="欢迎回来，" + userName;
		//设置导航栏的标题
		document.getElementById("tips").innerHTML="在下方填写您的产品反馈";
		//显示注销按钮
		$("#logined_div").show();
		//显示用户图片的按钮
		console.log(document.getElementById("user_photo_button"));
		document.getElementById("user_photo_button").style.visibility= 'visible';
	}
	
    Layout.init();
    RevosliderInit.initRevoSlider();
});
</script>
<!-- END JAVASCRIPTS -->

</body>

<!-- END BODY -->
</html>