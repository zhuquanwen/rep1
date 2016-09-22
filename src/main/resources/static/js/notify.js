
/**提示信息*/
function notifyInfo(message){
	$.notify({
		icon: 'glyphicon glyphicon-ok-sign',
		title: '<strong>信息:</strong>',
		message: message
	},{
		type: 'success',
		placement: {
			from: "top",
			align: "center"
		}
	});
}

/**提示错误*/
function notifyError(message){
	$.notify({
		icon: 'glyphicon glyphicon-remove-sign',
		title: '<strong>错误:</strong>',
		message: message
		},{
		type: 'danger',
		placement: {
			from: "top",
			align: "center"
		}
	});
}

/**提示警告*/
function notifyWarning(message){
	$.notify({
		icon: 'glyphicon glyphicon-warning-sign',
		title: '<strong>警告:</strong>',
		message: message
	},{
		type: 'warning',
		placement: {
			from: "top",
			align: "center"
		}
	});
}