/**
 * 
 */
"use strict";

i18n.init();

var STATUS_ALL = 0,
 STATUS_PEDDING = 1,
 STATUS_PROCESSING = 2,
 STATUS_JUDGE_ERROR = 3,
 STATUS_ACCEPTED = 4,
 STATUS_PRESENTATION_ERROR = 5,
 STATUS_WRONG_ANSWER = 6,
 STATUS_TIME_LIMITED_EXCEED = 7,
 STATUS_MEMORY_LIMITED_EXCEED = 8,
 STATUS_OUTPUT_LIMITED_EXCEED = 9,
 STATUS_RUNTIME_ERROR = 10,
 STATUS_COMPLIE_ERROR = 11,
 PERMISSION_LOGIN = 1,
 PERMISSION_VIEW_ALL_SOURCE_CODE = 2,
 PERMISSION_ADMIN_PRIVILEGE = 4,
 PERMISSION_SEE_LOCKED_DESCRIPTION = 8,
 PERMISSION_SEE_LOCKED_PROBLEM = 16,
 PERMISSION_ADD_CONTEST = 32,
 LIST_PUBLIC = 1,
 LIST_PRIVATE = 2,
 LIST_REGISTERATION = 4,
 LIST_REPLAY = 8,
 LIST_RUNNING = 16,
 LIST_PASSED = 32,
 LIST_PEDDING = 64; 

var contestTypeDescription=[$.t("Public"),$.t("Private"),$.t("Registeration Needed"),$.t("Replay")];

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
	this.syncStatusList={};
	this.syncHandle=0;
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
					var t=oObj.aData[4]==0?1:oObj.aData[4];
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
	this.syncStatusList[runId]=false;
};

