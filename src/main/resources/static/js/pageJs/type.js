 								
var typeUrl = "/count/countType/getCountTypes";		
var typeAddUrl = "/count/countType/addCountType";
var typeUpdateUrl = "/count/countType/updateCountType";
var typeRemoveUrl = "/count/countType/removeCountType";
$(function(){
	//表单验证
	validate();
	
	bindFormSubmit();
	bindForm2Submit();
	
	searchBootstrapTable();
	
	//绑定修改的模态窗口事件
	bindUpdateEvent();
	
	//绑定删除时间
	bindRemoveEvent();
});

function bindRemoveEvent(){
	$("#removeInfo").on("click",function(){
		var rows = $('#viewTable').bootstrapTable('getAllSelections');
		if(rows.length == 0){
			notifyWarning("必须选择一条记录!");
		}else{
			for (var i = 0; i < rows.length; i++) {
				delete rows[i][''];
			}
			
			$.ajax({
				  type: 'post',
				  url: typeRemoveUrl,
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
function bindUpdateEvent(){
	$("#updateInfo").on("click",function(){
		var rows = $('#viewTable').bootstrapTable('getAllSelections');
		if(rows.length == 0){
			notifyWarning("必须选择一条记录!");
		}else if(rows.length>1){
			notifyWarning("只能选择一条记录!");
		}else{
			var id = rows[0].id;
			var name = rows[0].name;
			var intout = rows[0].intout;
			$("#id").val(id);
			$("#name").val(name);
			$("#intout").val(intout);
			$("#updateRecord").modal("show");
		}
		
	});
/*	$('#updateRecord').on('show.bs.modal', function () {
		  console.log(1111);
	})*/
}

function bindFormSubmit(){
	$("#form").submit(function(ev){ev.preventDefault();});
	$("#submitForm").on("click", function(){

		   var bootstrapValidator = $("#form").data('bootstrapValidator');
		   bootstrapValidator.validate();
		   if(bootstrapValidator.isValid())
			   $.post(typeAddUrl, $("#form").serialize(), function(data) {
			        if(data && data.status == true){
			        	$("#addRecord").modal('hide');
			        	notifyInfo("新增记录成功!");
			        	searchBootstrapTable();
			        }else{
			        	notifyInfo("新增记录出错!");
			        }
			    })
		
		   else return;

	});
}

function bindForm2Submit(){
	$("#form2").submit(function(ev){ev.preventDefault();});
	$("#submitForm2").on("click", function(){

		   var bootstrapValidator = $("#form2").data('bootstrapValidator');
		   bootstrapValidator.validate();
		   if(bootstrapValidator.isValid())
			   $.post(typeUpdateUrl, $("#form2").serialize(), function(data) {
			        if(data && data.status == true){
			        	$("#updateRecord").modal('hide');
			        	notifyInfo("修改记录成功!");
			        	searchBootstrapTable();
			        }else{
			        	notifyInfo("修改记录出错!");
			        }
			    })
		
		   else return;

	});
}

function searchData(){
	searchBootstrapTable();
}

function queryParams(params) {  //配置参数

    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的  
      pageSize: params.pageSize,   //页面大小  
      pageNumber: params.pageNumber,  //页码  
      pageList : params.pageList,
      sortOrder: params.order//排位命令（desc，asc）  
      
    };  
    return temp;  
  }  

function refreshBootstrapTable(){
	$('#viewTable').bootstrapTable('refresh', {url: typeUrl});
}

function searchBootstrapTable(){
	
	$('#viewTable').bootstrapTable("destroy");
	$('#viewTable').bootstrapTable({
		method: 'get',
		url : typeUrl,
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
			{ field : "id", visible : false },   
			{ field : "name", title : "名称", align : "center", valign : "middle" },
			{ field : 'intout',title : '类型', align : 'center' ,valign : "middle",
				formatter : function(value, row, index){
					var returnStr = value;
					if(1 == value){
						returnStr = "收入";
					}else if(-1 == value){
						returnStr = "支出";
					}
					return returnStr;
				}
			}
			
		]
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
             typeName: {//验证input项：验证规则
                 message: 'The username is not valid',
                
                 validators: {
                     notEmpty: {//非空验证：提示消息
                         message: '用户名不能为空'
                     },
                     stringLength: {
                         min: 2,
                         max: 40,
                         message: '用户名长度必须在2到40之间'
                     }
                     //,
                     /*threshold :  6 ,*/ //有6字符以上才发送ajax请求，（input中输入一个字符，插件会向服务器发送一次，设置限制，6字符以上才开始）
                     /*remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
                         url: 'exist2.do',//验证地址
                         message: '用户已存在',//提示消息
                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                         type: 'POST'//请求方式
                         *//**自定义提交数据，默认值提交当前input value
                          *  data: function(validator) {
                               return {
                                   password: $('[name="passwordNameAttributeInYourForm"]').val(),
                                   whatever: $('[name="whateverNameAttributeInYourForm"]').val()
                               };
                            }
                          *//*
                     },*/
                     /*regexp: {
                         regexp: /^[a-zA-Z0-9_\.]+$/,
                         message: '用户名由数字字母下划线和.组成'
                     }*/
                 }
             }
            
             
         }
     });
	
	
	 $('#form2')
     .bootstrapValidator({
         message: 'This value is not valid',
         feedbackIcons: {/*input状态样式图片*/
             valid: 'glyphicon glyphicon-ok',
             invalid: 'glyphicon glyphicon-remove',
             validating: 'glyphicon glyphicon-refresh'
         },
         fields: {/*验证：规则*/
             name: {//验证input项：验证规则
                 message: 'The username is not valid',
                
                 validators: {
                     notEmpty: {//非空验证：提示消息
                         message: '用户名不能为空'
                     },
                     stringLength: {
                         min: 2,
                         max: 40,
                         message: '用户名长度必须在2到40之间'
                     }
                     //,
                     /*threshold :  6 ,*/ //有6字符以上才发送ajax请求，（input中输入一个字符，插件会向服务器发送一次，设置限制，6字符以上才开始）
                     /*remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
                         url: 'exist2.do',//验证地址
                         message: '用户已存在',//提示消息
                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                         type: 'POST'//请求方式
                         *//**自定义提交数据，默认值提交当前input value
                          *  data: function(validator) {
                               return {
                                   password: $('[name="passwordNameAttributeInYourForm"]').val(),
                                   whatever: $('[name="whateverNameAttributeInYourForm"]').val()
                               };
                            }
                          *//*
                     },*/
                     /*regexp: {
                         regexp: /^[a-zA-Z0-9_\.]+$/,
                         message: '用户名由数字字母下划线和.组成'
                     }*/
                 }
             }
            
             
         }
     });
     /*.on('success.form.bv', function(e) {//点击提交之后
         // Prevent form submission
         e.preventDefault();

         // Get the form instance
         var $form = $(e.target);

         // Get the BootstrapValidator instance
         var bv = $form.data('bootstrapValidator');

         // Use Ajax to submit form data 提交至form标签中的action，result自定义
         $.post($form.attr('action'), $form.serialize(), function(result) {
//do something...
});
     });*/

}

function submitForm(){
	$('#form').bootstrapValidator('validate');

}
