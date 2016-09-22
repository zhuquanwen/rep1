var addUrl = "/count/count/add";
var updateUrl = "/count/count/update";
var removeUrl = "/count/count/remove";								
									
$(function(){
		$('.form_datetime').datetimepicker({
			format : 'yyyy-mm-dd hh:ii',
			language:  'zh-CN',
			weekStart: 1,
			todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			forceParse: 0,
			showMeridian: 1
		}).on('changeDate', function(e){
			reInvalidTimeInput();
	    });
										
		//表单验证
		validate();
		
		bindModalEvent();
		bindFormSubmit();
//		bindForm2Submit();
		
		searchBootstrapTable();
		
		//绑定修改的模态窗口事件
		bindUpdateEvent();
		
		//绑定删除时间
		bindRemoveEvent();
		
		//绑定新增事件
		bindAddEvent();
});

function bindModalEvent(){
	$('#modalWindow').on('hide.bs.modal', function () {
		$("#form").data('bootstrapValidator').resetForm();
	})
}

function bindFormSubmit(){
	$("#form").submit(function(ev){ev.preventDefault();});
	$("#submitForm").on("click", function(){

		   var bootstrapValidator = $("#form").data('bootstrapValidator');
		   bootstrapValidator.validate();
		   if(bootstrapValidator.isValid()){
			   var id = $("#id").val();
			   var url;
			   if(id == null||"" == id){
				   url = addUrl;
			   }else{
				   url = updateUrl;
			   }
			   $.post(url, $("#form").serialize(), function(data) {
			        if(data && data.status == true){
			        	$("#modalWindow").modal('hide');
			        	notifyInfo("变更记录成功!");
			        	searchBootstrapTable();
			        }else{
			        	notifyInfo("变更记录出错!");
			        }
			    })
		   }else {return};

	});
}

function validate(){
	 $('#form')
     .bootstrapValidator({
         message: 'This value is not valid',
         feedbackIcons: {/*input状态样式图片*/
             valid: 'glyphicon glyphicon-ok',
             invalid: 'glyphicon glyphicon-remove',
             validating: 'glyphicon glyphicon-refresh'
         },
         fields: {/*验证：规则*/
             money: {//验证input项：验证规则
                 message: 'The username is not valid',
                
                 validators: {
                     notEmpty: {//非空验证：提示消息
                         message: '费用不能为空'
                     },
                     stringLength: {
                         min: 1,
                         max: 40,
                         message: '费用长度必须在1到40之间'
                     }
                     ,
                     regexp: {
	                     regexp: /^(\d*\.)?\d+$/,
	                     message: '费用必须是有效整数或小数'
                     }
                    
                 }
             },
             dateTimeInput3: {//验证input项：验证规则
                 message: 'The username is not valid',
                
                 validators: {
                     notEmpty: {//非空验证：提示消息
                         message: '时间不能为空'
                     }
                    
                 }
             }
             
         }
     });
}

function bindAddEvent(){
	
	$("#addButton").on("click",function(){
		 
		/*$("#id").val(null);
		$("#money").val(null);*/
		document.getElementById("form").reset(); 
		$("#dateTimeInput3").val(formateDateTime());
		$("#dtp_input3").val(formateDateTime());
		$("#modalWindow").modal("show");
		$("#myModalLabel").text("新增一条记录");
		
		/*$("#form").data('bootstrapValidator').resetForm(); */
	     
	});
	
}

function bindUpdateEvent(){
	
	$("#updateButton").on("click",function(){
		var rows = $('#viewTable').bootstrapTable('getAllSelections');
		if(rows.length == 0){
			notifyWarning("必须选择一条记录!");
		}else if(rows.length >1){
			notifyWarning("只能选择一条记录!");
		}else{
			document.getElementById("form").reset(); 
			var date = formateDateTime(new Date(rows[0].datetime));
			$("#dateTimeInput3").val(date);
			$("#dtp_input3").val(date);
			$("#id").val(rows[0].id);
			
			$("#typeName").val(rows[0].countTypeId);
			$("#money").val(rows[0].money);
			$("#modalWindow").modal("show");
			$("#myModalLabel").text("修改一条记录");
		}
		
	     
	});
	
}

function bindRemoveEvent(){
	$("#removeButton").on("click",function(){
		var rows = $('#viewTable').bootstrapTable('getAllSelections');
		if(rows.length == 0){
			notifyWarning("必须选择一条记录!");
		}else{
			for (var i = 0; i < rows.length; i++) {
				delete rows[i][''];
			}
			
			$.ajax({
				  type: 'post',
				  url: removeUrl,
				  data: {datas: JSON.stringify(rows)},
				  dataType: "json",
				  success: function(data) {
				        if(data && data.status == true){
				        	
				        	notifyInfo("删除记录成功!");
				        	searchBootstrapTable();
				        }else{
				        	notifyInfo("删除记录出错!");
				        	searchBootstrapTable();
				        }
				    }
				 
				});
		}
		
	     
	});
}

function searchData(){
	searchBootstrapTable();
}

function queryParams(params) {  //配置参数
	var startTime = $("#dtp_input1").val();
	var endTime = 	$("#dtp_input2").val();
    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的  
      pageSize: params.pageSize,   //页面大小  
      pageNumber: params.pageNumber,  //页码  
      pageList : params.pageList,
      sortOrder: params.order,//排位命令（desc，asc）  
      
      startTime : startTime,
      endTime : endTime	
    };  
    return temp;  
  }  

function refreshBootstrapTable(){
	$('#viewTable').bootstrapTable('refresh', {url: '/count/count/getCounts'});
}

function searchBootstrapTable(){
	
	$('#viewTable').bootstrapTable("destroy");
	$('#viewTable').bootstrapTable({
		method: 'get',
		url : '/count/count/getCounts',
		cache : false,
		striped : true,
		pagination : true,
		pageSize : 5,
		pageNumber : 1,
		pageList : [ 5, 10, 20 ],
		clickToSelect : true,
		sidePagination : 'server',// 设置为服务器端分页
		toolbar : '#toolbar',
		queryParamsType : '',
		
		queryParams: queryParams,
		
		columns : [ 
			{ field : "", checkbox : true },       
			{ field : "countTypeName", title : "类别", align : "center", valign : "middle" },
			{ field : 'money',title : '费用', align : 'center' ,valign : "middle" },
			{ field : "inoutTypeName", title : "资金流向", align : "center", valign : "middle",
				formatter : function(value, row, index){
					var returnStr = value;
					if(1 == value){
						returnStr = "收入";
					}else if(-1 == value){
						returnStr = "支出";
					}
					return returnStr;
				}
			},
			{ field : "username", title : "记录人", align : "center", valign : "middle" },
			{ field : "datetime", title : "日期", align : "center", valign : "middle",
				formatter : function(value, row, index){
					return formateDateTime(value);
				}
			}
		]
	});
}

function removeDateTime2(){
	$("#dateTimeInput2").val("");
	$("#dtp_input2").val("");
}

function removeDateTime1(){
	$("#dateTimeInput1").val("");
	$("#dtp_input1").val("");
}

function removeDateTime3(){
	$("#dateTimeInput3").val("");
	$("#dtp_input3").val("");
	reInvalidTimeInput();
}

function reInvalidTimeInput(){
	$("#form")
    .data('bootstrapValidator')
    .updateStatus('dateTimeInput3', 'NOT_VALIDATED')
    .validateField('dateTimeInput3');
}