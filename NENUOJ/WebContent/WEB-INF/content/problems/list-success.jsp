<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://htmlcompressor.googlecode.com/taglib/compressor" prefix="c"%>
<c:html enabled="${site.enableHtmlCompress}" removeIntertagSpaces="true">
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-jquery-tags" prefix="j"%>
<%@ taglib uri="/struts-bootstrap-tags" prefix="b"%>
<s:include value="../include/init.jsp"></s:include>
<s:set var="pageTitle" value="getText('problems-list')"/>
<title><s:text name="site.title"/> - <s:property value="#pageTitle"/></title>
<s:include value="../include/header.jsp"></s:include>
<div>
<s:form id="addRemoteProblem_form" action="add-remote-problem" namespace="/problems/json" theme="bootstrap">
<s:select
	tooltip="%{getText('tooltip.judgerSource')}"
	label="%{getText('judgerSource')}"
	list="judgeSourceList"
	name="judgerSource"
	emptyOption="false"/>
<s:textfield label="%{getText('problemNumber')}" name="problemNumber" tooltip="%{getText('tooltip.problemNumber')}" />
<s:submit id="register_form_submit" value='%{_("submit")}' cssClass="btn btn-primary" />
</s:form>
<table id="problemList">
	<thead>
		<tr>
			<th><s:text name="judger_source"/></th>
			<th><s:text name="problem_number"/></th>
			<th><s:text name="problem_title"/></th>
			<th><s:text name="accepted"/></th>
			<th><s:text name="submitted"/></th>
			<th><s:text name="ac_rate"/></th>
			<th><s:text name="source"/></th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td colspan="8">Loading data from server</td>
		</tr>
	</tbody>
</table>


<script>
$(function(){
	WinguseAjaxForm("#addRemoteProblem_form",function(data){
		if(data.code==0)
			alert(data.message);
	});
	$('#problemList').dataTable( {
		"bProcessing": true,
		"bServerSide": true,
		"sAjaxSource": "<s:url action="list" namespace="/problems/json"/>",
		"fnServerData": function ( sSource, aoData, fnCallback ) {
			console.log(sSource+"\n",aoData);
			fnCallback(aoData);
			/* Add some extra data to the sender #/
			aoData.push( { "name": "more_data", "value": "my_value" } );
			$.getJSON( sSource, aoData, function (json) { 
				/# Do whatever additional processing you want on the callback, then tell DataTables #/
				fnCallback(json);
			} );*/
		},
		"sPaginationType": "full_numbers",
		"bJQueryUI": true
	} );
});
</script>
</div>


<s:include value="../include/footer.jsp"></s:include>
</c:html>