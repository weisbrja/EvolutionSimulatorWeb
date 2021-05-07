package com.weisbrja.event;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EventBus {

	private final Map<Class, List<EventListener>> eventListenerMap;

	public EventBus() {
		eventListenerMap = new HashMap<>();
	}

	public <T extends Event> void listenFor(Class<T> eventClass, EventListener<T> eventListener) {
		if (!eventListenerMap.containsKey(eventClass))
			eventListenerMap.put(eventClass, new LinkedList<>());

		eventListenerMap.get(eventClass).add(eventListener);
	}

	public <T extends Event> void emit(T event) {
		Class eventClass = event.getClass();

		for (EventListener eventListener : eventListenerMap.get(eventClass))
			eventListener.handle(event);
	}
}
