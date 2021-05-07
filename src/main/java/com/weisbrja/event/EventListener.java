package com.weisbrja.event;

public interface EventListener<T extends Event> {

	void handle(T event);
}
