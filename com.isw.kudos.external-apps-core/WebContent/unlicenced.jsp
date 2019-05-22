<!DOCTYPE  html>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="com.isw.kudos.external.apps.AppLoader"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
	<script>
		window.kudosAppLoader = {
			appLoaderConfig: <%= AppLoader.getConfig() %>,
			contextPath: "<%= request.getContextPath() %>"
		};

		<%@include file="resources/url-polyfill.min.js" %>
		<%@include file="resources/jquery.min.js" %>
		<%@include file="resources/loader.min.js" %>
	</script>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>...</title>
	<style>
		<%@include file="resources/default.css" %>
	</style>
	<%= AppLoader.injectCSS() %>
</head>
<body class="lotusui lotusui30 lotusui30dojo lotusui30_body lotusui30_fonts lotusui30 lotusBlue lotusSpritesOn">
	<script>window.kudosAppLoader.go();</script>
	<h3><%= AppLoader.getNotAuthMessage() %></h3>
</body>
</html>
