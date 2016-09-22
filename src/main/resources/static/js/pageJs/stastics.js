//获取统计概况的URL
var general_url = "/count/stastics/getGeneral";
var stasticType_columnView_url = "/count/stastics/getType/column";
var stasticType_pieView_url = "/count/stastics/getType/pie";
var stasticInout_columnView_url = "/count/stastics/getInout/column";
var stasticInout_pieView_url = "/count/stastics/getInout/pie";

var stasticTime_lineView_url = "/count/stastics/getTime/line";
$(function(){
	toInitDatetime();
	
	
	
	$("#dateTimeInput2").val(formateDateTime());
	$("#dtp_input2").val(formateDateTime());
	
	$("#dateTimeInput4").val(formateDateTime());
	$("#dtp_input4").val(formateDateTime());
	
	$("#dateTimeInput5").val(formateDateTime(new Date().getTime()-5*30*24*3600*1000,"month"));
	$("#dtp_input5").val(formateDateTime(new Date().getTime()-5*30*24*3600*1000,"month"));
	
	$("#dateTimeInput6").val(formateDateTime(new Date().getTime(),"month"));
	$("#dtp_input6").val(formateDateTime(new Date().getTime(),"month"));
	
	//获取统计概况
	getGeneral();
	
	
});

function toInitDatetime(){
	$('#dateSpan1').datetimepicker({
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
		//
    });
	$('#dateSpan2').datetimepicker({
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
		//
    });
	$('#dateSpan3').datetimepicker({
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
		//
    });
	$('#dateSpan4').datetimepicker({
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
		//
    });
	
	$('#dateSpan5').datetimepicker({
		 format: 'yyyy-mm',  
         weekStart: 1,  
         autoclose: true,  
         startView: 3,  
         minView: 3,  
         forceParse: false,  
         language: 'zh-CN'
	}).on('changeDate', function(e){
		//
		console.log(1111);
    });
	$('#dateSpan6').datetimepicker({
		 format: 'yyyy-mm',  
        weekStart: 1,  
        autoclose: true,  
        startView: 3,  
        minView: 3,  
        forceParse: false,  
        language: 'zh-CN'
	}).on('changeDate', function(e){
		//
		console.log(1111);
   });
}

function stasticByType(){
	var title= {
            text: '类型统计(柱状图)',
            style:{
            	'font-size':'16px',
            	'font-family':'微软雅黑',
            	'color':'#333333'
		    }
        };
        
    var subTitle={
            text: '按照类型统计账务情况',
            style:{
            	'font-size':'12px',
            	'font-family':'微软雅黑'
            	
            }
        };    
		var categories=[];
		var yAxis={
            min: 0,
            title: {
                text: '统计钱数 (元)'
            }
        };
   var url = stasticType_columnView_url + "?startTime=" + $("#dtp_input1").val()+"&endTime=" + $("#dtp_input2").val();
  getColumnhart('stasticType_columnView',title,subTitle,yAxis,url);
 
   var title1= {
           text: '按类型统计(饼状图)'
   };
       
  
		
   var url1 = stasticType_pieView_url + "?startTime=" + $("#dtp_input1").val()+"&endTime=" + $("#dtp_input2").val();
   getPieChart('stasticType_PieView',title1,url1);
}

function stasticByInout(){
	var title= {
            text: '收支统计(柱状图)',
            style:{
            	'font-size':'16px',
            	'font-family':'微软雅黑',
            	'color':'#333333'
		    }
        };
        
    var subTitle={
            text: '按照收支统计账务情况',
            style:{
            	'font-size':'12px',
            	'font-family':'微软雅黑'
            	
            }
        };    
		var categories=[];
		var yAxis={
            min: 0,
            title: {
                text: '统计钱数 (元)'
            }
        };
   var url = stasticInout_columnView_url + "?startTime=" + $("#dtp_input3").val()+"&endTime=" + $("#dtp_input4").val();
  getColumnhart('stasticInout_columnView',title,subTitle,yAxis,url);
 
   var title1= {
           text: '按收支统计(饼状图)'
   };
       
  
		
   var url1 = stasticInout_pieView_url + "?startTime=" + $("#dtp_input3").val()+"&endTime=" + $("#dtp_input4").val();
  getPieChart('stasticInout_PieView',title1,url1);
}
function stasticByTime(){
	if($("#dtp_input5").val()==null||$("#dtp_input5").val()==""||
			$("#dtp_input6").val()==null||$("#dtp_input6").val()==""){
		notifyError("开始时间和结束时间不能为空!");
		return;
	}else{
		var url = stasticTime_lineView_url + "?startTime=" + $("#dtp_input5").val()+
		"&endTime=" + $("#dtp_input6").val() + "&timeType=" + $("#timeType").val();
		var chart = {
			borderWidth:1, 	
            type: 'line'  
        };
		var text = "";
		if("year" == $("#timeType").val()){
			text = $("#dtp_input5").val().split("-")[0] + "至"+$("#dtp_input6").val().split("-")[0] + "收支情况"
		}else if("month" == $("#timeType").val()){
			text = $("#dtp_input5").val().split("-")[0] + $("#dtp_input5").val().split("-")[1] +
			"至"+$("#dtp_input6").val().split("-")[0] + $("#dtp_input6").val().split("-")[1] + "收支情况"
		}
        var title = {  
	            text: text
        };
        var div = "stasticTime_LineView";
        var subtitle = {  
	            text: '收支情况'  
        }
        var xAxis = {  
            categories: [  
     	                '一月',  
     	                '二月',  
     	                '三月',  
     	                '四月',  
     	                '五月',  
     	                '六月',  
     	                '七月',  
     	                '八月',  
     	                '九月',  
     	                '十月',  
     	                '十一月',  
     	                '十二月'  
     	            ]  
     	        };
       var yAxis= {  
            min: 0,  
            title: {  
                text: '收入 (￥)'  
            }  
        }; 
       var tooltip = {  
           headerFormat: '<span style="font-size:20px">{point.key}</span><table>',  
           pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +  
               '<td style="padding:0"><b>{point.y:.1f} 元</b></td></tr>',  
           footerFormat: '</table>',  
           shared: true,  
           useHTML: true  
       };
       var series = [{  
           name: '张三',  
           data: [4995, 7169, 1064, 7292, 2440, 4545, 6545, 9564, 1245, 4512, 6523, 4514]  
 
       }, {  
           name: '李思',  
           data: [8361, 2354, 4512, 2356, 4515, 6451, 9865, 5455, 8254, 6562, 6945, 2356]  
 
       }, {  
           name: '王武',  
           data: [4512, 9565, 6564, 2652, 3265, 1202, 7845, 9845, 2356, 7844, 5424, 6312]  
 
       }, {  
           name: '赵六',  
           data: [6523, 8956, 6531, 6532, 9864, 4552, 9564, 7845, 6523, 4512, 8956, 2356]  
 
       }]  
		
	}
	
	getLine(div,chart,title,subtitle,xAxis,yAxis,tooltip,series,url);
}



