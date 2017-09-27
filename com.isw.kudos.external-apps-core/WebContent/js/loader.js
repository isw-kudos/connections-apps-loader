window.kudosAppLoader = window.kudosAppLoader || {};

(function main() {
	var connectionsPage = {};
	
	function setTitle(str) {
		window.document.title = str || "";
	}
	
	function setIcon(iconUrl) {
		var link = document.querySelector("link[rel*='icon']");
		if(!link) {
			link = document.createElement('link');
			document.getElementsByTagName('head')[0].appendChild(link);
		}
	    link.type = 'image/x-icon';
	    link.rel = 'shortcut icon';
	    link.href = iconUrl;
	}
	
	function writeToPage(str) {
		document.write(str);
	}
	
	function getTitleCase(str) {
		if(!str) return null;
		return str.charAt(0).toUpperCase() + str.slice(1);
	}

	function redirectToLogin() {
		//try redirecting through the login page
		window.location.href = "/blogs/roller-ui/login-redirect.jsp?redirect="+encodeURIComponent(window.location.href);
	}

	//function to be called in the body to load iframe
	window.kudosAppLoader.go = function() {
		var config = window.kudosAppLoader.appLoaderConfig;
		var contextPath = window.kudosAppLoader.contextPath;
		var urlParts = window.location.pathname.replace(contextPath,"").split("/");
		var remoteAppName = urlParts.length > 1 ? urlParts[1] : null;
		var appContext = contextPath + "/"+remoteAppName;
		var configURLStr = remoteAppName ? config[remoteAppName] : null;
		var appFrame = document.getElementById("app-frame");
		
		//add the Connections body (header etc)
		writeToPage(connectionsPage.body);
		
		//ensure the header height is reasonable
		var bodyHeight = $(document.body).height();
		if(bodyHeight && bodyHeight < 100)
			appFrame.style.paddingTop = bodyHeight+'px';
		
		//check if we're showing a page!
		if(configURLStr) {
			var configURL = new URL(configURLStr);
			
			//ensure Connections title is cleared
			setTitle(getTitleCase(remoteAppName));		
			
			function receiveAppMessage(event) {
				if(!event || !event.data || event.origin !== configURL.origin) return;
				
				var data = event.data;
				if(data.route) history.replaceState(null, null, appContext + data.route);
				if(data.title) setTitle(data.title);
				if(data.icon) setIcon(data.icon);
			}
			window.addEventListener("message", receiveAppMessage, false);
			
			//load our frame to the correct route
			if(appFrame) {
				var appRoot = configURL.origin + (configURL.pathname==='/' ? '' : configURL.pathname);
				var currentAppRoute = window.location.pathname.replace(appContext,"");
				
				var searches = [];
				if(window.location.search) searches.push(window.location.search.replace('?',''));
				if(configURL.search) searches.push(configURL.search.replace('?',''));
				var currentSearch = searches.length ? '?'+searches.join('&') : '';
				var currentHash = window.location.hash || configURL.hash;
//				console.log('setting', appRoot, currentAppRoute, currentSearch, currentHash);
				appFrame.src = appRoot + currentAppRoute + currentSearch + currentHash;
			}
		}
		else {
			if(appFrame) appFrame.style.display = 'none';
			
			var linkWrapper = $('<div class="app-links" ></div>');
			$(document.body).append(linkWrapper);
			for(var remoteAppName in config) {
				var link = $('<span class="app-link"><a href="'+contextPath+'/'+remoteAppName+'">'+getTitleCase(remoteAppName)+'</a></span>');
				linkWrapper.append(link);
			}
		}
	};

	//Initialisation
	var connURL = "/blogs/roller-ui/about.do";
	function getConnectionsPage() {
		$.ajax({
			url: connURL,///activities/service/html/mainpage#aboutpage",///search/web/jsp/toolsHomepage.jsp",
			type : "GET",
			dataType : "text",
			async: false,
			cache: false
		}).done(function(data, textStatus, jqXHR) {
			function extractTagContent(str, tag) {
				//ensure case insensitivity when searching for tag
				var lowerStr = str.toLowerCase();
				var lowerTag = tag.toLowerCase();
				var start = lowerStr.indexOf("<"+lowerTag);
				if(start>-1) {
					start = lowerStr.indexOf(">", start) + 1;
		            var end = lowerStr.indexOf("</" + lowerTag, start);
		            if(end > start)
		            	return str.substring(start, end);
				}
				return null;
			}
			
			//remove annoying things
			var headStr = extractTagContent(data, "head");
			headStr = headStr.replace(/<title>[^<]*<\/title>/, '');
			headStr = headStr.replace(/<meta[^>]*>/g, '');
			//how could this possibly go wrong...?
			headStr = headStr.replace(/document.title[\s]*=[^;]*;/g, '');
			connectionsPage.head = headStr;
			
			var bodyStr = extractTagContent(data, "body");
			//how could this possibly go wrong...?
			bodyStr = bodyStr.replace(/document.title[\s]*=[^;]*;/g, '');
			connectionsPage.body = bodyStr;
			
		}).fail(function(xhr, textStatus, errorThrown) {
			console.error("Connections integration load failure",arguments);
			redirectToLogin();
		});
	}
	
	getConnectionsPage();
	writeToPage(connectionsPage.head);
	
	//check Connections variable to see if header worked
	if(!window.appName) redirectToLogin();
})();
