<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://htmlcompressor.googlecode.com/taglib/compressor" prefix="c"%>
<c:html enabled="${site.enableHtmlCompress}" removeIntertagSpaces="true">
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-jquery-tags" prefix="j"%>
<%@ taglib uri="/struts-bootstrap-tags" prefix="b"%>
<s:include value="include/init.jsp"></s:include>
<s:set var="pageTitle" value="getText('register')"/>
<title><s:text name="site.title"/> - <s:property value="#pageTitle"/></title>
<s:include value="include/header.jsp"></s:include>
<s:actionerror theme="bootstrap"/>
<s:actionmessage theme="bootstrap"/>
<s:fielderror theme="bootstrap"/>

<s:form id="register_form" action="register-submit" theme="bootstrap" cssClass="form-horizontal">
	<s:textfield label="%{getText('username')}" name="username" tooltip="%{getText('tooltip.username')}" />
	<s:password label="%{getText('password')}" name="password" tooltip="%{getText('tooltip.password')}" />
	<s:password label="%{getText('password2')}" name="password2" tooltip="%{getText('tooltip.password2')}" />
<div class="form-actions">
	<j:submit cssClass="btn btn-primary" formIds="register_form" validate="true" validateFunction="bootstrapValidation"/>
</div>
</s:form>


<s:include value="include/footer.jsp"></s:include>
</c:html>