OJ.prototype.rejudge=function(runId){
	var I = this;
	$("#solution_"+runId+">.statusDescription").html(
		I.statusDescriptionHTML(STATUS_PEDDING, $.t('Pedding Rejudge'), runId)
	);
	$.post(
		baseUrl + "/problems/json/rejudge.action",{
			runId:runId
		},function(d){
			if(d.code == 0){
			}else{
				I.showMessage(d.message);
			}
			I.addSyncStatus(runId);
			I.syncStatus();
		},"json"
	);
};
OJ.prototype.showMessage=function(message,title,callback){
	var I = this;
	I.messageCallback = callback;
	if(title=="")title="&nbsp;";
	if(!I.messageDialogLoaded){
		I.messageDialogLoaded = true;
		$("body").append('<div id="messageDialog" class="hide"></div>');
		$("#messageDialog").dialog({
			modal:true,
			buttons:{
				OK : function(){
					$(this).dialog("close");
					if(I.messageCallback)
						I.messageCallback();
				}
			},
			height:"auto",
			width:"auto"
		});
	}
	var $m = $("#messageDialog");
	$m.html(message);
	$m.dialog({title:title});//TODO type
	$m.dialog("open");
};
OJ.prototype.syncStatus=function(){
	var I = this,runIdList="",queryCount = 0;
	clearTimeout(I.syncHandle);//to avoid to many sync
	for(var runId in I.syncStatusList){
		if(runIdList!="")runIdList+=",";
		runIdList+=runId;
		queryCount++;
	}
	if(runIdList=="")return;
	$.get(
		baseUrl + "/problems/json/sync-status.action",{
			r:Math.random(),
			runIdList:runIdList
		},function(d){
			if(d.code==0){
				var responseCount = 0;
				for(var i in d.data){
					responseCount++;
					var s = d.data[i];
					var runId = s[0],status = s[11],statusDescription = s[3].xss();
					$("#solution_"+runId+">.statusDescription").html(
						I.statusDescriptionHTML(status, statusDescription, runId)
					);
					$("#solution_"+runId+">.memory").text(s[4]+"KB");
					$("#solution_"+runId+">.time").text(s[5]+"MS");
					$("#solution_"+runId+">.remoteRunId").text(s[13]);
					$("#solution_"+runId+">.judgeTime").text(new Date().ojFormat());
					if(status != STATUS_PEDDING  && status != STATUS_PROCESSING)
						delete I.syncStatusList[runId];
				}
				clearTimeout(I.syncHandle);
				if(responseCount!=queryCount){
					I.showMessage("Server response and local query does not match!");
				}else{
					I.syncHandle=setTimeout(function(){I.syncStatus();},500);
				}
			}else{
				I.showMessage(d.message);
			}
		},"json"
	);
};
OJ.prototype.statusDescriptionHTML=function(status,statusDescription,runId){
	if(status==STATUS_PEDDING){//pedding
		return '<span class="badge">'+statusDescription+'</span>';
	}else if(status==STATUS_PROCESSING){//processing
		return '<div class="progress progress-striped active"><div class="bar" style="width:100%">'+statusDescription+'</div></div>';
	}else if(status==STATUS_JUDGE_ERROR){//judge error
		return '<span class="badge badge-warning" title="'+$.t("Last Status Description: ")+statusDescription+'">'+
		$.t("judgeError")+' <i onclick="oj.rejudge('+runId+')" class="icon-repeat icon-white" style="cursor: pointer;" title="'+$.t("Click here to rejudge.")+'"></i></span>';
	}else if(status==STATUS_ACCEPTED){//accepted
		return '<span class="badge badge-success">'+statusDescription+'</span>';
	}else if(status==STATUS_COMPLIE_ERROR){
		return '<span class="badge badge-info" onclick="oj.loadSolutionInfo('+runId+')" style="cursor: pointer;"  title="'+$.t("Click here see detail.")+'">'+statusDescription+' <i class="icon-white icon-info-sign"></i></span>';
	}else{
		return '<span class="badge badge-important">'+statusDescription+'</span>';
	}
};
OJ.prototype.loadSolutionInfo=function(runId){
	var I = this;
	$.post(
		baseUrl + "/problems/json/status-info.action",{
			runId:runId
		},function(d){
			if(d.code==0){
				I.showMessage("<pre>"+d.additionalInformation+"</pre>");
			}else{
				I.showMessage(d.message);
			}
		},"json"
	);
	
};
OJ.prototype.loadStatus=function(sortable){
	var I = this;
	sortable=false;
	I.syncStatusList={};
	var $statusTable = $('#status').dataTable({
		"sDom": '<"H">t<"F"pr>',
		"bProcessing": true,
		"bServerSide": true,
		"iDisplayLength": 20,
		"bSortable": sortable,
	//	"bStateSave":true,
		"oLanguage": {
			"sInfo": "_START_ to _END_ of _TOTAL_ status",//TODO replace to _("xxx")
			"sInfoEmpty": "No status",
			"sInfoFiltered": " (filtering from _MAX_ total status)"
		},
		//"aaSorting": [[ 0, "desc" ]],
		"sAjaxSource": baseUrl + "/problems/json/status.action",
		"fnServerData": function ( sSource, _aoData, fnCallback ) {
			var aoData={};
			for(var i in _aoData){
				aoData[_aoData[i].name]=_aoData[i].value;
			}
			var orderIndex=aoData.iSortCol_0;
			if(aoData.sSortDir_0=="desc")
				orderIndex=-orderIndex;
			var $form=$("#status_form")[0];
			$.post( sSource, {
				orderByIndex:orderIndex,
				username:$form.username.value,
				language:$form.language.value,
				problemNumber:$form.problemNumber.value,
				statusCode:$form.statusCode.value,
				judgerSource:$form.judgerSource.value,
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
				"sClass": "runId"//runId
			},{
				"fnRender": function ( oObj ) {
					return "<a href='"+baseUrl + "/show-user.action#?username="+oObj.aData[1].xss()+"'>"+oObj.aData[1].xss()+"</a>";
				},
				"bSortable": false,
				"sClass": "username"//username
			},{
				"bSortable": false,
				"fnRender": function ( oObj ) {
					return "<a href='"+baseUrl + "/problems/view.action#?problemId="+oObj.aData[9]+"'>"+oObj.aData[2].xss()+"</a>";
				},
				"sClass": "problem"//problem
			},{
				"bSortable": false,
				"sClass": "statusDescription",//status description,
				"fnRender": function ( oObj ) {
					var status = oObj.aData[11],statusDescription = oObj.aData[3].xss(),runId = oObj.aData[0];
					return I.statusDescriptionHTML(status, statusDescription, runId);
				}
			},{
				"bSortable": sortable,
				"sClass": "memory",//memory
				"fnRender": function ( oObj ) {
					return oObj.aData[4]+"KB";
				}
			},{
				"bSortable": sortable,
				"fnRender": function ( oObj ) {
					return oObj.aData[5]+"MS";
				},
				"sClass": "time"//time
			},{
				"bSortable": false,
				"sClass":"language",//language
				"fnRender":function(oObj){
					var cssClass="";
					if(oObj.aData[14]){
						cssClass="shared";
					}
					if(oObj.aData[1].replace(/^.+?>(.+?)<.+?$/,"$1") == LOGIN_USERNAME || oObj.aData[14])
						return "<a class='"+cssClass+"' href='"+baseUrl+"/show-source-code.action?runId="+oObj.aData[0]+"'>"+oObj.aData[6]+"</a>";
					return oObj.aData[6];
				}
			},{
				"bSortable": sortable,
				"fnRender": function ( oObj ) {
					return oObj.aData[7]+"B";
				},
				"sClass":"codeLength"//code length
			},{
				"bSortable": false,
				"fnRender": function ( oObj ) {
					return new Date(oObj.aData[8]).ojFormat();
				},
				"sClass":"submitTime"//submit time
			}
		],
//		"bJQueryUI": true,
		"sPaginationType": "full_numbers",
		"fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {
			$(nRow).attr("id", "solution_"+aData[0]);
			if(aData[11]==2||aData[11]==1){//processing & pedding
				I.addSyncStatus(aData[0]);
			}
			return nRow;
		},
		"fnDrawCallback":function(oSettings){
			I.syncStatus();
		}
	} );
	$("#status_form").submit(function(){
		$statusTable.fnDraw();
		return false;
	});
};

