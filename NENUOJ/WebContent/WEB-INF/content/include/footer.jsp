<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
</div>
<footer class="footer">
	<p><s:text name="footer.copyright"/> <s:text name="footer.servertime" /><span id="servertime"></span></p>
	<p>&copy;2012 <s:text name="association"/></p>
</footer>
<script src="${pageContext.request.contextPath}/js/jquery-ui-1.10.2.custom.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/js/i18next.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.ba-hashchange.min.js"></script>
<script src="${pageContext.request.contextPath}/js/prettify.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-ui-timepicker-addon.js"></script>
<script src="${pageContext.request.contextPath}/js/global.js"></script>
<script>
<s:if test="#session.user==null">
$("#login_dialog").dialog({
	modal:true,
	autoOpen: false,
	buttons:{
		'<s:text name="login"/>':function(){
			$(this).find('form').submit();
		},'<s:text name="cancle"/>':function() {
			$(this).find('p.validateTips').html('');
			$(this).dialog('close');
		}
	},
});
$("#login").click(function(){
	$("#login_dialog").dialog("open");
	return false;
});
oj.loginRequired();
</s:if>
</script>
</body>
</html>