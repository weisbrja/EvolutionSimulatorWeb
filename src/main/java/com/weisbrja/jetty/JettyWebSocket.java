package com.weisbrja.jetty;

import com.weisbrja.AppContext;
import com.weisbrja.population.Population;
import com.weisbrja.simulation.SimulationModeChangedEvent;
import com.weisbrja.simulation.SimulationModeToggledEvent;
import com.weisbrja.view.ViewJSONDataSendEvent;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

import java.io.IOException;

@WebSocket
public class JettyWebSocket {

	private Session session;
	private RemoteEndpoint remote;

	public JettyWebSocket() {
		AppContext.getInstance().getEventBus().listenFor(ViewJSONDataSendEvent.class, this::handleViewJSONDataSend);
	}

	@OnWebSocketConnect
	public void onWebSocketConnect(Session session) {
		System.out.println("Connected: " + session);
		this.session = session;

		remote = session.getRemote();

		Population population = new Population(4, 1500);
		AppContext.getInstance().getEventBus().emit(new SimulationModeChangedEvent(true));

		population.initialize(10000);
		new Thread(population::startSimulating).start();
	}

	@OnWebSocketClose
	public void onWebSocketClose(int statusCode, String reason) {
		System.out.println("Closed: " + statusCode + ", " + reason);
		session = null;
		remote = null;
	}

	@OnWebSocketError
	public void onWebSocketError(Throwable throwable) {
		System.out.println("Error: " + throwable);
	}

	@OnWebSocketMessage
	public void onWebSocketMessage(String message) {
		System.out.println("Received: " + message);
		if (message.equals("t"))
			AppContext.getInstance().getEventBus().emit(new SimulationModeToggledEvent());
	}

	private void handleViewJSONDataSend(ViewJSONDataSendEvent viewJSONDataSendEvent) {
		if (this.session != null && session.isOpen() && remote != null) {
			try {
				remote.sendString(viewJSONDataSendEvent.getViewJSONData());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
