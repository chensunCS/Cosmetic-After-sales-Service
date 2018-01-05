<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="util.Source"%>
<!doctype html>
<html lang="zh">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
	<!-- 使用viewport可以使宽度自适应与屏幕大小，1.0表示屏幕以1:1的比例呈现，不会有任何的缩放 -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>用户注册</title>
	<!-- bootstrap框架的css文件 -->
	<link href="css/bootstrap_css/bootstrap.min.css" rel="stylesheet">
	
	<!-- 默认的css文件 -->
	<link rel="stylesheet" type="text/css" href="css/general_css/default.css">
	<link rel="stylesheet" href="css/general_css/style.css"> <!-- Resource style -->
	
	<!-- sweet alert的css文件 -->
	<link rel="stylesheet" type="text/css" href="css/sweetalert_css/sweetalert.css">
	
	<!-- switch的css文件 -->
    <link href="css/switch_css/highlight.css" rel="stylesheet">
    <link href="css/switch_css/bootstrap-switch.css" rel="stylesheet">
    <link href="css/switch_css/main.css" rel="stylesheet">
	
	<!-- 上传框的css文件 -->
	<link href="css/upload_css/fileinput.css" media="all" rel="stylesheet" type="text/css" />
	
	<!-- 时间选择器的css文件 -->
	<link href="css/register_css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
		
	<!-- 默认脚注css文件 -->
	<link rel="stylesheet" type="text/css" href="css/general_css/footer.css">
	
