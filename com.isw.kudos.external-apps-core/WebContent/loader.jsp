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
		
		<%@include file="js/url-polyfill.min.js" %>
		<%@include file="js/jquery.min.js" %>
		<%@include file="js/loader.min.js" %>
	</script>
	
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>...</title>
	
	<style>
	body {
		margin: 0 !important;
	}
	.lotusMain, .lotusFooter, .lotusLegal, .lotusTitleBarContent {
		display: none;
	}
	.lotusTitleBar2 .lotusInner {
		padding: 0 !important;
		height: 0 !important;
	}
	
	.icSearchPaneButton {
		top: 6px !important;
	}
	
	.app-frame {
		position: absolute;
		top: 0;
		height: 100vh;
		width: 100vw;
		padding-top: 45px;
		box-sizing: border-box;
		border: 0;
	}
	.app-links {
	    /* margin-left: 50%; */
	    /* transform: translateX(-50%); */
	    /* width: auto; */
	    text-align: center;
        margin-top: calc(50vh - 45px);
	}
	.app-link {
		display: inline-block;
	    width: 140px;
	    font-size: 16px;
	    font-weight: bold;
	}
	</style>
</head>

<body class="lotusui lotusui30 lotusui30dojo lotusui30_body lotusui30_fonts lotusui30 lotusBlue lotusSpritesOn">
	<iframe id="app-frame" class="app-frame"></iframe>
	<script>window.kudosAppLoader.go();</script>
</body>

</html>