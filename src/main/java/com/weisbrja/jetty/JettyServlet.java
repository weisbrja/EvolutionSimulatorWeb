package com.weisbrja.jetty;

import org.eclipse.jetty.websocket.server.JettyWebSocketServlet;
import org.eclipse.jetty.websocket.server.JettyWebSocketServletFactory;

public class JettyServlet extends JettyWebSocketServlet {

	@Override
	protected void configure(JettyWebSocketServletFactory factory) {
		factory.register(JettyWebSocket.class);
	}
}
