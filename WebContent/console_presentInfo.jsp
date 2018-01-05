<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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
    <title>用户反馈信息</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap_css/bootstrap.min.css" rel="stylesheet">

    <!-- 我的css文件-->
    <link href="css/console_css/console_css.css" rel="stylesheet">
    
    <!-- sweet alert的css文件 -->
	<link rel="stylesheet" type="text/css" href="css/sweetalert_css/sweetalert.css">
</head>
<body id="console_presentInfo_body" class="container">

	    <!-- 开始初始化用户的填写信息 -->
    <%
    	//定义查看信息的数量
    	int dataNum = 0;
    	//获取展示的数据数量
    	if(request.getParameter("dataNum") == null) {
    		dataNum = 10;
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
    	//定义查询语句（查询前十条数据）
    	String queryPresentInfo = "select * from present_info limit " + queryBeginIndex + ", " + dataNum;
    	//获取返回前十条用户信息
    	ArrayList<PresentInfo> presentInfos = selectDB.selectPresentInfo(queryPresentInfo);
    	//获取本次查询的提交数量
    	int currentInfoNum = presentInfos.size();
    	//定义全部提交的数量
    	int allInfoNum = 0;
    	//定义查询用户数量的SQL语句
    	String countPresentInfo = "select count(present_info_id) as count from present_info";
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
                <a href="<%= "./console.jsp" %>" class="navbar-brand">
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

    <!--头部-->
    <header class="page-header hidden-xs">
        <h1>我的控制台<small>用户反馈表格(present_info) #<%=pageIndex %></small></h1>
    </header>

    <!--显示该表的信息-->
    <div class="row">
        <!--显示下拉菜单按钮-->
        <div class="col-sm-12">
            <div style="" class="btn-group btn-group-justified">
                <!--选择显示多少条信息-->
                <div class="dropdown">
                    <button class="btn btn-info dropdown-toggle" type="button" id="byNumber" data-toggle="dropdown">
                        查看多少条信息
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                        <li role="presentation">
                        	<a role="menuitem" tabindex="-1" href="<%="./console_presentInfo.jsp?pageIndex=1&dataNum=" + 10%>" >每页10条</a>
                        </li>
                        <li role="presentation">
                        	<a role="menuitem" tabindex="-1" href="<%="./console_presentInfo.jsp?pageIndex=1&dataNum=" + 20%>" >每页20条</a>
                        </li>
                        <li role="presentation">
                        	<a role="menuitem" tabindex="-1" href="<%="./console_presentInfo.jsp?pageIndex=1&dataNum=" + 50%>">每页50条</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <!-- 显示注册用户信息的表格 -->
    <div class="row">
        <div class="col-sm-12">
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <td>用户名</td>
                        <td>使用产品日期</td>
                        <td>使用感受</td>
                        <td>出现的问题</td>
                        <td>填写日期</td>
                    </tr>
                    </thead>
                    <tbody>
                    
	                    <!-- 遍历列表，将信息填充到页面中 -->
	                    <%
	                    	for(PresentInfo presentInfo : presentInfos) {
	                    %>
	                    	<tr>
	                            <td><%=presentInfo.getUserID().split("_")[0] %></td>
	                            <td><%=DateUtil.dateToString(presentInfo.getUseDate()) %></td>
	                            <td><%=presentInfo.getUseFeeling() %></td>
	                            <td><%=presentInfo.getUseProblem() %></td>
	                            <td><%=presentInfo.getPresentInfoID().split("_")[2] %></td>
	                        </tr>
	                    <%
	                    	}
	                    %>
                    
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!--显示分页的信息-->
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
	                    <li><a href="<%="./console_presentInfo.jsp?pageIndex=" + (pageIndex-1) +"&dataNum=" + dataNum %>">上一页</a></li>
                    <%
                		}
                    %>
                    
                    <!-- 根据计算的索引来排布分页 -->
                    <%
                    	for(int i = pageBeginIndex ; i <= pageEndIndex ; i++) {
                    %>
	                    <li><a href="<%="./console_presentInfo.jsp?pageIndex=" + i +"&dataNum=" + dataNum %>"><%=i %></a></li>
                    <%
                    	}
                    %>
                    
                    <!-- 判断本次查询的页面是否为尾页 -->
                    <%
                    	//如果不是尾页
                    	if(currentInfoNum == dataNum && pageIndex * dataNum < allInfoNum) {
                    %>
                    	<!-- 显示下一页的标识 -->
                    	<li><a href="<%="./console_presentInfo.jsp?pageIndex=" + (pageIndex+1) +"&dataNum=" + dataNum %>">下一页</a></li>
                    <%
                    	}
                    %>
                </ul>
            </nav>

        </div>
    </div>
    
    <div class="row">
    	<!--显示下载文档的按钮-->
        <div class="col-sm-12 text-center">
            <button type="button" class="btn btn-success" onclick="exportExcel('<%=Source.presentInfoTable %>', '<%=Source.queryPresentInfo%>')">
            	下载成Excel文档查看
            	<span class="glyphicon glyphicon-save"></span>
            </button>
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

<!-- sweet alert的js文件 -->
<script src="js/sweetalert_js/sweetalert.min.js"></script>

<!-- 我的js文件 -->
<script src="js/console_js/console.js"></script>

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
