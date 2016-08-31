package com.itheima.auto_scroll;

public class AdapterBean {
	private int resourceId;
	private String text;
	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public AdapterBean(int resourceId, String text) {
		super();
		this.resourceId = resourceId;
		this.text = text;
	}
	
}
