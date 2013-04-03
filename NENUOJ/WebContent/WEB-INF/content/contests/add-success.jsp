<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://htmlcompressor.googlecode.com/taglib/compressor" prefix="c"%>
<c:html enabled="${site.enableHtmlCompress}" removeIntertagSpaces="true">
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-bootstrap-tags" prefix="b"%>
<s:include value="../include/init.jsp"></s:include>
<link href="${pageContext.request.contextPath}/css/jquery-ui-timepicker-addon.css" rel="stylesheet">
<s:set var="pageTitle" value="getText('Add Contest')"/>
<title><s:text name="site.title"/> - <s:property value="#pageTitle"/></title>
<s:include value="../include/header.jsp"></s:include>
<s:actionerror theme="bootstrap"/>
<s:actionmessage theme="bootstrap"/>
<s:fielderror theme="bootstrap"/>
<form id="contest_add_form" action="add" class="form-horizontal">
<div class="row">
<div class="span6">
	<h3><s:text name="Contest Basic Information"/></h3>
	<div class="control-group">
		<label class="control-label" for="contestTitle"><s:text name="Contest Title"/></label>
		<div class="controls">
			<input type="text" class="span3" name="contestTitle" id="contestTitle" placeholder="<s:text name="Contest Title"/>">
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="startTime"><s:text name="Start Time"/></label>
		<div class="controls">
			<input type="datetime" class="span3" name="startTime" id="startTime" placeholder="<s:text name="Start Time"/>">
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="endTime"><s:text name="End Time"/></label>
		<div class="controls">
			<input type="datetime" class="span3" name="endTime" id="endTime" placeholder="<s:text name="End Time"/>">
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="contestType"><s:text name="Contest Type"/></label>
		<div class="controls">
			<select name="contestType" class="span3" id="contestType">
				<option value="<s:property value="@vs@CONTEST_TYPE_PUBLIC"/>"><s:text name="Public"/></option>
				<option value="<s:property value="@vs@CONTEST_TYPE_PRIVATE"/>"><s:text name="Private"/></option>
				<option value="<s:property value="@vs@CONTEST_TYPE_REGISTRATION_NEEDED"/>"><s:text name="Registration Needed"/></option>
			</select>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="description"><s:text name="Description"/></label>
		<div class="controls">
			<textarea name="description" class="span3" id="description" placeholder="<s:text name="Description"/>"></textarea>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="announcement"><s:text name="Announcement"/></label>
		<div class="controls">
			<textarea name="announcement" class="span3" id="announcement" placeholder="<s:text name="Announcement"/>"></textarea>
		</div>
	</div>
</div>
<div class="span6" id="addProblemList">
	<h3><s:text name="Contest Problems"/> <i class="icon-plus-sign pointer" title="<s:text name="Add A Problem Field"/>"></i></h3>
</div>
</div>
<div class="row">
	<div class="form-actions">
		<input type="submit" value="<s:text name="Submit"/>" class="btn btn-primary">
	</div>
