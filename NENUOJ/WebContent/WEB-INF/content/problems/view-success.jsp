<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://htmlcompressor.googlecode.com/taglib/compressor" prefix="c"%>
<c:html enabled="${site.enableHtmlCompress}" removeIntertagSpaces="true">
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-jquery-tags" prefix="j"%>
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
			<j:a cssClass="btn" openDialog="problem_submit" href="#"><s:text name="submit" /></j:a>
			<j:a cssClass="btn" href="#"><s:text name="status" /></j:a>
			<j:a cssClass="btn" href="#"><s:text name="discuss" /></j:a>
		</div>
	</section>
</div>
<div class="span3">
	<h4><s:text name="description_list"/></h4>
	<ul class="nav nav-tabs nav-stacked" id="description_list">
	</ul>
</div>
</div>
<j:dialog
	id="problem_submit"
	buttons="{'%{_('submit')}':function(){$(this).find('form').submit();},'%{_('cancle')}':function() {$(this).find('p.validateTips').html('');$(this).dialog('close');}}/*Hack here*/"
	autoOpen="false"
	modal="true"
	title="%{_('submit_your_solution')}"
	resizable="true"
	draggable="true"
	cssClass="hide"
>
<s:form id="problem_submit_form" namespace="/problem/json" action="submit" theme="bootstrap" cssClass="form">
	<p class="validateTips"></p>
	<s:select label="%{_('language')}" name="language" list="{}"></s:select>
	<s:textarea label="%{_('source_code')}" name="source_code" />
</s:form>
</j:dialog>

<s:include value="../include/footer.jsp"></s:include>
<script>
$(function(){
	oj.loadProblem();
	$(window).hashchange(function(){
		oj.loadProblem();
	});
});
</script>
</c:html>