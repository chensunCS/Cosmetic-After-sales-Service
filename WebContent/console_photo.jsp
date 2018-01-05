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
    	request.setCharacterEncoding("utf-8");
    	response.setContentType("text/html;charset=utf-8");
    	//定义查看信息的数量
    	int dataNum = 0;
    	//获取展示的数据数量
    	if(request.getParameter("dataNum") == null) {
    		dataNum = 3;
    	}
    	else {
    		dataNum = Integer.parseInt(request.getParameter("dataNum"));
    	}
    	//定义页面的索引
    	int pageIndex = 0;
    	//获取页数
    	if(request.getParameter("pageIndex") == null) {
    		pageIndex = 1;
    	}
    	else {
    		pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
    	}
   		//计算查询的开始
   		int queryBeginIndex = dataNum* (pageIndex-1);
    	//定义查询对象
    	SelectDB selectDB = new SelectDB();
    	//定义查询语句（查询前3条数据）
    	String queryUserInfo = "select * from user_info limit " + queryBeginIndex + ", " + dataNum;
    	//获取返回前十条用户信息
    	ArrayList<UserInfo> userInfos = selectDB.selectUserInfo(queryUserInfo);
    	//获取本次查询的提交数量
    	int currentInfoNum = userInfos.size();
    	
    	//定义全部提交的数量
    	int allInfoNum = 0;
    	//定义查询用户数量的SQL语句
    	String countPresentInfo = "select count(user_id) as count from user_info";
    	//获取注册用户的数量
    	allInfoNum = selectDB.countTable(countPresentInfo);
    	
    	//获取本次分页的数量
    	//定义页面的开始，结束位置
    	int pageBeginIndex = (pageIndex - (pageIndex % 5)) + 1;
    	int pageEndIndex = 0;
    	//判断是否已经到了末尾
    	if(allInfoNum <= dataNum) {
    		pageEndIndex = pageBeginIndex;
    	}
    	else if(dataNum * (pageBeginIndex % 5) + 5 * dataNum <= allInfoNum) {
    		//如果没有到末尾，则自动增加5页
    		pageEndIndex = pageBeginIndex + 5;
    	}
    	else {
    		//判断是否刚好除整
    		if(allInfoNum % dataNum == 0) {
        		//如果到了末尾
        		pageEndIndex = pageBeginIndex + Math.abs(allInfoNum - pageBeginIndex * dataNum) / dataNum;
    		}
    		//如果总数没有除整
    		else {
        		//如果到了末尾
        		pageEndIndex = pageBeginIndex + Math.abs(allInfoNum - pageBeginIndex * dataNum) / dataNum + 1;
    		}
    	}
    %>

    <!-- 导航栏 -->
    <ul class="navbar navbar-tabs navbar-inverse navbar-fixed-top">
        <!--根据内部边框计算元素大小-->
        <div class="container">
            <div class="navbar-header">
                <a href="./console.jsp" class="navbar-brand">
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
                    <li><a href="./console_userInfo.jsp">用户信息</a></li>
                    <li><a href="./console_presentInfo.jsp">填表信息</a></li>
                    <li><a href="./console_photo.jsp">用户照片</a></li>
                    <li><a href="./frontPage.jsp">退出</a></li>
                </ul>
            </div>

        </div>
    </ul>

    <!-- 显示标题信息-->
    <div class="row">
	    <header class="page-header hidden-xs">
	        <h1>我的控制台<small>查看用户的照片 #<%=pageIndex %></small></h1>
	        <button type="button" class="btn btn-success">
	        	查看用户更多照片
	        	<span class="glyphicon glyphicon-search"></span>
	        </button>
	    </header>
    </div>


    <!-- 操作每一个用户的信息 -->
    <%
	   	//定义查找的sql语句
	   	String tempSQL = "";
    	//定义用户ID
    	String userID = "";
    	//定义图片路径
    	String photoPath = "";
	   	//定义照片的列表
	   	ArrayList<PreviousPhoto> previousPhotos = null;
	    ArrayList<PresentPhoto> presentPhotos = null;
	    //获取用户的照片
	    for(UserInfo userInfo : userInfos) {
	    	//获取用户ID
	    	userID = userInfo.getUserID();
	    	System.out.println("用户的id是：" + userID);
	      	//定义查询语句
	      	tempSQL = "select * from previous_photo where user_id = '" + userID + "'";
	      	//根据用户ID查询照片
	      	previousPhotos = selectDB.selectPreviousPhoto(tempSQL);
	      	//添加到用户对象中
	      	userInfo.setPreviousPhotos(previousPhotos);
	      	//定义查询语句
	      	tempSQL = "select * from present_photo where user_id = '" + userID + "' limit 0, 5";
	      	//根据用户ID查询填写的照片
	      	presentPhotos = selectDB.selectPresentPhoto(tempSQL);
	      	//添加到用户对象中
	      	userInfo.setPresentPhotos(presentPhotos);
    %>

    <!--显示每个用户的信息-->
    <div class="row well">
        <!--头部显示用户的基本信息-->
        <blockquote>
            <h2><%=userInfo.getUserName() %></h2>
            <footer>电话：<%=userInfo.getUserPhone() %></footer>
        </blockquote>
        
        <!--显示这个用户的图片-->
        <div class="panel-group" id="<%=userID %>" role="tablist" aria-multiselectable="true">        	
            <!--每一个用户的照片信息-->
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
            <div class="panel panel-primary">
                <div class="panel-heading" role="tab" id="<%=userID + "headingTwo"%>">
                    <h4 class="panel-title">
                        <a class="collapsed" data-toggle="collapse" data-parent="<%="#" + userID%>" href="<%="#" + userID + "collapseTwo"%>" aria-expanded="false" aria-controls="<%=userID + "collapseTwo"%>">
                            反馈的照片（前5张照片）
                        </a>
                    </h4>
                </div>
                <div id="<%=userID + "collapseTwo"%>" class="panel-collapse collapse" role="tabpanel" aria-labelledby="<%=userID + "headingTwo"%>">
                    <div class="panel-body">
                        <!--显示当前日期的照片-->
                        <div class="panel-body text-center">
                        <%
                        	for(PresentPhoto presentPhoto : presentPhotos) {
                        		//生成图片的访问路径
                        		photoPath = Source.domainPath + "/" + presentPhoto.getFilePath();
                        %>
                            <img class="img-responsive img-thumbnail" width="250px" src="<%=photoPath%>">
                        <%
                        	}
                        %>
                        </div>
                    </div>
                </div>
            </div>            
        </div>        
    </div>
    
    <%
       	}
    %>

    <!--显示分页-->
    <div class="row">
        <div class="col-sm-12 text-center">
            <nav>
                <ul class="pager">
                	<!-- 判断本次查询的页面是否为首页 -->
                	<%
                		//如果不是首页
                		if(pageIndex != 1) {
                	%>
	                	<!-- 显示上一页的标识 -->
	                    <li><a href="<%="./console_photo.jsp?pageIndex=" + (pageIndex-1) +"&dataNum=" + dataNum %>">上一页</a></li>
                    <%
                		}
                    %>
                    
                    <!-- 根据计算的索引来排布分页 -->
                    <%
                    	for(int i = pageBeginIndex ; i <= pageEndIndex ; i++) {
                    %>
	                    <li><a href="<%="./console_photo.jsp?pageIndex=" + i +"&dataNum=" + dataNum %>"><%=i %></a></li>
                    <%
                    	}
                    %>
                    
                    <!-- 判断本次查询的页面是否为尾页 -->
                    <%
                    	//如果存在下一页
                    	if(currentInfoNum == dataNum && pageIndex * dataNum < allInfoNum) {
                    %>
                    	<!-- 显示下一页的标识 -->
                    	<li><a href="<%="./console_photo.jsp?pageIndex=" + (pageIndex+1) +"&dataNum=" + dataNum %>">下一页</a></li>
                    <%
                    	}
                    %>
                </ul>
            </nav>

        </div>
    </div>

    <!--尾部-->
    <footer class="text-center">
        <hr>
        <P>深圳前海广利生物科技有限公司@2016</P>
    </footer>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="js/scripts/jquery.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap_js/bootstrap.min.js"></script>

<!-- 判断是否需要登录 -->
<script>
	jQuery(document).ready(function() {
		
		var admin_id = '<%=session.getAttribute("admin_id")%>';
		var admin_password = '<%=session.getAttribute("admin_password")%>';
		//判断是否存在
		if(admin_id == "" || admin_id == null || admin_id == "null") {
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
