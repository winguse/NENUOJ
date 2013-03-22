<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://htmlcompressor.googlecode.com/taglib/compressor" prefix="c"%>
<c:html enabled="${site.enableHtmlCompress}" removeIntertagSpaces="true">
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-bootstrap-tags" prefix="b"%>
<s:include value="../include/init.jsp"></s:include>
<s:set var="pageTitle" value="getText('problem-detial')"/>
<title><s:property value="#pageTitle"/> - <s:text name="site.title"/></title>
<s:include value="../include/header.jsp"></s:include>
<div class="progress progress-striped active">
  <div class="bar" style="width: 10%;"></div>
</div>
<figure class="problem_limit">
	<strong class="time_limit"><s:text name="time_limit"/></strong><span></span>
	<strong class="memory_limit"><s:text name="memory_limit"/></strong><span></span>
	<strong class="accepted_submited"><s:text name="accepted_submited"/></strong><span></span>
	<strong class="long_int_format"><s:text name="long_int_format"/></strong><span></span>
</figure>
<div class="row">
<div class="span9">
	<section class="description"><h2><s:text name="description"/></h2><div class="well"></div></section>
	<section class="input"><h2><s:text name="input"/></h2><div class="well"></div></section>
	<section class="output"><h2><s:text name="output"/></h2><div class="well"></div></section>
	<section class="sampleIn"><h2><s:text name="sampleIn"/></h2><pre class="prettyprint linenums"></pre></section>
	<section class="sampleOut"><h2><s:text name="sampleOut"/></h2><pre class="prettyprint linenums"></pre></section>
	<section class="source"><h2><s:text name="source"/></h2><div class="well"></div></section>
	<section class="hint"><h2><s:text name="hint"/></h2><div class="well"></div></section>
	<section class="control pagination-centered">
		<div class="btn-group">
			<a class="btn" href="#" id="submit_btn"><s:text name="submit" /></a>
			<a class="btn" href="#"><s:text name="status" /></a>
			<a class="btn" href="#"><s:text name="discuss" /></a>
		</div>
	</section>
</div>
<div class="span3">
	<h4><s:text name="description_list"/></h4>
	<ul class="nav nav-tabs nav-stacked" id="description_list">
	</ul>
</div>
</div>
<div id="problem_submit" title="<s:text name="submit_your_solution" />" class="hide">
<s:form id="problem_submit_form" namespace="/problems/json" action="submit" theme="bootstrap" cssClass="form">
	<p class="validateTips"></p>
	<input type="hidden" id="problemId" name="problemId" value=""/>
	<s:select label="%{_('language')}" id="language" name="language" list="{}"></s:select>
	<s:textarea label="%{_('source_code')}" name="sourceCode" />
</s:form>
</div>
<script>
$(function(){
	oj.loadProblem();
	$(window).hashchange(function(){
		oj.loadProblem();
	});
	<s:if test="#session.user!=null">
	$("#problem_submit").dialog({
		modal:true,
		autoOpen: false,
		buttons:{
			'<s:text name="submit"/>':function(){
				$(this).find('form').submit();
			},'<s:text name="cancle"/>':function() {
				$(this).find('p.validateTips').html('');
				$(this).dialog('close');
			}
		}
	});
	var $form=$("#problem_submit_form");
	WinguseAjaxForm($form, function(data) {
		$form.find("p.validateTips").text(data.message);
		if (data.code == 0)
			setTimeout(function() {
			//	window.location.reload(); TODO
				alert(d.message);
			}, 1000);
	});
	</s:if>
	$("#submit_btn").click(function(){
	<s:if test="#session.user==null">
		$("#login_dialog").dialog("open");
	</s:if><s:else>
		$("#problem_submit").dialog("open");
	</s:else>
		return false;
	});
});
</script>
<s:include value="../include/footer.jsp"></s:include>
</c:html>