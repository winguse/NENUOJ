<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-bootstrap-tags" prefix="b"%>
</head>
<body>
<header>
<div class="navbar navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container">
			<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</a>
			<a class="brand" href="#">NENU OJ</a>
			<div class="nav-collapse">
				<ul class="nav">
					<li><s:a namespace="/" action="home"><s:text name="home" /></s:a></li>
					<li><s:a namespace="/problems" action="list"><s:text name="problems" /></s:a></li>
					<li><s:a namespace="/problems" action="status"><s:text name="status" /></s:a></li>
					<li><s:a namespace="/contests" action="list"><s:text name="contests" /></s:a></li>
					<s:if test="#session.user==null">
						<script>var LOGIN_USERNAME="",USER_PERMISSION = 0;</script>
						<li><s:a namespace="/" action="register"><s:text name="register" /></s:a></li>
						<div id="login_dialog" title="<s:text name="login"/>" class="hide">
						<s:form id="login_form" namespace="/" action="login" theme="bootstrap" cssClass="form">
							<p class="validateTips"></p>
							<s:textfield label="%{_('username')}" name="username" />
							<s:password label="%{_('password')}" name="password" />
						</s:form>
						</div>
						<li><a id="login" href="#"><s:text name="login" /></a></li>
					</s:if>
					<s:else>
						<script>var LOGIN_USERNAME="<s:property value="#session.user.username"/>",USER_PERMISSION = <s:property value="#session.user.permission"/>;</script>
						<li><s:a namespace="/" action="user-profiles">
							<s:text name="welcome" />
							<s:property value="#session.user.username"/>
						</s:a></li>
						<li><a href="#" id="logout" onclick="oj.logout();"><s:text name="logout" /></a></li>
					</s:else>
				</ul>
			</div>
		<!--/.nav-collapse -->
		</div>
	</div>
</div>
</header>
<div class="container">
<s:if test="%{ #pageTitle != null && #pageTitle != '' }">
<h1><s:property value="#pageTitle"/></h1>
</s:if>