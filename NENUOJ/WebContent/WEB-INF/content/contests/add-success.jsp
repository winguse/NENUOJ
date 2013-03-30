<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://htmlcompressor.googlecode.com/taglib/compressor" prefix="c"%>
<c:html enabled="${site.enableHtmlCompress}" removeIntertagSpaces="true">
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-bootstrap-tags" prefix="b"%>
<s:include value="../include/init.jsp"></s:include>
<s:set var="pageTitle" value="getText('Add Contest')"/>
<title><s:text name="site.title"/> - <s:property value="#pageTitle"/></title>
<s:include value="../include/header.jsp"></s:include>
<s:actionerror theme="bootstrap"/>
<s:actionmessage theme="bootstrap"/>
<s:fielderror theme="bootstrap"/>
<s:form id="contest_add_form" action="add" namespace="/contest/json" theme="bootstrap" cssClass="form-inline">
<div class="row">
<div class="span6">
	<s:textfield label="%{getText('Contest Title')}" name="contestTitle" tooltip="%{getText('tooltip.Contest Title')}" />
</div>
<div class="span6">
	
</div>
</div>
</s:form>


<s:include value="../include/footer.jsp"></s:include>
</c:html>