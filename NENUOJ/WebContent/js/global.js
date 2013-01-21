/**
 * 
 */

function OJ(){
}

OJ.prototype.loginCallback=function(data){
	alert(data.message);
};

OJ.prototype.loginInit=function(){
	this.WinguseAjaxForm("#login_form",this.loginCallback);
};

OJ.prototype.WinguseAjaxForm=function(formId,successCallback){
	var sj=jQuery.struts2_jquery,$form=$(formId),params={
		type:"POST",
		data:{
			"struts.enableJSONValidation": true
		},
		url:$form[0].action,
		async:false,
		cache:false,
		success:function(data){
			if(typeof(data.code)=='undefined'){
				bootstrapValidation($form,data);
			}else{
				successCallback(data);
			}
		},
		dataType:"json"
	};
	sj.require("js/plugins/jquery.form" + sj.minSuffix + ".js");
	$form.submit(function(){
		$form.ajaxSubmit(params);
		return false;
	});
};

var oj=new OJ();
oj.loginInit();