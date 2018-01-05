//判断填写输入框是否为空
function isEmpty(record_info) {
	
	for(var i = 0 ; i < record_info.length ; i++) {
		if(record_info[i] == "" || record_info[i] == null) {
			swal("必填项目为空！", "请确认您的填写!", "error");
			//存在空字符串
			return true;
		}
	}
	
	//不存在空字符串
	return false;
}

//判断电话是否为数字、长度是否够
function isPhoneValid(user_phone) {
	//判断输入的电话是否为数字
	if(isNaN(user_phone)) {
		//不是数字
		swal("输入电话号码不是数字！", "请正确填写您的电话!", "error");
		return false;
	}
	//判断长度是否为大于等于8或者小于11
	if(user_phone.length < 8 || user_phone.length > 11) {
		swal("电话号码长度非法！", "请正确填写您的电话!", "error");
		//长度不合法
		return false;
	}
	else {
		//如果合法，则返回true
		return true;
	}
}

//判断年龄是否合法
function isAgeValid(user_age) {
	//判断输入的是否为数字
	if(isNaN(user_age)) {
		swal("输入的年龄不是数字！", "请正确填写您的年龄!", "error");
		//不是数字
		return false;
	}
	//判断是否在合法区域内
	if(parseInt(user_age) > 0 && parseInt(user_age) < 100) {
		//在合法区域内
		return true;
	}
	else {
		swal("输入的年龄非法！", "请正确填写您的年龄!", "error");
		//不在合法区域内
		return false;
	}
}

//判断是否包含非法字符
function isExistInvalidChar(record_info) {
	for(var i = 0 ; i < record_info.length-1 ; i++) {
		if(record_info[i].indexOf("/") > 0 || record_info[i].indexOf("\\") > 0
				|| record_info[i].indexOf("$") > 0 || record_info[i].indexOf("*") > 0) {
			swal("输入部分包含非法字符！", "请确认您的填写!", "error");
			//存在非法字符，不合法
			return true;
		}
	}
	
	//不存在非法字符，合法
	return false;
}

//判断填表是否合法
function record() {
	//获取表单信息
	var use_date = document.getElementById("use_date").value;
	var use_product = document.getElementById("inputProduct").value;
	var use_feel = document.getElementById("inputFeel").value;
	var use_problem = document.getElementById("inputProblem").value;
	var upload_date = document.getElementById("upload_date").value;
	var present_photo = document.getElementById("file-1").value;

	var record_info = new Array(use_date, use_product, use_feel, use_problem, upload_date, present_photo);
	
	
	//如果为空，显示相应的提示
	if(isEmpty(record_info)) {
		//阻止表单的提交
		return false;
	}
	if(isExistInvalidChar(record_info)) {
		//阻止表单的提交
		return false;
	}
	else {
		//获取提交表单的信息
		var recordForm = document.getElementById("record_form");
		var recordForm = new FormData(recordForm);  
		
		//弹出响应框
		swal({
			  title: "确定上传?",
			  text: "",
			  type: "info",
			  showCancelButton: true,
			  closeOnConfirm: false,
			  confirmButtonText: "确定上传",
			  cancelButtonText: "取消上传",
			  showLoaderOnConfirm: true,
			},
			//发送请求的函数
			function(){
			  //进行用户反馈信息的上传
				$.ajax({
					cache: false,
					type: "POST",
					url:"servlet/RecordServlet",
					data:recordForm,// 你的formid
					processData:false,//data的值是对象
					contentType:false,//已经声明类型为multipart
					dataType:"text",	//返回纯字符串类型
					async: true,
					error: function(request) {
						swal({   title: "提交失败",   type: "error",   confirmButtonText: "OK" });
					},
					success: function(data) {
						//出现已经填写过相同的使用日期
						if(data == "same upload date") {
							$("#use_date").val("");
							swal("该上传日期已经填写", "请选择其他的日期作为使用日期", "warning");
						}
						//上传日期中存在相同的文件
						else if(data.split("@")[0] == "same file") {
							swal("图片" + data.split("@")[1] + "已经存在", "请重新上传该照片", "warning");
						}
						//单个文件超出大小限制
						else if(data == "single exceed") {
							swal({   title: "单个文件过大",   type: "error",   confirmButtonText: "OK" });
						}
						//总文件超出大小限制
						else if(data == "all exceed") {
							swal({   title: "总文件过大",   type: "error",   confirmButtonText: "OK" });
						}
						//上上传失败
						else if(data == "fail") {
							swal({   title: "提交失败",   type: "error",   confirmButtonText: "OK" });
						}
						//提交成功
						else {
							swal({
								  title: "提交成功",
								  text: "感谢您的填写",
								  type: "success",
								  confirmButtonText: "好的",
								  closeOnConfirm: false,
								  closeOnCancel: false
								},
								function(isConfirm){
									//跳转到首页
								  window.location.href="./frontPage.jsp";
							});//提交成功swal
						}
					}
				});//ajax请求
		});//swal相应框
	}	
}