</head>
<body>

	<div class="htmleaf-container">
		
		<!-- 导航栏 -->
	    <ul class="navbar navbar-default navbar-fixed-top">
	        <!--根据内部边框计算元素大小-->
	        <div class="container-fluid">
	            <div class="navbar-header">
	                <a href="<%="./frontPage.jsp"%>" class="navbar-brand">
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
	                    <li><a href="<%="./frontPage.jsp"%>">回到首页</a></li>
	                    <li><a href="<%="./frontPage.jsp"%>">退出注册</a></li>
	                </ul>
	            </div>
	
	        </div>
	    </ul>
		
		<!-- 注册表单 -->
		<div class="container kv-main">
			<!-- 置顶图片位置 -->
			<img class="img-responsive img-rounded" alt="置顶图片" src="images/header.jpg">
            <div style="width: 80%; margin: 0 auto; margin-top: 10px;">
            	<h1 style="margin: 0 auto; text-align: center;">注册您的个人信息</h1>
            </div>
            <div>
            	<h2>个人基本信息</h2>
            	<hr>
            </div>
            <form id="register_form" action="servlet/RegisterServlet" enctype="multipart/form-data" method="post">
            		
            	<!-- 输入用户姓名的输入框 -->
				<div class="control-group">
					<label class="control-label" contenteditable="true" for="inputName">姓名<em style="color: red">*</em></label>
					<span class="controls"><input id="inputName" class="form-control" name="user_name" placeholder="填写姓名" type="text" /></span>
				</div>
				<br>
            
        	    <!-- 输入用户电话的输入框 -->
				<div class="control-group">
				<label class="control-label" contenteditable="true" for="inputPhone">电话<em style="color: red">*</em></label>
					<!-- 输入组 -->
					<div class="input-group">
						<input id="inputPhone" class="form-control" name="user_phone" placeholder="填写电话（8到11为数字有效）（请勿填写非法字符）" type="text" />
						<span class="input-group-btn">
							<button id="sendSMSID_button" type="button" class="btn btn-info" onclick="getSMSID()">获取手机验证码</button>
							<button id="inputSMSID_button" type="button" class="btn btn-danger" style="display: none;" onclick="inputSMSCode()">填写验证码</button>
						</span>
					</div>
				
					
				</div>
				<br>
				
				<!-- 输入用户性别的输入框 -->
				<div class="control-group">
					<label class="control-label" contenteditable="true" for="inputGender">性别<em style="color: red">*</em></label>
	               	<div class="switch" >
					    <input id="inputGender" name="user_gender" class="form-control" 
					           type="checkbox" checked data-on-text="男" data-off-text="女"
					           data-on-color="success" data-off-color="warning"/>
					</div>
				</div>
				<br>
				
				<!-- 输入用户年龄的输入框 -->
				<div class="control-group">
					<label class="control-label" contenteditable="true" for="inputAge">年龄<em style="color: red">*</em></label>	
					<span class="controls"><input id="inputAge" class="form-control" name="user_age" placeholder="填写年龄（1~100岁有效）（请勿填写其他字符）" type="text" /></span>
				</div>
				<br>
				
				<!-- 输入用户家庭住址的输入框 -->
				<div class="control-group">
					<label class="control-label" contenteditable="true" for="inputAddress">家庭住址<em style="color: red">*</em></label>	
					<span class="controls"><textarea id="inputAddress" class="form-control" name="user_address" placeholder="家庭住址" rows="3"></textarea></span>
				</div>
				<br>
				
				<!-- 分界线，下面为皮肤现状的信息 -->
				<div>
					<h2>面部皮肤现状 </h2>
					<hr>
            	</div>
				
				<!-- 输入用户皮肤属性的输入框 -->				
				<div class="control-group">
					<label class="control-label" contenteditable="true" for="inputAttr">目前面部皮肤属性<em style="color: red">*</em></label>	
														
					<div class="input-group">
						<div class="input-group-btn">
				        <button type="button" class="btn btn-success dropdown-toggle" data-toggle="dropdown">点击选择皮肤属性 <span class="caret"></span></button>
					        <ul class="dropdown-menu" role="menu">
								<li>
									<a href="#" id="属性1" onclick="appendAttr(this); return false;">中性</a>
								</li>
								<li>
									<a href="#" id="属性2" onclick="appendAttr(this); return false;">干性</a>
								</li>
								<li>
									<a href="#" id="属性3" onclick="appendAttr(this); return false;">油性</a>
								</li>
								<li>
									<a href="#" id="属性4" onclick="appendAttr(this); return false;">混合性</a>
								</li>
								<li>
									<a href="#" id="属性5" onclick="appendAttr(this); return false;">敏感性</a>
								</li>
					        </ul>
				    	</div><!-- /btn-group -->
						<!-- 显示输入皮肤属性的输入框 -->
						<input id="inputAttr" class="form-control" name="user_skin_attr" placeholder="在菜单中选择您的皮肤属性" onclick="" readonly="readonly"></input>
						<!-- 显示移除皮肤属性的输入框 -->
						<span class="input-group-addon">
							<a style="text-decoration: none; color:black;" href="#" class="glyphicon glyphicon-remove" onclick="clearAttr(); return false;"></a>
						</span>
	                </div>	
						
				</div>
				<br>
				
				<!-- 输入用户目前使用护肤品的输入框 -->				
				<div class="control-group">
					<label class="control-label" contenteditable="true" for="inputProduct">选择目前使用产品<em style="color: red">*</em></label>	
					<span class="controls"><input id="inputProduct" class="form-control" name="user_present_product" placeholder="请填写目前您使用的产品（以分号分隔开）" type="text" /></span>
				</div>
				<br>
				
				<!-- 输入用于异常状况的输入框 -->
				<div class="control-group">
					<label class="control-label" contenteditable="true" for="inputProblem">目前异常状况<em style="color: red">*</em></label>	
					<span class="controls"><textarea id="inputProblem" class="form-control" name="user_skin_problem" placeholder="异常状况" rows="3"></textarea></span>
				</div>
				<br>
				
				<hr>
				
				<!-- 输入拍摄时间的控件 -->			    
				<div class="form-group">
	                <label for="dtp_input2" class="control-label">现状照片拍摄日期<em style="color:red">*</em></label>
	                <div class="input-group date form_date" data-date="" data-date-format="yyyy-m-dd" data-link-field="dtp_input2" data-link-format="yyyy-m-dd">
	                    <input class="form-control" id="upload_photo_date" name="upload_photo_date" type="text" placeholder="点击右侧按钮选择日期" readonly>
	                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
						<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
	                </div>
					<input type="hidden" id="dtp_input2" value="" /><br/>
	            </div>
           		
           		<!-- 上传现状照片的输入框 -->
                <div class="control-group">
               		<label class="control-label" contenteditable="true" for="file-1">上传现状照片（请确认上传的文件符合图片的格式）<em style="color: red">*</em></label>	
                    <input id="file-1" type="file" name="previous_photo" multiple class="file" data-overwrite-initial="false" data-min-file-count="2">
                    <br>
                    
	                <!-- 输入用户备注的输入框 -->
					<div class="control-group">
						<label class="control-label" contenteditable="true" for="inputRemark">备注</label>	
						<span class="controls"><textarea id="inputRemark" class="form-control" name="user_remark" placeholder="您的其他问题或需求" rows="4"></textarea></span>
					</div>
					<br>
                    
                    <!-- 提交按钮 -->
	                <button id="submit_graphic" class="btn btn-primary" onclick="register(); return false;" disabled >保存</button>
	                <button id="reset_graphic" type="reset" class="btn btn-default">重置</button>
                </div>
                
            </form>
        </div>
		
	</div>
	
	<div id="footer">
		<hr>
        <div class="container">
            <P>深圳前海广利生物科技有限公司@2016</P>
        </div>
    </div>
    
    <!-- 导入js文件的部分 -->
	
	<!-- jQuery的js文件 -->
    <script src="js/jQuery/jquery-3.1.0.min.js"></script>
    
    <!-- bootstrap框架的css文件 -->
	<script src="js/bootstrap_js/bootstrap.min.js"></script>
	
	<!-- 我的js文件 -->
	<script src="js/register_js/register.js"></script>
	
	<!-- 默认的js文件 -->
	<script src="js/general_js/main.js"></script> <!-- Resource jQuery -->
	
    
    <!-- bomb短信验证的js文件 -->
    <script src="js/bmob_js/bmob-min.js"></script>
    <script src="js/bmob_js/bmobSocketIo-min.js"></script>
    
    <!-- sweet alert的js文件 -->
    <script src="js/sweetalert_js/sweetalert.min.js"></script>
        
    <!-- switch的js文件 -->
    <script src="js/switch_js/jquery.min.js"></script>
    <script src="js/switch_js/highlight.js"></script>
    <script src="js/switch_js/bootstrap-switch.js"></script>
    <script src="js/switch_js/main.js"></script>

	<!-- 时间选择器的js文件 -->
	<script type="text/javascript" src="js/register_js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
		<script type="text/javascript" src="js/register_js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
		<script type="text/javascript">
			$('.form_date').datetimepicker({
		        language:  'zh-CN',
		        weekStart: 1,
		        todayBtn:  1,
				autoclose: 1,
				todayHighlight: 1,
				startView: 2,
				minView: 2,
				forceParse: 0
		    });
		</script>
	
	<!-- 上传需要的js文件 -->
    <script src="js/upload_js/fileinput.js" type="text/javascript"></script>
    <script src="js/upload_js/fileinput_locale_zh.js" type="text/javascript"></script>
    <script>
	    $("#file-1").fileinput({
	        uploadUrl: '#', // you must set a valid URL here else you will get an error
	        allowedFileExtensions : ['jpg', 'jpeg', 'gif', 'png', 'bmg'],
	        overwriteInitial: false,
	        showUpload: false, //不显示上传按钮
	        maxFileSize: 5120,
	        maxFilesNum: 5,
	        msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
	        //allowedFileTypes: ['image', 'video', 'flash'],
	        slugCallback: function(filename) {
	            return filename.replace('(', '_').replace(']', '_');
	        }
		});
		</script>
</body>
</html>