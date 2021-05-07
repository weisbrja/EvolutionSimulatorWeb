package com.weisbrja.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.server.config.JettyWebSocketServletContainerInitializer;

import java.net.URL;

public class JettyServer {

	private final Server server;

	public JettyServer(int port) {
		server = new Server(port);

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);

		ServletHolder servletHolder = new ServletHolder("jetty", new JettyServlet());
		context.addServlet(servletHolder, "/jetty");

		URL urlStatics = Thread.currentThread().getContextClassLoader().getResource("index.html");
		assert urlStatics != null;
		String urlBase = urlStatics.toExternalForm().replaceFirst("/[^/]*$", "/");
		ServletHolder defaultServletHolder = new ServletHolder("default", new DefaultServlet());
		defaultServletHolder.setInitParameter("resourceBase", urlBase);
		defaultServletHolder.setInitParameter("dirAllowed", "true");
		context.addServlet(defaultServletHolder, "/");

		JettyWebSocketServletContainerInitializer.configure(context, null);
	}

	public void start() {
		try {
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
