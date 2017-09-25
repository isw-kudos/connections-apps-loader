package com.isw.kudos.external.apps;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.ObjectName;

import org.json.JSONException;
import org.json.JSONObject;

import com.ibm.websphere.management.AdminService;
import com.ibm.websphere.management.AdminServiceFactory;

public class AppLoader {
	
	protected final static String clsName = AppLoader.class.getSimpleName();
	protected final static Logger log = Logger.getLogger(AppLoader.class.getName());
	static final String WAS_CONFIG_VARIABLE = "EXTERNAL_APPS_CONFIG";
	static JSONObject config = null;
	
	/**
	 * Gets the path stored under the webSphere variable ACTIVITIES_CONTENT  
	 * @return the value of the WebSphere variable
	 */
	public static JSONObject getConfig()
	{
		final String funcName = "getConfig";
		if(config == null)
		{
			String configStr = getWebsphereVariableValue(WAS_CONFIG_VARIABLE);
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
	
	/**
	 * Returns the websphere variable value given by variable name
	 * Cannot be called from a non-servlet class
	 * @param variableName - name of the websphere variable. e.g. PROFILE_STATS_DIR
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getWebsphereVariableValue(String variableName){
		final String funcName = "getWebsphereVariableValue";
		String value = null;
		try
		{
			log.logp(Level.FINE, clsName, funcName, "Getting websphere variable:"+variableName);
			//get the websphere variable for the file path for profiles stats directory
			AdminService adminService = AdminServiceFactory.getAdminService();
		    ObjectName queryName = new ObjectName( "WebSphere:*,type=AdminOperations" );
		    Set objs = adminService.queryNames( queryName, null );
		    if ( !objs.isEmpty() )
		    {
		        ObjectName thisObj = (ObjectName)objs.iterator().next();
		        String opName = "expandVariable";
		        String signature[] = { "java.lang.String" };
		        String params[] = { "${"+variableName+"}" } ;
		        value = adminService.invoke( thisObj, opName, params, signature ).toString();
		    }
			log.logp(Level.FINE, clsName, funcName, "Got variable value:"+value);
		}
		catch(Exception e)
		{
			String strLog = "Websphere Variable - Exception <" + e.getLocalizedMessage() + ">";
			log.logp(Level.SEVERE, clsName, funcName, strLog);
			log.logp(Level.FINE, clsName, funcName, strLog, e);				
		}
		return value;
	}
}
