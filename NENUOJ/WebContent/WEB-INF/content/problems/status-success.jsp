<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://htmlcompressor.googlecode.com/taglib/compressor" prefix="c"%>
<c:html enabled="${site.enableHtmlCompress}" removeIntertagSpaces="true">
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-bootstrap-tags" prefix="b"%>
<s:include value="../include/init.jsp"></s:include>
<s:set var="pageTitle" value="getText('online-status')"/>
<title><s:property value="#pageTitle"/> - <s:text name="site.title"/></title>
<s:include value="../include/header.jsp"></s:include>
<div>
<form id="status_form" action="<s:url action="status" namespace="/problems/json"/>" class="form-inline">
<label for="username"><s:text name="username" /></label><input type="text" class="input-small" name="username" id="username" placeholder="<s:text name="username" />">
<label for="language"><s:text name="language" /></label><s:select list="languageList" cssClass="input-small" id="language" name="languge"></s:select>
<label for="judgerSource"><s:text name="judgerSource" /></label><s:select cssClass="input-small" list="judgerSourceList" name="judgerSource" id="judgerSource"></s:select>
<label for="problemId"><s:text name="problemId" /></label><input type="text" class="input-small" name="problemId" id="problemId" placeholder="<s:text name="problemId" />">
<label for="result"><s:text name="result" /></label><s:select list="statusMapping" cssClass="input-small" id="result" name="result"></s:select>
<input type="submit" value="<s:text name="filter" />" class="btn">
<input type="reset" value="<s:text name="reset" />" class="btn">
</form>
<table id="status">
	<thead>
		<tr>
			<th><s:text name="runId"/></th>
			<th><s:text name="user"/></th>
			<th><s:text name="problem"/></th>
			<th><s:text name="result"/></th>
			<th><s:text name="memory"/></th>
			<th><s:text name="time"/></th>
			<th><s:text name="language"/></th>
			<th><s:text name="length"/></th>
			<th><s:text name="submitTime"/></th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td colspan="8">Loading data from server</td>
		</tr>
	</tbody>
</table>
<script>
$(function(){
	oj.loadStatus();
});
</script>
</div>
<s:include value="../include/footer.jsp"></s:include>
</c:html>