function changeTimeType(){
	removeDateTime5();
	removeDateTime6();
	$('#dateSpan6').datetimepicker('remove');
	$('#dateSpan5').datetimepicker('remove');
	if("year" == $("#timeType").val()){
		$('#dateSpan5').datetimepicker({
			   format: 'yyyy',  
		       weekStart: 1,  
		       autoclose: true,  
		       startView: 4,  
		       minView: 4,  
		       forceParse: false,  
		       language: 'zh-CN'
			}).on('changeDate', function(e){
				
		  });
			$('#dateSpan6').datetimepicker({
				   format: 'yyyy',  
			       weekStart: 1,  
			       autoclose: true,  
			       startView: 4,  
			       minView: 4,  
			       forceParse: false,  
			       language: 'zh-CN'
				}).on('changeDate', function(e){
					
			  });
			$("#dtp_input5").val(formateDateTime(new Date().getTime()-365*2*24*3600*1000,"year"));
			$("#dateSpan5").datetimepicker("update", formateDateTime(new Date().getTime()-365*2*24*3600*1000,"year")+"");
			$("#dtp_input6").val(formateDateTime(new Date().getTime(),"year"));	
			
			$("#dateSpan6").datetimepicker("update", formateDateTime(new Date().getTime(),"year")+"");
			
			
	}else{
		$('#dateSpan5').datetimepicker({
			 format: 'yyyy-mm',  
	         weekStart: 1,  
	         autoclose: true,  
	         startView: 3,  
	         minView: 3,  
	         forceParse: false,  
	         language: 'zh-CN'
		}).on('changeDate', function(e){
			//
	    });
		$('#dateSpan6').datetimepicker({
			 format: 'yyyy-mm',  
	        weekStart: 1,  
	        autoclose: true,  
	        startView: 3,  
	        minView: 3,  
	        forceParse: false,  
	        language: 'zh-CN'
		}).on('changeDate', function(e){
			//
	   });
		
		$("#dtp_input5").val(formateDateTime(new Date().getTime()-5*30*24*3600*1000,"month"));
		$("#dateSpan5").datetimepicker("update", formateDateTime(new Date().getTime()-5*30*24*3600*1000,"month")+"");
		$("#dateSpan6").datetimepicker("update", formateDateTime(new Date().getTime(),"month")+"");
		$("#dtp_input6").val(formateDateTime(new Date().getTime(),"month"));	
	}
	
	
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
	
}

function removeDateTime4(){
	$("#dateTimeInput4").val("");
	$("#dtp_input4").val("");
	
}
function removeDateTime5(){
	$("#dateTimeInput5").val("");
	$("#dtp_input5").val("");
	
}

function removeDateTime6(){
	$("#dateTimeInput6").val("");
	$("#dtp_input6").val("");
	
}

function getGeneral(){
	$.ajax({
		  type: 'post',
		  url: general_url,
		  dataType: "json",
		  success: function(data) {
		        var inx = data.STASTICS_MONTH_IN;
		        var outx = data.STASTICS_MONTH_OUT;
		        var inoutx = data.STASTICS_MONTH_IN_OUT;
		        var inoutxh = data.STASTICS_MONTH_IN_OUT_H;
		        $("#in").text(inx);
		        $("#out").text(outx);
		        $("#inout").text(inoutx);
		        $("#inouth").text(inoutxh);
		    },
		   error: function(data) {
			   notifyError("获取统计概况信息出错!");
		   }
		});
}