</div>
</form>
<script>
function AddProblem(judgerList,elementId){
	var I = this,opt="";
	I.judgerList = judgerList;
	elementId = elementId || "addProblemList";
	I.$container = $("#"+elementId);
	I.id="problem_"+parseInt(Math.random()*(1<<30));
	for(var i in I.judgerList){
		opt += '<option value="'+I.judgerList[i]+'">'+I.judgerList[i]+'</option>';
	}
	I.$container.append('<div class="row" id="'+I.id+'"><select class="span1 inline" name="judgerSource">'+
			opt+'</select><input value="" type="text" class="span1 inline" name="problemNumber" placeholder="<s:text name="Problem Number"/>">'+
			'<select class="span4 inline" name="problemDescription"><option value=""><s:text name="Enter Problem Number First"/></option></select> <i class="icon-trash pointer"></i></div>');
	I.$element = $("#"+I.id);
	I.$judgerSource = I.$element.find("select[name='judgerSource']");
	I.$problemNumber = I.$element.find("input[name='problemNumber']");
	I.$problemDescription = I.$element.find("select[name='problemDescription']");
	I.$delete = I.$element.find("i[class*='icon-trash']");
	I.$delete.click(function(){I.destory();return false;});
	var keyUpTimeoutHandle = 0;
	I.$problemNumber.keyup(function(){
		clearTimeout(keyUpTimeoutHandle);
		keyUpTimeoutHandle = setTimeout(function(){
			I.problemNumberOnchange();
		},500);
	});
	I.list[I.id] = I;
	I.problemNumerValide = false;
}
AddProblem.prototype.$container = null;
AddProblem.prototype.getJudgerSource = function(){
	return this.$judgerSource[0].value;
};
AddProblem.prototype.getProblemNumber = function(){
	if(this.problemNumerValide)
		return I.$problemNumber[0].value;
	return "";
};
AddProblem.prototype.getDescription = function(){
	if(this.problemNumerValide)
		return I.$problemDescription[0].value;
	return "";
	
};
AddProblem.prototype.problemNumberOnchange = function(){
	var I = this;
	I.problemNumerValide = false;
	var problemNumber = I.$problemNumber[0].value,judgerSource =  I.$judgerSource[0].value;
	if(problemNumber == ""){
		I.$problemDescription.html('<option value=""><s:text name="Enter Problem Number First"/></option>');
		return;
	}
	$.post(baseUrl+"/contests/json/problem-description-list.action",{
		problemNumber:problemNumber,
		judgerSource:judgerSource
		},function(d){
			var l = d.problemDescriptionList;
			if(l.length == 0){
				I.$problemDescription.html('<option value=""><s:text name="Problem Not Found.."/></option>');
				//TODO add crawl
			}else{
				var optHtml = "",lockedHtml ="";
				for(var i = 0; i<l.length; i++){
					var html = '<option value="'+l[i].id+'" title="Version Mark:'+l[i].versionMark+'\nLast update:'
						+new Date(l[i].lastupdate).ojFormat()+'">'+l[i].title+' @'+l[i].username+'</option>';
					if(l[i].locked)
						lockedHtml += html;
					else
						optHtml += html;
						
				}
				if(lockedHtml!="")
					I.$problemDescription.html('<optgroup label="<s:text name="Normal Descriptions"/>">'+optHtml+
						'</optgroup><optgroup label="<s:text name="Locked Descriptions"/>">'+lockedHtml+'</optgroup>');
				else
					I.$problemDescription.html(optHtml);
			}
		},"json"
	);
};
AddProblem.prototype.destory = function(){
	delete this.list[this.id];
	this.$element.remove();
};
AddProblem.prototype.list = {};
$(function(){
	var judgerList=[];
	$.get(baseUrl+"/json/supported-judger.action",{},function(d){
		judgerList = d.judgerSourceList;
		new AddProblem(judgerList);
	},"json");
	$("#addProblemList .icon-plus-sign").click(function(){
		new AddProblem(judgerList);
		return false;
	});
	
	//-----------------
	var $startTime = $('#startTime');
	var $endTime = $('#endTime');

	$startTime.datetimepicker({ 
		onClose: function(dateText, inst) {
			if ($endTime.val() != '') {
				var testStartDate = $startTime.datetimepicker('getDate');
				var testEndDate = $endTime.datetimepicker('getDate');
				if (testStartDate > testEndDate)
					$endTime.datetimepicker('setDate', testStartDate);
			} else {
				$endTime.val(dateText);
			}
		},
		onSelect: function (selectedDateTime){
			$endTime.datetimepicker('option', 'minDate', $startTime.datetimepicker('getDate') );
		},
		dateFormat:"yy-mm-dd",
		timeFormat:"HH:mm:ss",
		separator:"T"
	});
	$endTime.datetimepicker({ 
		onClose: function(dateText, inst) {
			if ($startTime.val() != '') {
				var testStartDate = $startTime.datetimepicker('getDate');
				var testEndDate = $endTime.datetimepicker('getDate');
				if (testStartDate > testEndDate)
					$startTime.datetimepicker('setDate', testEndDate);
			} else {
				$startTime.val(dateText);
			}
		},
		onSelect: function (selectedDateTime){
			$startTime.datetimepicker('option', 'maxDate', $endTime.datetimepicker('getDate') );
		},
		dateFormat:"yy-mm-dd",
		timeFormat:"HH:mm:ss",
		separator:"T",
		defaultValue:"Now"
	});
});
</script>
<s:include value="../include/footer.jsp"></s:include>
</c:html>