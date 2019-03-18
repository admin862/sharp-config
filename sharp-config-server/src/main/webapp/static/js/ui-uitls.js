function topMessage(message){
	$.messager.show({
		title: '提示信息',
		msg: '【系统提示】：'+message,
		style: {
			right: '',
			top: document.body.scrollTop + document.documentElement.scrollTop,
			bottom: ''
		}
	});
}