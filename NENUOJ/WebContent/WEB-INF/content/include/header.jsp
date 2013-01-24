<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-jquery-tags" prefix="j"%>
<%@ taglib uri="/struts-bootstrap-tags" prefix="b"%>
</head>
<body>
<header>
	<nav>
		<s:a namespace="/" action="home"><s:text name="home" /></s:a>
		<s:a namespace="problems" action="list"><s:text name="problems" /></s:a>
		<s:a namespace="/" action="status"><s:text name="status" /></s:a>
		<s:a namespace="contests" action="list"><s:text name="contests" /></s:a>
		<s:if test="#session.user==null">
			<s:a namespace="/" action="register"><s:text name="register" /></s:a>
	    	<j:dialog
				id="login_dialog"
				buttons="{'%{_('login')}':function(){$(this).find('form').submit();},'%{_('cancle')}':function() {$(this).find('p.validateTips').html('');$(this).dialog('close');}};oj.loginRequired()/*Small Hack*/"
				autoOpen="false"
				modal="true"
				title="%{_('login')}"
				resizable="true"
				draggable="true"
				cssClass="hide"
			>
			<s:form id="login_form" namespace="/" action="login" theme="bootstrap" cssClass="form">
				<p class="validateTips"></p>
				<s:textfield label="%{_('username')}" name="username" />
				<s:password label="%{_('password')}" name="password" />
			</s:form>
			</j:dialog>
	    	<j:a openDialog="login_dialog" id="login" href="#"><s:text name="login" /></j:a>
		</s:if>
		<s:else>
			<s:a action="user-profiles">
				<s:text name="welcome" />
				<s:property value="#session.user.username"/>
			</s:a>
			<a href="#" id="logout" onclick="oj.logout();"><s:text name="logout" /></a>
		</s:else>
	</nav>
	<h3><s:property value="#pageTitle"/></h3>
</header>
<div id="main">