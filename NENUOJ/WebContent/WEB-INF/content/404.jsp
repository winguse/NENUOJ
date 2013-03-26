<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://htmlcompressor.googlecode.com/taglib/compressor" prefix="c"%>
<c:html enabled="${site.enableHtmlCompress}" removeIntertagSpaces="true">
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-bootstrap-tags" prefix="b"%>
<s:include value="include/init.jsp"></s:include>
<s:set var="pageTitle" value="getText('404')"/>
<title><s:property value="#pageTitle"/> - <s:text name="site.title"/></title>
<s:include value="include/header.jsp"></s:include>
TODO sell cute here
<s:include value="include/footer.jsp"></s:include>
</c:html>
