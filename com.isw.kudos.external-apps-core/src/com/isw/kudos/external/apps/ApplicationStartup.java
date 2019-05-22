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
	 */
	public void init() throws ServletException
	{
		super.init();

		AppLoader.loadVariables();
		servletInstance = this;
	}
}
