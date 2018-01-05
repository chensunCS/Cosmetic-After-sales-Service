<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="util.*"%>
<%@page import="java.util.*" %>
<!doctype html>
<html lang="zh">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
	<!-- 使用viewport可以使宽度自适应与屏幕大小，1.0表示屏幕以1:1的比例呈现，不会有任何的缩放 -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>填写反馈信息</title>
	<!-- bootstrap框架的css文件 -->
	<link href="css/bootstrap_css/bootstrap.min.css" rel="stylesheet">
	
	<!-- 默认的css文件 -->
	<link rel="stylesheet" type="text/css" href="css/general_css/default.css">
	<link rel="stylesheet" href="css/general_css/style.css"> <!-- Resource style -->
	
	<!-- 上传框的css文件 -->
	<link href="css/upload_css/fileinput.css" media="all" rel="stylesheet" type="text/css" />
	
	<!-- sweet alert的css文件 -->
	<link rel="stylesheet" type="text/css" href="css/sweetalert_css/sweetalert.css">
	
	<!-- 时间选择器的css文件 -->
	<link href="css/register_css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
	
	<!-- 默认脚注css文件 -->
	<link rel="stylesheet" type="text/css" href="css/general_css/footer.css">
	
	<!-- 导入js文件的部分 -->
	
	<!-- jQuery的js文件 -->
    <script src="js/jQuery/jquery-3.1.0.min.js"></script>
    
    <!-- bootstrap框架的css文件 -->
	<script src="js/bootstrap_js/bootstrap.min.js"></script>
	
	<!-- 默认的js文件 -->
	<script src="js/general_js/main.js"></script> <!-- Resource jQuery -->
	
	<!-- 我的js文件 -->
	<script src="js/record_js/record.js"></script> 
	
	<!-- 判断是否需要登录 -->
	<script>
		jQuery(document).ready(function() {    
			
			var user_name = '<%=session.getAttribute("user_name")%>';
			var user_phone = '<%=session.getAttribute("user_phone")%>';
			//判断是否存在
			if(user_name == "" || user_name == null || user_name == "null") {
				swal({
					  title: "没有登录",
					  text: "请先登录后填写",
					  type: "warning",
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
</head>

<body id="record_body">

	<!-- 显示上传时候的模态框 -->
	<div id="upload_modal" class="modal fade">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <!-- 模态框的头部 -->
	            <div class="modal-header text-center">
	                <!-- 显示标题 -->
	                <h4 class="modal-title">正在下载，请稍等片刻:)</h4>
	            </div>
	            <!-- 模态框的主体 -->
	            <div class="modal-body text-center">
	                <div class="row">
	                    <div class="col-sm-12 text-center">
	                        <!-- 显示动态的gif -->
	                        <img class="img-responsive center-block" src="img/loading.gif">
	                    </div>
	                </div>
	
	            </div>
	            <!-- 模态框的脚注 -->
	            <div class="modal-footer text-center">
	                <h4>深圳前海广利生物科技有限公司 @2016</h4>
	            </div>
	        </div><!-- /.modal-content -->
	    </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->

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
            	<h1 style="margin: 0 auto; text-align: center;">使用产品后的反馈</h1>
            </div>
            <div>
            	<h2>个人基本信息</h2>
            	<hr>
            </div>
            <form id="record_form" action="servlet/RecordServlet" enctype="multipart/form-data" method="post">
            		
            	<!-- 输入用户姓名的输入框 -->
				<div class="control-group">
					<label class="control-label" contenteditable="true" for="inputName">姓名<em style="color: red">*</em></label>
					<span class="controls"><input id="inputName" class="form-control" readonly name="user_name" placeholder="姓名" type="text" value="<%=session.getAttribute("user_name") %>" /></span>
				</div>
				<br>
            
        	    <!-- 输入用户电话的输入框 -->
				<div class="control-group">
					<label class="control-label" contenteditable="true" for="inputPhone">电话<em style="color: red">*</em></label>
					<span class="controls"><input id="inputPhone" class="form-control" readonly name="user_phone" placeholder="电话" type="text" value="<%=session.getAttribute("user_phone") %>" /></span>
				</div>
				<br>
				
				<!-- 分界线，下面为使用产品后的反馈的信息 -->
				<div>
					<h2>使用后的反馈 </h2>
					<hr>
            	</div>
            	
            	<!-- 输入使用目前产品时间的控件 -->			    
				<div class="form-group">
	                <label for="dtp_input2" class="control-label">使用产品的日期<em style="color:red">*</em></label>
	                <div class="input-group date form_date" data-date="" data-date-format="yyyy-m-dd" data-link-field="dtp_input2" data-link-format="yyyy-m-dd">
	                    <input class="form-control" id="use_date" name="use_date" type="text" placeholder="点击右侧选择使用产品的日期" readonly>
	                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
						<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
	                </div>
					<input type="hidden" id="dtp_input2" value="" /><br/>
	            </div>
				
				<!-- 输入用户目前使用产品的输入框 -->				
				<div class="control-group">
					<label class="control-label" contenteditable="true" for="inputProduct">使用的护肤产品<em style="color: red">*</em></label>	
					<span class="controls"><input id="inputProduct" class="form-control" name="use_product" placeholder="请填写您现在使用的产品（以分号分隔开）" type="text" /></span>
				</div>
				<br>
				
				<!-- 输入使用后的感觉的输入框 -->
				<div class="control-group">
					<label class="control-label" contenteditable="true" for="inputFeel">使用后的感觉<em style="color: red">*</em></label>	
					<span class="controls"><textarea id="inputFeel" class="form-control" name="use_feeling" placeholder="填写试用产品后的感觉" rows="3"></textarea></span>
				</div>
				<br>
				
				<!-- 输入异常状况的输入框 -->
				<div class="control-group">
					<label class="control-label" contenteditable="true" for="inputProblem">异常状况<em style="color: red">*</em></label>	
					<span class="controls"><textarea id="inputProblem" class="form-control" name="use_problem" placeholder="使用产品后的异常状况" rows="3"></textarea></span>
				</div>
				<br>
				
				<hr>
				
				<!-- 输入拍摄时间的控件 -->			    
				<div class="form-group">
	                <label for="upload_date" class="control-label">今天填表日期<em style="color:red">*</em></label>
	                <span class="controls"><input id="upload_date" class="form-control" readonly name="upload_date" type="text" value="<%=DateUtil.dateToString(new Date()) %>" /></span>
	            </div>
           		
           		<!-- 上传现状照片的输入框 -->
                <div class="control-group">
               		<label class="control-label" contenteditable="true" for="file-1">上传使用后的照片（请确认上传的文件符合图片的格式）<em style="color: red">*</em></label>	
                    <input id="file-1" type="file" name="present_photo" multiple class="file" data-overwrite-initial="false" data-min-file-count="2">
                    <br>
                    
	                <!-- 输入用户备注的输入框 -->
					<div class="control-group">
						<label class="control-label" contenteditable="true" for="inputRemark">备注</label>	
						<span class="controls"><textarea id="inputRemark" class="form-control" name="user_remark" placeholder="您的其他问题或需求" rows="4"></textarea></span>
					</div>
					<br>
                    
                    <!-- 提交按钮 -->
	                <button id="submit_graphic" class="btn btn-primary" onclick="record(); return false;">保存</button>
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
	
	<!-- sweet alert的js文件 -->
    <script src="js/sweetalert_js/sweetalert.min.js"></script>
	
	<!-- 上传需要的js文件 -->
    <script src="js/upload_js/fileinput.js" type="text/javascript"></script>
    <script src="js/upload_js/fileinput_locale_zh.js" type="text/javascript"></script>
    <script>
	    $("#file-1").fileinput({
	        uploadUrl: '#', // you must set a valid URL here else you will get an error
	        allowedFileExtensions : ['jpg', 'jpeg', 'gif', 'png', 'bmg'],
	        overwriteInitial: false,
	        showUpload: false, //是否显示上传按钮
	        maxFileSize: 5120,
	        maxFilesNum: 5,
	        //allowedFileTypes: ['image', 'video', 'flash'],
	        slugCallback: function(filename) {
	            return filename.replace('(', '_').replace(']', '_');
	        }
		});
		</script>
</body>
</html>