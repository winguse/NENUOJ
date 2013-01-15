<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://htmlcompressor.googlecode.com/taglib/compressor" prefix="cp" %>
<cp:html enabled="${site.enableHtmlCompress}" removeIntertagSpaces="true">
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
<head>
<sj:head />
</head>
<body>
<a href="?request_locale=zh_CN">cn</a>
<a href="?request_locale=en_US">en</a>
<h2><s:property value="%{getText('helloWorld')}"/></h2>
<s:text name="message"></s:text>
<s:property value="message"/>
</body>
</html>
</cp:html>