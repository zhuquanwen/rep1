	/**
  		*container:图标所在的DIV
  		*title:标题
  		*subTitle:小标题
  		*yAxis：y轴信息
  		*url:获取数据的ajax URL
  		*/
  		function getColumnhartx(container,title,subTitle,yAxis,tooltip,url){
  			$("#"+container).html("");
  			 var options1 = {  
			    chart: {  
			    	/* style: {
						fontFamily: "Signika, serif"
					}, */
			    	 borderWidth:0, 
			        renderTo: container, 
			        type: 'column'
			        
			        
			    },  
			   title: title,
		        subtitle:subTitle,
			    xAxis: {  
			        
			        categories: []  
			    },  
			    yAxis:yAxis,
			    tooltip:tooltip,
		        plotOptions: {
		            column: {
		            	 pointWidth: 30,  
		            	 borderRadius: 0,            //柱子两端的圆角的半径
		                pointPadding: 0.2,
		                borderWidth: 0
		            }
		        },
		         lang:{
			          printChart:"打印图表", 
	                  downloadJPEG: "下载JPEG 图片",  
	                  downloadPDF: "下载PDF文档"  ,
	                  downloadPNG: "下载PNG 图片" , 
	                  downloadSVG: "下载SVG 矢量图"  ,
	                  exportButtonTitle: "导出图片"  
      			},
      			/*  loading: {
		            hideDuration: 1000,
		            showDuration: 1000
		        }, */ 
		        credits: {
				    enabled: false
				},
				colors: [ '#50B432', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4','#ff0000']
			    //series: [{}]  
			}; 
  			 
		    var chart1 = new Highcharts.Chart(options1);
		    chart1.showLoading('正在加载数据...');  
		    
		      $.ajax(  
		        {  
		            type : 'POST',  
		            url : url,   
		            data : null,  
		            success : function(data)  
		            {  	categories=data.categories;
		            	series=data.series;
		            	options1.xAxis={categories:categories};
		            	options1.series=series;
		            	/*var len=categories.length;*/
		            	/*var width=$('#nfsContainer').width();
		            	if((width-30)/30<len){
		            		var plotOptions={
		     		            column: {
		     		            	 borderRadius: 0,            //柱子两端的圆角的半径
		     		                pointPadding: 0.2,
		     		                borderWidth: 0
		     		            }
		     		        };
		            		options1.plotOptions=plotOptions;
		            	}*/
		            	
		            	chart1 = new Highcharts.Chart(options1);  
		            	
		            	var i=0;
			  			$("#"+container).find(".highcharts-button").each(function(){
			  				
			  				if(i!=0){
			  					$(this).remove();
			  				}
			  				i++;
			  			});
			  			
		            },  
		            error : function()  
		            {  
		                alert("获取统计图表数据出错!");  
		            }  
		        });  
  		}
  		
  		function initChart(container,title){
  			/*$('#'+container).highcharts().destroy();*/
  			$("#"+container).html("");
  			var options1 = {  
  				    chart: {  
  				    	
  				    	 borderWidth:0, 
  				        renderTo: container, 
  				        type: 'column'
  				        
  				        
  				    },
  				  credits: {
  				    enabled: false
  				  },
  				lang:{
			          printChart:"打印图表", 
	                  downloadJPEG: "下载JPEG 图片",  
	                  downloadPDF: "下载PDF文档"  ,
	                  downloadPNG: "下载PNG 图片" , 
	                  downloadSVG: "下载SVG 矢量图"  ,
	                  exportButtonTitle: "导出图片"  
    			},
  				title: {
  			            text: title,
  			            style:{
  			            	'font-size':'16px',
  			            	'font-family':'微软雅黑',
  			            	'color':'#333333'
  					    }
  			        }
  			};
  			var chart1 = new Highcharts.Chart(options1);
  			 chart1.showLoading('<span style="color:blue;">没有对应数据</span>'); 
  			var i=0;
  			$("#"+container).find(".highcharts-button").each(function(){
  				
  				if(i!=0){
  					$(this).remove();
  				}
  				i++;
  				
  			});
  			
  			/*var widthx=$('#nfsContainer').width();
        	if(widthx<1300&&window.navigator.userAgent.indexOf("Chrome") !== -1 ){
        		var width=(widthx-16)+"px";
        		
        		$("#nfsContainer1").css({"width":width});
        		$("#containerx").css({"width":width});
        		$("#container_bottom").css({"width":width});
        		$("#nfsContainer1").css({"max-width":width});
        		$("#containerx").css({"max-width":width});
        		$("#container_bottom").css({"max-width":width});
        		
        	}else{
        		var width=widthx+"px";
        		
        		$("#nfsContainer1").css({"width":width});
        		$("#containerx").css({"width":width});
        		$("#container_bottom").css({"width":width});
        		$("#nfsContainer1").css({"max-width":width});
        		$("#containerx").css({"max-width":width});
        		$("#container_bottom").css({"max-width":width});
        	}*/
    		
  		}
  		
  		
  		function getPieChart(containerx,title,url){
  			
  			
  			$.ajax(  
  			        {  
  			            type : 'POST',  
  			            url : url,   
  			            data : null,  
  			            success : function(data)  
  			            {  	
  			            	
  			            	series=data.series;
  			            	$('#'+containerx).highcharts({
  			                    chart: {
  			                        plotBackgroundColor: null,
  			                        plotBorderWidth: null,
  			                        plotShadow: false,
  			                        borderWidth:1, 
  			                        type: 'pie'
  			                    },
  			                    title: title, 
  			                    tooltip: {
  			                        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
  			                    },
  			                    plotOptions: {
  			                        pie: {
  			                            allowPointSelect: true,
  			                            cursor: 'pointer',
  			                            dataLabels: {
  			                                enabled: true,
  			                                format: '<b>{point.name}</b>: {point.percentage:.1f} %',
  			                                style: {
  			                                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
  			                                }
  			                            }
  			                        }
  			                    },
  			                    credits: {
  			            		    enabled: false
  			            		},
  			            		 lang:{
  			           	          printChart:"打印图表", 
  			                         downloadJPEG: "下载JPEG 图片",  
  			                         downloadPDF: "下载PDF文档"  ,
  			                         downloadPNG: "下载PNG 图片" , 
  			                         downloadSVG: "下载SVG 矢量图"  ,
  			                         exportButtonTitle: "导出图片"  
  			           			},
  			                    series : series
  			                  /*  series: [{
  			                        name: '11',
  			                        colorByPoint: true,
  			                        data: [{
  			                            name: 'Microsoft Internet Explorer',
  			                            y: 56.33
  			                        }, {
  			                            name: 'Chrome',
  			                            y: 24.03,
  			                            sliced: true,
  			                            selected: true
  			                        }, {
  			                            name: 'Firefox',
  			                            y: 10.38
  			                        }, {
  			                            name: 'Safari',
  			                            y: 4.77
  			                        }, {
  			                            name: 'Opera',
  			                            y: 0.91
  			                        }, {
  			                            name: 'Proprietary or Undetectable',
  			                            y: 0.2
  			                        }]
  			                    }]*/
  			                });
  			            },  
  			            error : function()  
  			            {  
  			                notifyError("获取统计图表数据出错!");  
  			            }  
  			        });  
  			
  			
  		}

  		/**
  			*container:图标所在的DIV
  			*title:标题
  			*subTitle:小标题
  			*yAxis：y轴信息
  			*url:获取数据的ajax URL
  			*/
  			function getColumnhart(container,title,subTitle,yAxis,url){
  				 var options1 = {  
  			    chart: {  
  			    	/* style: {
  						fontFamily: "Signika, serif"
  					}, */
  			    	 borderWidth:1, 
  			        renderTo: container, 
  			        type: 'column'
  			        
  			        
  			    },  
  			   title: title,
  		        subtitle:subTitle,
  			    xAxis: {  
  			        
  			        categories: []  
  			    },  
  			    yAxis:yAxis,
  		       /*  tooltip: {
  		            headerFormat: '<span style="font-size:12px">{point.key}</span><table>',
  		            pointFormat: '<tr><td style="font-size:12px;color:{series.color};padding:0">{series.name}: </td>' +
  		                '<td style="font-size:12px;padding:0"><b>{point.y:.1f} 个</b></td></tr>',
  		            footerFormat: '</table>',
  		            shared: true,
  		            useHTML: true
  		        },  */   //废除，应用默认样式
  		        
  		    
  		        
  		        plotOptions: {
  		            column: {
  		                pointPadding: 0.2,
  		                borderWidth: 0
  		            }
  		        },
  		         lang:{
  			          printChart:"打印图表", 
  		              downloadJPEG: "下载JPEG 图片",  
  		              downloadPDF: "下载PDF文档"  ,
  		              downloadPNG: "下载PNG 图片" , 
  		              downloadSVG: "下载SVG 矢量图"  ,
  		              exportButtonTitle: "导出图片"  
  					},
  					/*  loading: {
  		            hideDuration: 1000,
  		            showDuration: 1000
  		        }, */ 
  		        credits: {
  				    enabled: false
  				},
  				colors: [ '#50B432', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4','#ff0000']
  			    //series: [{}]  
  			}; 
  		    var chart1 = new Highcharts.Chart(options1);
  		    chart1.showLoading('正在加载数据...');  
  		    
  		      $.ajax(  
  		        {  
  		            type : 'POST',  
  		            url : url,   
  		            data : null,  
  		            success : function(data)  
  		            {  	categories=data.categories;
  		            	
  		            	series=data.series;
  		            	options1.xAxis={categories:categories};
  		            	options1.series=series;
  		            	chart1 = new Highcharts.Chart(options1);  
  		        		var i=0;
  			  			$("#"+container).find(".highcharts-button").each(function(){
  			  				
  			  				if(i%2==1){
  			  					$(this).remove();
  			  				}
  			  				i++;
  			  			});
  		        		
  		             	/* viewChart("container",title, categories, yAxis, series,subTitle); */
  		            },  
  		            error : function()  
  		            {  
  		                notifyError("获取统计图表数据出错!");  
  		            }  
  		        });  
  			}
  			function getLine(div,chart,title,subtitle,xAxis,yAxis,tooltip,series,url){
  				$.ajax(  
  					        {  
  					            type : 'POST',  
  					            url : url,   
  					            data : null,  
  					            success : function(data)  
  					            {  	
  					            	categories = data.categories;
  					            	var xAxis = {  
  					                        categories:categories
  					                 	        };
  					            	series = data.series;
  					            	$('#'+div).highcharts({  
  					         	       chart : chart,  
  					         	       title: title,  
  					         	        subtitle: subtitle,  
  					         	        xAxis: xAxis,  
  					         	        yAxis: yAxis, 
	  					         	    lang:{
	  								          printChart:"打印图表", 
	  						                  downloadJPEG: "下载JPEG 图片",  
	  						                  downloadPDF: "下载PDF文档"  ,
	  						                  downloadPNG: "下载PNG 图片" , 
	  						                  downloadSVG: "下载SVG 矢量图"  ,
	  						                  exportButtonTitle: "导出图片"  
	  					    			},
  					         	        tooltip: tooltip,
  					         	        credits: {
  						            		    enabled: false
  						            		},
  					         	        plotOptions: {  
  					         	            column: {  
  					         	                pointPadding: 0.2,  
  					         	                borderWidth: 0  
  					         	            }  
  					         	        },  
  					         	        series: series
  					         	    });  
  					            	
  					              
  					            },  
  					            error : function()  
  					            {  
  					                notifyError("获取统计图表数据出错!");  
  					            }  
  					        }); 
  				
  				
  				
  			}		