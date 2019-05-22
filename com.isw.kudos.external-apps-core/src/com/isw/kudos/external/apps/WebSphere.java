package com.isw.kudos.external.apps;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.ObjectName;

import com.ibm.websphere.management.AdminService;
import com.ibm.websphere.management.AdminServiceFactory;

public class WebSphere {

	protected final static String clsName = AppLoader.class.getSimpleName();
	protected final static Logger log = Logger.getLogger(AppLoader.class.getName());

	/**
	 * Returns the websphere variable value given by variable name
	 * Cannot be called from a non-servlet class
	 * @param variableName - name of the websphere variable. e.g. PROFILE_STATS_DIR
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getVariable(String variableName){
		final String funcName = "getVariable";
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
	          //Check the value has really been substituted
	          if (value!=null && value.equals("${"+variableName+"}")) {
	            value = null;
	          }
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
