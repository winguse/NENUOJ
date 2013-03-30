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
<div class="span6">
	<div class="row">
		<div class="span1"><s:text name="Judger Source"/></div>
		<div class="span1"><s:text name="Problem Number"/></div>
		<div class="span4"><s:text name="Description"/></div>
	</div>
	<div class="row">
		<div class="span1"><s:text name="Judger Source"/></div>
		<div class="span1"><s:text name="Problem Number"/></div>
		<div class="span4"><s:text name="Description"/></div>
	</div>
	<div class="row">
		<div class="span1"><s:text name="Judger Source"/></div>
		<div class="span1"><s:text name="Problem Number"/></div>
		<div class="span4"><s:text name="Description"/></div>
	</div>
</div>
</div>
</form>
<script>
$(function(){
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