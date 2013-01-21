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
<s:form id="register_form" action="register-operating" theme="bootstrap" cssClass="form-horizontal">
	<s:textfield label="%{getText('username')}" name="username" tooltip="%{getText('tooltip.username')}" />
	<s:password label="%{getText('password')}" name="password" tooltip="%{getText('tooltip.password')}" />
	<s:password label="%{getText('password2')}" name="password2" tooltip="%{getText('tooltip.password2')}" />
	<s:textfield label="%{getText('email')}" name="email" tooltip="%{getText('tooltip.email')}" />
	<s:textfield label="%{getText('nickname')}" name="nickname" tooltip="%{getText('tooltip.nickname')}" />
	<s:textfield label="%{getText('school')}" name="school" tooltip="%{getText('tooltip.school')}" />
	<s:textfield label="%{getText('major')}" name="major" tooltip="%{getText('tooltip.major')}" />
	<s:textfield label="%{getText('grade')}" name="grade" tooltip="%{getText('tooltip.grade')}" />
	<s:if test="%{site.enableRegisterVerifyCode == false}">
	<s:textfield label="%{getText('verify_code')}" name="verify_code" tooltip="%{getText('tooltip.verify_code')}"><img /></s:textfield>
	</s:if>
	<s:checkbox label="%{getText('agree_terms')}" name="agree" tooltip="%{getText('tooltip.agree')}"/>
	<p><s:a action="terms"><s:text name="service_terms"/></s:a></p>
<div class="form-actions">
<%--
	As the jquery's orginal submit send twice http request for a success, I decided to implement myself.  
	<j:submit cssClass="btn btn-primary" formIds="register_form" validate="true" validateFunction="bootstrapValidation"/>
--%>
	<s:submit id="register_form_submit" value='%{_("submit")}' cssClass="btn btn-primary" />
<script>
$(function(){
	OJ.prototype.WinguseAjaxForm("#register_form",function(data){
		if(data.code==0)
			alert(data.message);
	});
});
</script>
</div>
</s:form>
<s:include value="include/footer.jsp"></s:include>
</c:html>