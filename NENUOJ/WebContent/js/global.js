/**
 * 
 */
"use strict";

function OJ(){
}

OJ.prototype.loginRequired=function(){
	var $form=$("#login_form");
	WinguseAjaxForm($form,function(data){
		$form.find("div.error").removeClass("error");
	    $form.find("span.s2_help_inline").remove();
	    $form.find("div.s2_validation_errors").remove();
	    $form.find("p.validateTips").text(data.message);
	    if(data.code==0)
			setTimeout(function(){
				window.location.reload();
			},1000);
	});
	$form.keyup(function(e){
		if (e.keyCode == 13)$form.submit();
	});
};
OJ.prototype.logout=function(){
	$.post(
		"logout.action",{
			r:Math.random()
		},function(d){
			if(d.code==0){
				$("body").text(d.message);
			}
			window.location.reload();
		},"json"
	);
};

function WinguseAjaxForm(form,successCallback){
	var sj=jQuery.struts2_jquery,$form=typeof(form)=="string"?$(form):form,params={
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
	if(!$form.ajaxSubmit)
		sj.require("js/plugins/jquery.form" + sj.minSuffix + ".js");
	$form.submit(function(){
		$form.ajaxSubmit(params);
		return false;
	});
};

var oj=new OJ();