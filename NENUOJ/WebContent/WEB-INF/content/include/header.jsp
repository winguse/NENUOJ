<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-jquery-tags" prefix="j"%>
<%@ taglib uri="/struts-bootstrap-tags" prefix="b"%>
</head>
<body>
<header>
	<nav>
		<s:a action="home"><s:text name="home" /></s:a>
		<s:a action="problems"><s:text name="problems" /></s:a>
		<s:a action="status"><s:text name="status" /></s:a>
		<s:a action="contests"><s:text name="contests" /></s:a>
		<s:if test="#session.user==null">
			<s:a action="register"><s:text name="register" /></s:a>
	    	<j:dialog
				id="login_dialog"
				buttons="{'%{_('login')}':function(){},'%{_('cancle')}':function() {$(this).dialog('close');}}"
				autoOpen="false"
				modal="true"
				title="%{_('login')}"
				resizable="true"
				draggable="true"
			>
			<s:form id="login_form" action="login" theme="bootstrap" cssClass="form">
				<s:textfield label="%{_('username')}" name="username" />
				<s:password label="%{_('password')}" name="password" />
			</s:form>
			</j:dialog>
	    	<j:a openDialog="login_dialog" id="login" href="#"><s:text name="login" /></j:a>
		</s:if>
		<s:else>
			<s:a action="user-profiles">
				<s:text name="welcome" />
				<s:property value="#session.user.nickname"/>
			</s:a>
			<a href="#" id="logout"><s:text name="logout" /></a>
		</s:else>
	</nav>
	<h3><s:property value="#pageTitle"/></h3>
</header>
<div id="main">