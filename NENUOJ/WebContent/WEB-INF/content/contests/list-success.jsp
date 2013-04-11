<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://htmlcompressor.googlecode.com/taglib/compressor" prefix="c"%>
<c:html enabled="${site.enableHtmlCompress}" removeIntertagSpaces="true">
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-bootstrap-tags" prefix="b"%>
<s:include value="../include/init.jsp"></s:include>
<s:set var="pageTitle" value="getText('contests')"/>
<title><s:text name="site.title"/> - <s:property value="#pageTitle"/></title>
<s:include value="../include/header.jsp"></s:include>

<table id="problemList">
	<thead>
		<tr>
			<th><s:text name="judger_source"/></th>
			<th><s:text name="problem_number"/></th>
			<th><s:text name="problem_title"/></th>
			<th><s:text name="accepted"/></th>
			<th><s:text name="submitted"/></th>
			<th><s:text name="ac_rate"/></th>
			<th><s:text name="source"/></th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td colspan="8">Loading data from server</td>
		</tr>
	</tbody>
</table>


<s:include value="../include/footer.jsp"></s:include>
</c:html>