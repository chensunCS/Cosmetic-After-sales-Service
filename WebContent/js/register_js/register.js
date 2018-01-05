//判断填写输入框是否为空
function isEmpty(register_info) {
	
	for(var i = 0 ; i < register_info.length ; i++) {
		if(register_info[i] == "" || register_info[i] == null) {
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
function isExistInvalidChar(register_info) {
	for(var i = 0 ; i < register_info.length-1 ; i++) {
		if(register_info[i].indexOf("/") > 0 || register_info[i].indexOf("\\") > 0
				|| register_info[i].indexOf("$") > 0 || register_info[i].indexOf("*") > 0) {
			swal("输入部分包含非法字符！", "请确认您的填写!", "error");
			//存在非法字符，不合法
			return false;
		}
	}
}

//判断注册是否合法
function register() {
	
	//获取表单信息
	var user_name = document.getElementById("inputName").value;
	var user_phone = document.getElementById("inputPhone").value;
	var user_gender = document.getElementById("inputGender").value;
	var user_age = document.getElementById("inputAge").value;
	var user_address = document.getElementById("inputAddress").value;
	var user_skin_attr = document.getElementById("inputAttr").value;
	var user_skin_problem = document.getElementById("inputProblem").value;
	var user_present_product = document.getElementById("inputProduct").value;
	var upload_photo_date = document.getElementById("upload_photo_date").value;
	var previous_photo = document.getElementById("file-1").value;

	var register_info = new Array(user_name, user_phone, user_gender, user_age, user_address, 
								  user_skin_attr, user_skin_problem, user_present_product, upload_photo_date, previous_photo);
	/*
	 * 判断是否注册是否合法
	 */
	//如果为空，显示相应的提示
	if(isEmpty(register_info)){
		return false;
	}
	//如果电话不合法
	else if(!isPhoneValid(user_phone)) {
		return false;
	}
	//如果年龄不合法
	else if(!isAgeValid(user_age)) {
		return false;
	}
	//如果包含非法字符
	else if(isExistInvalidChar(register_info)) {
		return false;
	}
	else {
		//获取注册的表单
		var registerForm = document.getElementById("register_form");
		var registerData = new FormData(registerForm);
		
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
			function(){
				//进行用户注册信息的上传
				$.ajax({
					cache: false,
					type: "POST",
					url:"servlet/RegisterServlet",
					data:registerData,// 你的formid
					processData:false,//data的值是对象
					contentType:false,//已经声明类型为multipart
					dataType:"text",	//返回纯字符串类型
					async: true,
					error: function(request) {
						swal({   title: "注册失败",   type: "error",   confirmButtonText: "OK" });
					},
					success: function(data) {
						if(data == "occupied") {
							swal({   title: "用户名已经注册",   type: "error",   confirmButtonText: "OK" });
						}
						else if(data == "single exceed") {
							swal({   title: "单个文件过大",   type: "error",   confirmButtonText: "OK" });
						}
						else if(data == "all exceed") {
							swal({   title: "总文件过大",   type: "error",   confirmButtonText: "OK" });
						}
						else if(data == "fail") {
							swal({   title: "注册失败",   type: "error",   confirmButtonText: "OK" });
						}
						else {
							swal({
								  title: "注册成功",
								  text: "请在首页上方登录",
								  type: "success",
								  confirmButtonText: "好的",
								  closeOnConfirm: false,
								  closeOnCancel: false
								},
								function(isConfirm){
									//跳转到首页
								  window.location.href="./frontPage.jsp";
								});
						}
					}//ajax请求的success函数
				});//ajax请求
		});//swal，首先弹出的swal框
	}//上一个else的括号
}

//获取选择框中的元素
function appendAttr(obj) {
	//获取产品的名字
	var skin_attr = $(obj).html();
	//获取输入框的内容
	var input_value = $('#inputAttr').attr('value');
	//判断输入框的内容是否为空
	if(input_value == null) {
		input_value = "";
	}
	//定义追加的内容
	var appendTxt = input_value + skin_attr + ";";
	//将产品的名字追加到input中
	$('#inputAttr').attr('value', appendTxt);
	//隐藏该选项
	$(obj).hide();
}

//清除产品的元素
function clearAttr() {
	//清除产品输入框
	$('#inputAttr').attr('value','');
	//显示选择框的内容
	for(var i = 0 ; i < 5 ; i++) {
		$('#属性' + (i+1)).show();
	}
}

//获取手机短信验证
function getSMSID() {
	//定义applicationID
	var applicationID = "5bce8728026f89e48f5d8fe974551750";
	//定义REST API KEY
	var restAPIKey = "dda0cbb515d1eaccb213e9363ff30264";
	//初始化Bmob
	Bmob.initialize(applicationID, restAPIKey);
	//获取用户的手机号码
	var phoneNumber = $('#inputPhone').val();
	//定义SMS的对象
	var sms = {
		"mobilePhoneNumber": phoneNumber			//用户的手机号码
	};
	
	//向用户发送短信验证码
	Bmob.Sms.requestSmsCode(sms).then(function(obj) {
		
		//调取填写验证码的函数
		inputSMSCode();
		
		}, function(err){
			//获取错误代码
			var errorCode = eval(err).code;
			
			//根据不同的错误代号进行不同的响应
			responseErrorCode(errorCode);
		}
	);
}

function inputSMSCode() {
	
	//将发送按钮隐藏
	document.getElementById("sendSMSID_button").style.display = 'none';
	//将提交验证码按钮显示出来
	document.getElementById("inputSMSID_button").style.display = 'block';
	
	swal({
		  title: "验证码已发送",
		  text: "填写您的验证码",
		  type: "input",
		  showCancelButton: true,
		  closeOnConfirm: false,
		  animation: "slide-from-top",
		  inputPlaceholder: "输入您的验证码"
		},
		function(inputValue){
		  if (inputValue === false) return false;
		  
		  //如果填写为空的情况
		  if (inputValue === "") {
		    swal.showInputError("填写为空");
		    return false
		  }
		
		  //判断用户输入的验证码是否正确
		  verifySMSID(inputValue)

		});
}

//验证手机短信验证是否正确
function verifySMSID(smsCode) {
	//定义applicationID
	var applicationID = "5bce8728026f89e48f5d8fe974551750";
	//定义REST API KEY
	var restAPIKey = "dda0cbb515d1eaccb213e9363ff30264";
	//初始化Bmob
	Bmob.initialize(applicationID, restAPIKey);
	
	//获取用户的手机号码
	var phoneNumber = $('#inputPhone').val();
	
	//判断是否验证成功
	var isSuccess = true;
	
	//开始验证短信验证码
	Bmob.Sms.verifySmsCode(phoneNumber, smsCode).then(function(obj) {
		
		  //设置电话的输入框为readonly
		  document.getElementById("inputPhone").setAttribute("readonly", "readonly");
		  document.getElementById("submit_graphic").removeAttribute("disabled");
		
		  //如果正确
		  swal("验证成功", "请继续填写您的信息", "success");
		  
		}, function(err){
			
		  //如果错误
		  swal("验证错误", "请正确填写验证码", "error");
		  
		}
	);
}

//根据不同的代号进行不同的响应
function responseErrorCode(errorCode) {
	switch(errorCode) {
	//填写手机号错误的情况
	case 301:
		swal("填写手机号出错", "请确认您的手机号码", "error");
		break;
	//已经发送验证码的情况
	case 10010:
		swal("已经向您发送验证码", "请查看手机上的 验证码", "error");
		
		//将发送按钮隐藏
		document.getElementById("sendSMSID_button").style.display = 'none';
		//将提交验证码按钮显示出来
		document.getElementById("inputSMSID_button").style.display = 'block';
		break;
	default:
		
	}
}