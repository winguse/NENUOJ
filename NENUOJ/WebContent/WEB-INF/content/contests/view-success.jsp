<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://htmlcompressor.googlecode.com/taglib/compressor" prefix="c"%>
<c:html enabled="${site.enableHtmlCompress}" removeIntertagSpaces="true">
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-bootstrap-tags" prefix="b"%>
<s:include value="../include/init.jsp"></s:include>
<s:set var="pageTitle" value="getText('A Contest')"/>
<title><s:text name="site.title"/> - <s:property value="#pageTitle"/></title>
<s:include value="../include/header.jsp"></s:include>
<div id="contestTimeSlider"></div>
<div id="contestTabs">
	<ul>
		<li><a href="#overview"><s:text name="Overview"/></a></li>
		<li><a href="#problems"><s:text name="Problems"/></a></li>
		<li><a href="#status"><s:text name="Status"/></a></li>
		<li><a href="#rank"><s:text name="Rank"/></a></li>
		<li><a href="#discuss"><s:text name="Discuss"/></a></li>
	</ul>
	<div id="overview"></div>
	<div id="problems"></div>
	<div id="status"></div>
	<div id="rank"></div>
	<div id="discuss"></div>
</div>
<script>
$(function(){
	oj.loadContest();
});
</script>
<s:include value="../include/footer.jsp"></s:include>
</c:html>