<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
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
			<a href="#" id="login"><s:text name="login" /></a>
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