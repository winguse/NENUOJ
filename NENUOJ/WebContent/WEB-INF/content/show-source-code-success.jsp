<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://htmlcompressor.googlecode.com/taglib/compressor" prefix="c"%>
<c:html enabled="${site.enableHtmlCompress}" removeIntertagSpaces="true">
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-bootstrap-tags" prefix="b"%>
<s:include value="include/init.jsp"></s:include>
<title><s:text name="showSourceCode" /> - <s:text name="site.title"/></title>
<s:set var="pageTitle" value=""/>
<s:include value="include/header.jsp"></s:include>
<h1><s:property value="solution.username"/> <s:text name="solution for"/>
<a href="problems/view.action#?problemId=<s:property value="solution.problemId"/>" title="<s:property value="solution.problemTitle"/>">
<s:property value="solution.judgerSource"/> <s:property value="solution.prublemNumber"/></a>
</h1>
<table class="table">
<thead>
	<tr>
		<td><s:text name="local run id"/></td>
		<td><s:text name="remote run id"/></td>
		<td><s:text name="status"/></td>
		<td><s:text name="time"/></td>
		<td><s:text name="memory"/></td>
		<td><s:text name="language"/></td>
		<td><s:text name="codelength"/></td>
		<td><s:text name="submittime"/></td>
		<td><s:text name="judgetime"/></td>
		<td><s:text name="passrate"/></td>
		<s:if test="solution.contestId!=0">
		<td><s:text name="contest"/></td>
		</s:if>
		<s:if test="adminPrivilege">
		<td><s:text name="ipaddress"/></td>
		</s:if>
		<td><s:text name="shared"/></td>
	</tr>
</thead>
<tbody>
	<tr>
		<td><s:property value="soltion.runId"/></td>
		<td><s:property value="solution.remoteRunId"/></td>
		<td><s:property value="solution.statusDescription"/></td><!-- TODO use javascript to display css -->
		<td><s:property value="solution.time"/>MS</td>
		<td><s:property value="solution.memory"/>KB</td>
		<td><s:property value="soluton.language"/></td>
		<td><s:property value="solution.codeLength"/></td>
		<td><s:property value="solution.submitTime"/></td>
		<td><s:property value="solution.judgeTime"/></td>
		<td><s:property value="solution.passrate"/></td>
		<s:if test="solution.contestId!=0">
		<td><s:property value="solution.contest.title "/></td><%-- TODO contest information --%>
		</s:if>
		<s:if test="adminPrivilege">
		<td><s:property value="solution.ipAddress"/></td>
		</s:if>
		<td><s:property value="solution.shared"/></td><!-- TODO use javascript to display css -->
	</tr>
</tbody>
</table>
<pre class="prettyprint linenums"><s:property value="solution.sourceCode"/></pre>
<s:if test="solution.additionalInformation!=null&&solution.additionalInformation!=''&&solution.additionalInformation!='Undefined'">
<h4><s:text name="additionalInformation" /></h4>
<pre class="prettyprint linenums"><s:property value="solution.additionalInformation"/></pre>
</s:if>
<s:text name="discuss" /> <s:property value="solution.message.replyCount"/> TODO load discuss
<script>$(function(){window.prettyPrint && prettyPrint();});</script>
<s:include value="include/footer.jsp"></s:include>
</c:html>