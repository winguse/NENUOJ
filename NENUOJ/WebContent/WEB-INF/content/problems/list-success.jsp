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
		"sDom": '<"H"if>t<"F"plr>',
		"bProcessing": true,
		"bServerSide": true,
		"iDisplayLength": 50,
		"bStateSave":true,
		"oLanguage": {
			"sInfo": "_START_ to _END_ of _TOTAL_ problems",//TODO replace to _("xxx")
			"sInfoEmpty": "No problems",
			"sInfoFiltered": " (filtering from _MAX_ total problems)"
		},
		"aaSorting": [[ 1, "asc" ]],
		"sAjaxSource": "<s:url action="list" namespace="/problems/json"/>",
		"fnServerData": function ( sSource, _aoData, fnCallback ) {
			var aoData={};
			for(var i in _aoData){
				aoData[_aoData[i].name]=_aoData[i].value;
			}
			var orderIndex=aoData.iSortCol_0+1;
			console.log(aoData.sSortDir_0);
			if(aoData.sSortDir_0=="desc")
				orderIndex=-orderIndex;
			$.getJSON( sSource, {
				orderByIndex:orderIndex,
				filterString:aoData.sSearch,
				page:aoData.iDisplayStart/aoData.iDisplayLength,
				pageSize:aoData.iDisplayLength
			}, function (json) {
				fnCallback({
					sEcho:aoData.sEcho,
					iTotalDisplayRecords:json.totalCount,
					iTotalRecords:json.allProblemCount,
					aaData:json.data
				});
			} );
		},
		"aoColumns": [{
				"sClass": ""//judger soruce
			},{
				"sClass": ""//problem number
			},{
				"sClass": ""//problem title
			},{
				"sClass": ""//accepted
			},{
				"sClass": ""//submitted
			},{
				"fnRender": function ( oObj ) {
					var t=oObj.aData[3]==0?1:0;
					return parseInt(oObj.aData[3]/t*10000)/100.0+"%";
				},
				"sClass": ""//rate
			},{
				"sClass":""//source
			}
		],
		"sPaginationType": "full_numbers",
		"bJQueryUI": true
	} );
});
</script>
</div>


<s:include value="../include/footer.jsp"></s:include>
</c:html>