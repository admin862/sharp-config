	/**
	 * 读Cookie，返回值为相应Cookie的内容
	 * @param cookieName
	 * @returns {String}
	 */
	function getCookie(cookieName){
	    var cookieContent = '';
	    var cookieAry = document.cookie.split("; ");//得到Cookie数组
	    for(var i=0;i<cookieAry.length;i++){
	        var temp = cookieAry[i].split("=");
	        if(temp[0] == cookieName){
	             cookieContent = unescape(temp[1]);
	        }
	    }
	    return cookieContent;
	}
	
	/**
	 * 根据URL中Key获取值
	 * @param name
	 * @returns
	 */
	function getUrlParam(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
		var r = window.location.search.substr(1).match(reg); //匹配目标参数
		if (r != null)
			return unescape(r[2]);
		return null; //返回参数值
	}
	
	/**
	 * 设置cookie
	 * @param cname
	 * @param cvalue
	 * @param exdays
	 */
	function setCookie(cname, cvalue, exdays) {
	    var d = new Date();
	    d.setTime(d.getTime() + (exdays*24*60*60*1000));
	    var expires = "expires="+d.toUTCString();
	    document.cookie = cname + "=" + cvalue + "; " + expires;
	}
		
		
	/**
	 * 清除cookie  
	 * @param name
	 */
	function clearCookie(name) {  
	    setCookie(name, "", -1);  
	}
	
	
	function getUrlParam(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
		var r = window.location.search.substr(1).match(reg); //匹配目标参数
		if (r != null)
			return unescape(r[2]);
		return null; //返回参数值
	}
