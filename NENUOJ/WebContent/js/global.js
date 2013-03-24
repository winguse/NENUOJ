﻿/**
 * 
 */
"use strict";

function WinguseAjaxForm(form, successCallback) {
	var $form = typeof (form) == "string" ? $(form)
			: form, params = {
		type : "POST",
		data : {
			"struts.enableJSONValidation" : true
		},
		url : $form[0].action,
		async : false,
		cache : false,
		success : function(data) {
			$form.find("div.error").removeClass("error");
			$form.find("span.s2_help_inline").remove();
			$form.find("div.s2_validation_errors").remove();
			if (typeof (data.code) == 'undefined') {
				bootstrapValidation($form, data);
			} else {
				successCallback(data);
			}
		},
		dataType : "json"
	};
	$form.submit(function() {
		$form.ajaxSubmit(params);
		return false;
	});
};
String.prototype.xss=function(){
	return this.replace(/<script>.*?<\/script>/g,'')
	.replace(/"/g, '&quot;')
	.replace(/'/g, '&#39;')
	.replace(/</g, '&lt;')
	.replace(/>/g, '&gt;')
	.replace(/\r?\n/g, '<br/>');
};
function OJ() {
}

OJ.prototype.loginRequired = function() {
	var $form = $("#login_form");
	WinguseAjaxForm($form, function(data) {
		$form.find("p.validateTips").text(data.message);
		if (data.code == 0)
			setTimeout(function() {
				window.location.reload();
			}, 1000);
	});
	$form.keyup(function(e) {
		if (e.keyCode == 13)
			$form.submit();
	});
};
OJ.prototype.logout = function() {
	$.post(baseUrl + "/logout.action", {
		r : Math.random()
	}, function(d) {
		if (d.code == 0) {
			$("body").text(d.message);
		}
		window.location.reload();
	}, "json");
};
OJ.prototype.getParm=function(parm) {
	var re = new RegExp("#.*[?&]" + parm + "=([^&]+)(&|$)");
	var match = window.location.hash.match(re);
	return (match ? match[1] : "");
};
OJ.prototype.setPageTitle=function(title) {
	$("h1").text(title);
	document.title = title + document.title.replace(/^.+?( - .+?)$/, "$1");
};
OJ.prototype.loadProblem=function(problemId,descriptionId){
	if(!problemId)
		problemId=parseInt(this.getParm("problemId"));
	if(!descriptionId)
		descriptionId=parseInt(this.getParm("descriptionId"));
	var $progress=$(".progress>.bar"),I=this;
	if(isNaN(problemId)){
		$progress.animate({width:"100%"});
		I.setPageTitle($.t("invalid_parameter"));
		return;
	}
	if(isNaN(descriptionId))
		descriptionId=0;
	$("#problemId").attr("value",problemId);
	$progress.animate({width:"40%"});
	$.get(baseUrl + "/problems/json/view.action",{
		problemId:problemId,
		descriptionId:descriptionId,
		"struts.enableJSONValidation" : true
	},function(d){
		if(d.fieldErrors){
			$progress.animate({width:"100%"});
			I.setPageTitle($.t("invalid_parameter"));
		}else{
			var $pdl=$("#description_list"),p = d.problem;
			var langHtml = "";
			for(var i in p.supprotedLanguage){
				langHtml += "<option value='"+p.supprotedLanguage[i]+"'>"+p.supprotedLanguage[i]+"</option>";
			}
			$("#language").html(langHtml);
			$pdl.html("");
			for(var i in p.descriptionList){
				var pd=p.descriptionList[i];
				$pdl.append('<li><a href="#" data-pid="'+pd.problemId+'" data-id="'+pd.id+'"><strong>@'+pd.username.xss()+"</strong> "+pd.versionMark.xss()+'</a></li>');
			}
			$pdl.find("a").click(function(){
				var $this=$(this),pid=$this.attr("data-pid"),id=$this.attr("data-id");
				location.hash="?problemId="+pid+"&descriptionId="+id;
				return false;
			});
			$progress.animate({width:"80%"});
			$(".time_limit+*").text(p.timeLimit+"MS");
			$(".memory_limit+*").text(p.memoryLimit+"KB");
			$(".accepted_submited+*").text((100.0*p.accepted/(p.accepted?p.submitted:1)).toFixed(2)+"% ("+p.accepted+"/"+p.submitted+")");
			$(".long_int_format+*").text(p.longIntFormat);
			if(p.source=="")
				$(".source").fadeOut();
			else{
				$(".source>*:nth-child(2)").html(p.source);
				$(".source").fadeIn();
			}
			I.parseDescription(p.description);
			$progress.animate({width:"100%"});
		}
	},"json");
};
OJ.prototype.parseDescription=function(description){
	var section=["description","input","output","sampleIn","sampleOut","hint"],I=this;
	I.setPageTitle(description.title);
	for(var i=0;i<section.length;i++){
		if(description[section[i]]=="")
			$("."+section[i]).fadeOut();
		else{
			$("."+section[i]+">*:nth-child(2)").html(description[section[i]]);
			$("."+section[i]).fadeIn();
		}
	}
	window.prettyPrint && prettyPrint();
};
OJ.prototype.loadProblemList=function(){
	WinguseAjaxForm("#addRemoteProblem_form",function(data){
		if(data.code==0)
			alert(data.message);
	});
	$('#problemList').dataTable( {
		"sDom": '<"H"if>t<"F"plr>',
		"bProcessing": true,
		"bServerSide": true,
		"iDisplayLength": 50,
		"bStateSave":true,
		"oLanguage": {
			"sInfo": "_START_ to _END_ of _TOTAL_ problems",//TODO replace to _("xxx")
			"sInfoEmpty": "No problems",
			"sInfoFiltered": " (filtering from _MAX_ total problems)"
		},
		"aaSorting": [[ 1, "asc" ]],
		"sAjaxSource": baseUrl + "/problems/json/list.action",
		"fnServerData": function ( sSource, _aoData, fnCallback ) {
			var aoData={};
			for(var i in _aoData){
				aoData[_aoData[i].name]=_aoData[i].value;
			}
			var orderIndex=aoData.iSortCol_0+1;
			console.log(aoData.sSortDir_0);
			if(aoData.sSortDir_0=="desc")
				orderIndex=-orderIndex;
			$.post( sSource, {
				orderByIndex:orderIndex,
				filterString:aoData.sSearch,
				page:aoData.iDisplayStart/aoData.iDisplayLength,
				pageSize:aoData.iDisplayLength
			}, function (json) {
				fnCallback({
					sEcho:aoData.sEcho,
					iTotalDisplayRecords:json.totalCount,
					iTotalRecords:json.allProblemCount,
					aaData:json.data
				});
			},"json" );
		},
		"aoColumns": [{
				"sClass": ""//judger soruce
			},{
				"sClass": ""//problem number
			},{
				"fnRender": function ( oObj ) {
					return "<a href='"+baseUrl + "/problems/view.action#?problemId="+oObj.aData[7]+"'>"+oObj.aData[2].xss()+"</a>";
				},
				"sClass": ""//problem title
			},{
				"sClass": ""//accepted
			},{
				"sClass": ""//submitted
			},{
				"fnRender": function ( oObj ) {
					var t=oObj.aData[4]==0?1:0;
					return (oObj.aData[3]/t*100).toFixed(2)+"%";
				},
				"sClass": ""//rate
			},{
				"sClass":""//source
			}
		],
		"sPaginationType": "full_numbers",
		"bJQueryUI": true
	} );
};
OJ.prototype.loadStatus=function(){
	$('#status').dataTable( {
		"sDom": '<"H"if>t<"F"plr>',
		"bProcessing": true,
		"bServerSide": true,
		"iDisplayLength": 20,
		"bStateSave":true,
		"oLanguage": {
			"sInfo": "_START_ to _END_ of _TOTAL_ status",//TODO replace to _("xxx")
			"sInfoEmpty": "No status",
			"sInfoFiltered": " (filtering from _MAX_ total status)"
		},
	//	"aaSorting": [[ 1, "asc" ]],
		"sAjaxSource": baseUrl + "/problems/json/status.action",
		"fnServerData": function ( sSource, _aoData, fnCallback ) {
			var aoData={};
			for(var i in _aoData){
				aoData[_aoData[i].name]=_aoData[i].value;
			}
			var orderIndex=aoData.iSortCol_0+1;
			console.log(aoData.sSortDir_0);
			if(aoData.sSortDir_0=="desc")
				orderIndex=-orderIndex;
			$.post( sSource, {
				orderByIndex:orderIndex,
				username:$("#username").attr("value"),
				language:$("#language").attr("value"),
				problemNumber:$("#problemNumber").attr("value"),
				statusCode:$("#statusCode").attr("value"),
				judgerSource:$("#judgerSource").attr("value"),
				page:aoData.iDisplayStart/aoData.iDisplayLength,
				pageSize:aoData.iDisplayLength
			}, function (json) {
				fnCallback({
					sEcho:aoData.sEcho,
					iTotalDisplayRecords:json.totalCount,
					iTotalRecords:json.allStatusCount,
					aaData:json.data
				});
			},"json" );
		},
		"aoColumns": [{
				"sClass": ""//runId
			},{
				"sClass": ""//username
			},{
				"fnRender": function ( oObj ) {
					return "<a href='"+baseUrl + "/problems/view.action#?problemId="+oObj.aData[9]+"'>"+oObj.aData[2].xss()+"</a>";
				},
				"sClass": ""//problem
			},{
				"sClass": "",//status description,
				"fnRender": function ( oObj ) {
					return oObj.aData[3];
					//TODO if CE then.. and add css
				}
			},{
				"sClass": "",//memory
				"fnRender": function ( oObj ) {
					return oObj.aData[4]+"KB";
				}
			},{
				"fnRender": function ( oObj ) {
					return oObj.aData[5]+"MS";
				},
				"sClass": ""//time
			},{
				"sClass":""//language
			},{
				"fnRender": function ( oObj ) {
					return oObj.aData[7]+"B";
				},
				"sClass":""//code length
			},{
				"fnRender": function ( oObj ) {
					return new Date(oObj.aData[8]);
				},
				"sClass":""//submit time
			}
		],
//		"bJQueryUI": true,
		"sPaginationType": "full_numbers"
	} );
};
var oj;
oj = new OJ();
i18n.init();
