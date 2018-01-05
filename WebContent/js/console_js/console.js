//生成Excel文件，并提示下载
function exportExcel(tableName, sql) {
	//显示下载的提示框
	swal({
		  title: "确定导出该Excel文件?",
		  text: "导出的文件将存放在服务器上",
		  type: "info",
		  showCancelButton: true,
		  closeOnConfirm: false,
		  confirmButtonText: "确定导出",
		  cancelButtonText: "取消导出",
		  showLoaderOnConfirm: true,
		},
		//开始导出Excel文件的函数
		function(){
			//发送请求到Servlet中
			$.ajax({
				cache: false,
				type: "POST",
				url:"servlet/ExportExcelServlet",
				data: {table_name: tableName, sql_query: sql},// 数据
				processData:true,	//发送的数据是对象
				dataType:"text",	//返回纯字符串类型
				async: true,
				error: function(request) {
					swal({   title: "导出Excel文件失败",   type: "error",   confirmButtonText: "OK" });
				},
				success: function(data) {
					//获取返回的结果
					var result = data.split("&")[0];
					//获取文件名
					var fileName = data.split("&")[1];
					//获取文件路径
					var filePath = data.split("&")[2];
					
					console.log(fileName);
					console.log(filePath);
					
					if(result == "success") {
						swal({
							  title: "导出Excel文件成功",
							  text: "文件名是" + fileName,
							  type: "success",
							  showCancelButton: true,
							  confirmButtonText: "下载此文件",
							  cancelButtonText: "暂时不下载",
							  closeOnConfirm: false,
							  closeOnCancel: true
							},
							function(){
								//下载文件
								window.open(filePath, '_blank');
							});
					}
					else {
						swal({
							  title: "导出Excel文件失败",
							  text: "",
							  type: "error",
							  confirmButtonText: "请刷新页面重新下载",
							  closeOnConfirm: false,
							  closeOnCancel: false
							});
					}
				}
			});
	});

}