OJ.prototype.loadContestList=function(){
	var $contestList=$('#contestList').dataTable( {
		"sDom": '<"H"f>t<"F"iplr>',
		"bProcessing": true,
		"bServerSide": true,
		"iDisplayLength": 20,
		"bStateSave":true,
		"oLanguage": {
			"sInfo": "_START_ to _END_ of _TOTAL_ contests, &nbsp;",//TODO replace to _("xxx")
			"sInfoEmpty": "No contests",
			"sInfoFiltered": " (filtering from _MAX_ total contests)"
		},
		"aaSorting": [[ 1, "desc" ]],
		"sAjaxSource": baseUrl + "/contests/json/list.action",
		"fnServerData": function ( sSource, _aoData, fnCallback ) {
			var aoData={},contestStatus = 0;
			$("#contestList_extraFilter .active").each(function(){
				contestStatus|=$(this).attr("data-value");
			});
			for(var i in _aoData){
				aoData[_aoData[i].name]=_aoData[i].value;
			}
			var orderIndex=aoData.iSortCol_0+1;
			if(aoData.sSortDir_0=="desc")
				orderIndex=-orderIndex;
			$.post( sSource, {
				orderByIndex:orderIndex,
				contestType:-1,
				filterString:aoData.sSearch,
				page:aoData.iDisplayStart/aoData.iDisplayLength,
				pageSize:aoData.iDisplayLength,
				contestStatus:contestStatus
			}, function (json) {
				for(var i = 0;i < json.data.length;i++){
					json.data[i].push("");
				}
				fnCallback({
					sEcho:aoData.sEcho,
					iTotalDisplayRecords:json.totalCount,
					iTotalRecords:json.allContestsCount,
					aaData:json.data
				});
			},"json" );
		},
		"aoColumns": [{
				"sClass": ""//ID
			},{
				"fnRender": function ( oObj ) {
					return "<a href='"+baseUrl + "/contests/view.action#?id="+oObj.aData[0]+"'>"+oObj.aData[1]+"</a>";
				},
				"sClass": "title"//Title
			},{
				"fnRender": function ( oObj ) {
					return new Date(oObj.aData[2]).ojFormat();
				},
				"sClass": ""//Start Time
			},{
				"fnRender": function ( oObj ) {
					function _0(x){
						return x<10?"0"+x:x;
					}
					var ret = "",length = oObj.aData[3];
					if(length / (1000*3600*24)>=1){
						ret += parseInt(length / (1000*3600*24)) + " " + $.t("Days") + " ";
						length  = length % (1000*3600*24);
					}
					ret += _0(parseInt(length / (1000*3600))) + ":";
					length  = length % (1000*3600);
					
					ret += _0(parseInt(length / (1000*60))) + ":";
					length  = length % (1000*60);
					
					ret += _0(parseInt(length / (1000)));
					length  = length % (1000);
					return ret;
				},
				"bSortable": false,
				"sClass": ""//Length
			},{
				"fnRender": function ( oObj ) {
					return contestTypeDescription[oObj.aData[4]];
				},
				"sClass": ""//Type
			},{
				"fnRender": function ( oObj ) {
					return "<a href='"+baseUrl + "/show-user.action#?username="+oObj.aData[5].xss()+"'>"+oObj.aData[5].xss()+"</a>";
				},
				"bSortable": false,
				"sClass": ""//Host User
			},{
				"fnRender": function ( oObj ) {
					if(oObj.aData[1].replace(/^.+?>(.+?)<.+?$/,"$1") == LOGIN_USERNAME || USER_PERMISSION&PERMISSION_ADMIN_PRIVILEGE == PERMISSION_ADMIN_PRIVILEGE)
						return "<a href='#' data-id='"+oObj.aData[0]+"' class='icon-edit' title='"+$.t("Edit")+"'></a> <a href='#' data-id='"+oObj.aData[0]+"' class='icon-trash' title='"+$.t("Delete")+"'></a>";
					return "";//TODO contest mangement
				},
				"bSortable": false,
				"sClass": "options"// operation
			}
		],
		"sPaginationType": "full_numbers",
		"bJQueryUI": true,
		"fnDrawCallback": function( oSettings ) {
			$(".options>.icon-edit").click(function(){
				var contestId=$(this).attr("data-id");
				console.log('edit contest #'+contestId);
			});
			$(".options>.icon-trash").click(function(){
				var contestId=$(this).attr("data-id");
				console.log('delete contest #'+contestId);
			});
			if(LOGIN_USERNAME!=""){
				$(".options").show();
			}else{
				$(".options").hide();
			}
		}
	} );
	$("#contestList_filter").before(
		'<div class="dataTables_info" data-toggle="buttons-checkbox" id="contestList_extraFilter">'+
			'<div class="btn-group">'+
				'<button data-value="'+LIST_PUBLIC+'" type="button" class="btn btn-mini">'+$.t("Public")+'</button>'+
				'<button data-value="'+LIST_PRIVATE+'" type="button" class="btn btn-mini">'+$.t("Private")+'</button>'+
				'<button data-value="'+LIST_REGISTERATION+'" type="button" class="btn btn-mini">'+$.t("Registeration")+'</button>'+
				'<button data-value="'+LIST_REPLAY+'" type="button" class="btn btn-mini">'+$.t("Replay")+'</button>'+
			'</div>'+
			'<div class="btn-group">'+
				'<button data-value="'+LIST_RUNNING+'" type="button" class="btn btn-mini">'+$.t("Running")+'</button>'+
				'<button data-value="'+LIST_PASSED+'" type="button" class="btn btn-mini">'+$.t("Passed")+'</button>'+
				'<button data-value="'+LIST_PEDDING+'" type="button" class="btn btn-mini">'+$.t("Pedding")+'</button>'+
			'</div>'+
		'</div>'
	);
	$("#contestList_extraFilter button").click(function(){
		setTimeout(function(){$contestList.fnDraw();},200);// delay for 
	});
};
var oj;
oj = new OJ();
