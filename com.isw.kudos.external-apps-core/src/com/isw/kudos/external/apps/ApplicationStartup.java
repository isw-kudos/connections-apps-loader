package com.isw.kudos.external.apps;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

//@WebServlet(loadOnStartup=1, urlPatterns="/ApplicationStartup")
public class ApplicationStartup extends HttpServlet implements Servlet  {
	private static final long serialVersionUID = 1L;
	public static ApplicationStartup servletInstance = null;

	/**
	 * Called by the WAS servlet container. 
	 * Initialises the timer, 
	 * deletes decertified badges, 
	 * deletes awarded rank badges 
	 * and saves the profiles stats directory path to the  
	 */
	public void init() throws ServletException
	{
		super.init();

		saveWASVariables();
		servletInstance = this;
	}

	/**
	 * Get the config from the websphere variable on startup
	 */
	private void saveWASVariables()
	{
		AppLoader.getConfig();
	}
}
