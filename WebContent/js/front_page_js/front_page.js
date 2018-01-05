//去往注册界面的函数
function linkRegister() {
	window.location.href="./register.jsp";
}

//判断填写输入框是否为空
function isEmpty(record_info) {
	
	for(var i = 0 ; i < record_info.length ; i++) {
		if(record_info[i] == "" || record_info[i] == null) {
			swal("必填项目为空！", "请输入您的用户名和电话", "warning");
			//存在空字符串
			return true;
		}
	}
	
	//不存在空字符串
	return false;
}

//判断登录是否为空
function login() {
	//获取表单信息
	var user_name = document.getElementById("user_name").value;
	var user_phone = document.getElementById("user_phone").value;
	
	var login_info = new Array(user_name, user_phone);
	
	//如果为空
	if(isEmpty(login_info)) {
		//阻止提交表单
		return false;
	}
	else {
		//进行用户
		$.ajax({
			cache: false,
			type: "POST",
			url:"servlet/LoginServlet",
			data:$('#login_form').serialize(),// 你的formid
			dataType:"text",	//返回纯字符串类型
			async: true,
			error: function(request) {
				alert("提交失败！");
			},
			success: function(data) {
				//出现已经填写过相同的使用日期
				if(data == "error") {
					swal("用户名、电话输入错误", "请确认您的输入", "error");
					return true;
				}
				//提交成功
				else {
					swal("登录成功", "请填写您的产品反馈信息", "success");
					//修改页面
					$('#login_div').hide();
					$('#user_info').text("欢迎回来，" + data);
					$('#tips').text("点击下方的按钮填写反馈");
					//显示注销按钮
					$("#logined_div").show();
					//显示用户图片按钮
					document.getElementById("user_photo_button").style.visibility= 'visible';
				}
			}
		});
	}
}

//注销登录的用户
function logout() {
	$.ajax({
		cache: false,
		type: "POST",
		url:"servlet/LogoutServlet",
		data:"",// 你的formid
		dataType:"text",	//返回纯字符串类型
		async: false,
		error: function(request) {
			alert("提交失败！");
		},
		success: function(data) {
			//出现已经填写过相同的使用日期
			if(data == "success") {
				swal("注销成功", "请登录您的个人信息", "warning");
				
				//修改页面
				$('#login_div').show();
				$('#user_info').text("登录您的账号");
				$('#tips').text("请在右侧填写您的姓名和电话");
				$('#logined_div').hide();
				//隐藏查看图片的按钮
				document.getElementById("user_photo_button").style.visibility= 'hidden';
				return true;
			}
		}
	});
}

//撤回今天填写的内容
function retreat() {
	$.ajax({
		cache: false,
		type: "POST",
		url:"servlet/RetreatServlet",
		data:"",
		dataType:"text",	//返回纯字符串类型
		async: true,
		error: function(request) {
			alert("撤销失败！");
		},
		success: function(data) {
			//出现已经填写过相同的使用日期
			if(data == "success") {
				swal("撤回成功", "请填写您今天的表单", "success");
				return true;
			}
			else if(data == "not submit") {
				swal("撤回失败", "您今天的表单还没有填写", "error");
			}
		}
	});
}

//管理员登录
function adminLogin() {
	//进行用户
	$.ajax({
		cache: false,
		type: "POST",
		url:"servlet/AdminLoginServlet",
		data:$('#admin_login_form').serialize(),// 你的formid
		dataType:"text",	//返回纯字符串类型
		async: true,
		error: function(request) {
			alert("提交失败！");
		},
		success: function(data) {
			//出现已经填写过相同的使用日期
			if(data == "error") {
				swal("管理员账号密码错误", "请确认您的输入", "error");
				return true;
			}
			//提交成功
			else {
				swal({
					  title: "登录成功",
					  text: "点击下面按钮跳转到管理员界面",
					  type: "success",
					  confirmButtonText: "去往控制台",
					  closeOnConfirm: false,
					  closeOnCancel: false
					},
					function(isConfirm){
						//跳转到管理员界面
					  window.location.href = "./console.jsp";
					});
				
			}
		}
	});
}