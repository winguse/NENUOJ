<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://htmlcompressor.googlecode.com/taglib/compressor" prefix="c"%>
<c:html enabled="${site.enableHtmlCompress}" removeIntertagSpaces="true">
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-bootstrap-tags" prefix="b"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${site.title} - Title</title>
<b:head />
<link href="style/global.css" rel="stylesheet">
</head>
<body>
<script src="js/global.js"></script>
</body>
</html>
</c:html>