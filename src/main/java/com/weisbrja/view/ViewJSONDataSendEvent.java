package com.weisbrja.view;

import com.weisbrja.event.Event;

public class ViewJSONDataSendEvent implements Event {

	private final String viewJSONData;

	public ViewJSONDataSendEvent(String viewJSONData) {
		this.viewJSONData = viewJSONData;
	}

	public String getViewJSONData() {
		return viewJSONData;
	}
}
