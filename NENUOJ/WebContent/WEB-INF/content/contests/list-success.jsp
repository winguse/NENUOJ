<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://htmlcompressor.googlecode.com/taglib/compressor" prefix="c"%>
<c:html enabled="${site.enableHtmlCompress}" removeIntertagSpaces="true">
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-bootstrap-tags" prefix="b"%>
<s:include value="../include/init.jsp"></s:include>
<s:set var="pageTitle" value="getText('contests')"/>
<title><s:text name="site.title"/> - <s:property value="#pageTitle"/></title>
<s:include value="../include/header.jsp"></s:include>

<table id="contestList">
	<thead>
		<tr>
			<th><s:text name="ID"/></th>
			<th><s:text name="Title"/></th>
			<th><s:text name="Start Time"/></th>
			<th><s:text name="Length"/></th>
			<th><s:text name="Type"/></th>
			<th><s:text name="Manager"/></th>
			<th class="options"><s:text name="Options"/></th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td colspan="7">Loading data from server</td>
		</tr>
	</tbody>
</table>
<script>
$(function(){
	oj.loadContestList();
});
</script>

<s:include value="../include/footer.jsp"></s:include>
</c:html>