package com.isw.kudos.external.apps;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

public class AppLoader {

	protected final static String clsName = AppLoader.class.getSimpleName();
	protected final static Logger log = Logger.getLogger(AppLoader.class.getName());
	static final String WAS_CONFIG = "EXTERNAL_APPS_CONFIG";
	static final String WAS_CSS_INLINE = "EXTERNAL_APPS_CSS_INLINE";
	static final String WAS_CSS_URL = "EXTERNAL_APPS_CSS_URL";
	static final String WAS_AUTH_FAILURE_MESSAGE = "EXTERNAL_APPS_AUTH_FAILURE_MESSAGE";
	static JSONObject config = null;
	static JSONObject css = null;
	static String notAuth = null;

	/**
		We can only access the WebSphere variables on application startup
		Get them and save in memory
	**/
	public static void loadVariables() {
		AppLoader.getConfig();
		AppLoader.getCSS();
		AppLoader.getNotAuthMessage();
	}

	/**
	 * Gets the path stored under the webSphere variable ACTIVITIES_CONTENT
	 * @return the value of the WebSphere variable
	 */
	public static JSONObject getConfig()
	{
		final String funcName = "getConfig";
		if(config == null)
		{
			String configStr = WebSphere.getVariable(WAS_CONFIG);
			String errMsg = "Cannot load app configuration. " +
				"Please ensure you have created a WebSphere variable in the format: " +
				"{ \"ideas\": \"https://localdev.isw.net.au\", \"buzzy\":\"https://buzzy.buzz\" }";

			if(configStr==null || configStr.isEmpty()) {
				log.logp(Level.WARNING, clsName, funcName, errMsg);
				return new JSONObject();
			}

			try {
				config = new JSONObject(configStr);
			} catch (JSONException e) {
				log.logp(Level.SEVERE, clsName, funcName, "Error occurred while loading the config. "+errMsg, e);
			}
		}
		return config;
	}

	public static JSONObject getCSS()
	{
		final String funcName = "getCSS";
		if(css == null)
		{
			css = new JSONObject();
			String inline = WebSphere.getVariable(WAS_CSS_INLINE);
			String url = WebSphere.getVariable(WAS_CSS_URL);

			try {
				if (inline!=null) css.put("inline", inline);
				if (url!=null) css.put("url", url);
			} catch (JSONException e) {
				log.logp(Level.WARNING, clsName, funcName, "CSS error", e);
			}
		}
		return css;
	}

	public static String injectCSS() {
		String html = "";
		JSONObject css = getCSS();
		String inline = css.optString("inline");
		if (inline!=null) html += "<style>"+inline+"</style>";
		String url = css.optString("url");
		if(url!=null) html += "<link rel='stylesheet' type='text/css' href='"+url+"' title='Custom Styles'>";
		return html;
	}

	public static String getNotAuthMessage() {
		if(notAuth == null)
		{
			notAuth = WebSphere.getVariable(WAS_AUTH_FAILURE_MESSAGE);
			if (notAuth == null) {
				notAuth = "Sorry, you are not authorised/licenced to access this application. Please contact your administrator.";
			}
		}
		return notAuth;
	}
}
