<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://htmlcompressor.googlecode.com/taglib/compressor" prefix="c"%>
<c:html enabled="${site.enableHtmlCompress}" removeIntertagSpaces="true">
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-jquery-tags" prefix="j"%>
<%@ taglib uri="/struts-bootstrap-tags" prefix="b"%>
<s:include value="include/init.jsp"></s:include>
<s:set var="pageTitle" value="getText('home')"/>
<title><s:property value="#pageTitle"/> - <s:text name="site.title"/></title>
<s:set var="pageTitle" value="_('site.title')"/>
<s:include value="include/header.jsp"></s:include>
<p><s:text name="site.description"/></p>
<p><s:text name="site.supportedvoj"/></p>
<s:iterator value="site.supportedVOJ">
  <a href="<s:property/>"><s:property/></a>
</s:iterator>
<p><s:text name="site.statics"/></p>
<s:include value="include/footer.jsp"></s:include>
</c:html>