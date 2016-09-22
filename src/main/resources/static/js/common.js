/**
 * 格式化时间
 * @param value
 * @returns {String}
 */
function formateDateTime(value,type){
	var date;
	if(value==null||value==''){
		date=new Date();
	}else{
		date=new Date(value);
	}
	 var seperator1 = "-";
	    var seperator2 = ":";
	    var year = date.getFullYear();
	    var month = date.getMonth() + 1;
	    var strDate = date.getDate();
	    if (month >= 1 && month <= 9) {
	        month = "0" + month;
	    }
	    if (strDate >= 0 && strDate <= 9) {
	        strDate = "0" + strDate;
	    }
	    var strHour=date.getHours();
	    if (strHour >= 0 && strHour <= 9) {
	        strHour = "0" + strHour;
	    }
	    var strMi=date.getMinutes();
	    if (strMi >= 0 && strMi <= 9) {
	    	strMi = "0" + strMi;
	    }
	    var strSecond=date.getSeconds();
	    if (strSecond >= 0 && strSecond <= 9) {
	    	strSecond = "0" + strSecond;
	    }
	    var currentdate = year + seperator1 + month + seperator1 + strDate
	            + " " + strHour + seperator2 + strMi
	            + seperator2 + strSecond;
	    if("year" == type){
	    	currentdate = year;
	    }else if("month" == type){
	    	currentdate = year + seperator1 + month;
	    }
	    return currentdate;
}

