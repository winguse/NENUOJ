/**
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
Date.prototype.ojFormat=function(){
	function _(i){return i<10?"0"+i:""+i;}
	return this.getFullYear()+"-"+_(this.getMonth()+1)+"-"+_(this.getDate())+" "+_(this.getHours())+":"+_(this.getMinutes())+":"+_(this.getSeconds());
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
	this.syncStatus={};
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

OJ.prototype.addSyncStatus=function(runId){
	this.syncStatus[runId]=false;
};

OJ.prototype.rejudge=function(runId){
	this.addSyncStatus(runId);
	$("#solution_"+runId);
};


OJ.prototype.loadStatus=function(sortable){
	sortable=false;
	$('#status').dataTable({
		"sDom": '<"H">t<"F"pr>',
		"bProcessing": true,
		"bServerSide": true,
		"iDisplayLength": 20,
		"bSortable": sortable,
		"bStateSave":true,
		"oLanguage": {
			"sInfo": "_START_ to _END_ of _TOTAL_ status",//TODO replace to _("xxx")
			"sInfoEmpty": "No status",
			"sInfoFiltered": " (filtering from _MAX_ total status)"
		},
		"aaSorting": [[ 0, "desc" ]],
		"sAjaxSource": baseUrl + "/problems/json/status.action",
		"fnServerData": function ( sSource, _aoData, fnCallback ) {
			var aoData={};
			for(var i in _aoData){
				aoData[_aoData[i].name]=_aoData[i].value;
			}
			var orderIndex=aoData.iSortCol_0;
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
				"bSortable": false,
				"sClass": ""//runId
			},{

				"fnRender": function ( oObj ) {
					return "<a href='"+baseUrl + "/showUser.action#?username="+oObj.aData[1].xss()+"'>"+oObj.aData[1].xss()+"</a>";
				},
				"bSortable": false,
				"sClass": ""//username
			},{
				"bSortable": false,
				"fnRender": function ( oObj ) {
					return "<a href='"+baseUrl + "/problems/view.action#?problemId="+oObj.aData[9]+"'>"+oObj.aData[2].xss()+"</a>";
				},
				"sClass": ""//problem
			},{
				"bSortable": false,
				"sClass": "",//status description,
				"fnRender": function ( oObj ) {
					var status = oObj.aData[11];
					console.log();
					if(status==1){//pedding
						return '<span class="badge">'+oObj.aData[3].xss()+'</span>';
					}else if(status==2){//processing
						return '<div class="progress progress-striped active"><div class="bar" style="width:100%">'+oObj.aData[3].xss()+'</div></div>';
					}else if(status==3){//judge error
						return '<span class="badge badge-warning" title="'+$.t("Last Status Description: ")+oObj.aData[3].xss()+'">'+
						$.t("judgeError")+' <i onclick="oj.rejudge('+oObj.aData[0]+')" class="icon-repeat icon-white" style="cursor: pointer;" title="'+$.t("Click here to rejudge.")+'"></i></span>';
					}else if(status==4){//accepted
						return '<span class="badge badge-success">'+oObj.aData[3].xss()+'</span>';
					}else{
						//TODO if CE then.. and add css
						return '<span class="badge badge-important">'+oObj.aData[3].xss()+'</span>';
					}
					
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
				"bSortable": false,
				"sClass":""//language
			},{
				"fnRender": function ( oObj ) {
					return oObj.aData[7]+"B";
				},
				"sClass":""//code length
			},{
				"bSortable": false,
				"fnRender": function ( oObj ) {
					return new Date(oObj.aData[8]).ojFormat();
				},
				"sClass":""//submit time
			}
		],
//		"bJQueryUI": true,
		"sPaginationType": "full_numbers",
		"fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {
			$(nRow).attr("id", "solution_"+aData[0]);
			if(aData[11]==2||aData[11]==1){//processing & pedding
				//TODO add to update list
			}
			return nRow;
		}
	} );
};
var oj;
oj = new OJ();
i18n.init();
