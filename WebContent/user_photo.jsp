<%@page import="javax.sound.midi.SysexMessage"%>
<%@ page language="java" contentType="text/html;charset=utf-8"
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
    <title>用户图片</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap_css/bootstrap.min.css" rel="stylesheet">

    <!-- 我的css文件-->
    <link href="css/console_css/console_css.css" rel="stylesheet">

</head>
<body id="console_photo_body" class="container">

	    <!-- 开始初始化用户的注册信息 -->
    <%
    	//获取用户名
    	String userName = (String)request.getSession().getAttribute("user_name");
    	//获取用户手机号
    	String userPhone = (String)request.getSession().getAttribute("user_phone");

    	//判断是否为空
    	if(userName != null && !userName.equals("null")) {
    	//定义用户ID
    	String userID = userName + "_" + userPhone;
    	
    	//设置解码为utf-8
    	request.setCharacterEncoding("utf-8");
    	
    	//定义查询对象
    	SelectDB selectDB = new SelectDB();
    	//定义查询用户的sql
    	String queryUserInfo = "select * from user_info where user_id='" + userID + "'";
    	System.out.println("查询用户的语句是：" + queryUserInfo);
    	//获取返回该用户的信息
    	ArrayList<UserInfo> userInfos = selectDB.selectUserInfo(queryUserInfo);
    	//获取本次查询的提交数量
    	int currentInfoNum = userInfos.size();
    	//获取该用户的信息
    	UserInfo userInfo = null;
    	if(currentInfoNum == 1) {
    		userInfo = userInfos.get(0);
    	}
    	
    	//定义查找注册图片的sql语句
    	String previousPhotoSQL = "select * from previous_photo where user_id='" + userID + "' order by date";
    	System.out.println("查询用户以前图片的sql是：" + previousPhotoSQL);
    	//定义查找当前图片的sql语句
    	String presentPhotoSQL = "select * from present_photo where user_id='" + userID + "' order by date";
    	System.out.println("查询用户当前图片的sql是：" + presentPhotoSQL);
	   	//定义照片的列表
	   	ArrayList<PreviousPhoto> previousPhotos = selectDB.selectPreviousPhoto(previousPhotoSQL);
	    ArrayList<PresentPhoto> presentPhotos = selectDB.selectPresentPhoto(presentPhotoSQL);

    	//定义图片路径
    	String photoPath = "";
    	
    %>

    <!-- 导航栏 -->
    <ul class="navbar navbar-tabs navbar-inverse navbar-fixed-top">
        <!--根据内部边框计算元素大小-->
        <div class="container">
            <div class="navbar-header">
                <a href="./frontPage.jsp" class="navbar-brand">
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
                    <li><a href="./frontPage.jsp">首页</a></li>
                    <li><a href="./record.jsp">填写反馈</a></li>
                    <li><a href="./register.jsp">注册账号</a></li>
                </ul>
            </div>

        </div>
    </ul>

    <!-- 显示标题信息-->
    <div class="row">
	    <header class="page-header hidden-xs">
	        <h1><%=userName + "的照片集" %></h1>
	        <a type="button" class="btn btn-success" href="./frontPage.jsp">
	        	返回首页
	        	<span class="glyphicon glyphicon-search"></span>
	        </a>
	    </header>
    </div>


    <!--显示每个用户的信息-->
    <div class="row well">
        <!--头部显示用户的基本信息-->
        <blockquote>
            <h2><%=userInfo.getUserName() %></h2>
            <footer>电话：<%=userInfo.getUserPhone() %></footer>
        </blockquote>
        
        <!--显示这个用户的图片-->
        <div class="panel-group" id="<%=userID %>" role="tablist" aria-multiselectable="true">
                	
            <!-- 显示的是注册时候的照片 -->
            <div class="panel panel-primary">
                <div class="panel-heading" role="tab" id="<%=userID + "headingOne"%>">
                    <!--显示用户注册时候的图片-->
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="<%="#" + userID%>" href="<%="#" + userID + "collapseOne"%>" aria-expanded="true" aria-controls="<%=userID + "collapseOne"%>">
                            注册的照片
                        </a>
                    </h4>
                </div>
                <div id="<%=userID + "collapseOne"%>" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="<%=userID + "headingOne"%>">
                    <!--显示注册时候的照片-->
                    <div class="panel-body text-center">
                    	<!-- 对注册上传的照片进行操作 -->
                    	<%
                    		for(PreviousPhoto previousPhoto : previousPhotos) {
                    			//生成图片的访问路径
                    			photoPath = Source.domainPath + "/" + previousPhoto.getFilePath();
                    	%>
                            <img class="img-responsive img-thumbnail" width="250px" src="<%=photoPath%>">
                        <%
                    		}
                        %>
                    </div>
                </div>
            </div>
            
            
            <!-- 显示的是每个时间段的图片 -->
            <%
            	//定义初始时间
            	Date checkDate = null;
            	if(presentPhotos.size() != 0) {
            		checkDate = presentPhotos.get(0).getDate();
            	
            	//定义变量用于判断是否需要新创建面板
            	boolean isSwitch = true;
            	//定义记录面板数量的变量
            	int panelCount = 0;
            	
            	%>
            	<!-- 初始化面板 -->
            	<div class="panel panel-primary">
	                <div class="panel-heading" role="tab" id="<%=userID + "headingTwo" + panelCount%>">
	                    <h4 class="panel-title">
	                        <a class="collapsed" data-toggle="collapse" data-parent="<%="#" + userID%>" href="<%="#" + userID + "collapseTwo" + panelCount%>" aria-expanded="false" aria-controls="<%=userID + "collapseTwo" + panelCount%>">
	                           <%=DateUtil.dateToString(checkDate) %>
	                        </a>
	                    </h4>
	                </div>
	                <div id="<%=userID + "collapseTwo" + panelCount%>" class="panel-collapse collapse" role="tabpanel" aria-labelledby="<%=userID + "headingTwo" + panelCount%>">
	                    <div class="panel-body">
	                        <!--显示当前日期的照片-->
	                        <div class="panel-body text-center">
            	<% 
            	
            	//遍历当前的照片
            	for(PresentPhoto presentPhoto : presentPhotos) {
            		
            		//生成图片的访问路径
            		photoPath = Source.domainPath + "/" + presentPhoto.getFilePath();
            		
            		//判断是否需要创建面板
            		if(checkDate.equals(presentPhoto.getDate())) {
            			//如果相同，则不需要创建面板
            			isSwitch = false;
            			%>
            			<!-- 继续编写图片 -->
            				<img class="img-responsive img-thumbnail" width="250px" src="<%=photoPath%>">
            			<%
            		}
            		else {
            			//如果日期不相同，则需要创建面板
            			%>
            			<!-- 首先结束上一个面板 -->
            			        	</div>
			                    </div>
			                </div>
			            </div>
			            
			            <%
			            	//更新面板的数量
			            	panelCount ++;
			            %>
            			<!-- 然后创建新的面板 -->
            			<div class="panel panel-primary">
			                <div class="panel-heading" role="tab" id="<%=userID + "headingTwo" + panelCount%>">
			                    <h4 class="panel-title">
			                        <a class="collapsed" data-toggle="collapse" data-parent="<%="#" + userID%>" href="<%="#" + userID + "collapseTwo" + panelCount%>" aria-expanded="false" aria-controls="<%=userID + "collapseTwo" + panelCount%>">
			                          <%=DateUtil.dateToString(presentPhoto.getDate()) %>
			                        </a>
			                    </h4>
			                </div>
			                <div id="<%=userID + "collapseTwo" + panelCount%>" class="panel-collapse collapse" role="tabpanel" aria-labelledby="<%=userID + "headingTwo" + panelCount%>">
			                    <div class="panel-body">
			                        <!--显示当前日期的照片-->
			                        <div class="panel-body text-center">
			                        <!-- 继续编写图片 -->
            						<img class="img-responsive img-thumbnail" width="250px" src="<%=photoPath%>">
            			<%
            			
            			//并且将新的日期赋值到监测日期上
            			checkDate = presentPhoto.getDate();
            			isSwitch = true;
            		}//else
            		
            	}//循环
            	
            		
            	%>
            	<!-- 结束最后一个面板 -->
	                        </div>
	                    </div>
	                </div>
	            </div>
                <%
            		}//结束if
                %>
        </div>        
    </div>
    
    <%
    	//结束if
    	}
    %>

    <!--尾部-->
    <footer class="text-center">
        <hr>
        <P>深圳前海广利生物科技有限公司@2016</P>
    </footer>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="js/scripts/jquery.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap_js/bootstrap.min.js"></script>

<script>
	jQuery(document).ready(function() {
		
		var user_name = '<%=session.getAttribute("user_name")%>';
		var user_phone = '<%=session.getAttribute("user_password")%>';
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
